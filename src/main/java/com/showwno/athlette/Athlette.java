package com.showwno.athlette;

import com.showwno.athlette.command.AthleticCommand;
import com.showwno.athlette.command.AthleticResultCommand;
import com.showwno.athlette.listener.AsyncPlayerPreLoginListener;
import com.showwno.athlette.listener.PlateClickEvent;
import com.showwno.athlette.module.AthleticProcessor;
import com.showwno.athlette.module.ConfigurationProcessor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Athlette extends JavaPlugin {
    public static JavaPlugin JAVA_PLUGIN;

    @Override
    public void onEnable() {
        JAVA_PLUGIN = this;

        ConfigurationProcessor.onEnable();
        AthleticProcessor.onEnable();

        new AthleticCommand(this);
        new AthleticResultCommand(this);


        new PlateClickEvent(this);
        new AsyncPlayerPreLoginListener(this);
    }

    @Override
    public void onDisable() {
        AthleticProcessor.onDisable();
        ConfigurationProcessor.onDisable();
    }
}
