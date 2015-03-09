package com.polarbirds.darkness.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

/**
 * Created by Kristian Rekstad on 07.03.2015.
 * Based on {@link com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController}, but it has private fields
 * so it can not be extended from.
 */
public class FPSCameraController extends InputAdapter {

    protected final Camera camera;
    protected final IntIntMap keys = new IntIntMap();
    protected int STRAFE_LEFT = Input.Keys.A;
    protected int STRAFE_RIGHT = Input.Keys.D;
    protected int FORWARD = Input.Keys.W;
    protected int BACKWARD = Input.Keys.S;
    public float velocity = 5;
    public float degreesPerPixel = 0.5f;

    protected final Vector3 tmp = new Vector3();
    protected final Quaternion yAxisRotation = new Quaternion();
    protected final Quaternion rightAxisRotation = new Quaternion();



    public FPSCameraController(Camera camera) {
        this.camera = camera;
    }

    public void update(float deltaTime){
        if (keys.containsKey(FORWARD)) {
            tmp.set(camera.direction).nor().scl(deltaTime * velocity);
            camera.position.add(tmp);
        }
        if (keys.containsKey(BACKWARD)) {
            tmp.set(camera.direction).nor().scl(-deltaTime * velocity);
            camera.position.add(tmp);
        }
        if (keys.containsKey(STRAFE_LEFT)) {
            tmp.set(camera.direction).crs(camera.up).nor().scl(-deltaTime * velocity);
            camera.position.add(tmp);
        }
        if (keys.containsKey(STRAFE_RIGHT)) {
            tmp.set(camera.direction).crs(camera.up).nor().scl(deltaTime * velocity);
            camera.position.add(tmp);
        }
        //camera.update(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        keys.put(keycode, keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys.remove(keycode, 0);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }


    Matrix4 mat = new Matrix4();
    Matrix4 tmpMat = new Matrix4();

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float dx = -Gdx.input.getDeltaX()*degreesPerPixel;
        float dy = -Gdx.input.getDeltaY()*degreesPerPixel;


        //yAxisRotation.set(Vector3.Y, dx * degreesPerPixel).nor();
        //tmp.set(camera.direction).crs(camera.up).nor();

        //rightAxisRotation.set(tmp, dy * degreesPerPixel).nor();

        //tmp.set(camera.position);
        //camera.position.setZero();

        camera.direction.rotate(Vector3.Y, dx);
        camera.up.rotate(Vector3.Y, dx);

        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, dy);
        camera.up.rotate(tmp, dy);
        //camera.rotate(rightAxisRotation);
        //camera.rotate(yAxisRotation);
        //camera.translate(tmp);

        tmp.set(camera.direction).crs(camera.up);
        System.out.println("Right: "+tmp);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }
}
