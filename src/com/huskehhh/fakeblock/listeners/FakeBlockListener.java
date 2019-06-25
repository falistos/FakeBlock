package com.huskehhh.fakeblock.listeners;

import com.huskehhh.fakeblock.util.Utility;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class FakeBlockListener implements Listener {

    HashMap<String, Chunk> chunkTracking = new HashMap<String, Chunk>();

    /**
     * Method to listen for PlayerJoin, sending the Fake Packets when they do connect.
     * <p/>
     * Note: The delay is to ensure that when they are receiving the World Packets, they do not conflict or overwrite
     * the Fake packets for the Wall
     *
     * @param e - PlayerJoinEvent
     */

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Utility.sendFakeBlocks();
    }

    /**
     * Method to listen for Teleportation by a Player, sending the Fake Packets when they do teleport
     * <p/>
     * TODO: Implement a check if they are close to a Wall or not, saves processing / sending unneeded Packets
     * <p/>
     * Note: The delay is to ensure that when they are receiving the World Packets, they do not conflict or overwrite
     * the Fake packets for the Wall
     *
     * @param e - PlayerTeleportEvent
     */

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Utility.sendFakeBlocks();
    }

    /**
     * Method to listen for PlayerRespawn, sending the Fake Packets to them
     * <p/>
     * Note: The delay is to ensure that when they are receiving the World Packets, they do not conflict or overwrite
     * the Fake packets for the Wall
     *
     * @param e - PlayerRespawnEvent
     */

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Utility.sendFakeBlocks();
    }


    /**
     * Method to Listen for PlayerMove, checking if the Player is close to a Wall, if so, ensure they receive the Fake Packets
     *
     * @param e - PlayerMoveEvent
     */

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Utility.isNearWall(p, 10)) {
            Utility.sendFakeBlocks();
        }

        if (chunkTracking.containsKey(p.getName())) {
            if (chunkTracking.get(p.getName()) != p.getLocation().getChunk()) {
                chunkTracking.remove(p.getName());
                chunkTracking.put(p.getName(), p.getLocation().getChunk());
                Utility.processIndividual(p);
            }
        } else {
            chunkTracking.put(p.getName(), p.getLocation().getChunk());
        }

    }

    /**
     * Method to listen for PlayerInteract ensuring that if they click a Block that is apart of the Wall, send them the Wall again
     * to ensure that the client does not overwrite the Fake data
     * <p/>
     * Note: The delay is to ensure that when they are receiving the World Packets, they do not conflict or overwrite
     * the Fake packets for the Wall
     *
     * @param e - PlayerInteractEvent
     */

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getClickedBlock() != null) {
            Block b = e.getClickedBlock();

            if (Utility.isNear(b.getLocation(), p.getLocation(), 10) || Utility.isNearWall(p, 10)) {
                Utility.sendFakeBlocks();
            }
        }
    }

    /**
     * Method to listen for BlockBreak to send the Player the Wall packets again if they are close to the Wall
     * <p/>
     * Note: The delay is to ensure that when they are receiving the World Packets, they do not conflict or overwrite
     * the Fake packets for the Wall
     *
     * @param e - BlockBreakEvent
     */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (Utility.isNear(b.getLocation(), p.getLocation(), 10) || Utility.isNearWall(p, 10)) {
            Utility.sendFakeBlocks();
        }
    }


}