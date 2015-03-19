package com.polarbirds.darkness.graphics;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by Kristian Rekstad on 10.03.2015.
 * http://blog.xoppa.com/creating-a-shader-with-libgdx/
 */
public class NormalMapShader extends DefaultShader {

    public NormalMapShader(Renderable renderable) {
        super(renderable);
        this.program = new ShaderProgram(Shaders.normal_map_shader.getVert(), Shaders.normal_map_shader.getFrag());
    }



    @Override
    public boolean canRender(Renderable renderable) {
        return true; // huehue
    }
}
