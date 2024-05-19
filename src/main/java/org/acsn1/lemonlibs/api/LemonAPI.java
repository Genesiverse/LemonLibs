package org.acsn1.lemonlibs.api;

import lombok.Getter;
import org.acsn1.lemonlibs.actionbar.Actionbar;
import org.acsn1.lemonlibs.menu.sidebar.Sidebar;
import org.acsn1.lemonlibs.toast.ToastNotification;
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
        return new Actionbar(message);
    }

    @Override
    public Actionbar createActionbar(String message, int duration) {
        return new Actionbar(message, duration);
    }

    @Override
    public Sidebar createSidebar(String title, int rows, int amount, int version, Player player, boolean isRestorable) {
        return new Sidebar(title, rows, amount, version, player, isRestorable);
    }

    @Override
    public void pushToastNotification(ItemStack icon, String message, ToastStyle toastStyle, Player player, String owner) {
        new ToastNotification(icon, message, toastStyle, player, owner);
    }
}
