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

public class Ghost2 extends Enemy{

    SpriteSheet sheet;
    A_Star pathFinding;
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
        pathFinding = new A_Star(map);

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
        if(Vector2.dst(position.x,position.y,player.position.x,player.position.y) > 150){
            return;
        }
        this.player = player;
        pathFinding.setEndNode((int) player.getPosition().x/16, (int) player.getPosition().y/16);
        path = pathFinding.findPath((int)position.x/16,(int)position.y/16);
    }

    @Override
    public BoundingBox getRect(){
        return new BoundingBox(position.x + 2,position.y + 2,sheet.getWidth() - 4, (float) sheet.getHeight() /2 - 4);
    }

    @Override
    public void update() {
        super.update();

        if(Vector2.dst(position.x,position.y,target.x,target.y) <= 1.0){
            currentTargetIndex += 1;
            if(currentTargetIndex >= path.size()){
                if(this.player != null){
                    pathFinding.setEndNode((int) player.getPosition().x/16, (int) player.getPosition().y/16);
                    path = pathFinding.findPath((int)position.x/16,(int)position.y/16);
                    currentTargetIndex = 0;
                }
                return;
            }

            target = path.get(currentTargetIndex);
            target = new Vector2(target.x * 16,target.y * 16);
            Vector2 dir = new Vector2(target.x - position.x,target.y - position.y).nor();
            vel = new Vector2(dir.x *speed,dir.y * speed);

        }
        position = position.add(vel);

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
        batch.begin();
        TextureRegion t = sheet.getCurrentFrames();
        batch.draw(t, position.x ,position.y ,sheet.getWidth()/2,sheet.getHeight()/2,sheet.getWidth(),sheet.getHeight(),scaleX,1,0);
        batch.end();
    }
}