package com.ariel.Stages;

import com.ariel.ActionResolver;
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

import javax.swing.*;

/**
 * Esta clase alberga los menues
 */
public class Menu extends Stage {

    private static CompositeActor play_button;
    private static CompositeActor connect_button;
    private final CompositeActor cfg_button;
    private static SceneManager manager;
    private static ActionResolver resolver;

    public Menu(final SceneManager manager, SceneLoader sceneLoader, ActionResolver resolver){
        super(new FitViewport(720, 1280));
        // Guardamos el resolver
        this.resolver = resolver;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(false);
        // Guardamos la referencia al manager
        this.manager = manager;
        // Se obtiene el boton
        CompositeItemVO button1 = sceneLoader.loadVoFromLibrary("play_btn");
        // Instanciamos botones
        play_button = new CompositeActor(button1, sceneLoader.getRm());
        cfg_button = new CompositeActor(sceneLoader.loadVoFromLibrary("cfg_btn"), sceneLoader.getRm());
        connect_button = new CompositeActor(sceneLoader.loadVoFromLibrary("multiplayer"), sceneLoader.getRm());
        play_button.addListener(new MenuListener(MenuListener.Button.PLAY, play_button));
        connect_button.addListener(new MenuListener(MenuListener.Button.CONNECT, connect_button));
        Table menu = new Table();
        menu.setFillParent(true);
        menu.add(play_button).center().pad(5f).row();
        menu.add(cfg_button).center().pad(5f).row();
        menu.add(connect_button).center().pad(5f).row();
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
                case CONNECT:
                    switch (Gdx.app.getType()){
                        case Android:
                            resolver.showToast("Conectando...");
                            resolver.showToast("Error en la conexión, revise su firewall.");
                            break;
                        case HeadlessDesktop:
                        case Desktop:
                            new Runnable(){
                                @Override
                                public void run() {
                                    JOptionPane.showMessageDialog(null, "Conexión fallida, revise su firewall");
                                }
                            }.run();
                            break;
                    }
                    break;
            }
        }

        public enum Button{
            PLAY, CONFIG, CONNECT
        }
    }

}
