package com.showwno.athlette;

import com.showwno.athlette.command.AthleticCommand;
import com.showwno.athlette.command.AthleticResultCommand;
import com.showwno.athlette.listener.PlateClickEvent;
import com.showwno.athlette.module.AthleticProcessor;
import com.showwno.athlette.module.ConfigurationProcessor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Athlette extends JavaPlugin {
    public static JavaPlugin JAVA_PLUGIN;

    @Override
    public void onEnable() {
        JAVA_PLUGIN = this;
        // いろいろ実行!
        ConfigurationProcessor.onEnable();
        AthleticProcessor.onEnable();
        // コマンド登録!
        new AthleticCommand(this);
        new AthleticResultCommand(this);
        // リスナー登録!
        new PlateClickEvent(this);
    }

    @Override
    public void onDisable() {
        AthleticProcessor.onDisable();
        ConfigurationProcessor.onDisable();
    }
}
