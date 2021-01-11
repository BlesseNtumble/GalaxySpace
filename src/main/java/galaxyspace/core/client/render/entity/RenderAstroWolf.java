package galaxyspace.core.client.render.entity;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.models.entity.ModelAstroWolf;
import galaxyspace.core.client.render.entity.layers.LayerAstroWolfCollar;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderAstroWolf extends RenderLiving<EntityAstroWolf>
{
    private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/astro_wolf.png");
    private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/astro_wolf_tame.png");
    private static final ResourceLocation ANRGY_WOLF_TEXTURES = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/astro_wolf_angry.png");
    private static final ResourceLocation WOLF_THERMAL = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/astro_wolf_thermal.png");
    
    public RenderAstroWolf(RenderManager p_i47187_1_)
    {
        super(p_i47187_1_, new ModelAstroWolf(), 0.5F);
        this.addLayer(new LayerAstroWolfCollar(this));
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityAstroWolf livingBase, float partialTicks)
    {
        return livingBase.getTailRotation();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityAstroWolf entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (entity.isWolfWet())
        {
            float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
            GlStateManager.color(f, f, f);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityAstroWolf entity)
    {
        if (entity.isTamed())
        {
            return entity.wolfInventory.getStackInSlot(1).isEmpty() ? TAMED_WOLF_TEXTURES : WOLF_THERMAL;
        }
        else
        {
            return entity.isAngry() ? ANRGY_WOLF_TEXTURES : WOLF_TEXTURES;
        }
    }
}
