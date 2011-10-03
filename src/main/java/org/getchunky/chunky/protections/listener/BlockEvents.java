package org.getchunky.chunky.protections.listener;

import org.bukkit.Material;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getchunky.chunky.protections.ProtectionsManager;
import org.getchunky.chunky.protections.config.Config;
import org.getchunky.chunky.protections.util.Logging;

/**
 * @author dumptruckman, SwearWord
 */
public class BlockEvents extends BlockListener {

    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            Logging.debug("F&S on: " + event.getBlock().getType());
            if (event.getBlock().getType() == Material.TNT) ProtectionsManager.findTNT(event.getBlock(), event.getPlayer());
        }
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        Logging.debug(event.getBlockPlaced().getType().toString());
        if (Config.TNT_RESTRICT.getBoolean() && !event.isCancelled()) {
            if (event.getBlockPlaced().getType() == Material.REDSTONE_TORCH_ON) redstoneTorch(event);
        }
    }

    public void redstoneTorch(BlockPlaceEvent event) {
        ProtectionsManager.findTNT(event.getBlock(), event.getPlayer());
    }
}
