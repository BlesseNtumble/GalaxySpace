package galaxyspace.core.events;

import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.api.item.IItemPressurized;
import asmodeuscore.api.item.IItemSpaceFood;
import asmodeuscore.core.event.PressureEvent;
import asmodeuscore.core.event.RadiationEvent;
import asmodeuscore.core.handler.LightningStormHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IJetpackArmor;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.items.rockets.ItemTier4Rocket;
import galaxyspace.core.prefab.items.rockets.ItemTier5Rocket;
import galaxyspace.core.prefab.items.rockets.ItemTier6Rocket;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSDamageSource;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.WorldProviderKuiperBelt;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.event.wgen.GCCoreEventPopulate;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import micdoodle8.mods.galacticraft.api.inventory.IInventoryGC;
import micdoodle8.mods.galacticraft.api.item.EnumExtendedInventorySlot;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderMoon;
import micdoodle8.mods.galacticraft.core.entities.EntityLanderBase;
import micdoodle8.mods.galacticraft.core.entities.EntityMeteor;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.EnumModelPacketType;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.items.ItemTier1Rocket;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.items.ItemTier3Rocket;
import micdoodle8.mods.galacticraft.planets.asteroids.tile.TileEntityShortRangeTelepad;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.dimension.WorldProviderMars;
import micdoodle8.mods.galacticraft.planets.mars.items.ItemTier2Rocket;
import micdoodle8.mods.galacticraft.planets.venus.dimension.WorldProviderVenus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class GSEventHandler {

	/*
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e)
	{
	    if (e.getEntity() instanceof EntityPlayer) {
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.OXTANKS_LEFT);
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.OXTANKS_RIGHT);
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.PRESSURE_PROTECT);
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.TOGGLE_HELMET);
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.TOGGLE_CHEST);
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.TOGGLE_LEGS);
	        //((EntityPlayer) e.getEntity()).getAttributeMap().registerAttribute(GSAttributePlayer.TOGGLE_BOOTS);
	    }
	}*/

	@SubscribeEvent
	public void onFall(LivingFallEvent e) {
		if (e.getEntityLiving() instanceof EntityPlayer) {
			ItemStack chest = ((EntityPlayer) e.getEntityLiving()).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			// if crashes with NPE - change to ```if (...) e.setCanceled(true)```
			e.setCanceled(chest != null && chest.getItem() instanceof IJetpackArmor && ((IJetpackArmor) chest.getItem()).canFly(chest, (EntityPlayer) e.getEntityLiving()) && ((IJetpackArmor) chest.getItem()).isActivated(chest));
		}
	}
	
	@SubscribeEvent 
	public void onSetBlock(SetBlockEvent e) {
		
		if(!e.world.isRemote && e.world.provider instanceof IGalacticraftWorldProvider && (e.world.provider instanceof IProviderFreeze || e.world.provider instanceof WorldProviderMars || e.world.provider instanceof WorldProviderVenus))
		{
			float thermal = ((IGalacticraftWorldProvider)e.world.provider).getThermalLevelModifier();
			AxisAlignedBB bb = new AxisAlignedBB(e.pos.getX()-1,e.pos.getY()-1,e.pos.getZ()-1, e.pos.getX()+1,e.pos.getY()+2,e.pos.getZ()+1);
					
			if(e.world.provider instanceof IProviderFreeze ||  e.world.provider instanceof WorldProviderMars || e.world.provider instanceof WorldProviderVenus)
			{
				if(thermal >= 2.0)
				{
					if(e.block == Blocks.ICE.getDefaultState())
					{
						e.setCanceled(true);
						e.world.setBlockState(e.pos, Blocks.WATER.getDefaultState());
						GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(e.pos.getX() + e.world.rand.nextDouble(), e.pos.getY() + 0.4D + e.world.rand.nextDouble(), e.pos.getZ() + e.world.rand.nextDouble()), new Vector3(0.0D, 0.001D, 0.0D), new Object [] { 10, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
				    	
					}
				}
			}
			
			if(e.block == Blocks.WATER.getDefaultState() && !OxygenUtil.isAABBInBreathableAirBlock(e.world, bb, true))
			{
				if (e.block.getMaterial().equals(Material.WATER) && e.world.isAirBlock(e.pos.up())) {				
					if(thermal <= -1.0F || e.world.provider instanceof WorldProviderMoon) {
						e.world.setBlockState(e.pos, Blocks.ICE.getDefaultState());						
						e.setCanceled(true);						 
					}
					else if(thermal >= 2.0F) {
						GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(e.pos.getX() + e.world.rand.nextDouble(), e.pos.getY() + 0.4D + e.world.rand.nextDouble(), e.pos.getZ() + e.world.rand.nextDouble()), new Vector3(0.0D, 0.001D, 0.0D), new Object [] { 10, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
						e.world.setBlockState(e.pos, Blocks.AIR.getDefaultState());
						e.setCanceled(true);						
					}
				}
				//e.setCanceled(true);
			}
			/*
			if(!OxygenUtil.isAABBInBreathableAirBlock(e.world, bb, (thermal > 1.0F || thermal < -1.0F)) && !((IGalacticraftWorldProvider)e.world.provider).hasBreathableAtmosphere())
			{
				if(e.block.getBlock() instanceof BlockLeaves)
				{
					e.setCanceled(true);
				}
				
				if(e.block.getBlock() instanceof BlockSapling || (e.block.getBlock() instanceof BlockBush && e.block.getBlock() != Blocks.DEADBUSH))
				{
					e.setCanceled(true);
					e.world.setBlockState(e.pos, Blocks.DEADBUSH.getDefaultState());
				}
			}*/
		}
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent.RightClickBlock event)
	{		
		//Skip events triggered from Thaumcraft Golems and other non-players
        if (event.getEntityPlayer() == null || event.getEntityPlayer().inventory == null || event.getPos() == null || (event.getPos().getX() == 0 && event.getPos().getY() == 0 && event.getPos().getZ() == 0))
        {
            return;
        }
        
		final World world = event.getEntityPlayer().world;
		if (world == null) {
			return;
		}
		
		final Block block = world.getBlockState(event.getPos()).getBlock();
		ItemStack stack = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		
		if(!world.isRemote)
		{	
				
			//ICE BUCKET
			if(block == Blocks.ICE && stack.getItem() instanceof ItemBucket)
			{				
				stack.shrink(1);
				player.inventory.addItemStackToInventory(new ItemStack(GSItems.BASIC, 1, 17));				
				world.setBlockToAir(event.getPos());
			}	
			
			//EMERGENCY TELEPORT
			if(player.isSneaking() && stack.isItemEqual(new ItemStack(GSItems.BASIC, 1, 18)))
			{
				if(!stack.hasTagCompound())				
					stack.setTagCompound(new NBTTagCompound());
				
				if(block == AsteroidBlocks.shortRangeTelepad) 
				{
					TileEntityShortRangeTelepad tile = (TileEntityShortRangeTelepad) event.getWorld().getTileEntity(event.getPos());
					
					if(tile.hasEnoughEnergyToRun && tile.addressValid) 
					{
						stack.getTagCompound().setIntArray("position", new int[] {event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getWorld().provider.getDimension()});		
					}
				}
				else
				{
					
					stack.getTagCompound().setBoolean("turnonoff", !stack.getTagCompound().getBoolean("turnonoff"));					
				}
			}
			
			if(world.provider instanceof IGalacticraftWorldProvider && !((IGalacticraftWorldProvider)world.provider).hasBreathableAtmosphere())
			{
				AxisAlignedBB bb = new AxisAlignedBB(event.getPos().up());
				boolean comfort = ((IGalacticraftWorldProvider)world.provider).getThermalLevelModifier() >= -1.0F;
				boolean cold = ((IGalacticraftWorldProvider)world.provider).getThermalLevelModifier() < -1.0F;
				
				for(ItemStack ore : OreDictionary.getOres("treeLeaves"))
					if(stack != null && stack.getItem().equals(ore.getItem()))
					{
						if(cold && !OxygenUtil.isAABBInBreathableAirBlock(world, bb, true))
						{
							player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygenthermal")));				   
							event.setCanceled(true);
						}
						else if(comfort && !OxygenUtil.isAABBInBreathableAirBlock(world, bb, false))
						{
							player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygen")));				   
							event.setCanceled(true);
						}
					}
				
				for(ItemStack ore : OreDictionary.getOres("treeSapling"))
					if(stack != null && stack.getItem().equals(ore.getItem()))
					{
						if(cold && !OxygenUtil.isAABBInBreathableAirBlock(world, bb, true))
						{
							player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygenthermal")));				   
							event.setCanceled(true);
						}
						else if(comfort && !OxygenUtil.isAABBInBreathableAirBlock(world, bb, false))
						{
							player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygen")));				   
							event.setCanceled(true);
						}
					}
				
				if(stack != null && (stack.getItem().equals(Items.PUMPKIN_SEEDS) || stack.getItem().equals(Items.MELON_SEEDS) || stack.getItem().equals(Items.POTATO) || stack.getItem().equals(Items.CARROT) || stack.getItem().equals(Items.WHEAT_SEEDS)) && world.getBlockState(event.getPos()).getBlock() == Blocks.FARMLAND)
				{
					//float thermal = ((IGalacticraftWorldProvider)event.world.provider).getThermalLevelModifier();
					
					if(!OxygenUtil.isAABBInBreathableAirBlock(world, bb, true))
					{
						player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygenthermal")));				   
						//event.setCanceled(true);
					}						
				}	
			}				
		}
	}
	
	@SubscribeEvent
	public void onEntityInteract(PlayerInteractEvent event)
	{
		World world = event.getWorld();
		if (world == null) {
			return;
		}
				
		
		EntityPlayer player = event.getEntityPlayer();
					
		ItemStack i = event.getItemStack();//player.getHeldItemMainhand();
		//ItemStack j = player.getHeldItemOffhand();
				
		if(!world.isRemote && GalaxySpace.debug) {
			GalaxySpace.debug(Item.REGISTRY.getNameForObject(i.getItem()) + "");
		}
		
		if(!world.isRemote && GSConfigCore.enableHardMode && !player.capabilities.isCreativeMode)
		{						
			
			if(GalaxySpace.debug)
				GalaxySpace.debug(Item.REGISTRY.getNameForObject(i.getItem()) + "");
			
		
			if(world.provider instanceof IGalacticraftWorldProvider && !((IGalacticraftWorldProvider)world.provider).hasBreathableAtmosphere())
			{
				if(!OxygenUtil.isAABBInBreathableAirBlock(player, false) && checkFood(i))
				{
					player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygen.food")));				   
					event.setCancellationResult(EnumActionResult.FAIL);
					event.setCanceled(true);
				}					
			}
		}
			
	}

	private boolean checkFood(ItemStack s)
	{
		return !s.isEmpty() && s.getItem() instanceof ItemFood && !(s.getItem() instanceof micdoodle8.mods.galacticraft.core.items.ItemFood) && !(s.getItem() instanceof IItemSpaceFood);
	}

/*
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
	*/
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		World world = living.world;
		
		if (living instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) living;
			ItemStack chest = ((EntityPlayer) living).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			
			if (chest != null && chest.getItem() instanceof IJetpackArmor && !((EntityPlayer) living).onGround) {
				if(((IJetpackArmor) chest.getItem()).canFly(chest, (EntityPlayer) living) && ((IJetpackArmor) chest.getItem()).isActivated(chest) && ((EntityPlayer) living).posY < 256) 
				
				{
					((EntityPlayer) living).fallDistance = 0.0F;
					((EntityPlayer) living).distanceWalkedModified = 0.6F;
					
					GalaxySpace.proxy.resetPlayerInAirTime(((EntityPlayer) living));
					
					((IJetpackArmor) chest.getItem()).decrementFuel(chest);
				}
			}
			
			if(!player.capabilities.isCreativeMode) {
				ItemStack held = player.getHeldItemMainhand();
				
				if(held.getItem() instanceof ItemTier1Rocket
						|| held.getItem() instanceof ItemTier2Rocket
						|| held.getItem() instanceof ItemTier3Rocket
						|| held.getItem() instanceof ItemTier4Rocket
						|| held.getItem() instanceof ItemTier5Rocket
						|| held.getItem() instanceof ItemTier6Rocket)
				{
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 4));
				}				
				
			}
			
			
		}
				
		if (living instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)living;			
			final GCPlayerStats stats = GCPlayerStats.get(player);			
			
			LightningStormHandler.spawnLightning(player);
								
			this.updateSchematics(player, stats);
			//this.changeBlocks(world, player);
			//this.throwMeteors(player);

			
			if(world.rand.nextInt(50) <= 10 && !this.getProtectArmor(player) && world.provider instanceof WorldProviderTitan && world.isRaining() && world.canBlockSeeSky(player.getPosition()))
			{
				player.attackEntityFrom(GSDamageSource.acid, 1.5F);
			}
			
			/*if (solar < 48 || world.provider instanceof WorldProviderMercury && player.posY <= 30)
			{
				player.removePotionEffect(GSPotions.radiation.id);
			}*/
			
			if(player.world.provider instanceof WorldProviderKuiperBelt && player.posY <= -20)
			{				
				player.connection.setPlayerLocation(player.posX, 188, player.posZ, player.rotationYaw, player.rotationPitch);
			}
			
			if(!player.capabilities.isCreativeMode && player.getHealth() <= 5.0F)
			{					
				for(ItemStack stack : player.inventory.mainInventory)
				{
					if(stack.getItem() == GSItems.BASIC && stack.getItemDamage() == 18)
					{						
						if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("turnonoff"))
						{							
							int[] pos = stack.getTagCompound().getIntArray("position");								
							if(pos != null && pos.length >= 3 && player.world.provider.getDimension() == pos[3]) {
								player.sendMessage(new TextComponentString(EnumColor.AQUA + GCCoreUtil.translateWithFormat("gui.message.emergency_teleport") + " x:" + pos[0] + " y:" + pos[1] + " z:" + pos[2]));
								   
								player.connection.setPlayerLocation(pos[0], pos[1], pos[2], player.rotationYaw, player.rotationPitch);								
								stack.shrink(1);
							}
						}
						break;
					}
				}
			}
			
	        /*if (stats.getShieldControllerInSlot().isEmpty())
	        {
	        	GCPlayerHandler.sendGearUpdatePacket(player, EnumModelPacketType.REMOVE, EnumExtendedInventorySlot.SHIELD_CONTROLLER);
	        }
	       	else*/ if (stats.getShieldControllerInSlot().isItemEqual(new ItemStack(GSItems.BASIC, 1, 16)))
	        {
	       		ItemStack shield = stats.getShieldControllerInSlot();
	       		if (shield.hasTagCompound())
	            {
	       			int shieldtime = shield.getTagCompound().getInteger("shieldtime");
	       			
	       			if(!player.capabilities.isCreativeMode && player.world.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider)player.world.provider).getCelestialBody().atmosphere.isCorrosive())
	       			{
		       			if (shieldtime >= 1)
		                {
		                	if(player.ticksExisted % 20 == 0) 
		                	{
		                		shieldtime -= 1;
		                		shield.getTagCompound().setInteger("shieldtime", shieldtime);
		                	}
		                }
		                else
		                {
		                	shield.shrink(1);
		                	player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translateWithFormat("gui.message.now_noshield")));
		                }
	       			}
	            }
	       		else
	            {
	       			shield.setTagCompound(new NBTTagCompound());
	       			shield.getTagCompound().setInteger("shieldtime", ItemBasicGS.SHIELD_TIME);
	            }
	       		int gearID = GalacticraftRegistry.findMatchingGearID(stats.getShieldControllerInSlot(), EnumExtendedInventorySlot.SHIELD_CONTROLLER);

	          	if (gearID >= 0)
	           	{
	          		GCPlayerHandler.sendGearUpdatePacket(player, EnumModelPacketType.ADD, EnumExtendedInventorySlot.SHIELD_CONTROLLER, gearID);
	           	}
	        }

	        stats.setLastShieldControllerInSlot(stats.getShieldControllerInSlot());
	       
	       
			ItemStack stack = player.inventory.armorInventory.get(3);
			IInventoryGC inv = AccessInventoryGC.getGCInventoryForPlayer(player);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == GSItems.SPACE_SUIT_HELMET && player.isInWater())
				{
					int count = 150;
					int air = player.getAir();
					for(int i = 2; i <= 3; i++)
					{
						if(!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItemDamage() != inv.getStackInSlot(i).getMaxDamage())
						{
							if(air < count)
							{
								player.setAir(air + count);
								inv.getStackInSlot(i).setItemDamage(inv.getStackInSlot(i).getItemDamage() + 1);
							}
							break;
						}
							
					}					
		        }
			}
			
			if(OxygenUtil.isAABBInBreathableAirBlock(player))
			{
				for(int i = 2; i <= 3; i++)
					if(!inv.getStackInSlot(i).isEmpty() && (inv.getStackInSlot(i).getItem() == GSItems.OXYGENTANK_TIER_EPP || inv.getStackInSlot(i).hasTagCompound() && inv.getStackInSlot(i).getTagCompound().hasKey("epp")))
					{
						ItemStack tank = inv.getStackInSlot(i);
						if(tank.getItemDamage() != 0)
						{
							if(player.ticksExisted % 10 == 0)
							{
								tank.setItemDamage(tank.getItemDamage() - 2);
							}
						}					
					}
			}
		}
		
		//}
		/*if (living instanceof EntityPlayer) 
		{
			if(living.worldObj.provider instanceof WorldProviderTCetiE)
			{
				if (living.isInWater())
				{
					applyReverseWaterMovement(living);				
				}
			}
		}*/
		
	}
	
	@SubscribeEvent
    public void onPlanetDecorated(GCCoreEventPopulate.Post event)
    {
		if(event.world.provider instanceof WorldProviderMars)
		{
			genOre(event.world, event.pos, new WorldGenMinableMeta(GSBlocks.MARS_ORES, 4, 0, true, MarsBlocks.marsBlock, 9), 6, 4, 18);	//diamond		
			genOre(event.world, event.pos, new WorldGenMinableMeta(GSBlocks.MARS_ORES, 6, 1, true, MarsBlocks.marsBlock, 9), 10, 6, 30); //gold
			genOre(event.world, event.pos, new WorldGenMinableMeta(GSBlocks.MARS_ORES, 16, 2, true, MarsBlocks.marsBlock, 9), 15, 6, 70);	//coal		
			genOre(event.world, event.pos, new WorldGenMinableMeta(GSBlocks.MARS_ORES, 10, 3, true, MarsBlocks.marsBlock, 9), 10, 6, 20);	//redstone		
			genOre(event.world, event.pos, new WorldGenMinableMeta(GSBlocks.MARS_ORES, 8, 4, true, MarsBlocks.marsBlock, 9), 4, 6, 20);	//silicon		
			genOre(event.world, event.pos, new WorldGenMinableMeta(GSBlocks.MARS_ORES, 6, 5, true, MarsBlocks.marsBlock, 9), 16, 6, 45);	//aluminum		
			
		}
    }
	
	void genOre(World world, BlockPos pos, WorldGenerator wg, int amountPerChunk, int minY, int maxY)
	{
		BlockPos pos1 = pos.add(world.rand.nextInt(16), world.rand.nextInt(maxY - minY) + minY, world.rand.nextInt(16));
		
		for(int i = 0; i < amountPerChunk; i++)		
			wg.generate(world, world.rand, pos1);
		
	}
	
	@SubscribeEvent
	public void onRadiation(RadiationEvent event)
	{
		EntityLivingBase living = (EntityLivingBase) event.getEntity();
		
		if(living instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) living;			
			
			event.setCanceled(!GSConfigCore.enableRadiationSystem || this.getProtectArmor(player) || player.getRidingEntity() instanceof EntityLanderBase || player.getRidingEntity() instanceof EntityTieredRocket || this.inRadiationBubble(player.getEntityWorld(), player.posX, player.posY, player.posZ));		
		}
	}
	
	@SubscribeEvent
	public void onPressure(PressureEvent event)
	{
		EntityLivingBase living = (EntityLivingBase) event.getEntity();
		
		if(living instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) living;
			World world = player.getEntityWorld();
			int level = event.getPressureLevel();
			
			if(!player.capabilities.isCreativeMode && GSConfigCore.enablePressureSystem)
        	{
				if(!this.getPressureArmor(player) && !this.getProtectArmor(player))
	        	{
	        		if(!this.inGravityZone(world, player, true)) 
        			{	        
	        			
	        			if(player.ticksExisted % 50 == 0) {
		        			if(level > 10) player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 10*20));					       
					        if(level > 35) player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10*20));
					        if(level > 25) player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 280, 4));		        			
		        			if(level > 45) player.attackEntityFrom(GSDamageSource.pressure, 2.5F);
	        			}	        			
	        			
				        /*
				        if(GSConfigCore.enableHardMode)
				        {
				        	if(level > 35) player.attackEntityFrom(GSDamageSource.pressure, 1.0F);
				        	if(level > 45) player.attackEntityFrom(GSDamageSource.pressure, 1000.0F);				        			
				        }
				        else
		        		{ 	
				        	if(level > 25) 
		        			{
		        				player.jumpMovementFactor /= 2;//0.5F;
		        				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 280, 4));
		        			} 				        				        				
		        		}*/
        			}	        		
	        	}	
        	}
		}
	}
	
	public static boolean getProtectArmor(EntityPlayerMP player)
	{			
		boolean[] check = new boolean[4];
		boolean flag = false;
		
		for(String string : GSConfigCore.radiation_armor)
		{
			String[] meta = string.split(":");			
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));

			if(meta.length > 2)
			{
				for(int i = 0; i <= 3; i++)
				{
					ItemStack itemstack = new ItemStack(item, 1, Integer.parseInt(meta[2]));
					check[i] = !player.inventory.armorInventory.get(i).isEmpty() && player.inventory.armorInventory.get(i) == itemstack;			
					if(check[i]) break;
				}
			}
			else
				for(int i = 0; i <= 3; i++)
				{
					check[i] = !player.inventory.armorInventory.get(i).isEmpty() && player.inventory.armorInventory.get(i).getItem() == item;			
					if(check[i]) break;
				}
			
				
			if(check[0] && check[1] && check[2] && check[3]) break;
			
		}		
		
		return check[0] && check[1] && check[2] && check[3];
	}
	
	public static boolean getPressureArmor(EntityPlayerMP player)
	{
		boolean armor1 = !player.inventory.armorInventory.get(0).isEmpty() && player.inventory.armorInventory.get(0).getItem() instanceof IItemPressurized;
		boolean armor2 = !player.inventory.armorInventory.get(1).isEmpty() && player.inventory.armorInventory.get(1).getItem() instanceof IItemPressurized;
		boolean armor3 = !player.inventory.armorInventory.get(2).isEmpty() && player.inventory.armorInventory.get(2).getItem() instanceof IItemPressurized;
		boolean armor4 = !player.inventory.armorInventory.get(3).isEmpty() && player.inventory.armorInventory.get(3).getItem() instanceof IItemPressurized;
			
		
		return armor1 && armor2 && armor3 && armor4;
	}
		
	public static boolean inGravityZone(World world, EntityPlayer player, boolean checkPressureModule)
	{
		
		for (final BlockVec3Dim blockVec : TileEntityGravitationModule.loadedTiles)
		{			
			if (blockVec != null && blockVec.dim == world.provider.getDimension())
            {
				
				TileEntity tile = world.getTileEntity(blockVec.toBlockPos());
				
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
            if (blockVec != null && blockVec.dim == world.provider.getDimension())
            {
            	TileEntity tile = world.getTileEntity(blockVec.toBlockPos());
            	if (tile instanceof TileEntityRadiationStabiliser)
            	{
	            	if (((TileEntityRadiationStabiliser) tile).inBubble(avgX, avgY, avgZ)) return true;
            	}
            }
        }

		return false;
	}
	
	public static boolean inShieldBubble(World world, double avgX, double avgY, double avgZ)
	{
        for (final BlockVec3Dim blockVec : TileEntityPlanetShield.loadedTiles)
        {
            if (blockVec != null && blockVec.dim == world.provider.getDimension())
            {
            	TileEntity tile = world.getTileEntity(blockVec.toBlockPos());
            	if (tile instanceof TileEntityPlanetShield)
            	{
	            	if (((TileEntityPlanetShield) tile).inBubble(avgX, avgY, avgZ)) return true;
            	}
            }
        }

		return false;
	}
		
	public static boolean consumeItemStack(IInventory inventory, ItemStack stack) {
		//GalaxySpace.debug(getAmount(inventory, stack) + "");
		   if(getAmount(inventory, stack) >= stack.getCount()) {			   
		      for (int i = 0; i < inventory.getSizeInventory(); i++) {
		         if(isItemStackEqual(inventory.getStackInSlot(i), stack)){
		            int amount = Math.min(stack.getCount(), inventory.getStackInSlot(i).getCount());
		            if(amount > 0) {
		               inventory.getStackInSlot(i).shrink(amount);
		               if(inventory.getStackInSlot(i).getCount() <= 0) {
		                  inventory.setInventorySlotContents(i, ItemStack.EMPTY);		                  
		               }
		               stack.shrink(amount);
		            }
		            if(stack.getCount() <= 0) {		            	
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
		         amount += inventory.getStackInSlot(i).getCount();
		      }
		   }
		   return amount;
		}

	public static boolean isItemStackEqual(ItemStack stack1, ItemStack stack2) {
		return (!stack1.isEmpty() && !stack2.isEmpty() && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage());
	}
	/*
	public static ItemStack changeStackItem(ItemStack stack, Item item) {
	      ItemStack newStack = new ItemStack(item);
	      newStack.setItemDamage(stack.getItemDamage());
	      newStack.stackTagCompound = stack.stackTagCompound;
	      return newStack;
	}

	*/	
	protected void updateSchematics(EntityPlayerMP player, GCPlayerStats playerStats)
    {
       /* SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicCone));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicBody));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicEngine));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicBooster));
        SchematicRegistry.addUnlockedPage(player, SchematicRegistry.getMatchingRecipeForID(GSConfigSchematics.idSchematicFins));
        */
        /*Collections.sort(playerStats.unlockedSchematics);

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
        }*/
    }
	
	public static void enableFlight(EntityPlayer player, boolean state) {
		ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		if (chest != null && chest.getItem() instanceof IJetpackArmor) {
			((IJetpackArmor) chest.getItem()).switchState(chest, state);
			/*if (((IJetpack) chest.getItem()).isActivated(chest)) {
				player.worldObj.playSoundAtEntity(player, "jetpack:launch", 1, 1);
				JetCore.network.sendToAllAround(new SoundMessage(Item.getIdFromItem(chest.getItem()), player.getCommandSenderName()), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64));
			}*/
		}
	}
	
	protected void throwMeteors(EntityPlayerMP player)
	{
	    World world = player.world;
	    if (world.provider instanceof IAdvancedSpace && !world.isRemote)
	    {
	    	if (((IGalacticraftWorldProvider) world.provider).getMeteorFrequency() > 0 && ConfigManagerCore.meteorSpawnMod > 0.0)
	        {
	    		// final int f = (int) (((IGalacticraftWorldProvider) world.provider).getMeteorFrequency() * 1000D * (1.0 / ConfigManagerCore.meteorSpawnMod));
	    		final int f = 1;
	    		if (world.rand.nextInt(f) == 0) /*&& world.getWorldTime() > 6000L && world.getWorldTime() < 9000L)*/
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

	    				final EntityMeteor meteor = new EntityMeteor(world, player.posX + x, player.posY + y, player.posZ + z, motX - 0.5D, 0, motZ - 0.5D, 1);

	    				if (!world.isRemote)
	    				{
	    					world.spawnEntity(meteor);
	    				}
	    			}
	    		}	               
	        }
	    }
	 }

}
