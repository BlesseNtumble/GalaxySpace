/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  micdoodle8.mods.galacticraft.api.entity.IRocketType$EnumRocketType
 *  micdoodle8.mods.galacticraft.core.entities.EntityTier1Rocket
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.model.ModelChest
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  net.minecraftforge.client.model.AdvancedModelLoader
 *  net.minecraftforge.client.model.IModelCustom
 *  org.lwjgl.Sys
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.entity.IRocketType;
import micdoodle8.mods.galacticraft.core.entities.EntityTier1Rocket;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class ItemRendererCargoFluidRocket
implements IItemRenderer {
    protected static final ResourceLocation chestTexture = new ResourceLocation("textures/entity/chest/normal.png");
    private static final ResourceLocation cargoRocketTexture = new ResourceLocation("galacticraftmars", "textures/model/cargoRocket.png");
    private IModelCustom cargoRocketModel;
    protected IModelCustom modelSpaceship;
    protected final ModelChest chestModel = new ModelChest();
    private static final IModelCustom model = AdvancedModelLoader.loadModel((ResourceLocation)new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/tank.obj"));
    private static ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/tank.png");

    public ItemRendererCargoFluidRocket(IModelCustom cargoRocketModel) {
        this.cargoRocketModel = cargoRocketModel;
    }

    protected void renderSpaceship(IItemRenderer.ItemRenderType type, RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
        GL11.glPushMatrix();
        this.transform(item, type);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(cargoRocketTexture);
        this.cargoRocketModel.renderAll();
        GL11.glPopMatrix();
        if (type == IItemRenderer.ItemRenderType.INVENTORY) {
            int index = Math.min(Math.max(item.getItemDamage() >= 10 ? item.getItemDamage() - 10 : item.getItemDamage(), 0), IRocketType.EnumRocketType.values().length - 1);
            if (IRocketType.EnumRocketType.values()[index].getInventorySpace() > 3) {
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
                GL11.glPushMatrix();
                GL11.glDisable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glScalef((float)0.5f, (float)-0.5f, (float)-0.5f);
                GL11.glTranslatef((float)1.9f, (float)1.85f, (float)1.7f);
                boolean short1 = false;
                GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)-1.5f, (float)-1.5f, (float)-1.5f);
                float f1 = 0.0f;
                f1 = 1.0f - f1;
                f1 = 1.0f - f1 * f1 * f1;
                model.renderAll();
                GL11.glEnable((int)2929);
                GL11.glPopMatrix();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
        }
    }

    public void transform(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
        EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
        if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
            GL11.glRotatef((float)70.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-10.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)-0.8f, (float)-8.2f, (float)0.0f);
            GL11.glScalef((float)5.2f, (float)5.2f, (float)5.2f);
            if (player != null && player.ridingEntity != null && player.ridingEntity instanceof EntityTier1Rocket) {
                GL11.glScalef((float)0.0f, (float)0.0f, (float)0.0f);
            }
        }
        if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef((float)8.5f, (float)5.9f, (float)1.0f);
            GL11.glRotatef((float)28.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)230.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)73.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)5.2f, (float)5.2f, (float)5.2f);
            if (player != null && player.ridingEntity != null && player.ridingEntity instanceof EntityTier1Rocket) {
                GL11.glScalef((float)0.0f, (float)0.0f, (float)0.0f);
            }
        }
        GL11.glTranslatef((float)0.0f, (float)0.1f, (float)0.0f);
        GL11.glScalef((float)-0.4f, (float)-0.4f, (float)0.4f);
        if (type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.ENTITY) {
            if (type == IItemRenderer.ItemRenderType.INVENTORY) {
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)0.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glScalef((float)0.4f, (float)0.4f, (float)0.4f);
                GL11.glTranslatef((float)0.8f, (float)5.6f, (float)-0.4f);
            } else {
                GL11.glTranslatef((float)0.0f, (float)-0.9f, (float)0.0f);
                GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            }
            GL11.glScalef((float)1.3f, (float)1.3f, (float)1.3f);
            GL11.glTranslatef((float)0.0f, (float)-0.6f, (float)0.0f);
            GL11.glRotatef((float)((float)Sys.getTime() / 30.0f % 360.0f + 45.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
    }

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        switch (type) {
            case ENTITY: {
                return true;
            }
            case EQUIPPED: {
                return true;
            }
            case EQUIPPED_FIRST_PERSON: {
                return true;
            }
            case INVENTORY: {
                return true;
            }
        }
        return false;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        switch (type) {
            case EQUIPPED: {
                this.renderSpaceship(type, (RenderBlocks)data[0], item, -0.5f, -0.5f, -0.5f);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                this.renderSpaceship(type, (RenderBlocks)data[0], item, -0.5f, -0.5f, -0.5f);
                break;
            }
            case INVENTORY: {
                this.renderSpaceship(type, (RenderBlocks)data[0], item, -0.5f, -0.5f, -0.5f);
                break;
            }
            case ENTITY: {
                this.renderSpaceship(type, (RenderBlocks)data[0], item, -0.5f, -0.5f, -0.5f);
                break;
            }
        }
    }
}

