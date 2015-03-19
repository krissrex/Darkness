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

            ShaderProgram.pedantic = false;
            if (!normal_map_shader.normal_map.isCompiled()){
                Gdx.app.log("Shaders", "Failed to compile normal shader: "+normal_map_shader.normal_map.getLog());
            }
            if (!default_shader.default_shader.isCompiled()){
                Gdx.app.log("Shaders", "Failed to compile default shader: "+default_shader.default_shader.getLog());
            }

        }
    }

    private static String readString(String shaderFileName){
        return Gdx.files.internal("shader/"+shaderFileName).readString();
    }

    private static void load(){
        default_shader.vert = readString("default_shader.vert");
        default_shader.frag = readString("default_shader.frag");
        default_shader.default_shader = new ShaderProgram(default_shader.vert, default_shader.frag);

        normal_map_shader.vert = readString("xoppa_uber.vert"); //readString("normal_map.vert");
        normal_map_shader.frag = readString("xoppa_uber.frag"); //readString("normal_map.frag");
        normal_map_shader.normal_map = new ShaderProgram(normal_map_shader.vert, normal_map_shader.frag);
    }

    public static class default_shader {
        public static ShaderProgram default_shader;
        protected static String vert;
        protected static String frag;

        public static String getFrag() {return frag;}
        public static String getVert() {return vert;}
    }

    public static class normal_map_shader {
        public static ShaderProgram normal_map;
        protected static String vert;
        protected static String frag;

        public static String getFrag() {return frag;}
        public static String getVert(){return vert;}
    }
}
