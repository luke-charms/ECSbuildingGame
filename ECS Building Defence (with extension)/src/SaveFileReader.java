import bugs.Bug;
import bugs.ConcurrentModificationBug;
import bugs.NoneTerminationBug;
import bugs.NullPointerBug;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import students.AiStudent;
import students.CsStudent;
import students.CyberStudent;
import students.SeStudent;
import students.Students;

public class SaveFileReader {

  BufferedReader reader;
  PrintStream output;

  public SaveFileReader(String filename) {
    //SaveFileReader constructor
    try {
      this.reader = new BufferedReader(new FileReader(filename));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  public String getLine() {
    //method to return each line of the save file
    try {
      return this.reader.readLine();
    } catch (Exception e) {
      e.getLocalizedMessage();
      return null;
    }
  }

  public boolean fileIsReady() {
    //method to check whether file is ready to be read from
    try {
      return this.reader.ready();
    } catch (Exception e) {
      //if not, an exception is thrown and printed to the console
      e.getLocalizedMessage();
      return false;
    }
  }

  public String[] loadGame() {
    //method to load construction points, top floor, file and current knowledge points
    return getLine().split(",");
  }

  public void update(Battle battle, String filename) {
    //method to update the SAVE file with the current state of the battle
    try {
      this.output = new PrintStream(filename);
      //flushes (cleans) the SAVE file and initialises a PrintStream object
    } catch (Exception e) {
      e.printStackTrace();
    }
    output.println(
        battle.building.getTopFloor() + "," + battle.building.getConstructionPoints() + ","
            + "easy.txt" + "," + battle.team.getKnowledgePoints());
    output.println(battle.team.getNewStudentCost() + "," + battle.getRound());
    for (Students student : battle.team.getStudents()) {
      output.print(student + "," + student.getLevel() + "," + student.getDelay() + ";");
    }
    output.println(" ");
    for (Bug bug : battle.building.getBugs()) {
      output.print(
          bug + "," + bug.getName() + "," + bug.getLevel() + "," + bug.getCurrentSteps() + ","
              + bug.getCurrentFloor() + "," + bug.getCurrentHp() + ";");
    }
    /*
    method loads top floor, construction points, knowledge points, round number, current student
    cost and all student and bug information into the SAVE file
     */
  }

  public ArrayList<Students> loadStudents() {
    //method to load all the saved students into the game
    ArrayList<Students> students = new ArrayList<>();
    String[] student = getLine().split(";");
    //splits all the students into each individual one with its components
    for (int i = 0; i < student.length - 1; i++) {
      String[] studentParts = student[i].split(",");
      if (studentParts[0].equals("AiStudent")) {
        students.add(new AiStudent(Integer.parseInt(studentParts[1])));
        students.get(i).setDelay(Integer.parseInt(studentParts[2]));
      } else if (studentParts[0].equals("SeStudent")) {
        students.add(new SeStudent(Integer.parseInt(studentParts[1])));
        students.get(i).setDelay(Integer.parseInt(studentParts[2]));
      } else if (studentParts[0].equals("CsStudent")) {
        students.add(new CsStudent(Integer.parseInt(studentParts[1])));
        students.get(i).setDelay(Integer.parseInt(studentParts[2]));
      } else {
        students.add(new CyberStudent(Integer.parseInt(studentParts[1])));
        students.get(i).setDelay(Integer.parseInt(studentParts[2]));
      }
      //creates a new student and adds them to the arraylist depending on their saved name
    }
    return students;
  }

  public ArrayList<Bug> loadBugs() {
    //method to load all the saved bugs into the game
    ArrayList<Bug> bugs = new ArrayList<>();
    String[] bug = getLine().split(";");
    //splits all the bugs into each individual one with its components
    for (int i = 0; i < bug.length; i++) {
      String[] bugParts = bug[i].split(",");
      if (bugParts[0].equals("ConcurrentModificationBug")) {
        bugs.add(new ConcurrentModificationBug((bugParts[1]), Integer.parseInt(bugParts[2]),
            Integer.parseInt(bugParts[3])));
        bugs.get(i).setCurrentFloor(Integer.parseInt(bugParts[4]));
        bugs.get(i).setCurrentHp(Integer.parseInt(bugParts[5]));
      } else if (bugParts[0].equals("NoneTerminationBug")) {
        bugs.add(new NoneTerminationBug(((bugParts[1])), Integer.parseInt(bugParts[2]),
            Integer.parseInt(bugParts[3])));
        bugs.get(i).setCurrentFloor(Integer.parseInt(bugParts[4]));
        bugs.get(i).setCurrentHp(Integer.parseInt(bugParts[5]));
      } else {
        bugs.add(new NullPointerBug(((bugParts[1])), Integer.parseInt(bugParts[2]),
            Integer.parseInt(bugParts[3])));
        bugs.get(i).setCurrentFloor(Integer.parseInt(bugParts[4]));
        bugs.get(i).setCurrentHp(Integer.parseInt(bugParts[5]));
      }
      //creates a new bug and adds them to the arraylist depending on their saved class name
    }
    return bugs;
  }
}