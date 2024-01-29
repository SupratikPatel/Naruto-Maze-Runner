package de.tum.cit.ase.maze.Utilities;

/**
 * This class serves as a singleton manager for various utility classes in the game, such as the SoundManager.
 * It ensures that only one instance of each utility class is created and provides a global point of access to them.
 */
public class Manager {

    /**
     * The SoundManager instance used for managing sound effects and music.
     */
    public SoundManager soundManager;

    /**
     * The private constructor for the Manager class.
     * It initializes the SoundManager.
     */
    private Manager() {
        soundManager = new SoundManager();
    }

    /**
     * The static instance of the Manager class.
     */
    private static Manager instance = null;

    /**
     * Provides the global point of access to the Manager instance.
     * If the instance does not exist yet, it is created.
     *
     * @return The single instance of the Manager class.
     */
    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager();

        return instance;
    }
}