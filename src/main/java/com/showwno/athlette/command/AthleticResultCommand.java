package com.showwno.athlette.command;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.AthleticProcessor;
import com.showwno.athlette.module.ConfigurationProcessor;
import com.showwno.athlette.object.Athletic;
import com.showwno.athlette.object.CustomConfiguration;
import com.showwno.athlette.option.AthleticOption;
import com.showwno.athlette.util.TimeFormatter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AthleticResultCommand implements CommandExecutor, TabCompleter {
    public AthleticResultCommand(Athlette pl) {
        Objects.requireNonNull(pl.getCommand("athle-result")).setExecutor(this);
        Objects.requireNonNull(pl.getCommand("athle-result")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String lb, @NotNull String[] args) {
        if (args.length >= 1) {
            if (!(AthleticProcessor.isExist(args[0])))
                s.sendMessage("§7≫ "+ AthleticOption.name(args[0]) +" §fは存在しません!");
            else {
                String id = args[0];
                CustomConfiguration c =  ConfigurationProcessor.RECORD_DATA;
                FileConfiguration con = c.getConfiguration();
                if (con.getLong( id + ".full.time", 0) >= 1) {
                    s.sendMessage("§e§l≫ §f総合タイム記録: §l"+ TimeFormatter.format(con.getLong( id + ".full.time")) + " §7§oBy."+ con.getString(id + ".full.by"));
                    for(String i : Objects.requireNonNull(con.getConfigurationSection(id + ".cps")).getKeys(false)) {
                        long time = con.getLong(id + ".cps." + i + ".time", 0);
                        String name = con.getString(id + ".cps." + i + ".by", "???");
                        s.sendMessage("§eCp.§l" + i + " §7>> §f§l" + TimeFormatter.format(time) + " §7§oBy." + name);
                    }
                } else {
                    s.sendMessage("§7≫ "+ AthleticOption.name(id) +" §fにはまだ記録がありません!");
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> comps = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            for (Athletic athletic : AthleticProcessor.ATHLETICS_LIST.values()) {
                comps.add(athletic.getId());
            }
            StringUtil.copyPartialMatches(args[0], commands, comps);
        }
        return comps;
    }
}
