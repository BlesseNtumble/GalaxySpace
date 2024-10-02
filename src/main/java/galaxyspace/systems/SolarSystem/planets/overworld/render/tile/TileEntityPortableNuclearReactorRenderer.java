package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPortableNuclearReactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TileEntityPortableNuclearReactorRenderer extends TileEntitySpecialRenderer{

	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/NuclearReactor.obj") ); 
	private static ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/NuclearReactor.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
	{

		GL11.glPushMatrix();

		GL11.glTranslated(x + 0.5F, y, z + 0.5F);
		// GL11.glScalef(0.01F, 0.01F, 0.01F);
		// GL11.glRotatef(-90F, 0, 1, 0);
		bindTexture(texture);
		model.renderPart("Base");

		TileEntityPortableNuclearReactor tile = (TileEntityPortableNuclearReactor) tileEntity;

		if (tile.itemCookTime > 0) {
			GL11.glTranslated(0F, 1.05F, 0F);
			model.renderPart("Rod");

			Tessellator tess = Tessellator.instance;
			GL11.glColor4f(0.0F, 1.0F, 0.2F, 1.0F);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glPushMatrix();
			GL11.glTranslated(-0.06F, -0.73F, 0.2501F);
			tess.startDrawing(GL11.GL_TRIANGLE_STRIP);
			tess.addVertex(0, 0, 0);
			tess.addVertex(.13F, 0, 0);
			tess.addVertex(0, .36F, 0);
			tess.addVertex(.13F, .36F, 0);
			tess.draw();
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(-0.06F, -0.73F, -0.2501F);
			tess.startDrawing(GL11.GL_TRIANGLE_STRIP);
			tess.addVertex(0, 0, 0);
			tess.addVertex(.13F, 0, 0);
			tess.addVertex(0, .36F, 0);
			tess.addVertex(.13F, .36F, 0);
			tess.draw();
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(-0.2501F, -0.73F, -0.06F);
			tess.startDrawing(GL11.GL_TRIANGLE_STRIP);
			tess.addVertex(0, 0, 0);
			tess.addVertex(0, 0, .13F);
			tess.addVertex(0, .36F, 0);
			tess.addVertex(0, .36F, .13F);
			tess.draw();
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(0.2501F, -0.73F, -0.06F);
			tess.startDrawing(GL11.GL_TRIANGLE_STRIP);
			tess.addVertex(0, 0, 0);
			tess.addVertex(0, 0, .13F);
			tess.addVertex(0, .36F, 0);
			tess.addVertex(0, .36F, .13F);
			tess.draw();
			GL11.glPopMatrix();

			GL11.glEnable(GL11.GL_LIGHTING);
		}

		GL11.glPopMatrix();
	}
}
