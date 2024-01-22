package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.Utilities.SpriteSheet;

public class Player extends GameEntities{
    float speed;
    SpriteSheet walk_down, walk_up,walk_left,walk_right;
    SpriteSheet sheet;
    Vector2 direction,facing_direction;
    boolean shooting=false;
    float shoot_time=0;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public SpriteSheet getWalk_down() {
        return walk_down;
    }

    public void setWalk_down(SpriteSheet walk_down) {
        this.walk_down = walk_down;
    }

    public SpriteSheet getWalk_up() {
        return walk_up;
    }

    public void setWalk_up(SpriteSheet walk_up) {
        this.walk_up = walk_up;
    }

    public SpriteSheet getWalk_left() {
        return walk_left;
    }

    public void setWalk_left(SpriteSheet walk_left) {
        this.walk_left = walk_left;
    }

    public SpriteSheet getWalk_right() {
        return walk_right;
    }

    public void setWalk_right(SpriteSheet walk_right) {
        this.walk_right = walk_right;
    }

    public SpriteSheet getSheet() {
        return sheet;
    }

    public void setSheet(SpriteSheet sheet) {
        this.sheet = sheet;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getFacing_direction() {
        return facing_direction;
    }

    public void setFacing_direction(Vector2 facing_direction) {
        this.facing_direction = facing_direction;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public float getShoot_time() {
        return shoot_time;
    }

    public void setShoot_time(float shoot_time) {
        this.shoot_time = shoot_time;
    }

    public Player(Vector2 position, Texture texture) {
        super(position, null);
        walk_down=new SpriteSheet(new Texture("player_walk.png"),4,4);
        walk_right=new SpriteSheet(new Texture("player_walk.png"),4,4);
        walk_up=new SpriteSheet(new Texture("player_walk.png"),4,4);
        walk_left=new SpriteSheet(new Texture("player_walk.png"),4,4);


        walk_down.setPlay(0, 3, 0.1f, true);
        walk_right.setPlay(4, 7, 0.1f, true);
        walk_up.setPlay(8, 11, 0.1f, true);
        walk_left.setPlay(12, 15, 0.1f, true);

        setSheet(walk_up);
        setSpeed(2);
        setDirection(new Vector2());
        setFacing_direction(new Vector2());
    }
    //Players movement when arrow keys are released
    public void Key_Release(int keystroke){
        switch(keystroke) {
            case Input.Keys.LEFT:
                if (direction.x == -1) {
                    direction.x = 0 ;
                }
                break;
            case Input.Keys.RIGHT:
                if (direction.x == 1) {
                    direction.x = 0;
                }
                break;
            case Input.Keys.UP:
                if (direction.y == 1) {
                    direction.y = 0;
                }
                break;
            case Input.Keys.DOWN:
                if (direction.y == -1) {
                    direction.y = 0;
                }
                break;
            case Input.Keys.SPACE:
                break;
        }
    }
    //Players movement when arrow keys are pressed
    public void Key_Pressed(int keystroke){
        switch(keystroke) {
            case Input.Keys.LEFT:
                direction = new Vector2(-1,0);
                sheet = walk_left;
                facing_direction = new Vector2(direction.x,direction.y);
                break;
            case Input.Keys.RIGHT:
                direction = new Vector2(1,0);
                sheet = walk_right;
                facing_direction = new Vector2(direction.x,direction.y);
                break;
            case Input.Keys.UP:
                direction = new Vector2(0,1);
                sheet = walk_up;
                facing_direction = new Vector2(direction.x,direction.y);
                break;
            case Input.Keys.DOWN:
                direction = new Vector2(0,-1);
                sheet = walk_down;
                facing_direction = new Vector2(direction.x,direction.y);
                break;
            case Input.Keys.SPACE:
                shoot_time = 0;
                shooting = true;
                break;
        }
    }
    public void update(Maps maps){
        Vector2 velocity=new Vector2(direction.x*speed,direction.y*speed);
        Vector2 previous_position=new Vector2(position.x,position.y);

        position=position.add(velocity);
        if(velocity.x!=0 || velocity.y!=0){
            sheet.play();
        }

    }


}
