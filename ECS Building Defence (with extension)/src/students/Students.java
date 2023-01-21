package students;

import bugs.Bug;
import building.Building;
import java.util.Random;

public class Students implements Comparable<Students>, Student {
  /*
  I decided to use a superclass called Students in order to allow each individual
  student class to extend from it so certain methods could be written and then
  applied to all the students instead of individually
   */

  protected int level;
  protected int attack;
  protected int delay;
  protected int baseDelay;
  protected int powerRanking;

  /*
  I also decided to implement the comparable in order to sort the students in
  terms of their current level and their power ranking (explained below)
  */

  public Students(int level, int attack, int delay) {
    this.level = level;
    this.attack = attack;
    this.delay = delay;
  }

  public int getLevel() {
    return level;
  }

  public int upgradeCost() {
    return (int) (100 * Math.pow(2, level));
  }

  public int getPowerRanking() {
    //this is a method I implemented myself in order to sort the
    // student according to how effective they were in the game
    return powerRanking;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    //method implemented to allow the SAVE file to initialise the
    // delay the student's had when saved
    this.delay = delay;
  }

  public int defence(Building building) {
    /*
    the defence method creates an array of all the bugs in the building
    and initialises a method variable of knowledge points gained during the round.
    the method then checks to make sure the bugs array isn't empty.
     */
    Bug[] bugs = building.getAllBugs();
    int knowledgePointsGained = 0;
    if (bugs.length != 0) {
      if (this.delay < 2) {
        //if it isn't the student's special attack, the method will attack normally
        if (this instanceof AiStudent) {
          /*
          the AiStudent's special ability has 2 ways of initialising:
          1) if there's 3 bugs or more in the building, the method will perform
          normally and attack the first 3 bugs in the building and store
          any knowledge points gained if the bugs reach 0 Hp
           */
          if (bugs.length >= 3) {
            System.out.println("Machine Learning success! Direct hit on first 3 bugs!");
            for (int i = 0; i < 3; i++) {
              System.out.println(bugs[i].getName() + " was attacked with: " + ((int) Math.round(
                  this.attack * Math.pow(getLevel(), 1.2))) + " damage by " + this);
              //code to output a more readable format of the student's name
              bugs[i].damage((int) Math.round(this.attack * Math.pow(getLevel(), 1.2)));
              if (bugs[i].getCurrentHp() == 0) {
                System.out.println(bugs[i].getName() + " removed!");
                knowledgePointsGained = knowledgePointsGained + ((bugs[i].getLevel()) * 20);
                building.removeBug(bugs[i]);
              }
            }
          } else {
            /*
            2) if there's less than 3 bugs in the building, the method will attack
            all the bugs present in the building and store any knowledge points
            gained if the bugs reach 0 Hp
             */
            System.out.println(
                "Machine Learning success! Direct hit on first " + bugs.length + " bugs!");
            for (Bug bug : bugs) {
              System.out.println(bug.getName() + " was attacked with: " + ((int) Math.round(
                  this.attack * Math.pow(getLevel(), 1.2))) + " damage by " + this);
              //this statement was used in order to output a readable format
              // of the student's damage to the bugs and by whom
              bug.damage((int) Math.round(this.attack * Math.pow(getLevel(), 1.2)));
              if (bug.getCurrentHp() == 0) {
                System.out.println(bug.getName() + " removed!");
                //all the actions and damage is printed to the console
                knowledgePointsGained = knowledgePointsGained + ((bug.getLevel()) * 20);
                building.removeBug(bug);
              }
            }
          }
          this.delay = this.baseDelay;
          return knowledgePointsGained;

        } else if (this instanceof CsStudent) {
          /*
           2) CsStudent special ability:
          the CsStudent will simply deal 4 times the normal damage
          dealt to the first bug and print all actions to the console
           */
          System.out.println("Charge up of pair programming finished! Unleash 4x the damage!");
          System.out.println(bugs[0].getName() + " was attacked with: " + ((int) (4 * (Math.round(
              this.attack * Math.pow(getLevel(), 1.2))))) + " damage by " + this);
          bugs[0].damage((int) (4 * (Math.round(this.attack * Math.pow(getLevel(), 1.2)))));
          this.delay = this.baseDelay;

        } else if (this instanceof CyberStudent) {
          /*
          3) CyberStudent special ability:
          the CyberStudent's special ability initialises a temporary
          double to calculate the percentage chance of removing the
          first bug in the building.
           */
          double temporary;
          if ((this.getLevel() + 20) > 50) {
            temporary = 0.5;
          } else {
            temporary = ((((double) (this.getLevel()) + 20)) / 100);
          }
          boolean value = new Random().nextDouble() <= temporary;
          /*
          a random double is created and checked to see if it is smaller than
          the temporary double. if it is, the first bug is removed
          and the knowledge points gained from it are stored
           */
          if (value) {
            System.out.println("Antivirus software setup success! Instant removal of first bug!");
            knowledgePointsGained = knowledgePointsGained + ((bugs[0].getLevel()) * 20);
            System.out.println(bugs[0].getName() + " removed!");
            building.removeBug(bugs[0]);
            this.delay = this.baseDelay;
            return knowledgePointsGained;
          } else {
            /*
            if the random double is not smaller than the temporary double,
            the CyberStudent deals double damage to the first bug instead
             */
            System.out.println(
                "Antivirus software setup failed! Initiate backup of double damage instead!");
            System.out.println(bugs[0].getName() + " was attacked with: " + ((int) (2 * (Math.round(
                this.attack * Math.pow(getLevel(), 1.2))))) + " damage by " + this);
            bugs[0].damage((int) (2 * (Math.round(this.attack * Math.pow(getLevel(), 1.2)))));
            //all damage and special attacks are printed to the console
          }
          this.delay = this.baseDelay;

        } else if (this instanceof SeStudent) {
          /*
          4) SeStudent special ability:
          the SeStudent's special ability works in the exact
          same way as the AiStudent, only it checks if there are
          5 or more bugs in the building and then splits into
          either slowing down all 5 bugs or the number of
          bugs in the building
           */
          if (bugs.length >= 5) {
            System.out.println("Group work initiated! Slow down of first 5 bugs by 2 steps!");
            for (int i = 0; i < 5; i++) {
              bugs[i].slowDown(2);
            }
          } else {
            System.out.println(
                "Group work initiated! Slow down of first " + bugs.length + " by 2 steps");
            for (Bug bug : bugs) {
              bug.slowDown(2);
            }
          }
          this.delay = this.baseDelay;
        }
      } else {
        /*
        if it is not the student's special ability turn
        each student will just attack the first bug in
        the building with their normal attack
         */
        this.delay = this.delay - 1;
        System.out.println(bugs[0].getName() + " was attacked with " + ((int) Math.round(
            this.attack * Math.pow(getLevel(), 1.2))) + " damage by " + this);
        bugs[0].damage((int) Math.round(this.attack * Math.pow(getLevel(), 1.2)));
      }

      if ((bugs[0].getCurrentHp() == 0)) {
        //if the first bug in the building is attacked to 0 HP,
        //the knowledge points gained are stored and returned by the method
        System.out.println(bugs[0].getName() + " removed!");
        knowledgePointsGained = ((bugs[0].getLevel()) * 20);
        building.removeBug(bugs[0]);
      } else {
        return 0;
      }
      return knowledgePointsGained;
    } else {
      return 0;
    }
  }

  public void upgradeStudent() {
    this.level += 1;
  }

  @Override
  public int compareTo(Students student) {
    /*
    method to sort the students in terms of the
    level they currently are at and then in
    terms of their power ranking, which was
    explained above!
     */
    if (student.getLevel() > this.getLevel()) {
      return 1;
    } else if (student.getLevel() < this.getLevel()) {
      return -1;
    } else {
      return Integer.compare(this.getPowerRanking(), student.getPowerRanking());
    }
  }

  @Override
  public String toString() {
    //this piece of code prints out a more readable format of
    // what the student's class is
    return getClass().toString().replace("class students.", "");
  }
}