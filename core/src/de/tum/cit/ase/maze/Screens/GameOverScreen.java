package de.tum.cit.ase.maze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The screen displayed when the game is over.
 * This class handles the display of the game over message, the player's score, and the time taken.
 * It also provides a button to return to the main menu.
 */
public class GameOverScreen implements Screen {

    private final Stage stage;
    private final MazeRunnerGame game;

    /**
     * Constructor for the GameOverScreen class.
     * Sets up the camera, viewport, stage, and UI elements for the game over screen.
     *
     * @param game  The instance of the MazeRunnerGame.
     * @param score The final score achieved by the player.
     * @param time  The time taken by the player.
     */
    public GameOverScreen(MazeRunnerGame game, int score, float time) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements
        setupUI(score, time);
    }

    /**
     * Sets up the UI elements for the game over screen.
     * Adds labels for game over message, score, and time, and a button to go back to the menu.
     *
     * @param score The final score achieved by the player.
     * @param time  The time taken by the player.
     */
    private void setupUI(int score, float time) {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameOverLabel = new Label("GameOver!!", game.getSkin(), "title");
        Label scoreLabel = new Label("Score: " + score, game.getSkin(), "title");
        Label timeLabel = new Label("Time: " + String.format("%.2fs", time), game.getSkin(), "title");

        gameOverLabel.setColor(Color.RED);
        table.add(gameOverLabel).padBottom(80).row();
        table.add(scoreLabel).padBottom(80).row();
        table.add(timeLabel).padBottom(80).row();

        TextButton goToGameButton = new TextButton("Menu", game.getSkin());
        table.add(goToGameButton).width(300).row();

        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
    }

    /**
     * Renders the game over screen.
     * Clears the screen and draws the stage with the UI elements.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * Resizes the viewport of the stage.
     * This method is called when the screen size is changed.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    /**
     * Disposes of resources used by the screen.
     */
    @Override
    public void dispose() {
        // Dispose of the stage when the screen is disposed
        stage.dispose();
    }

    /**
     * Called when the screen is shown.
     * Sets the input processor so the stage can receive input events.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set the input processor so the stage can receive input events
    }

    /**
     * Unused method from the Screen interface.
     */
    @Override
    public void pause() {
    }

    /**
     * Unused method from the Screen interface.
     */
    @Override
    public void resume() {
    }

    /**
     * Unused method from the Screen interface.
     */
    @Override
    public void hide() {
    }
}