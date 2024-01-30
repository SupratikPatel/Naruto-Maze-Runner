package de.tum.cit.ase.maze.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.maze.Utilities.Manager;

/**
 * The `MenuScreen` class is responsible for displaying the main menu of the game.
 * It extends the LibGDX `Screen` class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private Stage stage;
    private final MazeRunnerGame game;

    /**
     * Constructor for `MenuScreen`.
     *
     * @param game The game instance associated with this screen.
     */
    public MenuScreen(MazeRunnerGame game) {
        this.game = game;
        initializeStage();
        setupUI();
    }

    /**
     * Initializes the stage for the menu screen.
     */
    private void initializeStage() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1.5f;
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
    }

    /**
     * Sets up the user interface for the menu screen.
     */
    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Game", game.getSkin(), "title")).padBottom(80).row();

        TextButton playButton = new TextButton("Play", game.getSkin());
        TextButton loadGameButton = new TextButton("Load", game.getSkin());
        TextButton exitButton = new TextButton("Exit", game.getSkin());

        Gdx.input.setInputProcessor(stage);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(playButton).width(300);
        table.add(loadGameButton).width(300);
        table.add(exitButton).width(300).row();

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToGame();
            }
        });

        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadGame();
            }
        });

        Manager.getInstance().soundManager.playMenuMusic();
    }

    /**
     * Renders the menu screen.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * Resizes the menu screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    /**
     * Disposes of the menu screen when it is no longer needed.
     */
    @Override
    public void dispose() {
        // Dispose of the stage when the screen is disposed
        stage.dispose();
    }

    /**
     * Shows the menu screen.
     */
    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the `Screen` interface but are not used in this screen.

    /**
     * Unused method. Does nothing.
     */
    @Override
    public void pause() {
    }

    /**
     * Unused method. Does nothing.
     */
    @Override
    public void resume() {
    }

    /**
     * Unused method. Does nothing.
     */
    @Override
    public void hide() {
    }
}