package de.alphahelix.crateaddon.commands;

import de.alphahelix.alphalibary.command.arguments.Argument;
import de.alphahelix.crateaddon.CrateAddon;
import de.alphahelix.crateaddon.instances.Crate;

public class CrateArgument extends Argument<Crate> {
    @Override
    public boolean matches() {
        return fromArgument() != null;
    }

    @Override
    public Crate fromArgument() {
        for(Crate c : CrateAddon.getFileCrates())
            if(c.getName().equalsIgnoreCase(getEnteredArgument())) return c;
        return null;
    }
}
