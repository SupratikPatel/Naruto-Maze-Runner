package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Traps extends GameEntities {
    public Traps(Vector2 position,Texture texture) {
        super(position, new Texture("trap.png"));
    }
}
