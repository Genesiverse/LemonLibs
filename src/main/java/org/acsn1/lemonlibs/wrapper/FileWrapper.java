package org.acsn1.lemonlibs.wrapper;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
public class FileWrapper {

    private final Plugin plugin;
    private final String pluginPath;
    private final String name;
    private File folder;
    private File file;
    private FileConfiguration config;

    public FileWrapper(Plugin plugin, String pluginPath, String name) {
        this.plugin = plugin;
        this.pluginPath = pluginPath;
        this.name = name;

        folder = new File(getPluginPath());
        if(!(folder.exists())) folder.mkdirs();

        file = new File(folder, name + ".yml");
        if(!file.exists()) {
            createFile(plugin, file);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void createFile(Plugin plugin, File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            InputStream in = plugin.getResource(name + ".yml");
            try {
                int i;
                while ((i = in.read()) != -1) {
                    out.write(i);
                }
            } finally {
                if (in != null) in.close();
                if (out != null) out.close();
            }
        } catch(IOException ex) {
            plugin.getLogger().severe("Failed to create " + name + ".yml");
        }
    }

    public void reload(Plugin plugin) {
        folder = new File(getPluginPath());
        if(!(folder.exists())) folder.mkdirs();

        file = new File(folder, name + ".yml");
        if(!file.exists()) {
            createFile(plugin, file);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public String[] getArray(String key) {
        String[] matrix = new String[10];
        String string = config.getString(key);
        for(int i = 0; i < 10; i++) {
            matrix[i]= String.valueOf(string.toCharArray()[i]);
        }
        return matrix;
    }

    public int[] translateButtons(String key) {
        String string = config.getString(key);
        if(string==null) return new int[0];
        if(!string.contains(",")) return new int[] {Integer.parseInt(string)};
        String[] splitter = string.split(",");
        int[] matrix = new int[splitter.length];
        for(int i = 0; i < splitter.length;i++) {
            matrix[i]= Integer.parseInt(splitter[i]);
        }

        return matrix;
    }

}
