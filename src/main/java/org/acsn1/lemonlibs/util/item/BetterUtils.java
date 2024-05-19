package org.acsn1.lemonlibs.util.item;

import org.acsn1.lemonlibs.wrapper.FileWrapper;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class BetterUtils {

    public BetterItem deserialize(String path, FileWrapper fileWrapper) {
        int[] slots = fileWrapper.translateButtons(path+".slots");
        return new BetterItem(Material.matchMaterial(fileWrapper.getConfig().getString(path + ".material")))
                .setName(fileWrapper.getConfig().getString(path+".name"))
                .setLore(fileWrapper.getConfig().getStringList(path+".lore"))
                .setModeldata(fileWrapper.getConfig().getInt(path+".modeldata"))
                .setSlots(slots);

    }

    public BetterItem deserialize(String path, YamlConfiguration config) {
        int[] slots = translateButtons(path+".slots", config);
        return new BetterItem(Material.matchMaterial(config.getString(path + ".material")))
                .setName(config.getString(path+".name"))
                .setLore(config.getStringList(path+".lore"))
                .setModeldata(config.getInt(path+".modeldata"))
                .setSlots(slots);

    }

    public String[] getArray(String key, YamlConfiguration config) {
        String[] matrix = new String[10];
        String string = config.getString(key);
        for(int i = 0; i < 10; i++) {
            matrix[i]= String.valueOf(string.toCharArray()[i]);
        }
        return matrix;
    }

    public int[] translateButtons(String key, YamlConfiguration config) {
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
