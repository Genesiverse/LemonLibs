package org.acsn1.lemonlibs.toast;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.util.ChatUtils;
import org.acsn1.lemonlibs.util.sound.SoundAnalyzer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
public class ToastNotification implements SoundAnalyzer {

    private NamespacedKey key;
    private final ItemStack icon;
    private String owner;
    private final String message;
    private final ToastStyle toastStyle;
    private final Player player;
    private boolean shown;

    @Override
    public void playSound(Player player) {
        Map<String, List<String>> map = LemonLibs.getInstance().getFileLoader().getSoundFile().getSounds();
        List<String> sounds = map.get("toast");
        if(sounds==null)return;
        player.playSound(player.getLocation(), sounds.get(ThreadLocalRandom.current().nextInt(sounds.size())), 1, 1);

    }

    public ToastNotification(ItemStack icon, String message, ToastStyle toastStyle, Player player, String owner) {
        this.key = new NamespacedKey(LemonLibs.getInstance(), UUID.randomUUID().toString());
        this.icon = icon;
        this.message = message;
        this.toastStyle = toastStyle;
        this.player = player;
        this.owner = owner;
        LemonLibs.getInstance().getModuleLoader().getToastManager().addToQueue(this);

    }

    public void show() {
        this.key = new NamespacedKey(LemonLibs.getInstance(), UUID.randomUUID().toString());
        playSound(player);
        createAdvancement();
        grantAdvancement(player);
        setShown(true);
    }

    private void createAdvancement() {
        ItemMeta meta = icon.getItemMeta();
        int modeldata = 0;
        if(meta==null) return;
        if(meta.hasCustomModelData()) modeldata = meta.getCustomModelData();
        Bukkit.getUnsafe().loadAdvancement(key, "{\n" +
                "    \"criteria\": {\n" +
                "        \"trigger\": {\n" +
                "            \"trigger\": \"minecraft:impossible\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"display\": {\n" +
                "        \"icon\": {\n" +
                "            \"item\": \"minecraft:" + icon.getType().name().toLowerCase() + "\",\n" +
                "            \"nbt\": \"{SkullOwner:" + owner + ",CustomModelData:" + modeldata + "}\"\n" +
                "        },\n" +
                "        \"title\": {\n" +
                "            \"text\": \"" + ChatUtils.color(message.replace("{nl}", "\n")) + "\"\n" +
                "        },\n" +
                "        \"description\": {\n" +
                "            \"text\": \"\"\n" +
                "        },\n" +
                "        \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                "        \"frame\": \"" + toastStyle.toString().toLowerCase() + "\",\n" +
                "        \"announce_to_chat\": false,\n" +
                "        \"show_toast\": true,\n" +
                "        \"hidden\": true\n" +
                "    },\n" +
                "    \"requirements\": [\n" +
                "        [\n" +
                "            \"trigger\"\n" +
                "        ]\n" +
                "    ]\n" +
                "}");
    }

    private void grantAdvancement(Player player) {
        Advancement advancement = Bukkit.getAdvancement(key);
        if(advancement==null) return;
        player.getAdvancementProgress(advancement).awardCriteria("trigger");
    }

    public void revokeAdvancement(Player player) {
        Advancement advancement = Bukkit.getAdvancement(key);
        if(advancement==null) return;
        player.getAdvancementProgress(advancement).revokeCriteria("trigger");
        Bukkit.getUnsafe().removeAdvancement(key);
        LemonLibs.getInstance().getModuleLoader().getToastManager().removeFromQueue(this);
    }

}
