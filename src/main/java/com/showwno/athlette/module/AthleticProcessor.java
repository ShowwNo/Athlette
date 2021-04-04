package com.showwno.athlette.module;
import com.showwno.athlette.manager.Resource;
import com.showwno.athlette.object.AthleCP;
import com.showwno.athlette.object.Athletic;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public class AthleticProcessor {
    public static final Map<String, Athletic> ATHLETICS_LIST = new HashMap<>();
    public static final Map<Location, List<AthleCP>> PLATE_LOCATION_LIST = new HashMap<>();

    public static void onEnable() {
        ConfigurationSection configurationSection = Resource.getConfigurationSection("athletic", ConfigurationProcessor.SERVER_DATA);
        if (!(configurationSection == null)) {
            for (String id : configurationSection.getKeys(false)) {
                Athletic athle = new Athletic(id);
                PLATE_LOCATION_LIST.putAll(athle.getCPAll()
                        .stream().collect(Collectors.groupingBy(AthleCP::getLoc)));
                ATHLETICS_LIST.put(id, athle);
            }
        }
    }

    public static void onDisable() {
        for (Athletic athletic : ATHLETICS_LIST.values()) {
            athletic.onDisable();
        }
    }

    public static void add(String id, Athletic athletic) {
        ATHLETICS_LIST.put(id, athletic);
    }

    public static boolean isExist(String id) {
        return ATHLETICS_LIST.containsKey(id);
    }
    public static Athletic get(String id) {
        if (isExist(id)) return ATHLETICS_LIST.get(id);
        return null;
    }

    public static List<AthleCP> getSortedPlateLocation(Location l) {
        List<AthleCP> cps = PLATE_LOCATION_LIST.get(l);
        cps.sort(Comparator.comparingInt(AthleCP::getNum));
        Collections.reverse(cps);
        return cps;
    }
}
