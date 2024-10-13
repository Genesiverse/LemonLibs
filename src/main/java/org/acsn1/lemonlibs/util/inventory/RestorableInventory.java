package org.acsn1.lemonlibs.util.inventory;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;

@Getter @Setter
public class RestorableInventory {

    private InventoryStoring inventoryStoring;
    private final Player player;

    public RestorableInventory(Player player) {
        this.player = player;
        this.inventoryStoring = new InventoryStoring();
        //LemonLibs.getInstance().getModuleLoader().getMenuManager().getInventories().add(this);
    }

}
