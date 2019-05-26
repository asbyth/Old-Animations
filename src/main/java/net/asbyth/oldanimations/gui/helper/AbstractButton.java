package net.asbyth.oldanimations.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class AbstractButton extends GuiButton {

    private double hoverFade = 0;
    private long prevDeltaTime;

    public AbstractButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (prevDeltaTime == 0) {
            prevDeltaTime = System.currentTimeMillis();
        }

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2f;
        hoverFade = hovered ? Math.min(100, hoverFade + hoverInc) : Math.max(0, hoverFade - hoverInc);

        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, new Color(0, 0, 0, (int) (100 - (hoverFade / 2))).getRGB());
        mouseDragged(mc, mouseX, mouseY);

        int textColor = enabled ? 255 : 180;

        drawCenteredString(mc.fontRendererObj, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, new Color(textColor, textColor, textColor, 255).getRGB());

        prevDeltaTime = System.currentTimeMillis();
    }
}
