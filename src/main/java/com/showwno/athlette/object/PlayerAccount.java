package com.showwno.athlette.object;

import com.showwno.athlette.manager.Resource;
import com.showwno.athlette.module.ConfigurationProcessor;
import com.showwno.athlette.util.TimeFormatter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerAccount {
    private String uuid;
    private Athletic playingAthletic;
    private Integer cp;
    private long date0;
    private long date1;
    private List<Long> result = Arrays.asList(new Long[100]);
    private boolean isHide;
    private Location lastCP;

    public PlayerAccount(String uuid) {
        this.uuid = uuid;
        this.playingAthletic = null;
        this.cp = 1;
        this.date0 = 0;
        this.date1 = 0;
        Collections.fill(result, null);
        this.isHide = false;
    }

    public String getUniqueId() {//30
        return this.uuid;
    }

    public Integer getCP() {
        return this.cp;
    }
    public void setCP(Integer cp) {
        this.cp = cp;
    }

    public Athletic getPlayingAthletic() {
        return this.playingAthletic;
    }
    public void setPlayingAthletic(Athletic athletic) {
        this.playingAthletic = athletic;
    }

    public void registerResult(int num) {
        this.result.set(num, System.currentTimeMillis() - this.date1);
        this.date1 = System.currentTimeMillis();
    }
    public void setStart() {
        this.date0 = System.currentTimeMillis();
        this.date1 = this.date0;
    }

    public void deleteResult() {
        Collections.fill(this.result, null); //60
    }

    public void saveResult(Player p, String id) {
        int i = 1;
        long full = System.currentTimeMillis() - this.date0;
        String score;
        CustomConfiguration c =  ConfigurationProcessor.RECORD_DATA;
        FileConfiguration con = c.getConfiguration();
        long sa;
        if (con.getLong(id + ".full.time", 0) >= 1) {
            Long current = con.getLong(id +".full.time", 0);
            sa = Math.abs(full - current);
            if (current >= full) {
                Resource.set(id +".full.time", full, c);
                Resource.set(id +".full.by", p.getName(), c);
                score = "§f«§9-"+TimeFormatter.format(sa)+"§f» §e§l更新!!";
            } else score = "§f«§c+"+TimeFormatter.format(sa)+"§f»";
        } else {
            p.sendTitle("§7-- §6§l一番乗り!! §7--", "", 0, 5, 0);
            Resource.set(id + ".first", p.getName(), c);
            Resource.set(id + ".firsttime", full, c);
            Resource.set(id +".full.time", full, c);
            Resource.set(id +".full.by", p.getName(), c);
            score = "";
        } p.sendMessage("§e§l≫ §f総合タイム結果: §l"+ TimeFormatter.format(full) +" "+ score);
        if (this.result == null) return;
        for(Long time : this.result.stream().filter(Objects::nonNull).collect(Collectors.toList())) {
            if (con.getLong(id + ".cps." + i + ".time", 0) >= 1) {
                Long current = con.getLong(id +".cps."+ i + ".time", 0);
                sa = Math.abs(time - current);
                if (current >= time) {
                    Resource.set(id +".cps."+ i + ".time", time, c);
                    Resource.set(id +".cps."+ i + ".by", p.getName(), c);
                    score = "§f«§9-"+TimeFormatter.format(sa)+"§f» §e§l更新!!";
                } else score = "§f«§c+"+TimeFormatter.format(sa)+"§f»";
            } else {
                Resource.set(id +".cps."+ i + ".time", time, c);
                Resource.set(id +".cps."+ i + ".by", p.getName(), c);
                score = "";
            } p.sendMessage("§eCp.§l"+ i +" §7>> §f§l"+ TimeFormatter.format(time) +" "+ score);
            i ++;
        }
        deleteResult();
    }

    public boolean getIsHide() {
        return this.isHide;
    }

    public void setIsHide(boolean b) {
        this.isHide = b;
    }

    public Location getLastCP() {
        return this.lastCP;
    }

    public void setLastCP(Location l) {
        this.lastCP = l;
    }
}