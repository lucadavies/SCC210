import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import java.lang.Object;

import org.jsfml.graphics.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;


public abstract class Entity {

    private Drawable obj;
    int x; //current x-coordinate
    int y; //current y-coordinate

    private Sprite img;
    private int width;
    private int height;
    private CollisionBox colBox;

    private int ssCols;
    private int ssRows;
    private int ssX = 0; //sprite sheet column
    private int ssY = 0; //sprite sheet row

    protected Random rnd = new Random();
    Map level;


    /**
     * public Entity - Constructor for Entity...
     *
     * @param {type} int x              x position of Entity.
     * @param {type} int y              y position of Entity.
     * @param {type} int r
     * @param {type} String textureFile path to file containing Entity image.
     * @return {type}                    description
     */
    public Entity(int x, int y, int width, int height, String textureFile) {
        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get(textureFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imgTexture.setSmooth(false);
        img = new Sprite(imgTexture);
        img.setOrigin(0, 0);
        img.setPosition(x, y);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        colBox = new CollisionBox(x, y, width, height);
        ssCols = imgTexture.getSize().x / width;
        ssRows = imgTexture.getSize().y / height;
        obj = img;
    }


    /**
     * public void - Depending on which tank sprite image is needed it sets one or the other.
     *
     * @return {type}  description
     */
    public void next() {
        if (ssX >= ssCols - 1) {
            ssX = 0;
        } else {
            ssX++;
        }
        img.setTextureRect(new IntRect(ssX * width, (this instanceof Tank ? ssY : 0) * height, width, height));
        if (this instanceof Tank) {
            System.out.println("ssX: " + ssX + ", ssY: " + ssY);
        }
    }


    /**
     * public Sprite - Returns the image of the sprite.
     *
     * @return {type}  returns sprite image
     */
    public Sprite getSprite() {
        return img;
    }


    /**
     * public int - Returns width of the Entity, as int.
     *
     * @return {type}  description
     */
    public int getWidth() {
        return width;
    }


    /**
     * public int - Returns height of Entity, as int.
     *
     * @return {type}  description
     */
    public int getHeight() {
        return height;
    }


    /**
     * public int - Returns sprite sheet column.
     *
     * @return {type}  description
     */
    public int getssX() {
        return ssX;
    }

    /**
     * public int - Returns sprite sheet row.
     *
     * @return {type}  description
     */
    public int getssY() {
        return ssY;
    }

    /**
     * public RectangleShape - Returns hitbox of the CollisionBox.
     *
     * @return {type}  description
     */
    public RectangleShape getRectBox() {
        return colBox.getRectBox();
    }


    /**
     * public CollisionBox - Returns CollisionBox of Entity.
     *
     * @return {type}  description
     */
    public CollisionBox getHitBox() {
        return colBox;
    }

    //
    //This method sets the num of lines down, and how many rows across from the left
    //

    /**
     * public void - Sets Entity within sheet.
     *
     * @param {type} int x Number of rows across the sheet.
     * @param {type} int y Number of lines down the sheet.
     * @return {type}       description
     */
    public void setSpriteWithinSheet(int x, int y) {
        ssX = x;
        ssY = y;
        img.setTextureRect(new IntRect(ssX * width, 0, width, height));
    }


    /**
     * public void - Sets an Image to a sprite.
     *
     * @param {type} String texture Path to image file.
     * @return {type}                description
     */
    public void setImgTexture(String texture) {
        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get(texture));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        img = new Sprite(imgTexture);
    }


    /**
     * boolean within - Returns true if position of entity matches the x and y parameters, returns false otherwise.
     *
     * @param {type} float x x position to be compared.
     * @param {type} float y y position to be compared.
     * @return {type}         description
     */
    boolean within(float x, float y) {
        if (this.x == x && this.y == y) {
            return true;
        }
        return false;
    }


    /**
     * public void - Sets Entity to map.
     *
     * @param {type} Map level
     * @return {type}           description
     */
    public void setMap(Map level) {
        this.level = level;
    }

    //
    //Render the object at its new position
    //

    /**
     * void draw - Renders the object to it's new position.
     *
     * @param {type} RenderWindow w description
     * @return {type}                description
     */
    void draw(RenderWindow w) {
        w.draw(obj);
    }


}
