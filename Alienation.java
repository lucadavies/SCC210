import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

import java.io.IOException;
import java.nio.file.Paths;

public class Alienation {

    private int SCREEN_WIDTH = 1020;
    private int SCREEN_HEIGHT = 1020;
    private String title = "Alienation";
    private MainMenu m;
    private SplashScreen inst;
    private SplashScreen win;
    private SplashScreen lose;
    private Sound loseSFX;
    private Driver game;

    private RenderWindow window;

    public Alienation() {
        window = new RenderWindow(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT), title, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);
        m = new MainMenu(window, "res/art/ui/main.png");
        inst = new SplashScreen(window, "res/art/ui/inst-bg.png");
        win = new SplashScreen(window, "res/art/ui/win.png");
        lose = new SplashScreen(window, "res/art/ui/lose.png");
        SoundBuffer tempBuf = new SoundBuffer();
        try {
            tempBuf.loadFromFile(Paths.get("res/audio//gameover.wav"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        loseSFX = new Sound(tempBuf);
        game = new Driver(window);

    }

    private void start() {
        m.run();
        if (m.getCloseState() == MainMenu.MENU_STATE.PLAY) {
            inst.run();
            if (game.run()) {
                win.run();
            } else {
                loseSFX.play();
                lose.run();
            }
        }
    }

    public static void main(String[] args) {
        Alienation game = new Alienation();
        game.start();
    }
}
