package view;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import model.Block;
import model.Event;
import model.GameDataPublisher;
import model.Observer;

public class SoundView implements Observer {
    private GameDataPublisher gameData;
    private Block[][] blockGrid;

    // SOUND
    private GameView gameView;
    private Clip clip;
    private Clip musicClip;
    private String playerWalked;
    private String box_Moved;
    private String markedBoxPlaced;
    private String levelCompleted;
    private String bg_music;

    public SoundView(GameDataPublisher gameData) {
        this.gameData = gameData;
        this.gameView = gameData.game.gameView;

        // SOUND
        playerWalked = "../assets/walk.wav";
        markedBoxPlaced = "../assets/marked_box_placed.wav";
        levelCompleted = "../assets/collect.wav";
        bg_music = "../assets/" + gameView.getSetting("music_track") + ".wav";

        if (gameView.getSetting("easter_egg").equals("true")) {
            box_Moved = "../assets/pipe.wav";
        } else {
            box_Moved = "../assets/box_moved.wav";
        }

    }

    public void printBlocks() {

        System.out.println(gameData.lvlGridToString());
    }

    public void setFile(String soundName) {

        try {
            File file = new File(soundName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setMusicFile(String soundName) {

        try {
            File file = new File(soundName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playSE(String soundName) {
        setFile(soundName);
        clip.start();
    }

    public void playMusic(String musicName, boolean loop) {
        setMusicFile(musicName);

        try {
            musicClip.start();
            if (loop) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playMusic(String musicName) {
        playMusic(musicName, true);
    }

    // default music
    public void playMusic() {
        playMusic(bg_music, true);
    }

    public void stopMusic() {
        if (musicClip != null) {
            musicClip.setFramePosition(0);
            musicClip.stop();
        }

    }

    @Override
    public void update(Event event) {

        switch (event) {
            case PLAYER_MOVED:
                playSE(playerWalked);
                break;
            case BOX_MOVED:
                playSE(box_Moved);
                break;
            case BOX_MOVED_ON_TARGET:
                playSE(markedBoxPlaced);
                break;
            case LVL_LOADED:
                stopMusic();
                playMusic();
                break;
            case LVL_COMPLETE:
                playSE(levelCompleted);
                break;

            default:
                break;
        }

    }

}
