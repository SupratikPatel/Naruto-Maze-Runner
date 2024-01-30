package de.tum.cit.ase.maze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class MenuScreen implements Screen {
    private Stage stage;
    private final MazeRunnerGame game;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    public MenuScreen(MazeRunnerGame game) {
        this.game = game;
        initializeStage();
        setupUI();

        // Load the background texture and create a sprite
        backgroundTexture = new Texture(Gdx.files.internal("background.png")); // Replace with your image file
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void initializeStage() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1.0f;
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Maze Runner", game.getSkin(), "title")).padBottom(100);

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        // Draw the background sprite
        game.getSpriteBatch().begin();
        backgroundSprite.draw(game.getSpriteBatch());
        game.getSpriteBatch().end();

        // Now draw the stage with the buttons on top of the background
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}