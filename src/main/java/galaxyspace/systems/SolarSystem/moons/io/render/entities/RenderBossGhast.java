package galaxyspace.systems.SolarSystem.moons.io.render.entities;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderBossGhast extends RenderLiving
{
	private static final ResourceLocation evolvedBossTextures = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/bossGhast.png");
	private int field_77068_a;

	public RenderBossGhast()
	{
		super(new ModelGhast(), 1.0F);
		//this.field_77068_a = ((ModelBlaze)this.mainModel).func_78104_a();
	}
	
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
        GL11.glScalef(4.0F, 4.0F, 4.0F);
    }
  /*  
	public void renderBossBlaze(EntityBossBlaze par1EntityEvolvedBlaze, double par2, double par4, double par6, float par8, float par9)
	{
		final int i = ((ModelBlaze)this.mainModel).func_78104_a();

		if (i != this.field_77068_a)
		{
			this.field_77068_a = i;
			this.mainModel = new ModelBlaze();
		}
		super.doRender(par1EntityEvolvedBlaze, par2, par4, par6, par8, par9);
	}
	*/

/*
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderBossBlaze((EntityBossBlaze)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderBossBlaze((EntityBossBlaze)par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
*/
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return evolvedBossTextures;
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		//this.renderBossBlaze((EntityBossBlaze)par1Entity, par2, par4, par6, par8, par9);
		BossStatus.setBossStatus((IBossDisplayData) par1Entity, false);
        super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}
	
    @Override
    protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
    {
        return super.getColorMultiplier(par1EntityLivingBase, par2, par3);
    }

    @Override
    protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return -1;
    }
}