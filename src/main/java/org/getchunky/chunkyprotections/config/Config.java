package org.getchunky.chunkyprotections.config;

import org.getchunky.chunkyprotections.ChunkyProtections;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman, SwearWord
 */
public enum Config {
    LANGUAGE_FILE_NAME("settings.language_file", "english.yml", "# This is the language file you wish to use."),
    DEBUG_MODE("settings.debug_mode.enable", false, "# Enables debug mode."),
    DATA_SAVE_PERIOD("settings.data.save_every", 300, "# This is often plugin data is written to the disk."),

    TNT_RESTRICT("griefing.tnt_restriction", true,
            "# This will cause TNT not lit by flint and steel to not break blocks.",
            "# TNT lit by flint and steel will only break blocks if the user has destroy permissions where it lands."),
    ;

    private String path;
    private Object def;
    private String[] comments;

    Config(String path, Object def, String...comments) {
        this.path = path;
        this.def = def;
        this.comments = comments;
    }

    public final Boolean getBoolean() {
        return config.getBoolean(path, (Boolean)def);
    }

    public final Integer getInt() {
        return config.getInt(path, (Integer)def);
    }

    public final String getString() {
        return config.getString(path, (String)def);
    }

    /**
     * Retrieves the path for a config option
     * @return The path for a config option
     */
    private String getPath() {
        return path;
    }

    /**
     * Retrieves the default value for a config path
     * @return The default value for a config path
     */
    private Object getDefault() {
        return def;
    }

    /**
     * Retrieves the comment for a config path
     * @return The comments for a config path
     */
    private String[] getComments() {
        if (comments != null) {
            return comments;
        }

        String[] comments = new String[1];
        comments[0] = "";
        return comments;
    }

    private static CommentedConfiguration config;

    /**
     * Loads the configuration data into memory and sets defaults
     * @throws IOException
     */
    public static void load() throws IOException {
        // Make the data folders
        ChunkyProtections.getInstance().getDataFolder().mkdirs();

        // Check if the config file exists.  If not, create it.
        File configFile = new File(ChunkyProtections.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        // Load the configuration file into memory
        config = new CommentedConfiguration(new File(ChunkyProtections.getInstance().getDataFolder(), "config.yml"));
        config.load();

        // Sets defaults config values
        setDefaults();

        // Saves the configuration from memory to file
        config.save();
    }

    /**
     * Loads default settings for any missing config values
     */
    private static void setDefaults() {
        for (Config path : Config.values()) {
            config.addComment(path.getPath(), path.getComments());
            if (config.getString(path.getPath()) == null) {
                config.setProperty(path.getPath(), path.getDefault());
            }
        }
    }
}
