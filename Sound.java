import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

        File file = new File("BGMusic.wav");
        File file2 = new File("ClickSound.wav");

    
    
    public void setFile(int i) {

        if(i==0){
            try{
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(ais);
            }catch(Exception e){
    
            }
        }
        else if(i==1){
            try{
                AudioInputStream ais = AudioSystem.getAudioInputStream(file2);
                clip = AudioSystem.getClip();
                clip.open(ais);
            }catch(Exception e){
    
            }
        }
        
    }

    public void play() {

        clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        clip.stop();
    }
}