/*
 *Class for all characters in the game to be based on.
 *Both enemies and the player (+ allies)
 */
import java.util.*;
import java.lang.Object;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

public class Pickup extends MovingEntity{
    
    
    public enum pickUpType{
        smallLaserGun;
        superLaserGun;
        bomb;
        misile;
        oldMirror;
        boots;
        alienMess;
        vaccumCleaner;
        frozenStone;
        fastStopWatch;
        slowStopWatch;
        angleWings;
        doubleBullet;
        tripleBullet;
        superBullet;
    }
    
    public Pickup(int x,int y,int r,String textureFile,float width,float height,int lineNumber,pickUpType seed)
    {
        this.seed = seed;
        super(x,y,r,textureFile,width,height,lineNumber);
        switch (seed){
            case smallLaserGun:
            case superLaserGun:
            case bomb:
            case misile:
            case oldMirror:
            case oldMirror:
            case boots:
            case alienMess:
            case vaccumCleaner:
            case frozenStone:
            case fastStopWatch:
            case slowStopWatch:
            case angleWings:
            case doubleBullet:
            case tripleBullet:
            case superBullet:
                break;
                
        }
        
        
        
        
    }

