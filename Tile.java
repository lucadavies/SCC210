import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.lang.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Tile {

    Drawable objc;
    private Sprite img;
    private Texture imgTexture = new Texture();
    private String imgPath;
    private boolean walk_through = true;
    private boolean shoot_through = false;
    private float scale_times = 2;
    private Vector2i size;

    public Tile(String path_of_image) {

        this.imgPath = path_of_image;
        this.loadImage();
        this.size = this.imgTexture.getSize();

        img = new Sprite(imgTexture);        // enlarge the image twice
        img.setOrigin(new Vector2f(imgTexture.getSize()));
        img.scale(this.scale_times, this.scale_times);

        objc = img;                // set image drawable
    }

    /**
     * Draw image on the Screen
     */
    public void draw(RenderWindow w) {
        w.draw(objc);
    }

    /**
     * Load image based on the image path.
     */
    public void loadImage() {
        try {
            imgTexture.loadFromFile(Paths.get(this.imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scale picture to "times" time
     */
    public void imgScale(float times) {
        this.img.scale(times, times);
    }

    public String getPath() {
        return this.imgPath;
    }

    public void setPath(String newPath) {
        this.imgPath = newPath;
        this.loadImage();
    }


    public float getWidth() {
        return this.size.x;
    }

    public float getHeight() {
        return this.size.y;
    }

    public boolean getWalkThrough() {
        return this.walk_through;
    }

    public void setWalkThrough(boolean ifWalkable) {
        this.walk_through = ifWalkable;
    }


    public boolean getShootThrough() {
        return this.shoot_through;
    }

    public void setShootThrough(boolean ifShootable) {
        this.shoot_through = ifShootable;
    }

}