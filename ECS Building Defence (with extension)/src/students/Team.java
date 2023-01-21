package students;

import building.Building;
import java.util.ArrayList;
import java.util.Random;

public class Team {

  protected int knowledgePoints;
  protected int studentCost = 100;
  ArrayList<Students> students = new ArrayList<>();

  public Team(int knowledgePoints) {
    this.knowledgePoints = knowledgePoints;
  }

  public int getKnowledgePoints() {
    return knowledgePoints;
  }

  public Students[] getStudents() {
    return students.toArray(new Students[0]);
  }

  public int studentsAct(Building building) {
    int roundKnowledgePoints = 0;
    for (Students student : students) {
      roundKnowledgePoints += student.defence(building);
    }
    this.knowledgePoints += roundKnowledgePoints;
    return roundKnowledgePoints;
    /*
    I decided to configure the studentsAct method to return the knowledge
    points gained during the round, so that it can be printed to the console.
     */
  }

  public int getNewStudentCost() {
    return studentCost;
  }

  public void setNewStudentCost(int studentCost) {
    //method implemented to allow the SAVE file to initialise the student cost that was saved
    this.studentCost = studentCost;
  }

  public void recruitNewStudent() throws Exception {
    if (knowledgePoints >= getNewStudentCost()) {
      int value = new Random().nextInt(4);
      if (value == 0) {
        students.add(new AiStudent(1));
      } else if (value == 1) {
        students.add(new CsStudent(1));
      } else if (value == 2) {
        students.add(new CyberStudent(1));
      } else {
        students.add(new SeStudent(1));
      }
      knowledgePoints -= getNewStudentCost();
      studentCost += 10;
    } else {
      throw new Exception("Insufficient Knowledge Points!");
      //the exception thrown if the player doesn't have enough knowledge points
    }
  }

  public void upgrade(Students student) throws Exception {
    if (knowledgePoints >= student.upgradeCost()) {
      knowledgePoints -= student.upgradeCost();
      student.upgradeStudent();
    } else {
      throw new Exception("Insufficient Knowledge Points!");
      //the exception thrown if the player doesn't have enough knowledge points
    }
  }

  public void addStudent(Students student) {
    //method implemented to allow the SAVE file to add the students to the team that were saved
    students.add(student);
  }
}