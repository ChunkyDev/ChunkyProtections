package org.getchunky.chunky.protections.listener;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.getchunky.chunky.protections.ProtectionsManager;
import org.getchunky.chunky.protections.config.Config;

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
