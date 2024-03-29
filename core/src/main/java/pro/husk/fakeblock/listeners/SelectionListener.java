package pro.husk.fakeblock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pro.husk.fakeblock.objects.Config;
import pro.husk.fakeblock.objects.WallObject;

public class SelectionListener implements Listener {

    @EventHandler
    public void playerSelect(PlayerInteractEvent event) {
        String fakeBlockTitle = ChatColor.BLACK + "[" + ChatColor.AQUA + "FakeBlock" + ChatColor.BLACK + "] ";

        Player player = event.getPlayer();

        if (Config.isSelecting(player.getName())) {
            if (event.getHand() == EquipmentSlot.HAND) {
                Block block = event.getClickedBlock();

                Config config = Config.getCurrentConfigurations().get(player.getName());

                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    player.sendMessage(fakeBlockTitle + ChatColor.GREEN + "Location saved.");
                    config.setLocation2(block.getLocation());
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    player.sendMessage(fakeBlockTitle + ChatColor.GREEN + "Location saved.");
                    config.setLocation1(block.getLocation());
                }

                if (config.getLocation1() != null && config.getLocation2() != null) {
                    // TODO: Prompt for material type and listen for chat or use @Conversation

                    // Create WallObject
                    WallObject wallObject = config.createWallObject();

                    // Remove config object
                    config.remove();

                    // Save Wall to config
                    wallObject.saveWall();

                    player.sendMessage(fakeBlockTitle + ChatColor.GREEN + "Wall '" + wallObject.getName() + "' created. " +
                            "Please refer to the configuration if you wish to make changes");
                }

                event.setCancelled(true);
            }
        }
    }
}