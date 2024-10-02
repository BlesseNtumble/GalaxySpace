/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.api.prefab.entity.EntitySpaceshipBase
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.IModelCustom
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.render.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntitySpaceshipBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderRockets
extends Render {
    private ResourceLocation rocketTexture;
    protected IModelCustom rocketModelObj;

    public RenderRockets(IModelCustom spaceshipModel, String textureDomain, String texture) {
        this.rocketModelObj = spaceshipModel;
        this.rocketTexture = new ResourceLocation(textureDomain, "textures/model/" + texture + ".png");
        this.shadowSize = 2.0f;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.rocketTexture;
    }

    public void renderSpaceship(EntitySpaceshipBase entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glDisable((int)32826);
        GL11.glPushMatrix();
        float var24 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * par9 + 180.0f;
        float var25 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * par9 + 45.0f;
        GL11.glTranslatef((float)((float)par2), (float)((float)par4 - 2.2f), (float)((float)par6));
        GL11.glRotatef((float)(180.0f - par8), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-var24), (float)0.0f, (float)0.0f, (float)1.0f);
        float var28 = entity.rollAmplitude / 3.0f - par9;
        float var30 = entity.shipDamage - par9;
        if (var30 < 0.0f) {
            var30 = 0.0f;
        }
        if (var28 > 0.0f) {
            float i = entity.getLaunched() ? (float)(5 - MathHelper.floor_double((double)(entity.timeUntilLaunch / 85))) / 10.0f : 0.3f;
            GL11.glRotatef((float)(MathHelper.sin((float)var28) * var28 * i * par9), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(MathHelper.sin((float)var28) * var28 * i * par9), (float)1.0f, (float)0.0f, (float)1.0f);
        }
        this.bindTexture(this.rocketTexture);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        GL11.glScalef((float)0.9f, (float)0.9f, (float)0.9f);
        this.rocketModelObj.renderAll();
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderSpaceship((EntitySpaceshipBase)par1Entity, par2, par4, par6, par8, par9);
    }
}

