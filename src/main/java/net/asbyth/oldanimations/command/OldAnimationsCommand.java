package net.asbyth.oldanimations.command;

import net.asbyth.oldanimations.OldAnimations;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class OldAnimationsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "oldanimations";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("animations", "old_animations", "newframes"); // new frames because theres so many of these mods
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/oldanimations";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        OldAnimations.instance.openGui();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
