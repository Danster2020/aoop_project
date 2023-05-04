import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Game game;
    Clip clip;
    String soundEffect_Walk, box_Moved, marked_box_placed, bg_music;
    //URL soundURL[] = new URL[10];

    Sound(Game g) {
        this.game = g;
        soundEffect_Walk = "../assets/walk.wav";
        box_Moved = "../assets/box_moved.wav";
        marked_box_placed = "../assets/marked_box_placed.wav";
        bg_music = "../assets/Pizza_time.mid.wav";
    }

    public Sound() {
        //soundURL[0] = getClass().getResource("walk_sound.mp3");
        System.out.println("helo");
    }

    public void setFile(String soundName) {
        try {
            File file = new File(soundName);
            System.out.println(file);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {

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
    
    public void playAudio(String soundName) {
        setFile(soundName);
        play();
        loop();       
    }

    public void stopAudio() {
        stop();
    }

    public void playSE(String soundName) {
        setFile(soundName);
        play();
    }
}
