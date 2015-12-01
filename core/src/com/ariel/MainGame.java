package com.ariel;

import com.ariel.Managers.SceneManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Json;

public class MainGame extends ApplicationAdapter {

    private SceneManager sceneManager;

    @Override
	public void create () {
        String prefs = Gdx.app.getPreferences(Config.prefName).getString(Config.prefId);
        if(prefs == null || prefs.length() <= 0){
            Preferences preferences = Gdx.app.getPreferences(Config.prefName);
            Config cfg = new Config();
            preferences.putString(Config.prefId, new Json().prettyPrint(cfg));
            preferences.flush();
        }
        // Creamos el manejador de escenas
        sceneManager = new SceneManager();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sceneManager.render(Gdx.graphics.getDeltaTime());
    }
}