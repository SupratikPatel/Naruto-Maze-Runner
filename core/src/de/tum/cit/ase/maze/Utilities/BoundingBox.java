package de.tum.cit.ase.maze.Utilities;

/**
 * Represents an axis-aligned bounding box with a position and size.
 * This class is used for collision detection purposes.
 */
public class BoundingBox {
    public float x, y, w, h;

    /**
     * Constructs a new BoundingBox with the specified position and size.
     *
     * @param x The x-coordinate of the bottom-left corner of the bounding box.
     * @param y The y-coordinate of the bottom-left corner of the bounding box.
     * @param w The width of the bounding box.
     * @param h The height of the bounding box.
     */
    public BoundingBox(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Checks if this bounding box collides with another bounding box.
     *
     * @param b The other bounding box to check for collision with.
     * @return true if the bounding boxes collide, false otherwise.
     */
    public boolean collide(BoundingBox b) {
        return b.x < this.x + this.w &&
                b.x + b.w > this.x &&
                b.y < this.y + this.h &&
                b.h + b.y > this.y;
    }
}