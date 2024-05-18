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
public class iNews {

    private final String prefix = LemonLibs.getInstance().getFileLoader().getINewsFile().getValue("prefix");
    private final Map<String, Map<String, String>> placeholders = Maps.newHashMap();


    public iNews() {
        LemonLibs.getInstance().getFileLoader().getINewsFile().getConfigurationSection("placeholder_settings").getKeys(false).forEach(placeholder-> {
            Map<String, String> map = Maps.newHashMap();
            ConfigurationSection section = LemonLibs.getInstance().getFileLoader().getINewsFile().getConfigurationSection("placeholder_settings."+placeholder);
            assert section!=null;

            section.getKeys(false).forEach(key-> {
                map.put(key, LemonLibs.getInstance().getFileLoader().getINewsFile().getValue("placeholder_settings."+placeholder+"."+key));
            });


            placeholders.put(placeholder, map);
        });

    }

    public String getPlaceholder(Player player, String category, String subCategory) {
        if(!placeholders.containsKey(category)) return "";

        if(!placeholders.get(category).containsKey(subCategory)) return "";

        return LemonLibs.getInstance().getPlaceholderTranslator().translatePlaceholder(player, placeholders.get(category).get(subCategory));

    }
