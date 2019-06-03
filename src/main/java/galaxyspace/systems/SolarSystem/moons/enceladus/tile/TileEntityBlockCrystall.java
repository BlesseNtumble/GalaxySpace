package galaxyspace.systems.SolarSystem.moons.enceladus.tile;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.moons.enceladus.models.ModelCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityBlockCrystall extends TileEntitySpecialRenderer{

	private final ModelCrystal model;

	public TileEntityBlockCrystall() {
		this.model = new ModelCrystal();
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTickTime, int par9, float alpha) {
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 220F, 0F);
	    
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.8F, (float) z + 0.5F);
		ResourceLocation textures = (new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/enceladus_crystal.png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glScalef(1.2F, 1.2F, 1.2F);

		if (te.getWorld().getBlockState(te.getPos().up()).isNormalCube()) {
			GL11.glTranslatef(0.0F, -2.15F, 0.0F);
			GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
		} else
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		GL11.glRotatef(90F * te.getBlockMetadata(), 0.0F, 1.0F, 0.0F);
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}

