package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PlayerItemDropListener implements Listener {
    public PlayerItemDropListener(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent ev) {
        ItemStack i = ev.getItemDrop().getItemStack();
        if (!i.getType().equals(Material.CLOCK)
                && !i.getType().equals(Material.BARRIER)
                && !i.getType().equals(Material.LIME_DYE)
                && !i.getType().equals(Material.GRAY_DYE)
        ) return;
        ev.setCancelled(i.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)
                || i.getItemFlags().contains(ItemFlag.HIDE_PLACED_ON)
                || i.getItemFlags().contains(ItemFlag.HIDE_DESTROYS));
    }
}
