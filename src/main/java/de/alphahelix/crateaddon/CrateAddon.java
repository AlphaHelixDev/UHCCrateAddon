package de.alphahelix.crateaddon;

import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.crateaddon.commands.CrateCommand;
import de.alphahelix.crateaddon.files.CrateFile;
import de.alphahelix.crateaddon.files.MessageFile;
import de.alphahelix.crateaddon.instances.Crate;
import de.alphahelix.crateaddon.instances.CrateOptions;
import de.alphahelix.crateaddon.instances.Messages;
import de.alphahelix.crateaddon.inventories.CrateInventory;
import de.alphahelix.crateaddon.inventories.CrateOpenInventory;
import de.alphahelix.crateaddon.listerners.DeathListener;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.addons.core.Addon;
import de.alphahelix.uhcremastered.enums.GState;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CrateAddon extends Addon {

    private static CrateAddon instance;
    private static CrateOptions crateOptions;
    private static Messages messages;
    private static CrateOpenInventory crateOpenInventory;
    private static ArrayList<Crate> fileCrates = new ArrayList<>();

    public static CrateAddon getInstance() {
        return instance;
    }

    public static CrateOptions getCrateOptions() {
        return crateOptions;
    }

    public static void setCrateOptions(CrateOptions crateOptions) {
        CrateAddon.crateOptions = crateOptions;
    }

    public static CrateOpenInventory getCrateOpenInventory() {
        return crateOpenInventory;
    }

    public static Messages getMessages() {
        return messages;
    }

    public static void setMessages(Messages messages) {
        CrateAddon.messages = messages;
    }

    public static ArrayList<Crate> getCrates(OfflinePlayer p) {
        return StatsUtil.getStatistics(p).getCustomStatistics(Crate.class);
    }

    public static ArrayList<Crate> getFileCrates() {
        return fileCrates;
    }

    public static Crate getRandomCrate() {
        if (getFileCrates().isEmpty()) return null;
        return getFileCrates().get(ThreadLocalRandom.current().nextInt(getFileCrates().size()));
    }

    @Override
    public void onEnable() {
        instance = this;

        new CrateFile();
        new MessageFile();
        new CrateInventory();
        new CrateCommand();
        crateOpenInventory = new CrateOpenInventory();

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                if (GState.is(GState.LOBBY))
                    Util.runLater(5, false, () ->
                            e.getPlayer().getInventory().setItem(getCrateOptions().getIcon().getSlot(), getCrateOptions().getIcon().getItemStack()));
            }
        }, UHC.getInstance());

        new DeathListener();
    }
}
