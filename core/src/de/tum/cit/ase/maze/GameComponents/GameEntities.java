package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.Utilities.BoundingBox;

public class  GameEntities {
    protected Vector2 position;
    Texture texture;
    public boolean destroyFlAG=false;

    public GameEntities(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;

    }
    public void update(){

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public BoundingBox getRect(){
        return new BoundingBox(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
    public void draw(Batch batch){
        batch.begin();
        batch.draw(texture,position.x,position.y,texture.getWidth(),texture.getHeight(),0,0,texture.getWidth(),texture.getHeight(),false,false);
        batch.end();
    }
}
