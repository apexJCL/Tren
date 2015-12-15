package com.ariel;

import com.ariel.Managers.SaveStateManager;
import com.ariel.Managers.SceneManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Json;
import com.kotcrab.vis.ui.VisUI;

public class MainGame extends ApplicationAdapter {

    private ActionResolver resolver;
    private SceneManager sceneManager;

    public MainGame(ActionResolver resolver){
        this.resolver = resolver;
    }

    public MainGame(){

    }

    @Override
	public void create () {

        // Cargamos VisUI
        VisUI.load();
        configLoader();
        // Creamos el manejador de escenas
        sceneManager = new SceneManager(resolver);
    }

    private void configLoader() {
        String prefs = Gdx.app.getPreferences(Config.prefName).getString(Config.prefId);
        if(prefs == null || prefs.length() <= 0){
            Preferences preferences = Gdx.app.getPreferences(Config.prefName);
            Config cfg = new Config();
            preferences.putString(Config.prefId, new Json().prettyPrint(cfg));
            preferences.flush();
        }
        String savestate = Gdx.app.getPreferences(Config.prefName).getString(SaveStateManager.prefName);
        if (savestate == null || savestate.length() <= 0) {
            SaveStateManager saveState = new SaveStateManager();
            Preferences preferences = Gdx.app.getPreferences(Config.prefName);
            preferences.putString(SaveStateManager.prefName, new Json().prettyPrint(saveState));
            preferences.flush();
        }
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sceneManager.render(Gdx.graphics.getDeltaTime());
    }
}
