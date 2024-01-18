package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteSheet {
Texture texture;
boolean loop;
int rows, columns,from,to,current,height,width;
float time,countTime;
TextureRegion[] frames;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public float getCountTime() {
        return countTime;
    }

    public void setCountTime(float countTime) {
        this.countTime = countTime;
    }

    public TextureRegion getTexture(int frames) {
        return this.frames[frames];
    }

    public TextureRegion getCurrentFrames() {
        return frames[current];
    }

    public SpriteSheet(Texture texture, int rows, int columns) {
        this.texture = texture;
        this.rows = rows;
        this.columns = columns;

        frames =new TextureRegion[rows*columns];
        width= texture.getWidth()/columns;
        height= texture.getHeight()/rows;
        //Splitting  the sheet
        TextureRegion[][] tr=TextureRegion.split(texture, width, height);
       int x=0;
       for(int i=0;i<rows;i++){
           for(int j=0;j<columns;j++){
               frames[x]=tr[i][j];
               x++;
           }
       }
    }
    public void flip(boolean flipX){
        int x=0;
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                frames[x].flip(flipX,false);
                x++;
            }
        }
    }
    public void setPlay(int from,int to, float time, boolean loop){
        this.from=from;
        this.to=to;
        this.time=time;
        this.loop=loop;
        this.current=from;

    }
    public void play(){
        countTime=countTime+ Gdx.graphics.getDeltaTime();
        if(countTime>=time){
            setCountTime(0);
            current++;
            if (current>to && loop) {
                setCurrent(from);
            } else setCurrent(to);
        }
    }
    public void draw(Batch batch, Vector2 position,float scaleX, float scaleY){
        batch.draw(frames[current],position.x,position.y, (float) width /2, (float) height /2,width,height,scaleX,scaleY,0);
    }
    public void dispose(){
        texture.dispose();
    }
}
