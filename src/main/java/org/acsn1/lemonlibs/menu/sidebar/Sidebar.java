package org.acsn1.lemonlibs.menu.sidebar;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.util.ChatUtils;
import org.acsn1.lemonlibs.util.inventory.RestorableInventory;
import org.acsn1.lemonlibs.util.sound.SoundAnalyzer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
public class Sidebar extends RestorableInventory implements Listener, SoundAnalyzer {

    private final JavaPlugin plugin;
    private String symbol;
    private String title;
    private int version;
    private int amount;
    private final int rows;
    private int pages;
    private int current;
    private Map<Integer, Map<Integer, ItemStack>> pageItems;
    private Inventory inventory;
    private Player player;
    private final boolean isRestorable;
    private int column = 8;

    public Sidebar(JavaPlugin plugin, String title, int rows, int amount, int cols, int version, Player player, boolean isRestorable) {
        super(player);
        this.plugin = plugin;
        this.title = title;
        this.rows = rows;
        this.amount = amount;
        this.column = cols;
        this.version = version;
        this.pages = amount/(rows*9);
        this.pageItems = new HashMap<>();
        this.current = 1;
        this.player = player;
        this.isRestorable = isRestorable;
        Bukkit.getPluginManager().registerEvents(this,plugin);

    }

    public Sidebar setColumn(int column) {
        this.column = column;
        return this;
    }

    public void update(List<ItemStack> items) {
        int amount = items.size();
        setAmount(amount);

        int pages = (getAmount() / ((getRows() * 9) - getRows())) + 1;
        setPages(pages);


        for (int i = 1; i <= pages; i++) {
            pageItems.put(i, new HashMap<>());
        }
        int pos = 0;
        int currentPage = 1;
        int amountPerPage = (getRows() * 9) - 1;

        if(version == 1) {
            for (int x = 0; x < items.size(); x++) {

                if (pos > amountPerPage) {
                    currentPage++;
                    pos = 0;
                }

                if (pos % 9 == column) {
                    getPageItems().get(currentPage).put(pos, new ItemStack(Material.AIR));
                    pos++;
                    if (pos < amountPerPage - 1) {
                        getPageItems().get(currentPage).put(pos, items.get(x));
                        pos++;
                    }
                } else {
                    getPageItems().get(currentPage).put(pos, items.get(x));
                    pos++;
                }


            }
        }
        if(version == 2) {
            for (int x = 0; x < items.size(); x++) {

                if (pos > amountPerPage) {
                    currentPage++;
                    pos = 0;
                }

                if (pos % 9 == column) {

                    getPageItems().get(currentPage).put(pos, new ItemStack(Material.AIR));
                    pos++;
                    if (pos < amountPerPage - 1) {
                        getPageItems().get(currentPage).put(pos, items.get(x));
                        pos++;
                    }
                } else {
                    getPageItems().get(currentPage).put(pos, items.get(x));
                    pos++;
                }

            }
        }
        if(inventory!=null) {
            if (player.getOpenInventory().getTopInventory().equals(inventory)) {
                for (Map.Entry<Integer, ItemStack> entry : getPageItems().get(currentPage).entrySet()) {
                    inventory.setItem(entry.getKey(), entry.getValue());

                }
                player.getOpenInventory().getTopInventory().setStorageContents(inventory.getStorageContents());
            }
        }

    }

    public void show(int page) {

        setCurrent(page);
        this.symbol = LemonLibs.getInstance()
                .getFileLoader().getSidebarFile().getConfig().getString("page_combinations_settings."+rows+"_rows.version-"+version+".page-"
                        + pages + "." + page + ".symbol");
        if(symbol != null) {
            if (title.contains("%sidebar%")) title = title.replace("%sidebar%", symbol);
        }

        inventory = Bukkit.createInventory(null, rows * 9, ChatUtils.color(title));
        if(getPageItems().containsKey(page)) {
            for (Map.Entry<Integer, ItemStack> entry : getPageItems().get(page).entrySet()) {
                inventory.setItem(entry.getKey(), entry.getValue());
            }
        }

        if(isRestorable) {
            new BukkitRunnable() {
                public void run() {
                    if (!getInventoryStoring().getItems().containsKey(player.getUniqueId()) && !player.getInventory().isEmpty()) {
                        getInventoryStoring().store(player);
                    }

                    player.openInventory(inventory);
                    cancel();
                }
            }.runTaskLater(LemonLibs.getInstance(), 2L);

            return;
        }
        player.openInventory(inventory);
    }

    @EventHandler
    public void onSidebarClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        if(!event.getInventory().equals(inventory)) return;
        if(!event.getView().getTopInventory().equals(inventory)) return;
        LemonLibs.getInstance().getModuleLoader().getMenuManager().getInventories().remove(this);
        if(!isRestorable) {
            //setPlayer(null);
            inventory=null;
            return;
        }
        getInventoryStoring().restore(player);
        //setPlayer(null);
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        LemonLibs.getInstance().getModuleLoader().getMenuManager().getInventories().remove(this);

        if(!isRestorable) {
            //setPlayer(null);
            //inventory=null;
            return;
        }
        getInventoryStoring().restore(player);
        //setPlayer(null);
        HandlerList.unregisterAll(this);

    }


    @EventHandler
    public void onPageChange(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        if(event.getClickedInventory()==null)return;
        if(!event.getView().getTopInventory().equals(inventory)) return;
        if(!(event.getClickedInventory().equals(inventory))) return;
        if(p!=player) p.closeInventory();

        if(event.getSlot() % 9 == column || event.getCurrentItem() != null) {
            playSound(p);
        }
        // Previous Page Buttons
        if(getButtons(getCurrent(), false,event.getSlot())) {

            setCurrent(getCurrent()-1);
            show(getCurrent());
        } else

            // Next Page Buttons
            if(getButtons(getCurrent(), true, event.getSlot())) {
                setCurrent(getCurrent()+1);
                show(getCurrent());
            }

        if(version==2) {
            switch(event.getSlot()) {
                case 53:
                    show(1);
                    break;
                case 8:
                    p.closeInventory();
                    break;
            }
        }
        event.setCancelled(true);
    }


    private boolean getButtons(int currentPage, boolean next, int slot) {
        if(!next) {
            String backButtons = "page_combinations_settings." + rows + "_rows.version-" + version + ".page-" + pages + "." + currentPage + ".previous";

            int[] buttons = LemonLibs.getInstance()
                    .getFileLoader().getSidebarFile().translateButtons(backButtons);
            for(int x : buttons) {
                if(x == slot) {
                    return true;
                }
            }
            return false;
        }
        String nextButtons = "page_combinations_settings."+rows+"_rows.version-"+ version + ".page-" + pages + "." + currentPage + ".next";
        int[] buttons = LemonLibs.getInstance()
                .getFileLoader().getSidebarFile().translateButtons(nextButtons);
        for(int x : buttons) {
            if(x == slot) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void playSound(Player player) {
        Map<String, List<String>> map = LemonLibs.getInstance().getFileLoader().getSoundFile().getSounds();
        List<String> sounds = map.get("sidebar");
        if(sounds==null)return;
        player.playSound(player.getLocation(), sounds.get(ThreadLocalRandom.current().nextInt(sounds.size())), 1, 1);

    }

}
