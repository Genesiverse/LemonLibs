package org.acsn1.lemonlibs;

import lombok.Getter;
import org.acsn1.lemonlibs.api.LemonAPI;
import org.acsn1.lemonlibs.file.FileLoader;
import org.acsn1.lemonlibs.module.ModuleLoader;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class LemonLibs extends JavaPlugin {

    @Getter private static LemonLibs instance;
    private FileLoader fileLoader;
    private ModuleLoader moduleLoader;
    private LemonAPI lemonAPI;

    @Override
    public void onEnable() {
        instance = this;
        fileLoader = new FileLoader();
        moduleLoader = new ModuleLoader();

        lemonAPI = new LemonAPI(this);
        ServicesManager servicesManager = getServer().getServicesManager();
        servicesManager.register(LemonAPI.class, lemonAPI, this, ServicePriority.High);

    }

    @Override
    public void onDisable() {
        getServer().getServicesManager().unregister(LemonAPI.class, lemonAPI);
    }

}
