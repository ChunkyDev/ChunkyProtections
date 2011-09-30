package com.dumptruckman.chunkyprotections.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dumptruckman
 */
public enum Perm {
    EXAMPLE (new Permission("ChunkyProtections.example", PermissionDefault.OP)),
    ;

    private Permission perm;

    Perm(Permission perm) {
        this.perm = perm;
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

