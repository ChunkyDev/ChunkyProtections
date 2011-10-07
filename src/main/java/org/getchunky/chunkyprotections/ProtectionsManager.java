package org.getchunky.chunkyprotections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.getchunky.chunkyprotections.config.Config;
import org.getchunky.chunkyprotections.permission.Perm;
import org.getchunky.chunkyprotections.util.Logging;

import java.util.HashMap;
import java.util.List;

/**
 * @author dumptruckman, SwearWord
 */
public class ProtectionsManager {

    private static HashMap<Block, Player> litTntBlocks = new HashMap<Block, Player>();
    private static HashMap<Entity, Player> litTntEntities = new HashMap<Entity, Player>();

    public static HashMap<Block, Player> getLitTntBlocks() {
        return litTntBlocks;
    }

    public static HashMap<Entity, Player> getLitTntEntities() {
        return litTntEntities;
    }

    public static void findTNT(Block block, Player player) {
        if (block.getRelative(BlockFace.EAST).getType() == Material.TNT) lightTNT(block.getRelative(BlockFace.EAST), player);
        if (block.getRelative(BlockFace.NORTH).getType() == Material.TNT) lightTNT(block.getRelative(BlockFace.NORTH), player);
        if (block.getRelative(BlockFace.WEST).getType() == Material.TNT) lightTNT(block.getRelative(BlockFace.WEST), player);
        if (block.getRelative(BlockFace.SOUTH).getType() == Material.TNT) lightTNT(block.getRelative(BlockFace.SOUTH), player);
        if (block.getRelative(BlockFace.UP).getType() == Material.TNT) lightTNT(block.getRelative(BlockFace.UP), player);
        if (block.getRelative(BlockFace.DOWN).getType() == Material.TNT) lightTNT(block.getRelative(BlockFace.DOWN), player);
    }

    private static void lightTNT(final Block block, final Player player) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChunkyProtections.getInstance(), new Runnable() {
            public void run() {
                List<Entity> entities = Bukkit.getServer().getWorld(block.getWorld().getName()).getEntities();
                for (Entity entity : entities) {
                    if (entity.getLocation().getBlock().equals(block)) {
                        Logging.debug(entity.toString());
                        break;
                    }
                }
            }
        });
        if (Config.TNT_RESTRICT.getBoolean())
            if (!Perm.TNT_IGNORE.has(player)) {
                Logging.debug("Tracking tnt lit by: " + player.getName());
                ProtectionsManager.getLitTntBlocks().put(block, player);
            }
    }
}
