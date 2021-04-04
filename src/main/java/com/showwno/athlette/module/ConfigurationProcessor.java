package com.showwno.athlette.module;

import com.showwno.athlette.object.CustomConfiguration;

public class ConfigurationProcessor {

    public static final CustomConfiguration RECORD_DATA = new CustomConfiguration("record.yml");
    public static final CustomConfiguration SERVER_DATA = new CustomConfiguration("data.yml");

    public static void onEnable() {
        SERVER_DATA.reloadConfiguration();
        RECORD_DATA.reloadConfiguration();
        SERVER_DATA.saveConfiguration();
        RECORD_DATA.saveConfiguration();
    }

    public static void onDisable() {
        SERVER_DATA.saveConfiguration();
        RECORD_DATA.saveConfiguration();
    }
}