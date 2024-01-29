package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.Pathfinding.PathFinding;
import de.tum.cit.ase.maze.Utilities.BoundingBox;
import de.tum.cit.ase.maze.Utilities.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class Ghost2 extends Enemy{

    SpriteSheet sheet;
    PathFinding pathFinding;
    List<Vector2> path;
    Vector2 target;
    Vector2 vel;
    float speed;
    int currentTargetIndex;
    int scaleX = 1;
    Player player;

    public Ghost2(Vector2 pos, Maps map) {
        super(pos, null);
        sheet = new SpriteSheet(new Texture("ghost-Sheet2.png"),1,4);
        sheet.setPlay(0, 3, 0.02f, true);
        pathFinding = new PathFinding(map);


        currentTargetIndex = 0;
        speed = 1.0F;
        target = new Vector2(pos.x ,pos.y);
        vel = new Vector2(0,0);
        this.player = null;
        this.path = new ArrayList<>();
    }
    public void setPlayer(Player player){
        if(this.player != null){
            return;
        }
        if(Vector2.dst(getPosition().x,getPosition().y,player.getPosition().x,player.getPosition().y) > 150){
            return;
        }
        this.player = player;
        Block destCell = pathFinding.getMap().getCell((int) player.getPosition().y/16,(int) player.getPosition().x/16);
        path = pathFinding.findPath((int)getPosition().x/16,(int)getPosition().y/16,destCell.getColumn(),destCell.getRow() );
    }

    @Override
    public BoundingBox getRect(){
        return new BoundingBox(getPosition().x + 2,getPosition().y + 2,sheet.getWidth() - 4, (float) sheet.getHeight() /2 - 4);
    }

    @Override
    public void update() {
        super.update();

     //   Gdx.app.log("Ghos2", "Update method called");

        if(Vector2.dst(getPosition().x,getPosition().y,target.x,target.y) <= 1.0){
            currentTargetIndex += 1;
            if(currentTargetIndex >= path.size()){

                if(this.player != null){
                    Block destCell = pathFinding.getMap().getCell((int) player.getPosition().y/16,(int) player.getPosition().x/16);
                    path = pathFinding.findPath((int)getPosition().x/16,(int)getPosition().y/16,destCell.getColumn(),destCell.getRow() );
                    currentTargetIndex = 0;
                }
                return;
            }

            target = path.get(currentTargetIndex);
            target = new Vector2(target.x * 16,target.y * 16);
            Vector2 dir = new Vector2(target.x - getPosition().x,target.y - getPosition().y).nor();
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
