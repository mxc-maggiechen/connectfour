import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;
public class BackEnd {
  //establish variables
    static Scanner sc = new Scanner(System.in);
    int WIDTH = 7, HEIGHT = 6;
    int tokens = 42;
    int columnOrder [] = {3,2,4,1,5,0,6};
    int dx [] = {0,1,1,-1};
    int dy [] = {1,1,0,1};
    GameFiles game;

    public BackEnd() throws Exception{
        game = new GameFiles(true);
    }

    //method that decides who is the current player and which AI to call during AI mode
    public void recordBoard(int input){//playerOrder =0 if PVP, 1 if player goes first, 2 if player goes 2nd
        if(game.gameMode==0){//code for PVP mode
            playMove(input);
        }else{//code for any AI mode
            if((game.board.getMoves()%2)+1 == game.playerOrder){
                playMove(input);
            }else{
                switch(game.gameMode){//selects the correct AI to a play a move against the player
                    case 1:
                        game.board.play(easyAI(game));
                        break;
                    case 2:
                        game.board.play(mediumAI(game));
                        break;
                    case 3:
                        game.board.play(hardAI(game));
                        break;
                }
            }
        }
  }


    //method used when front end or AI requests to confirm a move on the current game board
    public void playMove(int input){
        System.out.println("playing move");
        if(game.board.canPlay(input)){
            System.out.println("can play");
            game.board.play(input);
            //dk if this is the right place
        }
    }

    //code for the easy AI, method will return the column the AI wants to place its checker in
    //easy AI always picks a random column to play its move
    public int easyAI(GameFiles game){
        int randomIndex;
        while(true){
            randomIndex = (int)(Math.random()*game.board.WIDTH);
            System.out.println(randomIndex);
            if(game.board.canPlay(randomIndex)){
                return randomIndex;
            }
        }
    }

