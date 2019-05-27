package net.asbyth.oldanimations.animations;

import net.asbyth.oldanimations.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HEALTH;

public class HealthFlashingAnimation extends Gui {

    private Random random = new Random();
    private long healthUpdateCounter;
    private int playerHealth;
    private int lastPlayerHealth;
    private float lastSystemTime;

    @SubscribeEvent // mixins didnt want to work, so i used an event instead
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.type == HEALTH) {
            event.setCanceled(true);

            renderHealthFlashing(event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
        }
    }

    private void renderHealthFlashing(int width, int height) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(icons);

        GlStateManager.enableBlend();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = (EntityPlayer) mc.getRenderViewEntity();

        int health = MathHelper.ceiling_float_int(player.getHealth());
        int updateCounter = mc.ingameGUI.getUpdateCounter();

        boolean playerTakingDamage = healthUpdateCounter > updateCounter && (healthUpdateCounter - updateCounter) / 3L % 2L == 1L;

        if (health < playerHealth && player.hurtResistantTime > 0) {
            lastSystemTime = Minecraft.getSystemTime();
            healthUpdateCounter = updateCounter + 20;
        } else if (health > playerHealth && player.hurtResistantTime > 0) {
            lastSystemTime = Minecraft.getSystemTime();
            healthUpdateCounter = updateCounter + 10;
        }

        if (Minecraft.getSystemTime() - lastSystemTime > 1000.0f) {
            playerHealth = health;
            lastPlayerHealth = health;
            lastSystemTime = Minecraft.getSystemTime();
        }

        playerHealth = health;
        int lastHealth = lastPlayerHealth;

        IAttributeInstance attributeInstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        float healthMax = (float) attributeInstance.getAttributeValue();
        float absorption = player.getAbsorptionAmount();
        int healthRows = MathHelper.ceiling_float_int((healthMax + absorption) / 2.0f / 10.0f);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);
        random.setSeed(updateCounter * 312871);

        int left = width / 2 - 91;
        int topHeight = height - GuiIngameForge.left_height;
        GuiIngameForge.left_height += healthRows * rowHeight;

        if (rowHeight != 10) {
            GuiIngameForge.left_height += 10 - rowHeight;
        }

        int regen = -1;

        if (player.isPotionActive(Potion.regeneration)) {
            regen = updateCounter % 25;
        }

        int top = 9 * (mc.theWorld.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
        int background = playerTakingDamage ? 25 : 16;
        int margin = 16;

        if (player.isPotionActive(Potion.poison)) {
            margin += 36;
        } else if (player.isPotionActive(Potion.wither)) {
            margin += 72;
        }

        float absorptionRemaining = absorption;

        for (int i = MathHelper.ceiling_float_int((healthMax + absorption) / 2.0f) - 1; i >= 0; --i) {
            int row = MathHelper.ceiling_float_int((i + 1) / 10.0f) - 1;
            int x = left + i % 10 * 8;
            int y = topHeight - row * rowHeight;

            if (health <= 4) {
                y += random.nextInt(2);
            }

            if (i == regen) {
                y -= 2;
            }

            drawTexturedModalRect(x, y, background, top, 9, 9);

            if (playerTakingDamage && !Settings.OLD_PLAYER_DAMAGE_FLASH) {
                if (i * 2 + 1 < lastHealth) {
                    drawTexturedModalRect(x, y, margin + 54, top, 9, 9);
                } else if (i * 2 + 1 == lastHealth) {
                    drawTexturedModalRect(x, y, margin + 63, top, 9, 9);
                }
            }

            if (absorptionRemaining > 0.0f) {
                if (absorptionRemaining == absorption && absorption % 2.0f == 1.0f) {
                    drawTexturedModalRect(x, y, margin + 153, top, 9, 9);
                } else {
                    drawTexturedModalRect(x, y, margin + 144, top, 9, 9);
                }

                absorptionRemaining -= 2.0f;
            } else if (i * 2 + 1 < health) {
                drawTexturedModalRect(x, y, margin + 36, top, 9, 9);
            } else if (i * 2 + 1 == health) {
                drawTexturedModalRect(x, y, margin + 45, top, 9, 9);
            }
        }

        GlStateManager.disableBlend();
    }
}
