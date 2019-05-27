package net.asbyth.oldanimations.gui;

import net.asbyth.oldanimations.OldAnimations;
import net.asbyth.oldanimations.gui.helper.AbstractButton;
import net.asbyth.oldanimations.gui.helper.AbstractGui;
import net.minecraft.client.gui.GuiButton;

import static net.asbyth.oldanimations.config.Settings.*;

public class AnimationsGui extends AbstractGui {

    @Override
    public void initGui() {
        buttonList.add(new AbstractButton(0, getCenter() - 155, getRowPos(1), 150, 20, getSuffix(OLD_SNEAKING_ANIMATION, "Sneaking Animation")));
        buttonList.add(new AbstractButton(1, getCenter() + 5, getRowPos(1), 150, 20, getSuffix(OLD_HEALTH_FLASHING, "Health Flashing")));
        buttonList.add(new AbstractButton(2, getCenter() - 155, getRowPos(2), 150, 20, getSuffix(OLD_PLAYER_DAMAGE_FLASH, "Player Damage Flash")));
        buttonList.add(new AbstractButton(3, getCenter() + 5, getRowPos(2), 150, 20, getSuffix(OLD_BLOCKHITTING_ANIMATION, "Block Hitting Animation")));
        buttonList.add(new AbstractButton(4, getCenter() - 155, getRowPos(3), 150, 20, getSuffix(OLD_ENCHANTMENT_GLINT, "Enchantment Glint")));
        buttonList.add(new AbstractButton(5, getCenter() + 5, getRowPos(3), 150, 20, getSuffix(OLD_BLOCKING_POSITION, "Blocking Position")));
        buttonList.add(new AbstractButton(6, getCenter() - 155, getRowPos(4), 150, 20, getSuffix(OLD_ROD_POSITION, "Rod Position")));
        buttonList.add(new AbstractButton(7, getCenter() + 5, getRowPos(4), 150, 20, getSuffix(OLD_BOW_POSITION, "Bow Position")));
        buttonList.add(new AbstractButton(8, getCenter() - 155, getRowPos(5), 150, 20, getSuffix(OLD_BOW_SCALE, "Bow Scale")));
        buttonList.add(new AbstractButton(9, getCenter() + 5, getRowPos(5), 150, 20, getSuffix(OLD_ROD_SCALE, "Rod Scale")));
        buttonList.add(new AbstractButton(10, getCenter() - 155, getRowPos(6), 150, 20, getSuffix(HIT_GROUND_WHILE_EATING, "Swing While Eating")));
        buttonList.add(new AbstractButton(11, getCenter() + 5, getRowPos(6), 150, 20, getSuffix(HIT_GROUND_WHILE_AIMING, "Swing While Aiming")));
        buttonList.add(new AbstractButton(12, getCenter() - 155, getRowPos(7), 150, 20, getSuffix(OLD_TAB_LIST, "Tab List")));
        buttonList.add(new AbstractButton(13, getCenter() + 5, getRowPos(7), 150, 20, getSuffix(OLD_DEBUG_MENU, "Debug Menu")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawCenteredString(fontRendererObj, "Old Animations", getCenter(), getRowPos(0), 16777215);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                OLD_SNEAKING_ANIMATION = !OLD_SNEAKING_ANIMATION;
                button.displayString = getSuffix(OLD_SNEAKING_ANIMATION, "Sneaking Animation");
                break;
            case 1:
                OLD_HEALTH_FLASHING= !OLD_HEALTH_FLASHING;
                button.displayString = getSuffix(OLD_HEALTH_FLASHING, "Health Flashing");
                break;

            case 2:
                OLD_PLAYER_DAMAGE_FLASH = !OLD_PLAYER_DAMAGE_FLASH;
                button.displayString = getSuffix(OLD_PLAYER_DAMAGE_FLASH, "Player Damage Flash");
                break;
            case 3:
                OLD_BLOCKHITTING_ANIMATION= !OLD_BLOCKHITTING_ANIMATION;
                button.displayString = getSuffix(OLD_BLOCKHITTING_ANIMATION, "Block Hitting Animation");
                break;

            case 4:
                OLD_ENCHANTMENT_GLINT = !OLD_ENCHANTMENT_GLINT;
                button.displayString = getSuffix(OLD_ENCHANTMENT_GLINT, "Enchantment Glint");
                break;
            case 5:
                OLD_BLOCKING_POSITION= !OLD_BLOCKING_POSITION;
                button.displayString = getSuffix(OLD_BLOCKING_POSITION, "Blocking Position");
                break;

            case 6:
                OLD_ROD_POSITION = !OLD_ROD_POSITION;
                button.displayString = getSuffix(OLD_ROD_POSITION, "Rod Position");
                break;
            case 7:
                OLD_BOW_POSITION= !OLD_BOW_POSITION;
                button.displayString = getSuffix(OLD_BOW_POSITION, "Bow Position");
                break;

            case 8:
                OLD_BOW_SCALE = !OLD_BOW_SCALE;
                button.displayString = getSuffix(OLD_BOW_SCALE, "Bow Scale");
                break;
            case 9:
                OLD_ROD_SCALE= !OLD_ROD_SCALE;
                button.displayString = getSuffix(OLD_ROD_SCALE, "Rod Scale");
                break;

            case 10:
                HIT_GROUND_WHILE_EATING = !HIT_GROUND_WHILE_EATING;
                button.displayString = getSuffix(HIT_GROUND_WHILE_EATING, "Swing While Eating");
                break;
            case 11:
                HIT_GROUND_WHILE_AIMING= !HIT_GROUND_WHILE_AIMING;
                button.displayString = getSuffix(HIT_GROUND_WHILE_AIMING, "Swing While Aiming");
                break;

            case 12:
                OLD_TAB_LIST = !OLD_TAB_LIST;
                button.displayString = getSuffix(OLD_TAB_LIST, "Tab List");
                break;
            case 13:
                OLD_DEBUG_MENU= !OLD_DEBUG_MENU;
                button.displayString = getSuffix(OLD_DEBUG_MENU, "Debug Menu");
                break;
        }
    }

    @Override
    protected String getButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 0:
                return "Sneaking pre/post 1.8.x has a smooth animation to it, while in 1.8.x, that animation was removed and is now instant.";
            case 1:
                return "The Health bar in 1.8.x flashes with a white tint.";

            case 2:
                return "Armor used to flash when hitting a player, giving you a clearer indication of whether or not you hit them.";
            case 3:
                return "Block Hitting's animation was changed in 1.8.x, making people think it wasn't possible anymore.";

            case 4:
                return "honestly who fucking knows what this one is lol";
            case 5:
                return "Blocking used to be more rotated towards the face and not outwards.";

            case 6:
                return "Rods used to be positioned a bit more downwards than they are now.";
            case 7:
                return "Bows used to be positioned a bit more downwards than they are now.";

            case 8:
                return "Bows used to be scaled down more than they are now.";
            case 9:
                return "Rods used to be scaled down more than they are now.";

            case 10:
                return "Eating while punching the ground was removed in 1.8.x._p_cThis is purely cosmetic.";
            case 11:
                return "Aiming while punching the ground was removed in 1.8.x._p_cThis is purely cosmetic.";

            case 12:
                return "The tab menu previews heads in 1.8.x.";
            case 13:
                return "The debug menu was updated to give more information for technical users, but displays too much for the average user..";

            default:
                return null;
        }
    }

    @Override
    public void onGuiClosed() {
        OldAnimations.instance.saveConfig();
    }
}
