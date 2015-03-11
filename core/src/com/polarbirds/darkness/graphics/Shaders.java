package com.polarbirds.darkness.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by Kristian Rekstad on 10.03.2015.
 */
public class Shaders {
    private static boolean hasInitialized = false;
    public static void init(){
        if (!hasInitialized){
            load();
            hasInitialized = true;
        }
    }


    private static void load(){
        normal_map_shader.vert = Gdx.files.internal("shader/normal_map.vert").readString();
        normal_map_shader.frag = Gdx.files.internal("shader/normal_map.frag").readString();
        normal_map_shader.normal_map = new ShaderProgram(normal_map_shader.vert, normal_map_shader.frag);
    }

    public static class normal_map_shader {
        public static ShaderProgram normal_map;
        protected static String vert;
        protected static String frag;

        public static String getFrag() {return frag;}
        public static String getVert(){return vert;}
    }
}
