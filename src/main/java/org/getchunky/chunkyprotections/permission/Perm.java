package org.getchunky.chunkyprotections.permission;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dumptruckman
 */
public enum Perm {
    CREEPER_GRIEF ("chunky.grief.creeper"),
    TNT_IGNORE ("chunky.grief.tnt.ignore"),
    ;

    private Permission perm;

    Perm(Permission perm) {
        this.perm = perm;
    }

    Perm(String node) {
        this.perm = Bukkit.getServer().getPluginManager().getPermission(node);
    }

    private Permission getPerm() {
        return perm;
    }

    public boolean has(CommandSender sender) {
        return sender.hasPermission(perm);
    }

    public static void load(JavaPlugin plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();
        for (Perm perm : Perm.values()) {
            pm.addPermission(perm.getPerm());
        }
    }
}

