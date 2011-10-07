package org.getchunky.chunkyprotections.listener;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.getchunky.chunkyprotections.ProtectionsManager;
import org.getchunky.chunkyprotections.config.Config;

/**
 * @author dumptruckman, SwearWord
 */
public class PlayerEvents extends PlayerListener {

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Config.TNT_RESTRICT.getBoolean() && !event.isCancelled()) {
            if (event.getClickedBlock().getType() == Material.LEVER) ProtectionsManager.findTNT(event.getClickedBlock(), event.getPlayer());
            if (event.getClickedBlock().getType() == Material.STONE_BUTTON) ProtectionsManager.findTNT(event.getClickedBlock(), event.getPlayer());
            if (event.getClickedBlock().getType() == Material.WOOD_PLATE) ProtectionsManager.findTNT(event.getClickedBlock(), event.getPlayer());
            if (event.getClickedBlock().getType() == Material.STONE_PLATE) ProtectionsManager.findTNT(event.getClickedBlock(), event.getPlayer());
        }
    }
}
