package org.acsn1.lemonlibs.menu;

import lombok.Getter;
import org.acsn1.lemonlibs.util.inventory.RestorableInventory;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public class MenuManager {

    private final Set<RestorableInventory> inventories = new HashSet<>();

    public void stop() {
        for(RestorableInventory restorableInventory : inventories) {
            if(restorableInventory.getPlayer() != null) {
                restorableInventory.getInventoryStoring().restore(restorableInventory.getPlayer());
                restorableInventory.getPlayer().closeInventory();
            }
        }
        inventories.clear();
    }

    public RestorableInventory getSidebar(Player player) {
        for(RestorableInventory restorableInventory : inventories) {
            if(restorableInventory.getPlayer().equals(player)) {
                return restorableInventory;
            }
        }
        return null;
    }

}
