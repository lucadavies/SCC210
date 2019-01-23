/*
 *Class for all characters in the game to be based on.
 *Both enemies and the player (+ allies)
 */
import java.util.*;
import java.lang.Object;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.nio.file.Paths;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class Pickup extends Entity{
    
    private Sprite img;
    
    //Creates a list of pickUps.
    public enum pickUpType
    {
       
        smallLaserGun,        
        superLaserGun,
        bomb,
        misile,
        oldMirror,
        boots,
        alienMess,
        vaccumCleaner,
        frozenStone,
        fastStopWatch,
        slowStopWatch,
        angleWings,
        doubleBullet,
        tripleBullet,
        superBullet
    }
    
    //constructor for pickUps.
    public Pickup(pickUpType pickup)
    {
        //this.pickUpType = pickUpType;
        
        switch (pickup){
            case smallLaserGun:
                this.setImgTexture("smallGun.png");
                    break;
            case superLaserGun:
                this.setImgTexture("superLaserGun.png");
                    break;
            case bomb:
                this.setImgTexture("Bomb.png");
                    break;
            case misile:
                this.setImgTexture("Missile.png");
                    break;
            case oldMirror:
                this.setImgTexture("oldMirror.png");
                    break;
            case boots:
                this.setImgTexture("boots.png");
                    break;
            case alienMess:
                this.setImgTexture("alienMess.png");
                    break;
            case vaccumCleaner:
                this.setImgTexture("vaccumCleaner.png");
                    break;
            case frozenStone:
                this.setImgTexture("frozen.png");
                    break;
            case fastStopWatch:
                this.setImgTexture("fastStopWatch.png");
                    break;
            case slowStopWatch:
                this.setImgTexture("slowStopWatch.png");
                    break;
            case angleWings:
                this.setImgTexture("angleWings.png");
                    break;
            case doubleBullet:
                this.setImgTexture("doubleBullet.png");
                    break;
            case tripleBullet:
                this.setImgTexture("tripleBullet.png");
                    break;
            case superBullet:
                this.setImgTexture("superBullet.png");
                    break;
                
            }
    }
    
    //Method to show the image texture copied from movingEntity.
    public void setImgTexture(String texture) {
            Texture imgTexture = new Texture();
            try {
                imgTexture.loadFromFile(Paths.get(texture));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
                img = new Sprite(imgTexture);
      }
    
     //Method which removes pick up from the screen.
     public void removePickUp(pickUpType pickUp){
         pickUp = null;
     }
        
        
        
}

