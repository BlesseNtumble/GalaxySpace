package galaxyspace.systems.SolarSystem.planets.ceres.renderer.entities;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.ceres.entities.EntityBossBlaze;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBossBlaze extends RenderLiving<EntityBossBlaze>
{
	private static final ResourceLocation evolvedBossTextures = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/boss_blaze.png");
	private int field_77068_a;

	public RenderBossBlaze(RenderManager renderManagerIn)
	{
		super(renderManagerIn, new ModelBlaze(), 4.0F);
	}
	
    @Override
    protected void preRenderCallback(EntityBossBlaze entity, float partialTicks)
    {
        GL11.glScalef(4.0F, 4.0F, 4.0F);
        GlStateManager.rotate((float) (Math.pow(entity.deathTicks, 2) / 5.0F + (Math.pow(entity.deathTicks, 2) / 5.0F - Math.pow(entity.deathTicks - 1, 2) / 5.0F) * partialTicks), 0.0F, 1.0F, 0.0F);
        
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityBossBlaze entity) {
		return evolvedBossTextures;
	}

}