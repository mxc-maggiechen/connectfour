public class PlaySound {

    Sound sound = new Sound();
    long clipTimePosition;

    public void playMusic(int i) {
        
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play(); 
    }
    public void pause(){
        clipTimePosition = sound.clip.getMicrosecondPosition();
        sound.stop();
    }
    public void resume(int i){
        sound.setFile(i);
        sound.clip.setMicrosecondPosition(clipTimePosition);
        sound.play();
        sound.loop();
    }

}