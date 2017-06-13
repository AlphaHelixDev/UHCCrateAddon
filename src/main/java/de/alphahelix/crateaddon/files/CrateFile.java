package de.alphahelix.crateaddon.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.crateaddon.instances.Crate;
import de.alphahelix.crateaddon.instances.CrateOptions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CrateFile extends SimpleJSONFile {
    public CrateFile() {
        super(CrateAddon.getInstance().getDataFolder().getAbsolutePath(), "crates.json");
        init();
        CrateAddon.setCrateOptions(getValue("Options", CrateOptions.class));

        for (Crate c : getValue("Crates", Crate[].class)) {
            CrateAddon.getFileCrates().add(c);
        }
    }

    private void init() {
        if (jsonContains("Options")) return;

        setValue("Options", new CrateOptions(
                "§7You just got a [crate] crate dropped!",
                "§5Crates",
                "§5Crate§7: [crate]",
                new SimpleFile.InventoryItem(new ItemBuilder(Material.CHEST).setName("§5Crates").build(), 4)));

        setValue("Crates", new Crate[]{
                new Crate(
                        "Example", new ItemStack(Material.APPLE), 1.0,
                        new Kit("ExampleCrateKit", 0, new ItemStack(Material.APPLE), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.COMPASS))
                )
        });
    }
}
