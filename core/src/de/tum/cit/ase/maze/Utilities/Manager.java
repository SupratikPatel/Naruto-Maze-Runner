package de.tum.cit.ase.maze.Utilities;

public class Manager {

    public SoundManager soundManager;

    private Manager() {
        soundManager = new SoundManager();
    }

    private static Manager instance = null;

    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager();

        return instance;
    }
}
