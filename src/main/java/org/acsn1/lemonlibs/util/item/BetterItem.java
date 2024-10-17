package org.acsn1.lemonlibs.util.item;

import lombok.Getter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.builder.ItemBuilder;
import org.acsn1.lemonlibs.placeholderapi.impl.PlaceholderTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class BetterItem {

    private String name;
    private Material material;
    private int modeldata;
    private List<String> lore;
    private int[] slots;

    public BetterItem(String name, Material material, int modeldata, List<String> lore, int[] slots) {
        this.name = name;
        this.material = material;
        this.modeldata = modeldata;
        this.lore = lore;
        this.slots = slots;
    }

    public BetterItem(Material material) {
        this.material = material;
    }

    public BetterItem setName(String name) {
        this.name = name;
        return this;
    }

    public BetterItem setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public BetterItem setModeldata(int modeldata) {
        this.modeldata = modeldata;
        return this;
    }

    public BetterItem setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public BetterItem setSlots(int[] slots) {
        this.slots = slots;
        return this;
    }

    public BetterItem fillEmptyLoreBug() {
        for(int i = 0; i < 280; i++) {
            getLore().add("");
        }
        getLore().add("                                                                                                                                                                                                                                                                                                                                                                                                     ");
        return this;
    }

    public BetterItem translatePlaceholders(Player player) {
        PlaceholderTranslator translator = LemonLibs.getInstance().getModuleLoader().getPlaceholderProvider().getPlaceholderTranslator();
        if(getName()!=null && !getName().equalsIgnoreCase("")) setName(translator.translatePlaceholder(player, getName()));
        if(getLore() != null && !getLore().isEmpty()) setLore(translator.translatePlaceholder(player, getLore()));
        return this;
    }

    public ItemStack tryBuild() {
        return new ItemBuilder(material, 1).setName(name)
                .setLore(lore).setModelData(modeldata).build();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BetterItem translateCustomPlaceholders() {
        if(getLore()==null || getLore().isEmpty()) return this;
        for(String x : getLore()) {
            if(x.contains("%ilore%")) fillEmptyLoreBug(); break;
        }

        return this;
    }

}
