import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

public class Alienation {
    private int level;
    private int SCREEN_WIDTH = 1020;
    private int SCREEN_HEIGHT = 1020;
    private String title = "Alienation";
    private Menu m;
    private Driver game;

    private RenderWindow window;

    public Alienation() {
        window = new RenderWindow(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT), title, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);
        m = new Menu(window);
        game = new Driver(window);

    }

    private void start() {
        m.run();
        if (m.getCloseState() == Menu.MENU_STATE.PLAY) {
            game.run();
        }
    }

    public static void main(String[] args) {
        Alienation game = new Alienation();
        game.start();
    }
}
