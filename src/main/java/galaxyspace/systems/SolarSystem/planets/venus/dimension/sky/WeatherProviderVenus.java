package galaxyspace.systems.SolarSystem.planets.venus.dimension.sky;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;

public class WeatherProviderVenus extends IRenderHandler{
	
    private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");

    /** Rain X coords */
    float[] rainXCoords;
    /** Rain Y coords */
    float[] rainYCoords;
	private int rendererUpdateCount;
	private Random random;
	
	
	public WeatherProviderVenus() {
		this.random = new Random();
	}



	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		
		++this.rendererUpdateCount;
		// do normal weather rendering
		renderNormalWeather(partialTicks, mc);
     
	}



	private void renderNormalWeather(float partialTicks, Minecraft mc) {
		float rainStrength = mc.theWorld.getRainStrength(partialTicks);

        if (rainStrength > 0.0F)
        {
   	        mc.entityRenderer.enableLightmap((double)partialTicks);

            this.initializeRainCoords();

            EntityLivingBase entitylivingbase = mc.renderViewEntity;
            WorldClient worldclient = mc.theWorld;
            int k2 = MathHelper.floor_double(entitylivingbase.posX);
            int l2 = MathHelper.floor_double(entitylivingbase.posY);
            int i3 = MathHelper.floor_double(entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            double d0 = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)partialTicks;
            double d1 = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)partialTicks;
            double d2 = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)partialTicks;
            int k = MathHelper.floor_double(d1);
            byte range = 4;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 8;
            }

            byte b1 = -1;
            float f5 = (float)this.rendererUpdateCount + partialTicks;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 10;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            for (int l = i3 - range; l <= i3 + range; ++l)
            {
                for (int i1 = k2 - range; i1 <= k2 + range; ++i1)
                {
                    int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
                    float f6 = this.rainXCoords[j1] * 0.5F;
                    float f7 = this.rainYCoords[j1] * 0.5F;
                    BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(i1, l);

                    if (biomegenbase.canSpawnLightningBolt() || biomegenbase.getEnableSnow())
                    {
                        int yHeight = worldclient.getPrecipitationHeight(i1, l) + 9 - (int)(4.8F * rainStrength);
                        int y = l2 - range;
                        int yMax = l2 + range;

                        if (y < yHeight)
                        {
                            y = yHeight;
                        }

                        if (yMax < yHeight)
                        {
                            yMax = yHeight;
                        }

                        float f8 = 1.0F;
                        int yBase = yHeight;

                        if (yHeight < k)
                        {
                            yBase = k;
                        }

                        if (y != yMax)
                        {
                            this.random.setSeed((long)(i1 * i1 * 3121 + i1 * 45238971 ^ l * l * 418711 + l * 13761));
                            float f9 = biomegenbase.getFloatTemperature(i1, y, l);
                            float downwardsMotion;
                            double xDist;

                            /*if (worldclient.getWorldChunkManager().getTemperatureAtHeight(f9, yHeight) >= 0.15F)
                            {*/
                            	
                                if (b1 != 0)
                                {
                                    if (b1 >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    b1 = 0;
                                    
                                    mc.getTextureManager().bindTexture(locationRainPng);
                                    
                                    tessellator.startDrawingQuads();
                                }

                                float speed = f5 / 2;
                                downwardsMotion = (((int)speed + i1 * i1 * 3121 + i1 * 45238971 + l * l * 418711 + l * 13761 & 31) + partialTicks) / 32.0F * (1.0F + this.random.nextFloat());
  
                                //GalaxySpace.debug(downwardsMotion + "");
                                double yo = this.random.nextDouble() / 1.8D;
                                
                                double d3 = (double)((float)i1 + 0.5F) - entitylivingbase.posX;
                                xDist = (double)((float)l + 0.5F) - entitylivingbase.posZ;
                                float f12 = MathHelper.sqrt_double(d3 * d3 + xDist * xDist) / (float)range;
               
                                tessellator.setBrightness(worldclient.getLightBrightnessForSkyBlocks(i1, yBase, l, 0));
                                tessellator.setColorRGBA_F(1.0F, 0.5F, 0.0F, ((1.0F - f12 * f12) * 0.5F + 0.5F) * rainStrength);
                                tessellator.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)y - yo, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8), (double)((float)y * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)y - yo, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8), (double)((float)y * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)yMax - yo, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8), (double)((float)yMax * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)yMax - yo, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8), (double)((float)yMax * f8 / 4.0F + downwardsMotion * f8));
                                
                            //}
                            /*else
                            {
                                if (b1 != 1)
                                {
                                    if (b1 >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    b1 = 1;
                                    mc.getTextureManager().bindTexture(locationSnowPng);
                                    tessellator.startDrawingQuads();
                                }

                                downwardsMotion = ((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                                float f16 = this.random.nextFloat() + f5 * 0.01F * (float)this.random.nextGaussian();
                                float f11 = this.random.nextFloat() + f5 * (float)this.random.nextGaussian() * 0.001F;
                                xDist = (double)((float)i1 + 0.5F) - entitylivingbase.posX;
                                double zDist = (double)((float)l + 0.5F) - entitylivingbase.posZ;
                                float f14 = MathHelper.sqrt_double(xDist * xDist + zDist * zDist) / (float)range;
                                float f15 = 1.0F;
                                tessellator.setBrightness((worldclient.getLightBrightnessForSkyBlocks(i1, yBase, l, 0) * 3 + 15728880) / 4);
                                tessellator.setColorRGBA_F(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * rainStrength);
                                tessellator.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)y, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)y * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)y, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)y * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)yMax, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)yMax * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)yMax, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)yMax * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            }*/
                        }
                    }
                }
            }

            if (b1 >= 0)
            {
                tessellator.draw();
            }

            tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            mc.entityRenderer.disableLightmap((double)partialTicks);
        }
	}

	private void initializeRainCoords() {
		if (this.rainXCoords == null)
		{
		    this.rainXCoords = new float[1024];
		    this.rainYCoords = new float[1024];

		    for (int i = 0; i < 32; ++i)
	        {
	            float f1 = (float)(i - 16);
	            for (int j = 0; j < 32; ++j)
	            {
	                float f = (float)(j - 16);
	                float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
	                this.rainXCoords[i << 5 | j] = -f1 / f2;
	                this.rainYCoords[i << 5 | j] = f / f2;
	            }
	        }		    
		}
	}
	
}
