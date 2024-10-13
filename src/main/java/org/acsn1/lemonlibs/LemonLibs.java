package org.acsn1.lemonlibs;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.api.LemonAPI;
import org.acsn1.lemonlibs.file.FileLoader;
import org.acsn1.lemonlibs.module.ModuleLoader;
import org.acsn1.lemonlibs.toast.ToastCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class LemonLibs extends JavaPlugin {

    @Getter private static LemonLibs instance;
    private FileLoader fileLoader;
    @Setter private ModuleLoader moduleLoader;
    private LemonAPI lemonAPI;


    @Override
    public void onEnable() {
        instance = this;
        fileLoader = new FileLoader();
        moduleLoader = new ModuleLoader();
        lemonAPI = new LemonAPI();
        new ToastCommand(this); // Register this command only - no need for a command provider for just one command

    }

}
