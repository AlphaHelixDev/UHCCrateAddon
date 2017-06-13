package de.alphahelix.crateaddon.inventories;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Sounds;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.crateaddon.instances.Crate;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

public class CrateOpenInventory extends SimpleListener {

    private static final HashMap<String, Thread> THREADS = new HashMap<>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (THREADS.containsKey(e.getPlayer().getName()))
            THREADS.get(e.getPlayer().getName()).stop();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (THREADS.containsKey(e.getWhoClicked().getName()))
            e.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
    public void openInventory(Crate c, Player p) {
        if (THREADS.containsKey(p.getName())) {
            THREADS.get(p.getName()).stop();
        }

        Inventory inv = Bukkit.createInventory(null, 9 * 3, CrateAddon.getCrateOptions().getOpenGUIName(c));
        PlayerStatistic stats = StatsUtil.getStatistics(p);

        Thread thread = new Thread(
                () -> {
                    ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7)
                            .build();
                    ItemStack winGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 5)
                            .build();

                    for (int slot = 0; slot < 9; slot++) {
                        inv.setItem(slot, glass);
                    }

                    for (int slot = 18; slot < inv.getSize(); slot++) {
                        inv.setItem(slot, glass);
                    }

                    inv.setItem(4, winGlass);
                    inv.setItem(22, winGlass);

                    p.openInventory(inv);

                    int showns = new Random().nextInt(20) + 50;
                    int maximalSleep = 3 * 100;
                    int toSleep = 0;
                    double differenceBetweenRuns = maximalSleep / showns;

                    for (int index = 0; index < showns; index++) {
                        for (int slot = 9; slot <= 17; slot++) {
                            if (slot > 9) {
                                inv.setItem(slot - 1, inv.getItem(slot));
                            }
                        }

                        if (c.getKitIcons().size() <= 0)
                            continue;

                        int r = new Random().nextInt(
                                c.getKitIcons().size());

                        ItemStack obtain = new ItemStack(c.getKitIcons().get(r));

                        inv.setItem(17, obtain);

                        toSleep += differenceBetweenRuns;

                        try {
                            Thread.sleep(toSleep);
                        } catch (InterruptedException ignored) {
                        }

                        p.playSound(p.getLocation(), Sounds.CLICK.bukkitSound(), 1F, 1F);
                        p.updateInventory();
                    }

                    if (inv.getItem(13) == null)
                        return;

                    ItemStack won = inv.getItem(13).clone();

                    if (!hasDisplayName(won))
                        return;

                    inv.clear();
                    inv.setItem(13, won);

                    for (int slot = 0; slot < 9; slot++) {
                        inv.setItem(slot, winGlass);
                    }

                    for (int slot = 18; slot < inv.getSize(); slot++) {
                        inv.setItem(slot, winGlass);
                    }

                    p.playSound(p.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 1F, 0F);
                    p.updateInventory();

                    Kit kit = null;

                    for (Kit k : c.getRewards()) {
                        if (isSame(won, k.getIcon())) kit = k;
                    }

                    if (kit != null)
                        if (!stats.hasKit(kit))
                            stats.addKit(kit);

                    stats.removeCustomStatistic(c);

                    StatsUtil.pushCache(p);

                    new BukkitRunnable() {
                        public void run() {
                            THREADS.remove(p.getName());
                            p.closeInventory();
                        }
                    }.runTaskLaterAsynchronously(UHC.getInstance(), 20 * 3);
                });

        THREADS.put(p.getName(), thread);
        thread.start();
    }

    private boolean hasDisplayName(ItemStack itemStack) {
        return (itemStack != null && itemStack.hasItemMeta()
                && itemStack.getItemMeta().hasDisplayName());
    }

    private boolean isSame(ItemStack a, ItemStack b) {
        return (a.getType() == b.getType()) &&
                (a.getAmount() == b.getAmount()) &&
                (a.getDurability() == b.getDurability()) &&
                (a.hasItemMeta() == b.hasItemMeta()) &&
                (a.hasItemMeta() && a.getItemMeta().hasDisplayName() == b.getItemMeta().hasDisplayName()) &&
                (a.hasItemMeta() && a.getItemMeta().getDisplayName().equals(b.getItemMeta().getDisplayName())) &&
                (a.hasItemMeta() && a.getItemMeta().hasLore() == b.getItemMeta().hasLore()) &&
                (a.hasItemMeta() && a.getItemMeta().getLore().equals(b.getItemMeta().getLore()));
    }
}
