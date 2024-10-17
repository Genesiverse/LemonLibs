package org.acsn1.lemonlibs.builder;

import org.acsn1.lemonlibs.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SkullBuilder {

    private Material material;
    private int modelData;
    private String skin;
    private List<String> lore;
    private OfflinePlayer player;
    private String name;
    private ItemStack itemStack;

    public SkullBuilder setMaterial(Material material)
    {
        this.material = material;
        return this;
    }

    public SkullBuilder setModelData(int modelData) {
        this.modelData = modelData;
        return this;
    }

    public SkullBuilder setSkin(String skin) {
        this.skin = skin;
        return this;
    }

    public SkullBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public SkullBuilder addLore(String line) {
        if(lore==null) lore = new ArrayList<>();
        this.lore.add(line);
        return this;
    }

    public SkullBuilder setPlayer(OfflinePlayer player) {
        this.player = player;
        return this;
    }


    public ItemStack build() {
        itemStack = new ItemStack(material);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        if(name!=null) meta.setDisplayName(ChatUtils.color(name));
        if(lore!=null) meta.setLore(ChatUtils.color(lore));
        if(skin!=null) {
            try {
                meta.getOwnerProfile().getTextures().setSkin(new URL(skin));
            }catch(MalformedURLException ignored) {}
        }

        if(player!=null) meta.setOwningPlayer(player);

        itemStack.setItemMeta(meta);
        return itemStack.clone();
    }

}
