package com.dumptruckman.chunkyprotections.listener;

import com.dumptruckman.chunky.ChunkyManager;
import com.dumptruckman.chunky.object.ChunkyChunk;
import com.dumptruckman.chunky.object.ChunkyPlayer;
import com.dumptruckman.chunky.permission.ChunkyAccessLevel;
import com.dumptruckman.chunky.permission.ChunkyPermissionChain;
import com.dumptruckman.chunky.permission.ChunkyPermissions;
import com.dumptruckman.chunkyprotections.ChunkyProtections;
import com.dumptruckman.chunkyprotections.permission.Perm;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dumptruckman
 */
public class ChunkyProtectionsEntityListener extends EntityListener {

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        if (event.getEntity() instanceof Monster) creeperGrief(event);
    }

    public void creeperGrief(EntityExplodeEvent event) {
        Monster monster = (Monster)event.getEntity();
        if (monster.getTarget() == null || !(monster.getTarget() instanceof Player)) return;

        Player player = (Player)monster.getTarget();
        if (!Perm.CREEPER_GRIEF.has(player)) return;
        
        ChunkyPlayer cPlayer = ChunkyManager.getChunkyPlayer(player);
        ChunkyChunk cChunk = cPlayer.getCurrentChunk();
        ChunkyAccessLevel access = ChunkyAccessLevel.NONE;
        boolean hasPerm = ChunkyPermissionChain.hasPerm(cChunk, cPlayer, ChunkyPermissions.Flags.DESTROY, access);
        if (!hasPerm) {
            event.setCancelled(true);
        }
    }
}
