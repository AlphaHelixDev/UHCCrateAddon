package de.alphahelix.crateaddon.files;

import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.crateaddon.instances.Messages;

public class MessageFile extends SimpleJSONFile {
    public MessageFile() {
        super(CrateAddon.getInstance().getDataFolder().getAbsolutePath(), "messages.json");
        addMessages();
        CrateAddon.setMessages(getValue("Messages", Messages.class));
    }

    private void addMessages() {
        if(jsonContains("Messages")) return;

        setValue("Messages", new Messages(
                "§7[§3CrateAddon§7] ",
                "§7You just got a [crate] crate dropped.",
                "§7You don't have permissions to execute this command!",
                "§7You gave yourself a [crate] crate.",
                "§7You gave [player] a [crate] crate.",
                "§7You took a [crate] from yourself.",
                "§7You took a [crate] from [player]."
        ));
    }
}
