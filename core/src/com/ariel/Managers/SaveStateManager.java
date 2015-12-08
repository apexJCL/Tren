package com.ariel.Managers;

import com.ariel.Config;
import com.ariel.Scenes.GameScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import static com.ariel.Scenes.GameScene.LEVEL;

public class SaveStateManager {

    public static String prefName = "Savegame";
    private String actualLevel = "MainScene";
    private LEVEL actual = LEVEL.L1;

    public static SaveStateManager Load(){
        return new Json().fromJson(SaveStateManager.class, Gdx.app.getPreferences(Config.getName()).getString(prefName));
    }

    public LEVEL getLevel() {
        return actual;
    }

    public String getActualLevel(){
        switch (actual){
            case L1:
                return "MainScene";
            case L2:
                return "Nivel2";
        }
        return "Menu";
    }
}
