package org.acsn1.lemonlibs.api;

import lombok.Getter;
import org.acsn1.lemonlibs.actionbar.Actionbar;
import org.acsn1.lemonlibs.toast.ToastStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LemonAPI implements API {

    private final JavaPlugin plugin;
    public LemonAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Actionbar createActionbar(String message) {
        return null;
    }

    @Override
    public Actionbar createActionbar(String message, int duration) {
        return null;
    }

    @Override
    public void pushToastNotification(ItemStack icon, String message, ToastStyle toastStyle, Player player, String owner) {

    }
}
