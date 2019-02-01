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
    static final int TILE_SIZE = 60;
    private CollisionBox hitbox = new CollisionBox(0,0,60,60);
    private int x = 0;
    private int y = 0;

    public Tile(String path_of_image, int xPos, int yPos) {

        this.imgPath = path_of_image;
        this.loadImage();
        this.size = this.imgTexture.getSize();

        img = new Sprite(imgTexture);        // enlarge the image twice
        //img.setOrigin(new Vector2f(imgTexture.getSize()));
        img.setOrigin(0, 0);
        //img.scale(this.scale_times, this.scale_times);
        img.setPosition(xPos, yPos);

        hitbox.setPosition(xPos,yPos);

        x = xPos;
        y = yPos;

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

    public CollisionBox getHitbox(){return hitbox;}

    public int getX(){return x;}

    public int getY(){return y;}

}
