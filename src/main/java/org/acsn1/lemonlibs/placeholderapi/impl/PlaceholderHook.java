package org.acsn1.lemonlibs.placeholderapi.impl;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.acsn1.lemonlibs.LemonLibs;
import org.bukkit.entity.Player;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "iNews";
    }

    @Override
    public String getAuthor() {
        return "acsn1";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }


    @Override
    public String onPlaceholderRequest(Player p, String identifier) {


        String placeholder = LemonLibs.getInstance().getModuleLoader().getPlaceholderProvider().getPlaceholderTranslator()
                .translatePlaceholder(p, LemonLibs.getInstance().getModuleLoader().getNews().getPrefix() + "_"+identifier);
        String[] matrix = placeholder.split("_");

        return LemonLibs.getInstance().getModuleLoader().getNews().getPlaceholder(p, matrix[1], matrix[2]);

    }

}
