package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents a sprite sheet, which is a collection of smaller images, or "frames",
 * stored in a larger image, or "texture". The frames can be used to animate a character or object in a game.
 */
public class SpriteSheet {
    private Texture texture;
    private boolean loop;
    private int rows, columns, from, to, current, height, width;
    private float time, countTime = 0;
    TextureRegion[] frames;

    /**
     * Constructor for SpriteSheet. Splits the given texture into a grid of frames.
     *
     * @param texture The texture to split into frames.
     * @param rows The number of rows in the grid.
     * @param columns The number of columns in the grid.
     */
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

    /**
     * Sets the range of frames to play, the time each frame should be displayed, and whether the animation should loop.
     *
     * @param from The first frame to play.
     * @param to The last frame to play.
     * @param time The time each frame should be displayed.
     * @param loop Whether the animation should loop.
     */
    public void setPlay(int from, int to, float time, boolean loop) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.loop = loop;
        current = from;
    }

    /**
     * Updates the current frame based on the elapsed time. If the animation reaches the end and looping is enabled,
     * it wraps back to the start. If looping is not enabled, it stops at the last frame.
     */
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }



}