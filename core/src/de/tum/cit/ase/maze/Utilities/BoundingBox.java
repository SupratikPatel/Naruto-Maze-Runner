package de.tum.cit.ase.maze.Utilities;

public class BoundingBox {
    public float x,y,w,h;

    public BoundingBox(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean collide(BoundingBox b) {
        BoundingBox boundingBox = this;
        return b.x < boundingBox.x + boundingBox.w &&
                b.x + b.w > boundingBox.x &&
                b.y < boundingBox.y + boundingBox.h &&
                b.h + b.y > boundingBox.y;
    }
}
