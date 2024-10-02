package galaxyspace.core.client;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.gameevent.TickEvent.Type;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import micdoodle8.mods.galacticraft.core.client.KeyHandler;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;

public class GSKeyHandlerClient extends KeyHandler{

	public static KeyBinding toggleHelmet;
	public static KeyBinding toggleChest;
	public static KeyBinding toggleLegs;
	public static KeyBinding toggleBoots;
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	static
    {
		toggleHelmet = new KeyBinding(GCCoreUtil.translate("keybind.togglehelmet.name"), GSConfigCore.keyOverrideToggleHelmetI == 0 ? Keyboard.KEY_F : GSConfigCore.keyOverrideToggleHelmetI, GalaxySpace.MODID);
		toggleChest  = new KeyBinding(GCCoreUtil.translate("keybind.togglechest.name"), GSConfigCore.keyOverrideToggleChestI == 0 ? Keyboard.KEY_G : GSConfigCore.keyOverrideToggleChestI, GalaxySpace.MODID);
		toggleLegs  = new KeyBinding(GCCoreUtil.translate("keybind.togglelegs.name"), GSConfigCore.keyOverrideToggleLegsI == 0 ? Keyboard.KEY_H : GSConfigCore.keyOverrideToggleLegsI, GalaxySpace.MODID);
		toggleBoots  = new KeyBinding(GCCoreUtil.translate("keybind.toggleboots.name"), GSConfigCore.keyOverrideToggleBootsI == 0 ? Keyboard.KEY_J : GSConfigCore.keyOverrideToggleBootsI, GalaxySpace.MODID);
/*
		toggleHelmetGoggles = new KeyBinding(GCCoreUtil.translate("keybind.togglehelmet.name"), GSConfigCore.keyOverrideToggleHelmetGogglesI == 0 ? Keyboard.KEY_F : GSConfigCore.keyOverrideToggleHelmetGogglesI, GalaxySpace.MODID);
		toggleJetpack  = new KeyBinding(GCCoreUtil.translate("keybind.togglejetpack.name"), GSConfigCore.keyOverrideToggleJetpackI == 0 ? Keyboard.KEY_G : GSConfigCore.keyOverrideToggleJetpackI, GalaxySpace.MODID);
   */ }
	
	public GSKeyHandlerClient()
    {
		super(new KeyBinding[] { GSKeyHandlerClient.toggleHelmet, GSKeyHandlerClient.toggleChest, GSKeyHandlerClient.toggleLegs, GSKeyHandlerClient.toggleBoots }, new boolean[] { false, false, false, false }, GSKeyHandlerClient.getVanillaKeyBindings(), new boolean[] {});
    }

	private static KeyBinding[] getVanillaKeyBindings()
    {
		return new KeyBinding[] {};
    }
	
	@Override
	public void keyDown(Type types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		
		if (GSKeyHandlerClient.mc.thePlayer != null && tickEnd && Minecraft.getMinecraft().inGameHasFocus)
        {
			EntityClientPlayerMP playerBase = PlayerUtil.getPlayerBaseClientFromPlayer(GSKeyHandlerClient.mc.thePlayer, false);

			if (kb.getKeyCode() == GSKeyHandlerClient.toggleHelmet.getKeyCode())
	        {
				GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_NBT_ITEM_IN_ARMOR, new Object[] {3, ItemSpaceArmors.suit_buttons[0]}));	          
	        }
			
			if (kb.getKeyCode() == GSKeyHandlerClient.toggleChest.getKeyCode())
	        {
				GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_NBT_ITEM_IN_ARMOR, new Object[] {2, ItemSpaceArmors.suit_buttons[1]}));	   	
	        }
			
			if (kb.getKeyCode() == GSKeyHandlerClient.toggleLegs.getKeyCode())
	        {
				GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_NBT_ITEM_IN_ARMOR, new Object[] {1, ItemSpaceArmors.suit_buttons[2]}));	   
	        }
			
			if (kb.getKeyCode() == GSKeyHandlerClient.toggleBoots.getKeyCode())
	        {
				GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_NBT_ITEM_IN_ARMOR, new Object[] {0, ItemSpaceArmors.suit_buttons[3]}));	   
	        }
        }
		
	}

	@Override
	public void keyUp(Type types, KeyBinding kb, boolean tickEnd) {
		
	}

}
