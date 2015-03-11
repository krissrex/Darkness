package com.polarbirds.darkness.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

/**
 * Created by Kristian Rekstad on 10.03.2015.
 * http://blog.xoppa.com/creating-a-shader-with-libgdx/
 */
public class NormalMapShader implements Shader {
    @Override
    public void init() {

    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext context) {

    }

    @Override
    public void render(Renderable renderable) {

    }

    @Override
    public void end() {

    }

    @Override
    public void dispose() {

    }
}
