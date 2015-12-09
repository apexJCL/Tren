package com.ariel.Stages;

import com.ariel.Managers.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

/**
 * Esta clase alberga los menues
 */
public class Menu extends Stage {

    private static CompositeActor play_button;
    private final CompositeActor cfg_button;
    private static SceneManager manager;

    public Menu(final SceneManager manager, SceneLoader sceneLoader){
        super(new FitViewport(720, 1280));
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(false);
        // Guardamos la referencia al manager
        this.manager = manager;
        // Se obtiene el boton
        CompositeItemVO button1 = sceneLoader.loadVoFromLibrary("play_btn");
        // Instanciamos botones
        play_button = new CompositeActor(button1, sceneLoader.getRm());
        cfg_button = new CompositeActor(sceneLoader.loadVoFromLibrary("cfg_btn"), sceneLoader.getRm());
        play_button.addListener(new MenuListener(MenuListener.Button.PLAY, play_button));
        cfg_button.addListener(new MenuListener(MenuListener.Button.CONFIG, cfg_button));
        Table menu = new Table();
        menu.setFillParent(true);
        menu.add(play_button).center().pad(5f).row();
        menu.add(cfg_button).center().pad(5f).row();
        addActor(menu);
    }

    private static class MenuListener extends ClickListener{

        private Button type;
        private CompositeActor actor;

        public MenuListener(Button type, CompositeActor actor) {
            this.type = type;
            this.actor = actor;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            actor.setLayerVisibility("pressed", true);
            switch (type){
                case PLAY:
                    break;
                case CONFIG:
                    break;
            }
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            actor.setLayerVisibility("pressed", false);
            switch (type){
                case PLAY:
                    manager.changeScene(SceneManager.State.GAMEPLAY);
                    break;
                case CONFIG:
                    manager.changeScene(SceneManager.State.CONFIG);
                    break;
            }
        }

        public enum Button{
            PLAY, CONFIG
        }
    }

}
