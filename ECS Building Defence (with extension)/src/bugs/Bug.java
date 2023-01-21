package bugs;

public class Bug implements Comparable<Bug> {
  //Comparable interface used to sort bugs in building class

  protected String name;
  protected int baseHp;
  protected int baseSteps;
  protected int level;

  protected int currentHp;
  protected int currentSteps;
  protected int currentFloor = -1;

  public Bug(String name, int baseHp, int baseSteps, int level) {
    this.name = name;
    this.baseHp = baseHp;
    this.baseSteps = baseSteps;
    this.level = level;
    this.currentHp = (int) Math.round(this.baseHp * (Math.pow(level, 1.5)));
  }

  public Bug(String name, int baseHp, int baseSteps, int level, int initialSteps) {
    this.name = name;
    this.baseHp = baseHp;
    this.baseSteps = baseSteps;
    this.level = level;
    this.currentSteps = initialSteps;
    this.currentHp = (int) Math.round(this.baseHp * (Math.pow(level, 1.5)));
  }

  public int getBaseSteps() {
    return baseSteps;
  }

  public int getLevel() {
    return level;
  }

  public int getCurrentHp() {
    return currentHp;
  }

  public void setCurrentHp(int currentHp) {
    //method implemented to allow the SAVE file to initialise
    // the current HP that the bugs had
    this.currentHp = currentHp;
  }

  public String getName() {                 //added this yourself!
    return name;
  }

  public int getCurrentSteps() {
    return currentSteps;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentFloor(int currentFloor) {
    //method implemented to allow the SAVE file to initialise
    // the floor that the bugs were saved on
    this.currentFloor = currentFloor;
  }

  public void move() {
    //moves the bug forward one step every time until it
    // has 0 steps left to move, when it will then move up a floor
    if (currentSteps > 0) {
      currentSteps -= 1;
    } else {
      currentFloor += 1;
      currentSteps = baseSteps - 1;
    }
  }

  public void damage(int amount) {
    //takes away the damage dealt to the bug from its current
    // hp and makes sure the hp is never negative but always 0
    currentHp -= amount;
    if (currentHp < 0) {
      currentHp = 0;
    }
  }

  public void slowDown(int steps) {
    currentSteps += steps;
  }

  @Override
  public int compareTo(Bug bug) {
    /*
   part of the comparable interface and used to
   order the bugs in terms of current floor and current
   steps required to move to the next floor
     */
    if (bug.getCurrentFloor() > this.getCurrentFloor()) {
      return 1;
    } else if (bug.getCurrentFloor() < this.getCurrentFloor()) {
      return -1;
    } else {
      return Integer.compare(this.getCurrentSteps(), bug.getCurrentSteps());
    }
  }


  @Override
  public String toString() {
    //this piece of code prints out a more readable format of
    // what the bug's class is
    return getClass().toString().replace("class bugs.", "");
  }
}