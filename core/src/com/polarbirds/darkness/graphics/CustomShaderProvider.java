package com.polarbirds.darkness.graphics;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;

/**
 * Created by Kristian Rekstad on 19.03.2015.
 */
public class CustomShaderProvider extends DefaultShaderProvider {

    public CustomShaderProvider() {
        super();
    }

    @Override
    protected Shader createShader(Renderable renderable) {
        if (renderable.material.has(TextureAttribute.Normal)){
            return new NormalMapShader(renderable);
        } else {
            return super.createShader(renderable);
        }
    }
}
