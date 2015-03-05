package com.polarbirds.darkness.graphics;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public interface RenderableObject {

    /** Do not call begin or end inside the call.*/
    public void render(ModelBatch batch);
}
