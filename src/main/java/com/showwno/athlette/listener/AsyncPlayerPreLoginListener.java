package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.object.PlayerAccount;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {
    public AsyncPlayerPreLoginListener(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerProcessor.addPlayer(new PlayerAccount(event.getUniqueId().toString()));
    }
}