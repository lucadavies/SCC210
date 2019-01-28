
/*
*Class for the map which is displayed to the user
*
*/

import org.jsfml.graphics.RenderWindow;

public class Map
{

    mapType seed;
	Tile[][] grid = new Tile[17][17];

    //enum type for the maps public so u can use it outside of this
    public enum mapType
	{
		FARM,
		FOREST,
		RIVER,
		CAVE,
		PLANET,
		SHIP
	}

	//constructor which provides seed to generate a specific map
    public Map(mapType seed)
    {
        this.seed = seed;
        switch (seed)
        {
            case FARM:
                this.setBackground("art/map/farm1.png");
                this.setBoundaries("art/map/grass.png");
                break;
            case FOREST:
                this.setBackground("forest_background.png");
                this.setBoundaries("bush.png");
                break;
            case RIVER:
                this.setBackground("river_background.png");
                this.setBoundariesRiver("bush.png");
                break;
            case CAVE:
                this.setBackground("cave_background.png");
                this.setBoundariesCave("bush.png");
                break;
            case PLANET:
                this.setBackground("planet_background.png");
                this.setBoundaries("bush.png");
                break;
            case SHIP:
                this.setBackground("ship_background.png");
                this.setBoundaries("bush.png");
                break;
        }
    }
    //sets all the tiles to one single image
	private void setBackground(String backgroundTile)
	{
		for(int i = 0; i < 17; i++)
			for (int j = 0; j < 17; j++)
				grid[i][j] = new Tile(backgroundTile, i * Tile.TILE_SIZE,j * Tile.TILE_SIZE);
	}
	//sets the boundaries of the map to one single image
    private void setBoundaries(String image)
    {
        for(int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                if (i == 0 || i == 16 || j == 0 || j == 16)
                    grid[i][j] = new Tile(image,i * Tile.TILE_SIZE,j * Tile.TILE_SIZE);
    }
    //sets the boundaries for the cave map which has a different border than the regualar box one
    private void setBoundariesCave(String image)
    {
        for(int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                if ((i == 1 && (j < 7 || j > 9)) || (i == 15 && (j < 7 || j > 9)) || j == 0 || j == 16 || j == 6 || j == 10)
                    grid[i][j] = new Tile(image,i * Tile.TILE_SIZE,j * Tile.TILE_SIZE);
    }
    //sets the boundaries for the river map which has a different border than the regular box one
    private void setBoundariesRiver(String image)
    {
        for(int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
               //PLACEHOLDER if ((i==1 && (j<7 || j>9)) || (i==15 && (j<7 || j>9)) || j==0 || j==16 || j==6 || j==10)
                grid[i][j] = new Tile(image,i * Tile.TILE_SIZE,j * Tile.TILE_SIZE);
    }
    //uses x and y to set a specific tile to a specific image/object
    private void addObject(int x, int y, String image)
    {
        grid[x][y]= new Tile(image, x * Tile.TILE_SIZE,y * Tile.TILE_SIZE);
    }
    //returns a specific tile
    public Tile getTile(int x, int y)
    {
        return grid[x][y];
    }

    public Tile[][] getTiles(){
      return grid;
    }

    public void draw(RenderWindow w)
    {
        for (int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                grid[i][j].draw(w);
    }
}
