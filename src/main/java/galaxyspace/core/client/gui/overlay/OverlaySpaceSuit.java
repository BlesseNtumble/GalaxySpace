package galaxyspace.core.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.gui.overlay.OverlayDetectors;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.client.GSKeyHandlerClient;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.Overlay;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStatsClient;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OverlaySpaceSuit extends Overlay {

	public static void renderSpaceSuitOverlay(EntityPlayer player)
	{
		if(player == null) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaled = new ScaledResolution(mc);
		final EntityPlayerSP playerBaseClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);
		final GCPlayerStatsClient stats = GCPlayerStatsClient.get(playerBaseClient);		
		
		int i = 0;	
		/*
		if(player.world.provider instanceof IGalacticraftWorldProvider)
		{
			IGalacticraftWorldProvider prov = (IGalacticraftWorldProvider) player.world.provider;
			float temp = prov.getCelestialBody().atmosphere.thermalLevel() == 0.0F ? (prov.getThermalLevelModifier() * BodiesHelper.getDefaultDergrees) + BodiesHelper.getDefaultDergrees : prov.getThermalLevelModifier() * BodiesHelper.getDefaultDergrees;
			String str = GalaxySpace.debug ? "Thermal Level: " + String.format("%.4f", prov.getThermalLevelModifier()) + " | " + String.format("%.2f", temp) + " C" : "T: " + String.format("%.1f", temp) + " C";
			mc.fontRenderer.drawString(str, scaled.getScaledWidth() - (str.length() * 5) - 12, 75, 0xFFFFFF);
		}
		*/
		boolean hasSuit = false;
		for(ItemStack stack : player.inventory.armorInventory)
		{
			if(!stack.isEmpty() && stack.getItem() instanceof ItemSpaceSuit)
			{	
				hasSuit = true;
				break;
			}
		}
		
		String[] keys = new String[] { 
				GSKeyHandlerClient.toggleHelmet.getDisplayName(),
				GSKeyHandlerClient.toggleChest.getDisplayName(),
				GSKeyHandlerClient.toggleLegs.getDisplayName(),
				GSKeyHandlerClient.toggleBoots.getDisplayName()						
			};
		
		int max = 0;
		for(String key : keys)		
			if(key.length() > max) 
				max = key.length();
		
		int xOffset = -35, yOffset = scaled.getScaledHeight() - 140;
		final ResourceLocation guiTexture = new ResourceLocation(AsmodeusCore.ASSET_PREFIX, "textures/gui/gui.png");
		
		if(hasSuit) {
			//mc.entityRenderer.setupOverlayRendering();
			GlStateManager.enableBlend();
	        GlStateManager.depthMask(false);
	        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	
	        GlStateManager.disableLighting();
	        GlStateManager.enableDepth();
	        GlStateManager.enableAlpha();
	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(guiTexture);
	        OverlayDetectors.drawTexturedModalRect((double)xOffset + 35, (double)yOffset - 78, 50 + (max * mc.fontRenderer.FONT_HEIGHT), 86, 0, 43, 30, 65, false, false, 256, 256);
	        GlStateManager.color(1, 1, 1);
	    	GlStateManager.disableAlpha();
		    GlStateManager.disableBlend();
		}
	    
		for(ItemStack stack : player.inventory.armorInventory)
		{
			if(stack.isEmpty() || !(stack.getItem() instanceof ItemSpaceSuit)) 
				continue;
			
			if(!stack.isEmpty() && stack.getItem() instanceof ItemSpaceSuit)
			{				
				boolean hasModule = false;
				ItemStack module = ItemStack.EMPTY;
				
				if(((IModificationItem)stack.getItem()).getAvailableModules() != null)
					for(ItemModule modules : ((IModificationItem)stack.getItem()).getAvailableModules())
					{
						if(stack.getTagCompound().hasKey(modules.getName()) && modules.isActiveModule()) {
							
							hasModule = stack.getTagCompound().getBoolean(modules.getName());	
							module = modules.getIcon();
						}						
					}
				
				if(module.isEmpty()) continue; 
				
				
				GL11.glPushMatrix();
				
				boolean enable = false;
				if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).equals(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceSuit.suit_buttons[0])) { enable = true;}
				};
				
				if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).equals(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceSuit.suit_buttons[1])) { enable = true;}
				};
				
				if(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).equals(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceSuit.suit_buttons[2])) { enable = true;}
				};
				
				if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET).equals(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceSuit.suit_buttons[3])) { enable = true;}
				};
								
				
				
				if(GSConfigCore.spacesuit_pos.equals("up") || GSConfigCore.spacesuit_pos.equals("top")) 
					yOffset = 70;
				
				if(GSConfigCore.spacesuit_pos.equals("center")) 
					yOffset = scaled.getScaledHeight() / 2 + 20;
				
				if(GSConfigCore.spacesuit_pos.equals("down") || GSConfigCore.spacesuit_pos.equals("bottom")) 
					yOffset = scaled.getScaledHeight() - 140;
				
				
				String key = keys[3 - player.inventory.armorInventory.indexOf(stack)];
				
				
				boolean horizontal = false;
				if(!module.isEmpty()) {
					
					EnumColor status = enable ? EnumColor.BRIGHT_GREEN : EnumColor.RED;
					if(stack.getItemDamage() >= stack.getMaxDamage()) 
						status = EnumColor.ORANGE;
					
					
					RenderHelper.enableStandardItemLighting();
					if(horizontal) {
						
						
						mc.getRenderItem().renderItemAndEffectIntoGUI(module, xOffset - (i * 35) + 160, yOffset - 6);
						mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, xOffset - (i * 35) + 160, yOffset - 6);
						RenderHelper.disableStandardItemLighting();
						mc.fontRenderer.drawSplitString(status + "[" + key + "] ", xOffset - (i * 35) + 162 - ((int)(key.length() / 2) * 6), yOffset + 10, key.length() * 20, 0xFFFFFF);
					}
					else {
								
						mc.getRenderItem().renderItemIntoGUI(module, xOffset + 40, yOffset - (i * 20) - 15);
						if(status == EnumColor.ORANGE)
						{
							 FMLClientHandler.instance().getClient().renderEngine.bindTexture(guiTexture);
							 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
							 OverlayDetectors.drawTexturedModalRect((float) xOffset + 50, (float)yOffset - 5 - (i * 20), 15, 15, 81, 14, 10, 10, false, false, 256, 256);
							   
						}
						else						
							mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, xOffset + 40, yOffset - (i * 20) - 15);
						
						RenderHelper.disableStandardItemLighting();
						mc.fontRenderer.drawString(status + "[" + key + "] ", xOffset + 60, yOffset - (i * 20) - 10, 0xFFFFFF);
						
					}

				}
				else 
				{
					if(horizontal) 
						mc.getRenderItem().renderItemAndEffectIntoGUI(stack, xOffset - (i * 20) + 60, yOffset - 10);					
					else
						mc.getRenderItem().renderItemAndEffectIntoGUI(stack, xOffset, yOffset - (i * 20));
					//mc.fontRenderer.drawString(stack.getMaxDamage() - stack.getItemDamage() + "/" + stack.getMaxDamage(), xOffset + 20, scaled.getScaledHeight() - (yOffset - 5) - (i * 20), 0xFFFFFF);
				}
				
								
				GL11.glPopMatrix();				
			}
			
			i++;
		}
	}
}
