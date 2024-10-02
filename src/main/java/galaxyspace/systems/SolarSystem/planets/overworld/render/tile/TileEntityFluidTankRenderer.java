/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.AdvancedModelLoader
 *  net.minecraftforge.client.model.IModelCustom
 *  net.minecraftforge.fluids.FluidStack
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.FluidRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFluidTank;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class TileEntityFluidTankRenderer
extends TileEntitySpecialRenderer {
    private static final IModelCustom model = AdvancedModelLoader.loadModel((ResourceLocation)new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/tank.obj"));
    private static ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/tank.png");

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        GL11.glTranslated((double)(x + 0.5), (double)(tileEntity.getBlockMetadata() == 1 ? y + 1.0 : y), (double)(z + 0.5));
        GL11.glRotatef((float)(tileEntity.getBlockMetadata() == 1 ? 180.0f : 0.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
        this.renderAModelAt((TileEntityFluidTank)tileEntity, x, y, z, tick);
    }

    private void renderAModelAt(TileEntityFluidTank tileEntity, double x, double y, double z, float partialTick) {
        FluidStack liquid;
        FluidStack fluidStack = liquid = tileEntity.waterTank.getFluid() != null ? tileEntity.waterTank.getFluid() : null;
        if (liquid == null || liquid.getFluid() == null || liquid.amount <= 0) {
            return;
        }
        int color = 0xFFFFFF;
        int[] displayList = FluidRenderer.getFluidDisplayLists(liquid, tileEntity.getWorldObj(), false);
        if (displayList == null) {
            return;
        }
        for (int i = 0; i < 2; ++i) {
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)8192);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            this.bindTexture(TextureMap.locationBlocksTexture);
            FluidRenderer.setGLColorFromInt(color);
            GL11.glTranslatef((float)((float)x + 0.125f), (float)((float)y + 0.5f), (float)((float)z + 0.125f));
            if (i < 1) {
                GL11.glScalef((float)0.35f, (float)0.999f, (float)0.85f);
                GL11.glTranslatef((float)0.55f, (float)-0.38f, (float)-0.05f);
            } else {
                GL11.glScalef((float)0.85f, (float)0.999f, (float)0.35f);
                GL11.glTranslatef((float)-0.05f, (float)-0.379f, (float)0.55f);
            }
            int dl = (int)((float)liquid.amount / (float)tileEntity.waterTank.getCapacity() * 39.0f);
            GL11.glCallList((int)displayList[TileEntityFluidTankRenderer.clamp(dl, 0, 79)]);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }

    public static int clamp(int value, int min, int max) {
        return value < min ? min : (value > max ? max : value);
    }
}

