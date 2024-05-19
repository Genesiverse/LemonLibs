package org.acsn1.lemonlibs.inews;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.LemonLibs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter
@Setter
public class News {

    private final String prefix = LemonLibs.getInstance().getFileLoader().getNewsFile().getConfig().getString("prefix");
    private final Map<String, Map<String, String>> placeholders = Maps.newHashMap();


    public News() {
        ConfigurationSection section = LemonLibs.getInstance().getFileLoader().getNewsFile().getConfig().getConfigurationSection("placeholder_settings");

        if (section == null) {
            LemonLibs.getInstance().getLogger().warning("Section `placeholder_settings` does not exist in inews.yml!");
            return;
        }

        section.getKeys(false).forEach(placeholder -> {
            Map<String, String> map = Maps.newHashMap();
            ConfigurationSection insideSection = LemonLibs.getInstance().getFileLoader().getNewsFile().getConfig().getConfigurationSection("placeholder_settings." + placeholder);
            if (insideSection == null) return;

            section.getKeys(false).forEach(key -> {
                map.put(key, LemonLibs.getInstance().getFileLoader().getNewsFile().getConfig().getString("placeholder_settings." + placeholder + "." + key));
            });


            placeholders.put(placeholder, map);
        });

    }

    public String getPlaceholder(Player player, String category, String subCategory) {
        if (!placeholders.containsKey(category)) return "";

        if (!placeholders.get(category).containsKey(subCategory)) return "";

        return LemonLibs.getInstance().getModuleLoader().getPlaceholderProvider().getPlaceholderTranslator().translatePlaceholder(player, placeholders.get(category).get(subCategory));

    }
}
