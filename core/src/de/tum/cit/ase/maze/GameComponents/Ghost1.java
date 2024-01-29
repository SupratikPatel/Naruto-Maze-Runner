package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.Utilities.BoundingBox;
import de.tum.cit.ase.maze.Utilities.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class Ghost1 extends Enemy{

    private SpriteSheet sheet;
    private de.tum.cit.ase.maze.Pathfinding.A_Star AStar;
    private List<Vector2> path;
    private Vector2 target,vel;
    private float speed;
    private int currentTargetIndex;
    int scaleX = 1;
    Player player;

    public SpriteSheet getSheet() {
        return sheet;
    }

    public void setSheet(SpriteSheet sheet) {
        this.sheet = sheet;
    }

    public de.tum.cit.ase.maze.Pathfinding.A_Star getPathFinding() {
        return AStar;
    }

    public void setPathFinding(de.tum.cit.ase.maze.Pathfinding.A_Star AStar) {
        this.AStar = AStar;
    }

    public List<Vector2> getPath() {
        return path;
    }

    public void setPath(List<Vector2> path) {
        this.path = path;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getCurrentTargetIndex() {
        return currentTargetIndex;
    }

    public void setCurrentTargetIndex(int currentTargetIndex) {
        this.currentTargetIndex = currentTargetIndex;
    }

    public int getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public Player getPlayer() {
        return player;
    }

    public Ghost1(Vector2 pos, Maps map) {
        super(pos, null);
        sheet = new SpriteSheet(new Texture("ghost-Sheet.png"),1,4);
        sheet.setPlay(0, 3, 0.02f, true);
        AStar = new de.tum.cit.ase.maze.Pathfinding.A_Star(map);
        currentTargetIndex = 0;
        speed = 0.5f;
        target = new Vector2(pos.x ,pos.y);
        vel = new Vector2(0,0);
        this.player = null;
        this.path = new ArrayList<>();
    }

    @Override
    public BoundingBox box(){
        return new BoundingBox(getPosition().x + 2,getPosition().y + 2,sheet.getWidth() - 4, (float) sheet.getHeight() /2 - 4);
    }

    @Override
    public void update() {
        super.update();

        //   Gdx.app.log("Ghos2", "Update method called");

        if(Vector2.dst(position.x,position.y,target.x,target.y) <= 1.0){
            currentTargetIndex += 1;
            if(currentTargetIndex >= path.size()){
                Block randomCell = AStar.getMap().getRandomEmptyCell();

                path = AStar.findPath((int)position.x/16,(int)position.y/16, randomCell.getColumn(),randomCell.getRow() );
                currentTargetIndex = 0;
                return;
            }

            target = path.get(currentTargetIndex);
            target = new Vector2(target.x * 16,target.y * 16);
            Vector2 dir = new Vector2(target.x - position.x,target.y - position.y).nor();
            vel = new Vector2(dir.x *speed,dir.y * speed);

        }
//        System.out.println("vel: " + target.x + ";" + target.y);
        position = getPosition().add(vel);

        if(vel.x < 0){
            scaleX = -1;
        }
        else {
            scaleX = 1;
        }

        sheet.play();
    }

    @Override
    public void draw(Batch batch) {
        // draw currentFrame of player at position x y

        //   Gdx.app.log("Ghost2", "Draw method called");
        batch.begin();
        TextureRegion t = sheet.getCurrentFrames();
        batch.draw(t, getPosition().x ,getPosition().y , (float) sheet.getWidth() /2, (float) sheet.getHeight() /2,sheet.getWidth(),sheet.getHeight(),scaleX,1,0);
        batch.end();
    }
}
