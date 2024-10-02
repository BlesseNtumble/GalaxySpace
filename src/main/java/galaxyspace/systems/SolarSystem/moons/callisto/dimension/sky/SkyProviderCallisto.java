/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  micdoodle8.mods.galacticraft.api.vector.Vector3
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.moons.callisto.dimension.sky;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SkyProviderCallisto
extends SkyProviderBase {
    private static final ResourceLocation jupiterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/jupiter.png");
    private static final ResourceLocation ioTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/io.png");
    private static final ResourceLocation europaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/europa.png");
    private static final ResourceLocation ganymedeTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/ganymede.png");

    @Override
    protected void rendererSky(Tessellator tessellator, float f10, float ticks) {
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        f10 = 0.2f;
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glRotatef((float)-14.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)-105.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ioTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)f10, 0.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)f10, 1.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)(-f10), 1.0, 0.0);
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)(-f10), 0.0, 0.0);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        f10 = 0.5f;
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glRotatef((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)-35.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(europaTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)f10, 0.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)f10, 1.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)(-f10), 1.0, 0.0);
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)(-f10), 0.0, 0.0);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        f10 = 1.0f;
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glRotatef((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)-110.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ganymedeTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)f10, 0.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)f10, 1.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)(-f10), 1.0, 0.0);
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)(-f10), 0.0, 0.0);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        f10 = 17.0f;
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glRotatef((float)-180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)110.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(jupiterTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)f10, 0.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)f10, 1.0, 1.0);
        tessellator.addVertexWithUV((double)f10, -100.0, (double)(-f10), 1.0, 0.0);
        tessellator.addVertexWithUV((double)(-f10), -100.0, (double)(-f10), 0.0, 0.0);
        tessellator.draw();
        float f = 0.9f;
        this.renderAtmo(tessellator, 0.0f, 0.0f, f10 - 2.0f, new Vector3((double)(0.47058824f * f), (double)(0.43137255f * f), (double)(0.47058824f * f)));
    }

    @Override
    protected boolean enableBaseImages() {
        return true;
    }

    @Override
    protected float sunSize() {
        return 10.5f;
    }

    @Override
    protected boolean enableStar() {
        return true;
    }

    @Override
    protected ResourceLocation sunImage() {
        return null;
    }

    @Override
    protected int modeLight() {
        return 0;
    }

    @Override
    protected Vector3 colorSunAura() {
        return null;
    }

    @Override
    protected Vector3 getAtmosphereColor() {
        return null;
    }
}

