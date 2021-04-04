package com.showwno.athlette.command;

import com.showwno.athlette.Athlette;
import com.showwno.athlette.module.AthleticProcessor;
import com.showwno.athlette.object.AthleCP;
import com.showwno.athlette.object.Athletic;
import com.showwno.athlette.option.AthleticOption;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AthleticCommand implements CommandExecutor, TabCompleter {
    public AthleticCommand(Athlette pl) {
        Objects.requireNonNull(pl.getCommand("athletic")).setExecutor(this);
        Objects.requireNonNull(pl.getCommand("athletic")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String lb, @NotNull String[] args) {
        if (args.length >= 1) {
            Player p = null;
            if (s instanceof Player) p = (Player) s;
            Athletic athle = null;
            if (args.length >= 2) {
                if (AthleticProcessor.isExist(args[1]))
                    athle = AthleticProcessor.get(args[1]);
                else {
                    athle = new Athletic(args[1]);
                    athle.setCreator(s.getName());
                }
            }

            switch (args[0]) {
                case "create":
                    if (args.length >= 2) {
                        if (!AthleticProcessor.isExist(args[1])) {
                            AthleticProcessor.add(args[1], athle);
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fを作成しました!");
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは既に存在します!");
                    } else {
                        s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    }
                    break;
                case "enter":
                    if (p == null) {
                        s.sendMessage("こんそーるからじっこうできません! いえーい");
                        break;
                    }
                    if (args.length >= 2) {
                        if (AthleticProcessor.isExist(args[1])) {
                            assert athle != null;
                            athle.setEnter(p.getLocation());
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fの開始地点を設定しました!");
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else {
                        s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    }
                    break;
                case "exit":
                    if (p == null) {
                        s.sendMessage("こんそーるからじっこうできません! いえーい");
                        break;
                    }
                    if (args.length >= 2) {
                        if (AthleticProcessor.isExist(args[1])) {
                            assert athle != null;
                            athle.setExit(p.getLocation());
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fの終了地点を設定しました!");
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else {
                        s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    }
                    break;
                case "list":
                    for (Athletic athletic : AthleticProcessor.ATHLETICS_LIST.values()) {
                        s.sendMessage("§7 - [§b" + athletic.getId() + "§7]"+ athletic.getName());
                    }
                    break;
                case "info":
                    if (args.length >= 2) {
                        if (AthleticProcessor.isExist(args[1])) {
                            assert athle != null;
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fの情報");
                            s.sendMessage("§7 - §f名前: §e§l" + athle.getName());
                            s.sendMessage("§7 - §f製作: " + athle.getCreator());
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else {
                        s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    }
                    break;
                case "setcp":
                    if (args.length >= 3) {
                        if (AthleticProcessor.isExist(args[1])) {
                            assert athle != null;
                            if (p == null) {
                                s.sendMessage("こんそーるからじっこうできません! いえーい");
                                break;
                            }
                            int num;
                            try {
                                num = Integer.parseInt(args[2]);
                            } catch (NumberFormatException e) {
                                s.sendMessage("§7≫ §fチェックポイントの番号は整数である必要があります!");
                                break;
                            }
                            if (athle.getCp(num) != null) {
                                s.sendMessage("§7≫ §fチェックポイント[§e"+num+"§f] は既に存在します! 上書き不可!");
                                break;
                            }
                            Location l = p.getLocation().getBlock().getLocation();
                            AthleCP cp = new AthleCP(l, athle, num);
                            athle.setCp(cp);
                            List<AthleCP> cps = new ArrayList<>();
                            if (!(AthleticProcessor.PLATE_LOCATION_LIST.get(l) == null)) cps.addAll(AthleticProcessor.PLATE_LOCATION_LIST.get(l));
                            cps.add(cp);
                            AthleticProcessor.PLATE_LOCATION_LIST.put(l, cps);
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fのチェックポイント[§e"+num+"§f]を設定しました!");
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    break;
                case "deletecp":
                    if (args.length >= 3) {
                        if (AthleticProcessor.isExist(args[1])) {
                            int num;
                            try {
                                num = Integer.parseInt(args[2]);
                            } catch (NumberFormatException e) {
                                s.sendMessage("§7≫ §fチェックポイントの番号は整数である必要があります!");
                                break;
                            }
                            assert athle != null;
                            if (athle.getCp(num) == null) {
                                s.sendMessage("§7≫ §fチェックポイント[§e"+num+"§f] は存在しません!");
                                break;
                            }
                            athle.deleteCp(num);
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fのチェックポイント[§e"+num+"§f]を削除しました!");
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    break;
                case "name":
                    if (args.length >= 3) {
                        if (AthleticProcessor.isExist(args[1])) {
                            assert athle != null;
                            athle.setName("§e§l "+ args[2] +" ");
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fの名前を設定しました!");
                        } else s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                        break;
                    }
                case "end":
                    if (args.length >= 3) {
                        if (AthleticProcessor.isExist(args[1])) {
                            int num;
                            try {
                                num = Integer.parseInt(args[2]);
                            } catch (NumberFormatException e) {
                                s.sendMessage("§7≫ §f終点の番号は整数である必要があります!");
                                break;
                            }
                            assert athle != null;
                            athle.setEnd(num);
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    break;
                case "delete":
                    if (args.length >= 2) {
                        if (AthleticProcessor.isExist(args[1])) {
                            assert athle != null;
                            for (AthleCP cp : athle.getCPAll()) {
                                AthleticProcessor.PLATE_LOCATION_LIST.remove(cp.getLoc());
                            }
                            AthleticProcessor.ATHLETICS_LIST.remove(athle.getId());
                            s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fを削除しました!");
                        } else s.sendMessage("§7≫ "+ AthleticOption.name(args[1]) +" §fは存在しません!");
                    } else {
                        s.sendMessage("§7≫ コマンドに不備アリ! なんとかせい!");
                    }
                    break;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String a, @NotNull String[] args) {
        final List<String> comps = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            comps.add("create");
            comps.add("name");
            comps.add("end");
            comps.add("setcp");
            comps.add("enter");
            comps.add("exit");
            comps.add("delete");
            comps.add("deletecp");
            comps.add("info");
            comps.add("list");
            StringUtil.copyPartialMatches(args[0], commands, comps);

        } else if (args.length == 2 && !(args[1].equals("list") || args[1].equals("create"))) {
            for (Athletic athletic : AthleticProcessor.ATHLETICS_LIST.values()) {
                comps.add(athletic.getId());
            }
            StringUtil.copyPartialMatches(args[1], commands, comps);
        }
        return comps;
    }
}