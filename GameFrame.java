import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.util.Scanner;

public class GameFrame extends JFrame implements ActionListener {

  //create objects of all panel classes
    MenuPanel menuPanel = new MenuPanel();
    GamePanel gamePanel = new GamePanel();
    LoadGamePanel loadGamePanel = new LoadGamePanel();
    NewGamePanel newGamePanel = new NewGamePanel();
    SaveGamePanel saveGamePanel = new SaveGamePanel();
    TurnPanel turnPanel = new TurnPanel();
    PlaySound ps = new PlaySound();
  
    GameFrame() throws Exception{

        ps.playMusic(0); //When gameframe is constructed, the music starts playing

      //add action listeners to the buttons present on the panels
        menuPanel.MuteButton.addActionListener(this);
        menuPanel.SoundButton.addActionListener(this);
        menuPanel.newGameButton.addActionListener(this);
        menuPanel.loadGameButton.addActionListener(this);

        gamePanel.MuteButton.addActionListener(this);
        gamePanel.SoundButton.addActionListener(this);
        gamePanel.returnMenuButton.addActionListener(this);
        gamePanel.saveGameButton.addActionListener(this);
        gamePanel.clearBoardButton.addActionListener(this);

        newGamePanel.MuteButton.addActionListener(this);
        newGamePanel.SoundButton.addActionListener(this);
        newGamePanel.backMenuButton.addActionListener(this);
        newGamePanel.hardAIButton.addActionListener(this);
        newGamePanel.easyAIButton.addActionListener(this);
        newGamePanel.mediumAIButton.addActionListener(this);
        newGamePanel.pvpButton.addActionListener(this);

        loadGamePanel.MuteButton.addActionListener(this);
        loadGamePanel.SoundButton.addActionListener(this);
        loadGamePanel.backMenuButton.addActionListener(this);
        loadGamePanel.fileField.addKeyListener(new MyKeyAdapter());

        turnPanel.MuteButton.addActionListener(this);
        turnPanel.SoundButton.addActionListener(this);
        turnPanel.backMenuButton.addActionListener(this);
        turnPanel.AIFirstButton.addActionListener(this);
        turnPanel.playerFirstButton.addActionListener(this);

        saveGamePanel.MuteButton.addActionListener(this);
        saveGamePanel.SoundButton.addActionListener(this);
        saveGamePanel.returnGameButton.addActionListener(this);
        saveGamePanel.enterButton.addActionListener(this);

      //build the game frame
        this.add(menuPanel);
        this.setTitle("Connect 4");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    //detects all interactions with buttons
    public void actionPerformed (ActionEvent event){
        String command = event.getActionCommand();
        if (command.equals("New Game")) {
            System.out.println("creating new game");
            this.remove(menuPanel);
            this.add(newGamePanel);
        }
        else if (command.equals("<--")) {
            if (event.getSource().equals(newGamePanel.backMenuButton)) {
                this.remove(newGamePanel);
                this.add(menuPanel);

            }
            else if (event.getSource().equals(loadGamePanel.backMenuButton)) {
                this.remove(loadGamePanel);
                loadGamePanel.errorPanel.setVisible(false);
                this.add(menuPanel);
            }
            else {
                this.remove(turnPanel);
                this.add(newGamePanel);
            }
        }
        else if (command.equals("Load Game")) {
            this.remove(menuPanel);
            this.add(loadGamePanel);
        }
        else if (command.equals("Hard AI")) {
            gamePanel.be.game.gameMode =3;
            this.remove(newGamePanel);
            this.add(turnPanel);
        }
        else if (command.equals("Medium AI")) {
            gamePanel.be.game.gameMode =2;
            this.remove(newGamePanel);
            this.add(turnPanel);
        }
        else if (command.equals("Easy AI")) {
            gamePanel.be.game.gameMode =1;
            this.remove(newGamePanel);
            this.add(turnPanel);
        }
        else if (command.equals("PvP")) {
            gamePanel.be.game.gameMode = 0;
            gamePanel.be.game.playerOrder =0;
            gamePanel.player=1;
            this.remove(newGamePanel);
            this.add(gamePanel);
            gamePanel.timer();
		        gamePanel.timer.start();
        }
        else if (command.equals("AI First")) {
            gamePanel.player =2;
            gamePanel.be.game.playerOrder =2;
            this.remove(turnPanel);
            gamePanel.repaint();
            gamePanel.revalidate();
            this.add(gamePanel);
            gamePanel.timer();
		        gamePanel.timer.start();
        }
        else if (command.equals("Player First")) {
            gamePanel.player =1;
            gamePanel.be.game.playerOrder =1;
            this.remove(turnPanel);
            gamePanel.repaint();
            gamePanel.revalidate();
            this.add(gamePanel);
            gamePanel.timer();
		        gamePanel.timer.start();
        }

        else if (command.equals("Return to Menu")){
            System.out.println("returning to menu");
            this.remove(gamePanel);
            this.add(menuPanel);
            try {
                gamePanel.newGame();
            }
            catch(Exception ex)
            {
            }
            gamePanel.stopTimer();
            GamePanel.seconds=0;
            gamePanel.counterLabel.setText("Time Elapsed: 00:00");
        } else if (command.equals("Save Game")){
            this.remove(gamePanel);
            this.add(saveGamePanel);
            gamePanel.stopTimer();
        } else if(command.equals("Enter")){
            try{
                gamePanel.be.game.saveGame(saveGamePanel.textField.getText());
            }catch(Exception ex){

            }
            this.remove(saveGamePanel);
            this.add(gamePanel);
            gamePanel.timer.start();
        } else if(command.equals("Return to Game")){
            this.remove(saveGamePanel);
            this.add(gamePanel);
            gamePanel.timer.start();
        }

        else if(command.equals("Clear Board")){
            System.out.println("Clearing board");
            try {
                gamePanel.clearBoard();
            }
            catch(Exception ex)
            {
            }
            this.remove(gamePanel);
            this.add(gamePanel);
        }
        else if(command.equals(".")){
              menuPanel.MuteButton.setVisible(true);
              gamePanel.MuteButton.setVisible(true);
              newGamePanel.MuteButton.setVisible(true);
              loadGamePanel.MuteButton.setVisible(true);
              turnPanel.MuteButton.setVisible(true);
              saveGamePanel.MuteButton.setVisible(true);
              menuPanel.SoundButton.setVisible(false);
              gamePanel.SoundButton.setVisible(false);
              newGamePanel.SoundButton.setVisible(false);
              loadGamePanel.SoundButton.setVisible(false);
              turnPanel.SoundButton.setVisible(false);
              saveGamePanel.SoundButton.setVisible(false);
              ps.pause();
  
          }
          else if(command.equals("")){
              menuPanel.MuteButton.setVisible(false);
              gamePanel.MuteButton.setVisible(false);
              newGamePanel.MuteButton.setVisible(false);
              loadGamePanel.MuteButton.setVisible(false);
              turnPanel.MuteButton.setVisible(false);
              saveGamePanel.MuteButton.setVisible(false);
              menuPanel.SoundButton.setVisible(true);
              gamePanel.SoundButton.setVisible(true);
              newGamePanel.SoundButton.setVisible(true);
              loadGamePanel.SoundButton.setVisible(true);
              turnPanel.SoundButton.setVisible(true);
              saveGamePanel.SoundButton.setVisible(true);
              ps.resume(0);
          }
        repaint();
        revalidate();
    }

    //receive input from the keyboard
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        /*
        Everytime 
        */
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String fileName = loadGamePanel.fileField.getText();
                try {
                    Scanner sc = new Scanner(new FileReader(fileName));
                    String name = sc.next();
                    int gameMode = Integer.parseInt(sc.next());
                    int playerOrder = Integer.parseInt(sc.next());
                    String hash = sc.next();
                  int seconds = Integer.parseInt(sc.next());
                    Board b = new Board(hash);
                    gamePanel.be.game.playerOrder = playerOrder;
                    gamePanel.player = playerOrder;
                    gamePanel.be.game.gameMode = gameMode;
                    gamePanel.be.game.board = b;
                  GamePanel.seconds = seconds;
                    gamePanel.loadPrevGame();
                  gamePanel.timer.start();
                    if (gamePanel.be.game.board.checkWin() || gamePanel.be.game.board.getMoves() == 42) gamePanel.gameState = 2;
                    remove(loadGamePanel);
                    add(gamePanel);

                } catch (Exception exception) {
                    loadGamePanel.errorPanel.setVisible(true);
                }
                if (loadGamePanel.clicks%2 == 0) {
                    loadGamePanel.remove(loadGamePanel.backGround1);
                    loadGamePanel.add(loadGamePanel.backGround2);
                }
                else {
                    loadGamePanel.add(loadGamePanel.backGround1);
                    remove(loadGamePanel.backGround2);
                }
                loadGamePanel.clicks++;
                repaint();
                revalidate();
            }
        }
    }
}
