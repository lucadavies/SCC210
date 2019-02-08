import java.util.*;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.*;
import org.jsfml.graphics.*;

/*
 *Class that handles all the points in the game
 */

public class OutOfBounds {

    private int xMax = 1020;
    private int yMax = 1020;

    private int inBounds = 1;
    private int outBounds = 0;

    private int[][] playerArea = new int[1020][1020];

    public OutOfBounds() {
        initialise(playerArea);
    }


    //set all points to 1
    public void initialise(int[][] a) {
        for (int i = 0; i < yMax; i++) {
            for (int j = 0; j < xMax; j++) {
                playerArea[j][i] = 1;
            }
        }
    }


    //set all out of bounds points to 0
    public void accept(int x, int y, int w, int h) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                playerArea[j + x][i + y] = 0;
            }
        }
    }


    //checks ahead to see if the player will be out of bounds and blocks movement appropriately
    public void isOutOfBounds(int x, int y, int w, int h, int checkAhead) {
        /*
        for (int i = 0; i < h; i++) {
            if (playerArea[x - checkAhead][i + y] == 0 || Player.getPlayerInstance().x == checkAhead) {
                Player.getPlayerInstance().canMoveLeft = false;
            } else {
                Player.getPlayerInstance().canMoveLeft = true;
            }
            if (playerArea[x + w + checkAhead][i + y] == 0 || Player.getPlayerInstance().x == 1020 - checkAhead) {
                Player.getPlayerInstance().canMoveRight = false;
            } else {
                Player.getPlayerInstance().canMoveRight = true;
            }
        }
        for (int i = 0; i < w; i++) {
            if (playerArea[x + i][y - checkAhead] == 0 || Player.getPlayerInstance().y == checkAhead) {
                Player.getPlayerInstance().canMoveUp = false;
            } else {
                Player.getPlayerInstance().canMoveUp = true;
            }
            if (playerArea[x + i][h + y + checkAhead] == 0 || Player.getPlayerInstance().y == 1020 - checkAhead) {
                Player.getPlayerInstance().canMoveDown = false;
            } else {
                Player.getPlayerInstance().canMoveDown = true;
            }
        }
        */
    }
}
