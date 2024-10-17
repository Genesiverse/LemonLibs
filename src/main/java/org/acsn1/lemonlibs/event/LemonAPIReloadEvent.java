package org.acsn1.lemonlibs.event;

import lombok.Getter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.api.LemonAPI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class LemonAPIReloadEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final LemonLibs plugin;
    private final LemonAPI lemonAPI;

    public LemonAPIReloadEvent(LemonLibs plugin, LemonAPI lemonAPI) {
        this.plugin = plugin;
        this.lemonAPI = lemonAPI;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
