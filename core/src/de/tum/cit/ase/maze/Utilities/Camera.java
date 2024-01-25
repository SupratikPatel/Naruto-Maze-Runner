package de.tum.cit.ase.maze.Utilities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.GameComponents.Maps;

public class Camera {
    OrthographicCamera camera;
    Maps maps;


    public Camera(OrthographicCamera camera, Maps maps) {
        this.camera = camera;
        this.maps=maps;
        camera.zoom=0.5f;
    }
    public void follow(Vector2 target){
        camera.position.x= target.x;
        camera.position.y= target.y;
        float X_center= camera.viewportWidth* camera.zoom*0.5f;
        float Y_center= camera.viewportHeight* camera.zoom*0.5f;

        if(camera.position.y<Y_center){
            camera.position.y=Y_center;
        } else if (camera.position.y > (maps.getRows()*16 -Y_center)){
            camera.position.y= (maps.getRows()*16 - Y_center);
        }
        if (camera.position.x<X_center){
            camera.position.x=X_center;
        } else if (camera.position.x > maps.getColumns()*16 - X_center){
            camera.position.x=maps.getColumns()*16 - X_center;
        }
    }
}
