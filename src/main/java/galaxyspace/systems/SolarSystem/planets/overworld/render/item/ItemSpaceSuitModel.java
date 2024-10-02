/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.AdvancedModelLoader
 *  net.minecraftforge.client.model.IModelCustom
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.models.ModelOBJArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class ItemSpaceSuitModel
extends ModelOBJArmor {
    public static final IModelCustom model = AdvancedModelLoader.loadModel((ResourceLocation)new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/spacesuit.obj"));
    public static final ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/armor/spacesuit_layer.png");
    public static final ResourceLocation texture_layer = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/armor/spacesuit.png");
    private final int partType;
    public static final IModelCustom modelJet = AdvancedModelLoader.loadModel((ResourceLocation)new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/jetpack1.obj"));
    public static final ResourceLocation textureJet = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/armor/jetpack1.png");
    public float[] color = new float[3];

    public ItemSpaceSuitModel(int armorType) {
        this.partType = armorType;
        this.color[0] = 0.4f;
        this.color[1] = 0.4f;
        this.color[2] = 0.4f;
    }

    @Override
    public void pre() {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
    }

    @Override
    public void post() {
        GL11.glDisable((int)3042);
    }

    @Override
    public void partHead() {
        if (this.partType == 0) {
            GL11.glTranslatef((float)0.0f, (float)-1.9f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.78f, (float)0.75f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GL11.glColor3f((float)this.color[0], (float)this.color[1], (float)this.color[2]);
            model.renderPart("G");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_layer);
            model.renderPart("G");
        }
    }

    @Override
    public void partBody() {
        if (this.partType == 1 || this.partType == 5) {
            GL11.glTranslatef((float)0.0f, (float)-1.75f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GL11.glColor3f((float)this.color[0], (float)this.color[1], (float)this.color[2]);
            model.renderPart("Gru");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_layer);
            model.renderPart("Gru");
        }
        if (this.partType == 5 || this.partType == 6) {
            if (this.partType == 5) {
                GL11.glTranslatef((float)0.0f, (float)1.9f, (float)0.0f);
            } else {
                GL11.glTranslatef((float)0.0f, (float)-0.3f, (float)-0.7f);
            }
            Minecraft.getMinecraft().renderEngine.bindTexture(textureJet);
            modelJet.renderPart("wing1");
            modelJet.renderPart("wing2");
            modelJet.renderPart("corp");
        }
    }

    @Override
    public void partRightArm() {
        if (this.partType == 1 || this.partType == 5) {
            GL11.glTranslatef((float)0.375f, (float)-1.6f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GL11.glColor3f((float)this.color[0], (float)this.color[1], (float)this.color[2]);
            model.renderPart("P2");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_layer);
            model.renderPart("P2");
        }
    }

    @Override
    public void partLeftArm() {
        if (this.partType == 1 || this.partType == 5) {
            GL11.glTranslatef((float)-0.375f, (float)-1.6f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GL11.glColor3f((float)this.color[0], (float)this.color[1], (float)this.color[2]);
            model.renderPart("L2");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_layer);
            model.renderPart("L2");
        }
    }

    @Override
    public void partRightLeg() {
        if (this.partType == 2) {
            GL11.glTranslatef((float)0.125f, (float)-0.75f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GL11.glColor3f((float)this.color[0], (float)this.color[1], (float)this.color[2]);
            model.renderPart("P3");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_layer);
            model.renderPart("P3");
        }
    }

    @Override
    public void partLeftLeg() {
        if (this.partType == 2) {
            GL11.glTranslatef((float)-0.125f, (float)-0.75f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GL11.glColor3f((float)this.color[0], (float)this.color[1], (float)this.color[2]);
            model.renderPart("L3");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_layer);
            model.renderPart("L3");
        }
    }
}

