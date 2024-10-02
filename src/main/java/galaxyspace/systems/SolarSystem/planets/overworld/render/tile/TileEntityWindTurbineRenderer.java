package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.models.ModelWindTurbine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindTurbine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityWindTurbineRenderer extends TileEntitySpecialRenderer
{
    //private static final ResourceLocation solarPanelGybTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/solarPanelGybrid.png");
	private static final ResourceLocation windTurbineTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/windTurbine.png");
	ModelWindTurbine model = new ModelWindTurbine();

    
    public TileEntityWindTurbineRenderer()
    {
    }
    
    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        TileEntityWindTurbine wind = (TileEntityWindTurbine) var1;

        this.bindTexture(this.windTurbineTexture);
        
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.49F, 3.3F, 0.48F);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F, 0, 0, 1);
        GL11.glRotatef(90 * wind.getBlockMetadata(), 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.87F, 0.87F, 0.87F);
        this.model.renderPole(0.0625F);
        wind.angle = (wind.angle+((wind.yCoord+4)/500F)* (wind.getWindBoost() * 4.0F)) % 360;
        
        GL11.glScalef(0.87F, 0.87F, 0.87F);
        GL11.glRotatef(wind.angle, 0F, 0F, 1.0F);
        this.model.renderFlares(0.0625F);
        GL11.glPopMatrix();

    }
}
