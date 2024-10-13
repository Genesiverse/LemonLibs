package org.acsn1.lemonlibs.api;

import org.acsn1.lemonlibs.actionbar.Actionbar;
import org.acsn1.lemonlibs.menu.sidebar.Sidebar;
import org.acsn1.lemonlibs.module.ModuleLoader;
import org.acsn1.lemonlibs.toast.ToastStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public interface API {

    /* Date: 19/05/2024
     * API for LemonLibs
     * Property of Genesiverse.inc
     * Author: acsn1
     */

    Actionbar createActionbar(String message);
    Actionbar createActionbar(String message, int duration);
    Sidebar createSidebar(JavaPlugin plugin, String title, int rows, int amount, int cols, int version, Player player, boolean isRestorable);
    void pushToastNotification(ItemStack icon, String message, ToastStyle toastStyle, Player player, String owner);
    ModuleLoader getModuleLoader();

}
