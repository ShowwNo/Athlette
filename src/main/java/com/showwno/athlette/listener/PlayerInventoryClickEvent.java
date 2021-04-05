package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryClickEvent implements Listener {
    public PlayerInventoryClickEvent(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent ev) {
        ItemStack i = ev.getInventory().getItem(ev.getSlot());
        if (i == null
                || !i.getType().equals(Material.CLOCK)
                && !i.getType().equals(Material.BARRIER)
                && !i.getType().equals(Material.LIME_DYE)
                && !i.getType().equals(Material.GRAY_DYE)
        ) return;
        ev.setCancelled(i.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)
                || i.getItemFlags().contains(ItemFlag.HIDE_PLACED_ON)
                || i.getItemFlags().contains(ItemFlag.HIDE_DESTROYS));
    }
}
