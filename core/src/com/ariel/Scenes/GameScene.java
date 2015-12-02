package com.ariel.Scenes;

import com.ariel.IScripts.Tren;
import com.ariel.Stages.HUD;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by apex on 11/28/15.
 */
public class GameScene extends BaseSceneConfig {

    public GameScene(String sceneName, SceneLoader sceneLoader, Viewport viewport, Stage uiStage) {
        super(sceneName, sceneLoader, viewport, uiStage);
    }

    @Override
    public void addScripts() {
        super.addScripts();
        // Cargamos el script del tren
        Tren tren = new Tren((OrthographicCamera)viewport.getCamera(),(HUD)stage);
        // Se le asigna
        rootWrapper.getChild("tren").addScript(tren);
    }
}
