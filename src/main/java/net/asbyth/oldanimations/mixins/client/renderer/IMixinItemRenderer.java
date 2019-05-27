package net.asbyth.oldanimations.mixins.client.renderer;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemRenderer.class)
public interface IMixinItemRenderer {
    @Invoker void callRotateArroundXAndY(float angle, float p_178101_2_);
    @Invoker void callSetLightMapFromPlayer(AbstractClientPlayer clientPlayer);
    @Invoker void callRotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks);
    @Invoker void callRenderItemMap(AbstractClientPlayer clientPlayer, float p_178097_2_, float p_178097_3_, float p_178097_4_);
    @Invoker void callPerformDrinking(AbstractClientPlayer clientPlayer, float p_178104_2_);
    @Invoker void callDoBlockTransformations();
    @Invoker void callDoBowTransformations(float p_178098_1_, AbstractClientPlayer clientPlayer);
    @Invoker void callDoItemUsedTransformations(float p_178105_1_);
    @Invoker void callRenderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform);
    @Invoker void callRenderPlayerArm(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_);
}
