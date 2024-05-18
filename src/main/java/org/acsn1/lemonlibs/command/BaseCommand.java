package org.acsn1.lemonlibs.command;

import lombok.Getter;
import org.acsn1.lemonlibs.LemonLibs;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public abstract class BaseCommand implements CommandExecutor, TabCompleter {

    private final String name;
    private String permission;
    private Consumer<CommandContext> context;
    private BiConsumer<CommandContext, List<String>> suggestions;

    public BaseCommand(String name) {
        this.name = name;
        initialize();
    }

    public BaseCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    public BaseCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public BaseCommand setContext(Consumer<CommandContext> context) {
        this.context = context;
        return this;
    }

    public BaseCommand setSuggestions(BiConsumer<CommandContext, List<String>> suggestions) {
        this.suggestions = suggestions;
        return this;
    }

    public boolean isPermissionless() { return permission == null;}

    public abstract void sendUsage(CommandSender sender);
    public abstract void initialize();

    public boolean register() {

        PluginCommand command = LemonLibs.getInstance().getCommand(name);
        if(command==null) {
            LemonLibs.getInstance().getLogger().warning("Command " + name + " was not registered successfully!");
            return false;
        }

        command.setExecutor(this);
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!command.getName().equalsIgnoreCase(name)) return true;
        if(!isPermissionless()) {
            if(!sender.hasPermission(permission)) {
                sender.sendMessage("No permission!");
                return true;
            }
        }
        if(context==null) return true;
        context.accept(new CommandContext(sender, command, label, args));

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (suggestions != null) {
            suggestions.accept(new CommandContext(sender, command, label, args), completions);
        }
        return completions.isEmpty() ? Collections.emptyList() : completions;
    }
}
