package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * This class manages the sounds for a game. It loads sounds from files, stores them in a map,
 * and provides methods to play the sounds.
 */
public class SoundManager {

    /**
     * A map from sound names to Sound objects.
     */
    public HashMap<String, Sound> sounds = new HashMap<>();

    /**
     * Constructor for SoundManager. Loads sounds from files and stores them in the sounds map.
     */
    public SoundManager() {
        sounds.put("menuMusic", Gdx.audio.newSound(Gdx.files.internal("sounds/background.mp3")));
        sounds.put("gameMusic", Gdx.audio.newSound(Gdx.files.internal("sounds/gameMusic.mp3")));
        sounds.put("death", Gdx.audio.newSound(Gdx.files.internal("sounds/death.wav")));
        sounds.put("death2", Gdx.audio.newSound(Gdx.files.internal("sounds/death2.mp3")));
        sounds.put("item", Gdx.audio.newSound(Gdx.files.internal("sounds/item.wav")));
        sounds.put("shoot", Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3")));
        sounds.put("win", Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3")));
        sounds.put("collectkey", Gdx.audio.newSound(Gdx.files.internal("sounds/collectkey.mp3")));
    }

    /**
     * Plays the specified sound at the specified volume.
     *
     * @param sound The name of the sound to play.
     * @param volume The volume at which to play the sound.
     */
    public void play(String sound, float  volume) {
        sounds.get(sound).play(volume);
    }

    /**
     * Stops the game music and starts playing the menu music in a loop.
     */
    public void playMenuMusic() {
        sounds.get("gameMusic").stop();
        sounds.get("menuMusic").stop();
        sounds.get("menuMusic").loop(0.7f);
    }

    /**
     * Stops the menu music and starts playing the game music in a loop.
     */
    public void playGameMusic() {
        sounds.get("menuMusic").stop();
        sounds.get("gameMusic").stop();
        sounds.get("gameMusic").loop(0.2f);
    }
    /**
     * Stops the menu music and Game Music
     */
    public void stopMusic(){
        sounds.get("menuMusic").stop();
        sounds.get("gameMusic").stop();
    }
}