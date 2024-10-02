package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockSolarWindPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.models.ModelSolarWindPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarWind;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;


public class TileEntitySolarWindPanelRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation solarPanelTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/solarWindPanelBasic.png");
    private static final ResourceLocation solarPanelAdvTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/solarWindPanelBasic.png");
    public ModelSolarWindPanel modelw = new ModelSolarWindPanel();

    @Override
    public void renderTileEntityAt(TileEntity var1, double par2, double par4, double par6, float var8)
    {
        TileEntitySolarWind panel = (TileEntitySolarWind) var1;

        if (var1.getBlockMetadata() >= BlockSolarWindPanel.BASIC_METADATA)
        {
            this.bindTexture(TileEntitySolarWindPanelRenderer.solarPanelTexture);
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);

        GL11.glTranslatef(0.5F, 1.0F, 0.5F);
        this.modelw.renderPole();
        GL11.glTranslatef(0.0F, 1.5F, 0.0F);

        GL11.glRotatef(-180.0F, 0, 0, 1);
        GL11.glRotatef(90.0F, 0, 1, 0);

        float celestialAngle = (panel.getWorldObj().getCelestialAngle(1.0F) - 0.784690560F) * 360.0F;
        float celestialAngle2 = panel.getWorldObj().getCelestialAngle(1.0F) * 360.0F;

        GL11.glRotatef(panel.currentAngle - (celestialAngle - celestialAngle2), 1.0F, 0.0F, 0.0F);

        this.modelw.renderPanel();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
