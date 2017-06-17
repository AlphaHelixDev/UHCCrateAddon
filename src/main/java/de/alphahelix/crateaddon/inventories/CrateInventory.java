package de.alphahelix.crateaddon.inventories;

import de.alphahelix.alphalibary.inventorys.InventoryBuilder;
import de.alphahelix.alphalibary.utils.Sounds;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.crateaddon.instances.Crate;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.enums.GState;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrateInventory {

    public CrateInventory() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent e) {
                if (!GState.is(GState.LOBBY)) return;
                if (e.getItem() == null) return;
                if(!Util.isSame(e.getItem(), CrateAddon.getCrateOptions().getIcon().getItemStack())) return;

                openInv(e.getPlayer());
            }
        }, UHC.getInstance());
    }

    private void openInv(Player p) {
        PlayerStatistic stats = StatsUtil.getStatistics(p);

        InventoryBuilder ib = new InventoryBuilder(p, CrateAddon.getCrateOptions().getGUIName(), ((CrateAddon.getCrates(p).size() / 9) + 1) * 9);

        for (int i = 0; i < CrateAddon.getCrates(p).size(); i++) {
            Crate c = CrateAddon.getCrates(p).get(i);
            ib.addItem(ib.new SimpleItem(c.getIcon(), i) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    Player p = (Player) e.getWhoClicked();

                    e.setCancelled(true);

                    if (!stats.hasCustomStatistic(c)) return;
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 10, 1);

                    CrateAddon.getCrateOpenInventory().openInventory(c, p);
                }
            });
        }

        p.openInventory(ib.build());
    }
}
