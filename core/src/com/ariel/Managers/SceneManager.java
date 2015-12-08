package com.ariel.Managers;

import com.ariel.Config;
import com.ariel.Scenes.BaseSceneConfig;
import com.ariel.Scenes.GameScene;
import com.ariel.Scenes.MenuScene;
import com.ariel.Stages.HUD;
import com.ariel.Stages.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Esta clase maneja las escenas y de cada escena maneja la creacion/carga de objetos/scripts
 */
public class SceneManager {

    private BaseSceneConfig actualScene;
    private final FitViewport viewport;
    private SceneLoader sceneLoader;
    private Stage stage;

    public SceneManager(){
        viewport = new FitViewport(9, 16);
        // Este se encarga de cargar las escenas
        sceneLoader = new SceneLoader();
        // Instanciamos el configurador de escena
        actualScene = new MenuScene("Menu", sceneLoader, viewport);
        // Le indicamos que cargue la escena
        actualScene.Load();
        // Cargamos el stage de stage
        stage = new Menu(this, sceneLoader);
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
        MENU, GAMEPLAY
    }

    public void changeScene(State scene){
        switch (scene){
            case MENU:
                break;
            case GAMEPLAY:
                // Cargamos el HUD antes que la escena, ya que aqui se ocupa acceder a cosas del mismo
                stage = new HUD(sceneLoader);
                // Cambiamos la escena
                actualScene = new GameScene(SaveStateManager.Load().getActualLevel(), sceneLoader, viewport, stage);
                // cargamos la escena
                actualScene.Load();
                break;
        }
    }

    public FitViewport getViewport() { return viewport; }

    public SceneLoader getSceneLoader() { return sceneLoader; }

}
