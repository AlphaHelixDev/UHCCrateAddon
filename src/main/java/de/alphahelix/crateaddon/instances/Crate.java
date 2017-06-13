package de.alphahelix.crateaddon.instances;

import com.google.gson.JsonElement;
import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.uhcremastered.instances.CustomStatistic;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Crate extends CustomStatistic implements Serializable {

    private String name;
    private ItemStack icon;
    private double rarity;
    private ArrayList<Kit> rewards = new ArrayList<>();

    public Crate(String name, ItemStack icon, double rarity, Kit... rewards) {
        this.name = name;
        this.icon = icon;
        this.rarity = rarity;
        Collections.addAll(this.rewards, rewards);
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public double getRarity() {
        return rarity;
    }

    public ArrayList<Kit> getRewards() {
        return rewards;
    }

    public ArrayList<ItemStack> getKitIcons() {
        ArrayList<ItemStack> icons = new ArrayList<>();
        for (Kit k : getRewards()) {
            icons.add(new ItemBuilder(k.getIcon()).setName(k.getName()).build());
        }
        return icons;
    }

    @Override
    public String toString() {
        return "Crate{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", rarity=" + rarity +
                ", rewards=" + rewards +
                '}';
    }

    @Override
    public JsonElement toFileValue() {
        return SimpleJSONFile.gson.toJsonTree(this);
    }
}
