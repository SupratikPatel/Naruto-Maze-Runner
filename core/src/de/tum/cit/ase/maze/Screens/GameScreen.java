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
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final Stage stage;

    private Viewport viewport;

    List<GameEntities> objects;
    Batch batch;
    Maps map;
    Player player;
    Camera followCamera;

    int score ;
    float time ;
    int heart;
    int keyCount;

    Label scoreLabel;
    Label heartLabel;
    Label timeLabel;
    Label keyLabel;
    Label bananaLabel;
    Label missingKeyLabel;

    Table pauseMenu;
    boolean gamePause = false;

    public GameScreen(MazeRunnerGame game,String mapPath) {
        this.game = game;
        objects = new ArrayList<>();


        // Create and configure the camera for the game view
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);
        stage = new Stage(); // Create a stage for UI elements

//        viewport = new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
//        ExtendViewportPermalink

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        batch = game.getSpriteBatch();
        map = new Maps(mapPath,objects);
        player = new Player(new Vector2(map.getEntryBlock().getColumn() * 16,map.getEntryBlock().getRow() * 16));
        keyInput();
        followCamera = new Camera(camera,map);

        spawnBullet();

        score  = 0;
        time = 0;
        heart = 3;
        keyCount = 0;
        createUI();

    }

    void createUI(){
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage
        table.center().top();


        // heart
        Table heartTable = new Table();
        table.add(heartTable);
        heartTable.setBackground(new TextureRegionDrawable(new Texture("uiBox.png")));

        Image hearthImg = new Image(new Texture("heart.png"));
        heartLabel = new Label("1",game.getSkin());
        heartLabel.setColor(Color.BLACK);
        heartTable.add(heartLabel).pad(10);
        heartTable.add(hearthImg);


        // score
        Table scoreTable = new Table();
        table.add(scoreTable);
        scoreTable.setBackground(new TextureRegionDrawable(new Texture("uiBox.png")));

        Label label2 = new Label("Score:",game.getSkin());
        scoreLabel = new Label("10000",game.getSkin());
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

        Label label3 = new Label("Time:",game.getSkin());
        timeLabel = new Label("120s",game.getSkin());
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

        keyLabel = new Label("0/" + map.getKeyCount(),game.getSkin());
        keyLabel.setFontScale(0.6f);
        keyLabel.setColor(Color.YELLOW);
        keyTable.add(keyLabel).pad(10);
        keyTable.add(new Image(new Texture("key.png")));

        missingKeyLabel = new Label("missing key!",game.getSkin());
        missingKeyLabel.setVisible(false);
        missingKeyLabel.setColor(Color.WHITE);
        missingKeyLabel.setPosition((float) Gdx.graphics.getWidth() /2 - missingKeyLabel.getWidth()/2, (float) Gdx.graphics.getHeight() /2 - missingKeyLabel.getHeight()/2);
        stage.addActor(missingKeyLabel);


        pauseMenu = new Table();
        pauseMenu.setFillParent(true);

        pauseMenu.add(new Label("ESC: Continue!",game.getSkin())).padBottom(20).row();


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

    void keyInput(){
        InputMultiplexer mixInput = new InputMultiplexer();
        mixInput.addProcessor(stage);
        mixInput.addProcessor(new InputAdapter(){
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

    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            game.goToVictoryScreen();
            gamePause = !gamePause;
            pauseMenu.setVisible(gamePause);
        }
        ScreenUtils.clear(0, 0, 0, 1);







//        camera.position.x += (viewport.getScreenX() + viewport.getScreenWidth()/4 + delta - camera.position.x) * 1 * delta;
        camera.update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if(!gamePause){
            // collision player with other objects
            collision2();

            // collision between objects
            collision();
            followCamera.follow(player.getPosition());

            // update time,score,heart
            time += delta;
            timeLabel.setText(String.format("%.2f",time) + "s");
            scoreLabel.setText(score);
            heartLabel.setText(heart);
            keyLabel.setText(keyCount + "/" + map.getKeyCount());

            List<GameEntities> addedObjects = new ArrayList<>();
            player.update(map);
            player.shoot(addedObjects);


            for(GameEntities obj : objects){
                obj.update();
            }
            objects.addAll(addedObjects);

            missingKeyLabel.setVisible(false);
            for(int row = 0; row < map.getNum_Of_Rows();row++) {
                for (int col = 0; col < map.getNum_Of_Column(); col++) {
                    Block block = map.getCell(row,col);
                    if(block.getBlocksType() == BlockType.EXIT && block.rectangle().collide(player.getRect())){

                        if(keyCount == map.getKeyCount()){
                            score += 100;
                            Manager.getInstance().soundManager.play("win",1.0f);//win
                            game.goToVictoryScreen(score,time);
                            return;
                        }
                        else {
                            missingKeyLabel.setVisible(true);
                            Manager.getInstance().soundManager.play("item",1.0f);//win
                        }
                    }
                }
            }



            for(int i = 0; i < objects.size();i++){
                if(objects.get(i).destroyFlAG){
                    objects.remove(i);
                }
            }
        }

        //draw
        draw();

    }
    public void draw(){
        map.draw(batch,player);

        for(GameEntities obj : objects){
            if(Vector2.dst(player.getPosition().x,player.getPosition().y,obj.getPosition().x,obj.getPosition().y) > 2000){ // fog of war
                continue;
            }
            obj.draw(batch);
        }

        player.draw(batch);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage


        // debug
//        Utils.getInstance().drawRect(player.getRect(),camera);
//        for(int row = 0; row < map.getRows();row++) {
//            for (int col = 0; col < map.getCols(); col++) {
//                Block cell = map.getCell(row,col);
//                Utils.getInstance().drawRect(cell.getRect(),camera);
//            }
//        }

        viewport.apply(false);
    }

    public void collision(){
        for(GameEntities obj : objects){
            for(GameEntities otherObj: objects){
                if(obj == otherObj){
                    continue;
                }
                if(obj instanceof Bullet && (otherObj instanceof Ghost2 || otherObj instanceof Traps)){
                    if(obj.getRect().collide(otherObj.getRect())){
                        score += 10;
                        obj.destroyFlAG = true;
                        otherObj.destroyFlAG = true;
                    }
                }

            }
        }
    }
    public void collision2(){
        for(GameEntities obj : objects){
            if(obj instanceof Ghost2){
                ((Ghost2)obj).setPlayer(player);
            }
            if(obj instanceof Enemy && obj.getRect().collide(player.getRect())){
                Manager.getInstance().soundManager.play("death",1.0f);
                playerDie();
            }
            else if(obj instanceof Key && obj.getRect().collide(player.getRect())){
                obj.destroyFlAG = true;
                keyCount++;
                score += 50;
                Manager.getInstance().soundManager.play("item",1.0f);
            }
            else if(obj instanceof Traps && obj.getRect().collide(player.getRect())){
                Manager.getInstance().soundManager.play("death",1.0f);
                playerDie();
            }
        }
    }
    void playerDie(){
        heart--;
        Block entryBlock = map.getEntryBlock();
        player.setPosition(new Vector2(entryBlock.getColumn() * 16, entryBlock.getRow() * 16));
        Manager.getInstance().soundManager.play("death",1.0f);
        if(heart <= 0){
            Manager.getInstance().soundManager.play("death2",2.0f);
            game.goToGameOverScreen(score,time);
        }
    }

    void spawnBullet(){
        Block emptyBlock = map.getRandomEmptyCell();
        while (Vector2.dst(player.getPosition().x,player.getPosition().y, emptyBlock.getColumn()*16, emptyBlock.getRow() * 16) > 200){
            emptyBlock = map.getRandomEmptyCell();
        }
    }


    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
        viewport.update(width,height,false);
//        stage.getViewport().update(width, height, false); // Update the stage viewport on resize
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    // Additional methods and logic can be added as needed for the game screen
}