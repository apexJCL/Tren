package com.ariel.Stages;

import com.ariel.Config;
import com.ariel.Managers.SceneManager;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by apex on 12/8/15.
 */
public class ConfigMenu extends Stage {

    private final SceneManager manager;
    private Table UI;

    public ConfigMenu(SceneManager manager, SceneLoader sceneLoader){
        // Guardamos referencia para manejar la escena
        this.manager = manager;
        UI = new Table();
        UI.top().center().setFillParent(true);
        // definimos que funge como entrada
        Gdx.input.setInputProcessor(this);
        // Aquí cargamos el actor que controla el tipo de entrada
        CompositeItemVO input_type = sceneLoader.loadVoFromLibrary("input_type");
        CompositeActor input_type_actor = new CompositeActor(input_type, sceneLoader.getRm());
        final Label input_text = (Label) input_type_actor.getItem("text");
        // Le definimos el tipo de entrada que esté previamente configurado
        input_text.setText(Config.Load().getInputTypeText());
        input_type_actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Config cfg = Config.Load();
                cfg.switchInput();
                Config.Save(cfg);
                input_text.setText(cfg.getInputTypeText());
            }
        });
        // Agregando los actores a la escena
        UI.add(input_type_actor).center().top().expandY().pad(10f);
        addActor(UI);
    }

    private class botonAtras implements IScript{

        @Override
        public void init(Entity entity) {
            addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    manager.changeScene(SceneManager.State.MENU);
                }
            });
        }

        @Override
        public void act(float delta) {

        }

        @Override
        public void dispose() {

        }
    }

}
