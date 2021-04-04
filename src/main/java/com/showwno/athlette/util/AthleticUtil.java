package com.showwno.athlette.util;

import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.object.Athletic;
import com.showwno.athlette.object.PlayerAccount;
import com.showwno.athlette.option.AthleticOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class AthleticUtil {
    public static void start(Player p, Athletic athle) {
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        if (athle.getEnter() != null) p.teleport(athle.getEnter());
        account.setCP(1);
        account.setStart();
        account.setPlayingAthletic(athle);
        p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, SoundCategory.MASTER, 2, 2);
        p.sendMessage("§7≫ "+ AthleticOption.name(athle.getName()) + " §fを開始しました! §8(id: "+ athle.getId() +"§8)");
        account.deleteResult();
    }

    public static void cp(Player p, int i) {
        p.sendActionBar(Component.text("§9>§7>  CheckPoint §e"+ i +"  §7<§9<"));
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 2, 2);
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        account.setCP(i);
        account.registerResult(i - 1);
    }

    public static void goal(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 2, (float) 0.1);
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        account.registerResult(account.getCP());
        account.saveResult(p, account.getPlayingAthletic().getId());
        account.setPlayingAthletic(null);
        account.setCP(1);
    }
}