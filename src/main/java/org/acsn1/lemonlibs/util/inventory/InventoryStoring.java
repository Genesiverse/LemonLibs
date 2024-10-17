package org.acsn1.lemonlibs.util.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryStoring {

    private Map<UUID, ItemStack[]> items = new HashMap<>();
    private Map<UUID, ItemStack[]> armor = new HashMap<>();

    public void store(Player player){

        UUID uuid = player.getUniqueId();

        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        if(items.get(player.getUniqueId()) != null) return;


        items.putIfAbsent(uuid, contents);
        armor.putIfAbsent(uuid, armorContents);

        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

    }

    public void restore(HumanEntity player){
        UUID uuid = player.getUniqueId();

        if(items.get(uuid) == null) return;
        ItemStack[] contents = items.get(uuid);
        ItemStack[] armorContents = armor.get(uuid);

        if(contents != null){
            player.getInventory().setContents(contents);
        }
        else{
            player.getInventory().clear();
        }

        if (armorContents[3] != null) player.getInventory().setHelmet(armorContents[3]);
        if (armorContents[2] != null) player.getInventory().setChestplate(armorContents[2]);
        if (armorContents[1] != null) player.getInventory().setLeggings(armorContents[1]);
        if (armorContents[0] != null) player.getInventory().setBoots(armorContents[0]);
    }

    public Map<UUID, ItemStack[]> getItems() { return items; }

}
