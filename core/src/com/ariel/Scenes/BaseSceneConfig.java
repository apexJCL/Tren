package com.ariel.Scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Created by apex on 11/28/15.
 */
public class BaseSceneConfig {

    protected SceneLoader sceneLoader;
    protected Viewport viewport;
    private String scenename;
    protected Stage stage;

    /**
     * Define una escena normal
     * @param sceneName
     * @param sceneLoader
     * @param viewport
     */
    public BaseSceneConfig(String sceneName, SceneLoader sceneLoader, Viewport viewport){
        this.scenename = sceneName;
        this.sceneLoader = sceneLoader;
        this.viewport = viewport;
    }

    /**
     * Define una escena que cuenta con un UI
     * @param sceneName
     * @param sceneLoader
     * @param viewport
     * @param uiStage
     */
    public BaseSceneConfig(String sceneName, SceneLoader sceneLoader, Viewport viewport, Stage uiStage){
        this.scenename = sceneName;
        this.sceneLoader = sceneLoader;
        this.viewport = viewport;
        this.stage = uiStage;
    }

    public void Load(){
        sceneLoader.loadScene(scenename, viewport);
        // Este metodo debe sobreescribirse para cargar los scripts pertinentes de cada escena/entidad
        addScripts();
    }

    public void addScripts(){

    }
}
