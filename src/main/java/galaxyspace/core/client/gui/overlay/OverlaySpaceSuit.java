package galaxyspace.core.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.client.GSKeyHandlerClient;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.Overlay;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OverlaySpaceSuit extends Overlay {

	public static void renderSpaceSuitOverlay(EntityPlayer player)
	{
		if(player == null) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		int i = 0;	
		
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
						continue;
					}
				
				if(module.isEmpty()) return;
				
				ScaledResolution scaled = new ScaledResolution(mc);
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
								
				int xOffset = 5, yOffset = 0;
				
				if(GSConfigCore.spacesuit_pos.equals("up") || GSConfigCore.spacesuit_pos.equals("top")) 
					yOffset = 70;
				
				if(GSConfigCore.spacesuit_pos.equals("center")) 
					yOffset = scaled.getScaledHeight() / 2 + 20;
				
				if(GSConfigCore.spacesuit_pos.equals("down") || GSConfigCore.spacesuit_pos.equals("bottom")) 
					yOffset = scaled.getScaledHeight() - 20;
				
				String[] keys = new String[] { 
						/*Keyboard.getKeyName(GSKeyHandlerClient.toggleHelmet.getKeyCode()), 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleChest.getKeyCode()), 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleLegs.getKeyCode()), 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleBoots.getKeyCode())*/
						GSKeyHandlerClient.toggleHelmet.getDisplayName(),
						GSKeyHandlerClient.toggleChest.getDisplayName(),
						GSKeyHandlerClient.toggleLegs.getDisplayName(),
						GSKeyHandlerClient.toggleBoots.getDisplayName()
						
				};
				
				boolean horizontal = false;
				if(!module.isEmpty()) {
					
					EnumColor status = enable ? EnumColor.BRIGHT_GREEN : EnumColor.RED;
					if(stack.getItemDamage() >= stack.getMaxDamage()) 
						status = EnumColor.ORANGE;
					
					RenderHelper.enableStandardItemLighting();
					if(horizontal) {
						mc.getRenderItem().renderItemAndEffectIntoGUI(module, xOffset - (i * 20) + 60, yOffset - 6);
						mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, xOffset - (i * 20) + 60, yOffset - 6);
						RenderHelper.disableStandardItemLighting();
						mc.fontRenderer.drawString(status + "[" + keys[3 - player.inventory.armorInventory.indexOf(stack)] + "] ", xOffset - (i * 20) + 62, yOffset + 10, 0xFFFFFF);
					}
					else {
						mc.getRenderItem().renderItemAndEffectIntoGUI(module, xOffset, yOffset - (i * 20));
						mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, xOffset, yOffset - (i * 20));
						RenderHelper.disableStandardItemLighting();
						mc.fontRenderer.drawString(status + "[" + keys[3 - player.inventory.armorInventory.indexOf(stack)] + "] ", xOffset + 20, (yOffset + 5) - (i * 20), 0xFFFFFF);
						
					}
					
					
					//if(GSConfigCore.spacesuit_small_button)
					//{
						
						
						
					/*}
					else {
						String status = enable ? EnumColor.BRIGHT_GREEN + "[Enabled]" : EnumColor.RED + "[Disabled]";					
						if(stack.getItemDamage() >= stack.getMaxDamage()) status = EnumColor.ORANGE + "[No Energy]";
						mc.fontRenderer.drawString("[" + keys[3 - player.inventory.armorInventory.indexOf(stack)] + "] " + status, xOffset + 20, (yOffset + 5) - (i * 20), 0xFFFFFF);
					}*/
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
