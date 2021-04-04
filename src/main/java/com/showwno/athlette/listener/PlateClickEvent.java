package com.showwno.athlette.listener;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.AthleticProcessor;
import com.showwno.athlette.module.PlayerProcessor;
import com.showwno.athlette.object.AthleCP;
import com.showwno.athlette.object.Athletic;
import com.showwno.athlette.object.PlayerAccount;
import com.showwno.athlette.util.AthleticUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Objects;

public class PlateClickEvent implements Listener {
    public PlateClickEvent(Athlette pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onPlateEvent(PlayerInteractEvent ev) {
        if (!(ev.getAction() == Action.PHYSICAL)) return;
        Player p = ev.getPlayer();
        Location l;
        List<AthleCP> cps;
        try {
            l = Objects.requireNonNull(ev.getClickedBlock()).getLocation();
            cps = AthleticProcessor.getSortedPlateLocation(l);
        } catch (Exception e) { return; }
        PlayerAccount account = PlayerProcessor.getPlayerFromUUID(p.getUniqueId().toString());
        for (AthleCP cp : cps) {
            Integer c = cp.getNum();
            if (!(account.getPlayingAthletic() == null)) {
                String id = cp.getId();
                if (c == account.getCP() + 1 && account.getPlayingAthletic().getId().equals(id)) {
                    Athletic athle = cp.getAthle();
                    if (athle.getEnd().equals(account.getCP())) {
                        AthleticUtil.goal(p);
                    } else {
                        AthleticUtil.cp(p, c);
                    }
                    break;
                }
            } if (c.equals(1)) {
                AthleticUtil.start(p, cp.getAthle());
            }
        }
    }
}