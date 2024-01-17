package de.tum.cit.ase.maze.GameComponents;

public enum CellType {
    WALL,TRAP,ENEMY,KEY,ENTRY_POINT,EXIT;

    public static CellType fromId(int n){
        switch (n){
            case 0:return WALL;
            case 1:return ENTRY_POINT;
            case 2:return EXIT;
            case 3:return TRAP;
            case 4:return ENEMY;
            case 5:return KEY;
        }
        return null;
    }
}
