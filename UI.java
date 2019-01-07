import java.util.ArrayList;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class UI
{
    private RenderWindow window;
    private ArrayList<Entity> ents;
    private Img goBtn;
    private Img quitBtn;
    public enum MENU_STATE {PLAY, QUIT}
    private MENU_STATE selectedState = MENU_STATE.PLAY;

    public UI(RenderWindow w)
    {
        window = w;
        ents = new ArrayList<>();

        goBtn = new Img(250, 190, "./assets/PlaySel.png");
        quitBtn = new Img(250, 310, "./assets/ExitUnsel.png");

        addEnt(goBtn);
        addEnt(quitBtn);
    }

    public void run() {
        boolean done = false;
        while (!done && window.isOpen())
        {
            window.clear(new Color(200, 142, 255));
            for (Entity e : ents) {
                e.draw(window);
            }
            window.display();
            for (Event event : window.pollEvents( )) {
                if (event.type == Event.Type.KEY_PRESSED){
                    if (event.asKeyEvent().key == Keyboard.Key.UP) {
                        goBtn.setSprite("./assets/PlaySel.png");
                        quitBtn.setSprite("./assets/ExitUnsel.png");
                        selectedState = MENU_STATE.PLAY;
                    }
                    if (event.asKeyEvent().key == Keyboard.Key.DOWN) {
                        goBtn.setSprite("./assets/PlayUnsel.png");
                        quitBtn.setSprite("./assets/ExitSel.png");
                        selectedState = MENU_STATE.QUIT;
                    }
                    if (event.asKeyEvent().key == Keyboard.Key.RETURN) {
                        done = true;
                    }
                }
                else if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    window.close( );
                }
            }
        }
    }

    private void addEnt(Entity e)
    {
        ents.add(e);
    }

    public MENU_STATE getCloseState()
    {
        return selectedState;
    }

    /*public static void main (String args[]) {
        RenderWindow win = new RenderWindow();
        win.create(new VideoMode(500, 500), "TestUI", WindowStyle.DEFAULT);
        win.setFramerateLimit(60);
        UI menu = new UI(win);
        menu.run();
    }
    */

}