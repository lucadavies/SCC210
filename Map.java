
/*
 *Class for the map which is displayed to the user
 *
 */

import org.jsfml.graphics.RenderWindow;

public class Map {

    private mapType seed;
    private Tile[][] grid = new Tile[17][17];
    private int walkers = 0;
    private int runners = 0;
    private int gunners = 0;
    private int tanks = 0;
    private int bosses = 0;
    private int totalAliens;

    //enum type for the maps public so u can use it outside of this
    public enum mapType {
        FARM,
        FOREST,
        RIVER,
        CAVE,
        PLANET,
        SHIP,
        BOSS,
        TEST
    }

    /**
     * Generates a custom specific map based on the mapType took in as a param
     * @param takes in a mapType variable that is used to select the map
     */
    public Map(mapType seed) {
        this.seed = seed;
        switch (seed) {
            case FARM:
                walkers = 20;
                //CORE
                this.setBackground("art/map/farm3.png");
                this.setBoundaries("art/map/farm3bush.png");

                //corn
                this.addCollidingObject(11, 8, "art/map/farm3corn.png");
                this.addCollidingObject(12, 3, "art/map/farm3corn.png");
                this.addCollidingObject(5, 2, "art/map/farm3corn.png");
                this.addCollidingObject(4, 11, "art/map/farm3corn.png");

                //top entrance
                this.addCollidingObject(11, 0, "art/map/farm3fence2.png");
                this.addCollidingObject(10, 0, "art/map/farm3fence2.png");
                this.addNonCollidingObject(9, 0, "art/map/farm3.png");
                this.addNonCollidingObject(8, 0, "art/map/farm3.png");
                this.addNonCollidingObject(7, 0, "art/map/farm3.png");
                this.addCollidingObject(6, 0, "art/map/farm3fence2.png");

                //rocks
                this.addCollidingObject(8, 5, "art/map/farm3rock.png");
                this.addCollidingObject(3, 7, "art/map/farm3rocks.png");
                this.addCollidingObject(12, 13, "art/map/farm3rock.png");

                //right entrance
                this.addCollidingObject(16, 6, "art/map/farm3bushmail.png");
                this.addNonCollidingObject(16, 7, "art/map/mudgrass.png");
                this.addNonCollidingObject(16, 8, "art/map/mudgrass.png");
                this.addNonCollidingObject(16, 9, "art/map/mudgrass.png");

                this.addCollidingObject(15, 10, "art/map/farm3rock.png");
                this.addCollidingObject(15, 6, "art/map/farm3rock.png");
                this.addCollidingObject(16, 11, "art/map/farm3rocks.png");
                this.addCollidingObject(16, 5, "art/map/farm3rocks.png");

                //bottom entrance
                for (int i = 3; i < 17; i++)
                    this.addCollidingObject(i, 16, "art/map/farm2fence1.png");
                this.addNonCollidingObject(7, 16, "art/map/farm2.png");
                this.addNonCollidingObject(8, 16, "art/map/farm2.png");
                this.addNonCollidingObject(9, 16, "art/map/farm2.png");


                //lower field
                for (int i = 1; i < 16; i++)
                    this.addNonCollidingObject(i, 15, "art/map/farm1.png");

                //left field
                for (int i = 1; i < 16; i++)
                    this.addCollidingObject(0, i, "art/map/farm2corn.png");
                this.addCollidingObject(1, 15, "art/map/farm2corn.png");

                this.addNonCollidingObject(0, 7, "art/map/farm2.png");
                this.addNonCollidingObject(0, 8, "art/map/farm2.png");
                this.addNonCollidingObject(0, 9, "art/map/farm2.png");

                break;

            case FOREST:
                walkers = 15;
                runners = 15;
                //core
                this.setBackground("art/map/grass.png");
                this.setBoundaries("art/map/grassverticalfence.png");

                //top
                for (int i = 1; i < 16; i++)
                    this.addCollidingObject(i, 0, "art/map/grassfence.png");

                //bottom
                for (int i = 1; i < 16; i++)
                    this.addCollidingObject(i, 16, "art/map/grassfence.png");

                //corners
                this.addCollidingObject(0, 0, "art/map/grassrocksivy.png");
                this.addCollidingObject(0, 16, "art/map/grassrocksivy.png");
                this.addCollidingObject(16, 16, "art/map/grassrocksivy.png");
                this.addCollidingObject(16, 0, "art/map/grassrocksivy.png");

                //objects
                this.addCollidingObject(13, 10, "art/map/grasscuttree.png");
                this.addCollidingObject(12, 10, "art/map/grasscuttree.png");
                this.addCollidingObject(11, 10, "art/map/grasscuttree.png");
                this.addCollidingObject(13, 9, "art/map/grasscuttree.png");
                this.addCollidingObject(12, 8, "art/map/grasscuttree.png");

                this.addCollidingObject(3, 3, "art/map/grasscuttree.png");

                this.addCollidingObject(13, 3, "art/map/grasscuttree.png");

                this.addCollidingObject(3, 10, "art/map/grasscuttree.png");

                this.addCollidingObject(10, 5, "art/map/grassrock.png");
                this.addCollidingObject(9, 5, "art/map/grassrock.png");

                this.addCollidingObject(9, 6, "art/map/grassrock.png");

                this.addCollidingObject(8, 13, "art/map/grassrocks.png");
                this.addCollidingObject(9, 13, "art/map/grassrocks.png");
                //this.addCollidingObject(8, 11, "art/map/grassrocks.png");
                //this.addCollidingObject(7, 11, "art/map/grassrocks.png");
                this.addCollidingObject(8, 10, "art/map/grassrocks.png");
                this.addCollidingObject(6, 10, "art/map/grassrocks.png");
                this.addCollidingObject(7, 9, "art/map/grassrocks.png");
                this.addCollidingObject(6, 9, "art/map/grassrocks.png");

                this.addCollidingObject(13, 13, "art/map/grassapple.png");
                this.addCollidingObject(14, 14, "art/map/grassapple.png");

                this.addCollidingObject(3, 6, "art/map/grassrock.png");
                this.addCollidingObject(3, 7, "art/map/grassrock.png");
                this.addCollidingObject(4, 6, "art/map/grassrock.png");
                this.addCollidingObject(5, 6, "art/map/grassrock.png");

                this.addCollidingObject(6, 3, "art/map/grassapple.png");
                this.addCollidingObject(7, 2, "art/map/grassapple.png");
                this.addCollidingObject(6, 1, "art/map/grasscuttree.png");

                //right exit
                this.addCollidingObject(16, 6, "art/map/grassrocksivy.png");
                this.addCollidingObject(16, 10, "art/map/grassrocksivy.png");
                this.addNonCollidingObject(16, 7, "art/map/mudgrass.png");
                this.addNonCollidingObject(16, 8, "art/map/mudgrass.png");
                this.addNonCollidingObject(16, 9, "art/map/mudgrass.png");

                //left exit
                this.addNonCollidingObject(0, 7, "art/map/grass.png");
                this.addNonCollidingObject(0, 8, "art/map/grass.png");
                this.addNonCollidingObject(0, 9, "art/map/grass.png");

                //top exit
                this.addNonCollidingObject(7, 0, "art/map/grass.png");
                this.addNonCollidingObject(8, 0, "art/map/grass.png");
                this.addNonCollidingObject(9, 0, "art/map/grass.png");

                //bottom exit
                this.addNonCollidingObject(7, 16, "art/map/mudgrass.png");
                this.addNonCollidingObject(8, 16, "art/map/mudgrass.png");
                this.addNonCollidingObject(9, 16, "art/map/mudgrass.png");

                //bottom mushroom farm
                this.addCollidingObject(2, 16, "art/map/mudgrassmushroom.png");
                this.addCollidingObject(3, 16, "art/map/mudgrassmushroom.png");
                this.addCollidingObject(4, 16, "art/map/mudgrassmushroom.png");
                this.addCollidingObject(5, 16, "art/map/mudgrassmushroom.png");
                this.addCollidingObject(2, 15, "art/map/grassmushroom.png");
                this.addCollidingObject(3, 15, "art/map/grassmushroom.png");
                this.addCollidingObject(4, 15, "art/map/grassmushroom.png");
                this.addCollidingObject(5, 15, "art/map/grassmushroom.png");
                break;
            case RIVER:
                runners = 30;
                gunners = 10;
                //CORE
                this.setBackground("art/map/mudgrass.png");
                this.setBoundariesRiver("art/map/mudgrassivy.png");

                //river top
                for (int i = 0; i < 17; i++)
                    this.addWaterObject(i, 0, "art/map/water.png");
                for (int i = 1; i < 16; i++)
                    this.addCollidingObject(i, 1, "art/map/mudgrassfence.png");
                for (int i = 5; i < 12; i++)
                    this.addWaterObject(i, 1, "art/map/water.png");
                for (int i = 5; i < 12; i++)
                    this.addWaterObject(i, 2, "art/map/water.png");

                this.addWaterObject(7, 3, "art/map/water.png");
                this.addWaterObject(8, 3, "art/map/water.png");
                this.addWaterObject(9, 3, "art/map/water.png");

                this.addCollidingObject(12, 1, "art/map/mudgrassbush.png");
                this.addCollidingObject(12, 2, "art/map/mudgrassbush.png");
                this.addCollidingObject(12, 3, "art/map/mudgrassbush.png");
                this.addCollidingObject(4, 1, "art/map/mudgrassbush.png");
                this.addCollidingObject(4, 2, "art/map/mudgrassbush.png");
                this.addCollidingObject(4, 3, "art/map/mudgrassbush.png");
                this.addCollidingObject(10, 3, "art/map/mudgrassfence.png");
                this.addCollidingObject(11, 3, "art/map/mudgrassfence.png");
                this.addCollidingObject(5, 3, "art/map/mudgrassfence.png");
                this.addCollidingObject(6, 3, "art/map/mudgrassfence.png");

                //top bridge
                for (int i = 0; i < 4; i++) {
                    this.addNonCollidingObject(7, i, "art/map/waterbridgevertical.png");
                    this.addNonCollidingObject(8, i, "art/map/waterbridgevertical.png");
                    this.addNonCollidingObject(9, i, "art/map/waterbridgevertical.png");
                }
                //right exit
                this.addNonCollidingObject(16, 9, "art/map/mudgrass.png");
                this.addNonCollidingObject(16, 8, "art/map/mudgrass.png");
                this.addNonCollidingObject(16, 7, "art/map/mudgrass.png");

                this.addCollidingObject(16, 6, "art/map/mudgrasscuttree.png");
                this.addCollidingObject(16, 10, "art/map/mudgrasscuttree.png");

                //bottom exit
                this.addNonCollidingObject(7, 16, "art/map/mudgrass.png");
                this.addNonCollidingObject(8, 16, "art/map/mudgrass.png");
                this.addNonCollidingObject(9, 16, "art/map/mudgrass.png");
                this.addCollidingObject(6, 16, "art/map/mudgrasslog.png");
                this.addCollidingObject(10, 16, "art/map/mudgrasslog.png");

                //left exit


                this.addNonCollidingObject(0, 7, "art/map/corruptedmudglow.png");
                this.addNonCollidingObject(0, 8, "art/map/corruptedmudglow.png");
                this.addNonCollidingObject(0, 9, "art/map/corruptedmudglow.png");
                this.addCollidingObject(0, 6, "art/map/mudgrassrocks.png");
                this.addCollidingObject(0, 10, "art/map/mudgrassrocks.png");

                //objects
                this.addCollidingObject(10, 13, "art/map/mudgrasslog.png");
                this.addCollidingObject(11, 13, "art/map/mudgrasslog.png");
                this.addCollidingObject(9, 13, "art/map/mudgrasslog.png");
                this.addCollidingObject(10, 12, "art/map/mudgrassmushroom.png");

                this.addCollidingObject(4, 12, "art/map/mudgrasslog.png");
                this.addCollidingObject(4, 11, "art/map/mudgrasslog.png");
                this.addCollidingObject(5, 10, "art/map/mudgrassrocks.png");
                this.addCollidingObject(5, 13, "art/map/mudgrassrocks.png");

                this.addCollidingObject(13, 7, "art/map/mudgrasslog.png");
                this.addCollidingObject(13, 8, "art/map/mudgrasslog.png");
                this.addCollidingObject(13, 6, "art/map/mudgrassrock.png");
                this.addCollidingObject(12, 7, "art/map/mudgrassrock.png");

                this.addCollidingObject(4, 7, "art/map/mudgrasscuttree.png");
                this.addCollidingObject(5, 7, "art/map/mudgrasscuttree.png");
                this.addCollidingObject(4, 6, "art/map/mudgrasscuttree.png");
                this.addCollidingObject(5, 6, "art/map/mudgrasscuttree.png");

                break;
            case CAVE:
                runners = 20;
                gunners = 10;
                tanks = 10;
                //CORE
                this.setBackground("art/map/stone.png");
                this.setBoundariesCave("art/map/stonerocks.png");

                //water top
                for (int i = 0; i <= 16; i++)
                    this.addWaterObject(i, 0, "art/map/water.png");

                //water bottom
                for (int i = 0; i <= 16; i++)
                    this.addWaterObject(i, 16, "art/map/water.png");

                //water vertical mid
                for (int i = 0; i <= 16; i++)
                    this.addWaterObject(8, i, "art/map/water.png");

                //rest of water
                this.addWaterObject(7, 1, "art/map/water.png");
                this.addWaterObject(9, 1, "art/map/water.png");
                this.addWaterObject(7, 15, "art/map/water.png");
                this.addWaterObject(9, 15, "art/map/water.png");

                //bridge MID
                this.addNonCollidingObject(7, 7, "art/map/stonebridge.png");
                this.addNonCollidingObject(8, 7, "art/map/waterbridge.png");
                this.addNonCollidingObject(9, 7, "art/map/stonebridge.png");
                this.addNonCollidingObject(7, 8, "art/map/stonebridge.png");
                this.addNonCollidingObject(8, 8, "art/map/waterbridge.png");
                this.addNonCollidingObject(9, 8, "art/map/stonebridge.png");
                this.addNonCollidingObject(7, 9, "art/map/stonebridge.png");
                this.addNonCollidingObject(8, 9, "art/map/waterbridge.png");
                this.addNonCollidingObject(9, 9, "art/map/stonebridge.png");

                //bridge top
                for (int i = 0; i < 2; i++) {
                    this.addNonCollidingObject(7, i, "art/map/stonebridgevertical.png");
                    this.addNonCollidingObject(8, i, "art/map/stonebridgevertical.png");
                    this.addNonCollidingObject(9, i, "art/map/stonebridgevertical.png");
                }
                this.addNonCollidingObject(7, 0, "art/map/waterbridgevertical.png");
                this.addNonCollidingObject(8, 0, "art/map/waterbridgevertical.png");
                this.addNonCollidingObject(9, 0, "art/map/waterbridgevertical.png");

                this.addNonCollidingObject(7, 2, "art/map/stone.png");
                this.addNonCollidingObject(8, 2, "art/map/stone.png");
                this.addNonCollidingObject(9, 2, "art/map/stone.png");

                this.addNonCollidingObject(7, 3, "art/map/stone.png");
                this.addNonCollidingObject(8, 3, "art/map/stone.png");
                this.addNonCollidingObject(9, 3, "art/map/stone.png");

                this.addNonCollidingObject(7, 4, "art/map/stone.png");
                this.addNonCollidingObject(8, 4, "art/map/stone.png");
                this.addNonCollidingObject(9, 4, "art/map/stone.png");

                //bridge top

                this.addNonCollidingObject(7, 1, "art/map/stonebridgevertical.png");
                this.addNonCollidingObject(8, 1, "art/map/stonebridgevertical.png");
                this.addNonCollidingObject(9, 1, "art/map/stonebridgevertical.png");

                this.addNonCollidingObject(7, 0, "art/map/waterbridgevertical.png");
                this.addNonCollidingObject(8, 0, "art/map/waterbridgevertical.png");
                this.addNonCollidingObject(9, 0, "art/map/waterbridgevertical.png");

                for (int i = 2; i < 5; i++) {
                    this.addNonCollidingObject(7, i, "art/map/stone.png");
                    this.addNonCollidingObject(8, i, "art/map/stone.png");
                    this.addNonCollidingObject(9, i, "art/map/stone.png");
                }

                //bridge bottom

                this.addNonCollidingObject(7, 15, "art/map/stonebridgevertical.png");
                this.addNonCollidingObject(8, 15, "art/map/stonebridgevertical.png");
                this.addNonCollidingObject(9, 15, "art/map/stonebridgevertical.png");

                this.addNonCollidingObject(7, 16, "art/map/waterbridgevertical.png");
                this.addNonCollidingObject(8, 16, "art/map/waterbridgevertical.png");
                this.addNonCollidingObject(9, 16, "art/map/waterbridgevertical.png");

                for (int i = 12; i < 15; i++) {
                    this.addNonCollidingObject(7, i, "art/map/stone.png");
                    this.addNonCollidingObject(8, i, "art/map/stone.png");
                    this.addNonCollidingObject(9, i, "art/map/stone.png");
                }

                //left
                this.addNonCollidingObject(0, 7, "art/map/stoneluca.png");
                this.addNonCollidingObject(0, 8, "art/map/stoneluca.png");
                this.addNonCollidingObject(0, 9, "art/map/stoneluca.png");

                //right
                this.addNonCollidingObject(16, 7, "art/map/stoneluca.png");
                this.addNonCollidingObject(16, 8, "art/map/stoneluca.png");
                this.addNonCollidingObject(16, 9, "art/map/stoneluca.png");

                //objects
                this.addCollidingObject(4, 4, "art/map/stonerock.png");
                this.addCollidingObject(3, 7, "art/map/stonerock.png");
                this.addCollidingObject(4, 11, "art/map/stonegoo.png");
                this.addCollidingObject(3, 12, "art/map/stonegoo.png");

                this.addCollidingObject(13, 3, "art/map/stonemushroom.png");
                this.addCollidingObject(12, 7, "art/map/stonegoo.png");
                this.addCollidingObject(13, 8, "art/map/stonegoo.png");
                this.addCollidingObject(13, 12, "art/map/stonemushroom.png");

                break;
            case SHIP:
                walkers = 20;
                runners = 20;
                gunners = 20;
                //CORE
                this.setBackground("art/map/ship.png");
                this.setBoundaries("art/map/shippipes.png");

                //top
                this.addNonCollidingObject(8, 0, "art/map/ship.png");
                this.addNonCollidingObject(9, 0, "art/map/ship.png");
                this.addNonCollidingObject(7, 0, "art/map/ship.png");

                //bottom
                this.addNonCollidingObject(8, 16, "art/map/ship.png");
                this.addNonCollidingObject(9, 16, "art/map/ship.png");
                this.addNonCollidingObject(7, 16, "art/map/ship.png");

                //left
                this.addNonCollidingObject(0, 7, "art/map/ship.png");
                this.addNonCollidingObject(0, 8, "art/map/ship.png");
                this.addNonCollidingObject(0, 9, "art/map/ship.png");

                //right
                this.addNonCollidingObject(16, 7, "art/map/ship.png");
                this.addNonCollidingObject(16, 8, "art/map/ship.png");
                this.addNonCollidingObject(16, 9, "art/map/ship.png");

                //objects
                this.addCollidingObject(12, 3, "art/map/shipterminal.png");
                this.addCollidingObject(8, 3, "art/map/shipterminal.png");
                this.addCollidingObject(4, 3, "art/map/shipterminal.png");
                this.addCollidingObject(6, 6, "art/map/shipterminal.png");
                this.addCollidingObject(10, 6, "art/map/shipterminal.png");
                this.addCollidingObject(12, 9, "art/map/shipterminal.png");
                this.addCollidingObject(8, 9, "art/map/shipterminal.png");
                this.addCollidingObject(4, 9, "art/map/shipterminal.png");
                this.addCollidingObject(6, 12, "art/map/shipterminal.png");
                this.addCollidingObject(10, 12, "art/map/shipterminal.png");
                this.addCollidingObject(13, 14, "art/map/shipterminal.png");
                this.addCollidingObject(3, 14, "art/map/shipterminal.png");
                break;
            case PLANET:
                walkers = 30;
                runners = 20;
                gunners = 20;
                tanks = 20;
                //CORE
                this.setBackground("art/map/planet1.png");
                this.setBoundaries("art/map/planet2rocks.png");

                //top
                this.addNonCollidingObject(7, 0, "art/map/planetspecial2.png");
                this.addNonCollidingObject(8, 0, "art/map/planetspecial2.png");
                this.addNonCollidingObject(9, 0, "art/map/planetspecial2.png");

                //bottom
                this.addNonCollidingObject(9, 16, "art/map/planetspecial2.png");
                this.addNonCollidingObject(8, 16, "art/map/planetspecial2.png");
                this.addNonCollidingObject(7, 16, "art/map/planetspecial2.png");

                //right
                this.addNonCollidingObject(16, 9, "art/map/planetspecial2.png");
                this.addNonCollidingObject(16, 8, "art/map/planetspecial2.png");
                this.addNonCollidingObject(16, 7, "art/map/planetspecial2.png");

                //left

                this.addNonCollidingObject(0, 7, "art/map/planetspecial1.png");
                this.addNonCollidingObject(0, 8, "art/map/planetspecial1.png");
                this.addNonCollidingObject(0, 9, "art/map/planetspecial1.png");

                //objects
                this.addCollidingObject(8, 9, "art/map/planet1mushroom.png");
                this.addCollidingObject(8, 10, "art/map/planet1mushroom.png");
                this.addCollidingObject(9, 10, "art/map/planet1mushroom.png");
                this.addCollidingObject(7, 10, "art/map/planet1mushroom.png");
                this.addCollidingObject(8, 11, "art/map/planet1mushroom.png");
                this.addCollidingObject(8, 12, "art/map/planet1mushroom.png");

                this.addCollidingObject(10, 3, "art/map/planet1mushroom.png");
                this.addCollidingObject(9, 3, "art/map/planet1mushroom.png");
                this.addCollidingObject(9, 4, "art/map/planet1mushroom.png");
                this.addCollidingObject(8, 4, "art/map/planet1mushroom.png");
                this.addCollidingObject(8, 5, "art/map/planet1mushroom.png");

                this.addCollidingObject(13, 5, "art/map/planet1mushroom.png");
                this.addCollidingObject(12, 6, "art/map/planet1mushroom.png");
                this.addCollidingObject(13, 7, "art/map/planet1mushroom.png");

                this.addCollidingObject(13, 11, "art/map/planet1mushroom.png");
                this.addCollidingObject(12, 12, "art/map/planet1mushroom.png");
                this.addCollidingObject(12, 13, "art/map/planet1mushroom.png");

                this.addCollidingObject(3, 3, "art/map/planet1mushroom.png");
                this.addCollidingObject(4, 4, "art/map/planet1mushroom.png");

                this.addCollidingObject(4, 7, "art/map/planet1mushroom.png");
                this.addCollidingObject(3, 8, "art/map/planet1mushroom.png");

                this.addCollidingObject(3, 11, "art/map/planet1mushroom.png");
                this.addCollidingObject(3, 12, "art/map/planet1mushroom.png");
                this.addCollidingObject(4, 13, "art/map/planet1mushroom.png");

                break;
            case BOSS:
                bosses = 1;
                this.setBackground("art/map/planet1.png");
                this.setBoundaries("art/map/planet2rocks.png");
                break;
            case TEST:
                this.setBackground("art/map/debug/b.png");
                for (int i = 0; i < 17; i++) {
                    for (int j = 0; j < 17; j++) {
                        if (i == 0 || i == 16) {
                            this.addCollidingObject(i, j, "art/map/debug/" + j + ".png");
                        } else if (j == 0 || j == 16) {
                            this.addCollidingObject(i, j, "art/map/debug/" + i + ".png");
                        }
                    }
                }
        }
        totalAliens = walkers + runners + gunners + tanks + bosses;
    }

