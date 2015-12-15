package com.ariel.Managers;

import com.ariel.ActionResolver;
import com.ariel.Scenes.BaseSceneConfig;
import com.ariel.Scenes.ConfigScene;
import com.ariel.Scenes.GameScene;
import com.ariel.Scenes.MenuScene;
import com.ariel.Stages.ConfigMenu;
import com.ariel.Stages.HUD;
import com.ariel.Stages.Menu;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Esta clase maneja las escenas y de cada escena maneja la creacion/carga de objetos/scripts
 */
public class SceneManager {

    private ActionResolver resolver;
    private BaseSceneConfig actualScene;
    private final FitViewport viewport;
    private SceneLoader sceneLoader;
    private Stage stage;

    public SceneManager(ActionResolver resolver){
        // Guardamos el resolver
        this.resolver = resolver;
        viewport = new FitViewport(9, 16);
        // Este se encarga de cargar las escenas
        sceneLoader = new SceneLoader();
        // Instanciamos el configurador de escena
        actualScene = new MenuScene("Menu", sceneLoader, viewport);
        // Le indicamos que cargue la escena
        actualScene.Load();
        // Cargamos el stage de stage
        stage = new Menu(this, sceneLoader, resolver);
    }

    /**
     * Aqui se renderizan las cosas, primero llamando el motor, despues el stage, que por lo usual
     * es el que contendra la GUI/estatico
     * @param delta
     */
    public void render(float delta){
        sceneLoader.getEngine().update(delta);
        stage.act();
        stage.draw();
    }

    /**
     * Enumeradores para controlar las escenas
     */
    public enum State{
        MENU, GAMEPLAY, CONFIG
    }

    public void changeScene(State scene){
        switch (scene){
            case MENU:
                actualScene = new MenuScene("Menu", sceneLoader, viewport);
                // Cargamos el stage de stage
                stage = new Menu(this, sceneLoader, resolver);
                break;
            case GAMEPLAY:
                // Cargamos el HUD antes que la escena, ya que aqui se ocupa acceder a cosas del mismo
                stage = new HUD(sceneLoader, this);
                // Cambiamos la escena
                actualScene = new GameScene(SaveStateManager.Load().getActualLevel(), sceneLoader, viewport, stage);
                break;
            case CONFIG:
                // Cargamos la escena
                actualScene = new ConfigScene("ConfigMenu", sceneLoader, viewport);
                // Definimos el nuevo stage
                stage = new ConfigMenu(this, sceneLoader);
                break;
        }
        // Le indicamos que cargue la escena
        actualScene.Load();
    }

    public FitViewport getViewport() { return viewport; }

    public SceneLoader getSceneLoader() { return sceneLoader; }

}
