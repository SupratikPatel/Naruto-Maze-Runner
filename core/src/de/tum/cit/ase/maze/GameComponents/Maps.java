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

/**
 * Represents the game map in a maze runner game.
 * This class is responsible for loading map properties from a file, initializing the map grid,
 * and managing various elements such as entry and exit points, traps, enemies, and keys.
 */
public class Maps {
    private static final int CELL_SIZE = 16; // The size of each cell in the map grid.
    private int Num_Of_Rows; // The number of rows in the map grid.
    private int Num_Of_Column; // The number of columns in the map grid.
    private Vector2 position; // The position of the map in the game world.
    private SpriteSheet tileSheet; // The sprite sheet used for rendering the map tiles.

    private Block entryBlock; // The entry block of the map.
    private Block exitBlock; // The exit block of the map.
    private Block[][] grid; // The grid representing the map layout.
    private List<Block> emptyBlocks; // A list of empty blocks in the map.
    private Random random; // A random number generator.
    private int keyCount = 0; // The count of keys placed in the map.

    /**
     * Constructor for the Maps class.
     * Initializes the map and loads properties from a file.
     *
     * @param path The path to the properties file.
     * @param gameElements A list of game elements.
     */
    public Maps(String path, List<GameEntities> gameElements) {
        initialize();
        loadPropertiesFile(path, gameElements);
    }

    /**
     Initializes the map by creating a new sprite sheet,
     empty blocks list, random number generator, and position vector.
     */
    private void initialize() {
        tileSheet = new SpriteSheet(new Texture("basictiles.png"), 15, 8);
        emptyBlocks = new ArrayList<>();
        random = new Random();
        position = new Vector2(0, 0);
    }

    /**
     Loads properties from a file and initializes the map grid and game elements.
     @param path The path to the properties file.
     @param entities A list of game entities.
     */
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

    /**
     Finds the number of rows and columns in the map grid based on the properties file.
     @param prop The properties loaded from the file.
     */
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

    /**
     Initializes the map grid with empty blocks.
     */
    private void initializeGrid() {
        grid = new Block[Num_Of_Rows][Num_Of_Column];
        for (int row = 0; row < Num_Of_Rows; row++) {
            grid[row] = new Block[Num_Of_Column];
            for (int col = 0; col < Num_Of_Column; col++) {
                grid[row][col] = new Block(row, col, null);
            }
        }
    }

    /**
     Processes a cell entry from the properties file and updates the map grid and game entities accordingly.
     @param entry The cell entry from the properties file.
     @param entities A list of game entities.
     */
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

    /**
     Processes an enemy cell and adds an enemy entity to the game entities list.
     @param entities A list of game entities.
     @param block The block representing the enemy cell.
     */
    private void processEnemyCell(List<GameEntities> entities, Block block) {
        if (random.nextInt(10) <= 3) {
            entities.add(new Ghost2(new Vector2(block.getColumn() *16, block.getRow() * 16), this));
        } else {
            entities.add(new Ghost1(new Vector2(block.getColumn() * 16, block.getRow() * 16), this));
        }

    }
    /**
     Populates the list of empty cells in the map grid.
     */
    private void populateEmptyCells() {
        for (int row = 0; row < Num_Of_Rows; row++) {
            for (int col = 0; col < Num_Of_Column; col++) {
                if (grid[row][col].getBlocksType() == null || grid[row][col].getBlocksType() == BlockType.ENEMY) {
                    emptyBlocks.add(grid[row][col]);
                }
            }
        }
    }

    /**
     Returns the block at a specific row and column in the map grid.
     @param row The row of the block.
     @param col The column of the block.
     @return The block at the specified row and column.
     */
    public Block getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     Returns a random empty block from the map grid.
     @return A random empty block.
     */
    public Block getRandomEmptyCell() {
        int randNum = random.nextInt(emptyBlocks.size());
        return emptyBlocks.get(randNum);
    }

    /**
     Draws the map to the screen.
     @param batch The batch used for drawing.
     @param player The player character.
     */
    public void draw(Batch batch, Player player) {
        batch.begin();

        for (int row = 0; row < Num_Of_Rows; row++) {
            for (int col = 0; col < Num_Of_Column; col++) {
                Block block = grid[row][col];
                if (Vector2.dst(player.position.x, player.position.y, block.getColumn() * CELL_SIZE, block.getRow() * CELL_SIZE) > 1500) {
                    continue;
                }

                if (block.getBlocksType() == null) {
                    drawCell(batch, block, tileSheet.getTexture(65));
                } else {
                    //using enhanced switched method
                    switch (block.getBlocksType()) {
                        case WALL -> drawCell(batch, block, tileSheet.getTexture(15));
                        case ENTRY_POINT, TRAP, ENEMY, KEY -> drawCell(batch, block, tileSheet.getTexture(65));
                        case EXIT -> drawCell(batch, block, tileSheet.getTexture(36));
                    }
                }
            }
        }

        batch.end();
    }


    /**
     Draws a specific cell to the screen.
     @param batch The batch used for drawing.
     @param block The block representing the cell.
     @param textureRegion The texture region used for drawing the cell.
     */
    private void drawCell(Batch batch, Block block, TextureRegion textureRegion) {
        batch.draw(textureRegion, position.x + block.getColumn() * CELL_SIZE, position.y + block.getRow() * CELL_SIZE,
                (float) tileSheet.getWidth() / 2, (float) tileSheet.getHeight() / 2, tileSheet.getWidth(), tileSheet.getHeight(), 1, 1, 0);
    }

    //Getters and Setters
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

}
