package galaxyspace.core.events;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.item.IJetpack;
import galaxyspace.core.client.gui.GSGuiMainMenu;
import galaxyspace.core.client.gui.GSGuiUpdate;
import galaxyspace.core.client.gui.overlay.OverlayDetectors;
import galaxyspace.core.client.gui.overlay.OverlaySpaceSuit;
import galaxyspace.core.client.gui.screen.GSGuiCelestialSelection;
import galaxyspace.core.client.models.ModelOxygenTank;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.handler.GSColorRingClient;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSAttributePlayer;
import galaxyspace.core.util.GSThreadVersionCheck;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.WorldProviderProximaB;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.WorldProviderBarnardaC_WE;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan_WE;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.sky.CloudProviderTitan;
import galaxyspace.systems.SolarSystem.planets.ceres.gui.GSGuiRocketInventory;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier4Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.WorldProviderVenus;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.sky.CloudProviderVenus;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiRocketInventory;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.tick.KeyHandlerClient;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.core.wrappers.BlockMetaList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.event.terraingen.BiomeEvent;

public class GSClientTickHandler {

	
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	public Random rand;
	//boolean attack = true;
	
	private static boolean toggleFlight;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiOpenEvent(GuiOpenEvent event)
	{		
		Minecraft mc = Minecraft.getMinecraft();
	
		if ((event.gui instanceof GuiMainMenu) && GSConfigCore.enableNewMenu)
			event.gui = new GSGuiMainMenu();

		if (event.gui instanceof GuiRocketInventory && mc.thePlayer.ridingEntity instanceof EntityTier4Rocket)
		{
			event.gui = new GSGuiRocketInventory(mc.thePlayer.inventory, (EntityTier4Rocket) mc.thePlayer.ridingEntity, ((EntityTier4Rocket) mc.thePlayer.ridingEntity).getType());
		}
		
		if (event.gui instanceof GuiCelestialSelection && GSConfigCore.enableNewGalaxyMap)
		{
			if(mc.gameSettings.isKeyDown(KeyHandlerClient.galaxyMap)) event.gui = new GSGuiCelestialSelection(true, null, null);
		}
	}
	/*
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void biomeColorGrassBlock(BiomeEvent.BiomeColor event)
	{
		//event.newColor = 0x89AC76;
		/*World world = FMLClientHandler.instance().getWorldClient();
		if(world != null)
		{
			//GalaxySpace.debug(world. + "");
			for(Entry<ChunkCoordIntPair, Ticket> chunks : world.getPersistentChunks().entries())
			{
				ChunkPosition pos = chunks.getKey().func_151349_a(0);
				
				int chunkX = pos.chunkPosX;
				int chunkZ = pos.chunkPosZ;
				
				for(int x = 0; x < 16; x++)
					for(int z = 0; z < 16; z++)
					{
						WE_Biome biome = WE_Biome.getBiomeAt(x, z);
						
						event.newColor = biome.biomeGrassColor;
						
					}
			}
			
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void biomeColorWaterBlock(BiomeEvent.GetWaterColor event)
	{		
		World world = FMLClientHandler.instance().getWorldClient();
		if(world != null)
		{
			if(world.provider instanceof WorldProviderProximaB)
				event.newColor = 0xEEDD44;
			if(world.provider instanceof WorldProviderBarnardaC_WE)
				event.newColor = 0x11FF66;
		}
	}
	*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		WorldClient world = mc.theWorld;

			if (event.phase == Phase.START)
			{
				if (!mc.isGamePaused())
				{					
					CloudProviderVenus.cloudTickCounter += 3;
					CloudProviderTitan.cloudTickCounter += 1;
					//CloudProviderBarnardaC.cloudTickCounter += 1;
					//CloudProviderTCetiE.cloudTickCounter += 1;
				}				
				
				if (player != null && player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() instanceof ItemSpaceArmors)
                {
                    ClientProxyCore.valueableBlocks.clear();

                    for (int i = -4; i < 5; i++)
                    {
                        int x = MathHelper.floor_double(player.posX + i);
                        for (int j = -4; j < 5; j++)
                        {
                            int y = MathHelper.floor_double(player.posY + j);
                            for (int k = -4; k < 5; k++)
                            {
                                int z = MathHelper.floor_double(player.posZ + k);

                                final Block block = player.worldObj.getBlock(x, y, z);

                                if (block.getMaterial() != Material.air)
                                {
                                    int metadata = world.getBlockMetadata(x, y, z);
                                    boolean isDetectable = false;

                                    for (BlockMetaList blockMetaList : ClientProxyCore.detectableBlocks)
                                    {
                                        if (blockMetaList.getBlock() == block && blockMetaList.getMetaList().contains(metadata))
                                        {
                                            isDetectable = true;
                                            break;
                                        }
                                    }

                                    if (isDetectable || (block instanceof IDetectableResource && ((IDetectableResource) block).isValueable(metadata)))
                                    {
                                        ClientProxyCore.valueableBlocks.add(new BlockVec3(x, y, z));
                                    }
                                }
                            }
                        }
                    }
                }
			}
			
			if (world != null && world.provider instanceof WorldProviderVenus)
	        {
	            if(world.getWorldInfo().isRaining()) world.setRainStrength(1.5F);
	        }
			if (world != null && (world.provider instanceof WorldProviderTitan || world.provider instanceof WorldProviderTitan_WE))
	        {
	            if(world.getWorldInfo().isRaining()) world.setRainStrength(1.0F);
	        }
					
			if(event.phase == Phase.END)
			{		
				
				if (Side.CLIENT != null) 
				{
					//AnimTickHandler.onClientTick(event);
					
					
					if(player != null && world != null)
					{	
						ItemStack chest = player.getCurrentArmor(2);
						if (chest != null && chest.getItem() instanceof IJetpack && ((IJetpack) chest.getItem()).canFly(chest, mc.thePlayer) && ((IJetpack) chest.getItem()).isActivated(chest) && !player.onGround) {
							GalaxySpace.proxy.showJetpackParticles(mc.theWorld, player, ((IJetpack) chest.getItem()).getFireStreams(chest));
						}
						
						//tickEnd();
						if(GSConfigCore.enableCheckVersion && GSThreadVersionCheck.newversion && mc.inGameHasFocus)
						{
							GSThreadVersionCheck.newversion = false;
							GSGuiUpdate gui = new GSGuiUpdate();
							FMLClientHandler.instance().showGuiScreen(gui);
						}
						
						/*if(player.getCommandSenderName().equals("BlesseNtumble") && !mc.isGamePaused())
						{
							 world.spawnParticle("flame", player.posX + world.rand.nextDouble() - 0.5D * (double)player.width, player.posY + (world.rand.nextDouble() - 0.5D) * (double)player.height, player.posZ + (world.rand.nextDouble() - 0.5D) * (double)player.width, 0.0D, 0.0D, 0.0D);
							 GalaxySpace.proxy.spawnParticle("greenportal", new Vector3(player.posX + (world.rand.nextDouble() - 0.5D) * (double)player.width, player.posY + (world.rand.nextDouble() - 0.5D) * (double)player.height - 0.25D, player.posZ + (world.rand.nextDouble() - 0.5D) * (double)player.width), new Vector3((world.rand.nextDouble() - 0.5D) * 2.0D, -world.rand.nextDouble(), (world.rand.nextDouble() - 0.5D) * 2.0D), new Object [] { });
	
						}*/
						
					}
					
				}
			
			}

	}

	private static void tickEnd() {
		//if ((mc.thePlayer == null) || (mc.theWorld == null)) return;
		
		if (!mc.isGamePaused()) {
			if (mc.theWorld.playerEntities.isEmpty()) mc.theWorld.playerEntities.add(mc.thePlayer);
			Iterator<EntityPlayer> itr = mc.theWorld.playerEntities.iterator();
			while (itr.hasNext()) {
				EntityPlayer player = itr.next();
				if ((player == null) || (player.dimension != mc.thePlayer.dimension)) {
					itr.remove();
				} else {
					ItemStack chest = player.getCurrentArmor(2);
					if (chest != null && chest.getItem() instanceof IJetpack && ((IJetpack) chest.getItem()).canFly(chest, mc.thePlayer) && ((IJetpack) chest.getItem()).isActivated(chest) && !player.onGround) {
						GalaxySpace.proxy.showJetpackParticles(mc.theWorld, player, ((IJetpack) chest.getItem()).getFireStreams(chest));
					} else itr.remove();
				}
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRender(RenderPlayerEvent.Specials.Post event) 
	{
		GSColorRingClient.onPostRender(event);

		EntityPlayer player = event.entityPlayer;
		if (player != null) {	

/*
			GL11.glPushMatrix();
			GL11.glScalef(0.4F, 0.4F, 0.4F);
			GL11.glTranslatef(-0.5F, -2.8F, 0);
			
			IIcon icon = GSItems.LeadBattery.getIconFromDamage(0);
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
			
			float f = icon.getMinU();
			float f1 = icon.getMaxU();
			float f2 = icon.getMinV();
			float f3 = icon.getMaxV();
			
			ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 2f / 32f);
			GL11.glPopMatrix();
	*/
    
			ModelOxygenTank tank = new ModelOxygenTank();
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_BLEND);
			
			if (player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).getBaseValue() > 0) {				
				double tex = player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).getBaseValue() + 1;
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/oxTank" + (int) tex +".png"));
				tank.renderLeft((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
				
			}
			if (player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).getBaseValue() > 0) {
				
				
				double tex = player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).getBaseValue() + 1;
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/oxTank" + (int) tex +".png"));
				tank.renderRight((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
			}
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
	
	/*	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderLiving(RenderLivingEvent event)
	{

	}
	*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderTick(RenderTickEvent event)
	{
        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final EntityPlayerSP player = minecraft.thePlayer;
        int level = 0;
        if (event.phase == Phase.END)
        {
        	if (player != null)
            {
    	        
        		if(player.worldObj.provider instanceof IGalacticraftWorldProvider && OxygenUtil.shouldDisplayTankGui(minecraft.currentScreen))
        		{        			         		
		        	if(player.worldObj.provider instanceof IAdvancedSpace)
		        	{
		        		IAdvancedSpace provider = (IAdvancedSpace) player.worldObj.provider;
			        	level = Math.round(provider.AtmosphericPressure() / 4);
			        	
			        }   
		        	OverlayDetectors.renderPressureIndicator(level, this.getInvalidLevel(1), !ConfigManagerCore.oxygenIndicatorLeft, !ConfigManagerCore.oxygenIndicatorBottom);			                
		        	OverlayDetectors.renderRadiationIndicator((int)player.getEntityAttribute(GSAttributePlayer.RADIATION_LVL).getAttributeValue(), this.getInvalidLevel(2), !ConfigManagerCore.oxygenIndicatorLeft, !ConfigManagerCore.oxygenIndicatorBottom);			                
		        }
        		/*
        		if(player.ridingEntity instanceof EntityTieredRocket)
        		{
        			OverlayRocketHelp.renderSpaceshipOverlay();
        		}*/
        		if(GSConfigCore.enableSpaceSuitHUD)
        		{
        			/*
	        		ItemStack helmet = player.inventory.armorItemInSlot(3);
	        		if (minecraft.inGameHasFocus && helmet != null && helmet.getItem() == GSItems.SpacesuitHelmet && helmet.stackTagCompound.getBoolean(ItemSpaceArmors.sensor) && helmet.getItemDamage() != helmet.getMaxDamage()) {
	        		
	        			boolean t = false;
	        			
	        			if(player.getEntityAttribute(GSAttributePlayer.TOGGLE_HELMET) != null)
	        				t = player.getEntityAttribute(GSAttributePlayer.TOGGLE_HELMET).getBaseValue() == 1;
	        			
	        			Minecraft.getMinecraft().fontRenderer.drawString(GCCoreUtil.translate("gui.togglehelmet") + " " +  (t ? GCCoreUtil.translate("gui.sensor.advancedon") : GCCoreUtil.translate("gui.sensor.advancedoff")), 10, 4, 0x03b88f);
	        			
	        		}
	        			
	        		ItemStack plate = player.inventory.armorItemInSlot(2);
	        		if (minecraft.inGameHasFocus && plate != null && plate.getItem() == GSItems.SpacesuitPlate && plate.stackTagCompound.getBoolean(ItemSpaceArmors.jetpack) && plate.getItemDamage() != plate.getMaxDamage()) {
	        			
	        			boolean j = false;
	        			if(player.getEntityAttribute(GSAttributePlayer.TOGGLE_JETPACK) != null)
	        				j = player.getEntityAttribute(GSAttributePlayer.TOGGLE_JETPACK).getBaseValue() == 1;
	        			
	        			Minecraft.getMinecraft().fontRenderer.drawString(GCCoreUtil.translate("gui.togglejetpack") +  (j ? GCCoreUtil.translate("gui.sensor.advancedon") : GCCoreUtil.translate("gui.sensor.advancedoff")), 10, 4*4, 0x03b88f);
	        			
	        		}*/
        		}
        		if(minecraft.inGameHasFocus && GalaxySpace.debug)
        		{
        			boolean isSpace = player.worldObj.provider instanceof IGalacticraftWorldProvider;
        			long t1 = player.worldObj.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.worldObj.provider).getDayLength() : 24000;
        			long time = player.worldObj.getWorldTime() % (t1 > 0 ? t1 : 1);
        		
        			String[] s = { 
        					GalaxySpace.MODID + " " + GalaxySpace.VERSION + " DEBUG Mode",
        					"MC Version: 1.7.10",
        					"Celestial Body: " + ((player.getEntityWorld().provider instanceof IGalacticraftWorldProvider) ? ((IGalacticraftWorldProvider)player.getEntityWorld().provider).getCelestialBody().getLocalizedName() : "Unnamed"),
        					"",
        					"Player Data:",
        					"Username: " + player.getCommandSenderName(),
        					"X: " + (int) player.posX,
        					"Y: " + (int) player.posY,
        					"Z: " + (int) player.posZ,
        					"Current Item: " + (player.inventory.getCurrentItem() != null ? GameData.getItemRegistry().getNameForObject(player.inventory.getCurrentItem().getItem()) + ":" +  player.inventory.getCurrentItem().getItemDamage(): "None"),
        					"",
        					"World Data:",
        					"Dimension: " + player.worldObj.provider.getDimensionName() + " (ID: " + player.worldObj.provider.dimensionId + ")",
        					"Gravity: " + (isSpace ? ((IGalacticraftWorldProvider) player.worldObj.provider).getGravity() : "0.085F"),
        					"Biome: " + player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ).biomeName + " (ID: " + player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ).biomeID + ")",
        					"Current Time: " + time + " | Total Time: " + (player.worldObj.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.worldObj.provider).getDayLength() : "24000"),
        					"Moon Phase: " + (player.worldObj.provider instanceof WorldProviderAdvancedSpace ? ((WorldProviderAdvancedSpace)player.worldObj.provider).getMoonPhase(player.worldObj.getWorldTime()) : player.worldObj.provider.getMoonPhase(player.worldObj.getWorldTime())),
        					"",
        					"Is Galacticraft Provider: " + ((player.getEntityWorld().provider instanceof IGalacticraftWorldProvider) ? "Yes" : "No"),
        					"Is Advance Space Provider: " + ((player.getEntityWorld().provider instanceof IAdvancedSpace) ? "Yes" : "No"),
        					"Is Enable Oregen: " + (GSConfigCore.enableOresGeneration == true ? "Yes" : "No"),
        					"Is Enable World Engine: " +  (GSConfigCore.enableWorldEngine == true ? "Yes" : "No")
        			};
        			
        			int k = 3;
        			if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) k = s.length;
        			for(int i = 0; i < k; i++)
        				minecraft.fontRenderer.drawStringWithShadow(s[i], 10, 28 + i*10, ColorUtil.to32BitColor(255, 255, 255, 255));
            			
        		}
        		
        		if(minecraft.inGameHasFocus && !minecraft.gameSettings.hideGUI && GSConfigCore.enableSpaceSuitHUD)
        			OverlaySpaceSuit.renderSpaceSuitOverlay(player);
            }
        	
        	GL11.glDisable(GL11.GL_LIGHTING);
        }
	}
	
	private boolean getInvalidLevel(int mode)
	{
		final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final EntityPlayer player = minecraft.thePlayer;
        
        if(mode == 1)
        {
	        int level;
	        
	        if(player.worldObj.provider instanceof IAdvancedSpace)
	    	{
	        	IAdvancedSpace provider = (IAdvancedSpace) player.worldObj.provider;
	        	level = provider.AtmosphericPressure();
	    	}
	        else level = 0;
	        
			if(!player.capabilities.isCreativeMode && level > 25 && !(getAtmoArmor(player) || GSEventHandler.getProtectArmor(player)) && !GSEventHandler.inGravityZone(player.worldObj, player, true)) return true;	
        }
        else if(mode == 2)
        {
        	if((int)player.getEntityAttribute(GSAttributePlayer.RADIATION_LVL).getAttributeValue() > 45) return true;	
        }
		return false;
	}
	
	public static boolean getAtmoArmor(EntityPlayer player)
	{
		boolean armor1 = player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == GSItems.SpacesuitHelmet;
		boolean armor2 = player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == GSItems.SpacesuitPlate;
		boolean armor3 = player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == GSItems.SpacesuitLeg;
		boolean armor4 = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == GSItems.SpacesuitBoots;

		return armor1 && armor2 && armor3 && armor4;
	}

	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(PlayerTickEvent e) {
		if (e.phase == TickEvent.Phase.START && e.side == Side.CLIENT) {
			ItemStack chest = e.player.getCurrentArmor(2);
			
			if(chest != null && chest.getItem() instanceof IJetpack) 
				if( ((IJetpack)chest.getItem()).canFly(chest, e.player))				
					parseKeybindings(e.player);
		}
	}
	
	double urmot = 0.015;
	double ufmot = -0.015;
	
	private void parseKeybindings(EntityPlayer player) {
		/*
		if (GSKeyHandlerClient.toggleJetpack.isPressed() && !toggleFlight) {
			toggleFlight = true;
			GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_ENABLE_FLIGHT));
		} else if (toggleFlight) {
			toggleFlight = false;
		}
		*/
		
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

		
		if (Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed()) {
			//player.motionY += urmot;
			player.motionY = Math.min(player.motionY + (double) (power * 0.15F), 0.6000000238418579D);
			
			GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_CHANGE_FLIGHT_STATE, new Object[] {true}));
		}
		else GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_CHANGE_FLIGHT_STATE, new Object[] {false}));
		/*
		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed()) {
			player.motionY += ufmot;
		}*/
	}
}
