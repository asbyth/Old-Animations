package net.asbyth.oldanimations.mixins.client.renderer.entity;

import net.asbyth.oldanimations.config.Settings;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> {

    @Shadow protected List<LayerRenderer<T>> layerRenderers;

    /**
     * @author asbyth
     * @reason old player flashing when taking damage
     */
    @SuppressWarnings("unchecked")
    @Overwrite
    protected void renderLayers(T entitylivingbaseIn, float f1, float f2, float partialTicks, float f3, float f4, float f5, float f6) {
        for (LayerRenderer<T> layerRenderer : layerRenderers) {
            boolean combineTextures = layerRenderer.shouldCombineTextures();

            if (Settings.OLD_PLAYER_DAMAGE_FLASH) {
                combineTextures = true;
            }

            boolean brightnessSet = ((IMixinRendererLivingEntity<T>) this).callSetBrightness(entitylivingbaseIn, partialTicks, combineTextures);
            layerRenderer.doRenderLayer(entitylivingbaseIn, f1, f2, partialTicks, f3, f4, f5, f6); // when testing, i forgot to add this. kind of important yeah?

            if (brightnessSet) {
                ((IMixinRendererLivingEntity) this).callUnsetBrightness();
            }
        }
    }
}
