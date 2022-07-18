
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class GameFiles{
  public HashMap<String, Integer> dp = new HashMap<>();
  public static PrintWriter output;
  public static Scanner input;
  Board board;
  
  int gameMode=1;//0 = PVP OR DEFAULT, 1 = easyAI, 2 = medium AI, 3 = hardAI
  int playerOrder =1;//playerOrder =0 if PVP, 1 if player goes first, 2 if player goes 2nd
  private String name; //name of the current game file
  private int move; //the amount of moves that has been played for the game
  private boolean newGame; //checks if the game has been saved before
  
  GameFiles(boolean newG) throws Exception {
    board = new Board("");
    newGame = newG;
    String fileName = "solutions.txt";
    Scanner sc = new Scanner(new FileReader(fileName));
    while (sc.hasNext()) {
      String pos = sc.next();
      int score = Integer.parseInt(sc.next());

      dp.put(pos, score);
    }
  }

  //method used to save the game and output necessary information as a text file
  public void saveGame(String fname) throws Exception{
    /***save file contain following info:
     File name:
     Game mode:
     Player Order:
     Hash:
     Time:
     ***/
    name = fname;
    output = new PrintWriter(new File(name));
    output.println(name);
    output.println(gameMode);
    output.println(playerOrder);
    output.println(board.getHash());
    output.println(GamePanel.getSecond());
    output.close();
  }

  //accessor and modifier methods
  public void setGameMode(int input){
    gameMode = input;
  }

  public int getGameMode(){
    return gameMode;
  }

  public void setName(String input){
    name = input;
  }

  public String getName(){
    return name;
  }

  public void setMove(int input){
    move = input;
  }

  public int getMove(){
    return move;
  }

  public boolean getNewGame(){
    return newGame;
  }

}
