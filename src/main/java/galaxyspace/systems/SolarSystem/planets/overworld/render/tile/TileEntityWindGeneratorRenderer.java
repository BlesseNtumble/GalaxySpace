package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import asmodeuscore.core.utils.Utils;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.models.ModelWindTurbine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;

public class TileEntityWindGeneratorRenderer extends TileEntitySpecialRenderer<TileEntityWindGenerator> {
	private static final ResourceLocation windTurbineTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX,
			"textures/model/wind_turbine.png");
	public ModelWindTurbine model = new ModelWindTurbine();

	private OBJModel.OBJBakedModel cube;
	private OBJModel.OBJBakedModel hexadecagon;

	private void updateModels() {
		if (cube == null) {
			try {
				IModel model = OBJLoaderGC.instance
						.loadModel(new ResourceLocation(GalaxySpace.MODID, "windmill_straight.obj"));
				Function<ResourceLocation, TextureAtlasSprite> spriteFunction = location -> Minecraft.getMinecraft()
						.getTextureMapBlocks().getAtlasSprite(location.toString());

				cube = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("cube"), false),
						DefaultVertexFormats.ITEM, spriteFunction);
				hexadecagon = (OBJModel.OBJBakedModel) model.bake(
						new OBJModel.OBJState(ImmutableList.of("hexadecagon"), false), DefaultVertexFormats.ITEM,
						spriteFunction);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void render(TileEntityWindGenerator wind, double x, double y, double z, float partialTickTime, int par9,
			float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.disableRescaleNormal();
		
		if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		} else {
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		wind.angle = Math.min((wind.angle + ((wind.getPos().getY()) / 500F) * (wind.getWindBoost() * 10.0F)),
				wind.angle + 15F) % 360;

		if(wind.isAdvanced())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.translate((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.48F);
			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			this.updateModels();
			
			GlStateManager.scale(2D, 1D, 2D);
			ClientUtil.drawBakedModel(hexadecagon);
			
			GlStateManager.translate((float)0.0F, (float) 2.0F, (float) 0.0F);
			ClientUtil.drawBakedModel(hexadecagon);
			
			if (wind.getFanType() >= 0) {
				GL11.glRotatef(wind.angle, 0F, 1F, 0F);
				float r = 1.0F, g = 1.0F, b = 1.0F;
				switch (wind.getFanType()) {
					case 0: {
						r = 0.6F;
						g = 0.6F;
						b = 0.65F;
						break;
					}
					case 1: {
						r = 0.3F;
						g = 0.3F;
						b = 0.4F;
						break;
					}
					case 2: {
						r = 1.0F;
						g = 1.0F;
						b = 1.0F;
						break;
					}
					case 3: {
						r = 0.15F;
						g = 0.15F;
						b = 0.15F;
						break;
					}
				}

				int i_r = (int)(255 * r);
				int i_g = (int)(255 * g);
				int i_b = (int)(255 * b);
				
				ClientUtil.drawBakedModelColored(cube, Utils.getIntColorWHC(i_r, i_g, i_b, 255));
			}
		} else {
			this.bindTexture(this.windTurbineTexture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glTranslatef(0.49F, 3.3F, 0.48F);
			GL11.glRotatef(180.0F, 0, 0, 1);
			GL11.glRotatef(wind.getBlockMetadata() * 90 + 90, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.87F, 0.87F, 0.87F);
			this.model.renderPole(0.0625F);
			GL11.glTranslatef(0.0F, 0.06F, 0.0F);
			GL11.glRotatef(wind.angle, 0F, 0F, 1.0F);
			
			if (wind.getFanType() >= 0) {
				float r = 1.0F, g = 1.0F, b = 1.0F;
				switch (wind.getFanType()) {
					case 0: {
						r = 0.6F;
						g = 0.6F;
						b = 0.65F;
						break;
					}
					case 1: {
						r = 0.3F;
						g = 0.3F;
						b = 0.4F;
						break;
					}
					case 2: {
						r = 1.0F;
						g = 1.0F;
						b = 1.0F;
						break;
					}
					case 3: {
						r = 0.15F;
						g = 0.15F;
						b = 0.15F;
						break;
					}
				}
				GL11.glColor4f(r, g, b, 1.0F);
				this.model.renderFlares(0.0625F);
			}
			
		}
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	/*
	 * @Override public void render(TileEntityWindGenerator wind, double par2,
	 * double par4, double par6, float partialTickTime, int par9, float alpha) {
	 * 
	 * //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F,
	 * 240F);
	 * 
	 * /*if (var1.getBlockMetadata() >= BlockSolarPanel.ULTRA_METADATA) {
	 * this.bindTexture(TileEntityWindTurbine.solarPanelUltTexture); } else {
	 * this.bindTexture(this.windTurbineTexture); //}
	 * 
	 * GL11.glPushMatrix(); GlStateManager.disableRescaleNormal();
	 * 
	 * if (Minecraft.isAmbientOcclusionEnabled()) {
	 * GlStateManager.shadeModel(GL11.GL_SMOOTH); } else {
	 * GlStateManager.shadeModel(GL11.GL_FLAT); }
	 * 
	 * GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); GL11.glTranslatef((float) par2,
	 * (float) par4, (float) par6);
	 * 
	 * GL11.glTranslatef(0.49F, 3.3F, 0.48F);
	 * 
	 * //GL11.glTranslatef(0.0F, 1.5F, 0.0F);
	 * 
	 * GL11.glRotatef(180.0F, 0, 0, 1); GL11.glRotatef(wind.getBlockMetadata() * 90
	 * + 90, 0.0F, 1.0F, 0.0F); GL11.glScalef(0.87F, 0.87F, 0.87F);
	 * this.model.renderPole(0.0625F); //GL11.glScalef(2.0F, 2.0F, 2.0F);
	 * //this.model.renderPanel();
	 * 
	 * wind.angle = Math.min((wind.angle+((wind.getPos().getY())/500F) *
	 * (wind.getWindBoost() * 15.0F)), wind.angle + 15F) % 360;
	 * 
	 * GL11.glTranslatef(0.0F, 0.06F, 0.0F); GL11.glRotatef(wind.angle, 0F, 0F,
	 * 1.0F);
	 * 
	 * if(wind.getFanType() >= 0) { float r = 1.0F, g = 1.0F,b = 1.0F; switch
	 * (wind.getFanType()) { case 0: { r = 0.6F; g = 0.6F; b = 0.65F;
	 * 
	 * break; } case 1: { r = 0.3F; g = 0.3F; b = 0.4F;
	 * 
	 * break; } case 2: { r = 1.0F; g = 1.0F; b = 1.0F;
	 * 
	 * break; } case 3: { r = 0.15F; g = 0.15F; b = 0.15F;
	 * 
	 * break; } }
	 * 
	 * GL11.glColor4f(r, g, b, 1.0F);
	 * 
	 * this.model.renderFlares(0.0625F); GL11.glTranslatef(0.0F, -0.1F, 0.0F); }
	 * 
	 * GlStateManager.disableRescaleNormal(); GL11.glPopMatrix();
	 * 
	 * RenderHelper.enableStandardItemLighting(); }
	 */
}
