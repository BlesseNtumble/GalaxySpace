package galaxyspace.core.client.gui.overlay;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.client.GSKeyHandlerClient;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.Overlay;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class OverlaySpaceSuit extends Overlay {

	private static RenderItem itemRender = RenderItem.getInstance();

	public static void renderSpaceSuitOverlay(EntityPlayer player)
	{		
		Minecraft mc = Minecraft.getMinecraft();
		
		if(player == null) 
			return;
		
		int i = 0;
		for(ItemStack stack : player.inventory.armorInventory)
		{
			if(stack == null || !(stack.getItem() instanceof ItemSpaceArmors)) 
				continue;
			
			if(stack != null && stack.getItem() instanceof ItemSpaceArmors)
			{				
				boolean hasModule = false;
				ItemStack module = null;
				for(ItemModule modules : GSUtils.getListModule())
				{
					if(((IModificationItem)stack.getItem()).getType(stack) != Module_Type.SPACESUIT) continue;
					
					if(stack.getTagCompound().hasKey(modules.getName()) && modules.isActiveModule()) {
						hasModule = stack.getTagCompound().getBoolean(modules.getName());	
						module = modules.getIcon();
					}
					continue;
				}
				
				ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				GL11.glPushMatrix();
				
				boolean enable = false;
				if(player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).isItemEqual(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceArmors.suit_buttons[0])) { enable = true;}
				};
				
				if(player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).isItemEqual(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceArmors.suit_buttons[1])) { enable = true;}
				};
				
				if(player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).isItemEqual(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceArmors.suit_buttons[2])) { enable = true;}
				};
				
				if(player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).isItemEqual(stack)) {
					if(stack.getTagCompound().getBoolean(ItemSpaceArmors.suit_buttons[3])) { enable = true;}
				};
								
				int xOffset = 5, yOffset = 0;
				
				if(GSConfigCore.spacesuit_pos.equals("up") || GSConfigCore.spacesuit_pos.equals("top")) 
					yOffset = 70;
				
				if(GSConfigCore.spacesuit_pos.equals("center")) 
					yOffset = scaled.getScaledHeight() / 2 + 20;
				
				if(GSConfigCore.spacesuit_pos.equals("down") || GSConfigCore.spacesuit_pos.equals("bottom")) 
					yOffset = scaled.getScaledHeight() - 20;
				
				String[] keys = new String[] { 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleHelmet.getKeyCode()), 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleChest.getKeyCode()), 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleLegs.getKeyCode()), 
						Keyboard.getKeyName(GSKeyHandlerClient.toggleBoots.getKeyCode())
				};
				
				if(module != null) {
					RenderHelper.enableStandardItemLighting();
					itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, module, xOffset, yOffset - (i * 20));
					RenderHelper.disableStandardItemLighting();
					String status = enable ? EnumColor.BRIGHT_GREEN + "[Enabled]" : EnumColor.RED + "[Disabled]";
					if(stack.getItemDamage() >= stack.getMaxDamage()) status = EnumColor.ORANGE + "[No Energy]";
					
					
					mc.fontRenderer.drawString("[" + keys[3 - (EntityLiving.getArmorPosition(stack) - 1)] + "] " + status, xOffset + 20, (yOffset + 5) - (i * 20), 0xFFFFFF);
							
						
				}
				else 
				{
					itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, xOffset, yOffset - (i * 20));
					//mc.fontRenderer.drawString(stack.getMaxDamage() - stack.getItemDamage() + "/" + stack.getMaxDamage(), xOffset + 20, scaled.getScaledHeight() - (yOffset - 5) - (i * 20), 0xFFFFFF);
				}
				
				itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, xOffset, yOffset - (i * 20));
								
				GL11.glPopMatrix();				
			}
			
			i++;
		}
	}
}
