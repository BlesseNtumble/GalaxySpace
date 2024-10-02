package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarPanel;
import micdoodle8.mods.galacticraft.core.client.model.block.ModelSolarPanel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TileEntitySolarPanelRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation solarPanelGybTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/solarPanelGybrid.png");
	public ModelSolarPanel model = new ModelSolarPanel();
    public static final IModelCustom modelobj = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/solarpanel.obj"));
	
    @Override
    public void renderTileEntityAt(TileEntity var1, double par2, double par4, double par6, float var8)
    {
        TileEntitySolarPanel panel = (TileEntitySolarPanel) var1;


        this.bindTexture(TileEntitySolarPanelRenderer.solarPanelGybTexture);
       

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);

        GL11.glTranslatef(0.5F, 3.1F, 0.48F);
        this.modelobj.renderPart("base");

        float celestialAngle = (panel.getWorldObj().getCelestialAngle(1.0F) - 0.784690560F) * 360.0F;
        float celestialAngle2 = panel.getWorldObj().getCelestialAngle(1.0F) * 360.0F;

        GL11.glRotatef(panel.currentAngle - (celestialAngle - celestialAngle2), 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.9F, 0.9F, 0.9F);
        this.modelobj.renderPart("panels");
       
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
