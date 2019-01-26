import java.util.ArrayList;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/*
 * Class to display a menu with selectable Play and Quit buttons
 */
public class UI {
    private RenderWindow window;
    private ArrayList<Entity> ents;
    private Images goBtn;
    private Images quitBtn;

    public enum MENU_STATE {PLAY, QUIT}

    private MENU_STATE selectedState = MENU_STATE.PLAY;

    /*
     * Creates a new instance of UI in the given RenderWindow
     *
     * @param w the RenderWindow in which to draw this UI
     */
    public UI(RenderWindow w) {
        window = w;
        ents = new ArrayList<>();

        goBtn = new Images(250, 190, 0, System.getProperty("user.dir") + "/art/menu/PlaySel.png");
        quitBtn = new Images(250, 310, 0, System.getProperty("user.dir") + "/art/menu/ExitUnsel.png");

        addEnt(goBtn);
        addEnt(quitBtn);
    }

    /*
     * Call to start the UI animating so that key events are polled allowing options to be selected
     */
    public void run() {
        boolean done = false;
        while (!done && window.isOpen()) {
            window.clear(new Color(200, 142, 255));
            for (Entity e : ents) {
                e.draw(window);
            }
            window.display();
            for (Event event : window.pollEvents()) {
                if (event.type == Event.Type.KEY_PRESSED) {
                    if (event.asKeyEvent().key == Keyboard.Key.UP) {
                        goBtn = new Images(250, 190, 0, System.getProperty("user.dir") + "/art/menu/PlaySel.png");
                        quitBtn = new Images(250, 310, 0, System.getProperty("user.dir") + "/art/menu/ExitUnsel.png");
                        selectedState = MENU_STATE.PLAY;
                    }
                    if (event.asKeyEvent().key == Keyboard.Key.DOWN) {
                        goBtn = new Images(250, 190, 0, System.getProperty("user.dir") + "/art/menu/PlayUnsel.png");
                        quitBtn = new Images(250, 310, 0, System.getProperty("user.dir") + "/art/menu/ExitSel.png");
                        selectedState = MENU_STATE.QUIT;
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

    private void addEnt(Entity e) {
        ents.add(e);
    }

    /*
     * Gets the state of the UI when closed: MENU_STATE.PLAY or MENU_STATE.QUIT
     * @return the closing state of this UI
     */
    public MENU_STATE getCloseState() {
        return selectedState;
    }
}
