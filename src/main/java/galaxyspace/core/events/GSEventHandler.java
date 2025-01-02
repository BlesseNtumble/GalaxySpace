package galaxyspace.core.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.GalaxySpace;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.api.item.IItemPressurized;
import galaxyspace.api.item.IItemRadiation;
import galaxyspace.api.item.IItemSpaceFood;
import galaxyspace.api.item.IJetpack;
import galaxyspace.core.achievements.AchEvent;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.handler.GSLightningStormHandler;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.entity.GSEntityMeteor;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.core.util.GSAttributePlayer;
import galaxyspace.core.util.GSDamageSource;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.WorldProviderKuiper;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGSOxygenTank;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemAncientSword;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension.WorldProviderTCetiF;
import micdoodle8.mods.galacticraft.api.event.wgen.GCCoreEventPopulate;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import micdoodle8.mods.galacticraft.api.inventory.IInventoryGC;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicPage;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderMoon;
import micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.entities.EntityLanderBase;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.ThermalArmorEvent;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.items.ItemBasic;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.tile.TileEntityParaChest;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.dimension.WorldProviderMars;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class GSEventHandler {
	
	protected HashMap<String, InventoryPlayer> playerKeepsMap = new HashMap<String, InventoryPlayer>();
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e)
	{
	    if (e.entity instanceof EntityPlayer) {
	        ((EntityPlayer) e.entity).getAttributeMap().registerAttribute(GSAttributePlayer.OXTANKS_LEFT);
	        ((EntityPlayer) e.entity).getAttributeMap().registerAttribute(GSAttributePlayer.OXTANKS_RIGHT);
	        ((EntityPlayer) e.entity).getAttributeMap().registerAttribute(GSAttributePlayer.RADIATION_LVL);
	        ((EntityPlayer) e.entity).getAttributeMap().registerAttribute(GSAttributePlayer.TOGGLE_HELMET);
	        ((EntityPlayer) e.entity).getAttributeMap().registerAttribute(GSAttributePlayer.TOGGLE_JETPACK);
	    }

	}
	@SubscribeEvent
	public void onFall(LivingFallEvent e) {
		if (e.entityLiving instanceof EntityPlayer) {
			ItemStack chest = ((EntityPlayer) e.entityLiving).getCurrentArmor(2);
			// if crashes with NPE - change to ```if (...) e.setCanceled(true)```
			e.setCanceled(chest != null && chest.getItem() instanceof IJetpack && ((IJetpack) chest.getItem()).canFly(chest, (EntityPlayer) e.entityLiving) && ((IJetpack) chest.getItem()).isActivated(chest));
		}
	}
	
	@SubscribeEvent
	public void onChangeDim(PlayerChangedDimensionEvent e)
	{
		if (e.player instanceof EntityPlayerMP)
		{

			EntityPlayerMP player = (EntityPlayerMP)e.player;			
			final GCPlayerStats GCPlayer = GCPlayerStats.get(player);	
			
			
		}
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event)
	{
		ItemStack i = event.entityPlayer.inventory.getCurrentItem();

		if(i != null && (event.action == event.action.RIGHT_CLICK_BLOCK || event.action == event.action.RIGHT_CLICK_AIR) && !event.world.isRemote) 
			GalaxySpace.debug("" + GameData.getItemRegistry().getNameForObject(i.getItem()));

		if(!event.world.isRemote)
		{
			EntityPlayerMP player = (EntityPlayerMP)event.entityPlayer;			
			final GCPlayerStats GCPlayer = GCPlayerStats.get(player);	
			
			if(GCPlayer.spaceshipTier >= 5 && event.action == Action.RIGHT_CLICK_BLOCK)
			{
				TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
				if(tile != null && tile instanceof TileEntityParaChest)
				{
					TileEntityParaChest chest = (TileEntityParaChest)tile;
					if(chest.fuelTank.getFluid().getFluid() == GalacticraftCore.fluidFuel) {
						chest.fuelTank.setFluid(new FluidStack(GSFluids.HeliumHydrogen, chest.fuelTank.getFluidAmount()));
						GCPlayer.spaceshipTier = 0;
					}
					
				}
			}
		}
	
		if(!event.world.isRemote && GSConfigCore.enableHardMode && !event.entityPlayer.capabilities.isCreativeMode)
		{
			if(event.world.provider instanceof IGalacticraftWorldProvider && !((IGalacticraftWorldProvider)event.world.provider).hasBreathableAtmosphere())
			{				
				if(i != null && i.getItem() instanceof ItemFood && !(i.getItem() instanceof IItemSpaceFood) && !(i.getItem() instanceof ItemBasic) && event.action != event.action.LEFT_CLICK_BLOCK)
				{		
					if(((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier() <= -2.0F && !OxygenUtil.isAABBInBreathableAirBlock(event.entityPlayer, true))
					{
						event.setResult(event.useItem.DENY);
						event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygenandthermal.food")));	
						event.setCanceled(true);
					}
					else if(((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier() > -2.0F && !OxygenUtil.isAABBInBreathableAirBlock(event.entityPlayer, false))
					{
						event.setResult(event.useItem.DENY);
						event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygen.food")));				   
						event.setCanceled(true);
					}				
				}
				
				if(event.action == Action.RIGHT_CLICK_BLOCK)
				{
					AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(event.x-1,event.y,event.z-1, event.x+1,event.y+2,event.z+1);
					
					for(ItemStack stack : OreDictionary.getOres("treeLeaves"))
						if(i != null && i.getItem().equals(stack.getItem()))
						{
							if(((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier() < -1.0F && !OxygenUtil.isAABBInBreathableAirBlock(event.world, bb, true))
							{
								event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygenthermal")));				   
								event.setCanceled(true);
							}
							else if(((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier() >= -1.0F && !OxygenUtil.isAABBInBreathableAirBlock(event.world, bb, false))
							{
								event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygen")));				   
								event.setCanceled(true);
							}
						}
					
					for(ItemStack stack : OreDictionary.getOres("treeSapling"))
						if(i != null && i.getItem().equals(stack.getItem()))
						{
							if(((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier() < -1.0F && !OxygenUtil.isAABBInBreathableAirBlock(event.world, bb, true))
							{
								event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygenthermal")));				   
								event.setCanceled(true);
							}
							else if(((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier() >= -1.0F && !OxygenUtil.isAABBInBreathableAirBlock(event.world, bb, false))
							{
								event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygen")));				   
								event.setCanceled(true);
							}
						}
					
					if(i != null && (i.getItem() instanceof ItemSeeds || i.getItem().equals(Items.pumpkin_seeds) || i.getItem().equals(Items.melon_seeds) || i.getItem().equals(Items.potato) || i.getItem().equals(Items.carrot) || i.getItem().equals(Items.wheat_seeds)) && event.world.getBlock(event.x, event.y, event.z) == Blocks.farmland)
					{
						//float thermal = ((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier();
						
						if(!OxygenUtil.isAABBInBreathableAirBlock(event.world, bb, true))
						{
							event.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needoxygenthermal")));				   
							event.setCanceled(true);
						}						
					}					
				}
			}	
		}
	}
	
	@SubscribeEvent 
	public void onSetBlock(SetBlockEvent e) {
		
		if(!e.world.isRemote && GSConfigCore.enableHardMode && e.world.provider instanceof IGalacticraftWorldProvider && (e.world.provider instanceof IProviderFreeze || e.world.provider instanceof WorldProviderMoon || e.world.provider instanceof WorldProviderMars))
		{
			float thermal = ((IGalacticraftWorldProvider)e.world.provider).getThermalLevelModifier();
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(e.x-1,e.y-1,e.z-1, e.x+1,e.y+2,e.z+1);
						
			if(e.block == Blocks.water && e.meta == 0 && !OxygenUtil.isAABBInBreathableAirBlock(e.world, bb, true))
			{
				if (e.block.getMaterial().equals(Material.water)) {				
					if(thermal <= -1.0F || e.world.provider instanceof WorldProviderMoon) {
						e.world.setBlock(e.x, e.y, e.z, Blocks.ice);
						e.setCanceled(true);
						 
					}
					else if(thermal >= 2.0F) {
						e.setCanceled(true);
						
					}
				}
				//e.setCanceled(true);
			}
			
			if(!OxygenUtil.isAABBInBreathableAirBlock(e.world, bb, (thermal > 1.0F || thermal < -1.0F)))
			{
				if(e.block instanceof BlockLeavesBase)
				{
					e.setCanceled(true);
				}
				
				if(e.block instanceof BlockSapling || (e.block instanceof BlockBush && e.block != Blocks.deadbush))
				{
					e.setCanceled(true);
					e.world.setBlock(e.x, e.y, e.z, Blocks.deadbush);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			
			if(consumeItemStack(player, player.inventory, new ItemStack(GSItems.BasicItems, 1 , 17)))
			{
				InventoryPlayer keepInventory = new InventoryPlayer(null);
				
				keepAllArmor(player, keepInventory);
				for (int i = 0; i < player.inventory.mainInventory.length; i++)
				{
					keepInventory.mainInventory[i] = ItemStack.copyItemStack(player.inventory.mainInventory[i]);
					player.inventory.mainInventory[i] = null;
				}
				keepInventory.setItemStack(new ItemStack(GSItems.BasicItems, 1 , 17));
				
				playerKeepsMap.put(player.getCommandSenderName(), keepInventory);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		if (playerKeepsMap.containsKey(player.getCommandSenderName()))
		{
			InventoryPlayer keepInventory = playerKeepsMap.get(player.getCommandSenderName());
			
			for (int i = 0; i < player.inventory.armorInventory.length; i++)
			{
				if (keepInventory.armorInventory[i] != null)
				{
					player.inventory.armorInventory[i] = keepInventory.armorInventory[i];
				}
			}
			for (int i = 0; i < player.inventory.mainInventory.length; i++)
			{
				if (keepInventory.mainInventory[i] != null)
				{
					player.inventory.mainInventory[i] = keepInventory.mainInventory[i];
				}
			}
			
			playerKeepsMap.remove(player.getCommandSenderName());
		}
	}
	
	@SubscribeEvent
    public void onPlanetDecorated(GCCoreEventPopulate.Post event)
    {
		if(event.worldObj.provider instanceof WorldProviderMoon)
		{
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.Ores, 4, 2, true, GCBlocks.blockMoon, 4), 3, 4, 18);	//sapphire		
			
		}
		
		if(event.worldObj.provider instanceof WorldProviderMars)
		{
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.MarsOresBlocks, 4, 0, true, MarsBlocks.marsBlock, 9), 2, 4, 18);	//diamond		
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.MarsOresBlocks, 6, 1, true, MarsBlocks.marsBlock, 9), 10, 6, 30); //gold
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.MarsOresBlocks, 16, 2, true, MarsBlocks.marsBlock, 9), 15, 6, 70);	//coal		
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.MarsOresBlocks, 10, 3, true, MarsBlocks.marsBlock, 9), 10, 6, 20);	//redstone		
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.MarsOresBlocks, 8, 4, true, MarsBlocks.marsBlock, 9), 4, 6, 20);	//silicon		
			genOre(event.worldObj, event.chunkX, event.chunkZ, new WorldGenMinableMeta(GSBlocks.MarsOresBlocks, 6, 5, true, MarsBlocks.marsBlock, 9), 16, 6, 45);	//aluminum		
			
		}
    }
	
	void genOre(World world, int chunkX, int chunkZ, WorldGenerator wg, int amountPerChunk, int minY, int maxY)
	{
		int posX = chunkX + world.rand.nextInt(16);
		int posZ = chunkZ + world.rand.nextInt(16);
		int posY = world.rand.nextInt(maxY - minY) + minY;
		
		for(int i = 0; i < amountPerChunk; i++)		
			wg.generate(world, world.rand, posX, posY, posZ);
		
	}
	
	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event) 
	{		
		EntityPlayer player = event.player;
		if (playerKeepsMap.containsKey(player.getCommandSenderName()))
		{
			InventoryPlayer keepInventory = playerKeepsMap.get(player.getCommandSenderName());
			
			// set player to the player logging out
			keepInventory.player = player;
			keepInventory.dropAllItems();
			
			playerKeepsMap.remove(player.getCommandSenderName());
		}
	}
	
	private void keepAllArmor(EntityPlayer player, InventoryPlayer keepInventory) {
		for (int i = 0; i < player.inventory.armorInventory.length; i++)
		{
			keepInventory.armorInventory[i] = ItemStack.copyItemStack(player.inventory.armorInventory[i]);
			player.inventory.armorInventory[i] = null;
		}
	}
	
	@SubscribeEvent
	public void onEntityAttack(LivingHurtEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack stack = player.getHeldItem();
			
			if(stack != null && stack.getItem() == GSItems.AncientSword && stack.hasTagCompound() && stack.getTagCompound().getBoolean(ItemAncientSword.charge))
			{
				if(player.isUsingItem())
				{
					EntityLivingBase base = (EntityLivingBase) event.source.getEntity();
					
					
					int i = 5;
					base.addVelocity((double)(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                    
					double x = base.posX;
					double y = base.posY;
					double z = base.posZ;
					
					
					for(int k = 0; k < 10; k++)						
						GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + base.worldObj.rand.nextDouble(), y + base.worldObj.rand.nextDouble(), z + base.worldObj.rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.0D, 0.0D - 0.03D), new Object [] { 10, (int)(Math.random() * 8.0D), false, new Vector3(0.8F, 0.0F, 0.0F), 1.0D } );
					   
					stack.getTagCompound().setBoolean(ItemAncientSword.charge, false);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event)
	{
		if(!event.entityPlayer.getEntityWorld().isRemote)
			if(event.target instanceof EntityAlienVillager && GSConfigCore.enableAlienTradeSystem)
			{
				EntityAlienVillager villager = (EntityAlienVillager) event.target;
				if(!villager.isChild())
				{
					EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
					GCPlayerStats stats = GCPlayerStats.get(player);
					
					if(stats.frequencyModuleInSlot == null && !player.capabilities.isCreativeMode)
					{
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.message.needfrequencyModule")));				   
					}
					else event.entityPlayer.openGui(GalaxySpace.MODID, 10, event.entityPlayer.getEntityWorld(), 0, 0, 0);
				}
			}
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{
		World world = event.world;
		MovingObjectPosition pos = event.target;
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		int meta = world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);

		if (event.current.getItem() == Items.bucket)
		{
			if (block == GSFluids.BlockHeliumHydrogen && meta == 0)
			{
				world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
				event.result = new ItemStack(GSItems.HeliumHydrogenBucket, 1, 0);
				event.setResult(Result.ALLOW);
			}	
			
			if (block == GSFluids.BlockLiquidMethane && meta == 0)
			{
				world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
				event.result = new ItemStack(GSItems.EthaneMethaneBucket, 1, 0);
				event.setResult(Result.ALLOW);
			}
			
			if (block == GSFluids.BlockSulfurAcid && meta == 0)
			{
				world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
				event.result = new ItemStack(GSItems.SulfurAcidBucket, 1, 0);
				event.setResult(Result.ALLOW);
			}
		
			if(block == Blocks.ice && meta == 0)
			{
				world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
				event.result = new ItemStack(GSItems.IceBucket, 1, 0);
				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
    public void onThermalArmorEvent(ThermalArmorEvent event)
    {
        if (event.armorStack == null)
        {
            event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.REMOVE);
            return;
        }
        if (event.armorStack.getItem() == GSItems.ThermalPaddingTier2 && event.armorStack.getItemDamage() == event.armorIndex)
        {
            event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.ADD);
            return;
        }
        
        event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.NOTHING);
    }
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.entityLiving;
		World world = living.worldObj;	
		
		if (living instanceof EntityPlayer) {
			double umot = 0.085;
			ItemStack chest = ((EntityPlayer) living).getCurrentArmor(2);
			if (chest != null && chest.getItem() instanceof IJetpack && !((EntityPlayer) living).onGround) {
				if(((IJetpack) chest.getItem()).canFly(chest, (EntityPlayer) living) && ((IJetpack) chest.getItem()).isActivated(chest) && ((EntityPlayer) living).posY < 256) 
				
				{
					
					//((EntityPlayer) living).motionY += umot;						
					
					((EntityPlayer) living).fallDistance = 0.0F;
					((EntityPlayer) living).distanceWalkedModified = 0.6F;
					GalaxySpace.proxy.resetPlayerInAirTime(((EntityPlayer) living));			
					
					((IJetpack) chest.getItem()).decrementFuel(chest);
				}
			}
			EntityPlayer player = (EntityPlayer) living;
			
			if(player.worldObj.provider instanceof WorldProviderKuiper && player.posY <= 0)
			{
				player.setPosition(player.posX, 300, player.posZ);
			}		
			
			GSLightningStormHandler.spawnLightning(player);
		}
		
		if (living instanceof EntityPlayerMP)
		{

			EntityPlayerMP player = (EntityPlayerMP)living;			
			final GCPlayerStats GCPlayer = GCPlayerStats.get(player);			

			
			AchEvent.onEntityUpdate(event);
			
			//this.updateSchematics(player, GCPlayer);
			//if(GSConfigCore.enableHardMode) this.changeBlocks(world, player);
			//this.throwMeteors(player);

			if (GCPlayer.usingPlanetSelectionGui && GSConfigCore.enableNewGalaxyMap)
	        {
	            //This sends the planets list again periodically (forcing the Celestial Selection screen to open) in case of server/client lag
	        	//#PACKETSPAM			
				
				this.sendPlanetList(player, GCPlayer);				
	        }
			/*	
			if(world.provider instanceof WorldProviderVenus && world.isRaining() && world.canBlockSeeTheSky((int)player.posX, (int)player.posY, (int)player.posZ))
			{
				player.attackEntityFrom(GSDamageSource.acid, 1.5F);
			}
			*/
			if(player.ridingEntity instanceof EntityLanderBase)
			{
				EntityLanderBase lander = (EntityLanderBase) player.ridingEntity;
				
				//GalaxySpace.debug(GCPlayer.spaceshipTier + "");
				if(GCPlayer.spaceshipTier >= 5 && lander.fuelTank.getFluid() != null && lander.fuelTank.getFluid().getFluid() == GalacticraftCore.fluidFuel)
				{
					//GalaxySpace.debug(lander.fuelTank.getFluid() + "");
					lander.fuelTank.setFluid(new FluidStack(GSFluids.HeliumHydrogen, lander.fuelTank.getFluidAmount()));
				}
			}
			
			if(player.ticksExisted % 20 == 0 && player.getHeldItem() != null && player.inventory.getCurrentItem().getItem() == GSItems.BasicItems && player.inventory.getCurrentItem().getItemDamage() == 13)
			{
				player.setFire(1);
			}
			
			ItemStack stack = player.getCurrentArmor(3);
			IInventoryGC inv = AccessInventoryGC.getGCInventoryForPlayer(player);
			if(stack != null)
			{
				if (stack.getItem() == GSItems.SpacesuitHelmet)
		        {
					if(player.isInWater())
					{
						int count = 150;
						if(inv.getStackInSlot(2) != null)
						{
							if(inv.getStackInSlot(2).getItemDamage() != inv.getStackInSlot(2).getMaxDamage())
							{
								int air = player.getAir();
								if(air < count)
								{
									player.setAir(air + count);
									//stack.setItemDamage(stack.getItemDamage() + 1);
									inv.getStackInSlot(2).setItemDamage(inv.getStackInSlot(2).getItemDamage() + 1);
								}
							}
						}
						else if(inv.getStackInSlot(2) == null && inv.getStackInSlot(3) != null)
						{
							if(inv.getStackInSlot(3).getItemDamage() != inv.getStackInSlot(3).getMaxDamage())
							{
								int air = player.getAir();
								if(air < count)
								{
									player.setAir(air + count);
									//stack.setItemDamage(stack.getItemDamage() + 1);
									inv.getStackInSlot(3).setItemDamage(inv.getStackInSlot(3).getItemDamage() + 1);
								}
							}
						}
						if(inv.getStackInSlot(2) != null && inv.getStackInSlot(3) != null)
						{
							if(inv.getStackInSlot(2).getItemDamage() == inv.getStackInSlot(2).getMaxDamage() && inv.getStackInSlot(3).getItemDamage() != inv.getStackInSlot(3).getMaxDamage())
							{
								int air = player.getAir();
								if(air < count)
								{
									player.setAir(air + count);
									//stack.setItemDamage(stack.getItemDamage() + 1);
									inv.getStackInSlot(3).setItemDamage(inv.getStackInSlot(3).getItemDamage() + 1);
								}
							}
						}
					}
		        }
			}
			
			//-----------------------------------------------------------------------------------------------
			//-------------------------------------�������� ����!--------------------------------------------
			//-----------------------------------------------------------------------------------------------
			
			
			
			if(player.ticksExisted % 10 == 0)
			{
				player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).getAttributeValue();
				player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).getAttributeValue();
				
				for(int i = 2; i <= 3; i++)
				{
					if(inv.getStackInSlot(i) != null)
					{
						IAttribute tank = i == 2 ? GSAttributePlayer.OXTANKS_LEFT : GSAttributePlayer.OXTANKS_RIGHT; 
						
						if(inv.getStackInSlot(i).getItem() instanceof ItemGSOxygenTank)
						{		
							ItemGSOxygenTank item = (ItemGSOxygenTank) inv.getStackInSlot(i).getItem();
							
							player.getEntityAttribute(tank).setBaseValue(item.getTier());
							
							if(item.getEPP())
							{
								if(player.worldObj.provider instanceof WorldProviderSurface || player.worldObj.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider)player.worldObj.provider).hasBreathableAtmosphere() ||OxygenUtil.isAABBInBreathableAirBlock(player) || OxygenUtil.inOxygenBubble(world, player.posX, player.posY, player.posZ))
									inv.getStackInSlot(i).setItemDamage(inv.getStackInSlot(i).getItemDamage() - 1);
							}
						}
						else player.getEntityAttribute(tank).setBaseValue(0);
					}
				}
				/*
				//-----------------------------------------------------------------------------------------------
				if(inv.getStackInSlot(2) != null)
				{
					
					if(inv.getStackInSlot(2).getItem() == GSItems.OxygenTankTier4)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).setBaseValue(3);
					}
					else if(inv.getStackInSlot(2).getItem() == GSItems.OxygenTankTier5)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).setBaseValue(4);
					}
					else if(inv.getStackInSlot(2).getItem() == GSItems.OxygenTankTier6)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).setBaseValue(5);
					}
					else if(inv.getStackInSlot(2).getItem() == GSItems.OxygenTankEPPTier1)
					{
						
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).setBaseValue(6);
						//if(OxygenUtil.isAABBInBreathableAirBlock(player) || OxygenUtil.inOxygenBubble(world, player.posX, player.posY, player.posZ))
						//{
							
							inv.getStackInSlot(2).setItemDamage(inv.getStackInSlot(2).getItemDamage() - 1);
						//}
					}
					else player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).setBaseValue(0);
					
				}
				else
				{
					player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).setBaseValue(0);
				}
				
				if(inv.getStackInSlot(3) != null)
				{
					if(inv.getStackInSlot(3).getItem() == GSItems.OxygenTankTier4)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).setBaseValue(3);
					}
					else if(inv.getStackInSlot(3).getItem() == GSItems.OxygenTankTier5)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).setBaseValue(4);
					}
					else if(inv.getStackInSlot(3).getItem() == GSItems.OxygenTankTier6)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).setBaseValue(5);
					}
					else if(inv.getStackInSlot(3).getItem() == GSItems.OxygenTankEPPTier1)
					{
						player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).setBaseValue(6);
						//if(OxygenUtil.isAABBInBreathableAirBlock(player) || OxygenUtil.inOxygenBubble(world, player.posX, player.posY, player.posZ))
						//{					
							if(inv.getStackInSlot(3).getItemDamage() != inv.getStackInSlot(3).getMaxDamage()) inv.getStackInSlot(3).setItemDamage(inv.getStackInSlot(3).getItemDamage() - 1);
						//}
					}
					else player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).setBaseValue(0);
				}
				else
				{
					player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).setBaseValue(0);
				}
				 */
				//-----------------------------------------------------------------------------------------------

			}
			
			this.doRadiationForEntity(world, player);
			this.doPressureForEntity(world, player);
			if(GSConfigCore.enableSolarRadiationSystem) this.applyRadiation(world, player);
			
		}

		if(living instanceof EntityPlayer &&living.worldObj.provider instanceof WorldProviderTCetiF)
			if (living.isInWater())				
				applyReverseWaterMovement(living);				
				
			
		
		
	}
	    
	private void applyRadiation(World world, EntityPlayerMP player)
	{
		if(player.worldObj.provider instanceof IAdvancedSpace || player.worldObj.provider instanceof WorldProviderMoon)
		{
			IAttributeInstance lvl = player.getEntityAttribute(GSAttributePlayer.RADIATION_LVL);
        	
			boolean solar = player.worldObj.provider instanceof WorldProviderMoon ? true : ((IAdvancedSpace) player.worldObj.provider).SolarRadiation();
			if(!solar) 
			{
				if(player.ticksExisted % 600 == 0)
        		{
					if(lvl.getAttributeValue() > 0) lvl.setBaseValue(lvl.getAttributeValue() - 1);  
        		}
			}
			
			//float j = ((IAdvancedSpace) player.worldObj.provider).getSolarRadiationMultiplier() > 0 ? 10 / ((IAdvancedSpace) player.worldObj.provider).getSolarRadiationMultiplier() + 1.0F : 10;
			float j = 10;
			
			if(player.ticksExisted % Math.round(j) == 0)
    		{					
    			if(!player.capabilities.isCreativeMode && solar)
    			{       
    				if(!(CompatibilityManager.isAndroid(player) || this.getRadiationArmor(player) || this.getProtectArmor(player)) && !this.inRadiationBubble(world, player.posX, player.posY, player.posZ))
    		        {
    		        	if(lvl.getAttributeValue() < 58 && player.worldObj.isDaytime() 
    		        			&& player.posY > player.worldObj.getTopSolidOrLiquidBlock((int)player.posX, (int)player.posZ) - 1)
    		        	{
    		        		lvl.setBaseValue(lvl.getAttributeValue() + 1);   
    		        	}
    		        }
    		        else if(lvl.getAttributeValue() > 45) lvl.setBaseValue(lvl.getAttributeValue() - 1);        	        		
    		    }
    			else lvl.setBaseValue(0);
    		}
		}
	}
	
	private void doRadiationForEntity(World world, EntityLivingBase living)
	{
		EntityPlayerMP player = (EntityPlayerMP)living;
		
		IAttributeInstance lvl = player.getEntityAttribute(GSAttributePlayer.RADIATION_LVL);
		
		if (lvl.getAttributeValue() > 45)
		{
			WorldProvider provider = player.worldObj.provider;
	        
	        if(living.posY <= 1000)
	        {
	        	if (!(living instanceof EntityPlayer))
	        	{
	        		if (living.ticksExisted % 100 == 0 && (!(living instanceof IEntityLivingData)) && !(living.getClass() == EntityEvolvedZombie.class || living.getClass() == EntityEvolvedSpider.class || living.getClass() == EntityEvolvedSkeleton.class || living.getClass() == EntityEvolvedCreeper.class))
	        		{
	        			living.addPotionEffect(new PotionEffect(GSPotions.radiation.id, 80));
	        		}
	        	}
	        	else if (living instanceof EntityPlayerMP)
	        	{			

					if (player.ticksExisted % 20 == 0 && !player.capabilities.isCreativeMode && !this.inRadiationBubble(world, player.posX, player.posY, player.posZ))
					{
						player.addPotionEffect(new PotionEffect(GSPotions.radiation.id, 80));
					}
					else if (this.getRadiationArmor(player) || this.getProtectArmor(player) || this.inRadiationBubble(world, player.posX, player.posY, player.posZ))
					{
						player.removePotionEffect(GSPotions.radiation.id);
					}
	        	}
	        }
		}
	}
	
	public void doPressureForEntity(World world, EntityPlayerMP player)
	{
		if(player.worldObj.provider instanceof IAdvancedSpace)
		{
    		IAdvancedSpace provider = (IAdvancedSpace) player.worldObj.provider;
    		int level = provider.AtmosphericPressure();        		

    		for(int i = 0; i < 4; i++)
    		{
        		ItemStack stack = player.getCurrentArmor(i);        		
        		
        		if(!player.capabilities.isCreativeMode)
        		{
	        		if(stack == null || !(stack.getItem() instanceof IItemPressurized || this.getProtectArmor(player)))
	        		{
	        			if(!this.inGravityZone(world, player, true)) 
	        			{	        				
	        				if(player.ticksExisted % 50 == 0 && !CompatibilityManager.isAndroid(player)) {
			        			if(level > 10) player.addPotionEffect(new PotionEffect(Potion.confusion.id, 10*20));					       
						        if(level > 35) player.addPotionEffect(new PotionEffect(Potion.blindness.id, 10*20));
						        if(level > 25) player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 280, 4));		        			
			        			if(level > 45) player.attackEntityFrom(GSDamageSource.pressure, 2.5F);
		        			}	
	        				/*
				        	if(level > 15) player.addPotionEffect(new PotionEffect(Potion.blindness.id, 280));
				        	if(level > 20) player.addPotionEffect(new PotionEffect(Potion.confusion.id, 280));
				        	if(GSConfigCore.enableHardMode && !CompatibilityManager.isAndroid(player))
				        	{
				        		if(level > 25) player.attackEntityFrom(GSDamageSource.pressure, 1.0F);
				        		if(level > 35) player.attackEntityFrom(GSDamageSource.pressure, 1000.0F);				        			
				        	}
				        	else
		        			{ 	if(level > 25) 
		        				{
		        					player.jumpMovementFactor = 0.5F;
		        					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 280, 4));
		        				} 				        				        				
		        			}*/
	        			}
	        		}
	        			        			
        		}

    		}
    		
		}
	}
	public static boolean getRadiationArmor(EntityPlayerMP player)
	{
		boolean armor1 = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() instanceof IItemRadiation;
		boolean armor2 = player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() instanceof IItemRadiation;
		boolean armor3 = player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() instanceof IItemRadiation;
		boolean armor4 = player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() instanceof IItemRadiation;
			
		return armor1 && armor2 && armor3 && armor4;
	}
	
	private void changeBlocks(World world, int x, int y, int z)
	{
		float thermal = ((IGalacticraftWorldProvider)world.provider).getThermalLevelModifier();
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x-1,y-1,z-1, x+1,y+1,z+1);
		
		if(!OxygenUtil.isAABBInBreathableAirBlock(world, bb, true))
		{
			if (world.getBlock(x, y, z) == Blocks.water	&& world.getBlockMetadata(x, y, z) == 0)
			{
				world.setBlock(x, y, z, Blocks.ice, 0, 3);				
			}
			
			else if(world.getBlock(x, y, z) instanceof BlockSapling || (world.getBlock(x, y, z) instanceof BlockBush && world.getBlock(x, y, z) != Blocks.deadbush))
			{
				world.setBlock(x, y, z, Blocks.deadbush, 0, 3);			
			}
			
			else if(world.getBlock(x, y, z) instanceof BlockLeavesBase)
			{
				world.setBlock(x, y, z, Blocks.air, 0, 3);
			}
		}
	}

	private void sendMessage(EntityPlayer player, String message, int countdown)
	{		
		player.addChatComponentMessage(new ChatComponentText(message));		
	}
	
	private void applyReverseWaterMovement(EntityLivingBase entity){

		AxisAlignedBB par1AxisAlignedBB = entity.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D);

		int i = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int j = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int k = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int l = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int i1 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int j1 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);

		if (!entity.worldObj.checkChunksExist(i, k, i1, j, l, j1)){
			return;
		}else{
			boolean flag = false;
			Vec3 vec3 = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);

			for (int k1 = i; k1 < j; ++k1){
				for (int l1 = k; l1 < l; ++l1){
					for (int i2 = i1; i2 < j1; ++i2){
						Block block = entity.worldObj.getBlock(k1, l1, i2);

							if (block != null && block.getMaterial() == Material.water){
									double d0 = l1 + 1 - BlockLiquid.getLiquidHeightPercent(entity.worldObj.getBlockMetadata(k1, l1, i2));
	
								if (l >= d0){
									flag = true;
									block.velocityToAddToEntity(entity.worldObj, k1, l1, i2, entity, vec3);
								}							
						}
					}
				}
			}

			if (vec3.lengthVector() > 0.0D && entity.isInWater()){
				vec3 = vec3.normalize();
				double d1 = -0.014D;
				entity.motionX += vec3.xCoord * d1;
				entity.motionY += vec3.yCoord * d1;
				entity.motionZ += vec3.zCoord * d1;
			}
		}
	}
	
	public static boolean consumeItemStack(EntityPlayer player, IInventory inventory, ItemStack stack) {
		   if(getAmount(inventory, stack) >= stack.stackSize) {
		      for (int i = 0; i < inventory.getSizeInventory(); i++) {
		         if(isItemStackEqual(inventory.getStackInSlot(i), stack)){
		            int amount = Math.min(stack.stackSize, inventory.getStackInSlot(i).stackSize);
		            if(amount > 0) {
		               inventory.getStackInSlot(i).stackSize -= amount;
		               if(inventory.getStackInSlot(i).stackSize <= 0) {
		                  inventory.setInventorySlotContents(i, null);
		                  
		               }
		               stack.stackSize -= amount;
		               player.inventoryContainer.detectAndSendChanges();
		            }
		            if(stack.stackSize <= 0) {		            	
		               return true;
		            }
		         }
		      }
		   }
		   return false;
		}

	public static int getAmount(IInventory inventory, ItemStack stack) {
		   int amount = 0;
		   for (int i = 0; i < inventory.getSizeInventory(); i++) {
		      if(isItemStackEqual(inventory.getStackInSlot(i), stack)) {
		         amount += inventory.getStackInSlot(i).stackSize;
		      }
		   }
		   return amount;
		}

	public static boolean isItemStackEqual(ItemStack stack1, ItemStack stack2) {
		if(stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem();
		return (stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage());
	}
	
	public static ItemStack changeStackItem(ItemStack stack, Item item) {
	      ItemStack newStack = new ItemStack(item);
	      newStack.setItemDamage(stack.getItemDamage());
	      newStack.stackTagCompound = stack.stackTagCompound;
	      return newStack;
	}

		
	protected void updateSchematics(EntityPlayerMP player, GCPlayerStats playerStats)
    {
       /* SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicCone));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicBody));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicEngine));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicBooster));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicFins));
        */
        Collections.sort(playerStats.unlockedSchematics);

        if (player.playerNetServerHandler != null && (playerStats.unlockedSchematics.size() != playerStats.lastUnlockedSchematics.size() || (player.ticksExisted - 1) % 100 == 0))
        {
            Integer[] iArray = new Integer[playerStats.unlockedSchematics.size()];

            for (int i = 0; i < iArray.length; i++)
            {
                ISchematicPage page = playerStats.unlockedSchematics.get(i);
                iArray[i] = page == null ? -2 : page.getPageID();
            }

            List<Object> objList = new ArrayList<Object>();
            objList.add(iArray);

            GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_UPDATE_SCHEMATIC_LIST, objList), player);
        }
    }
	
    protected void sendPlanetList(EntityPlayerMP player, GCPlayerStats playerStats)
    {
    	HashMap<String, Integer> map;
    	if (player.ticksExisted % 50 == 0)
    		//Check for genuine update - e.g. maybe some other player created a space station or changed permissions
    		//CAUTION: possible server load due to dimension loading, if any planets or moons were (contrary to GC default) set to hotload
    		map = WorldUtil.getArrayOfPossibleDimensions(playerStats.spaceshipTier, player);
    	else
    		map = WorldUtil.getArrayOfPossibleDimensionsAgain(playerStats.spaceshipTier, player);

        String temp = "";
        int count = 0;

        for (Entry<String, Integer> entry : map.entrySet())
        {
            temp = temp.concat(entry.getKey() + (count < map.entrySet().size() - 1 ? "?" : ""));
            count++;
        }
                
        if (!temp.equals(playerStats.savedPlanetList) || (player.ticksExisted % 1 == 0))
	    {
        	Integer[] ints = new Integer[] {playerStats.spaceshipTier, playerStats.fuelLevel};
        	GalaxySpace.packetPipeline.sendTo(new GSPacketSimple(GSEnumSimplePacket.C_UPDATE_DIMENSION_LIST, new Object[] { player.getGameProfile().getName(), temp, ints }), player);
	        playerStats.savedPlanetList = new String(temp);	       
	    }
    }
	
	protected void throwMeteors(EntityPlayerMP player)
	{
	    World world = player.worldObj;
	    if (world.provider instanceof IAdvancedSpace && !world.isRemote)
	    {
	    	if (((IGalacticraftWorldProvider) world.provider).getMeteorFrequency() > 0 && ConfigManagerCore.meteorSpawnMod > 0.0)
	        {
	    		final int f = (int) (((IGalacticraftWorldProvider) world.provider).getMeteorFrequency() * 1000D * (1.0 / ConfigManagerCore.meteorSpawnMod));
	    		//final int f = 200;
	    		if (world.rand.nextInt(f) == 0 /*&& world.getWorldTime() > 6000L && world.getWorldTime() < 9000L*/)
	    		{
	    			final EntityPlayer closestPlayer = world.getClosestPlayerToEntity(player, 100);

	    			if (closestPlayer == null || closestPlayer.getEntityId() <= player.getEntityId())
	    			{
	    				int x, y, z;
	    				double motX, motZ;
	    				x = world.rand.nextInt(1) - 8;
	    				y = world.rand.nextInt(20) + 200;
	    				z = world.rand.nextInt(1) - 8;
	    				motX = world.rand.nextDouble() * 1;
	    				motZ = world.rand.nextDouble() * 1;

	    				final GSEntityMeteor meteor = new GSEntityMeteor(world, player.posX + x, player.posY + y, player.posZ + z, motX - 0.5D, 0, motZ - 0.5D, 6);

	    				if (!world.isRemote)
	    				{
	    					world.spawnEntityInWorld(meteor);
	    				}
	    			}
	    		}	               
	        }
	    }
	 }
	
	public static void enableFlight(EntityPlayer player, boolean state) {
		ItemStack chest = player.getCurrentArmor(2);
		if (chest != null && chest.getItem() instanceof IJetpack) {
			((IJetpack) chest.getItem()).switchState(chest, state);
			/*if (((IJetpack) chest.getItem()).isActivated(chest)) {
				player.worldObj.playSoundAtEntity(player, "jetpack:launch", 1, 1);
				JetCore.network.sendToAllAround(new SoundMessage(Item.getIdFromItem(chest.getItem()), player.getCommandSenderName()), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64));
			}*/
		}
	}
	
	public static boolean inGravityZone(World world, EntityPlayer player, boolean checkPressureModule)
	{
		for (final BlockVec3Dim blockVec : TileEntityGravitationModule.loadedTiles)
		{
			if (blockVec != null && blockVec.dim == world.provider.dimensionId)
            {
				TileEntity tile = world.getTileEntity(blockVec.x, blockVec.y, blockVec.z);
				
				if (tile instanceof TileEntityGravitationModule)
            	{
					TileEntityGravitationModule gravity = (TileEntityGravitationModule)tile;
					
					if(!gravity.disabled && gravity.hasEnoughEnergyToRun && gravity.inGravityZone(world, player)) {
						if(checkPressureModule && gravity.getStackInSlot(1) != null) return true;
						if(!checkPressureModule) return true;	
					}
            	}
            }
		}
		return false;
	}
	
	public static boolean inRadiationBubble(World world, double avgX, double avgY, double avgZ)
	{
        for (final BlockVec3Dim blockVec : TileEntityRadiationStabiliser.loadedTiles)
        {
            if (blockVec != null && blockVec.dim == world.provider.dimensionId)
            {
            	TileEntity tile = world.getTileEntity(blockVec.x, blockVec.y, blockVec.z);
            	if (tile instanceof TileEntityRadiationStabiliser)
            	{
	            	if (((TileEntityRadiationStabiliser) tile).inBubble(avgX, avgY, avgZ)) return true;
            	}
            }
        }

		return false;
	}
	
	
	public static boolean getProtectArmor(EntityPlayer player)
	{
			
		boolean[] check = new boolean[4];
		
		for(String string : GSConfigCore.protect_armor)
		{
			String[] meta = string.split(":");
			if(meta.length > 2)
			{
				for(int i = 0; i < 4; i++)
				{
					String prefix = meta[0];
					Item item = GameRegistry.findItem(prefix, meta[1]);
					int metadata = Integer.parseInt(meta[2]);
					
					check[i] = item != null && player.inventory.armorInventory[i] != null && player.inventory.armorInventory[i].getItem() == item && player.inventory.armorInventory[i].getItemDamage() == metadata;			
					
					if(check[i]) 
						break;
				}
			}
			else
			{
				for(int i = 0; i < 4; i++)
				{
					//String[] itemInfo = string.split(":");
					String prefix = meta.length == 1 ? "minecraft" : meta[0];
					Item item = GameRegistry.findItem(prefix, meta[1]);
					
					check[i] = item != null && player.inventory.armorInventory[i] != null && player.inventory.armorInventory[i].getItem() == item;			
					
					if(check[i]) 
						break;
				}
			}
			if(check[0] && check[1] && check[2] && check[3]) 
				break;
		}		

		return check[0] && check[1] && check[2] && check[3];
	}
	
}
