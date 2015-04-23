package com.polarbirds.darkness.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class Assets {
    private Assets(){}

    public static final class texture {
        public static final String badlogic = "badlogic.jpg";
    }

    public static final class model {
        //public static final String player = "";
        public static final String weapon_teslaGun = "3d/weapons/Tesla rifle.g3db";
        public static final String debugEnemy = "3d/enemies/testEnemy.g3db";

        //Map
        public static final String map_I = "3d/map/I/Tunnel I -y y.g3db";
        public static final String map_L = "3d/map/L/Tunnel L -y x.g3db";
        public static final String map_T = "3d/map/T/Tunnel T x -y y.g3db";
        public static final String map_X = "3d/map/X/Tunnel X -y y -x x.g3db";

        public static final String map_room = "3d/map/Room_start/Room 5y 5-y 5-x 4x open x.g3db";


        // Util
        public final static void loadAll(AssetManager manager) {
            List<String> all = Arrays.asList(weapon_teslaGun, debugEnemy,
                    map_I, map_L, map_T, map_X,
                    map_room);

            for (String model : all) {
                manager.load(model, Model.class);
            }
        }
    }



}