    //code for the medium AI, method will return the column the AI wants to place its checker in
    public static int mediumAI(GameFiles game){
      
        int copyOfBoard[][] = new int[7][6]; //Creates copy of board
        int copyOfHeight[] = new int[7]; //Creates copy of the height array

        //copying over the heights
        for(int i=0;i<7;i++){
            copyOfHeight[i]=game.board.height[i];
        }
        //copying over the board
        for(int x=0;x<7;x++){
            for(int y=0;y<6;y++){
                copyOfBoard[x][y]=game.board.getBoard()[x][y];
            }
        }

        //getting move from board
        int move = game.board.getMoves();

        //all the different combinations of discs 
        int twoInARow2[] = {((move%2)+1),((move%2)+1)};

        int twoInARow4[] = {0,((move%2)+1), 0,((move%2)+1)};
        int twoInARow41[] = {((move%2)+1),0,0,((move%2)+1)};
        int twoInARow42[] = {((move%2)+1),0,((move%2)+1),0};

        int threeInARow3[] = {((move%2)+1),((move%2)+1),((move%2)+1)};
        int oppThreeInARow3[] = {(((move+1)%2)+1),(((move+1)%2)+1),(((move+1)%2)+1)};

        int threeInARow4[] = {((move%2)+1),0,((move%2)+1),((move%2)+1)};
        int threeInARow41[] = {((move%2)+1),((move%2)+1),0,((move%2)+1)};
        int oppThreeInARow4[] = {(((move+1)%2)+1),0,(((move+1)%2)+1),(((move+1)%2)+1)};
        int oppThreeInARow41[] = {(((move+1)%2)+1),(((move+1)%2)+1),0,(((move+1)%2)+1)};

        int fourInARow[] = {((move%2)+1),((move%2)+1),((move%2)+1),((move%2)+1)};
        int oppFourInARow[] = {(((move+1)%2)+1),(((move+1)%2)+1),(((move+1)%2)+1),(((move+1)%2)+1)};

        //variables
        int high=0;
        int highestScorePlace=0;
        int columnScore;
        int scores[] = new int[7];
        int combinations2[] = new int[2];
        int combinations3[] = new int[3];
        int combinations4[] = new int[4];
        int changesInX[] = {-1, -1,  0,  1, 1,  1, 0, -1}; //all the differnt directions the word can go in for x, 8 in total
        int changesInY[] = { 0, -1, -1, -1, 0,  1, 1,  1}; //all the differnt directions the word can go in for y, 8 in total
        int y=0;
        int oy=0;

        //for loop for placing a discs in every possible column to calculate score
        for(int i=0;i<7;i++){
            columnScore=0;
            if(copyOfHeight[i]<6){
                y=0;
                for (; y < 6; y++) {
                //if current cell is empty and either we are at the bottom row or the cell one unit down is not empty then place a token here 
                    if (copyOfBoard[i][y] == 0 && (y == 5 || copyOfBoard[i][y+1] != 0)) break;
                }
                if(y==7||y==6){
                    y=0;
                }
                //place a disc in i column
                copyOfBoard[i][y] = (move%2)+1;
                copyOfHeight[i]++;

                //if centre column, 
                if(i==3){
                    columnScore+=10;
                }

                for(int z=0;z<7;z++){
                    //copyOfHeight[z]=game.board.height[z];
                    if(copyOfHeight[z]<6){


                        oy = 0;
                        for (; oy < 6; oy++) {
                        /*if current cell is empty and either we are at the bottom row or the cell one unit down is not empty
                        then place a token here
                        */
                            if (copyOfBoard[z][oy] == 0 && (oy == 5 || copyOfBoard[z][oy+1] != 0)) break;
                        }
                        if(oy==7||oy==6){
                            oy=0;
                        }
                        copyOfBoard[z][oy] = ((move+1)%2)+1;
                        System.out.println("added second disc at " + z + " column");
                        for (int n = 0; n < 6; n++) {
                            for (int m = 0; m < 7; m++) {
                                System.out.print(copyOfBoard[m][n]);
                            }
                            System.out.println();
                        }
        
                        for (int y2 = 0; y2 < 6; y2++) {
                            for (int x2 = 0; x2 < 7; x2++) {
                                if (copyOfBoard[x2][y2] == (((move+1)%2)+1)) {
                                    for (int j = 0; j < 8; j++) {

                                        int ny3 = y2 + changesInY[j] * 2;
                                        int nx3 = x2 + changesInX[j] * 2;
                                        if (ny3 < 0 || nx3 < 0 || ny3 >= 6 || nx3 >= 7) continue;
                                        for (int k = 0; k < 3; k++) {
                                            combinations3[k]=copyOfBoard[x2 + (changesInX[j] * k)][y2 + (changesInY[j] * k)];
                                        }
                                        if(Arrays.equals(combinations3, oppThreeInARow3)){
                                            columnScore-=6;
                                        }

                                        int ny4 = y2 + changesInY[j] * 3;
                                        int nx4 = x2 + changesInX[j] * 3;
                                        if (ny4 < 0 || nx4 < 0 || ny4 >= 6 || nx4 >= 7) continue;

                                        for (int k = 0; k < 4; k++) {
                                            combinations4[k]=copyOfBoard[x2 + (changesInX[j] * k)][y2 + (changesInY[j] * k)];
                                        }

                                        if(Arrays.equals(combinations4, oppThreeInARow4) || Arrays.equals(combinations4, oppThreeInARow41) ){
                                            columnScore-=5;
                                        }

                                        if(Arrays.equals(combinations4, oppFourInARow)){
                                            columnScore-=9000;
                                        }

                                    }

                                }
                            }
                        }
                        copyOfBoard[z][oy]=0;
                    }
                }

                for (int y1 = 0; y1 < 6; y1++) {
                    for (int x1 = 0; x1 < 7; x1++) {
                        if (copyOfBoard[x1][y1] == ((move%2)+1)) {

                            for (int j = 0; j < 8; j++) {

                                int ny2 = y1 + changesInY[j];
                                int nx2 = x1 + changesInX[j];
                                if (ny2 < 0 || nx2 < 0 || ny2 >= 6 || nx2 >= 7) continue;
                                for (int k = 0; k < 2; k++) {
                                    combinations2[k]=copyOfBoard[x1 + (changesInX[j] * k)][y1 + (changesInY[j] * k)];
                                }
                                if(Arrays.equals(combinations2, twoInARow2)){
                                    columnScore+=1;
                                }

                                int ny3 = y1 + changesInY[j] * 2;
                                int nx3 = x1 + changesInX[j] * 2;
                                if (ny3 < 0 || nx3 < 0 || ny3 >= 6 || nx3 >= 7) continue;
                                for (int k = 0; k < 3; k++) {
                                    combinations3[k]=copyOfBoard[x1 + (changesInX[j] * k)][y1 + (changesInY[j] * k)];
                                }
                                if(Arrays.equals(combinations3, threeInARow3)){
                                    columnScore+=5;
                                }

                                int ny4 = y1 + changesInY[j] * 3;
                                int nx4 = x1 + changesInX[j] * 3;
                                if (ny4 < 0 || nx4 < 0 || ny4 >= 6 || nx4 >= 7) continue;
                                for (int k = 0; k < 4; k++) {
                                    combinations4[k]=copyOfBoard[x1 + (changesInX[j] * k)][y1 + (changesInY[j] * k)];
                                }

                                if(Arrays.equals(combinations4, twoInARow4) || Arrays.equals(combinations4, twoInARow41) || Arrays.equals(combinations4, twoInARow42)){
                                    columnScore+=4;
                                }
                                if(Arrays.equals(combinations4, threeInARow4) || Arrays.equals(combinations4, threeInARow41) ){
                                    columnScore+=12;
                                }
                                if(Arrays.equals(combinations4, fourInARow)){
                                    columnScore+=10000;
                                }
                            }
                            //game.board.height[i]--;

                        }
                    }
                }
                System.out.println("score" + columnScore);
                copyOfHeight[i]--;
                copyOfBoard[i][y]=0;
            }
            else{
                columnScore-=99999;
            }

            System.out.println("score" + columnScore);
            scores[i]=columnScore;
        }

        high=-99999;

        for(int z=0;z<7;z++){
            if(scores[z]>high){
                high=scores[z];
                highestScorePlace=z;
            }
        }
        System.out.println("best coloumn is " + highestScorePlace);
        return highestScorePlace;
    }

