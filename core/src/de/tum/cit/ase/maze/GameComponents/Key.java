package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a Key in the game, extending the GameEntities class.
 * The Key is an object that the player must find in order to exit the maze.
 */
public class Key extends GameEntities {

    /**
     * Constructs a new Key with a specified position.
     *
     * @param position The initial position of the key in the game world.
     */
    public Key(Vector2 position) {
        super(position, new Texture("key.png"));
    }
}