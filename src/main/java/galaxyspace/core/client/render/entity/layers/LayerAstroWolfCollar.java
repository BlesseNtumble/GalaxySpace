package galaxyspace.core.client.render.entity.layers;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.entity.RenderAstroWolf;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerAstroWolfCollar implements LayerRenderer<EntityAstroWolf>
{
    private static final ResourceLocation WOLF_COLLAR = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/astro_wolf_collar.png");
    private final RenderAstroWolf wolfRenderer;

    public LayerAstroWolfCollar(RenderAstroWolf wolfRendererIn)
    {
        this.wolfRenderer = wolfRendererIn;
    }

    public void doRenderLayer(EntityAstroWolf entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible())
        {
            this.wolfRenderer.bindTexture(WOLF_COLLAR);
            float[] afloat = entitylivingbaseIn.getCollarColor().getColorComponentValues();
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.wolfRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
