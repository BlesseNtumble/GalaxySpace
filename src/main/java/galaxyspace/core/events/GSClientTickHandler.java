package galaxyspace.core.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.astronomy.gui.book.ACGuiGuideBook;
import asmodeuscore.core.handler.ColorBlockHandler;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import asmodeuscore.core.utils.Utils;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.WE_PerlinNoise;
import asmodeuscore.core.utils.worldengine.WE_WorldProvider;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IJetpackArmor;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.client.gui.GSGuiMainMenu;
import galaxyspace.core.client.gui.book.pages.general.Page_ActualUpdate;
import galaxyspace.core.client.gui.overlay.OverlaySpaceSuit;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.util.GSThreadVersionCheck;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemGeologicalScanner;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemMatterManipulator;
import micdoodle8.mods.galacticraft.api.event.ZeroGravityEvent;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
	public static Map<IBlockState, String> blocks = new HashMap<IBlockState, String>();
	public static int ticks;
	
	
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
	public void onToolTip(ItemTooltipEvent e) {
		if(e.getItemStack().getItem() instanceof IModificationItem)
		{
			IModificationItem mod = (IModificationItem) e.getItemStack().getItem();
			if(mod.getType(e.getItemStack()) != null && mod.getAvailableModules() != null) {
				//e.getToolTip().add("");
				e.getToolTip().add(1, EnumColor.AQUA + GCCoreUtil.translate("gui.module.caninstall"));
			}
		}
		
		if(e.getItemStack().isItemEqual(new ItemStack(MarsItems.schematic, 1, 0)))
		{
			if(GSConfigCore.enableAdvancedRocketCraft) {
				//e.getToolTip().add("");
				e.getToolTip().add(1, EnumColor.RED + "Disabled. See new recipe in JEI/NEI!");
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		WorldClient world = mc.world;

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
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onLivingRender(RenderLivingEvent.Post event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			
			ItemStack mainhand = player.getHeldItemMainhand();
			ItemStack offhand = player.getHeldItemOffhand();
			
			if(!mainhand.isEmpty() && mainhand.getItem() instanceof ItemMatterManipulator || !offhand.isEmpty() && offhand.getItem() instanceof ItemMatterManipulator) {
				ModelBase mdl = event.getRenderer().getMainModel();
				
				if (mdl instanceof ModelPlayer) {
			 		ModelPlayer model = (ModelPlayer) mdl;
			 		if (player.getPrimaryHand()==EnumHandSide.RIGHT) {
			 			model.bipedRightArm.rotateAngleY = -0.1F + model.bipedHead.rotateAngleY;
			 			//model.rightArmPose = ArmPose.BOW_AND_ARROW;
			 		} else {
			 			model.leftArmPose = ArmPose.BOW_AND_ARROW;
			 		}
		 		}
			}
		}
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
        		
        		if(minecraft.currentScreen == null && GalaxySpace.debug)
        		{
        			
        			long t1 = player.world.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.world.provider).getDayLength() : 24000;
        			long time = player.world.getWorldTime() % (t1 > 0 ? t1 : 1);
        		
        			float temp = player.world.provider instanceof IGalacticraftWorldProvider ? ((IGalacticraftWorldProvider) player.world.provider).getThermalLevelModifier() : 1.0F;
        			boolean isWE = player.getEntityWorld().provider instanceof WE_WorldProvider;
        			double count = 0;
        			
        			World world = ColorBlockHandler.world;
        			if(isWE && player.getEntityWorld().provider != null && world.provider instanceof WE_WorldProvider) {
	        			WE_ChunkProvider chunk = ((WE_WorldProvider)world.provider).chunk_provider;
	        			if(chunk != null) {
		        			double scaleX = chunk.biomemapScaleX;
		        			double persistance = chunk.biomemapPersistence;
		        			count = WE_PerlinNoise.PerlinNoise2D((player.getEntityWorld().getSeed() * 11) ^ 6,	player.getEntityWorld().getChunk(player.getPosition()).x / scaleX, player.getEntityWorld().getChunk(player.getPosition()).z / scaleX,
		        					persistance, chunk.biomemapNumberOfOctaves)
		        				* chunk.biomemapScaleY;
	        			}
        			}        			
       			
        			String[] s = { 
        					GalaxySpace.NAME + " " + GalaxySpace.VERSION + " DEBUG Mode",
        					"MC Version: 1.12.2",
        					"Celestial Body: " + ((player.getEntityWorld().provider instanceof IGalacticraftWorldProvider) ? ((IGalacticraftWorldProvider)player.getEntityWorld().provider).getCelestialBody().getLocalizedName() : "Unnamed"),
        					"FPS: " + mc.getDebugFPS(),
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
        					"Current Time: " + time + " | Day Length: " + (player.world.provider instanceof WorldProviderSpace ? ((WorldProviderSpace) player.world.provider).getDayLength() : player.world.provider instanceof WE_WorldProviderSpace ?  ((WE_WorldProviderSpace) player.world.provider).getDayLength() : "24000") + " | Total Time: " + player.world.getWorldTime(),
        					"Moon Phase: " + player.world.getMoonPhase(),
        					"Chunk Pos: x" + player.getEntityWorld().getChunk(player.getPosition()).x + " z" + player.getEntityWorld().getChunk(player.getPosition()).z,
        					"",
        					"Is Galacticraft Provider: " + ((player.getEntityWorld().provider instanceof IGalacticraftWorldProvider) ? "Yes" : "No"),
        					"Is Advance Space Provider: " + ((player.getEntityWorld().provider instanceof IAdvancedSpace) ? "Yes" : "No"), 
        					"Is Enable Oregen: " + (GSConfigCore.enableOresGeneration == true ? "Yes" : "No"),
        					"Is World Engine Provider: " + (isWE ? "Yes" : "No"),
        					"[WE] Biome Perlin Count: " + count
        					
        					
        			};
            			
        			int k = 4;
        			if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) k = s.length;
        			GL11.glPushMatrix();
        			//GlStateManager.disableLighting();
        			for(int i = 0; i < k; i++)
        				if(!minecraft.gameSettings.hideGUI)
        					minecraft.fontRenderer.drawStringWithShadow(s[i], 10, 28 + i*10, ColorUtil.to32BitColor(255, 255, 255, 255));
        			//GlStateManager.enableLighting();
        			GL11.glPopMatrix();

        		}
        		GlStateManager.disableLighting();
        		
        		GlStateManager.pushMatrix();
        		if(minecraft.inGameHasFocus && !minecraft.gameSettings.hideGUI && GSConfigCore.enableSpaceSuitHUD)
        			OverlaySpaceSuit.renderSpaceSuitOverlay(playerBaseClient);
        		GlStateManager.popMatrix();
        		//System.out.println(ticks);
        		ScaledResolution scaled = new ScaledResolution(mc);
        		if(minecraft.inGameHasFocus && !minecraft.gameSettings.hideGUI && ticks > 0) {
        			GlStateManager.pushMatrix();
        			int xPos = 10;
        	        int yPos = scaled.getScaledHeight() / 2 - 50;
        	        int color = Utils.getIntColorWHC(0, 0, 0, 150);
        	        int offsetY = 0;
        	        mc.fontRenderer.drawStringWithShadow("Geological Scanner Data:", xPos, yPos - 10, 0xFFFFFF);
        	        for(Entry<IBlockState, String> block : blocks.entrySet()) {
    					ItemStack item = new ItemStack(Item.getItemFromBlock(block.getKey().getBlock()), 1, block.getKey().getBlock().getMetaFromState(block.getKey()));				
    					
        				mc.ingameGUI.drawRect(xPos, yPos + (24 * offsetY), xPos + 180, yPos + 22 + (24 * offsetY), color);
        				mc.getRenderItem().zLevel = -100;
        				mc.getRenderItem().renderItemIntoGUI(item, xPos + 3, yPos + (24 * offsetY) + 3);   
        				mc.fontRenderer.drawStringWithShadow(block.getValue(), xPos + 25, yPos + (24 * offsetY) + 6, 0xFFFFFF);
        				offsetY++;
        			}     
        			
        			
        			GlStateManager.popMatrix();        		
        		}
        		if(ticks > 0 && player.ticksExisted % 10 == 0)
    				ticks--;
    			
            }
        }
	}

	/*
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onDrawBlockSelectionBox(DrawBlockHighlightEvent e) {
		
	}
	*/
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onZeroGravity(ZeroGravityEvent.InFreefall event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if(GSEventHandler.inGravityZone(entity.getEntityWorld(), entity, false))
			event.setCanceled(true);
	}
	 
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void renderBlocks(RenderWorldLastEvent e) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		ItemStack stack = player.getHeldItemMainhand();
		
		if(player != null && stack != null && stack.getItem() instanceof ItemGeologicalScanner && stack.getItemDamage() < stack.getMaxDamage() && stack.getTagCompound().getInteger("mode") == 0) {	
			RayTraceResult ray = ItemBasicGS.getRay(player.getEntityWorld(), player, false);
    		
			if(ray != null && ray.hitVec.distanceTo(player.getPositionVector()) < 5.0F) {
				final Tessellator tess = Tessellator.getInstance();
				BufferBuilder worldRenderer = tess.getBuffer();

				//GalaxySpace.debug(ray.hitVec.distanceTo(player.getPositionVector()) + "");
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.glLineWidth(2.0F);
				GlStateManager.disableTexture2D();
				GlStateManager.depthMask(false);
				BlockPos blockpos = ray.getBlockPos();
				IBlockState iblockstate = player.world.getBlockState(blockpos);

				if (iblockstate.getMaterial() != Material.AIR && player.world.getWorldBorder().contains(blockpos)) {
					double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) player.ticksExisted;
					double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) player.ticksExisted;
					double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) player.ticksExisted;
					Minecraft.getMinecraft().renderGlobal.drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox(player.world, blockpos)
							.grow(2.0D).offset(-d3, -d4, -d5), 1.0F, 0.0F, 0.0F, 0.4F);
					
					//FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "textures/misc/gradient.png"));

				}

				GlStateManager.depthMask(true);
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
				/*GlStateManager.pushMatrix();
	    		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "textures/misc/gradient.png"));
	    		GlStateManager.disableDepth();
	            GlStateManager.enableTexture2D();
	            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
	            Minecraft.getMinecraft().entityRenderer.disableLightmap();
	            GlStateManager.disableTexture2D();
	    		GlStateManager.translate(ray.getBlockPos().getX(), ray.getBlockPos().getY() + 1, ray.getBlockPos().getZ());
	    		final Tessellator tess = Tessellator.getInstance();
	    		BufferBuilder worldRenderer = tess.getBuffer();
	    		GlStateManager.color(1.0F, 0.7F, 0.7F, 1.0F);
	    		float cA = -0.01F;
	    		float cB = 1.01F;

	    		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        		worldRenderer.pos(cA, cB, cA).tex(0D, 1D).endVertex();
        		worldRenderer.pos(cB, cB, cA).tex(1D, 1D).endVertex();
        		worldRenderer.pos(cB, cB, cB).tex(1D, 0D).endVertex();
        		worldRenderer.pos(cA, cB, cB).tex(0D, 0D).endVertex();
        		tess.draw();
        		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        		worldRenderer.pos(cA, cA, cA).tex(0D, 0D).endVertex();
        		worldRenderer.pos(cA, cA, cB).tex(0D, 1D).endVertex();
        		worldRenderer.pos(cB, cA, cB).tex(1D, 1D).endVertex();
        		worldRenderer.pos(cB, cA, cA).tex(1D, 0D).endVertex();
        		tess.draw();
        		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        		worldRenderer.pos(cA, cA, cA).tex(1D, 0D).endVertex();
        		worldRenderer.pos(cA, cB, cA).tex(0D, 0D).endVertex();
        		worldRenderer.pos(cA, cB, cB).tex(0D, 1D).endVertex();
        		worldRenderer.pos(cA, cA, cB).tex(1D, 1D).endVertex();
        		tess.draw();
        		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        		worldRenderer.pos(cB, cA, cA).tex(1D, 1D).endVertex();
        		worldRenderer.pos(cB, cA, cB).tex(1D, 0D).endVertex();
        		worldRenderer.pos(cB, cB, cB).tex(0D, 0D).endVertex();
        		worldRenderer.pos(cB, cB, cA).tex(0D, 1D).endVertex();
        		tess.draw();
        		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        		worldRenderer.pos(cA, cA, cA).tex(1D, 0D).endVertex();
        		worldRenderer.pos(1F, cA, cA).tex(0D, 0D).endVertex();
        		worldRenderer.pos(1F, 1F, cA).tex(0D, 1D).endVertex();
        		worldRenderer.pos(cA, 1F, cA).tex(1D, 1D).endVertex();
        		tess.draw();
        		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        		worldRenderer.pos(1F, cA, 1F).tex(1D, 1D).endVertex();
        		worldRenderer.pos(cA, cA, 1F).tex(1D, 0D).endVertex();
        		worldRenderer.pos(cA, 1F, 1F).tex(0D, 0D).endVertex();
        		worldRenderer.pos(1F, 1F, 1F).tex(0D, 1D).endVertex();
        		tess.draw();
	    		GlStateManager.popMatrix();*/
			}
		}
		
		if(stack.getItem() instanceof ItemMatterManipulator)
		{
			RayTraceResult ray = ItemBasicGS.getRay(player.getEntityWorld(), player, false);
    		if(ray != null && ray.hitVec.distanceTo(player.getPositionVector()) < 15.0F)
    		{
    			ItemMatterManipulator.drawLine(player.getPosition().add(0, player.getEyeHeight(), 0), ray.getBlockPos(), player.posX, player.posY, player.posZ);
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
