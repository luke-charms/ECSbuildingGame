import bugs.Bug;
import building.Building;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import students.Students;
import students.Team;

public class Battle {

  protected Team team;
  protected Building building;
  protected int round;
  //I decided to implement a counter/round variable to show the player what round they are on

  public Battle(Team team, Building building) {
    this.team = team;
    this.building = building;
  }

  /*
  My manageTeam method is programmed as follows: to begin with, there should always be, as a minimum 3 students in the team.
  Once there are 3 students in the team, it is recommended upgrading all of them to level 2, in the order: 1) CyberStudent  2) AiStudent
  3) CsStudent  4) SeStudent, (as designated by the student's power rankings).  This is because the CyberStudent
  is by far the most powerful student and the rest are determined/calculated mathematically as the order prescribes. This is accomplished by
  sorting the students using the Comparable interface and then calculating the student with the lowest level by using the getLevel method on the
  last student in the sorted ArrayList. The method then initialises a counter and checks through the sorted array to see whether the first, second...
  have the same level as the lowest level student. If any student does not have the same level as the lowest level, the counter is incremented until
  the student with the next lowest level is found and 'tagged' by the counter. Finally, the method checks whether the player has sufficient knowledge
  points to upgrade the student and upgrades the student if it does.

  After all students are upgraded to level 2, the manageTeam method will recruit 3 more students and upgrade all of them to level 2. After that, the
  method will upgrade all the team members to level 3 and recruit 6 more students until there are 12 students. The method will then continue as such,
  upgrading all the students to the number of students in the team, take away 7 (e.g. 12 students in the array, upgrade all students to level 5 then add
  one more, then upgrade all students to level 6, since there are 13 students in array now).
   */

  public void manageTeam() {
    try {
      if (team.getStudents().length <= 2 && team.getKnowledgePoints() >= team.getNewStudentCost()) {
        team.recruitNewStudent();
      } else if (team.getStudents().length > 2) {
        ArrayList<Students> sortedStudents = new ArrayList<>(Arrays.asList(team.getStudents()));
        Collections.sort(sortedStudents);
        int lowestLevel = sortedStudents.get(sortedStudents.size() - 1).getLevel();
        if (lowestLevel == 1) {
          int i = 0;
          for (Students student : sortedStudents) {
            if (student.getLevel() != lowestLevel) {
              i++;
            }
          }
          if (team.getKnowledgePoints() >= sortedStudents.get(i).upgradeCost()) {
            team.upgrade(sortedStudents.get(i));
          }
        } else if (lowestLevel == 2 && team.getStudents().length < 6
            && team.getKnowledgePoints() >= team.getNewStudentCost()) {
          team.recruitNewStudent();
        } else if (lowestLevel == 3 && team.getStudents().length < 12
            && team.getKnowledgePoints() >= team.getNewStudentCost()) {
          team.recruitNewStudent();
        } else if (lowestLevel == ((team.getStudents().length) - 7)
            && team.getStudents().length > 11
            && team.getKnowledgePoints() >= team.getNewStudentCost()) {
          team.recruitNewStudent();
        } else {
          int i = 0;
          for (Students student : sortedStudents) {
            if (student.getLevel() != lowestLevel) {
              i++;
            }
          }
          if (team.getKnowledgePoints() >= sortedStudents.get(i).upgradeCost()) {
            team.upgrade(sortedStudents.get(i));
          }
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void step() {
    try {
      Thread.sleep(1500);
      manageTeam();
      System.out.println(" ");
      System.out.println(
          "***************************** GAME UPDATES *******************************");
      building.bugsMove();
      int knowledgePointsGained = team.studentsAct(building);
      //students act to defence building method

      System.out.println("\n" +
          "This is round: " + round + "\n" +
          "The knowledge points gained this round were: " + knowledgePointsGained + "\n" +
          "The Team's current knowledge points are: " + team.getKnowledgePoints() + "\n" +
          "The current recruiting cost for a student is: " + team.getNewStudentCost() + "\n" +
          "The Building's current construction points are: " + building.getConstructionPoints()
          + "\n" +
          "***************************** STUDENTS *******************************"
      );
      for (Students student : team.getStudents()) {
        System.out.println(
            student + "'s level is " + student.getLevel() + ", next super attack is in: "
                + student.getDelay());
        //this piece of code prints out a more readable format of what the student's level/delay is
      }
      System.out.println("****************************** BUGS ******************************");
      for (Bug bug : building.getAllBugs()) {
        System.out.println(
            bug.getName() + "'s (" + bug + ") current level is: " + bug.getLevel() + ", HP is: "
                + bug.getCurrentHp() + ", floor is: " + bug.getCurrentFloor() + ", steps left are: "
                + bug.getCurrentSteps());
      }
      round += 1;
      //increments the round by 1 every round
      System.out.println(
          "------------------------------- ROUND OVER -------------------------------------");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public int getRound() {
    //piece of code used in EcsBuildingDefence class to figure out when to spawn next wave of bugs
    return round;
  }

  public void setRound(int round) {
    //method implemented to allow the SAVE file to initialise the round that was saved
    this.round = round;
  }
}