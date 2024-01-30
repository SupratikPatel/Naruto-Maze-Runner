package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a bullet, which is a projectile that the player can shoot in the game.
 * The bullet is used to eliminate enemies or deactivate traps.
 */
public class Bullet extends GameEntities {
    Vector2 dir; // The direction in which the bullet is moving
    float speed = 5; // The speed at which the bullet travels

    /**
     * Constructs a new Bullet with a specified position and direction.
     *
     * @param pos The initial position of the bullet in the game world.
     * @param dir The direction in which the bullet will move.
     */
    public Bullet(Vector2 pos, Vector2 dir) {
        super(pos, new Texture("playerProjectile.png")); // Call to the superclass constructor with position and texture
        this.dir = dir; // Set the direction of the bullet
    }

    /**
     * Updates the bullet's position based on its direction and speed.
     * This method overrides the update method in the GameEntities superclass.
     */
    @Override
    public void update() {
        super.update(); // Call the update method of the superclass
        Vector2 vel = new Vector2(dir.x * speed, dir.y * speed); // Calculate the velocity of the bullet
        position = position.add(vel); // Update the position of the bullet
    }
}