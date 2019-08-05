package com.huskehhh.fakeblock.objects;

import org.bukkit.Location;

public class Config {

    Location loc1, loc2;
    String worldname;
    String name;
    String blockname;


    /**
     * Method to store location 1 of wall during creation
     *
     * @param loc1 location 1 of wall
     */

    public void setLocation1(Location loc1) {
        this.loc1 = loc1;
    }

    /**
     * Method to store location 2 of wall during creation
     *
     * @param loc2 location 2 of wall
     */

    public void setLocation2(Location loc2) {
        this.loc2 = loc2;
    }

    /**
     * Set name of wall
     *
     * @param name of wall
     */
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set block name of the Wall to be created
     *
     * @param blockname - block name Given through command
     */
    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }


    /**
     * Create a Wall objects from stored data.
     *
     * @return Wall objects from data
     */

    public Wall createObject() {
        return new Wall(loc1, loc2, name, blockname);
    }
}
