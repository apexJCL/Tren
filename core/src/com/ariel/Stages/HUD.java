package com.ariel.Stages;

import com.ariel.Config;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

/**
 * Esta clase alberga el HUD del jugador, el cual contiene sus datos (por ahora solo su distancia recorrida)
 */
public class HUD extends Stage {

    private final Table UI;
    private CompositeActor panel_distancia, joystick, panel_tiempo;
    private Label valor, min, sec, millis;


    public HUD(SceneLoader sceneLoader){
        super(new FitViewport(720, 1280)); // Inicializamos para que se adapte igualmente a la pantalla
        // Le decimos que el que recibe la entrada tactil es este escenario
        Gdx.input.setInputProcessor(this);
        // Aqui se obtiene el objeto compuesto desde la libreria
        CompositeItemVO panel = sceneLoader.loadVoFromLibrary("panel_distancia");
        // De aqui se instancia el actor para poder modificar sus propiedades, entre otros
        panel_distancia = new CompositeActor(panel, sceneLoader.getRm());
        // Ahora obtenemos el del tiempo
        CompositeItemVO time = sceneLoader.loadVoFromLibrary("time_banner");
        // Ahora lo generamos
        panel_tiempo = new CompositeActor(time, sceneLoader.getRm());
        // Creamos la tabla que organizara al actor en su lugar
        UI = new Table();
        // Que llene la pantalla
        UI.setFillParent(true);
        // Que se organize hasta arriba a la izquierda y se expanda en X e Y
        UI.add(panel_distancia).top().left().expandX().row();
        UI.add(panel_tiempo).top().left().expandY().row();
        // Verificamos el tipo de entrada
        Config cfg = Config.Load();
        switch (cfg.getInputType()){ // Este sólo aplica si es Android
            case ACCELEROMETER:
                break;
            case JOYSTICK:
                configurarJoystick(sceneLoader);
                break;
        }
        // Ajustamos las etiquetas
        setupLabels();
        // Agregamos la tabla al stage
        addActor(UI);
    }

    private void configurarJoystick(SceneLoader sceneLoader) {
        // Obtenemos el joystick de la libreria
        CompositeItemVO jstick = sceneLoader.loadVoFromLibrary("joystick");
        // Despues lo instanciamos
        joystick = new CompositeActor(jstick, sceneLoader.getRm());
        // Agregamos el joystick en la parte inferior derecha
        addActor(joystick);
        // Definimos su tamaño
        joystick.setScale(0.5f);
        // Ajustamos su posicion
        joystick.setPosition(0, 0);
        // Obtenemos la perilla, que es la parte movil del joystic
        final Actor joystick_ball = joystick.getItem("joystick_ball");
        // Le definimos un color por defecto
        joystick.getItem("joystick_bg").setColor(1, 1, 1, 0.2f);
        joystick_ball.setColor(1, 1, 1, 0.6f); // Regresa el color al original
        // Esta variable guarda el estado defecto del joystick
        final float def_pos = joystick_ball.getY();
        // Le agregamos un escuchador para que se mueva
        joystick_ball.addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                joystick_ball.setColor(1, 0, 1, 0.6f); // Cambia el color para indicar que se esta tocando
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                joystick_ball.setColor(1, 1, 1, 0.6f); // Regresa el color al original
                joystick_ball.setY(def_pos);
            }

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                if(joystick_ball.getY() + deltaY >= 0 && joystick_ball.getY() + deltaY <= 350)
                    joystick_ball.setPosition(joystick_ball.getX(), joystick_ball.getY() + deltaY);
            }
        });
    }

    private void setupLabels(){
        valor = (Label) panel_distancia.getItem("meter");
        min = (Label) panel_tiempo.getItem("minutes");
        sec = (Label) panel_tiempo.getItem("seconds");
        millis = (Label) panel_tiempo.getItem("millis");
        // Default value
        min.setText("");
        sec.setText("");
        millis.setText("");
    }

    /**
     * Aqui se actualiza la etiqueta que marca la distancia recorrida
     * @param distancia
     */
    public void setValorDistancia(int distancia){
        if(distancia < 1000)
            valor.setText(distancia+"m");
        else
            valor.setText((distancia/1000)+"km");
    }

    public void setValorTiempo(long tiempo){
        min.setText(String.valueOf(tiempo/60000)+":");
        sec.setText(String.valueOf((tiempo/1000))+":");
        millis.setText(String.valueOf(tiempo%1000));
    }

    /**
     * Regresa una instancia del Joystick, para acceder a sus propiedades
     * @return
     */
    public CompositeActor getJoystick(){
        return joystick;
    }

}
