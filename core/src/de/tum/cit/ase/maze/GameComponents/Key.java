package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Key extends GameEntities {
    public Key(Vector2 position) {
        super(position,new Texture("key.png"));
    }
}