/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.IRenderHandler
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.moons.titan.dimension.sky;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class CloudProviderTitan
extends IRenderHandler {
    private static Minecraft mc;
    private static final ResourceLocation locationCloudsPng;
    public static int cloudTickCounter;

    public CloudProviderTitan() {
        mc = FMLClientHandler.instance().getClient();
    }

    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        GL11.glDisable((int)2884);
        float f1 = (float)(CloudProviderTitan.mc.renderViewEntity.lastTickPosY + (CloudProviderTitan.mc.renderViewEntity.posY - CloudProviderTitan.mc.renderViewEntity.lastTickPosY) * (double)partialTicks);
        Tessellator tessellator = Tessellator.instance;
        float f2 = 12.0f;
        float f3 = 2.0f;
        mc.renderEngine.bindTexture(locationCloudsPng);
        GL11.glEnable((int)3042);
        OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        mc.theWorld.getCloudColour(partialTicks);
        int b0 = 10;
        int b1 = 2;
        float f13 = 9.765625E-4f;
        for (int count = 0; count < 4; ++count) {
            GL11.glPushMatrix();
            GL11.glScalef((float)(f2 + (float)(count * 2)), (float)1.0f, (float)(f2 + (float)(count * 2)));
            for (int k = 0; k < 2; ++k) {
                float f10;
                float f9;
                float f8;
                if (k == 0) {
                    GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                } else if (CloudProviderTitan.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)true);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)true);
                    }
                } else {
                    GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                }
                double d0 = (float)cloudTickCounter * (1.0f - (float)count / 5.0f) + (float)count / 5.0f * partialTicks + (float)(count * 250000);
                double d1 = (CloudProviderTitan.mc.renderViewEntity.prevPosX + (CloudProviderTitan.mc.renderViewEntity.posX - CloudProviderTitan.mc.renderViewEntity.prevPosX) * (double)partialTicks + d0 * (double)0.03f) / (double)f2;
                double d2 = (CloudProviderTitan.mc.renderViewEntity.prevPosZ + (CloudProviderTitan.mc.renderViewEntity.posZ - CloudProviderTitan.mc.renderViewEntity.prevPosZ) * (double)partialTicks) / (double)f2 + (double)0.33f;
                float f4 = mc.theWorld.provider.getCloudHeight() - f1 + 0.33f + (float)count * 20.0f;
                int i = MathHelper.floor_double((double)(d1 / 2048.0));
                int j = MathHelper.floor_double((double)(d2 / 2048.0));
                d1 -= (double)(i * 2048);
                d2 -= (double)(j * 2048);
                float f5 = 234.0f / (1200.0f + (float)count * 300.0f);
                float f6 = 147.0f / (1200.0f + (float)count * 300.0f);
                float f7 = 9.0f / (1200.0f + (float)count * 300.0f);
                if (CloudProviderTitan.mc.gameSettings.anaglyph) {
                    f8 = (f5 * 30.0f + f6 * 59.0f + f7 * 11.0f) / 100.0f;
                    f9 = (f5 * 30.0f + f6 * 70.0f) / 100.0f;
                    f10 = (f5 * 30.0f + f7 * 70.0f) / 100.0f;
                    f5 = f8;
                    f6 = f9;
                    f7 = f10;
                }
                f8 = (float)(d1 * 0.0);
                f9 = (float)(d2 * 0.0);
                f10 = 0.00390625f;
                f8 = (float)MathHelper.floor_double((double)d1) * f10;
                f9 = (float)MathHelper.floor_double((double)d2) * f10;
                float f11 = (float)(d1 - (double)MathHelper.floor_double((double)d1));
                float f12 = (float)(d2 - (double)MathHelper.floor_double((double)d2));
                for (int l = -b1 + 1; l <= b1; ++l) {
                    for (int i1 = -b1 + 1; i1 <= b1; ++i1) {
                        int j1;
                        tessellator.startDrawingQuads();
                        float f14 = l * b0;
                        float f15 = i1 * b0;
                        float f16 = f14 - f11;
                        float f17 = f15 - f12;
                        if (f4 > -f3 - 1.0f) {
                            tessellator.setColorRGBA_F(f5 * 0.7f, f6 * 0.7f, f7 * 0.7f, 0.9f);
                            tessellator.setNormal(0.0f, -1.0f, 0.0f);
                            tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + 0.0f), (double)(f17 + (float)b0), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0f), (double)(f17 + (float)b0), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0f), (double)(f17 + 0.0f), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                            tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + 0.0f), (double)(f17 + 0.0f), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                        }
                        if (f4 <= f3 + 1.0f) {
                            tessellator.setColorRGBA_F(f5, f6, f7, 0.9f);
                            tessellator.setNormal(0.0f, 1.0f, 0.0f);
                            tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + f3 - f13), (double)(f17 + (float)b0), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3 - f13), (double)(f17 + (float)b0), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3 - f13), (double)(f17 + 0.0f), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                            tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + f3 - f13), (double)(f17 + 0.0f), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                        }
                        tessellator.setColorRGBA_F(f5 * 0.9f, f6 * 0.9f, f7 * 0.9f, 0.9f);
                        if (l > -1) {
                            tessellator.setNormal(-1.0f, 0.0f, 0.0f);
                            for (j1 = 0; j1 < b0; ++j1) {
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0f), (double)(f4 + 0.0f), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0f), (double)(f4 + f3), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0f), (double)(f4 + f3), (double)(f17 + 0.0f), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0f), (double)(f4 + 0.0f), (double)(f17 + 0.0f), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                            }
                        }
                        if (l <= 1) {
                            tessellator.setNormal(1.0f, 0.0f, 0.0f);
                            for (j1 = 0; j1 < b0; ++j1) {
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0f - f13), (double)(f4 + 0.0f), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0f - f13), (double)(f4 + f3), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0f - f13), (double)(f4 + f3), (double)(f17 + 0.0f), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0f - f13), (double)(f4 + 0.0f), (double)(f17 + 0.0f), (double)((f14 + (float)j1 + 0.5f) * f10 + f8), (double)((f15 + 0.0f) * f10 + f9));
                            }
                        }
                        tessellator.setColorRGBA_F(f5 * 0.8f, f6 * 0.8f, f7 * 0.8f, 0.9f);
                        if (i1 > -1) {
                            tessellator.setNormal(0.0f, 0.0f, -1.0f);
                            for (j1 = 0; j1 < b0; ++j1) {
                                tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + f3), (double)(f17 + (float)j1 + 0.0f), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3), (double)(f17 + (float)j1 + 0.0f), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0f), (double)(f17 + (float)j1 + 0.0f), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + 0.0f), (double)(f17 + (float)j1 + 0.0f), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                            }
                        }
                        if (i1 <= 1) {
                            tessellator.setNormal(0.0f, 0.0f, 1.0f);
                            for (j1 = 0; j1 < b0; ++j1) {
                                tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + f3), (double)(f17 + (float)j1 + 1.0f - f13), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3), (double)(f17 + (float)j1 + 1.0f - f13), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0f), (double)(f17 + (float)j1 + 1.0f - f13), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                                tessellator.addVertexWithUV((double)(f16 + 0.0f), (double)(f4 + 0.0f), (double)(f17 + (float)j1 + 1.0f - f13), (double)((f14 + 0.0f) * f10 + f8), (double)((f15 + (float)j1 + 0.5f) * f10 + f9));
                            }
                        }
                        tessellator.draw();
                    }
                }
            }
            GL11.glPopMatrix();
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
    }

    static {
        locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
        cloudTickCounter = 0;
    }
}

