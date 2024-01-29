package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {

    public HashMap<String, Sound> sounds = new HashMap<>();

    public SoundManager() {
        sounds.put("menuMusic", Gdx.audio.newSound(Gdx.files.internal("sounds/background.mp3")));
        sounds.put("gameMusic", Gdx.audio.newSound(Gdx.files.internal("sounds/gameMusic.mp3")));
        sounds.put("death", Gdx.audio.newSound(Gdx.files.internal("sounds/death.wav")));
        sounds.put("death2", Gdx.audio.newSound(Gdx.files.internal("sounds/death2.mp3")));
        sounds.put("item", Gdx.audio.newSound(Gdx.files.internal("sounds/item.wav")));
        sounds.put("shoot", Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3")));
        sounds.put("win", Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3")));


    }


    public void play(String sound, float  volume) {
        sounds.get(sound).play(volume);
    }


    public void playMenuMusic() {
        sounds.get("gameMusic").stop();

        sounds.get("menuMusic").stop();
        sounds.get("menuMusic").loop(0.7f);
    }

    public void playGameMusic() {
        sounds.get("menuMusic").stop();

        sounds.get("gameMusic").stop();
        sounds.get("gameMusic").loop(0.2f);
    }

}