package galaxyspace.systems.SolarSystem.moons.europa.render.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.moons.europa.entities.EntityEvolvedColdBlaze;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderEvolvedColdBlaze extends RenderLiving
{
	private static final ResourceLocation evolvedcoldBlazeTextures = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/evolvedcoldBlaze.png");
	private int field_77068_a;

	public RenderEvolvedColdBlaze()
	{
		super(new ModelBlaze(), 1.0F);
		this.field_77068_a = ((ModelBlaze)this.mainModel).func_78104_a();
	}
	/*
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
        GL11.glScalef(4.0F, 4.0F, 4.0F);
    }
    */
	public void renderEvolvedColdBlaze(EntityEvolvedColdBlaze par1EntityEvolvedBlaze, double par2, double par4, double par6, float par8, float par9)
	{
		final int i = ((ModelBlaze)this.mainModel).func_78104_a();

		if (i != this.field_77068_a)
		{
			this.field_77068_a = i;
			this.mainModel = new ModelBlaze();
		}
		super.doRender(par1EntityEvolvedBlaze, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getEvolvedColdBlazeTextures(EntityEvolvedColdBlaze par1EntityBlaze)
	{
		return evolvedcoldBlazeTextures;
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderEvolvedColdBlaze((EntityEvolvedColdBlaze)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderEvolvedColdBlaze((EntityEvolvedColdBlaze)par1EntityLivingBase, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEvolvedColdBlazeTextures((EntityEvolvedColdBlaze)par1Entity);
	}

}