import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.DecimalFormat;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

  //constants for panel layout
	static final int SCREEN_WIDTH = 1500/2;
	static final int SCREEN_HEIGHT= 1000/2;
	static final int BOARD_WIDTH = 700/2;
	static final int BOARD_HEIGHT = 600/2;
	static final int UNIT_SIZE = 100/2;

  static int seconds = 0;
	int minute;
  String ddSecond, ddMinute;
  DecimalFormat dFormat = new DecimalFormat("00");

  //cartesian plane for the board
	static final int BOARD_X = 600/2;
	static final int BOARD_Y = 300/2;
	static final int GAME_UNITS = (BOARD_WIDTH*BOARD_HEIGHT)/UNIT_SIZE;
	int userColumn=0;
	static final Color lightBlue = new Color(177, 225, 242);

  //variables to keep track of various states of the game
	int gameState =0; //gameState =0 for decide move, =1 for place checker, 2 game end
	int player=1; // tracks current player
	int checkerColumn; // tracks column of the latest confirmed checker
	int checkerRow; //tracks row of the latest confirmed checker
	boolean keyPressed = false;
	private ArrayList<Integer> columnList = new ArrayList<Integer>(); //keeps track of the columns of all the confirmed checkers in the order in which they were placed
	private ArrayList<Integer> rowList = new ArrayList<Integer>(); //keeps track of the rows of all the confirmed checkers in the order in which they were placed
	BackEnd be;
  PlaySound ps = new PlaySound();
  //variables for JButtons, JLabels, and Fonts
	JButton returnMenuButton = new JButton("Return to Menu");
	JButton clearBoardButton = new JButton("Clear Board");
	JButton saveGameButton = new JButton("Save Game");
	JButton saveGameAsButton = new JButton("Save Game As");
	JLabel instrLabel1 = new JLabel("use left/right key");
	JLabel instrLabel2 = new JLabel("to move checker");
	JLabel instrLabel3 = new JLabel("use down key");
	JLabel instrLabel4 = new JLabel("to confirm placement");

  //All labels used
  JLabel SoundLabel = new JLabel();
  JButton SoundButton = new JButton(".");
  JLabel MuteLabel = new JLabel();
  JButton MuteButton = new JButton("");
	JLabel counterLabel = new JLabel("Time Elapsed: 00:00");

  //All fonts used
	Font buttonFont = new Font("Monospaced", Font.BOLD, 15);
	Font instrFont = new Font("Monospaced", Font.BOLD, 15);

  
	GamePanel() throws Exception{
    //set layout and dimensions of panel
		be = new BackEnd();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.setLayout(null);

    //set labels and buttons to appropriate fonts
		instrLabel1.setFont(instrFont);
		instrLabel2.setFont(instrFont);
		instrLabel3.setFont(instrFont);
		instrLabel4.setFont(instrFont);
    counterLabel.setFont(instrFont);
		saveGameButton.setFont(buttonFont);
		clearBoardButton.setFont(buttonFont);
		returnMenuButton.setFont(buttonFont);

    MuteLabel.setIcon(new ImageIcon("Mute.jpg"));
        MuteButton.add(MuteLabel);
    MuteButton.setBackground(Color.WHITE);
    MuteButton.setVisible(false);
    SoundLabel.setIcon(new ImageIcon("Sound.jpg"));
        SoundButton.add(SoundLabel);
    SoundButton.setBackground(Color.WHITE);

    //place labels and buttons in the appropriate place
		instrLabel1.setBounds(UNIT_SIZE, UNIT_SIZE*2+UNIT_SIZE/2, UNIT_SIZE*4, UNIT_SIZE);
		instrLabel2.setBounds(UNIT_SIZE, UNIT_SIZE*3, UNIT_SIZE*4, UNIT_SIZE);
		instrLabel3.setBounds(UNIT_SIZE, UNIT_SIZE*3+UNIT_SIZE/2, UNIT_SIZE*4, UNIT_SIZE);
		instrLabel4.setBounds(UNIT_SIZE, UNIT_SIZE*4, UNIT_SIZE*4, UNIT_SIZE);
		saveGameButton.setBounds(UNIT_SIZE, UNIT_SIZE*5, UNIT_SIZE*4, UNIT_SIZE);
		clearBoardButton.setBounds(UNIT_SIZE, UNIT_SIZE*6, UNIT_SIZE*4, UNIT_SIZE);
		returnMenuButton.setBounds(UNIT_SIZE, UNIT_SIZE*7, UNIT_SIZE*4, UNIT_SIZE);
    MuteButton.setBounds(UNIT_SIZE*13+10, UNIT_SIZE/2, UNIT_SIZE+25, UNIT_SIZE);
    SoundButton.setBounds(UNIT_SIZE*13+10, UNIT_SIZE/2, UNIT_SIZE+25, UNIT_SIZE);
    counterLabel.setBounds(UNIT_SIZE, UNIT_SIZE*8, UNIT_SIZE*4, UNIT_SIZE);

    //add labels and buttons
		this.add(instrLabel1);
		this.add(instrLabel2);
		this.add(instrLabel3);
		this.add(instrLabel4);
		this.add(saveGameButton);
		this.add(clearBoardButton);
		this.add(returnMenuButton);
    this.add(MuteButton);
    this.add(SoundButton);
    this.add(counterLabel);

	}

  	Timer timer;
	public void timer(){

		timer = new Timer(1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				seconds++;
				ddSecond=dFormat.format(seconds);
				ddMinute=dFormat.format(minute);
				counterLabel.setText("Time Elapsed: " + ddMinute + ":" + ddSecond);
				if(seconds==60){
					seconds=0;
					minute++;
					counterLabel.setText("Time Elapsed: " + ddMinute + ":" + ddSecond);
				}
			}
		});
	}

	public void stopTimer(){
		timer.stop();
	}

	public static int getSecond(){
		return seconds;
	}


  //paint function used to display graphics
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Font title = new Font("Monospaced", Font.BOLD, 50);
		Font subtitle = new Font("Monospaced", Font.PLAIN, 25);

    //draw the title "Connect 4" on screen
		g2d.setFont(title);
		g2d.drawString("Connect 4", UNIT_SIZE*5, UNIT_SIZE*2);
		g2d.setFont(subtitle);

    //draw the background for the game board
		g.setColor(lightBlue);
		g.fillRect(BOARD_X,BOARD_Y,BOARD_WIDTH,BOARD_HEIGHT);
		g.setColor(Color.black);

		//draw a grid on the game board
		for(int i=BOARD_X/UNIT_SIZE;i<=BOARD_WIDTH/UNIT_SIZE + BOARD_X/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, BOARD_Y, i*UNIT_SIZE, BOARD_Y+BOARD_HEIGHT);//vertical lines
		}

		for(int i=BOARD_Y/UNIT_SIZE;i<=BOARD_WIDTH/UNIT_SIZE + BOARD_Y/UNIT_SIZE;i++) {
			g.drawLine(BOARD_X, i*UNIT_SIZE, BOARD_X+BOARD_WIDTH,i*UNIT_SIZE);//horizontal lines
		}

		//draw empty circles on the game board
		for(int i=BOARD_X/UNIT_SIZE;i<BOARD_WIDTH/UNIT_SIZE+BOARD_X/UNIT_SIZE;i++) {
			for(int j=BOARD_Y/UNIT_SIZE;j<BOARD_HEIGHT/UNIT_SIZE+BOARD_Y/UNIT_SIZE;j++) {
				g.setColor(Color.white);
				g.fillOval(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); // colour the empty circles white
				g.setColor(Color.black);
				g.drawOval(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); // draw outlines for the empty circles

			}

		}

    //displays the floating checker on top of the board for players to move around when placing their checkers
		if(gameState!=2){
			switch(player){//choose the appropriate colour to display
				case 1:
					g.setColor(Color.red);
					break;
				case 2:
					g.setColor(Color.yellow);
					break;
			}
			g.fillOval((BOARD_X/UNIT_SIZE+userColumn)*UNIT_SIZE, BOARD_Y/UNIT_SIZE*UNIT_SIZE - UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); //fill in the colours for the checker
			g.setColor(Color.black);
			g.drawOval((BOARD_X/UNIT_SIZE+userColumn)*UNIT_SIZE, BOARD_Y/UNIT_SIZE*UNIT_SIZE - UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); //draw the outline for the checker
		}


    //displays the previously placed checkers on the game board
		int r;
		int c;
		for(int i=0;i<rowList.size();i++){
			r = rowList.get(i);
			c = columnList.get(i);
			switch((i%2)+1){//choose the correct colour for drawing a previously placed checker
				case 1:
					g.setColor(Color.red);
					break;
				case 2:
					g.setColor(Color.yellow);
					break;
			}
			g.fillOval((BOARD_X/UNIT_SIZE+c)*UNIT_SIZE, (6-BOARD_Y/UNIT_SIZE+r)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
		} //colour in the placed checker

    //draw in the latest confirmed checker
		if(gameState!=0){
			switch(((rowList.size()-1)%2)+1){//chooses the correct colour for the latest confirmed checker
				case 1:
					g.setColor(Color.red);
					break;
				case 2:
					g.setColor(Color.yellow);
					break;
			}
			g.fillOval((BOARD_X/UNIT_SIZE+columnList.get(columnList.size()-1))*UNIT_SIZE, (6-BOARD_Y/UNIT_SIZE+rowList.get(rowList.size()-1))*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);//fill in the colours for the checker

      //draw in the end game statement texts when the game ends
			g2d.setColor(Color.black);
			if(gameState==2){//check if the game ended yet
				if(be.game.board.getMoves()==42){//text for when game is a draw
					g2d.drawString("DRAW!", UNIT_SIZE*7,UNIT_SIZE*10-10 );
				}else{
					if (be.game.gameMode != 0) {
						if (be.game.playerOrder == 2 && be.game.board.getMoves() % 2 == 1) g2d.drawString("AI Won!", UNIT_SIZE * 7, UNIT_SIZE * 10 - 10);						if (be.game.playerOrder == 2 && be.game.board.getMoves() % 2 == 1) g2d.drawString("AI Won!", UNIT_SIZE * 7, UNIT_SIZE * 10 - 10);
						else if (be.game.playerOrder == 1 && be.game.board.getMoves() % 2 == 0) g2d.drawString("AI Won!", UNIT_SIZE * 7, UNIT_SIZE * 10 - 10);
						else g2d.drawString("You Won!", UNIT_SIZE * 7, UNIT_SIZE * 10 - 10);
					}
					else {
						if (be.game.board.getMoves() % 2 == 1) g2d.drawString("Player 1 Won!", UNIT_SIZE * 7, UNIT_SIZE * 10 - 10);
						else g2d.drawString("Player 2 Won!", UNIT_SIZE * 5, UNIT_SIZE * 10 - 10);
					}
				}
				}
			}else{
				gameState=0;
			}

    //calls for the AI to play a move when applicable
		if(be.game.gameMode!=0 && be.game.playerOrder!=be.game.board.getMoves()%2+1 && gameState!=2){
			updateGameBoard();
		}
	}

  //method used when drawing a previously saved game that has been loaded
	public void loadPrevGame(){
		String hash = be.game.board.getHash();
		int height [] = new int[7];
		for (int i = 0; i < hash.length(); i++) {
			int column = hash.charAt(i) - '1';
			rowList.add(5-height[column]);
			columnList.add(column);
			height[column]++;
		}
		revalidate();
		repaint();
	}

  //method used when sending player input of the game to the back end to update the back end board, which then gets sent to the front end to update the UI
	public void updateGameBoard(){
		be.recordBoard(userColumn);
		columnList.add(be.game.board.checkerColumn);
		rowList.add(5-be.game.board.checkerRow);
		player = be.game.board.getMoves()%2+1;
		if(be.game.board.checkWin() || be.game.board.getMoves()==42){
			gameState=2;
			player--;
		}
		else{
			gameState=1;
		}
		repaint();
		revalidate();
	}

  //method used to clear the board and basically start a new game 
	public void clearBoard() throws Exception{
		BackEnd b = new BackEnd();
		b.game.gameMode = be.game.gameMode;
		b.game.playerOrder = be.game.playerOrder;
		be=b;
		player=1;
		rowList.clear();
		columnList.clear();
		gameState=0;
		repaint();
		revalidate();
	}

  //method used to start a new game
	public void newGame() throws Exception{
		be = new BackEnd();
		rowList.clear();
		columnList.clear();
		gameState=0;
		repaint();
		revalidate();
	}


	@Override
	public void actionPerformed(ActionEvent e){

	}
  //Key adapter class to receive input from keyboard
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			if(gameState!=2){//check if game has ended, keyboard inputs are invalid if game has ended
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if(userColumn!=0){
							userColumn--;
							revalidate();
							repaint();
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(userColumn!=6){
							userColumn++;
							revalidate();
							repaint();
						}
						break;
					case KeyEvent.VK_DOWN:
						if(be.game.board.canPlay(userColumn)){
							updateGameBoard();
						}
            if(be.game.board.canPlay(userColumn) || be.game.board.getMoves()%2+1!=be.game.playerOrder){
							ps.playSE(1);
						}
						break;
				}

			}

		}

	}
}

