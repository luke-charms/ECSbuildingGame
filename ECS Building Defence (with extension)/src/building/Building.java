package building;

import bugs.Bug;
import bugs.ConcurrentModificationBug;
import bugs.NoneTerminationBug;
import bugs.NullPointerBug;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Building {

  protected int constructionPoints;
  protected int topFloor;
  ArrayList<Bug> bugs = new ArrayList<>();

  public Building(int topFloor, int constructionPoints) {
    this.topFloor = topFloor;
    this.constructionPoints = constructionPoints;
  }

  public int getTopFloor() {
    return topFloor;
  }

  public int getConstructionPoints() {
    return constructionPoints;
  }

  public Bug[] getAllBugs() {
    ArrayList<Bug> bugsTemporary = new ArrayList<>();
    /*
    the getAllBugs method will create
    a new arraylist every time it is called,
    add all the bugs that are in the building and
    don't have 0 Hp and then sort them using the
    comparable interface (collections.sort)
     */
    for (Bug bug : bugs) {
      if (bug.getCurrentFloor() != -1 && bug.getCurrentHp() > 0) {
        bugsTemporary.add(bug);
      }
    }

    Collections.sort(bugsTemporary);
    return bugsTemporary.toArray(new Bug[0]);
    //it will then return the arraylist as an array[]
  }

  public int addBug(Bug bug) {
    if (bugs.contains(bug)) {
      return -1;
    } else {
      bugs.add(bug);
      return bugs.size();
    }
  }

  public void removeBug(Bug bug) {
    bugs.remove(bug);
  }

  public void bugsMove() {
    /*
    bugsMove uses an iterator
    to move all the bugs by one step
    in order to make sure that when a bug
    reaches the top floor, it can be removed from
    the array and not cause a ConcurrentModificationException
     */
    for (Iterator<Bug> it = bugs.iterator(); it.hasNext(); ) {
      Bug bug = it.next();
      bug.move();
      if (bug.getCurrentFloor() == getTopFloor() && bug.getCurrentHp() != 0) {
        if (bug instanceof ConcurrentModificationBug) {
          System.out.println(
              bug.getName() + " has reached the top of the building and damaged it by 2!");
          constructionPoints -= 2;
        } else if (bug instanceof NoneTerminationBug) {
          System.out.println(
              bug.getName() + " has reached the top of the building and damaged it by 4!");
          constructionPoints -= 4;
        } else if (bug instanceof NullPointerBug) {
          System.out.println(
              bug.getName() + " has reached the top of the building and damaged it by 1!");
          constructionPoints -= 1;
        }
        it.remove();
      } else if (bug.getCurrentFloor() == 0 && bug.getCurrentSteps() == (bug.getBaseSteps() - 1)) {
        System.out.println(bug.getName() + " was spawned!");
      }
    }

    if (constructionPoints <= 0) {
      System.out.println("GAME OVER");
    }
  }

  public ArrayList<Bug> getBugs() {
    //method implemented to save the list of bugs currently/about to attack the building!
    return bugs;
  }
}