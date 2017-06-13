package de.alphahelix.crateaddon.instances;

import de.alphahelix.alphalibary.file.SimpleFile;

public class CrateOptions {

    private String dropMessage, guiName, openGUIName;
    private SimpleFile.InventoryItem icon;

    public CrateOptions(String dropMessage, String guiName, String openGUIName, SimpleFile.InventoryItem icon) {
        this.dropMessage = dropMessage;
        this.guiName = guiName;
        this.openGUIName = openGUIName;
        this.icon = icon;
    }

    public String getDropMessage() {
        return dropMessage;
    }

    public String getGUIName() {
        return guiName;
    }

    public String getOpenGUIName(Crate crate) {
        return openGUIName.replace("[crate]", crate.getName());
    }

    public SimpleFile.InventoryItem getIcon() {
        return icon;
    }
}
