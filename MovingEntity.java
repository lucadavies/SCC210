import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

//this will be the class that reads
//the sprite sheets for the animated sprites
//---
//Its loosely based on the example image class

public class MovingEntity extends Entity {

    public MovingEntity(int x, int y, int r, String textureFile, float width, float height, int lineNumber) {
        super(x, y, r, textureFile, width, height, lineNumber);
    }

    //this method loops across the spritesheet row, animating the sprite

    public void performMove() {
        super.performMove();
        this.next();
    }

}
