package galaxyspace.systems.SolarSystem.moons.enceladus.tile;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.moons.enceladus.models.ModelCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityBlockCrystall extends TileEntitySpecialRenderer{

        
        //The model of your block
        //private final ModelCrystall model;
        private final ModelCrystal model;
        
        public TileEntityBlockCrystall() {
                this.model = new ModelCrystal();
        }

        
        @Override
        public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1.8F, (float) z + 0.5F);
                ResourceLocation textures = (new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/crystal.png")); 
                Minecraft.getMinecraft().renderEngine.bindTexture(textures);
                GL11.glScalef(1.2F, 1.2F, 1.2F);
                
                if(te.getWorldObj().getBlock(te.xCoord, te.yCoord + 1, te.zCoord).isNormalCube()) {
                	GL11.glTranslatef(0.0F, -2.15F, 0.0F);                 
                	GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
                }
                else GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                	
                this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

                GL11.glPopMatrix();
        }
}

