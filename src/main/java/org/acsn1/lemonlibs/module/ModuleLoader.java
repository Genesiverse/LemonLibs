package org.acsn1.lemonlibs.module;

import lombok.Getter;
import org.acsn1.lemonlibs.inews.News;
import org.acsn1.lemonlibs.menu.MenuManager;
import org.acsn1.lemonlibs.placeholderapi.PlaceholderProvider;
import org.acsn1.lemonlibs.toast.ToastManager;
import org.acsn1.lemonlibs.util.item.BetterUtils;

@Getter
public class ModuleLoader {

    private PlaceholderProvider placeholderProvider;
    private News news;
    private ToastManager toastManager;
    private MenuManager menuManager;
    private BetterUtils betterUtils;

    public ModuleLoader() {

        load();

    }

    public void load() {
        this.placeholderProvider = new PlaceholderProvider();
        this.news = new News();
        this.toastManager = new ToastManager();
        this.menuManager = new MenuManager();
        this.betterUtils = new BetterUtils();
    }



}
