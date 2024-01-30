package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.maze.Utilities.BoundingBox;
import de.tum.cit.ase.maze.Utilities.SpriteSheet;
import de.tum.cit.ase.maze.Utilities.Manager;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
/**
 * Represents the player in a maze runner game.
 * This class handles the player's properties such as movement, animation, shooting, and collision detection.
 */
public class Player extends GameEntities {
    float speed; // The speed at which the player moves.
    SpriteSheet walk_down, walk_up, walk_left, walk_right; // SpriteSheets for player animations in different directions.
    SpriteSheet sheet; // The current SpriteSheet being used for the player's animation.
    Vector2 direction, facing_direction; // The current direction of movement and the direction the player is facing.
    boolean shooting = false; // Flag to determine if the player is currently shooting.
    float shoot_time = 0; // Timer to control the shooting mechanism.
    int bulletCount = 0; // The number of bullets the player has.

    /**
     * Constructs a new Player with a specified initial position.
     * Initializes the player's animation sheets and sets the default direction and speed.
     *
     * @param position The initial position of the player in the game world.
     */
    public Player(Vector2 position) {
        super(position, null);
        walk_down = new SpriteSheet(new Texture("player_walk1.png"),4,4);
        walk_down.setPlay(0, 3, 0.1f, true);

        walk_right = new SpriteSheet(new Texture("player_walk1.png"),4,4);
        walk_right.setPlay(4, 7, 0.1f, true);

        walk_up = new SpriteSheet(new Texture("player_walk1.png"),4,4);
        walk_up.setPlay(8, 11, 0.1f, true);

        walk_left = new SpriteSheet(new Texture("player_walk1.png"),4,4);
        walk_left.setPlay(12, 15, 0.1f, true);


        //Our Own Player Design
//        walk_down = new SpriteSheet(new Texture("player_walk.png"),4,4);
//        walk_down.setPlay(0, 3, 0.1f, true);
//
//        walk_right = new SpriteSheet(new Texture("player_walk.png"),4,4);
//        walk_right.setPlay(8, 11, 0.1f, true);
//
//        walk_up = new SpriteSheet(new Texture("player_walk.png"),4,4);
//        walk_up.setPlay(12, 15, 0.1f, true);
//
//        walk_left = new SpriteSheet(new Texture("player_walk.png"),4,4);
//        walk_left.setPlay(4, 7, 0.1f, true);



        sheet = walk_up;

        speed = 2;

        direction = new Vector2(0,0);
        facing_direction = new Vector2(1,0);
    }
    /**
     * Handles the event when a key is released.
     * Stops the player's movement in the corresponding direction.
     *
     * @param keystroke The keycode of the released key.
     */
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
    /**
     * Handles the event when a key is pressed.
     * Starts the player's movement in the corresponding direction and handles shooting.
     *
     * @param keystroke The keycode of the pressed key.
     */
    //Players movement when arrow keys are pressed
    public void Key_Pressed(int keystroke){
        switch (keystroke) {
            case Input.Keys.LEFT -> {
                direction = new Vector2(-1, 0);
                sheet = walk_left;
                facing_direction = new Vector2(direction.x, direction.y);
            }
            case Input.Keys.RIGHT -> {
                direction = new Vector2(1, 0);
                sheet = walk_right;
                facing_direction = new Vector2(direction.x, direction.y);
            }
            case Input.Keys.UP -> {
                direction = new Vector2(0, 1);
                sheet = walk_up;
                facing_direction = new Vector2(direction.x, direction.y);
            }
            case Input.Keys.DOWN -> {
                direction = new Vector2(0, -1);
                sheet = walk_down;
                facing_direction = new Vector2(direction.x, direction.y);
            }
            case Input.Keys.SPACE -> {
                shoot_time = 0;
                shooting = true;
            }
        }
    }
    /**
     * Updates the player's position and animation based on the current direction of movement.
     * Checks for collisions with walls and ensures the player does not move outside the map boundaries.
     *
     * @param maps The game map containing information about walls and other obstacles.
     */
    public void update(Maps maps){
        Vector2 velocity=new Vector2(direction.x*speed,direction.y*speed);
        Vector2 previous_position=new Vector2(position.x,position.y);

        position=position.add(velocity);
        if(velocity.x!=0 || velocity.y!=0){
            sheet.play();
        }
        for(int row = 0; row < maps.getNum_Of_Rows();row++) {
            for (int col = 0; col < maps.getNum_Of_Column(); col++) {
                Block cell = maps.getCell(row,col);
                if(cell.getBlocksType() == BlockType.WALL && cell.rectangle().collide(this.box())){
                    position = previous_position;
                    return;
                }
            }
        }
        if(position.x < 0){
            position.x = 0;
        }
        if(position.y < 0){
            position.y = 0;
        }
    }
    /**
     * Handles the shooting mechanism of the player.
     * Creates a new bullet entity and plays the shooting sound effect.
     *
     * @param objects The list of game entities to which the new bullet will be added.
     */
    public void shoot(List<GameEntities> objects){
        if(shooting){
            bulletCount--;
            objects.add(new Bullet(new Vector2(position.x,position.y),new Vector2(facing_direction.x,facing_direction.y)));
            shooting = false;
            Manager.getInstance().soundManager.play("shoot",0.2f);
        }
    }

    /**
     * Returns the bounding box of the player for collision detection.
     *
     * @return The bounding box of the player.
     */
    @Override
    public BoundingBox box(){
        return new BoundingBox(position.x + 2,position.y + 2,sheet.getWidth() - 4, (float) sheet.getHeight() /2 - 4);
    }

    /**
     * Draws the player on the screen using the current animation frame.
     *
     * @param batch The batch used to draw the player's texture on the screen.
     */
    @Override
    public void draw(Batch batch) {
        // draw currentFrame of player at position x y
        batch.begin();
        TextureRegion t = sheet.getCurrentFrames();
        batch.draw(t, position.x ,position.y , (float) sheet.getWidth() /2, (float) sheet.getHeight() /2,sheet.getWidth(),sheet.getHeight(),1,1,0);
        batch.end();
    }

}
