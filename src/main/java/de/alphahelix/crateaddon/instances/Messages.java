package de.alphahelix.crateaddon.instances;

import org.bukkit.OfflinePlayer;

public class Messages {

    private String prefix, crateDropped, noPermissions, crateGivenOwn, crateGivenOther, crateTakeOwn, crateTakeOther;

    public Messages(String prefix, String crateDropped, String noPermissions, String crateGivenOwn, String tagSetOther, String tagResetOwn, String tagResetOther) {
        this.prefix = prefix;
        this.crateDropped = crateDropped;
        this.noPermissions = noPermissions;
        this.crateGivenOwn = crateGivenOwn;
        this.crateGivenOther = tagSetOther;
        this.crateTakeOwn = tagResetOwn;
        this.crateTakeOther = tagResetOther;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCrateDropped(String crate) {
        return crateDropped.replace("[crate]", crate);
    }

    public String getNoPermissions() {
        return getPrefix() + noPermissions;
    }

    public String getCrateGivenOwn(String crate) {
        return getPrefix() + crateGivenOwn.replace("[crate]", crate);
    }

    public String getCrateGivenOther(OfflinePlayer other, String crate) {
        return getPrefix() + crateGivenOther.replace("[player]", other.getName()).replace("[crate]", crate);
    }

    public String getCrateTakeOwn(String crate) {
        return getPrefix() + crateTakeOwn.replace("[crate]", crate);
    }

    public String getCrateTakeOther(OfflinePlayer other, String crate) {
        return getPrefix() + crateTakeOther.replace("[player]", other.getName()).replace("[crate]", crate);
    }

}
