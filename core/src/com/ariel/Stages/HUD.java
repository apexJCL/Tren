package com.ariel.Stages;

import com.ariel.Config;
import com.ariel.Managers.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

/**
 * Esta clase alberga el HUD del jugador, el cual contiene sus datos (por ahora solo su distancia recorrida)
 */
public class HUD extends Stage {

    private final Table UI;
    private final SceneManager sceneManager;
    private CompositeActor panel_distancia, joystick, panel_tiempo, mensaje_final;
    private Label valor_distancia, min, sec, millis;

    public HUD(SceneLoader sceneLoader, SceneManager sceneManager){
        super(new FitViewport(720, 1280)); // Inicializamos para que se adapte igualmente a la pantalla
        // GUardamos referencia al scenemanager que nos permite controlar las escenas mostradas
        this.sceneManager = sceneManager;
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
        // Cargamos el mensaje final
        CompositeItemVO fin = sceneLoader.loadVoFromLibrary("finish_msg");
        // Lo asignamos
        mensaje_final = new CompositeActor(fin, sceneLoader.getRm());
        // Configuramos sus botones
        configureMenuButtons();
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

    private void configureMenuButtons() {
        final CompositeActor repeat = (CompositeActor) mensaje_final.getItem("repeat");
        repeat.setTouchable(Touchable.enabled);
        repeat.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(SceneManager.State.GAMEPLAY);
            }
        });
        final CompositeActor menu = (CompositeActor) mensaje_final.getItem("menu");
        menu.setTouchable(Touchable.enabled);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(SceneManager.State.MENU);
            }
        });
        // Configuramos que la tecla de menu o back funcionará para abrir el menú
        addListener(new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if(event.getKeyCode() == Input.Keys.BACK)
                    System.out.println("Back Pressed");
                return true;
            }
        });
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
        valor_distancia = (Label) panel_distancia.getItem("meter");
        min = (Label) panel_tiempo.getItem("minutes");
        sec = (Label) panel_tiempo.getItem("seconds");
        millis = (Label) panel_tiempo.getItem("millis");
        // Default value
        min.setText("");
        sec.setText("");
        millis.setText("");
    }

    public void showFinishedMessage(){
        // Remueve todos los banners del padre
        UI.clearChildren();
        // Quitamos el joystick
        joystick.remove();
        // Ahora nos encargamos de rellenar los campos de la ventana
        Label tiempo = (Label) mensaje_final.getItem("time");
        Label distancia = (Label) mensaje_final.getItem("distance");
        tiempo.setText("Tiempo: "+min.getText()+sec.getText()+millis.getText());
        distancia.setText("Distancia: "+ valor_distancia.getText());
        // Asigna la posicion del mensaje
        mensaje_final.setPosition((getWidth() - mensaje_final.getWidth())/2f, (getHeight() - mensaje_final.getHeight())/2f);
        addActor(mensaje_final);
    }

    /**
     * Aqui se actualiza la etiqueta que marca la distancia recorrida
     * @param distancia
     */
    public void setValorDistancia(int distancia){
        valor_distancia.setText(distancia+"m");
    }

    public void setValorTiempo(long tiempo){
        min.setText(String.valueOf(tiempo/60000)+":");
        sec.setText(String.valueOf((tiempo/1000)%60)+":");
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
