import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

public class Alienation {

    private int SCREEN_WIDTH = 1020;
    private int SCREEN_HEIGHT = 1020;
    private String title = "Alienation";
    private MainMenu m;
    private SplashScreen inst;
    private Driver game;

    private RenderWindow window;

    public Alienation() {
        window = new RenderWindow(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT), title, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);
        m = new MainMenu(window, "art/ui/main.png");
        inst = new SplashScreen(window, "art/ui/inst-bg.png");
        game = new Driver(window);

    }

    private void start() {
        m.run();
        if (m.getCloseState() == MainMenu.MENU_STATE.PLAY) {
            inst.run();
            game.run();
        }
    }

    public static void main(String[] args) {
        Alienation game = new Alienation();
        game.start();
    }
}
