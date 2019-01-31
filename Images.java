import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.graphics.Drawable;

import java.io.IOException;
import java.nio.file.Paths;


public class Images extends Entity {
    private Sprite img;

    public Images(int x, int y, int r, String textureFile) {
        //
        // Load image/texture
        //
        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get(textureFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imgTexture.setSmooth(false);

        img = new Sprite(imgTexture);
        img.setOrigin(Vector2f.div(
                new Vector2f(imgTexture.getSize()), 2));
        this.x = x;
        this.y = y;

        //
        //Store references to object and key methods
        //
        obj = img;
        setPosition = img::setPosition;

    }


}
