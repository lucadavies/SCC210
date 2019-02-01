import java.util.ArrayList;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

public class Alienation {
    private int level;
    private int SCREEN_WIDTH = 1020;
    private int SCREEN_HEIGHT = 1020;
    private String title = "Alientation";
    private UI menu;
    private Driver game;

    RenderWindow window;

    public Alienation() {
        window = new RenderWindow(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT), title, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);
        menu = new UI(window);
        game = new Driver(window);

    }

    private void start()
    {
        menu.run();
        game.run();
    }

    public static void main(String[] args) {
        Alienation game = new Alienation();
        game.start();
    }
}
