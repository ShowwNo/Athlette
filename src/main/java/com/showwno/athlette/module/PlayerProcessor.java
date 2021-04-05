package com.showwno.athlette.module;


import com.showwno.athlette.object.PlayerAccount;
import org.bukkit.Bukkit;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerProcessor {
    public static final ConcurrentHashMap<String, PlayerAccount> PLAYERS_MAP = new ConcurrentHashMap<String, PlayerAccount>();

    public static PlayerAccount addPlayer(PlayerAccount playerAccount) {
        PLAYERS_MAP.put(playerAccount.getUniqueId(), playerAccount);
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
}