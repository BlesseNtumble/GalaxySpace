/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.biome.BiomeGenBase
 *  net.minecraftforge.client.IRenderHandler
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.moons.titan.dimension.sky;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class WeatherProviderTitan
extends IRenderHandler {
    private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
    float[] rainXCoords;
    float[] rainYCoords;
    private int rendererUpdateCount;
    private Random random = new Random();

    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        ++this.rendererUpdateCount;
        this.renderNormalWeather(partialTicks, mc);
    }

    private void renderNormalWeather(float partialTicks, Minecraft mc) {
        float rainStrength = mc.theWorld.getRainStrength(partialTicks);
        if (rainStrength > 0.0f) {
            mc.entityRenderer.enableLightmap((double)partialTicks);
            this.initializeRainCoords();
            EntityLivingBase entitylivingbase = mc.renderViewEntity;
            WorldClient worldclient = mc.theWorld;
            int k2 = MathHelper.floor_double((double)entitylivingbase.posX);
            int l2 = MathHelper.floor_double((double)entitylivingbase.posY);
            int i3 = MathHelper.floor_double((double)entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable((int)2884);
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            double d0 = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)partialTicks;
            double d1 = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)partialTicks;
            double d2 = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)partialTicks;
            int k = MathHelper.floor_double((double)d1);
            int range = 6;
            if (mc.gameSettings.fancyGraphics) {
                range = 10;
            }
            int b1 = -1;
            float f5 = (float)this.rendererUpdateCount + partialTicks;
            if (mc.gameSettings.fancyGraphics) {
                range = 10;
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            for (int l = i3 - range; l <= i3 + range; ++l) {
                for (int i1 = k2 - range; i1 <= k2 + range; ++i1) {
                    double xDist;
                    float downwardsMotion;
                    int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
                    float f6 = this.rainXCoords[j1] * 0.5f;
                    float f7 = this.rainYCoords[j1] * 0.5f;
                    BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(i1, l);
                    if (!biomegenbase.canSpawnLightningBolt() && !biomegenbase.getEnableSnow()) continue;
                    int k1 = worldclient.getPrecipitationHeight(i1, l);
                    int l1 = l2 - range;
                    int i2 = l2 + range;
                    if (l1 < k1) {
                        l1 = k1;
                    }
                    if (i2 < k1) {
                        i2 = k1;
                    }
                    float f8 = 1.0f;
                    int j2 = k1;
                    if (k1 < k) {
                        j2 = k;
                    }
                    if (l1 == i2) continue;
                    this.random.setSeed(i1 * i1 * 3121 + i1 * 45238971 ^ l * l * 418711 + l * 13761);
                    float f9 = biomegenbase.getFloatTemperature(i1, l1, l);
                    if (worldclient.getWorldChunkManager().getTemperatureAtHeight(f9, k1) >= 0.15f) {
                        if (b1 != 0) {
                            if (b1 >= 0) {
                                tessellator.draw();
                            }
                            b1 = 0;
                            mc.getTextureManager().bindTexture(locationRainPng);
                            tessellator.startDrawingQuads();
                        }
                        downwardsMotion = ((float)(this.rendererUpdateCount + i1 * i1 * 3121 + i1 * 45238971 + l * l * 418711 + l * 13761 & 0x1F) + partialTicks) / 32.0f * (3.0f + this.random.nextFloat());
                        downwardsMotion /= 6.0f;
                        double d3 = (double)((float)i1 + 0.5f) - entitylivingbase.posX;
                        xDist = (double)((float)l + 0.5f) - entitylivingbase.posZ;
                        float f12 = MathHelper.sqrt_double((double)(d3 * d3 + xDist * xDist)) / (float)range;
                        float f13 = 1.0f;
                        tessellator.setBrightness(worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0));
                        tessellator.setColorRGBA_F(f13, 0.5f, 0.0f, ((1.0f - f12 * f12) * 0.5f + 0.5f) * rainStrength);
                        tessellator.setTranslation(-d0 * 1.0, -d1 * 1.0, -d2 * 1.0);
                        tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5, (double)l1, (double)((float)l - f7) + 0.5, (double)(0.0f * f8), (double)((float)l1 * f8 / 4.0f + downwardsMotion * f8));
                        tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5, (double)l1, (double)((float)l + f7) + 0.5, (double)(1.0f * f8), (double)((float)l1 * f8 / 4.0f + downwardsMotion * f8));
                        tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5, (double)i2, (double)((float)l + f7) + 0.5, (double)(1.0f * f8), (double)((float)i2 * f8 / 4.0f + downwardsMotion * f8));
                        tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5, (double)i2, (double)((float)l - f7) + 0.5, (double)(0.0f * f8), (double)((float)i2 * f8 / 4.0f + downwardsMotion * f8));
                        tessellator.setTranslation(0.0, 0.0, 0.0);
                        continue;
                    }
                    if (b1 != 1) {
                        if (b1 >= 0) {
                            tessellator.draw();
                        }
                        b1 = 1;
                        mc.getTextureManager().bindTexture(locationSnowPng);
                        tessellator.startDrawingQuads();
                    }
                    downwardsMotion = ((float)(this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0f;
                    float f16 = this.random.nextFloat() + f5 * 0.01f * (float)this.random.nextGaussian();
                    float f11 = this.random.nextFloat() + f5 * (float)this.random.nextGaussian() * 0.001f;
                    xDist = (double)((float)i1 + 0.5f) - entitylivingbase.posX;
                    double zDist = (double)((float)l + 0.5f) - entitylivingbase.posZ;
                    float f14 = MathHelper.sqrt_double((double)(xDist * xDist + zDist * zDist)) / (float)range;
                    float f15 = 1.0f;
                    tessellator.setBrightness((worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 0xF000F0) / 4);
                    tessellator.setColorRGBA_F(f15, f15, f15, ((1.0f - f14 * f14) * 0.3f + 0.5f) * rainStrength);
                    tessellator.setTranslation(-d0 * 1.0, -d1 * 1.0, -d2 * 1.0);
                    tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5, (double)l1, (double)((float)l - f7) + 0.5, (double)(0.0f * f8 + f16), (double)((float)l1 * f8 / 4.0f + downwardsMotion * f8 + f11));
                    tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5, (double)l1, (double)((float)l + f7) + 0.5, (double)(1.0f * f8 + f16), (double)((float)l1 * f8 / 4.0f + downwardsMotion * f8 + f11));
                    tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5, (double)i2, (double)((float)l + f7) + 0.5, (double)(1.0f * f8 + f16), (double)((float)i2 * f8 / 4.0f + downwardsMotion * f8 + f11));
                    tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5, (double)i2, (double)((float)l - f7) + 0.5, (double)(0.0f * f8 + f16), (double)((float)i2 * f8 / 4.0f + downwardsMotion * f8 + f11));
                    tessellator.setTranslation(0.0, 0.0, 0.0);
                }
            }
            if (b1 >= 0) {
                tessellator.draw();
            }
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            mc.entityRenderer.disableLightmap((double)partialTicks);
        }
    }

    private void initializeRainCoords() {
        if (this.rainXCoords == null) {
            this.rainXCoords = new float[1024];
            this.rainYCoords = new float[1024];
            for (int i = 0; i < 32; ++i) {
                for (int j = 0; j < 32; ++j) {
                    float f2 = j - 16;
                    float f3 = i - 16;
                    float f4 = MathHelper.sqrt_float((float)(f2 * f2 + f3 * f3));
                    this.rainXCoords[i << 5 | j] = -f3 / f4;
                    this.rainYCoords[i << 5 | j] = f2 / f4;
                }
            }
        }
    }
}

