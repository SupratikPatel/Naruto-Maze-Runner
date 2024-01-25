package de.tum.cit.ase.maze.GameComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.Utilities.SpriteSheet;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Maps {
    private static final int CELL_SIZE = 16;
    private int Num_Of_Rows;
    private int Num_Of_Column;
    private Vector2 position;
    private SpriteSheet tileSheet;

    private Block entryBlock;
    private Block exitBlock;
    private Block[][] grid;
    private List<Block> emptyBlocks;
    private Random random;
    private int keyCount = 0;


    public int getNum_Of_Rows() {
        return Num_Of_Rows;
    }

    public void setNum_Of_Rows(int num_Of_Rows) {
        Num_Of_Rows = num_Of_Rows;
    }

    public int getNum_Of_Column() {
        return Num_Of_Column;
    }

    public void setNum_Of_Column(int num_Of_Column) {
        Num_Of_Column = num_Of_Column;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public SpriteSheet getTileSheet() {
        return tileSheet;
    }

    public void setTileSheet(SpriteSheet tileSheet) {
        this.tileSheet = tileSheet;
    }

    public Block getEntryBlock() {
        return entryBlock;
    }

    public void setEntryBlock(Block entryBlock) {
        this.entryBlock = entryBlock;
    }

    public Block getExitBlock() {
        return exitBlock;
    }

    public void setExitBlock(Block exitBlock) {
        this.exitBlock = exitBlock;
    }

    public Block[][] getGrid() {
        return grid;
    }

    public void setGrid(Block[][] grid) {
        this.grid = grid;
    }

    public List<Block> getEmptyBlocks() {
        return emptyBlocks;
    }

    public void setEmptyBlocks(List<Block> emptyBlocks) {
        this.emptyBlocks = emptyBlocks;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public Maps(String path, List<GameEntities> gameElements) {
        initialize();
        loadPropertiesFile(path, gameElements);
    }

    private void initialize() {
        tileSheet = new SpriteSheet(new Texture("basictiles.png"), 15, 8);
        emptyBlocks = new ArrayList<>();
        random = new Random();
        position = new Vector2(0, 0);
    }

    private void loadPropertiesFile(String path, List<GameEntities> entities) {
        Properties property = new Properties();

        try (FileInputStream input = new FileInputStream(path)) {
            property.load(input);

            findRowsAndColumns(property);
            initializeGrid();

            for (java.util.Map.Entry<Object, Object> entry : property.entrySet()) {
                processCellEntry(entry, entities);
            }
            populateEmptyCells();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void findRowsAndColumns(Properties prop) {
        for (java.util.Map.Entry<Object, Object> entry : prop.entrySet()) {
            String key = (String) entry.getKey();
            try {
                int row = Integer.parseInt(key.split(",")[0]);
                int col = Integer.parseInt(key.split(",")[1]);

                if (col > Num_Of_Column) {
                    Num_Of_Column = col;
                }
                if (row > Num_Of_Rows) {
                    Num_Of_Rows = row;
                }
            } catch (Exception e) {
                // Ignore invalid entries
            }
        }
        Num_Of_Column += 1;
        Num_Of_Rows += 1;
    }

    private void initializeGrid() {
        grid = new Block[Num_Of_Rows][Num_Of_Column];
        for (int row = 0; row < Num_Of_Rows; row++) {
            grid[row] = new Block[Num_Of_Column];
            for (int col = 0; col < Num_Of_Column; col++) {
                grid[row][col] = new Block(row, col, null);
            }
        }
    }

    private void processCellEntry(java.util.Map.Entry<Object, Object> entry, List<GameEntities> entities) {
        String key = (String) entry.getKey();
        int value = Integer.parseInt((String) entry.getValue());

        try {
            int col = Integer.parseInt(key.split(",")[1]);
            int row = Integer.parseInt(key.split(",")[0]);

            Block block = grid[row][col];

            block.setBlocksType(BlockType.blockCaseType(value));

            switch (block.getBlocksType()) {
                case ENTRY_POINT -> entryBlock = block;
                case EXIT -> exitBlock = block;
                case ENEMY -> processEnemyCell(entities, block);
                case KEY -> {
                    entities.add(new Key(new Vector2(block.getColumn() * CELL_SIZE, block.getRow() * CELL_SIZE)));
                    keyCount++;
                }
                case TRAP -> entities.add(new Traps(new Vector2(block.getColumn() * CELL_SIZE, block.getRow() * CELL_SIZE)));
            }
        } catch (Exception e) {
            // Ignore invalid entries
        }
    }

    private void processEnemyCell(List<GameEntities> entities, Block block) {

    }
    private void populateEmptyCells() {
        for (int row = 0; row < Num_Of_Rows; row++) {
            for (int col = 0; col < Num_Of_Column; col++) {
                if (grid[row][col].getBlocksType() == null || grid[row][col].getBlocksType() == BlockType.ENEMY) {
                    emptyBlocks.add(grid[row][col]);
                }
            }
        }
    }

    public Block getCell(int row, int col) {
        return grid[row][col];
    }

    public Block getRandomEmptyCell() {
        int randNum = random.nextInt(emptyBlocks.size());
        return emptyBlocks.get(randNum);
    }



    public void draw(Batch batch, Player player) {
        batch.begin();

        for (int row = 0; row < Num_Of_Rows; row++) {
            for (int col = 0; col < Num_Of_Column; col++) {
                Block block = grid[row][col];
                if (Vector2.dst(player.position.x, player.position.y, block.getColumn() * CELL_SIZE, block.getRow() * CELL_SIZE) > 1500) {
                    continue;
                }

                if (block.getBlocksType() == null) {
                    drawCell(batch, block, tileSheet.getTexture(73));
                } else {
                    //using enhanced switched method
                    switch (block.getBlocksType()) {
                        case WALL -> drawCell(batch, block, tileSheet.getTexture(1));
                        case ENTRY_POINT, TRAP, ENEMY, KEY -> drawCell(batch, block, tileSheet.getTexture(73));
                        case EXIT -> drawCell(batch, block, tileSheet.getTexture(36));
                    }
                }
            }
        }

        batch.end();
    }

    private void drawCell(Batch batch, Block block, TextureRegion textureRegion) {
        batch.draw(textureRegion, position.x + block.getColumn() * CELL_SIZE, position.y + block.getRow() * CELL_SIZE,
                (float) tileSheet.getWidth() / 2, (float) tileSheet.getHeight() / 2, tileSheet.getWidth(), tileSheet.getHeight(), 1, 1, 0);
    }


}
