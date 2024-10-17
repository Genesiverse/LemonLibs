package org.acsn1.lemonlibs.actionbar;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.util.sound.SoundAnalyzer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
public class Actionbar implements SoundAnalyzer {

    private final String message;
    private int duration;

    public Actionbar(String message) {
        this.message = message;
    }
    public Actionbar(String message, int duration) {
        this.message = message;
        this.duration = duration;
    }
    public Actionbar setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Actionbar show(Player... players) {
        Arrays.stream(players).forEach(player-> {
            playSound(player);
            new BukkitRunnable() {
                int counter;
                public void run() {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                    counter++;
                    if(counter >= duration) cancel();
                }
            }.runTaskTimer(LemonLibs.getInstance(), 0l, 20L);

        });
        return this;
    }

    @Override
    public void playSound(Player player) {
        Map<String, List<String>> map = LemonLibs.getInstance().getFileLoader().getSoundFile().getSounds();
        List<String> sounds = map.get("actionbar");
        if(sounds==null)return;
        player.playSound(player.getLocation(), sounds.get(ThreadLocalRandom.current().nextInt(sounds.size())), 1, 1);

    }
}
