package org.acsn1.lemonlibs.api;

import lombok.Getter;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LemonAPI implements API {

    private final JavaPlugin plugin;

    public LemonAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        ServicesManager servicesManager = plugin.getServer().getServicesManager();

        // Register API
    }

}
