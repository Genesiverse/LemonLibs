package org.acsn1.lemonlibs.placeholderapi;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.placeholderapi.impl.PlaceholderHook;
import org.acsn1.lemonlibs.placeholderapi.impl.PlaceholderTranslator;
import org.bukkit.Bukkit;

@Getter @Setter
public class PlaceholderProvider {

    private PlaceholderTranslator placeholderTranslator;

    public PlaceholderProvider() {

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")==null) {
            LemonLibs.getInstance().getLogger().warning("PlaceholderAPI was not found, support for it has been disabled.");

            return;
        }

        this.placeholderTranslator = new PlaceholderTranslator();
        new PlaceholderHook().register();
    }


}
