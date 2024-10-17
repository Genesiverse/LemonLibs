package org.acsn1.lemonlibs.util.item;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public class Icon {

    private final String string;
    public Icon(String string) {
        this.string = string;
    }

    public ItemStack build() {
        String[] matrix = string.split(":");
        if(matrix.length == 0) new ItemStack(Material.AIR);

        Material material = Material.matchMaterial(matrix[0]);
        if(material==null) material = Material.AIR;

        int modeldata = 0;
        try {
            modeldata = Integer.parseInt(matrix[1]);
        } catch(NumberFormatException ex) {}
        return new ItemBuilder(material).setModelData(modeldata).hideAttributes().hideEnchants().build();
    }

}
