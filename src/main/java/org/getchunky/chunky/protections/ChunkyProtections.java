package org.getchunky.chunky.protections;

import org.getchunky.chunky.protections.config.Config;
import org.getchunky.chunky.protections.listener.BlockEvents;
import org.getchunky.chunky.protections.listener.EntityEvents;
import org.getchunky.chunky.protections.listener.PlayerEvents;
import org.getchunky.chunky.protections.locale.Language;
import org.getchunky.chunky.protections.util.Logging;
import org.blockface.bukkitstats.CallHome;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * @author dumptruckman
 */
public class ChunkyProtections extends JavaPlugin {

    private static ChunkyProtections instance = null;

    private final EntityEvents entityListener = new EntityEvents();
    private final BlockEvents blockListener = new BlockEvents();
    private final PlayerEvents playerListener = new PlayerEvents();
    

    final public void onDisable() {
        // Display disable message/version info
        Logging.info("disabled.", true);
    }

    final public void onEnable() {
        // Store the instance of this plugin
        instance = this;

        // Grab the PluginManager
        final PluginManager pm = getServer().getPluginManager();

        // Load logger
        Logging.load();

        // Checks for Chunky
        if (pm.getPlugin("Chunky") == null) {
            Logging.severe("Chunky not found!  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the configuration
        try {
            Config.load();
        } catch (IOException e) {  // Catch errors loading the config file and exit out if found.
            Logging.severe("Encountered an error while loading the configuration file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the language
        try {
            Language.load();
        } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
            Logging.severe("Encountered an error while loading the language file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Register Events
        registerEvents();

        //Call Home (usage stats)
        CallHome.load(this);

        // Display enable message/version info
        Logging.info("enabled.", true);
    }

    final public void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        // Event registering goes here
        pm.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.EXPLOSION_PRIME, entityListener, Event.Priority.Highest, this);
        //pm.registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Highest, this);
    }

    public static ChunkyProtections getInstance() {
        return instance;
    }
}
