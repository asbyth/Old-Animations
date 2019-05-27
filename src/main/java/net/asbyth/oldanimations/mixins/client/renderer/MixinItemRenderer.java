package net.asbyth.oldanimations.mixins.client.renderer;

import net.asbyth.oldanimations.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.init.Items.filled_map;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow @Final private Minecraft mc;
    @Shadow private float prevEquippedProgress;
    @Shadow private float equippedProgress;
    @Shadow private ItemStack itemToRender;

    /**
     * @author asbyth
     * @reason transform positioning
     */
    @Overwrite
    private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    /**
     * @author asbyth
     * @reason block hitting, swing while eating / aiming
     */
    @Overwrite
    public void renderItemInFirstPerson(float partialTicks) {
        float equipProgress = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks);
        EntityPlayerSP player = mc.thePlayer;
        float swingProgress = player.getSwingProgress(partialTicks);
        float rotationPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        float rotationYaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;

        ((IMixinItemRenderer) this).callRotateArroundXAndY(rotationPitch, rotationYaw); // spelling :(
        ((IMixinItemRenderer) this).callSetLightMapFromPlayer(player);
        ((IMixinItemRenderer) this).callRotateWithPlayerRotations(player, partialTicks);

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        if (itemToRender != null) {
            if (itemToRender.getItem() == filled_map) {
                ((IMixinItemRenderer) this).callRenderItemMap(player, rotationPitch, equipProgress, swingProgress);
            } else if (player.getItemInUseCount() > 0) {
                EnumAction action = itemToRender.getItemUseAction();

                switch (action) {
                    case NONE: // todo: seperate none eat and drink
                    case EAT:
                    case DRINK:
                        transformFirstPersonItem(equipProgress, 0.0F);
                        break;

                    case BLOCK:
                        if (Settings.OLD_BLOCKHITTING_ANIMATION) {
                            transformFirstPersonItem(equipProgress, swingProgress);
                            ((IMixinItemRenderer) this).callDoBlockTransformations();
                            GlStateManager.scale(0.83f, 0.88f, 0.85f);
                            GlStateManager.translate(-0.3f, 0.1f, 0.0f);
                            break;
                        } else {
                            transformFirstPersonItem(equipProgress, 0.0F);
                            ((IMixinItemRenderer) this).callDoBlockTransformations();
                            break;
                        }

                    case BOW:
                        transformFirstPersonItem(equipProgress, 0.0F);
                        ((IMixinItemRenderer) this).callDoBowTransformations(partialTicks, player);
                        break;
                }
            } else {
                ((IMixinItemRenderer) this).callDoItemUsedTransformations(swingProgress);
                transformFirstPersonItem(equipProgress, swingProgress);
            }

            ((IMixinItemRenderer) this).callRenderItem(player, itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!player.isInvisible()) {
            ((IMixinItemRenderer) this).callRenderPlayerArm(player, equipProgress, swingProgress);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
}
