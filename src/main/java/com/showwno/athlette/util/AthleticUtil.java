package com.showwno.athlette.util;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.object.Athletic;
import com.showwno.athlette.object.PlayerAccount;
import com.showwno.athlette.option.AthleticOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        AthleticUtil.athleInventory(p);
        account.setLastCP(p.getLocation());
    }

    public static void cp(Player p, int i) {
        p.sendActionBar(Component.text("§9>§7>  CheckPoint §e"+ i +"  §7<§9<"));
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 2, 2);
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        account.setCP(i);
        account.registerResult(i - 1);
        account.setLastCP(p.getLocation());
    }

    public static void goal(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 2, (float) 0.1);
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        account.registerResult(account.getCP());
        account.saveResult(p, account.getPlayingAthletic().getId());
        account.setPlayingAthletic(null);
        account.setCP(1);
        try {
            if (account.getPlayingAthletic().getExit() != null) p.teleport(account.getPlayingAthletic().getExit());
        } catch (Exception ignored) {
        }
        AthleticUtil.deleteAthleInventory(p);
    }

    public static void end(Player p) {
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        try {
            if (account.getPlayingAthletic().getExit() != null) p.teleport(account.getPlayingAthletic().getExit());
        } catch (Exception ignored) {
        }

        account.setPlayingAthletic(null);
        account.setCP(1);
        account.deleteResult();
        AthleticUtil.deleteAthleInventory(p);
    }


    private static void athleInventory(Player p) {
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        ItemStack item_3 = new ItemStack(Material.BARRIER);
        ItemMeta meta_3 = item_3.getItemMeta();
        meta_3.setDisplayName("§7» §c§lアスレをやめる");
        meta_3.addItemFlags(ItemFlag.HIDE_DESTROYS);
        item_3.setItemMeta(meta_3);

        ItemStack item_4 = new ItemStack(Material.CLOCK);
        ItemMeta meta_4 = item_4.getItemMeta();
        meta_4.setDisplayName("§7» §e§lチェックポイントへTP");
        meta_4.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        item_4.setItemMeta(meta_4);

        ItemStack item_5 = AthleticUtil.getHideItem(account.getIsHide());

        p.getInventory().setItem(3, item_3);
        p.getInventory().setItem(4, item_4);
        p.getInventory().setItem(5, item_5);
    }

    public static void deleteAthleInventory(Player p) {
        p.getInventory().clear(3);
        p.getInventory().clear(4);
        p.getInventory().clear(5);
    }

    public static ItemStack getHideItem(boolean b) {
        Material type;
        String name;
        if (b) {
            type = Material.GRAY_DYE;
            name = "§c§l無効";
        } else {
            type = Material.LIME_DYE;
            name = "§a§l有効";
        }
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§7» §fプレイヤー表示: " + name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static void hide(Player p) {
        for (Player loop : p.getServer().getOnlinePlayers()) {
            p.hidePlayer(Athlette.JAVA_PLUGIN, loop);
        }
    }

    public static void unhide(Player p) {
        for (Player loop : p.getServer().getOnlinePlayers()) {
            p.showPlayer(Athlette.JAVA_PLUGIN, loop);
        }
    }
}