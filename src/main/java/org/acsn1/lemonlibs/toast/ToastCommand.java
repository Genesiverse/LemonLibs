package org.acsn1.lemonlibs.toast;

import org.acsn1.lemonlibs.command.BaseCommand;
import org.acsn1.lemonlibs.util.ChatUtils;
import org.acsn1.lemonlibs.util.item.Icon;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ToastCommand extends BaseCommand {

    public ToastCommand(JavaPlugin plugin) {
        super(plugin, "itoast", "lemonlibs.command.itoast");
    }

    @Override
    public void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatUtils.color("#00AEE6Usage: /itoast <player/all> <type> <icon> <sound> <text>"));
    }

    @Override
    public void initialize() {

        setContext(context-> {
            final CommandSender sender = context.getSender();
            if(context.getArgs().length < 5) {
                sendUsage(sender);
                return;
            }
            if(context.getArgs().length >= 5) {
                String username = context.getArgs()[0];

                ToastStyle toastStyle;
                try {
                    toastStyle = ToastStyle.valueOf(context.getArgs()[1].toUpperCase());
                } catch(IllegalArgumentException e) {
                    sender.sendMessage(ChatUtils.color("&cOptions for toast style: [Task, Goal, Challenge]"));
                    return;
                }

                final Icon icon = new Icon(context.getArgs()[2]);
                String sound = context.getArgs()[3];

                StringBuilder text = new StringBuilder();
                for(int i = 4; i < context.getArgs().length; i++) {
                    text.append(context.getArgs()[i]).append(" ");
                }
                if(username.equalsIgnoreCase("all")) {
                    Bukkit.getOnlinePlayers().forEach(p-> {
                        p.playSound(p.getLocation(), sound, 1, 1);
                        new ToastNotification(icon.build(), text.toString(), toastStyle, p, p.getName());
                    });

                } else{

                    Player target = Bukkit.getPlayer(context.getArgs()[0]);

                    if(target==null) {
                        sender.sendMessage(ChatUtils.color("&c"+username + " is currently offline."));
                        return;
                    }
                    target.playSound(target.getLocation(), sound, 1, 1);
                    new ToastNotification(icon.build(), text.toString(), toastStyle, target, target.getName());
                }
            }
        });

        register();
    }
}
