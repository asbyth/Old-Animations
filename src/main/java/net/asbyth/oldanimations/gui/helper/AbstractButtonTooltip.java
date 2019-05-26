package net.asbyth.oldanimations.gui.helper;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class AbstractButtonTooltip extends GuiScreen {

    private static final int lineHeight = 11;
    private long mouseOverTime = 0;
    private long prevSystemTime = -1;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawTooltipScreen(mouseX, mouseY);
    }

    private void drawTooltipScreen(int mouseX, int mouseY) {
        renderTooltipButtonEffect();

        int mousedOverButtonID = -1;

        for (GuiButton guiButton : buttonList) {
            AbstractButton button = (AbstractButton) guiButton;

            if (isButtonHoveredOver(mouseX, mouseY, button)) {
                mousedOverButtonID = button.id;

                if (getButtonTooltip(mousedOverButtonID) != null) {
                    renderTooltipButtonMouseOverEffect(button);
                }

                break;
            }
        }

        if (mousedOverButtonID > -1) {
            long systemTime = System.currentTimeMillis();

            if (prevSystemTime > 0) {
                mouseOverTime += systemTime - prevSystemTime;
            }

            prevSystemTime = systemTime;
        } else {
            mouseOverTime = 0;
        }

        long tooltipDelay = 900;
        if (mouseOverTime > tooltipDelay) {
            String tooltip = getButtonTooltip(mousedOverButtonID);

            if (tooltip != null) {
                renderTooltip(mouseX, mouseY, tooltip);
            }
        }
    }

    private void renderTooltip(int x, int y, String tooltip) {
        String[] tooltipArray = parseTooltipArrayFromString(tooltip);

        int tooltipWidth = getTooltipWidth(tooltipArray);
        int tooltipHeight = getTooltipHeight(tooltipArray);

        int tooltipXOffset = 0;
        int tooltipX = x + tooltipXOffset;

        int tooltipYOffset = 10;
        int tooltipY = y + tooltipYOffset;

        if (tooltipX > width - tooltipWidth - 7) {
            tooltipX = width - tooltipWidth - 7;
        }

        if (tooltipY > height - tooltipHeight - 8) {
            tooltipY = height - tooltipHeight - 8;
        }

        int innerAlpha = -0xFEFFFF0;
        drawGradientRect(tooltipX, tooltipY - 1, tooltipX + tooltipWidth + 6, tooltipY, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX, tooltipY + tooltipHeight + 6, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 7, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX, tooltipY, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX - 1, tooltipY, tooltipX, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX + tooltipWidth + 6, tooltipY, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);

        int outerAlpha = 0x505000FF;
        int outerAlpha2 = (outerAlpha & 0xFEFEFE) >> 1 | outerAlpha & -0x1000000;
        drawGradientRect(tooltipX, tooltipY + 1, tooltipX + 1, tooltipY + tooltipHeight + 6 - 1, outerAlpha, outerAlpha2); // left
        drawGradientRect(tooltipX + tooltipWidth + 5, tooltipY + 1, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 6 - 1, outerAlpha, outerAlpha2); // right
        drawGradientRect(tooltipX, tooltipY, tooltipX + tooltipWidth + 6, tooltipY + 1, outerAlpha, outerAlpha); // top
        drawGradientRect(tooltipX, tooltipY + tooltipHeight + 5, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6, outerAlpha2, outerAlpha2); // bottom

        int lineCount = 0;

        for (String s : tooltipArray) {
            mc.fontRendererObj.drawStringWithShadow(s, tooltipX + 2, tooltipY + 2 + lineCount * lineHeight, 16777215);
            lineCount++;
        }
    }

    private int getTooltipHeight(String[] tooltipArray) {
        int tooltipHeight = mc.fontRendererObj.FONT_HEIGHT - 2;

        if (tooltipArray.length > 1) {
            tooltipHeight += (tooltipArray.length - 1) * lineHeight;
        }

        return tooltipHeight;
    }

    private int getTooltipWidth(String[] tooltipArray) {
        int longestWidth = 0;

        for (String s : tooltipArray) {
            int width = mc.fontRendererObj.getStringWidth(s);

            if (width > longestWidth) {
                longestWidth = width;
            }
        }

        return longestWidth;
    }

    private String[] parseTooltipArrayFromString(String tooltip) {
        tooltip = decodeStringCodes(tooltip);

        String[] tooltipSections = tooltip.split("_p");
        ArrayList<String> tooltips = new ArrayList<>();

        for (String section : tooltipSections) {
            StringBuilder selectedTooltip = new StringBuilder();
            String[] tooltipWords = section.split(" ");

            for (String tooltipWord : tooltipWords) {
                int lineWidthWithNextWord = mc.fontRendererObj.getStringWidth(selectedTooltip + tooltipWord);

                if (lineWidthWithNextWord > 150) {
                    tooltips.add(selectedTooltip.toString().trim());
                    selectedTooltip = new StringBuilder(tooltipWord + " ");
                } else {
                    selectedTooltip.append(tooltipWord).append(" ");
                }
            }

            tooltips.add(selectedTooltip.toString().trim());
        }

        String[] tooltipArray = new String[tooltips.size()];
        tooltips.toArray(tooltipArray);

        return tooltipArray;
    }

    private String decodeStringCodes(String s) {
        return s
                .replace("_0", FontCodes.BLACK)
                .replace("_1", FontCodes.DARK_BLUE)
                .replace("_2", FontCodes.DARK_GREEN)
                .replace("_3", FontCodes.DARK_AQUA)
                .replace("_4", FontCodes.DARK_RED)
                .replace("_5", FontCodes.DARK_PURPLE)
                .replace("_6", FontCodes.GOLD)
                .replace("_7", FontCodes.GRAY)
                .replace("_8", FontCodes.DARK_GREY)
                .replace("_9", FontCodes.BLUE)
                .replace("_a", FontCodes.GREEN)
                .replace("_b", FontCodes.AQUA)
                .replace("_c", FontCodes.RED)
                .replace("_d", FontCodes.LIGHT_PURPLE)
                .replace("_e", FontCodes.YELLOW)
                .replace("_f", FontCodes.WHITE)
                .replace("_k", FontCodes.OBFUSCATED)
                .replace("_l", FontCodes.BOLD)
                .replace("_m", FontCodes.STRIKETHROUGH)
                .replace("_n", FontCodes.UNDERLINE)
                .replace("_o", FontCodes.ITALICS)
                .replace("_r", FontCodes.RESET);
    }

    private void renderTooltipButtonMouseOverEffect(AbstractButton button) {
        mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW + "?", button.xPosition + button.getButtonWidth() - 5, button.yPosition, 16777215);
    }

    private boolean isButtonHoveredOver(int mouseX, int mouseY, AbstractButton button) {
        if (mouseX >= button.xPosition && mouseX <= button.xPosition + button.getButtonWidth() && mouseY >= button.yPosition) {
            int buttonHeight = getButtonHeight(button, "height", "field_146121_g");

            return mouseY <= button.yPosition + buttonHeight;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private static <T, E> T getButtonHeight(E instance, String... fields) {
        Field field = null;

        for (String fieldName : fields) {
            try {
                field = GuiButton.class.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
            }

            if (field != null) {
                break;
            }
        }

        if (field != null) {
            field.setAccessible(true);

            T fieldT = null;
            try {
                fieldT = (T) field.get(instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return fieldT;
        }

        return null;
    }

    private void renderTooltipButtonEffect() {
        for (GuiButton guiButton : buttonList) {
            AbstractButton button = (AbstractButton) guiButton;

            if (getButtonTooltip(button.id) != null) {
                mc.fontRendererObj.drawStringWithShadow("?", button.xPosition + button.getButtonWidth() - 5, button.yPosition, 16777215);
            }
        }
    }

    protected abstract String getButtonTooltip(int buttonId);

    class FontCodes {
        static final String BLACK = "\2470";
        static final String DARK_BLUE = "\2471";
        static final String DARK_GREEN = "\2472";
        static final String DARK_AQUA = "\2473";
        static final String DARK_RED = "\2474";
        static final String DARK_PURPLE = "\2475";
        static final String GOLD = "\2476";
        static final String GRAY = "\2477";
        static final String DARK_GREY = "\2478";
        static final String BLUE = "\2479";
        static final String GREEN = "\247a";
        static final String AQUA = "\247b";
        static final String RED = "\247c";
        static final String LIGHT_PURPLE = "\247d";
        static final String YELLOW = "\247e";
        static final String WHITE = "\247f";

        static final String OBFUSCATED = "\247k";
        static final String BOLD = "\247l";
        static final String STRIKETHROUGH = "\247m";
        static final String UNDERLINE = "\247n";
        static final String ITALICS = "\247o";

        static final String RESET = "\247r";
    }
}
