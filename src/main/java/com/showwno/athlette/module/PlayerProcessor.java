package com.showwno.athlette.module;


import com.showwno.athlette.manager.Resource;
import com.showwno.athlette.object.PlayerAccount;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerProcessor {
    private static final ConcurrentHashMap<String, PlayerAccount> PLAYERS_MAP = new ConcurrentHashMap<String, PlayerAccount>();

    public static PlayerAccount addPlayer(PlayerAccount playerAccount) {
        PLAYERS_MAP.put(playerAccount.getUniqueId().toString(), playerAccount);
        return playerAccount;
    }

    public static PlayerAccount getPlayerFromUUID(String uuid) {
        return getPlayer(uuid, Bukkit.getOfflinePlayer(uuid).getName());
    }

    public static PlayerAccount getPlayer(String uuid, String name) {
        PlayerAccount playerAccount = PLAYERS_MAP.get(uuid);
        if (playerAccount == null) {
            playerAccount = addPlayer(new PlayerAccount(uuid));
        }
        return playerAccount;
    }

    @SuppressWarnings("deprecation")
    public static PlayerAccount getPlayer(String name) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        return getPlayer(offlinePlayer.getUniqueId().toString(), offlinePlayer.getName());
    }
}