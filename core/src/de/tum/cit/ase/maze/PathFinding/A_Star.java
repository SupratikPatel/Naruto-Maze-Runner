package de.tum.cit.ase.maze.PathFinding;

import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.GameComponents.BlockType;
import de.tum.cit.ase.maze.GameComponents.Maps;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the A* search algorithm to find the shortest path in a maze.
 * This class is responsible for pathfinding in a grid-based map using the A* algorithm.
 */
public class A_Star {
    List<Nodes> open_list; // The set of nodes to be evaluated.
    List<Nodes> close_list; // The set of nodes already evaluated.
    Maps maps; // The game map.

    Nodes startNodes; // The starting node of the path.
    Nodes endNodes; // The ending node of the path.

    /**
     * Constructs an A* pathfinder with a reference to the game map.
     *
     * @param maps The game map containing the nodes and their connections.
     */
    public A_Star(Maps maps) {
        this.maps = maps;
    }

    public Maps getMap(){
        return maps;
    }

    /**
     * Checks if a nodes is in the open list.
     *
     * @param nodes The nodes to check.
     * @return The nodes from the open list if it exists, null otherwise.
     */
    Nodes isInOpenList(Nodes nodes) {
        for (Nodes n : open_list) {
            if (n.equal(nodes)) {
                return n;
            }
        }
        return null;
    }
    /**
     * Checks if a node is in the close list based on its row and column.
     *
     * @param row The row of the node to check.
     * @param col The column of the node to check.
     * @return The node from the close list if it exists, null otherwise.
     */
    Nodes isInCloseList(int row, int col) {
        for (Nodes n : close_list) {
            if (n.getRow() == row && n.getColumn() == col) {
                return n;
            }
        }
        return null;
    }

    /**
     * Finds the node with the minimum 'f' cost in the open list.
     *
     * @return The index of the node with the lowest 'f' cost.
     */
    int findMinFNode() {
        float min = 999999999;
        int index = 0;
        for (int i = 0; i < open_list.size(); i++) {
            if (open_list.get(i).getF() < min) {
                min = open_list.get(i).getF();
                index = i;
            }
        }
        return index;
    }
    /**
     * Calculates the grid distance between two nodes.
     *
     * @param a The first node.
     * @param b The second node.
     * @return The grid distance between the two nodes.
     */
    static float grid_distance(Nodes a, Nodes b)
    {
        int dst_x = Math.abs(a.getColumn() - b.getColumn());
        int dst_y = Math.abs(a.getRow() - b.getRow());

        if (dst_x > dst_y)
        {
            return 5 * dst_y + 3 * (dst_x - dst_y);
        }
        return 5 * dst_x + 3 * (dst_y - dst_x);

    }

    /**
     * Explores the neighbors of a given nodes, updating the open list with new nodes to explore.
     *
     * @param nodes The nodes whose neighbors will be explored.
     */
    void exploreNeighbour(Nodes nodes) {
        int dir_row[] = { -1, 1, 0, 0 };
        int dir_col[] = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int new_row = dir_row[i] + nodes.getRow();
            int new_col = dir_col[i] + nodes.getColumn();

            if (new_row > maps.getNum_Of_Rows() - 1 || new_row < 0 || new_col > maps.getNum_Of_Column() - 1 || new_col < 0) {
                continue;
            }
            if (maps.getCell(new_row,new_col).getBlocksType() == BlockType.WALL) {
                continue;
            }
            if (isInCloseList(new_row, new_col) != null) {
                continue;
            }

            Nodes new_nodes = new Nodes(new_row, new_col, nodes);
            if (isInOpenList(new_nodes) != null) {
                new_nodes = isInOpenList(new_nodes);
            }

            float path_to_this_neighbour = nodes.getG() + 1;

            if (path_to_this_neighbour < new_nodes.getG() || isInOpenList(new_nodes) == null) {
                // is mud

                new_nodes.setG(path_to_this_neighbour);
                new_nodes.setH(grid_distance(new_nodes, endNodes));

                //graphData->changeType(new_nodes->getRow(), new_nodes->getCol(), 1);
                if (isInOpenList(new_nodes) == null) {
                    open_list.add(new_nodes);
                }
            }
        }

    }

    /**
     * Constructs the path from the end nodes to the start nodes by backtracking from the end nodes.
     *
     * @param nodes The end nodes of the path.
     * @return A list of Vector2 representing the path from start to end.
     */
    List<Vector2> showPath(Nodes nodes) {
        Nodes tmp = nodes;
        List<Nodes> trace_path = new ArrayList<>();
        List<Vector2> path = new ArrayList<>();
        while (tmp != null)
        {
            trace_path.add(tmp);
            tmp = tmp.getParent();
        }
        for (int i = trace_path.size() - 1; i >= 0; i--) {
            Nodes n = trace_path.get(i);
            path.add(new Vector2(n.getColumn(), n.getRow()));
        }

        if(!path.isEmpty()){
            path.remove(0);
        }

        return path;
    }

    /**
     * Finds the shortest path from a start position to an end position using the A* algorithm.
     *
     * @param col   The column of the start position.
     * @param row   The row of the start position.
     * @param toCol The column of the end position.
     * @param toRow The row of the end position.
     * @return A list of Vector2 representing the shortest path found, or an empty list if no path is found.
     */
    public List<Vector2> findPath(int col,int row, int toCol,int toRow) {
        startNodes = new Nodes(row,col,null);
        endNodes = new Nodes(toRow,toCol,null);

        open_list = new ArrayList<>();
        close_list = new ArrayList<>();

        open_list.add(startNodes);
        while (!open_list.isEmpty()) {

            //Sleep(1);
            int i_min = findMinFNode();
            Nodes currentNodes = open_list.get(i_min);


            close_list.add(currentNodes);
            if (currentNodes.equal(endNodes)) {
                return showPath(currentNodes);
            }

            open_list.remove(i_min);
            exploreNeighbour(currentNodes);
        }
        return new ArrayList<>();
    }

}