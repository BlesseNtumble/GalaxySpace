package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.models.ModelWindTurbine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityWindGeneratorRenderer extends TileEntitySpecialRenderer<TileEntityWindGenerator>
{
    //private static final ResourceLocation solarPanelGybTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/solarPanelGybrid.png");
	private static final ResourceLocation windTurbineTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/wind_turbine.png");
    public ModelWindTurbine model = new ModelWindTurbine();

    @Override
    public void render(TileEntityWindGenerator wind, double par2, double par4, double par6, float partialTickTime, int par9, float alpha)
    {

    	//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);
    	
        /*if (var1.getBlockMetadata() >= BlockSolarPanel.ULTRA_METADATA)
        {
            this.bindTexture(TileEntityWindTurbine.solarPanelUltTexture);
        }
        else
        {*/
            this.bindTexture(this.windTurbineTexture);
        //}

        GL11.glPushMatrix();
        GlStateManager.disableRescaleNormal();
        
        if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		} else {
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);

        GL11.glTranslatef(0.49F, 3.3F, 0.48F);
        
        //GL11.glTranslatef(0.0F, 1.5F, 0.0F);

        GL11.glRotatef(180.0F, 0, 0, 1);
        GL11.glRotatef(wind.getBlockMetadata() * 90 + 90, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.87F, 0.87F, 0.87F);
        this.model.renderPole(0.0625F);
        //GL11.glScalef(2.0F, 2.0F, 2.0F);
        //this.model.renderPanel();
        
        wind.angle = Math.min((wind.angle+((wind.getPos().getY())/100F) * (wind.getWindBoost() * 15.0F)), wind.angle + 15F) % 360;
        GL11.glTranslatef(0.0F, 0.06F, 0.0F);
        GL11.glRotatef(wind.angle, 0F, 0F, 1.0F);
        
        if(wind.getFanType() >= 0) {
	        float r = 1.0F, g = 1.0F,b = 1.0F;
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
	        GL11.glTranslatef(0.0F, -0.1F, 0.0F);
        }
        
        GlStateManager.disableRescaleNormal();
        GL11.glPopMatrix();

        RenderHelper.enableStandardItemLighting();
    }
}
