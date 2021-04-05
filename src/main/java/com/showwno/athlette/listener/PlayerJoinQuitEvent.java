package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.util.AthleticUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitEvent implements Listener {
    public PlayerJoinQuitEvent(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        AthleticUtil.deleteAthleInventory(ev.getPlayer());
        for (Player p : ev.getPlayer().getServer().getOnlinePlayers()) {
            if (PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString()).getIsHide()) {
                p.hidePlayer(Athlette.JAVA_PLUGIN, ev.getPlayer());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
        AthleticUtil.deleteAthleInventory(ev.getPlayer());
    }
}
