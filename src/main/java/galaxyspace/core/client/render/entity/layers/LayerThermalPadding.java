package galaxyspace.core.client.render.entity.layers;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSConstants;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemThermalPaddingBase;
import micdoodle8.mods.galacticraft.api.item.IItemThermal;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.model.ModelPlayerGC;
import micdoodle8.mods.galacticraft.core.client.render.entities.RenderPlayerGC;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.wrappers.PlayerGearData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerThermalPadding  extends LayerArmorBase<ModelBiped>
{
    private final RenderPlayer renderer;
    private final ResourceLocation texture_t3 = new ResourceLocation(GalaxySpace.MODID, "textures/model/armor/thermal_padding_t3_1.png");
    private final ResourceLocation texture_t4 = new ResourceLocation(GalaxySpace.MODID, "textures/model/armor/thermal_padding_t4_1.png");

    public LayerThermalPadding(RenderPlayer playerRendererIn)
    {
        super(playerRendererIn);
        this.renderer = playerRendererIn;
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }

    @Override
    protected void setModelSlotVisible(ModelBiped model, EntityEquipmentSlot slotIn)
    {
        model.setVisible(false);

        switch (slotIn)
        {
	        case HEAD:
	            model.bipedRightLeg.showModel = true;
	            model.bipedLeftLeg.showModel = true;
	            break;
	        case CHEST:
	            model.bipedRightLeg.showModel = true;
	            model.bipedLeftLeg.showModel = true;
	            break;
	        case LEGS:
	            model.bipedBody.showModel = true;
	            model.bipedRightArm.showModel = true;
	            model.bipedLeftArm.showModel = true;
	            break;
	        case FEET:
	            model.bipedHead.showModel = true;
	            model.bipedHeadwear.showModel = true;
	        default:
	        	break;
        }
    }

    public ItemStack getItemStackFromSlot(EntityLivingBase living, EntityEquipmentSlot slotIn)
    {
        PlayerGearData gearData = GalacticraftCore.proxy.getGearData((EntityPlayer) living);

        if (gearData != null)
        {
            int padding = gearData.getThermalPadding(slotIn.getSlotIndex() - 1);
            if (padding != GCPlayerHandler.GEAR_NOT_PRESENT)
            {       
                switch (padding)
                {
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T3_HELMET:
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T3_CHESTPLATE:
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T3_LEGGINGS:
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T3_BOOTS:
	                    return new ItemStack(GSItems.THERMAL_PADDING_3, 1, slotIn.getSlotIndex() - 1);
	                    
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T4_HELMET:
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T4_CHESTPLATE:
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T4_LEGGINGS:
	                case GSConstants.GEAR_ID_THERMAL_PADDING_T4_BOOTS:
	                    return new ItemStack(GSItems.THERMAL_PADDING_4, 1, slotIn.getSlotIndex() - 1);
	                default:
	                	break;
                }
            }
        }

        return ItemStack.EMPTY;   //This null is OK, it's used only as flag by calling code in this same class
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }

    private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        ItemStack itemstack = this.getItemStackFromSlot(entityLivingBaseIn, slotIn);

        
        if (!itemstack.isEmpty())
        {
            ModelBiped model = this.getModelFromSlot(slotIn);
            model.setModelAttributes(this.renderer.getMainModel());
            model.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.setModelSlotVisible(model, slotIn);
            if(itemstack.getItem() instanceof ItemThermalPaddingBase)
            {
            	IItemThermal item = (IItemThermal) itemstack.getItem();
            	switch (item.getThermalStrength())
            	{
            		case 3: 
            			this.renderer.bindTexture(this.texture_t3);
            			break;
            		case 4: 
            			this.renderer.bindTexture(this.texture_t4);
            			break;
            	}
            	//GalaxySpace.debug(itemstack.getUnlocalizedName() + "");
            }
           
            model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            // Start alpha render
            GlStateManager.disableLighting();
            Minecraft.getMinecraft().renderEngine.bindTexture(RenderPlayerGC.thermalPaddingTexture0);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            float time = entityLivingBaseIn.ticksExisted / 10.0F;
            float sTime = (float) Math.sin(time) * 0.5F + 0.5F;

            float r = 0.2F * sTime;
            float g = 1.0F * sTime;
            float b = 0.2F * sTime;

            if (entityLivingBaseIn.world.provider instanceof IGalacticraftWorldProvider)
            {
                float modifier = ((IGalacticraftWorldProvider) entityLivingBaseIn.world.provider).getThermalLevelModifier();

                if (modifier > 0)
                {
                    b = g;
                    g = r;
                }
                else if (modifier < 0)
                {
                    r = g;
                    g = b;
                }
            }

            GlStateManager.color(r, g, b, 0.4F * sTime);
            model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableLighting();
        }
    }

    @Override
    protected void initArmor()
    {
        this.modelLeggings = new ModelPlayerGC(0.55F, false);  //Head inside Oxygen Mask
        this.modelArmor = new ModelPlayerGC(0.05F, false);  //Chest and limbs close to skin
    }

    @Override
    public ModelBiped getModelFromSlot(EntityEquipmentSlot slotIn)
    {
        return slotIn == EntityEquipmentSlot.FEET ? this.modelLeggings : this.modelArmor;  //FEET is intended here, actually picks up the helmet (yes really)
    }
}

