package galaxyspace.systems.SolarSystem.moons.enceladus.tile;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.moons.enceladus.models.ModelCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class TileEntityBlockCrystall extends TileEntitySpecialRenderer{


        private final ModelCrystal model;
        private static final int WIDTH = 64;
        private static final int HEIGHT = 64;
        private final int[][] colors = new int[HEIGHT][WIDTH];
        public TileEntityBlockCrystall() {
                this.model = new ModelCrystal();
                try {
                        int[] imageData = TextureUtil.readImageData(
                                Minecraft.getMinecraft().getResourceManager(),
                                new ResourceLocation(GalaxySpace.ASSET_PREFIX,
                                        "textures/blocks/solarsystem/enceladus/crystalNoiseTexture.png")
                        );
                        if (imageData.length != WIDTH * HEIGHT) {
                                throw new IllegalStateException("Unexpected image size");
                        }

                        for (int y = 0; y < HEIGHT; y++) {
                                for (int x = 0; x < WIDTH; x++) {
                                        colors[y][x] = imageData[y * WIDTH + x];
                                }
                        }
                } catch (IOException e) {
                        colors[0][0] = -1;
                }
        }


        
        @Override
        public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1.8F, (float) z + 0.5F);
                ResourceLocation textures = (new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/crystal.png"));
                float r = (colors[Math.abs(te.xCoord%HEIGHT)][Math.abs(te.zCoord%WIDTH)] >>> 16) & 0xFF;
                float g = (colors[Math.abs(te.xCoord%HEIGHT)][Math.abs(te.zCoord%WIDTH)] >>> 8) & 0xFF;
                float b = (colors[Math.abs(te.xCoord%HEIGHT)][Math.abs(te.zCoord%WIDTH)]) & 0xFF;
                GL11.glColor3f(r/255.0F, g/255.0F, b/255.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(textures);
                GL11.glScalef(1.2F, 1.2F, 1.2F);
                
                switch(ForgeDirection.getOrientation(te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord))) {
                        case DOWN:
                                    GL11.glTranslatef(0.0F, -2.15F, 0.0F);
                                    GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
                                GL11.glTranslatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%0.40)-0.2F, (float) (Math.pow(g*te.yCoord/2.0F, Math.PI)%0.18), (float) (Math.pow(b*te.yCoord/2.0F, Math.PI)%0.40)-0.2F);
                                break;
                        case UP:
                                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                                GL11.glTranslatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%0.40)-0.2F, (float) (Math.pow(g*te.yCoord/2.0F, Math.PI)%0.18), (float) (Math.pow(b*te.yCoord/2.0F, Math.PI)%0.40)-0.2F);
                                break;
                        case NORTH:
                                GL11.glTranslatef(0F, -1.10F, -1.05F);
                                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                                GL11.glTranslatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%0.40)-0.2F, (float) (Math.pow(g*te.yCoord/2.0F, Math.PI)%0.18), (float) (Math.pow(b*te.yCoord/2.0F, Math.PI)%0.40)-0.2F);
                                break;
                        case SOUTH:
                                GL11.glTranslatef(0F, -1.10F, 1.05F);
                                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                                GL11.glTranslatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%0.40)-0.2F, (float) (Math.pow(g*te.yCoord/2.0F, Math.PI)%0.18), (float) (Math.pow(b*te.yCoord/2.0F, Math.PI)%0.18)-0.22F);

                                break;
                        case WEST:
                                GL11.glTranslatef(-1.05F, -1.10F, 0F);
                                GL11.glRotatef(-90F, 0.0F, 0.0F, 1F);
                                GL11.glTranslatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%0.40)-0.2F, (float) (Math.pow(g*te.yCoord/2.0F, Math.PI)%0.18), (float) (Math.pow(b*te.yCoord/2.0F, Math.PI)%0.40)-0.2F);

                                break;
                        case EAST:
                                GL11.glTranslatef(1.05F, -1.10F, 0F);
                                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                                GL11.glTranslatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%0.40)-0.2F, (float) (Math.pow(g*te.yCoord/2.0F, Math.PI)%0.18), (float) (Math.pow(b*te.yCoord/2.0F, Math.PI)%0.40)-0.2F);
                                break;
                }
                GL11.glRotatef((float) (Math.pow(r*te.yCoord/2.0F, Math.PI)%360), 0F, 1F, 0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_CULL_FACE);
                this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();

        }
}

