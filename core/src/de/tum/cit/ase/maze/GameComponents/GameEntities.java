package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.Utilities.BoundingBox;

/**
 * This is the superclass for all entities in the game.
 * It contains common properties and methods that all game entities share.
 */
public class GameEntities {
    protected Vector2 position; // The position of the entity in the game world
    Texture texture; // The texture of the entity
    public boolean destroyFlAG = false; // Flag to indicate whether the entity should be destroyed

    /**
     * Constructs a new game entity with the specified position and texture.
     *
     * @param position The position of the entity in the game world.
     * @param texture The texture of the entity.
     */
    public GameEntities(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;
    }

    /**
     * Updates the state of the entity. This method should be overridden by subclasses
     * to provide entity-specific update behavior.
     */
    public void update() {
    }

    /**
     * Returns the position of the entity in the game world.
     *
     * @return The position of the entity.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity in the game world.
     *
     * @param position The new position of the entity.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Returns a bounding box that encloses the entity. The bounding box is used for
     * collision detection and other similar operations.
     *
     * @return The bounding box of the entity.
     */
    public BoundingBox box() {
        return new BoundingBox(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Draws the entity on the specified batch.
     *
     * @param batch The batch on which to draw the entity.
     */
    public void draw(Batch batch) {
        batch.begin();
        batch.draw(texture, position.x, position.y, texture.getWidth(), texture.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.end();
    }
}
