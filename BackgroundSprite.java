/*
*Class for the backgrounds of the game (grass tiles, animated lava tiles etc..)
*
*/

public class BackgroundSprite extends MovingEntity {
  private boolean solid = false;

  public BackgroundSprite(String fileName) {
      super(0, 0, 0, fileName, Driver.SCREEN_WIDTH, Driver.SCREEN_HEIGHT, 0);
  }




}
