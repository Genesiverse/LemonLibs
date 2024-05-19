package org.acsn1.lemonlibs.file.config;

import lombok.Getter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.wrapper.FileWrapper;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SoundFile extends FileWrapper {

    private final Map<String, List<String>> sounds = new HashMap<>();

    public SoundFile() {
        super(LemonLibs.getInstance(), "plugins/GenesiCore/LemonLibs/", "sound");
        ConfigurationSection section = getConfig().getConfigurationSection("sounds");
        if(section==null) {
            LemonLibs.getInstance().getLogger().warning("Section `sounds` does not exist in " + getName() + ".yml");
            return;
        }
        for(String key : section.getKeys(false)) {
            List<String> soundList = getConfig().getStringList("sounds." + key);
            sounds.put(key.toLowerCase(), soundList);
        }
    }

}
