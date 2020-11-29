package galaxyspace.core.client.models.entity;

import galaxyspace.core.prefab.entities.EntityAstroWolf;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelAstroWolf extends ModelBase
{
    /** main box for the wolf head */
    public ModelRenderer wolfHeadMain;
    /** The wolf's body */
    public ModelRenderer wolfBody;
    /** Wolf'se first leg */
    public ModelRenderer wolfLeg1;
    /** Wolf's second leg */
    public ModelRenderer wolfLeg2;
    /** Wolf's third leg */
    public ModelRenderer wolfLeg3;
    /** Wolf's fourth leg */
    public ModelRenderer wolfLeg4;    
    /** Helmet */
    public ModelRenderer wolfHelmet;
    /** The wolf's tail */
    ModelRenderer wolfTail;
    /** The wolf's mane */
    ModelRenderer wolfMane;

    public ModelRenderer tube1;
    public ModelRenderer tube2;
    public ModelRenderer tube3;
    public ModelRenderer tube1_1;
    public ModelRenderer tube1_2;
    public ModelRenderer tube1_3;
    public ModelRenderer tube1_4;
    public ModelRenderer tube1_5;
    public ModelRenderer oxBallon;
    
    public ModelAstroWolf()
    {
    	this.textureWidth = 128;
    	this.textureHeight = 64;
        float f = 0.0F;
        float f1 = 13.5F;
        this.wolfHeadMain = new ModelRenderer(this, 0, 0);
        this.wolfHeadMain.addBox(-2.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F);
        this.wolfHeadMain.setRotationPoint(-1.0F, 13.5F, -7.0F);
        this.wolfBody = new ModelRenderer(this, 18, 14);
        this.wolfBody.addBox(-3.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F);
        this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
        this.wolfMane = new ModelRenderer(this, 21, 0);
        this.wolfMane.addBox(-3.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
        this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
        this.wolfLeg1 = new ModelRenderer(this, 0, 18);
        this.wolfLeg1.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        this.wolfLeg2 = new ModelRenderer(this, 0, 18);
        this.wolfLeg2.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        this.wolfLeg3 = new ModelRenderer(this, 0, 18);
        this.wolfLeg3.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        this.wolfLeg4 = new ModelRenderer(this, 0, 18);
        this.wolfLeg4.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        this.wolfTail = new ModelRenderer(this, 9, 18);
        this.wolfTail.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        
        this.wolfHelmet = new ModelRenderer(this, 51, 0);
        this.wolfHelmet.setRotationPoint(-0.0F, 12.6F, -9.2F);
        this.wolfHelmet.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10, -0.6F);
        this.setRotateAngle(wolfHelmet, 0.0F, 0.0F, 8.726646259971648E-4F);
        
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
        this.wolfHeadMain.setTextureOffset(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3, 3, 4, 0.0F);
        
        this.tube1 = new ModelRenderer(this, 51, 20);
        this.tube1.setRotationPoint(-0.5F, 9.0F, -6.0F);
        this.tube1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tube2 = new ModelRenderer(this, 51, 20);
        this.tube2.setRotationPoint(-0.5F, 9.3F, -0.5F);
        this.tube2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tube3 = new ModelRenderer(this, 51, 20);
        this.tube3.setRotationPoint(-0.5F, 10.1F, 0.5F);
        this.tube3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        
        this.tube1_1 = new ModelRenderer(this, 51, 20);
        this.tube1_1.setRotationPoint(-0.5F, 9.0F, -5.0F);
        this.tube1_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tube1_2 = new ModelRenderer(this, 51, 20);
        this.tube1_2.setRotationPoint(-0.5F, 9.0F, -4.0F);
        this.tube1_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tube1_3 = new ModelRenderer(this, 51, 20);
        this.tube1_3.setRotationPoint(-0.5F, 9.0F, -3.0F);
        this.tube1_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tube1_4 = new ModelRenderer(this, 51, 20);
        this.tube1_4.setRotationPoint(-0.5F, 9.0F, -2.0F);
        this.tube1_4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tube1_5 = new ModelRenderer(this, 51, 20);
        this.tube1_5.setRotationPoint(-0.5F, 9.0F, -1.0F);
        this.tube1_5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        
        this.oxBallon = new ModelRenderer(this, 51, 20);
        this.oxBallon.setRotationPoint(-1.0F, 9.0F, 0.0F);
        this.oxBallon.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        EntityAstroWolf entitywolf = (EntityAstroWolf)entityIn;
        
        if (this.isChild)
        {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 5.0F * scale, 2.0F * scale);
            this.wolfHeadMain.renderWithRotation(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.wolfBody.render(scale);
            this.wolfLeg1.render(scale);
            this.wolfLeg2.render(scale);
            this.wolfLeg3.render(scale);
            this.wolfLeg4.render(scale);
            this.wolfTail.renderWithRotation(scale);
            this.wolfMane.render(scale);
            
            if(entitywolf.wolfInventory.getStackInSlot(0).getItem() == GCItems.oxMask && !OxygenUtil.isAABBInBreathableAirBlock(entitywolf)) {
	            this.wolfHelmet.renderWithRotation(scale);
	            this.tube1.render(scale);
	            this.tube2.render(scale);
	            this.tube3.render(scale);
	            this.tube1_1.render(scale);
	            this.tube1_2.render(scale);
	            this.tube1_3.render(scale);
	            this.tube1_4.render(scale);
	            this.tube1_5.render(scale);
            }
            
            if(entitywolf.wolfInventory.getStackInSlot(2).getItem() instanceof ItemOxygenTank)
            	this.oxBallon.render(scale);
            
            GlStateManager.popMatrix();
        }
        else
        {
            this.wolfHeadMain.renderWithRotation(scale);
            this.wolfBody.render(scale);
            this.wolfLeg1.render(scale);
            this.wolfLeg2.render(scale);
            this.wolfLeg3.render(scale);
            this.wolfLeg4.render(scale);
            this.wolfTail.renderWithRotation(scale);
            this.wolfMane.render(scale);
            if(entitywolf.wolfInventory.getStackInSlot(0).getItem() == GCItems.oxMask && !OxygenUtil.isAABBInBreathableAirBlock(entitywolf)) {
	            this.wolfHelmet.render(scale);
	            this.tube1.render(scale);
	            this.tube2.render(scale);
	            this.tube3.render(scale);      
	            this.tube1_1.render(scale);
	            this.tube1_2.render(scale);
	            this.tube1_3.render(scale);
	            this.tube1_4.render(scale);
	            this.tube1_5.render(scale);
            }
            if(entitywolf.wolfInventory.getStackInSlot(2).getItem() instanceof ItemOxygenTank)
            	this.oxBallon.render(scale);
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityAstroWolf entitywolf = (EntityAstroWolf)entitylivingbaseIn;

        if (entitywolf.isAngry())
        {
            this.wolfTail.rotateAngleY = 0.0F;
        }
        else
        {
            this.wolfTail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        if (entitywolf.isSitting())
        {
            this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
            this.wolfMane.rotateAngleX = ((float)Math.PI * 2F / 5F);
            this.wolfMane.rotateAngleY = 0.0F;
            this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
            this.wolfBody.rotateAngleX = ((float)Math.PI / 4F);
            this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
            this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
            this.wolfLeg1.rotateAngleX = ((float)Math.PI * 3F / 2F);
            this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
            this.wolfLeg2.rotateAngleX = ((float)Math.PI * 3F / 2F);
            this.wolfLeg3.rotateAngleX = 5.811947F;
            this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
            this.wolfLeg4.rotateAngleX = 5.811947F;
            this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
            
            this.tube1_1.offsetY = 0.05F;
            this.tube1_2.offsetY = 0.08F;
            this.tube1_3.offsetY = 0.1F;
            this.tube1_4.offsetY = 0.13F;
            this.tube1_5.offsetY = 0.15F;
            this.tube2.offsetZ = 0.03F;
            this.tube2.offsetY = 0.18F;
            this.tube3.offsetY = 0.22F;
            
            this.oxBallon.rotateAngleX = 14.95F;
            this.oxBallon.rotateAngleY = 0F;
            this.oxBallon.offsetX = 0F;
            this.oxBallon.offsetY = 0.55F;
            this.oxBallon.offsetZ = 0.25F;
        }
        else
        {
            this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
            this.wolfBody.rotateAngleX = ((float)Math.PI / 2F);
            this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
            this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
            this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
            this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
            this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            
            this.tube1_1.offsetY = 0.0F;
            this.tube1_2.offsetY = 0.0F;
            this.tube1_3.offsetY = 0.0F;
            this.tube1_4.offsetY = 0.0F;
            this.tube1_5.offsetY = 0.0F;
            this.tube2.offsetZ = 0.0F;
            this.tube2.offsetY = 0.0F;
            this.tube3.offsetY = 0.0F;
            
            this.oxBallon.rotateAngleX = 0F;
            this.oxBallon.rotateAngleY = 3.15F;
            this.oxBallon.offsetX = 0.13F;
            this.oxBallon.offsetY = 0F;
            this.oxBallon.offsetZ = 0.4F;
        }

        this.wolfHeadMain.rotateAngleZ = entitywolf.getInterestedAngle(partialTickTime) + entitywolf.getShakeAngle(partialTickTime, 0.0F);
        this.wolfHelmet.rotateAngleZ = entitywolf.getInterestedAngle(partialTickTime) + entitywolf.getShakeAngle(partialTickTime, 0.0F);
       
        this.wolfMane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
        this.wolfBody.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
        this.wolfTail.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.wolfHeadMain.rotateAngleX = headPitch * 0.017453292F;
        this.wolfHeadMain.rotateAngleY = netHeadYaw * 0.017453292F;
        
        this.wolfHelmet.rotateAngleX = headPitch * 0.017453292F;
        this.wolfHelmet.rotateAngleY = netHeadYaw * 0.017453292F;
        
        this.wolfTail.rotateAngleX = ageInTicks;
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}