    /**
     * Creates the map's background by setting all the tiles in the tile matrix to one single image
     * @param backgroundTile the image tiles will be set to
     */
    private void setBackground(String backgroundTile) {
        for (int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                addNonCollidingObject(i, j, backgroundTile);
    }

    /**
     * Creates the outline boundaries of the map by setting all the tiles on the edges to one image
     * also turning walking and shooting collision ON for those edges
     * @param image the image the boundaries will be set to
     */
    private void setBoundaries(String image) {
        for (int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                if (i == 0 || i == 16 || j == 0 || j == 16) {
                    grid[i][j] = new Tile(image, i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
                    grid[i][j].setWalkThrough(false);
                    grid[i][j].setShootThrough(false);
                }
    }

    /**
     * Uses x and y to set a specific tile to a specific image/object with walk and shoot collision ON
     * @param x horizontal coordinate for the tile in the tile matrix
     * @param y vertical coordinate for the tile in the tile matrix
     * @param image picture the tile will be set to
     */
    private void addCollidingObject(int x, int y, String image) {
        grid[x][y] = new Tile(image, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
        grid[x][y].setWalkThrough(false);
        grid[x][y].setShootThrough(false);
    }

    /**
     * Uses x and y to set a specific tile to a specific image/object with walk collision ON and shoot collision OFF
     * @param x horizontal coordinate for the tile in the tile matrix
     * @param y vertical coordinate for the tile in the tile matrix
     * @param image picture the tile will be set to
     */
    private void addWaterObject(int x, int y, String image) {
        grid[x][y] = new Tile(image, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
        grid[x][y].setWalkThrough(false);
        grid[x][y].setShootThrough(true);
    }

    /**
     * Uses x and y to set a specific tile to a specific image/object with walk and shoot collision OFF
     * @param x horizontal coordinate for the tile in the tile matrix
     * @param y vertical coordinate for the tile in the tile matrix
     * @param image picture the tile will be set to
     */
    private void addNonCollidingObject(int x, int y, String image) {
        grid[x][y] = new Tile(image, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
        grid[x][y].setWalkThrough(true);
        grid[x][y].setShootThrough(true);
    }

    /**
     * Acts almost the same as the normal setBoundaries class but is custom tailored (not a square box) to the cave map's layout
     * @param image the image the boundaries will be set to
     */
    private void setBoundariesCave(String image) {
        for (int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                if (i == 0 || i == 16 || j == 1 || j == 15 || i == 7 || i == 9) {
                    grid[i][j] = new Tile(image, i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
                    grid[i][j].setWalkThrough(false);
                    grid[i][j].setShootThrough(false);
                }
    }

    /**
     * Acts almost the same as the normal setBoundaries class but is custom tailored (not a square box) to the river map's layout
     * @param image the image the boundaries will be set to
     */
    private void setBoundariesRiver(String image) {
        for (int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                if (i == 0 || i == 16 || j == 1 || j == 16) {
                    grid[i][j] = new Tile(image, i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
                    grid[i][j].setWalkThrough(false);
                    grid[i][j].setShootThrough(false);
                }
    }

    /**
     * Returns one specific tile
     * @param x horizontal coordinate for the tile in the tile matrix
     * @param y vertical coordinate for the tile in the tile matrix
     * @return returns the specific tile associated with the x and y the user inputed
     */
    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

    /**
     * @return returns grid matrix for further usage
     */
    public Tile[][] getTiles() {
        return grid;
    }

    /**
     * @return returns the seed of the current map
     */
    public mapType getType() {
        return seed;
    }

    /**
     * @return returns the number of all type of aliens remaining
     */
    public int getNumRemainingAliens() {
        return walkers + runners + gunners + tanks;
    }

    /**
     * Reduces the number of aliens required to be spawned
     * @param al takes in a specific type of alien class (Runner.class,Tank.class etc.)
     */
    public void decrementAlien(Class<?> al) {
        if (al == Walker.class) {
            walkers--;
        }
        else if (al == Runner.class) {
            runners--;
        }
        else if (al == Gunner.class) {
            gunners--;
        }
        else if (al == Tank.class) {
            tanks--;
        }
        else if (al == Boss.class) {
            bosses--;
        }
    }

    /**
     * @return Returns true or false by checking if the number of walkers that need to be spawned has reached zero or not
     */
    public boolean needsWalker() {
        return walkers > 0;
    }
    /**
     * @return Returns true or false by checking if the number of runners that need to be spawned has reached zero or not
     */
    public boolean needsRunner() {
        return runners > 0;
    }
    /**
     * @return Returns true or false by checking if the number of gunners that need to be spawned has reached zero or not
     */
    public boolean needsGunner() {
        return gunners > 0;
    }
    /**
     * @return Returns true or false by checking if the number of tanks that need to be spawned has reached zero or not
     */
    public boolean needsTank() {
        return tanks > 0;
    }
    /**
     * @return Returns true or false by checking if the number of bosses that need to be spawned has reached zero or not
     */
    public boolean needsBoss() {
        return bosses > 0;
    }

    /**
     * @return returns the total number of aliens that need to be killed for the current map
     */
    public int getTotalAliens() {
        return totalAliens;
    }

    /**
     * Displays the tile grid in the window took as a parameter
     * @param w takes a jsfml created windows
     */
    public void draw(RenderWindow w) {
        for (int i = 0; i < 17; i++)
            for (int j = 0; j < 17; j++)
                grid[i][j].draw(w);
    }
}
