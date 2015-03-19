package com.polarbirds.darkness.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
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

    protected float yAxisDegrees = 0;
    protected float xAxisDegrees = 0;

    protected float headBob = 0;

    protected final Vector3 tmp = new Vector3();
    protected final Vector3 tmp2 = new Vector3();
    protected final Quaternion yAxisRotation = new Quaternion();
    protected final Quaternion rightAxisRotation = new Quaternion();



    public FPSCameraController(Camera camera) {
        this.camera = camera;
    }

    public void update(float deltaTime){
        tmp.set(0f,0f,0f);
        if (keys.containsKey(FORWARD)) {
            tmp2.set(camera.direction).y=0;
            tmp2.nor();
            tmp.add(tmp2);
        }
        if (keys.containsKey(BACKWARD)) {
            tmp2.set(camera.direction).y=0;
            tmp2.nor();
            tmp.sub(tmp2);
        }
        if (keys.containsKey(STRAFE_LEFT)) {
            tmp2.set(camera.direction).crs(camera.up).y=0;
            tmp2.nor();
            tmp.sub(tmp2);
        }
        if (keys.containsKey(STRAFE_RIGHT)) {
            tmp2.set(camera.direction).crs(camera.up).y=0;
            tmp2.nor();
            tmp.add(tmp2);
        }
        tmp.nor().scl(deltaTime*velocity);
        camera.position.add(tmp);
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


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float dx = -Gdx.input.getDeltaX()*degreesPerPixel;
        float dy = -Gdx.input.getDeltaY()*degreesPerPixel; // dy > 0 means drag up

        /*xAxisDegrees += dy;
        yAxisDegrees += dx;

        xAxisDegrees = MathUtils.clamp(xAxisDegrees, -89f, 89f);

        if (yAxisDegrees >= 360){
            yAxisDegrees -= 360;
        } else if (yAxisDegrees <= -360){
            yAxisDegrees += 360;
        }*/

        strat1(dx, dy);

        return true;
    }

    private void strat1(float dx, float dy){
        tmp.set(camera.direction).crs(camera.up).nor();

        float currentY = camera.direction.y *90;
        if (Math.abs(currentY+dy) > 89){
            dy = Math.signum(currentY)*89-currentY;
        }

        camera.direction.rotate(tmp, dy).nor();
        camera.up.rotate(tmp, dy).nor();

        camera.direction.rotate(Vector3.Y, dx).nor();
        camera.up.rotate(Vector3.Y, dx).nor();

    }

    private void strat2(float dx, float dy){
        tmp.set(camera.direction).crs(Vector3.Y).y=0;

        if (tmp.len2() <= 0.01f && (camera.direction.y>0 && dy>0) || (camera.direction.y<0 && dy<0)){
            // parallell direction to up axis.
            return;
        }

        tmp.nor(); // right axis

        camera.direction.rotate(tmp, dy);
        camera.direction.rotate(Vector3.Y, dx);

        camera.up.set(camera.direction).crs(Vector3.Y).y=0;
        camera.up.crs(camera.direction).nor();
    }


    private void strat3(){
        // remember unit quaternions when rotating!!!!1!1! :pPpPP
        // q = q2 * q1 , q1 followed by q2

        /*
        Reset mouse coordinates to center-of-screen on each frame, so that mouse never gets caught on the screen borders
        Maintain the camera's "up" vector (disallow roll) and recompute the "sideways" vector
        Disallow looking up past the vertical +y axis, or down past the -y axis (too far up/down)
        Get the order of rotations correct (up/down first, then left/right)
        Renormalize the "up", "aim", and "sideways" vectors each frame
         */

        // NOT FULLY IMPLEMENTED.
        tmp.set(camera.direction).crs(camera.up);
        rightAxisRotation.setEulerAngles(yAxisDegrees, xAxisDegrees, 0);
        //camera.direction.rot
        //camera.direction.rotate(tmp, )
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }
}
