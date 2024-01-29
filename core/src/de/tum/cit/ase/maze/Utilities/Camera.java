package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.GameComponents.Maps;

/**
 * This class represents a camera that follows a target in the game world.
 * It ensures the camera stays within the bounds of the game map.
 */
public class Camera {
    OrthographicCamera camera;
    Maps maps;

    /**
     * Constructor for Camera. Initializes the camera and sets its zoom level.
     *
     * @param camera The OrthographicCamera instance to be used.
     * @param maps   The game map the camera will be operating within.
     */
    public Camera(OrthographicCamera camera, Maps maps) {
        this.camera = camera;
        this.maps = maps;
        camera.zoom = 0.2f; // Set the initial zoom level of the camera
    }

    /**
     * Updates the camera's position to follow the target while staying within the map's bounds.
     *
     * @param target The target position for the camera to follow.
     */
    public void follow(Vector2 target) {
        camera.position.x = target.x;
        camera.position.y = target.y;
        float X_center = camera.viewportWidth * camera.zoom * 0.5f;
        float Y_center = camera.viewportHeight * camera.zoom * 0.5f;

        // Keep the camera within the vertical bounds of the map
        if (camera.position.y < Y_center) {
            camera.position.y = Y_center;
        } else if (camera.position.y > (maps.getNum_Of_Rows() * 16 - Y_center)) {
            camera.position.y = (maps.getNum_Of_Rows() * 16 - Y_center);
        }

        // Keep the camera within the horizontal bounds of the map
        if (camera.position.x < X_center) {
            camera.position.x = X_center;
        } else if (camera.position.x > maps.getNum_Of_Column() * 16 - X_center) {
            camera.position.x = maps.getNum_Of_Column() * 16 - X_center;
        }
    }
}