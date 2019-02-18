import org.jsfml.graphics.*;

/*
 * Class to display a menu with selectable Play and Quit buttons
 */
public class MainMenu extends UI {

    private Sprite tick;
    private Texture tickTex = new Texture();

    public enum MENU_STATE {PLAY, RESTART, QUIT}

    private MENU_STATE selectedState = MENU_STATE.PLAY;

    /*
     * Creates a new instance of MainMenu in the given RenderWindow
     *
     * @param w the RenderWindow in which to draw this MainMenu
     */
    public MainMenu(RenderWindow w, String backgroundSrc) {
        super(w, backgroundSrc);
        loadImage(tickTex, "art/ui/tick.png");
        tick = new Sprite(tickTex);
        tick.setPosition(350, 100);
    }

    /*
     * Call to start the MainMenu animating so that key events are polled allowing options to be selected
     */
    public void run() {
        //super.run();
        boolean done = false;
        while (!done) {
            super.run();
            tick.draw(getWindow(), RenderStates.DEFAULT);
            display();
            if (upPressed()) {
                moveUp();
            }
            if (downPressed()) {
                moveDown();
            }
            if (enterPressed()) {
                done = true;
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
     * Gets the state of the MainMenu when closed: MENU_STATE.PLAY or MENU_STATE.QUIT
     * @return the closing state of this MainMenu
     */
    public MENU_STATE getCloseState() {
        return selectedState;
    }
}
