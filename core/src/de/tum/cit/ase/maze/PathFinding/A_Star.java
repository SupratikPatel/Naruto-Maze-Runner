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
    List<Node> open_list; // The set of nodes to be evaluated.
    List<Node> close_list; // The set of nodes already evaluated.
    Maps maps; // The game map.

    Node startNode; // The starting node of the path.
    Node endNode; // The ending node of the path.

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
     * Checks if a node is in the open list.
     *
     * @param node The node to check.
     * @return The node from the open list if it exists, null otherwise.
     */
    Node isInOpenList(Node node) {
        for (Node n : open_list) {
            if (n.equal(node)) {
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
    Node isInCloseList(int row, int col) {
        for (Node n : close_list) {
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
    static float grid_distance(Node a, Node b)
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
     * Explores the neighbors of a given node, updating the open list with new node to explore.
     *
     * @param node The node whose neighbors will be explored.
     */
    void exploreNeightbour(Node node) {
        int dir_row[] = { -1, 1, 0, 0 };
        int dir_col[] = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int new_row = dir_row[i] + node.getRow();
            int new_col = dir_col[i] + node.getColumn();

            if (new_row > maps.getNum_Of_Rows() - 1 || new_row < 0 || new_col > maps.getNum_Of_Column() - 1 || new_col < 0) {
                continue;
            }
            if (maps.getCell(new_row,new_col).getBlocksType() == BlockType.WALL) {
                continue;
            }
            if (isInCloseList(new_row, new_col) != null) {
                continue;
            }

            Node new_node = new Node(new_row, new_col, node);
            if (isInOpenList(new_node) != null) {
                new_node = isInOpenList(new_node);
            }

            float path_to_this_neighbour = node.getG() + 1;

            if (path_to_this_neighbour < new_node.getG() || isInOpenList(new_node) == null) {
                // is mud

                new_node.setG(path_to_this_neighbour);
                new_node.setH(grid_distance(new_node, endNode));

                //graphData->changeType(new_node->getRow(), new_node->getCol(), 1);
                if (isInOpenList(new_node) == null) {
                    open_list.add(new_node);
                }
            }
        }

    }

    /**
     * Constructs the path from the end node to the start node by backtracking from the end node.
     *
     * @param node The end node of the path.
     * @return A list of Vector2 representing the path from start to end.
     */
    List<Vector2> showPath(Node node) {
        Node tmp = node;
        List<Node> trace_path = new ArrayList<>();
        List<Vector2> path = new ArrayList<>();
        while (tmp != null)
        {
            trace_path.add(tmp);
            tmp = tmp.getParent();
        }
        for (int i = trace_path.size() - 1; i >= 0; i--) {
            Node n = trace_path.get(i);
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
        startNode = new Node(row,col,null);
        endNode = new Node(toRow,toCol,null);

        open_list = new ArrayList<>();
        close_list = new ArrayList<>();

        open_list.add(startNode);
        while (!open_list.isEmpty()) {

            //Sleep(1);
            int i_min = findMinFNode();
            Node currentNode = open_list.get(i_min);


            close_list.add(currentNode);
            if (currentNode.equal(endNode)) {
                return showPath(currentNode);
            }

            open_list.remove(i_min);
            exploreNeightbour(currentNode);
        }
        return new ArrayList<>();
    }

}