package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.models.ModelSolarWindPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindSolarPanel;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntitySolarWindPanelRenderer extends TileEntitySpecialRenderer<TileEntityWindSolarPanel>{

	public ModelSolarWindPanel model = new ModelSolarWindPanel();
	private static final ResourceLocation solarwindpanelTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/solarwind_panel.png");
    
	
	@Override
	public void render(TileEntityWindSolarPanel panel, double par2, double par4, double par6, float partialTickTime, int par9, float alpha) {

		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);
		this.bindTexture(this.solarwindpanelTexture);
		
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);

		GL11.glTranslatef(0.5F, 1.0F, 0.5F);
		this.model.renderPole();
		GL11.glTranslatef(0.0F, 1.5F, 0.0F);

		GL11.glRotatef(-180.0F, 0, 0, 1);
		GL11.glRotatef(90.0F, 0, 1, 0);

		float celestialAngle = (panel.getWorld().getCelestialAngle(1.0F) - 0.784690560F) * 360.0F;
		float celestialAngle2 = panel.getWorld().getCelestialAngle(1.0F) * 360.0F;

		//GL11.glRotatef(panel.currentAngle - (celestialAngle - celestialAngle2), 1.0F, 0.0F, 0.0F);

		this.model.renderPanel();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);		
	}
}
