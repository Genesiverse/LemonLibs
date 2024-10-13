package org.acsn1.lemonlibs.menu.sidebar;

import lombok.Getter;
import lombok.Setter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.util.ChatUtils;
import org.acsn1.lemonlibs.wrapper.FileWrapper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter @Setter
public abstract class IFastbar implements Listener {

    private final Map<String, String> placeholders = new HashMap<>();

    private final Player player;
    private String title;
    private int size;
    private int column;
    private int maxItemsPerPage;
    private int itemsAmount;

    private int version = 1;
    private FileWrapper wrapper;
    private String fastbarSymbol;
    private int currentPage;
    private int pages;
    private Inventory inventory;

    public IFastbar(Player player, String title) {
        this.player = player;
        this.title = title;
        Bukkit.getPluginManager().registerEvents(this, LemonLibs.getInstance());
    }

    public IFastbar setConfig(FileWrapper wrapper) {
        this.wrapper = wrapper;
        return this;
    }

    public IFastbar setTitle(String title) {
        this.title = title;
        return this;
    }

    public IFastbar setVersion(int version) {
        this.version = version;
        return this;
    }

    public IFastbar setSize(int size) {
        this.size = size;
        return this;
    }

    public IFastbar setColumn(int column) {
        this.column = column;
        return this;
    }

    public IFastbar setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
        return this;
    }

    public IFastbar setItemsAmount(int itemsAmount)  {
        this.itemsAmount = itemsAmount;
        return this;
    }

    public IFastbar setSidebarSymbol(String fastbarSymbol) {
        this.fastbarSymbol = fastbarSymbol;
        return this;
    }

    @SafeVarargs
    public final IFastbar addPlaceholder(Map.Entry<String, String>... entries) {
        Arrays.stream(entries).forEach(entry-> {
            final String key = entry.getKey();
            final String value = entry.getValue();
            placeholders.put(key, value);
        });
        return this;
    }

    public final IFastbar removePlaceholder(String... keys) {
        Arrays.stream(keys).forEach(placeholders::remove);
        return this;
    }

    @EventHandler
    public abstract void onClickListener(InventoryClickEvent event);

    private IFastbar calculatePages() {
        pages = (itemsAmount/maxItemsPerPage);
        return this;
    }


    public FastBarResult create(int page) {
        calculatePages();
        if(title==null) {
            return FastBarResult.INVALID_TITLE;
        }

        if(size<9) {
            return FastBarResult.INVALID_SIZE;
        }

        if(page > getPages()) {
            return FastBarResult.INVALID_PAGE;
        }

        if(wrapper==null) {
            return FastBarResult.INVALID_WRAPPER;
        }

        return FastBarResult.SUCCESS;
    }


    public String applyPlaceholders(String string, int page) {

        for(Map.Entry<String, String> entry : placeholders.entrySet()) {
            if(!string.contains(entry.getKey())) continue;
            //if(entry.getKey()==null || entry.getKey().isBlank()) continue;
            if(entry.getValue() == null || entry.getValue().equalsIgnoreCase("")) {
                string = string.replace(entry.getKey(), "");

                continue;
            }
            string = string.replace(entry.getKey(), entry.getValue());
        }
        this.fastbarSymbol = wrapper.getConfig().getString("page_combinations_settings."+size/9+"_rows.version-"+version+".page-"
                        + pages + "." + page + ".symbol");

        if(fastbarSymbol!=null) {
            string = string.replace("%fastbar%", fastbarSymbol);
        } else {
            string = string.replace("%fastbar%", "");
        }
        return string;
    }

    public void show(int page) {
        if(create(page) != FastBarResult.SUCCESS)  {
            Bukkit.getLogger().severe("Failed to open the fastbar inventory. Result: " + create(page).name());
            return;
        }

        if(page < 0 || page > getPages()) return;

        currentPage = page;


        final String newTitle = applyPlaceholders(title, page);

        inventory = Bukkit.createInventory(null, size, ChatUtils.color(newTitle));
        player.openInventory(inventory);

    }

    public boolean getButtons(int currentPage, boolean next, int slot) {
        if(!next) {
            String backButtons = "page_combinations_settings." + size/9 + "_rows.version-" + 2 + ".page-" + pages + "." + currentPage + ".previous";

            int[] buttons = wrapper.translateButtons(backButtons);
            for(int x : buttons) {
                if(x == slot) {
                    return true;
                }
            }
            return false;
        }
        String nextButtons = "page_combinations_settings."+size/9+"_rows.version-"+2 + ".page-" + pages + "." + currentPage + ".next";
        int[] buttons = wrapper.translateButtons(nextButtons);
        for(int x : buttons) {
            if(x== slot) {
                return true;
            }
        }
        return false;
    }


}
