package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends GameEntities {
    Vector2 dir;
    float speed = 5;
    public Bullet(Vector2 pos, Vector2 dir) {
        super(pos, new Texture("playerProjectile.png"));
        this.dir = dir;
    }

    @Override
    public void update() {
        super.update();
        Vector2 vel = new Vector2(dir.x * speed,dir.y * speed);
        position = position.add(vel);
}

}