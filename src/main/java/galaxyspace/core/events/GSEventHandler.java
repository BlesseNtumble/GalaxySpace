package galaxyspace.core.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.api.item.IItemSpaceFood;
import asmodeuscore.core.astronomy.gui.screen.NewGuiCelestialSelection;
import asmodeuscore.core.configs.AsmodeusConfig;
import asmodeuscore.core.event.PressureEvent;
import asmodeuscore.core.event.RadiationEvent;
import asmodeuscore.core.handler.LightningStormHandler;
import asmodeuscore.core.handler.capabilities.ACStatsCapability;
import asmodeuscore.core.network.packet.ACPacketSimple;
import asmodeuscore.core.network.packet.ACPacketSimple.ACEnumSimplePacket;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IJetpackArmor;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.handler.capabilities.GSCapabilityProviderStats;
import galaxyspace.core.handler.capabilities.GSCapabilityProviderStatsClient;
import galaxyspace.core.handler.capabilities.GSCapabilityStatsHandler;
import galaxyspace.core.handler.capabilities.GSStatsCapability;
import galaxyspace.core.handler.capabilities.StatsCapability;
import galaxyspace.core.hooks.GSHooksManager;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import galaxyspace.core.prefab.entities.MultiSeatRocketDriverTracker;
import galaxyspace.core.prefab.items.rockets.ItemTier4Rocket;
import galaxyspace.core.prefab.items.rockets.ItemTier5Rocket;
import galaxyspace.core.prefab.items.rockets.ItemTier6Rocket;
import galaxyspace.core.util.GSDamageSource;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigDimensions;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigDimensions;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.WorldProviderKuiperBelt;
import galaxyspace.systems.SolarSystem.planets.mars.dimension.WorldProviderMars_WE;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemThermalPaddingBase;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemPlasmaSword;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.event.ZeroGravityEvent;
import micdoodle8.mods.galacticraft.api.event.wgen.GCCoreEventPopulate;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import micdoodle8.mods.galacticraft.api.inventory.IInventoryGC;
import micdoodle8.mods.galacticraft.api.item.EnumExtendedInventorySlot;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.entities.EntityLanderBase;
import micdoodle8.mods.galacticraft.core.entities.EntityMeteor;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.EnumModelPacketType;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.ThermalArmorEvent;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.items.ItemTier1Rocket;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.galacticraft.planets.GCPlanetDimensions;
import micdoodle8.mods.galacticraft.planets.asteroids.items.ItemTier3Rocket;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.dimension.WorldProviderMars;
import micdoodle8.mods.galacticraft.planets.mars.items.ItemTier2Rocket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
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
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.server.permission.PermissionAPI;

public class GSEventHandler {

	private static List<BlockToChange> block_to_change = new ArrayList<BlockToChange>();
	private static List<ItemsToChange> items_to_change = new ArrayList<ItemsToChange>();
	
	private static final float hot_temp = 4F; // > 120C
	private static final float warn_temp = 2F; // > 60C
	private static final float cool_temp = -1.2F; // < -36
	private static final float cold_temp = -2F; // < -60
	
