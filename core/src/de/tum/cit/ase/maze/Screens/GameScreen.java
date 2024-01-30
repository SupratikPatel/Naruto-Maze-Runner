package de.tum.cit.ase.maze.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import de.tum.cit.ase.maze.Utilities.Camera;
import de.tum.cit.ase.maze.Utilities.Manager;
import de.tum.cit.ase.maze.GameComponents.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The `GameScreen` class is responsible for rendering the gameplay screen.
 * It handles the game logic, UI, and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final Stage stage;

    private final Viewport viewport;

    List<GameEntities> objects;
    Batch batch;
    Maps map;
    Player player;
    Camera followCamera;

    int score;
    float time;
    int heart;
    int keyCount;

    Label scoreLabel;
    Label heartLabel;
    Label timeLabel;
    Label keyLabel;
    Label missingKeyLabel;

    Table pauseMenu;
    boolean gamePause = false;

    /**
     * Constructor for the `GameScreen` class.
     * Initializes the game screen with the given game and map path.
     *
     * @param game    The `MazeRunnerGame` instance.
     * @param mapPath The path to the map file.
     */
    public GameScreen(MazeRunnerGame game, String mapPath) {
        this.game = game;
        objects = new ArrayList<>();

        // Create and configure the camera for the game view
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);
        stage = new Stage(); // Create a stage for UI elements

        // Get the font from the game's skin
        BitmapFont font = game.getSkin().getFont("font");
        batch = game.getSpriteBatch();
        map = new Maps(mapPath, objects);
        player = new Player(new Vector2(map.getEntryBlock().getColumn() * 16, map.getEntryBlock().getRow() * 16));
        keyInput();
        followCamera = new Camera(camera, map);

        spawnBullet();

        score = 0;
        time = 0;
        heart = 3;
        keyCount = 0;
        createUI();
    }

    /**
     * Creates the user interface for the game.
     */
    void createUI() {
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage
        table.center().top();

        // heart
        Table heartTable = new Table();
        table.add(heartTable);
        heartTable.setBackground(new TextureRegionDrawable(new Texture("uiBox.png")));

        Image hearthImg = new Image(new Texture("heart.png"));
        heartLabel = new Label("1", game.getSkin());
        heartLabel.setColor(Color.BLACK);
        heartTable.add(heartLabel).pad(10);
        heartTable.add(hearthImg);

        // score
        Table scoreTable = new Table();
        table.add(scoreTable);
        scoreTable.setBackground(new TextureRegionDrawable(new Texture("uiBox.png")));

        Label label2 = new Label("Score:", game.getSkin());
        scoreLabel = new Label("10000", game.getSkin());
        label2.setColor(Color.BLACK);
        label2.setFontScale(0.6f);
        scoreLabel.setFontScale(0.6f);
        scoreLabel.setColor(Color.CYAN);
        scoreTable.add(label2).pad(10);
        scoreTable.add(scoreLabel);

        // time
        Table timeTable = new Table();
        table.add(timeTable);
        timeTable.setBackground(new TextureRegionDrawable(new Texture("uiBox.png")));

        Label label3 = new Label("Time:", game.getSkin());
        timeLabel = new Label("120s", game.getSkin());
        label3.setColor(Color.BLACK);
        label3.setFontScale(0.6f);
        timeLabel.setFontScale(0.6f);
        timeLabel.setColor(Color.BLUE);
        timeTable.add(label3).pad(10);
        timeTable.add(timeLabel);

        // key
        Table keyTable = new Table();
        table.add(keyTable);
        keyTable.setBackground(new TextureRegionDrawable(new Texture("uiBox.png")));

        keyLabel = new Label("0/" + map.getKeyCount(), game.getSkin());
        keyLabel.setFontScale(0.6f);
        keyLabel.setColor(Color.YELLOW);
        keyTable.add(keyLabel).pad(10);
        keyTable.add(new Image(new Texture("key.png")));

        missingKeyLabel = new Label("missing key!", game.getSkin());
        missingKeyLabel.setVisible(false);
        missingKeyLabel.setColor(Color.WHITE);
        missingKeyLabel.setPosition((float) Gdx.graphics.getWidth() / 2 - missingKeyLabel.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2 - missingKeyLabel.getHeight() / 2);
        stage.addActor(missingKeyLabel);

        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        pauseMenu.add(new Label("ESC: Continue!", game.getSkin())).padBottom(20).row();

        TextButton loadBtn = new TextButton("Load", game.getSkin());
        pauseMenu.add(loadBtn).width(200);
        loadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadGame(); // Change to the game screen when button is pressed
            }
        });
        TextButton gotoMenuBtn = new TextButton("Menu", game.getSkin());
        pauseMenu.add(gotoMenuBtn).width(200).padLeft(50).row();
        gotoMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Change to the game screen when button is pressed
            }
        });
        pauseMenu.setVisible(false);
        stage.addActor(pauseMenu);
        Manager.getInstance().soundManager.playGameMusic();
    }

    /**
     * Handles key input for the game.
     */
    void keyInput() {
        InputMultiplexer mixInput = new InputMultiplexer();
        mixInput.addProcessor(stage);
        mixInput.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                player.Key_Pressed(keycode);
                return super.keyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode) {
                player.Key_Release(keycode);
                return super.keyUp(keycode);
            }
        });
        Gdx.input.setInputProcessor(mixInput);
    }

    /**
     * Renders the game screen, updating all entities and handling user input.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gamePause = !gamePause;
            pauseMenu.setVisible(gamePause);
        }
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if (!gamePause) {
            // collision player with other objects
            collision2();

            // collision between bullet and objects
            collision();
            followCamera.follow(player.getPosition());

            // update time, score, heart
            time += delta;
            timeLabel.setText(String.format("%.2f", time) + "s");
            scoreLabel.setText(Integer.toString(score));
            heartLabel.setText(Integer.toString(heart));
            keyLabel.setText(keyCount + "/" + map.getKeyCount());

            List<GameEntities> addedObjects = new ArrayList<>();
            player.update(map);
            player.shoot(addedObjects);

            for (GameEntities obj : objects) {
                obj.update();
            }
            objects.addAll(addedObjects);

            missingKeyLabel.setVisible(false);
            for (int row = 0; row < map.getNum_Of_Rows(); row++) {
                for (int col = 0; col < map.getNum_Of_Column(); col++) {
                    Block block = map.getCell(row, col);
                    if (block.getBlocksType() == BlockType.EXIT && block.rectangle().collide(player.box())) {
                        if (keyCount == map.getKeyCount()) {
                            score += 100;
                            Manager.getInstance().soundManager.stopMusic();
                            Manager.getInstance().soundManager.play("win", 1.0f);//win
                            game.goToVictoryScreen(score, time);
                            return;
                        } else {
                            missingKeyLabel.setVisible(true);
                            Manager.getInstance().soundManager.play("item", 1.0f);//win
                        }
                    }
                }
            }
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).destroyFlAG) {
                    objects.remove(i);
                }
            }
        }
        draw();
    }

    /**
     * Draws the game entities and UI elements to the screen.
     */
    public void draw() {
        map.draw(batch, player);

        for (GameEntities obj : objects) {
            if (Vector2.dst(player.getPosition().x, player.getPosition().y, obj.getPosition().x, obj.getPosition().y) > 2000) { // fog of war
                continue;
            }
            obj.draw(batch);
        }

        player.draw(batch);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
        viewport.apply(false);
    }

    /**
     * Handles collision detection between bullets and other game entities.
     */
    public void collision() {
        for (GameEntities obj : objects) {
            for (GameEntities otherObj : objects) {
                if (obj == otherObj) {
                    continue;
                }
                if (obj instanceof Bullet && (otherObj instanceof Ghost2 || otherObj instanceof Traps)) {
                    if (obj.box().collide(otherObj.box())) {
                        score += 10;
                        obj.destroyFlAG = true;
                        otherObj.destroyFlAG = true;
                    }
                }
            }
        }
    }

    /**
     * Handles collision detection between the player and other game entities.
     */
    public void collision2() {
        for (GameEntities obj : objects) {
            if (obj instanceof Ghost2) {
                ((Ghost2) obj).setPlayer(player);
            }
            if (obj instanceof Enemy && obj.box().collide(player.box())) {
                Manager.getInstance().soundManager.play("death", 1.0f);
                playerDie();
            } else if (obj instanceof Key && obj.box().collide(player.box())) {
                obj.destroyFlAG = true;
                keyCount++;
                score += 50;
                Manager.getInstance().soundManager.play("collectkey", 9.5f);
            } else if (obj instanceof Traps && obj.box().collide(player.box())) {
                Manager.getInstance().soundManager.play("death", 1.0f);
                playerDie();
            }
        }
    }

    /**
     * Handles the logic for when the player dies.
     */
    void playerDie() {
        heart--;
        Block entryBlock = map.getEntryBlock();
        player.setPosition(new Vector2(entryBlock.getColumn() * 16, entryBlock.getRow() * 16));
        Manager.getInstance().soundManager.play("death", 1.0f);
        if (heart <= 0) {
            Manager.getInstance().soundManager.play("death2", 2.0f);
            game.goToGameOverScreen(score, time);
        }
    }

    /**
     * Spawns bullets in the game world.
     */
    void spawnBullet() {
        Block emptyBlock = map.getRandomEmptyCell();
        while (Vector2.dst(player.getPosition().x, player.getPosition().y, emptyBlock.getColumn() * 16, emptyBlock.getRow() * 16) > 200) {
            emptyBlock = map.getRandomEmptyCell();
        }
    }

    /**
     * Resizes the game viewport.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
        viewport.update(width, height, false);
    }

    @Override
    public void pause() {
        // Additional logic can be added if needed
    }

    @Override
    public void resume() {
        // Additional logic can be added if needed
    }

    @Override
    public void show() {
        // Additional logic can be added if needed
    }

    @Override
    public void hide() {
        // Additional logic can be added if needed
    }

    @Override
    public void dispose() {
        // Additional logic can be added if needed
    }

    // Additional methods and logic can be added as needed for the game screen
}