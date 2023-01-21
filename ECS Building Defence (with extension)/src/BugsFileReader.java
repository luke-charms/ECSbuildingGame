import bugs.Bug;
import bugs.ConcurrentModificationBug;
import bugs.NoneTerminationBug;
import bugs.NullPointerBug;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BugsFileReader {

  BufferedReader reader;

  public BugsFileReader(String filename) {
    try {
      this.reader = new BufferedReader(new FileReader(filename));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  public String getLine() {
    //method to return each line of the bugs wave file
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

  public ArrayList<Bug> getBugsFileLine() {
    //method used to generate bugs from text file
    ArrayList<Bug> bugs = new ArrayList<>();
    String[] bugsParts = getLine().split(";");
    //splits each line of bugs text file by ';'
    for (String bugsPart : bugsParts) {
      String[] bugIParts = bugsPart.split(",");
      //iterates through each split part and further splits it by ','
      String bugName = bugIParts[0].substring(0, bugIParts[0].indexOf("("));
      //each individual bug components are saved with specific names
      String bugType = bugIParts[0].substring((bugIParts[0].indexOf("(") + 1));
      String bugLevel = bugIParts[1];
      String bugSteps = bugIParts[2].replace(")", "");
      if (bugType.equals("CMB")) {
        //if statement to check what type of bug it is
        bugs.add(new ConcurrentModificationBug(bugName, Integer.parseInt(bugLevel),
            Integer.parseInt(bugSteps)));
      } else if (bugType.equals("NPB")) {
        bugs.add(
            new NullPointerBug(bugName, Integer.parseInt(bugLevel), Integer.parseInt(bugSteps)));
      } else if (bugType.equals("NTB")) {
        bugs.add(new NoneTerminationBug(bugName, Integer.parseInt(bugLevel),
            Integer.parseInt(bugSteps)));
      }
    }
    return bugs;
  }
}