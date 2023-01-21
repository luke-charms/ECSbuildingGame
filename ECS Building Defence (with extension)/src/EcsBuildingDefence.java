import bugs.Bug;
import building.Building;
import java.util.Scanner;
import students.Students;
import students.Team;

public class EcsBuildingDefence {

  private static boolean saveGame = Boolean.FALSE;  //boolean variables to indicate whether the
  private static boolean loadGame = Boolean.FALSE;  //player has chosen to save/load their game
  private static SaveFileReader saveReader; //new class I made to parse and load from a save file
  private static Scanner input = new Scanner(System.in);  //library used to read input from user

  protected int topFloor;
  protected int constructionPoints;
  protected int knowledgePoints;
  protected String filename;

  public EcsBuildingDefence(int topFloor, int constructionPoints, String filename,
      int knowledgePoints) {
    this.topFloor = topFloor;
    this.constructionPoints = constructionPoints;
    this.filename = filename;
    this.knowledgePoints = knowledgePoints;
    main();
  }

  public static void main(String[] args) {
    saveReader = new SaveFileReader("SAVE.txt");
    if (saveReader.fileIsReady()) {
      //checks whether there is anything in the SAVE file
      System.out.println("Would you like to start from the previously saved file?  (Y/N) ");
      String userInput = input.next();
      if (userInput.equals("Y")) {
        //asks user if they want to start the game from the previous SAVE file
        loadGame = Boolean.TRUE;
        String[] parameters = saveReader.loadGame();
        //collects information on the round, student cost, construction points
        System.out.println("Would you like be able to save your game progress? (Y/N) ");
        userInput = input.next();
        if (userInput.equals("Y")) {
          //asks user if they want to save the game at the end
          saveGame = Boolean.TRUE;
        }
        new EcsBuildingDefence(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]),
            parameters[2], Integer.parseInt(parameters[3]));
        //creates a new game with the collected information from SAVE file
      } else {
        System.out.println("Would you like be able to save your game progress? (Y/N) ");
        userInput = input.next();
        if (userInput.equals("Y")) {
          //if the user doesn't want to start from previous SAVE file, they are asked whether they want to save their game progress
          saveGame = Boolean.TRUE;
        }
        new EcsBuildingDefence(4, 20, "easy.txt", 100);
      }   //if they don't want to start from previous SAVE file, a new game is created normally
    } else {
      saveGame = Boolean.TRUE;
      new EcsBuildingDefence(4, 20, "easy.txt", 100);
    }
  }

  public void main() {
    Battle battle = new Battle(new Team(knowledgePoints),
        new Building(topFloor, constructionPoints));
    BugsFileReader reader = new BugsFileReader(filename);
    //separate class I made to parse the configuration file

    if (loadGame == Boolean.TRUE) {
      String[] parameters = saveReader.loadGame();
      //loads all the parts of the saved game into the current game
      battle.team.setNewStudentCost(Integer.parseInt(parameters[0]));
      battle.setRound(Integer.parseInt(parameters[1]));
      for (Students student : saveReader.loadStudents()) {
        battle.team.addStudent(student);
      }
      for (Bug bug : saveReader.loadBugs()) {
        battle.building.addBug(bug);
      }
      for (int i = 0; i <= Math.floorDiv(battle.getRound(), (8 * battle.building.getTopFloor()));
          i++) {
        reader.getLine();
        //moves the reader to the correct line for the new wave of bugs
      }
    }

    if (reader.fileIsReady() == Boolean.FALSE) {
      System.exit(0);
    }
    while (battle.building.getConstructionPoints() > 0) {
      //continues to run until the building's construction points are 0
      try {
        if (reader.fileIsReady() == Boolean.TRUE
            && battle.getRound() % (8 * battle.building.getTopFloor()) == 0) {
          for (Bug bug : reader.getBugsFileLine()) {
            battle.building.addBug(bug);
          }
        } else if (reader.fileIsReady() == Boolean.FALSE && battle.building.getAllBugs().length == 0) {
          System.out.println("YOU WIN!");
          System.exit(0);
        }
        battle.step();
        if (saveGame == Boolean.TRUE) {
          saveReader.update(battle, "SAVE.txt");
          //updates the current state of the battle to the SAVE file
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    System.exit(0);
  }
}