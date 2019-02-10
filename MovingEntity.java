import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

//this will be the class that reads
//the sprite sheets for the animated sprites
//---
//Its loosely based on the example image class

public class MovingEntity extends Entity {

    BiConsumer<Float, Float> setPosition;
    private float speed = 5;

    public MovingEntity(int x, int y, int r, String textureFile, float width, float height, int lineNumber) {
        super(x, y, r, textureFile, width, height, lineNumber);
        setPosition = getSprite()::setPosition;
    }


    //
    // work out where object should be for next frame
    //
    void calcMove(int minx, int miny, int maxx, int maxy, float xPos, float yPos) {
        //
        //add deltas to x and y setPosition
        //
        x = xPos;
        y = yPos;

        //
        // check we've not collided with any other actor
        //
        /*for (Entity a : actors) {
            if (a.obj != obj && a.within(x, y)) {
                dx *= -1;
                x += dx;
                dy *= -1;
                y += dy;
            }
        }*/
    }

    public void moveLeft() {
        x -= speed;
        getHitBox().setPosition(x, y);
    }

    public void moveRight() {
        x += speed;
        getHitBox().setPosition(x, y);
    }

    public void moveUp() {
        y -= speed;
        getHitBox().setPosition(x, y);
    }

    public void moveDown() {
        y += speed;
        getHitBox().setPosition(x, y);
    }

    public boolean canMoveRight() {
        boolean blocked = false;
        int[] t = getOccupiedTiles();
        int tileX = t[0];
        int tileY = t[1];
        int tileCount = t[2];
        if (!level.grid[tileX][tileY].canWalkThrough()) {  //bottom-right tile
            blocked = true;
            System.out.println("Right move blocked by [" + tileX + "][" + tileY + "]");
        } else if (!level.grid[tileX][tileY - (tileY > 0 ? 1 : 0)].canWalkThrough()) {  //top-right tile
            blocked = true;
            System.out.println("Right move blocked by [" + tileX + "][" + (tileY - 1) + "]");
        } else if (tileCount == 2 && tileX == level.grid.length - 1) {
            blocked = true;
        }
        System.out.println();
        return !blocked;
    }

    public boolean canMoveLeft() {
        boolean blocked = false;
        int[] t = getOccupiedTiles();
        int tileX = t[0];
        int tileY = t[1];
        int tileCount = t[2];
        if (!level.grid[tileX - (tileX > 0 ? 1 : 0)][tileY].canWalkThrough()) {  //bottom-left tile
            blocked = true;
            System.out.println("Left move blocked by [" + (tileX - 1) + "][" + tileY + "]");
        } else if (!level.grid[tileX - (tileX > 0 ? 1 : 0)][tileY - (tileY > 0 ? 1 : 0)].canWalkThrough()) {  //top-right tile
            blocked = true;
            System.out.println("Left move blocked by [" + (tileX - 1) + "][" + (tileY - 1) + "]");
        } else if (tileCount == 2 && tileX == 0) {
            blocked = true;
        }
        System.out.println();
        return !blocked;
    }

    public boolean canMoveUp() {
        boolean blocked = false;
        int[] t = getOccupiedTiles();
        int tileX = t[0];
        int tileY = t[1];
        int tileCount = t[2];
        if (!level.grid[tileX - (tileX > 0 ? 1 : 0)][tileY - (tileY > 0 ? 1 : 0)].canWalkThrough()) {  //top-right tile
            blocked = true;
            System.out.println("Up move blocked by [" + (tileX - 1) + "][" + (tileY - 1) + "]");
        } else if (!level.grid[tileX][tileY - (tileY > 0 ? 1 : 0)].canWalkThrough()) {  //top-left tile
            blocked = true;
            System.out.println("Up move blocked by [" + (tileX) + "][" + (tileY - 1) + "]");
        } else if (tileCount == 2 && tileY == 0) {
            blocked = true;
        }
        System.out.println();
        return !blocked;
    }

    public boolean canMoveDown() {
        boolean blocked = false;
        int[] t = getOccupiedTiles();
        int tileX = t[0];
        int tileY = t[1];
        int tileCount = t[2];
        if (!level.grid[tileX][tileY].canWalkThrough()) {  //bottom-right tile
            blocked = true;
            System.out.println("Down move blocked by [" + tileX + "][" + tileY + "]");
        } else if (!level.grid[tileX - (tileX > 0 ? 1 : 0)][tileY].canWalkThrough()) {  //bottom-left tile
            blocked = true;
            System.out.println("Down move blocked by [" + (tileX - 1) + "][" + tileY + "]");
        } else if (tileCount == 2 && tileY == level.grid[0].length - 1) {
            blocked = true;
        }
        System.out.println();
        return !blocked;
    }

    private int[] getOccupiedTiles() {
        int x = -1, y = -1;
        int n = 0;  //counts how many tiles player currently occupies (logically can be 1, 2 or 4)
        for (int i = 0; i < level.grid.length - 1; i++) {
            for (int j = 0; j < level.grid[0].length - 1; j++) {
                if (level.grid[i][j].getHitbox().entityColliding(getHitBox().getRectBox())) { //if player is in tile[i][j]
                    System.out.println("Player in: [" + i + "," + j + "]");
                    x = i;  //store right-most tile player occupies
                    y = j;  //store bottom-most tile player occupies
                    n++;
                }
            }
        }
        System.out.println("n = " + n + "\nBottom right tile is [" + x + "," + y + "]");
        return new int[]{x, y, n};

    }

    public void performMove() {
        setPosition.accept(x, y);
        this.next();
    }

}
