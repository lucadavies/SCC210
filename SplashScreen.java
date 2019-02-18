import org.jsfml.graphics.*;

public class SplashScreen extends UI {

    public SplashScreen(RenderWindow w, String bgSrc) {
        super(w, bgSrc);
    }

    public void run() {
        while (!enterPressed()) {
            super.run();
            display();
        }
        reset();
    }
}
