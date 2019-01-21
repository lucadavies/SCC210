
/*
*Class for the map which is displayed to the user
*
*/
import java.util.Enumeration;
import java.util.Vector;

public class Map extends MovingEntity {

    int seed;
    int grid[17][17];
    Enumeration tiles;

    Vector tiles1 = new Vector();
    Vector tiles2 = new Vector();
    Vector tiles3 = new Vector();
    Vector tiles4 = new Vector();
    Vector tiles5 = new Vector();
    Vector tiles6 = new Vector();


    public Map(int seed)
    {
        this.seed=seed;
    }

    public void generateMap()
    {
        switch (seed)
        {
            case 1:
                tiles = tiles1.elements;
            case 2:
                tiles = tiles2.elements;
            case 3:
                tiles = tiles3.elements;
            case 4:
                tiles = tiles4.elements;
            case 5:
                tiles = tiles5.elements;
            case 6:
                tiles = tiles6.elements;
        }

        for(int i=0; i<17;i++)
            for (int j=0; j<17;j++)
                grid[i][j]=tiles.nextElement();
    }

    public int getGrid()
    {
        return grid;
    }
}
