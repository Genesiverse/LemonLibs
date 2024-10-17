package org.acsn1.lemonlibs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
@Getter
public class CommandContext {

    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

}
