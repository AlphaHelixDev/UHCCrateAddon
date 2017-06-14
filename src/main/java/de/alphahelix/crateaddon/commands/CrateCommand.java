package de.alphahelix.crateaddon.commands;

import de.alphahelix.alphalibary.command.CommandWatcher;
import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.alphalibary.command.arguments.OfflinePlayerArgument;
import de.alphahelix.alphalibary.command.arguments.StringArgument;
import de.alphahelix.alphalibary.utils.MessageUtil;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrateCommand extends SimpleCommand {
    public CrateCommand() {
        super("crate", "Give or take crate to/from a player");
    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {

        CommandWatcher own = new CommandWatcher(args);
        CommandWatcher others = new CommandWatcher(args);

        StringArgument giveTake = new StringArgument();
        CrateArgument name = new CrateArgument();
        OfflinePlayerArgument player = new OfflinePlayerArgument();

        own.addArguments(giveTake, name);
        others.addArguments(giveTake, name, player);


        if (own.isSame()) {
            if (cs instanceof Player) {
                if (giveTake.fromArgument().equalsIgnoreCase("give")) {
                    if (cs.hasPermission("uhc.crates.give.own") || cs.hasPermission("uhc.crates.admin") || cs.hasPermission("uhc.admin")) {
                        StatsUtil.getStatistics((OfflinePlayer) cs).addCustomStatistic(name.fromArgument());
                        StatsUtil.pushCache((OfflinePlayer) cs);
                        cs.sendMessage(CrateAddon.getMessages().getCrateGivenOwn(name.getEnteredArgument()));
                    } else
                        cs.sendMessage(CrateAddon.getMessages().getNoPermissions());
                } else if (giveTake.fromArgument().equalsIgnoreCase("take")) {
                    if (cs.hasPermission("uhc.crates.take.own") || cs.hasPermission("uhc.crates.admin") || cs.hasPermission("uhc.admin")) {
                        StatsUtil.getStatistics((OfflinePlayer) cs).removeCustomStatistic(name.fromArgument());
                        StatsUtil.pushCache((OfflinePlayer) cs);
                        cs.sendMessage(CrateAddon.getMessages().getCrateTakeOwn(name.getEnteredArgument()));
                    } else
                        cs.sendMessage(CrateAddon.getMessages().getNoPermissions());
                }
            }
            return true;
        } else if (others.isSame()) {
            if (giveTake.fromArgument().equalsIgnoreCase("give")) {
                if (cs.hasPermission("uhc.crates.give.others") || cs.hasPermission("uhc.crates.admin") || cs.hasPermission("uhc.admin")) {
                    StatsUtil.getStatistics(player.fromArgument()).addCustomStatistic(name.fromArgument());
                    StatsUtil.pushCache(player.fromArgument());
                    cs.sendMessage(CrateAddon.getMessages().getCrateGivenOther(player.fromArgument(), name.getEnteredArgument()));
                } else
                    cs.sendMessage(CrateAddon.getMessages().getNoPermissions());
            } else if(giveTake.fromArgument().equalsIgnoreCase("take")) {
                if (cs.hasPermission("uhc.crates.take.others") || cs.hasPermission("uhc.crates.admin") || cs.hasPermission("uhc.admin")) {
                    StatsUtil.getStatistics(player.fromArgument()).removeCustomStatistic(name.fromArgument());
                    StatsUtil.pushCache(player.fromArgument());
                    cs.sendMessage(CrateAddon.getMessages().getCrateTakeOther(player.fromArgument(), name.getEnteredArgument()));
                } else
                    cs.sendMessage(CrateAddon.getMessages().getNoPermissions());
            }
            return true;
        }

        if (cs instanceof Player) {
            Player p = (Player) cs;
            MessageUtil.sendCenteredMessage(p, "§3" + new String(new char[53]).replace("\0", "="));
            cs.sendMessage(" ");
            MessageUtil.sendCenteredMessage(p, "§7/crate give §8<§7name§8> §3- §7Gives yourself a crate");
            MessageUtil.sendCenteredMessage(p, "§7/crate give §8<§7name§8> §8<§7player§8> §3- §7Gives someone a crate");
            MessageUtil.sendCenteredMessage(p, "§7/crate take §3- §7Take a crate from yourself");
            MessageUtil.sendCenteredMessage(p, "§7/crate take §8<§7player§8> §3- §7Take a crate from someone else");
            cs.sendMessage(" ");
            MessageUtil.sendCenteredMessage(p, "§3" + new String(new char[53]).replace("\0", "="));
        } else {
            cs.sendMessage("§7/crate give §8<§7name§8> §8<§7player§8> §3- §7Gives someone a crate");
            cs.sendMessage("§7/crate take §8<§7player§8> §3- §7Take a crate from someone else");
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String s, String[] strings) {
        return null;
    }
}
