package galaxyspace.core.events;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProvider;
import asmodeuscore.core.astronomy.gui.book.ACGuiGuideBook;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IJetpackArmor;
import galaxyspace.core.client.gui.GSGuiMainMenu;
import galaxyspace.core.client.gui.GSGuiUpdate;
import galaxyspace.core.client.gui.book.pages.general.Page_ActualUpdate;
import galaxyspace.core.client.gui.overlay.OverlaySpaceSuit;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.util.GSThreadVersionCheck;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSClientTickHandler {

	public Minecraft mc = FMLClientHandler.instance().getClient();
	public Random rand;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiOpenEvent(GuiOpenEvent event)
	{
	
		if ((event.getGui() instanceof GuiMainMenu) && GSConfigCore.enableNewMenu)
			event.setGui(new GSGuiMainMenu());

		/*if (event.gui instanceof GuiRocketInventory && mc.thePlayer.ridingEntity instanceof EntityTier4Rocket)
		{
			event.gui = new GSGuiRocketInventory(mc.thePlayer.inventory, (EntityTier4Rocket) mc.thePlayer.ridingEntity, ((EntityTier4Rocket) mc.thePlayer.ridingEntity).getType());
		}*/		
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		WorldClient world = mc.world;

		if (event.phase == Phase.START)
		{
			if (!mc.isGamePaused())
			{					
					//CloudProviderVenus.cloudTickCounter += 3;
					//CloudProviderBarnardaC.cloudTickCounter += 1;
					//CloudProviderTCetiE.cloudTickCounter += 1;
			}	
			
			if(player != null)
			{
				//REMOVE AFTER FIX ON GC4!!
				/*
				if (!player.inventory.armorItemInSlot(3).isEmpty() && player.inventory.armorItemInSlot(3).getItem() instanceof ISensorGlassesArmor)
		        {
					ClientProxyCore.valueableBlocks.clear();

					for (int i = -4; i < 5; i++)
					{
						int x = MathHelper.floor(player.posX + i);
		                for (int j = -4; j < 5; j++)
		                {
		                	int y = MathHelper.floor(player.posY + j);
		                	for (int k = -4; k < 5; k++)
		                	{
		                		int z = MathHelper.floor(player.posZ + k);
								BlockPos pos = new BlockPos(x, y, z);

								IBlockState state = player.world.getBlockState(pos);
								final Block block = state.getBlock();

								if (block.getMaterial(state) != Material.AIR) {
									int metadata = block.getMetaFromState(state);
									boolean isDetectable = false;

									for (BlockMetaList blockMetaList : ClientProxyCore.detectableBlocks) {
										if (blockMetaList.getBlock() == block && blockMetaList.getMetaList().contains(metadata)) {
											isDetectable = true;
											break;
										}
									}

									if (isDetectable || (block instanceof IDetectableResource && ((IDetectableResource) block).isValueable(state))) {
										ClientProxyCore.valueableBlocks.add(new BlockVec3(x, y, z));
									}
								}
							}
						}
					}

					TileEntityOxygenSealer nearestSealer = TileEntityOxygenSealer.getNearestSealer(world, MathHelper.floor(player.posX), MathHelper.floor(player.posY), MathHelper.floor(player.posZ));
					if (nearestSealer != null && !nearestSealer.sealed) {
						ClientProxyCore.leakTrace = nearestSealer.getLeakTraceClient();
					} else {
						ClientProxyCore.leakTrace = null;
					}
				} else {
					ClientProxyCore.leakTrace = null;
				}*/
			}

		}

		if(event.phase == Phase.END)
		{
			if (Side.CLIENT != null) 
			{
				if(player != null && world != null)
				{
					if(GSThreadVersionCheck.newversion && mc.inGameHasFocus && GSConfigCore.enableCheckVersion)
					{
						GSThreadVersionCheck.newversion = false;
						
						//Minecraft.getMinecraft().addScheduledTask(() -> FMLClientHandler.instance().showGuiScreen(new GSGuiUpdate()));
						Minecraft.getMinecraft().addScheduledTask(() -> FMLClientHandler.instance().showGuiScreen(new ACGuiGuideBook(ACGuiGuideBook.Mode.TEXT, Book_Cateroies.GENERAL.getName(), new Page_ActualUpdate(true))));
						
					}
				}
			}				
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRender(RenderPlayerEvent.Post event) 
	{
		//GSColorRingClient.onPostRender(event);		
	}
		
	@SubscribeEvent(priority=EventPriority.LOW)
	@SideOnly(Side.CLIENT)
	public void onRenderTick(RenderTickEvent event)
	{
        final Minecraft minecraft = FMLClientHandler.instance().getClient();        
        final EntityPlayerSP player = minecraft.player;
        final EntityPlayerSP playerBaseClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);

        if (event.phase == Phase.END)
        {
        	if (player != null)
            {        		
        		/*
        		if(player.ridingEntity instanceof EntityTieredRocket)
        		{
        			OverlayRocketHelp.renderSpaceshipOverlay();
        		}*/
        		
        		if(minecraft.inGameHasFocus && !minecraft.gameSettings.hideGUI && GalaxySpace.debug)
        		{
        			
        			long t1 = player.world.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.world.provider).getDayLength() : 24000;
        			long time = player.world.getWorldTime() % (t1 > 0 ? t1 : 1);
        		
        			float temp = player.world.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.world.provider).getThermalLevelModifier() : 1.0F;
        			
        			String[] s = { 
        					GalaxySpace.NAME + " " + GalaxySpace.VERSION + " DEBUG Mode",
        					"MC Version: 1.12.2",
        					"Celestial Body: " + ((player.getEntityWorld().provider instanceof IGalacticraftWorldProvider) ? ((IGalacticraftWorldProvider)player.getEntityWorld().provider).getCelestialBody().getLocalizedName() : "Unnamed"),
        					"",
        					"Player Data:",
        					"X: " + (int) player.posX,
        					"Y: " + (int) player.posY,
        					"Z: " + (int) player.posZ,
        					"Current Item: " + (player.inventory.getCurrentItem() != null ? Item.REGISTRY.getNameForObject(player.inventory.getCurrentItem().getItem()) + ":" +  player.inventory.getCurrentItem().getItemDamage(): "None"),
        					
        					"",
        					"World Data:",
        					"Dimension: " + player.world.provider.getDimensionType().getName() + " (ID: " + player.world.provider.getDimensionType().getId() + ")",
        					"Temperature: " + temp + "F",
        					"Biome: " + player.world.getBiomeForCoordsBody(new BlockPos((int)player.posX, (int)player.posY, (int)player.posZ)).getBiomeName(),
        					"Current Time: " + time + " | Day Length: " + (player.world.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.world.provider).getDayLength() : "24000") + " | Total Time: " + player.world.getWorldTime(),
        					"Moon Phase: " + player.world.getMoonPhase(),
        					"",
        					"Is Galacticraft Provider: " + ((player.getEntityWorld().provider instanceof IGalacticraftWorldProvider) ? "Yes" : "No"),
        					"Is Advance Space Provider: " + ((player.getEntityWorld().provider instanceof IAdvancedSpace) ? "Yes" : "No"), 
        					"Is Enable Oregen: " + (GSConfigCore.enableOresGeneration == true ? "Yes" : "No"),
        					"Is World Engine Provider: " +  ((player.getEntityWorld().provider instanceof WE_WorldProvider) == true ? "Yes" : "No")
        			};
        			
        			int k = 3;
        			if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) k = s.length;
        			GL11.glPushMatrix();
        			//GlStateManager.disableLighting();
        			for(int i = 0; i < k; i++)
        				minecraft.fontRenderer.drawStringWithShadow(s[i], 10, 28 + i*10, ColorUtil.to32BitColor(255, 255, 255, 255));
        			//GlStateManager.enableLighting();
        			GL11.glPopMatrix();

        		}
        		
        		if(minecraft.inGameHasFocus && !minecraft.gameSettings.hideGUI && GSConfigCore.enableSpaceSuitHUD)
        			OverlaySpaceSuit.renderSpaceSuitOverlay(playerBaseClient);
        		
        		GlStateManager.disableLighting();
            }
        }
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(PlayerTickEvent e) {
		if (e.phase == Phase.START && e.side == Side.CLIENT) {
			ItemStack chest = e.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			
			if(chest != null && chest.getItem() instanceof IJetpackArmor) 
				if( ((IJetpackArmor)chest.getItem()).canFly(chest, e.player))				
					parseKeybindings(e.player);
		}
	}
	
	private void parseKeybindings(EntityPlayer player) {

		float power = 1.0F;
		int worldHeight1 = player.getEntityWorld().getHeight();
		int maxFlightHeight1 = (int) ((float)worldHeight1 / 1.28F);
		double y = player.posY;
		
		if (y > (double) (maxFlightHeight1 - 25)) {
			if (y > (double) maxFlightHeight1) {
				y = (double) maxFlightHeight1;
			}

			power = (float) ((double) power * (((double) maxFlightHeight1 - y) / 25.0D));
		}

		
		if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
			//player.motionY += urmot;
			player.motionY = Math.min(player.motionY + (double) (power * 0.15F), 0.6000000238418579D);
			
			GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_CHANGE_FLIGHT_STATE, player.world, new Object[] {true}));
		}
		else 
			GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_CHANGE_FLIGHT_STATE, player.world, new Object[] {false}));

	}
	
}