	static {
		
		items_to_change.add(new ItemsToChange(new ItemStack(Blocks.FURNACE), Blocks.AIR.getDefaultState()).setOxygenCheck(true));
		block_to_change.add(new BlockToChange(Blocks.WATER.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.ICE.getDefaultState(), 0.0F, true).setParticle("waterbubbles").setOxygenCheck(false));
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(GalaxySpace.MODID)) {
			ConfigManager.sync(GalaxySpace.MODID, Type.INSTANCE);
			GSConfigCore.config.save();    			
			GSConfigDimensions.config.save();    			
			GSConfigSchematics.config.save();    			
			GSConfigEnergy.config.save();    			
			ACConfigCore.config.save();    			
			ACConfigDimensions.config.save();
			BRConfigCore.config.save();
			BRConfigDimensions.config.save();

			GSConfigCore.syncConfig(true);
			GSConfigDimensions.syncConfig(true);
			GSConfigSchematics.syncConfig(true);
			GSConfigEnergy.syncConfig(true);
			ACConfigCore.syncConfig(true);
			ACConfigDimensions.syncConfig(true);
			BRConfigCore.syncConfig(true);
			BRConfigDimensions.syncConfig(true);
			
			GalaxySpace.debug = GSConfigCore.enableDebug;			
			GalaxySpace.instance.debug("reload");
		}
	}
	
	@SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayerMP)
        {        	
            event.addCapability(GSCapabilityStatsHandler.GS_PLAYER_PROPERTIES, new GSCapabilityProviderStats((EntityPlayerMP) event.getObject()));
        }
        else if (event.getObject() instanceof EntityPlayer && ((EntityPlayer)event.getObject()).world.isRemote)
        {
            this.onAttachCapabilityClient(event);
        }
    }

	@SideOnly(Side.CLIENT)
    private void onAttachCapabilityClient(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayerSP)
        {
            event.addCapability(GSCapabilityStatsHandler.GS_PLAYER_PROPERTIES_CLIENT, new GSCapabilityProviderStatsClient((EntityPlayerSP) event.getObject()));
        }
    }
		 
	@SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event)
    {
		StatsCapability oldStats = GSStatsCapability.get(event.getOriginal());
		StatsCapability newStats = GSStatsCapability.get(event.getEntityPlayer());
        newStats.copyFrom(oldStats, !event.isWasDeath()|| event.getOriginal().world.getGameRules().getBoolean("keepInventory"));
   
	}

	
	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload e) {
		if(e.getWorld().provider.getDimensionType() == GCPlanetDimensions.MARS)
			e.getWorld().getPerWorldStorage().saveAllData();
	}
	
	@SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
        	//GalaxySpace.packetPipeline.sendTo(new GSPacketSimple(GSEnumSimplePacket.C_UPDATE_WORLD, GCCoreUtil.getDimensionID(event.player.world)), (EntityPlayerMP)event.player);
        	
        	StatsCapability stats = GSStatsCapability.get(event.player);
        	
        	Integer[] ids = new Integer[256];
        	for(int i = 0; i < stats.getKnowledgeResearches().length; i++) {
        		ids[i] = stats.getKnowledgeResearches()[i];
        	}
        	GalaxySpace.packetPipeline.sendTo(new GSPacketSimple(GSEnumSimplePacket.C_UPDATE_RESEARCHES, GCCoreUtil.getDimensionID(event.player.world), new Object[] {ids}), (EntityPlayerMP)event.player);
        	
        }
    }	
		
	@SubscribeEvent
	public void onFall(LivingFallEvent e) {
		if (e.getEntityLiving() instanceof EntityPlayer) {
			ItemStack chest = ((EntityPlayer) e.getEntityLiving()).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			e.setCanceled(chest != null && chest.getItem() instanceof IJetpackArmor && ((IJetpackArmor) chest.getItem()).canFly(chest, (EntityPlayer) e.getEntityLiving()) && ((IJetpackArmor) chest.getItem()).isActivated(chest));
		}
		
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent e)
	{
		
		
	}
	
	@SubscribeEvent
	public void onDamage(LivingDamageEvent e) {
		if(e.getSource().getTrueSource() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) e.getSource().getTrueSource();
	
			ItemStack stack = player.getHeldItemMainhand();
			
			if(stack.getItem() instanceof ItemPlasmaSword && stack.getTagCompound().getFloat(ItemPlasmaSword.heat) >= 10.0F) {
				player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.overheat")));
				
				e.setCanceled(true);
			}
		}
		
		if(e.getEntityLiving() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) e.getEntityLiving();
			World world = player.getEntityWorld();
			if(!world.isRemote)
			{
				float protect = 0.0F;
				
				for(ItemStack s : player.inventory.armorInventory)
				{
					if(s.getItem() instanceof ItemSpaceSuit && s.hasTagCompound())
					{
						if(((ItemSpaceSuit)s.getItem()).getElectricityStored(s) > 5 && s.getTagCompound().hasKey("protection"))
							protect += 0.1F;
					}
				}
				
				e.setAmount(e.getAmount() - e.getAmount() * protect);
			}
		}
	}

	@SubscribeEvent 
	public void onSetBlock(SetBlockEvent e) {
		
		if(e.world != null && !e.world.isRemote && e.world.provider instanceof IGalacticraftWorldProvider && e.pos != null && e.block != null)
		{
			float thermal = ((IGalacticraftWorldProvider)e.world.provider).getThermalLevelModifier();
			AxisAlignedBB bb = new AxisAlignedBB(e.pos);
				
			for(BlockToChange block : block_to_change)
			{			
				if(block.only_gs_dim && !(e.world.provider instanceof IProviderFreeze))
					continue;		
				
				if(e.world.provider instanceof IProviderFreeze && !((IProviderFreeze)e.world.provider).isFreeze()) continue;
				
				if(block.need_check_temp) { 
					if((e.block == block.state || e.block.getMaterial() == block.state.getMaterial()) && !OxygenUtil.isAABBInBreathableAirBlock(e.world, bb, true))
					{
						if(thermal <= cool_temp)
						{
							e.world.setBlockState(e.pos, block.cold_replaced);
							e.setCanceled(true);
						}
						else if(thermal >= warn_temp) {
							e.world.setBlockState(e.pos, block.hot_replaced);
							block.spawnParticleHotTemp(e.world, e.pos);
							e.setCanceled(true);
						}
					}
				}
				else
				{
					e.world.setBlockState(e.pos, block.cold_replaced);
					block.spawnParticleHotTemp(e.world, e.pos);
					e.setCanceled(true);
				}
			}				
		}
	}
	
	@SubscribeEvent
	public void onUpdateBlocks(UpdateBlockEvent e) {
		
		if(!e.world.isRemote && GSConfigCore.enableOxygenForPlantsAndFoods) {
			if (!e.world.isAreaLoaded(e.pos, 1)) return;
			 			
			if(e.world.provider instanceof IGalacticraftWorldProvider) {
				if(((IGalacticraftWorldProvider)e.world.provider).hasBreathableAtmosphere()) return;
				if(((IGalacticraftWorldProvider)e.world.provider).getCelestialBody().atmosphere.isGasPresent(EnumAtmosphericGas.OXYGEN)) return;
				
				float thermal = ((IGalacticraftWorldProvider)e.world.provider).getThermalLevelModifier();
				boolean thermal_check = thermal > warn_temp || thermal < cool_temp;
				AxisAlignedBB bb = new AxisAlignedBB(e.pos);
				if(!OxygenUtil.isAABBInBreathableAirBlock(e.world, bb, thermal_check)) {
					
					if(e.block.getBlock() instanceof BlockLeaves) {	
						e.world.setBlockState(e.pos, GSBlocks.DRY_LEAVES.getDefaultState(), 3);
						e.setCanceled(true);
					}
									
					if(e.block.getBlock() instanceof BlockBush && e.block.getBlock() != Blocks.DEADBUSH) {
						if(e.world.getBlockState(e.pos.down()).isBlockNormalCube()) {
							e.world.setBlockState(e.pos, Blocks.DEADBUSH.getDefaultState(), 3);
						}
						else e.world.setBlockToAir(e.pos);
						e.setCanceled(true);						
					}
				}
			}
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
		
		final IBlockState state = world.getBlockState(event.getPos());
		final Block block = state.getBlock();
		ItemStack stack = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
			
		
		
		if(world.isRemote)
		{	

		}
		
		if(!world.isRemote && !block.hasTileEntity(state))
		{		
			if(CompatibilityManager.isIc2Loaded())
			{
				//IC2 WIND TURBINE
				if(world.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider)world.provider).hasNoAtmosphere() && ((IGalacticraftWorldProvider)world.provider).getWindLevel() <= 0.0F) {
					if(stack.getItem() == Item.getByNameOrId("ic2:te") && (stack.getItemDamage() == 11 || stack.getItemDamage() == 21)) {
						player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.nowind.name") + "! " + GCCoreUtil.translate("gui.message.cant_place")));	
						event.setCanceled(true);
					}
				}
			}

			//ICE BUCKET
			if(block == Blocks.ICE && stack.getItem() == Items.BUCKET)
			{				
				stack.shrink(1);
				player.inventory.addItemStackToInventory(new ItemStack(GSItems.BASIC, 1, 17));				
				world.setBlockToAir(event.getPos());
			}						
			
			if(world.provider instanceof IGalacticraftWorldProvider)
			{
				AxisAlignedBB bb = new AxisAlignedBB(event.getPos().up());
				float thermal = ((IGalacticraftWorldProvider)world.provider).getThermalLevelModifier();

				for(ItemsToChange ore : items_to_change)
				{					
					if(stack.getItem().equals(ore.itemstack.getItem()) && !((IGalacticraftWorldProvider)world.provider).hasBreathableAtmosphere())
					{		
						if(FluidUtil.isFilledContainer(stack) && world.getTileEntity(event.getPos()) != null)
							if(world.getTileEntity(event.getPos()) instanceof IFluidHandlerWrapper || world.getTileEntity(event.getPos()) instanceof IFluidHandler)
								return;
						
						if(ore.need_check_oxygen)
						{
							if(!OxygenUtil.isAABBInBreathableAirBlock(world, bb, ore.need_check_temp && (thermal > 2.0F || thermal < -2.0F)))
							{
								player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needoxygen")));				   
								event.setCanceled(true);
							}
						}						
						
						if(ore.need_check_temp)
						{
							if(!GSUtils.getThermalControl(world, event.getPos().up()) && (thermal > 2.0F || thermal < -2.0F))
							{
								player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translate("gui.message.needthermal")));				   
								event.setCanceled(true);
							}
						}
						
						if(ore.replaced != Blocks.AIR.getDefaultState())
							if(world.getBlockState(event.getPos()).getBlock().isReplaceable(world, event.getPos()))
								world.setBlockState(event.getPos(), ore.replaced);
							else
								world.setBlockState(event.getPos().up(), ore.replaced);
						break;
					}
				}			
				
			}				
		}
	}

	@SubscribeEvent
	public void onEntityInteract(PlayerInteractEvent.EntityInteract event)
	{
		EntityPlayer player = event.getEntityPlayer();
		
		if(!event.getWorld().isRemote) {
			
			ItemStack stack = event.getItemStack();
			//if(stack.isItemEqual(ItemBasicGS.BasicItems.ANIMAL_CELL.getItemStack()))
			//{
				//stack.setTagCompound(new NBTTagCompound());				
			//}
				
			if(event.getTarget() instanceof EntityWolf && !(event.getTarget() instanceof EntityAstroWolf))
			{
				EntityWolf wolf = (EntityWolf) event.getTarget();
				if(wolf.isTamed() && event.getItemStack().getItem() == GCItems.oxMask)
				{
					EntityAstroWolf astrowolf = new EntityAstroWolf(wolf.getEntityWorld());
					astrowolf.setOwnerId(wolf.getOwnerId());
					astrowolf.setTamed(wolf.isTamed());
					astrowolf.setAngry(wolf.isAngry());
					astrowolf.setHealth(wolf.getHealth());
					astrowolf.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
					astrowolf.setSitting(wolf.isSitting());
					astrowolf.setPositionAndRotation(wolf.posX, wolf.posY, wolf.posZ, wolf.rotationYaw, wolf.rotationPitch);		
					event.getTarget().setDead();
					event.getWorld().spawnEntity(astrowolf);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event)
	{
		World world = event.getWorld();
		if (world == null) {
			return;
		}			
				
		EntityPlayer player = event.getEntityPlayer();					
		ItemStack i = event.getItemStack();
				
		if(!world.isRemote && GalaxySpace.debug) 
			GalaxySpace.instance.debug(Item.REGISTRY.getNameForObject(i.getItem()) + " | " + i.getTranslationKey() + " | " + Biome.getBiome(Biome.getIdForBiome(world.getBiomeForCoordsBody(event.getPos()))));
		
					
		if(!world.isRemote && GSConfigCore.enableOxygenForPlantsAndFoods && !player.capabilities.isCreativeMode)
		{	
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

	@SubscribeEvent
	public void onPortalCreated(BlockEvent.PortalSpawnEvent e)
	{
		World world = e.getWorld();
		if(world != null) {
			if(world.provider instanceof IGalacticraftWorldProvider) {
				e.setCanceled(!((IGalacticraftWorldProvider)world.provider).netherPortalsOperational());
			}
		}
	}
	
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
			final StatsCapability gsstats = GSStatsCapability.get(player);
		
			LightningStormHandler.spawnLightning(player);
			
			// Added code
			if(!world.isRemote) {
				MultiSeatRocketDriverTracker.get().update();
			}
			//
			
        	//this.updateSchematics(player, stats);
			//this.throwMeteors(player);
			
			//if(gs_stats.getKnowledgeResearch()[0] > 0)
			//{
			//GalaxySpace.debug(gs_stats.getKnowledgeResearch()[0] + "");
			//}
			//GalaxySpace.instance.debug(GSFluids.NaturalGas.getBlock().getDefaultState().getMaterial() + "");
			
			if(world.getTotalWorldTime() % 15 == 0 && !this.getProtectArmor(player) && world.provider instanceof WorldProviderTitan && world.isRaining())
			{
				boolean flag = true;
				for(int y = 256; y > player.getPosition().getY(); y--)
				{
					if(!world.isAirBlock(new BlockPos(player.getPosition().getX(), y, player.getPosition().getZ()))) {
						flag = false;
						break;
					}
				}
				if(flag) player.attackEntityFrom(GSDamageSource.acid, 0.5F);
			}

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
			
			
			//
			if (stats.getShieldControllerInSlot().isItemEqual(new ItemStack(GSItems.BASIC, 1, 16)))
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

	        //stats.setLastShieldControllerInSlot(stats.getShieldControllerInSlot());
	       
	       
			ItemStack stack = player.inventory.armorInventory.get(3);
			IInventoryGC inv = AccessInventoryGC.getGCInventoryForPlayer(player);
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemSpaceSuit && player.isInWater() && stack.hasTagCompound() && stack.getTagCompound().hasKey("water_breathing"))
				{
					int count = 300;
					int air = player.getAir();
					for(int i = 2; i <= 3; i++)
					{
						if(!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItemDamage() != inv.getStackInSlot(i).getMaxDamage())
						{
							
							if(air < count)
							{
								player.setAir(count);
								
								if(inv.getStackInSlot(i).getItem() != GCItems.oxygenCanisterInfinite)
									inv.getStackInSlot(i).setItemDamage(inv.getStackInSlot(i).getItemDamage() + 1);
							}
							break;
						}
							
					}					
		        }
			}
			
			if(OxygenUtil.isAABBInBreathableAirBlock(player) || player.world.provider.getDimensionType() == DimensionType.OVERWORLD)
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
		
	}
	
	
	public static void consumeOxygenFromTank(EntityPlayerMP player, int consume_count) {
		IInventoryGC inv = AccessInventoryGC.getGCInventoryForPlayer(player);
		for (int i = 2; i <= 3; i++)
			if (!inv.getStackInSlot(i).isEmpty()) {
				ItemStack tank = inv.getStackInSlot(i);
				if (tank.getItemDamage() != tank.getMaxDamage()) {
					if (player.ticksExisted % 10 == 0) {
						tank.setItemDamage(tank.getItemDamage() + consume_count);
					}
					break;
				}
			}		
	}
	
	public static boolean isValidOxygenTanks(EntityPlayerMP player) {
		boolean valid = false;
		IInventoryGC inv = AccessInventoryGC.getGCInventoryForPlayer(player);
		for (int i = 2; i <= 3; i++) 
			if (!inv.getStackInSlot(i).isEmpty()) {
				ItemStack tank = inv.getStackInSlot(i);
				if (tank.getItemDamage() != tank.getMaxDamage()) {
					valid = true;
					break;
				}
			}
		
		return valid;
	}
	
	@SubscribeEvent
	public void onThermalArmorEvent(ThermalArmorEvent event) {		
		if (event.armorStack == ItemStack.EMPTY) {
			event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.REMOVE);
			return;
		}
		if (event.armorStack.getItem() instanceof ItemThermalPaddingBase && event.armorStack.getItemDamage() == event.armorIndex) {
			event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.ADD);
			return;
		}

		event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.NOTHING);
	}
	
	@SubscribeEvent
    public void onPlanetDecorated(GCCoreEventPopulate.Post event)
    {
		if(GSConfigCore.enableMarsNewOres && (event.world.provider instanceof WorldProviderMars || event.world.provider instanceof WorldProviderMars_WE))
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
	public void onZeroGravity(ZeroGravityEvent.InFreefall event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if(inGravityZone(entity.getEntityWorld(), entity, false))
			event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onRadiation(RadiationEvent event)
	{
		EntityLivingBase living = (EntityLivingBase) event.getEntity();
		
		if(living instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) living;			
			
			
			boolean checkAirLock = false;
			for(int y = 0; y < 255; y++) {
				if(player.world.getBlockState(player.getPosition().up(y)).getBlock() == GCBlocks.airLockSeal) {
					checkAirLock = true;
					break;
				}
			}
			if(!player.capabilities.isCreativeMode) 
				event.setCanceled(checkAirLock || !GSConfigCore.enableRadiationSystem || this.getProtectArmor(player) || player.getRidingEntity() instanceof EntityLanderBase || player.getRidingEntity() instanceof EntityTieredRocket || this.inRadiationBubble(player.getEntityWorld(), player.posX, player.posY, player.posZ));		
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
			float level = event.getPressureLevel();
			
			if(!player.capabilities.isCreativeMode)     				        		
	        	event.setCanceled(!GSConfigCore.enablePressureSystem || this.getProtectArmor(player) || this.inGravityZone(world, player, true) || player.getRidingEntity() instanceof EntityLanderBase || player.getRidingEntity() instanceof EntityTieredRocket);
        	
		}
	}
	
	public static boolean getProtectArmor(EntityPlayerMP player)
	{			
		boolean[] check = new boolean[4];
		//boolean flag = false;
		
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
		
		
	public static boolean inGravityZone(World world, EntityLivingBase player, boolean checkStabilisationModule)
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
						if(!checkStabilisationModule) return true;
						
						if(checkStabilisationModule)
						{
							for(int i = 0; i < 4; i++)
								if(gravity.getStackInSlot(i + 1).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 1)))								
									return true;
								
							return false;
						}
							
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
		return (!stack1.isEmpty() && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE));
	}
	
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
	    				x = world.rand.nextInt(1) - 4;
	    				y = world.rand.nextInt(20) + 200;
	    				z = world.rand.nextInt(1) - 4;
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

	private static class BlockToChange
	{
		private IBlockState state, hot_replaced, cold_replaced;
		private float temp;
		private boolean need_check_temp, need_check_oxygen, only_gs_dim = false;
		private String particle_name = "";
		
		BlockToChange(IBlockState state, IBlockState hot_replaced, IBlockState cold_replaced, float temp, boolean need_check_temp)
		{
			this.state = state;
			this.hot_replaced = hot_replaced;
			this.cold_replaced = cold_replaced;
			this.temp = temp;
			this.need_check_temp = need_check_temp;
		}
				
		public BlockToChange setParticle(String s)
		{
			this.particle_name = s;
			return this;
		}
		
		public BlockToChange setOnlyGSDim()
		{
			this.only_gs_dim = true;
			return this;
		}
		
		public BlockToChange setOxygenCheck(boolean check)
		{
			this.need_check_oxygen = check;
			return this;
		}
	
		void spawnParticleHotTemp(World world, BlockPos pos)
		{
			if(!particle_name.isEmpty())
				for(int i = 0; i < 5; i++)
					GalaxySpace.proxy.spawnParticle(particle_name, new Vector3(pos.getX() + world.rand.nextDouble(), pos.getY() + 1.0D + world.rand.nextDouble(), pos.getZ() + world.rand.nextDouble()), new Vector3(0.0D, 0.001D, 0.0D), new Object [] { 10, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );  	
		}
	}	
	
	private static class ItemsToChange
	{
		private ItemStack itemstack = ItemStack.EMPTY;
		private IBlockState replaced;
		private boolean need_check_temp, need_check_oxygen, only_gs_dim = false;
		
		ItemsToChange(ItemStack stack, IBlockState placed)
		{
			this.itemstack = stack;
			this.replaced = placed;
			
		}
		
		public ItemsToChange setOnlyGSDim()
		{
			this.only_gs_dim = true;
			return this;
		}
		
		public ItemsToChange setOxygenCheck(boolean check)
		{
			this.need_check_oxygen = check;
			return this;
		}
		
		public ItemsToChange setTempCheck(boolean check)
		{
			this.need_check_temp = check;
			return this;
		}
		
	}
}
