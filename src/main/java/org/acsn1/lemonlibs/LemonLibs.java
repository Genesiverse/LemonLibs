package org.acsn1.lemonlibs;

import lombok.Getter;
import org.acsn1.lemonlibs.file.FileLoader;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class LemonLibs extends JavaPlugin {

    @Getter private static LemonLibs instance;
    private FileLoader fileLoader;

    @Override
    public void onEnable() {
        instance = this;
        fileLoader = new FileLoader();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
