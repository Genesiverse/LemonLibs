package org.acsn1.lemonlibs.placeholderapi.impl;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderTranslator {

    public String translatePlaceholder(Player player, String msg) {
        if(msg== null || msg.equalsIgnoreCase("")) return "";
        return PlaceholderAPI.setPlaceholders(player, msg.replace("{", "%").replace("}", "%"));
    }


    public List<String> translatePlaceholder(Player player, List<String> list) {
        List<String> translated = new ArrayList<>();

        for(String x : list) {
            translated.add(translatePlaceholder(player, x));
        }
        return translated;
    }

}


