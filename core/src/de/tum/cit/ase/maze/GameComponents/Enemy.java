package de.tum.cit.ase.maze.GameComponents;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an enemy in the game, extending the GameEntities class.
 * An enemy is an entity that the player must avoid or defeat.
 */
public class Enemy extends GameEntities {
    /**
     * Constructs a new Enemy with a specified position and texture.
     *
     * @param position The initial position of the enemy in the game world.
     * @param texture  The texture of the enemy.
     */
    public Enemy(Vector2 position, Texture texture) {
        super(position, texture); // Call to the superclass constructor with position and texture
    }
}