package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteSheet {
    private Texture texture;
    private boolean loop;
    private int rows, columns, from, to, current, height, width;
    private float time, countTime = 0;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public TextureRegion[] getFrames() {
        return frames;
    }

    public void setFrames(TextureRegion[] frames) {
        this.frames = frames;
    }

    public SpriteSheet(Texture texture, int rows, int columns) {
        this.texture = texture;
        this.rows = rows;
        this.columns = columns;

        frames = new TextureRegion[rows * columns];
        width = texture.getWidth() / getColumns();
        height = texture.getHeight() / getRows();
        //Splitting  the sheet
        TextureRegion[][] tr = TextureRegion.split(texture, width, height);
        int x = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[x] = tr[i][j];
                x++;
            }
        }
    }

    public void setPlay(int from, int to, float time, boolean loop) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.loop = loop;
        current = from;

    }

    public void play() {
        countTime += Gdx.graphics.getDeltaTime();
        if(countTime >= time) {
            countTime = 0;
            current++;
            if(current > to && loop) {
                current = from;
            }
            else if(current > to){
                current = to;
            }
        }

    }

}