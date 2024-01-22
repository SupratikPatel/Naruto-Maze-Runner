package de.tum.cit.ase.maze.Utilities;

public class Rectangle {
    public float x,y,w,h;

    public Rectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean collide(Rectangle b) {
        Rectangle rectangle = this;
        return b.x < rectangle.x + rectangle.w &&
                b.x + b.w > rectangle.x &&
                b.y < rectangle.y + rectangle.h &&
                b.h + b.y > rectangle.y;
    }
}
