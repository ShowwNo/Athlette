package com.showwno.athlette.object;

import org.bukkit.Location;

public class AthleCP {
    private Location loc;
    private String id;
    private Athletic athle;
    private Integer num;

    public AthleCP(Location loc, Athletic athle, Integer num) {
        this.loc = loc;
        this.athle = athle;
        this.id = athle.getId();
        this.num = num;
    }

    public Location getLoc() {
        return this.loc;
    }
    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Athletic getAthle() {
        return this.athle;
    }

    public Integer getNum() {
        return this.num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}