package org.acsn1.lemonlibs.menu.popup;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.file.config.PopupFile;
import org.acsn1.lemonlibs.util.ChatUtils;
import org.acsn1.lemonlibs.util.inventory.RestorableInventory;
import org.acsn1.lemonlibs.util.item.BetterItem;
import org.acsn1.lemonlibs.util.sound.SoundAnalyzer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
public class Popup extends RestorableInventory implements Listener, SoundAnalyzer {

    private final Player player;
    private final int version;
    private String title;
    private final String[] numbers;
    private int rows;
    private int[] confirmSlots;
    private int[] cancelSlots;
    private Map<String, BetterItem> items;
    private Inventory inventory;
    private final Map<Character, String> numberTranslator;

    public Popup(int version, Player player) {
        super(player);
        this.version = version;
        this.player = player;
        this.items = new HashMap<>();
        this.numberTranslator = new HashMap<>();
        String path = "confirmation-menus.version."+version+".";
        PopupFile fileWrapper = LemonLibs.getInstance().getFileLoader().getPopupFile();
        this.numbers = fileWrapper.getArray(path + "numbers");

        numberTranslator.put('0', numbers[0]);
        numberTranslator.put('1', numbers[1]);
        numberTranslator.put('2', numbers[2]);
        numberTranslator.put('3', numbers[3]);
        numberTranslator.put('4', numbers[4]);
        numberTranslator.put('5', numbers[5]);
        numberTranslator.put('6', numbers[6]);
        numberTranslator.put('7', numbers[7]);
        numberTranslator.put('8', numbers[8]);
        numberTranslator.put('9', numbers[9]);
        numberTranslator.put(',', "ꡊ");
        numberTranslator.put('.', "ꡋ");

        this.rows = fileWrapper.getConfig().getInt(path + "rows");
        this.confirmSlots = fileWrapper.translateButtons(path + "confirm");
        this.cancelSlots = fileWrapper.translateButtons(path + "cancel");

        BetterItem description = LemonLibs.getInstance().getModuleLoader().getBetterUtils().deserialize(path + "description",
                fileWrapper);
        description.setLore(LemonLibs.getInstance().getModuleLoader().getPlaceholderProvider().getPlaceholderTranslator().translatePlaceholder(player, description.getLore()));
        items.put("description", description);

        // Load Stationary Items
        items.put("confirm.unclicked", LemonLibs.getInstance().getModuleLoader().getBetterUtils().deserialize(path + "stationary_items.confirm.stages.unclicked",
                fileWrapper).translatePlaceholders(player).translateCustomPlaceholders());
        items.put("confirm.clicked", LemonLibs.getInstance().getModuleLoader().getBetterUtils().deserialize(path + "stationary_items.confirm.stages.clicked",
                fileWrapper).translatePlaceholders(player).translateCustomPlaceholders());
        items.put("cancel.unclicked", LemonLibs.getInstance().getModuleLoader().getBetterUtils().deserialize(path + "stationary_items.cancel.stages.unclicked",
                fileWrapper).translatePlaceholders(player).translateCustomPlaceholders());
        items.put("cancel.clicked", LemonLibs.getInstance().getModuleLoader().getBetterUtils().deserialize(path + "stationary_items.cancel.stages.clicked",
                fileWrapper).translatePlaceholders(player).translateCustomPlaceholders());



        Bukkit.getPluginManager().registerEvents(this, LemonLibs.getInstance());
    }

    public Popup setTitle(String title) {
        this.title = title;
        return this;
    }

    public Popup setText(BetterItem betterItem) {
        betterItem.setSlots(LemonLibs.getInstance().getFileLoader().getPopupFile()
                .translateButtons("confirmation-menus.version." +version + ".text.slots"));
        items.put("text", betterItem);
        return this;
    }


    public String translatePrice(float price) {
        String string = String.valueOf(price);
        String output = "";
        for(char x : string.toCharArray()) {
            output+=numberTranslator.get(x);
        }
        return output;
    }

    public void show() {

        this.inventory = Bukkit.createInventory(null, rows * 9, ChatUtils.color(title));
        for(Map.Entry<String, BetterItem> entry : items.entrySet()) {
            if(entry.getKey().contains(".clicked")) continue;
            for (int slots : entry.getValue().getSlots()) {
                inventory.setItem(slots, entry.getValue().tryBuild());
            }
        }

        new BukkitRunnable() {
            public void run() {
                if(!getInventoryStoring().getItems().containsKey(player.getUniqueId()) && !player.getInventory().isEmpty()) {
                    getInventoryStoring().store(player);
                }

                player.openInventory(inventory);
                cancel();
            }
        }.runTaskLater(LemonLibs.getInstance(), 2L);


    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if(!event.getInventory().equals(inventory)) return;
        getInventoryStoring().restore(player);
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if(!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        if(event.getClickedInventory()==null)return;
        if(!(event.getClickedInventory().equals(inventory))) return;
        if(p!=player) p.closeInventory();


        if(Contains(confirmSlots, event.getSlot())) {
            playSound(player);
            BetterItem betterItem = items.get("confirm.clicked");
            for(int slots : betterItem.getSlots()) {
                inventory.setItem(slots, betterItem.tryBuild());
            }
        } else
        if(Contains(cancelSlots, event.getSlot())) {
            playSound(player);
            BetterItem betterItem = items.get("cancel.clicked");
            for(int slots : betterItem.getSlots()) {
                inventory.setItem(slots, betterItem.tryBuild());
            }

        }
        event.setCancelled(true);
    }

    public boolean Contains(int[] array, int slot) {
        for(int i : array) {
            if(i==slot) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void playSound(Player player) {
        Map<String, List<String>> map = LemonLibs.getInstance().getFileLoader().getSoundFile().getSounds();
        List<String> sounds = map.get("confirmation-menu");
        if(sounds==null)return;
        player.playSound(player.getLocation(), sounds.get(ThreadLocalRandom.current().nextInt(sounds.size())), 1, 1);

    }

}
