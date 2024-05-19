package org.acsn1.lemonlibs.api;

import org.acsn1.lemonlibs.actionbar.Actionbar;
import org.acsn1.lemonlibs.toast.ToastStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface API {

    /* Date: 19/05/2024
     * API for LemonLibs
     * Property of Genesiverse.inc
     * Author: acsn1
     */

    Actionbar createActionbar(String message);
    Actionbar createActionbar(String message, int duration);
    void pushToastNotification(ItemStack icon, String message, ToastStyle toastStyle, Player player, String owner);
}
