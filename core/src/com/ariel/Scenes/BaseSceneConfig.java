package com.ariel.Scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by apex on 11/28/15.
 */
public class BaseSceneConfig {

    protected SceneLoader sceneLoader;
    protected Viewport viewport;
    protected Stage stage;
    protected ItemWrapper rootWrapper;
    private String scenename;

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
        this.sceneLoader = sceneLoader;
        this.scenename = sceneName;
        this.viewport = viewport;
        this.stage = uiStage;
    }

    /**
     * Este metodo debe llamarse antes de escribir scripts para que se defina la raiz de la escena
     */
    public void loadWrapper(){
        // Se obtiene la raiz para obtener al jugador y asignarle sus datos
        this.rootWrapper = new ItemWrapper(sceneLoader.getRoot());
    }

    public void Load(){
        sceneLoader.loadScene(scenename, viewport);
        // Este metodo debe sobreescribirse para cargar los scripts pertinentes de cada escena/entidad
        addScripts();
    }

    /**
     * Este metodo se debe sobreescribir para asignar los scripts
     * Use rootWrapper para obtener un hijo, use sceneLoader para obtener un objeto compuesto
     */
    public void addScripts(){
        loadWrapper();
    }

    public void manageCompositeItems(){

    }
}
