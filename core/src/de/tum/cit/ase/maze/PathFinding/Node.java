package de.tum.cit.ase.maze.PathFinding;

/**
 * Represents a node in a grid-based map for pathfinding.
 * This class is used by the A* algorithm to store the cost values and parent node for each node in the path.
 */
public class Node {
    float g, h; // The 'g' and 'h' cost values for the A* algorithm.
    Node parent; // The parent node in the path.
    int Row, Column; // The row and column of the node in the grid.
    float distance; // The distance from the start node.

    /**
     * Constructs a new Node with a specified row, column, and parent node.
     *
     * @param row    The row of the node in the grid.
     * @param Column The column of the node in the grid.
     * @param parent The parent node in the path.
     */
    public Node(int row, int Column, Node parent) {
        this.Row = row;
        this.Column = Column;
        this.parent = parent;

        g = 0;
        h = 0;
        distance = Integer.MAX_VALUE;
    }

    /**
     * Returns the 'f' cost value, which is the sum of the 'g' and 'h' cost values.
     *
     * @return The 'f' cost value.
     */
    public float getF() {
        return g + h;
    }

    /**
     * Returns the 'g' cost value, which is the cost from the start node to this node.
     *
     * @return The 'g' cost value.
     */
    public float getG() {
        return g;
    }

    /**
     * Sets the 'g' cost value.
     *
     * @param g The new 'g' cost value.
     */
    public void setG(float g) {
        this.g = g;
    }

    /**
     * Sets the 'h' cost value.
     *
     * @param h The new 'h' cost value.
     */
    public void setH(float h) {
        this.h = h;
    }

    /**
     * Gets the parent node in the path.
     *
     * @return The parent node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Gets the row of the node in the grid.
     *
     * @return The row of the node.
     */
    public int getRow() {
        return Row;
    }

    /**
     * Gets the column of the node in the grid.
     *
     * @return The column of the node.
     */
    public int getColumn() {
        return Column;
    }

    /**
     * Checks if two nodes are equal based on their row and column.
     *
     * @param n The node to compare.
     * @return True if the nodes are equal, false otherwise.
     */
    public boolean equal(Node n) {
        return n.Row == Row && n.Column == Column;
    }
}