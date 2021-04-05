package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.object.PlayerAccount;
import com.showwno.athlette.util.AthleticUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerFlyEvent implements Listener {
    public PlayerFlyEvent(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onFlyToggle(PlayerToggleFlightEvent ev) {
        if (!ev.isFlying()) {
            Player p = ev.getPlayer();
            PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
            if (account.getPlayingAthletic() == null) return;
            p.sendMessage("§7» §cアスレに失敗しました! Flyは使用できません!");
            AthleticUtil.end(p);
        }
    }

    @EventHandler
    public void onFlying(PlayerMoveEvent ev) {
        Player p = ev.getPlayer();
        if (p.isFlying()) {
            PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
            if (account.getPlayingAthletic() == null) return;
            p.sendMessage("§7» §cアスレに失敗しました! Flyは使用できません!");
            AthleticUtil.end(p);
        }
    }
}