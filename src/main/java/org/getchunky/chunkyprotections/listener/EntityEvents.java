package org.getchunky.chunkyprotections.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyPermissions;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunky.permission.AccessLevel;
import org.getchunky.chunky.permission.PermissionChain;
import org.getchunky.chunkyprotections.ProtectionsManager;
import org.getchunky.chunkyprotections.config.Config;
import org.getchunky.chunkyprotections.permission.Perm;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.getchunky.chunkyprotections.util.Logging;

/**
 * @author dumptruckman
 */
public class EntityEvents extends EntityListener {

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        if (event.getEntity() instanceof Monster) creeperGrief(event);
        if (event.getEntity() instanceof TNTPrimed) tntRestrict(event);
    }

    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (Config.TNT_RESTRICT.getBoolean()) {
            Block block = event.getEntity().getLocation().getBlock();
            Logging.debug("Does " + ProtectionsManager.getLitTntBlocks() + " contain: " + block);
            if (ProtectionsManager.getLitTntBlocks().containsKey(block)) {
                Logging.debug("Tracked tnt turning into primed tnt");
                ProtectionsManager.getLitTntEntities().put(event.getEntity(), ProtectionsManager.getLitTntBlocks().get(block));
                ProtectionsManager.getLitTntBlocks().remove(block);
            }
        }
    }

    public void creeperGrief(EntityExplodeEvent event) {
        Monster monster = (Monster)event.getEntity();
        if (monster.getTarget() == null || !(monster.getTarget() instanceof Player)) return;

        Player player = (Player)monster.getTarget();
        if (!Perm.CREEPER_GRIEF.has(player)) return;
        
        ChunkyPlayer cPlayer = ChunkyManager.getChunkyPlayer(player);
        ChunkyChunk cChunk = cPlayer.getCurrentChunk();
        AccessLevel access = PermissionChain.hasPerm(cChunk, cPlayer, ChunkyPermissions.DESTROY);
        Logging.debug("Exploding monster targeting: " + player.getName() + " on land they " + (!access.causedDenial() ? "may" : "may not") + " destroy.");
        if (access.causedDenial()) {
            event.setCancelled(true);
        }
    }

    public void tntRestrict(EntityExplodeEvent event) {
        if (!Config.TNT_RESTRICT.getBoolean()) return;
        if (event.isCancelled()) return;

        if (!ProtectionsManager.getLitTntEntities().containsKey(event.getEntity())) {
            Logging.debug("Explosion not tracked");
            event.setCancelled(true);
            return;
        }
        Logging.debug("Tracked explosion happening");

        Player player = ProtectionsManager.getLitTntEntities().get(event.getEntity());
        ChunkyPlayer cPlayer = ChunkyManager.getChunkyPlayer(player);

        for (Block block : event.blockList()) {
            if (block.getType() == Material.TNT) {
                ProtectionsManager.getLitTntBlocks().put(block, player);
            }
            ChunkyChunk cChunk = ChunkyManager.getChunk(block);
            AccessLevel access = PermissionChain.hasPerm(cChunk, cPlayer, ChunkyPermissions.DESTROY);
            if (access.causedDenial()) {
                Logging.debug("Exploding tnt by: " + player.getName() + " on land they may not destroy.");
                event.setCancelled(true);
                break;
            }
        }

        ProtectionsManager.getLitTntEntities().remove(event.getEntity());
    }
}
