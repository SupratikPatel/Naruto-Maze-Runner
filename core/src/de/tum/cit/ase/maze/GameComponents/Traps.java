package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a trap in a maze runner game.
 * Traps are static game entities that reduce the life of the player upon collision.
 */
public class Traps extends GameEntities {

    /**
     * Constructs a new Trap with a specified initial position.
     * Initializes the trap with a texture representing its appearance in the game.
     *
     * @param position The initial position of the trap in the game world.
     */
    public Traps(Vector2 position) {
        super(position, new Texture("trap.png"));
    }
}