    /*
    Given a board and a player, this method returns the score of the board in terms of that player.
    Checker in the middle column add 4, any row of 2 adds 2 and any row of 3 adds 5. If it is the             players move and they can win on their move, return 100.
    */
    public int checkScore (Board P, int player) {
        if (player == P.getMoves()%2 + 1) {
            for (int x = 0; x < WIDTH; x++) {
                if (P.canPlay(x) && P.isWinningMove(x)) return 1000;
            }
        }
        int board[][] = P.getBoard();
        int score = 0;
        for (int y = 0; y < HEIGHT; y++) {
            if (board[3][y] == player) score +=4 ;
        }
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int token1 = 0, token2 = 0;
                for (int i = 0; i < dy.length; i++) {
                    int ny = y + dy[i] * 3, nx = x + dx[i] * 3;
                    if (ny < 0 || nx < 0 || ny >= 6 || nx >= 7) continue;
                    for (int j = 0; j < 4; j++) {
                        if (board[x + dx[i] * j][y + dy[i] * j] == player) token1++;
                        else if (board[x + dx[i] * j][y + dy[i] * j] != 0) token2++;
                    }
                    if (token2 == 0) {
                        if (token1 == 2) score += 2;
                        else if (token1 == 3) score += 5;
                    }
                }

            }
        }
        return score;
    }
  /*
    minimax algorithm searches all possible gameboards upto 8 moves ahead given a board position and   
    calculates the best possible score based on that. 
  */
    public int minimax ( Board P, int alpha, int beta, int depth) {
        if (P.checkWin()) {
            return -1000 * depth;
        }
        if (depth == 0) {
            int player = P.getMoves()%2+1;
            int curScore = checkScore(P, player);
            int oppScore = checkScore(P,player%2+1);
            return curScore-oppScore;
        }

        else if (P.getMoves() == tokens) {
            return 0;
        }
        for (int i = 0; i < WIDTH; i++) {
            if (P.canPlay(columnOrder[i])) {
                Board P2 = new Board(P.getHash());
                P2.play(columnOrder[i]);
                int score = -minimax(P2, -beta, -alpha, depth-1);
                if (score >= beta) return score;
                if (score > alpha) alpha = score;
            }
        }
        return alpha;
    }

  /* 
    Hard AI returns the best possible column by using memoization and the minimax algorithm to search        gameboards 8 moves ahead. Given a gameboard, Hard AI first searches 'solutions.txt' file to find 
    the best possible move from the current gameboard. If 'solutions.txt' doesn't contain the answer,
    HardAI uses minimax algorithm to find the best move. 
  */
    public int hardAI (GameFiles game) {
        Board P = game.board;
        int maxScore = -Integer.MAX_VALUE, AIMove = 0;
        for (int x = 0; x < 7; x++) {
            if (!P.canPlay(x)) continue;
            if ( P.isWinningMove(x)) {
                AIMove = x;
                break;
            }
            Board P2 = new Board(P.getHash());
            P2.play(x);
            if ( game.dp.containsKey(P2.getHash()) && game.dp.get(P2.getHash()) < 0) {
                AIMove = x;
                break;
            }
            int score = -minimax(P2,-Integer.MAX_VALUE, Integer.MAX_VALUE,8);
            if (score > maxScore) {
                AIMove = x;
                maxScore = score;
            }
        }
        return AIMove;
    }

}

