package com.showwno.athlette.object;

import com.showwno.athlette.Athlette;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CustomConfiguration {
    private FileConfiguration fileConfiguration;
    private File file;
    private String name;

    public CustomConfiguration(String name) {
        this.name = name;
        this.file = new File(Athlette.JAVA_PLUGIN.getDataFolder(), this.name);
    }

    public void reloadConfiguration() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        InputStream inputStream = Athlette.JAVA_PLUGIN.getResource(this.name);
        if (!(inputStream == null)) {
            this.fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
        }
    }

    public FileConfiguration getConfiguration() {
        if (this.fileConfiguration == null) {
            this.reloadConfiguration();
        }
        return this.fileConfiguration;
    }

    public void saveConfiguration() {
        if (!(this.fileConfiguration == null)) {
            try {
                this.getConfiguration().save(this.file);
            }
            catch (Exception exception) {
                Bukkit.getLogger().info("[§e§lAthlette§f] §cデータ保存中にエラーが発生しました:");
                exception.printStackTrace();
            }
        }
    }
}