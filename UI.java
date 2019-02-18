import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.window.*;
import org.jsfml.window.event.*;

import java.io.IOException;
import java.nio.file.Paths;

public class UI {

    private RenderWindow window;
    private Texture backgroundTex = new Texture();
    private Sprite background;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean enter = false;

    public UI (RenderWindow w, String bgSrc) {
        window = w;
        loadImage(backgroundTex, bgSrc);
        background = new Sprite(backgroundTex);
    }

    public void run() {
        window.clear();
        background.draw(window, RenderStates.DEFAULT);
        reset();
        for (Event event : window.pollEvents()) {
            if (event.type == Event.Type.KEY_PRESSED) {
                if (event.asKeyEvent().key == Keyboard.Key.UP) {
                    up = true;
                }
                if (event.asKeyEvent().key == Keyboard.Key.DOWN) {
                    down = true;
                }
                if (event.asKeyEvent().key == Keyboard.Key.LEFT) {
                    left = true;
                }
                if (event.asKeyEvent().key == Keyboard.Key.RIGHT) {
                    right = true;
                }
                if (event.asKeyEvent().key == Keyboard.Key.RETURN) {
                    enter = true;
                }
            } else if (event.type == Event.Type.CLOSED) {
                // the user pressed the close button
                window.close();
            }
        }
    }

    void reset() {
        up = false;
        down = false;
        left = false;
        right = false;
        enter = false;
    }

    boolean upPressed() {
        return up;
    }

    boolean downPressed() {
        return down;
    }

    boolean leftPressed() {
        return left;
    }

    boolean rightPressed() {
        return right;
    }

    boolean enterPressed() {
        return enter;
    }

    RenderWindow getWindow() {
        return window;
    }

    void display() {
        window.display();
    }

    void loadImage(Texture t, String path) {
        try {
            t.loadFromFile(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
