import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/*
 * Class to display a menu with selectable Play and Quit buttons
 */
public class Menu {
    private RenderWindow window;
    private Texture backgroundTex = new Texture();
    private Texture tickTex = new Texture();
    private Sprite background;
    private Sprite tick;

    public enum MENU_STATE {PLAY, RESTART, QUIT}

    private MENU_STATE selectedState = MENU_STATE.PLAY;

    /*
     * Creates a new instance of Menu in the given RenderWindow
     *
     * @param w the RenderWindow in which to draw this Menu
     */
    public Menu(RenderWindow w) {
        window = w;

        loadImage(backgroundTex, "art/menu/background.png");
        background = new Sprite(backgroundTex);
        loadImage(tickTex, "art/menu/tick.png");
        tick = new Sprite(tickTex);
        tick.setPosition(350, 100);
    }

    private void loadImage(Texture t, String path) {
        try {
            t.loadFromFile(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Call to start the Menu animating so that key events are polled allowing options to be selected
     */
    public void run() {
        boolean done = false;
        while (!done && window.isOpen()) {
            window.clear();
            background.draw(window, RenderStates.DEFAULT);
            tick.draw(window, RenderStates.DEFAULT);
            window.display();
            for (Event event : window.pollEvents()) {
                if (event.type == Event.Type.KEY_PRESSED) {
                    if (event.asKeyEvent().key == Keyboard.Key.UP) {
                        moveUp();
                    }
                    if (event.asKeyEvent().key == Keyboard.Key.DOWN) {
                        moveDown();
                    }
                    if (event.asKeyEvent().key == Keyboard.Key.RETURN) {
                        done = true;
                    }
                } else if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    window.close();
                }
            }
        }
    }

    private void moveUp() {
        if (selectedState == MENU_STATE.PLAY) { //if on play, move tick over quit
            tick.setPosition(450, 400);
            selectedState = MENU_STATE.QUIT;
        } else if (selectedState == MENU_STATE.RESTART) { //if on restart, move tick over play
            tick.setPosition(350, 100);
            selectedState = MENU_STATE.PLAY;
        } else if (selectedState == MENU_STATE.QUIT) { //if on quit, move tick over restart
            tick.setPosition(200, 250);
            selectedState = MENU_STATE.RESTART;
        }
    }

    private void moveDown() {
        if (selectedState == MENU_STATE.PLAY) { //if on play, move tick over restart
            tick.setPosition(200, 250);
            selectedState = MENU_STATE.RESTART;
        } else if (selectedState == MENU_STATE.RESTART) { //if on restart, move tick over quit
            tick.setPosition(450, 400);
            selectedState = MENU_STATE.QUIT;
        } else if (selectedState == MENU_STATE.QUIT) { //if on quit, move tick over play
            tick.setPosition(350, 100);
            selectedState = MENU_STATE.PLAY;
        }
    }

    /*
     * Gets the state of the Menu when closed: MENU_STATE.PLAY or MENU_STATE.QUIT
     * @return the closing state of this Menu
     */
    public MENU_STATE getCloseState() {
        return selectedState;
    }
}
