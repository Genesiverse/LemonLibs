package org.acsn1.lemonlibs.toast;

import org.acsn1.lemonlibs.LemonLibs;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ToastManager {

    private List<ToastNotification> notifications = new ArrayList<>();

    public ToastManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(LemonLibs.getInstance(), () -> {

            if(notifications.size() > 0) {
                ToastNotification first = notifications.get(0);
                if(first!=null && !first.isShown()) {
                    first.show();
                } else{
                    first.revokeAdvancement(first.getPlayer());

                }
            }

        }, 0, 10L);
    }


    public void addToQueue(ToastNotification toast) {
        notifications.add(toast);

    }

    public void removeFromQueue(ToastNotification toast) {
        notifications.remove(toast);
    }


}
