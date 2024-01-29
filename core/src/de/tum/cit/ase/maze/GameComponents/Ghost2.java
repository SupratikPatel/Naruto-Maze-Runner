package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.PathFinding.A_Star;
import de.tum.cit.ase.maze.Utilities.BoundingBox;
import de.tum.cit.ase.maze.Utilities.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Ghost2 enemy in the game, extending the Enemy class.
 * A Ghost2 enemy uses the A* pathfinding algorithm to navigate the game map.
 * Unlike Ghost1, Ghost2 activates when the player is in proximity and follows the player.
 */
public class Ghost2 extends Enemy {
    private SpriteSheet sheet; // The sprite sheet for the ghost's animations
    private A_Star AStar; // The A* pathfinding algorithm used by the ghost
    private List<Vector2> path; // The path that the ghost will follow
    private Vector2 target, vel; // The target position and velocity of the ghost
    private float speed; // The speed at which the ghost moves
    private int currentTargetIndex; // The index of the current target in the path
    private int scaleX = 1; // The scale factor for the ghost's sprite
    private Player player; // The player that the ghost is targeting

    /**
     * Constructs a new Ghost2 with a specified position and map.
     *
     * @param pos The initial position of the ghost in the game world.
     * @param map The map of the game world.
     */
    public Ghost2(Vector2 pos, Maps map) {
        super(pos, null);
        sheet = new SpriteSheet(new Texture("ghost-Sheet2.png"),1,4);
        sheet.setPlay(0, 3, 0.02f, true);
        AStar = new A_Star(map);
        currentTargetIndex = 0;
        speed = 1.0F;
        target = new Vector2(pos.x ,pos.y);
        vel = new Vector2(0,0);
        this.player = null;
        this.path = new ArrayList<>();
    }

    /**
     * Sets the player that the ghost will target. The ghost will only target a player
     * if it is within a certain distance of the ghost.
     *
     * @param player The player to target.
     */
    public void setPlayer(Player player){
        if(this.player != null){
            return;
        }
        if(Vector2.dst(getPosition().x,getPosition().y,player.getPosition().x,player.getPosition().y) > 150){
            return;
        }
        this.player = player;
        Block destCell = AStar.getMap().getCell((int) player.getPosition().y/16,(int) player.getPosition().x/16);
        path = AStar.findPath((int)getPosition().x/16,(int)getPosition().y/16,destCell.getColumn(),destCell.getRow() );
    }

    /**
     * Returns a bounding box that encloses the ghost. The bounding box is used for
     * collision detection and other similar operations.
     *
     * @return The bounding box of the ghost.
     */
    @Override
    public BoundingBox box() {
        return new BoundingBox(getPosition().x + 2, getPosition().y + 2, sheet.getWidth() - 4, (float) sheet.getHeight() / 2 - 4);
    }

    /**
     * Updates the ghost's position and state. This method overrides the update method
     * in the Enemy superclass.
     */
    @Override
    public void update() {
        super.update();

        if (Vector2.dst(getPosition().x, getPosition().y, target.x, target.y) <= 1.0) {
            currentTargetIndex += 1;
            if (currentTargetIndex >= path.size()) {

                if(this.player != null){
                    Block destCell = AStar.getMap().getCell((int) player.getPosition().y/16,(int) player.getPosition().x/16);
                    path = AStar.findPath((int)getPosition().x/16,(int)getPosition().y/16,destCell.getColumn(),destCell.getRow() );
                    currentTargetIndex = 0;
                }
                return;
            }

            target = path.get(currentTargetIndex);
            target = new Vector2(target.x * 16, target.y * 16);
            Vector2 dir = new Vector2(target.x - getPosition().x, target.y - getPosition().y).nor();
            vel = new Vector2(dir.x * speed, dir.y * speed);
        }

        position = getPosition().add(vel);

        if (vel.x < 0) {
            scaleX = -1;
        } else {
            scaleX = 1;
        }

        sheet.play();
    }

    /**
     * Draws the ghost on the specified batch.
     *
     * @param batch The batch on which to draw the ghost.
     */
    @Override
    public void draw(Batch batch) {
        batch.begin();
        TextureRegion t = sheet.getCurrentFrames();
        batch.draw(t, getPosition().x, getPosition().y, (float) sheet.getWidth() / 2, (float) sheet.getHeight() / 2, sheet.getWidth(), sheet.getHeight(), scaleX, 1, 0);
        batch.end();
    }

    // Getter for the ghost's properties...

    public Player getPlayer() {
        return player;
    }
}
