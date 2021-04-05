package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.object.PlayerAccount;
import com.showwno.athlette.util.AthleticUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

public class PlayerClickListener implements Listener {
    public PlayerClickListener(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent ev) {
        if (
                ev.getAction().equals(Action.PHYSICAL)
                        || ev.getItem() == null
                        || !ev.getItem().getType().equals(Material.CLOCK)
                        && !ev.getItem().getType().equals(Material.BARRIER)
                        && !ev.getItem().getType().equals(Material.LIME_DYE)
                        && !ev.getItem().getType().equals(Material.GRAY_DYE)
        ) return;
        Player p = ev.getPlayer();
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        if (ev.getItem().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            float pitch;
            if (account.getIsHide()) {
                account.setIsHide(false);
                AthleticUtil.unhide(p);
                pitch = 1.0f;
            } else {
                account.setIsHide(true);
                AthleticUtil.hide(p);
                pitch = 0.1f;
            }
            p.getInventory().setItem(5, AthleticUtil.getHideItem(account.getIsHide()));
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 2, pitch);
        } else if (ev.getItem().getItemFlags().contains(ItemFlag.HIDE_PLACED_ON)) {
            p.teleport(account.getLastCP());
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 2, 1);
        } else if (ev.getItem().getItemFlags().contains(ItemFlag.HIDE_DESTROYS)) {
            ev.setCancelled(true);
            AthleticUtil.end(p);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.MASTER, 2, 0.2f);
        }
    }
}
