package de.alphahelix.crateaddon.listerners;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.crateaddon.instances.Crate;
import de.alphahelix.uhcremastered.enums.GState;
import de.alphahelix.uhcremastered.events.game.DummieDeathEvent;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathListener extends SimpleListener {

    @EventHandler
    public void onDeath(DummieDeathEvent e) {
        if (e.getKiller() != null && e.getKiller() instanceof Player) {
            Crate c = CrateAddon.getRandomCrate();
            PlayerStatistic stats = StatsUtil.getStatistics((OfflinePlayer) e.getKiller());

            if (c == null) return;
            if (Math.random() > c.getRarity()) return;

            stats.addCustomStatistic(c);
            e.getKiller().sendMessage(CrateAddon.getMessages().getCrateDropped(c.getName()));
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (GState.is(GState.LOBBY) || GState.is(GState.RESTART)) return;

        if (e.getEntity() instanceof Player) {
            Player dead = (Player) e.getEntity();

            if (dead.getKiller() != null) {
                Crate c = CrateAddon.getRandomCrate();
                PlayerStatistic stats = StatsUtil.getStatistics(dead.getKiller());

                if (c == null) return;
                if (Math.random() > c.getRarity()) return;

                stats.addCustomStatistic(c);
                dead.getKiller().sendMessage(CrateAddon.getMessages().getCrateDropped(c.getName()));
            }
        }
    }
}
