package com.showwno.athlette.object;

import com.showwno.athlette.manager.Resource;
import com.showwno.athlette.module.ConfigurationProcessor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class Athletic {
    private String id;
    private String name;
    private String creator;
    private Integer end;
    private List<AthleCP> cp = Arrays.asList(new AthleCP[100]);
    private Location enter = null;
    private Location exit = null;

    public Athletic(String id) {
        String path = "athletic." + id;
        CustomConfiguration c = ConfigurationProcessor.SERVER_DATA;
        ConfigurationSection con = c.getConfiguration().getConfigurationSection(path + ".cps");
        this.id = id;
        this.name = (String) Resource.get(path + ".name", c, "無題");
        this.creator = (String) Resource.get(path + ".creator", c, "不明");
        this.end = (Integer) Resource.get(path + ".end", c, 1);
        if (c.getConfiguration().getLocation(path + ".enter") != null)
            this.enter = c.getConfiguration().getLocation(path + ".enter");
        if (c.getConfiguration().getLocation(path + ".exit") != null)
            this.exit = c.getConfiguration().getLocation(path + ".exit");
        Collections.fill(cp, null);
        if (!(con == null)) {
            for (String key : con.getKeys(false)) {
                if (!key.equals("0")) {
                    AthleCP cp = new AthleCP(c.getConfiguration().getLocation(path + ".cps." + key), this, Integer.parseInt(key));
                    this.cp.set(Integer.parseInt(key), cp);
                }
            }
        }
    }

    public String getId() {
        return this.id;
    }

    public @Nullable String getName() { return this.name; }
    public void setName(String name) {
        this.name = name;
    }

    public @NotNull String getCreator() {
        return this.creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public @NotNull Integer getEnd() {
        return this.end;
    }
    public void setEnd(Integer end) {
        this.end = end;
    }

    public AthleCP getCp(int i) {
        return this.cp.get(i);
    }
    public void setCp(AthleCP cp) {
        this.cp.set(cp.getNum(), cp);
        if (this.id.equals(cp.getId())) cp.setId(this.id);
    }
    public void deleteCp(int i) {
        this.cp.remove(i);
    }

    public Location getEnter() {
        return enter;
    }
    public void setEnter(Location enter) {
        this.enter = enter;
    }

    public Location getExit() {
        return this.exit;
    }
    public void setExit(Location exit) {
        this.exit = exit;
    }

    public List<AthleCP> getCPAll() {
        return this.cp.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void onDisable() {
        String path = "athletic." + id;
        CustomConfiguration c = ConfigurationProcessor.SERVER_DATA;
        Resource.set(path + ".name", this.name, c);
        Resource.set(path + ".creator", this.creator, c);
        Resource.set(path + ".end", this.end, c);
        for (AthleCP cp : this.cp.stream().filter(Objects::nonNull).collect(Collectors.toList())) {
            Resource.set(path + ".cps." + cp.getNum(), cp.getLoc(), c);
        }
    }
}