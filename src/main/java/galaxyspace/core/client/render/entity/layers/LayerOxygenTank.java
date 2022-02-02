package galaxyspace.core.client.render.entity.layers;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.wrappers.PlayerGearData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerOxygenTank implements LayerRenderer<AbstractClientPlayer>{
	
	public ModelRenderer[] OxygenTanks = new ModelRenderer[2];
	private final RenderPlayer playerRenderer;

	public LayerOxygenTank()
    {
		this.playerRenderer = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		float scaleFactor = 0.0F;
		ModelPlayer modelPlayer = playerRenderer.getMainModel();
		
	    this.OxygenTanks[0] = new ModelRenderer(modelPlayer, 0, 0).setTextureSize(16, 16);
        this.OxygenTanks[0].addBox(-1.5F, 0F, -1.5F, 3, 7, 3, scaleFactor);
        this.OxygenTanks[0].setRotationPoint(2F, 2F, 3.8F);
        this.OxygenTanks[0].mirror = true;
        
        this.OxygenTanks[1] = new ModelRenderer(modelPlayer, 0, 0).setTextureSize(16, 16);
        this.OxygenTanks[1].addBox(-1.5F, 0F, -1.5F, 3, 7, 3, scaleFactor);
        this.OxygenTanks[1].setRotationPoint(-2F, 2F, 3.8F);
        this.OxygenTanks[1].mirror = true;
    }

	@Override
    public void doRenderLayer(AbstractClientPlayer player, float f5, float f6, float partialTicks, float f8, float f2, float f7, float scale)
    {
		if (!player.isInvisible())
        {
            PlayerGearData gearData = GalacticraftCore.proxy.getGearData(player);
            
            if (gearData != null)
            {
            		
				ModelPlayer.copyModelAngles(this.playerRenderer.getMainModel().bipedBody, this.OxygenTanks[0]);
				ModelPlayer.copyModelAngles(this.playerRenderer.getMainModel().bipedBody, this.OxygenTanks[1]);

				if (Minecraft.getMinecraft().player.isSneaking()) {
					this.OxygenTanks[0].rotationPointY = 2.0F;
					this.OxygenTanks[1].rotationPointY = 2.0F;
					
					this.OxygenTanks[0].rotationPointZ = 1.6F;
					this.OxygenTanks[1].rotationPointZ = 1.6F;
				} else {
					this.OxygenTanks[0].rotationPointY = 0.5F;
					this.OxygenTanks[1].rotationPointY = 0.5F;
					
					this.OxygenTanks[0].rotationPointZ = 0.5F;
					this.OxygenTanks[1].rotationPointZ = 0.5F;
				}
				
				GlStateManager.enableRescaleNormal();
				if(gearData.getLeftTank() > 5) 
				{
					
					String tex = getTankFromId(gearData.getLeftTank()).getTranslationKey().replace("item.", "");
	            	FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/" + tex +".png"));
	            
					GlStateManager.pushMatrix();
					GlStateManager.translate(0.175F, 0.0F, 0.0F);
					GlStateManager.translate(0.0F, 0.19F, 0.0F);
					GlStateManager.translate(0.0F, 0.0F, 0.2F);
					GlStateManager.scale(1.01F, 1.03F, 1.01F);
					if (Minecraft.getMinecraft().player.isSneaking()) { 
						GlStateManager.rotate(29, 1, 0, 0); 
						GlStateManager.translate(0.0F, 0.031F, -0.076F);
					}
					this.OxygenTanks[0].render(scale);
				
					GlStateManager.popMatrix();
				}
				
				if(gearData.getRightTank() > 5) 
				{
					String tex = getTankFromId(gearData.getRightTank()).getTranslationKey().replace("item.", "");
	            	FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/" + tex +".png"));
	            
					
					GlStateManager.pushMatrix();
					GlStateManager.translate(-0.175F, 0.0F, 0.0F);
					GlStateManager.translate(0.0F, 0.19F, 0.0F);
					GlStateManager.translate(0.0F, 0.0F, 0.2F);
					GlStateManager.scale(1.01F, 1.03F, 1.01F);
					if (Minecraft.getMinecraft().player.isSneaking()) { 
						GlStateManager.rotate(29, 1, 0, 0); 
						GlStateManager.translate(0.0F, 0.031F, -0.076F);
					}
					this.OxygenTanks[1].render(scale);
	
					GlStateManager.color(1.0F, 1.0F, 1.0F);
					GlStateManager.popMatrix();
				}
            }
        }
    }

	public ItemStack getTankFromId(int id)
	{
		switch(id)
		{
			case 40: return new ItemStack(GSItems.OXYGENTANK_TIER_4);
			case 41: return new ItemStack(GSItems.OXYGENTANK_TIER_5);
			case 42: return new ItemStack(GSItems.OXYGENTANK_TIER_6);
			case 43: return new ItemStack(GSItems.OXYGENTANK_TIER_EPP);
			default: return new ItemStack(GCItems.oxTankHeavy);
		}
	}
	 
	@Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}
