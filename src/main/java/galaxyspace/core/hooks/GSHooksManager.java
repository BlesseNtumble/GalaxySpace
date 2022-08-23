package galaxyspace.core.hooks;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.core.event.AsmodeusEvent;
import asmodeuscore.core.handler.capabilities.ACStatsCapability;
import asmodeuscore.core.handler.capabilities.IStatsCapability;
import asmodeuscore.core.network.packet.ACPacketSimple;
import asmodeuscore.core.network.packet.ACPacketSimple.ACEnumSimplePacket;
import galaxyspace.api.block.IEnergyGeyser;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.events.SetBlockEvent;
import galaxyspace.core.events.UpdateBlockEvent;
import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import galaxyspace.core.prefab.entities.EntityMultiSeatRocket;
import galaxyspace.core.util.GSThreadVersionCheck;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS.BasicItems;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.entity.IEntityNoisy;
import micdoodle8.mods.galacticraft.api.item.IItemThermal;
import micdoodle8.mods.galacticraft.api.item.ISensorGlassesArmor;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntitySpaceshipBase;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.BubbleRenderer;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.client.FootprintRenderer;
import micdoodle8.mods.galacticraft.core.client.SkyProviderMoon;
import micdoodle8.mods.galacticraft.core.client.SkyProviderOverworld;
import micdoodle8.mods.galacticraft.core.client.gui.GuiIdsCore;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.client.jei.GalacticraftJEI;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderMoon;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderSpaceStation;
import micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager;
import micdoodle8.mods.galacticraft.core.entities.EntityCelestialFake;
import micdoodle8.mods.galacticraft.core.entities.IBubbleProviderColored;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.fluid.FluidNetwork;
import micdoodle8.mods.galacticraft.core.items.ItemBasic;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.network.GalacticraftPacketHandler;
import micdoodle8.mods.galacticraft.core.network.PacketRotateRocket;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.tick.KeyHandlerClient;
import micdoodle8.mods.galacticraft.core.tick.TickHandlerClient;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenSealer;
import micdoodle8.mods.galacticraft.core.tile.TileEntityScreen;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.DamageSourceGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.MapUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.core.wrappers.BlockMetaList;
import micdoodle8.mods.galacticraft.core.wrappers.Footprint;
import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
import micdoodle8.mods.galacticraft.planets.asteroids.dimension.WorldProviderAsteroids;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityCreeperBoss;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityGasLiquefier;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityMethaneSynthesizer;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import micdoodle8.mods.galacticraft.planets.venus.dimension.WorldProviderVenus;
import micdoodle8.mods.galacticraft.planets.venus.tile.TileEntityGeothermalGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.server.permission.PermissionAPI;

public class GSHooksManager {
	
	@Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = false) 
    public static boolean setBlockState(World world, BlockPos pos, IBlockState newState, int flags) { 
    	return MinecraftForge.EVENT_BUS.post(new SetBlockEvent(world, pos, newState, flags)); 
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS, isMandatory = true)
	public static void randomTick(Block block, World worldIn, BlockPos pos, IBlockState state, Random rand) {
		UpdateBlockEvent event = new UpdateBlockEvent(worldIn, pos, state, rand);
		MinecraftForge.EVENT_BUS.post(event);
		
		if(!event.isCanceled())
			block.updateTick(worldIn, pos, state, rand);
	}
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static int getAirProducts(TileEntityMethaneSynthesizer te) {
		
		WorldProvider WP = te.getWorld().provider;
        if (WP instanceof IGalacticraftWorldProvider)
        {
            ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider) WP).getCelestialBody().atmosphere.composition;
           
            if(atmos.contains(EnumAtmosphericGas.CO2))
            	return 1;
            /*
            if (atmos.size() > 0)
            {
                if (atmos.get(0) == EnumAtmosphericGas.CO2)
                {
                    return 1;
                }
            }
            if (atmos.size() > 1)
            {
                if (atmos.get(1) == EnumAtmosphericGas.CO2)
                {
                    return 1;
                }
            }
            if (atmos.size() > 2)
            {
                if (atmos.get(2) == EnumAtmosphericGas.CO2)
                {
                    return 1;
                }
            }
            */
            return 0;
        }

        return 0;
	}
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static int getAirProducts(TileEntityGasLiquefier te) {
		WorldProvider WP = te.getWorld().provider;
		if (WP instanceof IGalacticraftWorldProvider) {
			int result = 0;
			ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider) WP)
					.getCelestialBody().atmosphere.composition;
			if (atmos.size() > 0) {
				result = te.getIdFromName(atmos.get(0).name().toLowerCase()) + 1;
			}
			if (atmos.size() > 1) {
				result += 16 * (te.getIdFromName(atmos.get(1).name().toLowerCase()) + 1);
			}
			if (atmos.size() > 2) {
				result += 256 * (te.getIdFromName(atmos.get(2).name().toLowerCase()) + 1);
			}

			return result;
		}

		return 35;
	}
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void checkThermalStatus(GCPlayerHandler handler, EntityPlayerMP player, GCPlayerStats playerStats)
    {
        if (player.world.provider instanceof IGalacticraftWorldProvider && !player.capabilities.isCreativeMode && !CompatibilityManager.isAndroid(player))
        {
            final ItemStack thermalPaddingHelm = playerStats.getExtendedInventory().getStackInSlot(6);
            final ItemStack thermalPaddingChestplate = playerStats.getExtendedInventory().getStackInSlot(7);
            final ItemStack thermalPaddingLeggings = playerStats.getExtendedInventory().getStackInSlot(8);
            final ItemStack thermalPaddingBoots = playerStats.getExtendedInventory().getStackInSlot(9);
            float lowestThermalStrength = 0.0F;
            if (!thermalPaddingHelm.isEmpty() && !thermalPaddingChestplate.isEmpty() && !thermalPaddingLeggings.isEmpty() && !thermalPaddingBoots.isEmpty())
            {
                if (thermalPaddingHelm.getItem() instanceof IItemThermal)
                {
                    lowestThermalStrength += ((IItemThermal) thermalPaddingHelm.getItem()).getThermalStrength();
                }
                if (thermalPaddingChestplate.getItem() instanceof IItemThermal)
                {
                    lowestThermalStrength += ((IItemThermal) thermalPaddingChestplate.getItem()).getThermalStrength();
                }
                if (thermalPaddingLeggings.getItem() instanceof IItemThermal)
                {
                    lowestThermalStrength += ((IItemThermal) thermalPaddingLeggings.getItem()).getThermalStrength();
                }
                if (thermalPaddingBoots.getItem() instanceof IItemThermal)
                {
                    lowestThermalStrength += ((IItemThermal) thermalPaddingBoots.getItem()).getThermalStrength();
                }
                lowestThermalStrength /= 4.0F;
                lowestThermalStrength = Math.abs(lowestThermalStrength);  //It shouldn't be negative, but just in case!
            }

            IGalacticraftWorldProvider provider = (IGalacticraftWorldProvider) player.world.provider;
            float thermalLevelMod = provider.getThermalLevelModifier();
          
            if(player.world.provider instanceof IAdvancedSpace)
            {
            	provider = (IAdvancedSpace) player.world.provider;
            	thermalLevelMod = ((IAdvancedSpace)provider).getThermalLevelModifier(player);
            }
            //System.out.println(thermalLevelMod);
            
            
            float absThermalLevelMod = Math.abs(thermalLevelMod);

            if (absThermalLevelMod > 0D)
            {
                int thermalLevelCooldownBase = Math.abs(MathHelper.floor(200 / thermalLevelMod));
                int normaliseCooldown = MathHelper.floor(150 / lowestThermalStrength);
                int thermalLevelTickCooldown = thermalLevelCooldownBase;
                if (thermalLevelTickCooldown < 1)
                {
                    thermalLevelTickCooldown = 1;   //Prevent divide by zero errors
                }

                if (!thermalPaddingHelm.isEmpty() && !thermalPaddingChestplate.isEmpty() && !thermalPaddingLeggings.isEmpty() && !thermalPaddingBoots.isEmpty())
                {
                    //If the thermal strength exceeds the dimension's thermal level mod, it can't improve the normalise
                    //This factor of 1.5F is chosen so that a combination of Tier 1 and Tier 2 thermal isn't enough to normalise on Venus (three Tier 2, one Tier 1 stays roughly constant)
                    float relativeFactor = Math.max(1.0F, absThermalLevelMod / lowestThermalStrength) / 1.5F;
                    normaliseCooldown = MathHelper.floor(normaliseCooldown / absThermalLevelMod * relativeFactor);
                    if (normaliseCooldown < 1)
                    {
                        normaliseCooldown = 1;   //Prevent divide by zero errors
                    }
                    // Player is wearing all required thermal padding items
                    if ((player.ticksExisted - 1) % normaliseCooldown == 0)
                    {
                    	handler.normaliseThermalLevel(player, playerStats, 1);
                    }
                    thermalLevelMod /= Math.max(1.0F, lowestThermalStrength / 2.0F);
                    absThermalLevelMod = Math.abs(thermalLevelMod);
                }

                if (OxygenUtil.isAABBInBreathableAirBlock(player, true))
                {
                    playerStats.setThermalLevelNormalising(true);
                    handler.normaliseThermalLevel(player, playerStats, 1);
                    // If player is in ambient thermal area, slowly reset to normal
                    return;
                }

                // For each piece of thermal equipment being used, slow down the the harmful thermal change slightly
                if (!thermalPaddingHelm.isEmpty())
                {
                    thermalLevelTickCooldown += thermalLevelCooldownBase;
                }
                if (!thermalPaddingChestplate.isEmpty())
                {
                    thermalLevelTickCooldown += thermalLevelCooldownBase;
                }
                if (!thermalPaddingLeggings.isEmpty())
                {
                    thermalLevelTickCooldown += thermalLevelCooldownBase;
                }
                if (!thermalPaddingBoots.isEmpty())
                {
                    thermalLevelTickCooldown += thermalLevelCooldownBase;
                }

                // Instead of increasing/decreasing the thermal level by a large amount every ~200 ticks, increase/decrease
                //      by a small amount each time (still the same average increase/decrease)
                int thermalLevelTickCooldownSingle = MathHelper.floor(thermalLevelTickCooldown / absThermalLevelMod);
                if (thermalLevelTickCooldownSingle < 1)
                {
                    thermalLevelTickCooldownSingle = 1;   //Prevent divide by zero errors
                }

                if ((player.ticksExisted - 1) % thermalLevelTickCooldownSingle == 0)
                {
                    int last = playerStats.getThermalLevel();
                    playerStats.setThermalLevel((int) Math.min(Math.max(last + (thermalLevelMod < 0 ? -1 : 1), -22), 22));

                    if (playerStats.getThermalLevel() != last)
                    {
                    	//handler.sendThermalLevelPacket(player, playerStats);
                    	GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_UPDATE_THERMAL_LEVEL, GCCoreUtil.getDimensionID(player.world), new Object[] { playerStats.getThermalLevel(), playerStats.isThermalLevelNormalising() }), player);

                    }
                }

                // If the normalisation is outpacing the freeze/overheat
                playerStats.setThermalLevelNormalising(thermalLevelTickCooldownSingle > normaliseCooldown &&
                        !thermalPaddingHelm.isEmpty() &&
                        !thermalPaddingChestplate.isEmpty() &&
                        !thermalPaddingLeggings.isEmpty() &&
                        !thermalPaddingBoots.isEmpty());

                if (!playerStats.isThermalLevelNormalising())
                {
                    if ((player.ticksExisted - 1) % thermalLevelTickCooldown == 0)
                    {
                        if (Math.abs(playerStats.getThermalLevel()) >= 22)
                        {
                            player.attackEntityFrom(DamageSourceGC.thermal, 1.5F);
                        }
                    }

                    if (playerStats.getThermalLevel() < -15)
                    {
                        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5, 2, true, true));
                    }

                    if (playerStats.getThermalLevel() > 15)
                    {
                        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5, 2, true, true));
                    }
                }
            }
            else
            //Normalise thermal level if on Space Station or non-modifier planet
            {
                playerStats.setThermalLevelNormalising(true);
                handler.normaliseThermalLevel(player, playerStats, 2);
            }
        }
        else
        //Normalise thermal level if on Overworld or any non-GC dimension
        {
            playerStats.setThermalLevelNormalising(true);
            handler.normaliseThermalLevel(player, playerStats, 3);
        }
    }

	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static double getSolarEnergyMultiplier(WorldProviderVenus wp) {
		return 0;
	}
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static ItemStack getGuaranteedLoot(EntityCreeperBoss creeper, Random rand)
	{
		  List<ItemStack> stackList = new LinkedList<>();
	      stackList.addAll(GalacticraftRegistry.getDungeonLoot(2));
	      
	      return stackList.get(rand.nextInt(stackList.size())).copy();
	}
	
	@Hook(injectOnLine=59, returnCondition = ReturnCondition.ALWAYS)
    public static void update(TileEntityGeothermalGenerator te)
    {		
		if (te.ticks % 20 == 0)
        {
            BlockPos below = te.getPos().down();
            IBlockState stateBelow = te.getWorld().getBlockState(below);

            boolean lastValidSpout = ReflectionHelper.getPrivateValue(TileEntityGeothermalGenerator.class, te, "validSpout");
            boolean validSpoutHook = false;
            ReflectionHelper.setPrivateValue(TileEntityGeothermalGenerator.class, te, validSpoutHook, "validSpout");
            if (stateBelow.getBlock() instanceof IEnergyGeyser)
            {
                BlockPos pos1 = below.down();
                for (; te.getPos().getY() - pos1.getY() < 20; pos1 = pos1.down())
                {
                    IBlockState state = te.getWorld().getBlockState(pos1);
                    IEnergyGeyser geyser = (IEnergyGeyser) stateBelow.getBlock();
                    boolean work = geyser.isWorkGeyser(te.getWorld(), stateBelow, below);
                    
                    if (work)
                    {
                    	
                    	validSpoutHook = true;
                        ReflectionHelper.setPrivateValue(TileEntityGeothermalGenerator.class, te, validSpoutHook, "validSpout");
                        break;
                    }
                    else if (!state.getBlock().isAir(te.getWorld().getBlockState(pos1), te.getWorld(), pos1))
                    {
                        // Not valid
                        break;
                    }
                                      
                }
                
            }
            else if (stateBelow.getBlock() == VenusBlocks.spout)
            {
                BlockPos pos1 = below.down();
                for (; te.getPos().getY() - pos1.getY() < 20; pos1 = pos1.down())
                {
                    IBlockState state = te.getWorld().getBlockState(pos1);
                    if (state.getBlock() == VenusModule.sulphuricAcid.getBlock())
                    {
                    	validSpoutHook = true;
                        ReflectionHelper.setPrivateValue(TileEntityGeothermalGenerator.class, te, validSpoutHook, "validSpout");
                        break;
                    }
                    else if (!state.getBlock().isAir(te.getWorld().getBlockState(pos1), te.getWorld(), pos1))
                    {
                        // Not valid
                        break;
                    }
                }
            }

            if (te.getWorld().isRemote && validSpoutHook != lastValidSpout)
            {
                // Update active texture
                IBlockState state = te.getWorld().getBlockState(te.getPos());
                te.getWorld().notifyBlockUpdate(te.getPos(), state, state, 3);
            }
        }
		
		if (!te.getWorld().isRemote)
        {
            te.recharge(te.getInventory().get(0));

            if (te.disableCooldown > 0)
            {
                te.disableCooldown--;
            }

            te.generateWatts = Math.min(Math.max(getGenerate(te, ReflectionHelper.getPrivateValue(TileEntityGeothermalGenerator.class, te, "validSpout")), 0), TileEntityGeothermalGenerator.MAX_GENERATE_GJ_PER_TICK);
        }
        else
        {
            if (te.generateWatts > 0 && te.ticks % ((int) ((float)te.MAX_GENERATE_GJ_PER_TICK / (te.generateWatts + 1)) * 5 + 1) == 0)
            {
                double posX = te.getPos().getX() + 0.5;
                double posY = te.getPos().getY() + 1.0;
                double posZ = te.getPos().getZ() + 0.5;
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX - 0.25, posY, posZ - 0.25), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX - 0.25, posY, posZ), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX - 0.25, posY, posZ + 0.25), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX, posY, posZ - 0.25), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX, posY, posZ), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX, posY, posZ + 0.25), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX + 0.25, posY, posZ - 0.25), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX + 0.25, posY, posZ), new Vector3(0.0, 0.025, 0.0));
                GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX + 0.25, posY, posZ + 0.25), new Vector3(0.0, 0.025, 0.0));
            }
        }

        te.produce();
    }
	
	private static int getGenerate(TileEntityGeothermalGenerator te, boolean valid)
    {
        if (te.getDisabled(0))
        {
            return 0;
        }

        if (!valid)
        {
            return 0;
        }

        int diff = TileEntityGeothermalGenerator.MAX_GENERATE_GJ_PER_TICK - TileEntityGeothermalGenerator.MIN_GENERATE_GJ_PER_TICK;
        return (int) Math.floor((Math.sin(te.ticks / 50.0F) * 0.5F + 0.5F) * diff + TileEntityGeothermalGenerator.MIN_GENERATE_GJ_PER_TICK);
    }
	
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float getGravity(WorldProviderAsteroids wp)
    {
    	if(GSConfigCore.enableZeroGravityOnAsteroids)
    		return 0.08F;
    	
    	return 0.072F;
    } 
/*
	private static List<BlockPos> connectedPads = new ArrayList<BlockPos>();
	private static Ticket chunkLoadTicket;
	
	@Hook(injectOnLine=88)
    public static void update(TileEntityLaunchController te)
    {		
        if (!te.getWorld().isRemote)
        {
      		te.controlEnabled = te.launchSchedulingEnabled && te.hasEnoughEnergyToRun && !te.getDisabled(0);
        	
      		boolean frequencyCheckNeeded = ReflectionHelper.getPrivateValue(TileEntityLaunchController.class, te, "frequencyCheckNeeded");
        	if (frequencyCheckNeeded)
            {
                te.checkDestFrequencyValid();
                frequencyCheckNeeded = false;
            }
        	ReflectionHelper.setPrivateValue(TileEntityLaunchController.class, te, frequencyCheckNeeded, "frequencyCheckNeeded");
           
        	if (te.requiresClientUpdate)
            {
                // PacketDispatcher.sendPacketToAllPlayers(this.getPacket());
                // TODO
                te.requiresClientUpdate = false;
            }

            if (te.ticks % 40 == 0)
            {
                te.setFrequency(te.frequency);
                te.setDestinationFrequency(te.destFrequency);
                
                //System.out.println(te.attachedDock);
            }

            //Ticket chunkLoadTicket = ReflectionHelper.getPrivateValue(TileEntityLaunchController.class, te, "chunkLoadTicket");
            //List<BlockPos> connectedPads = ReflectionHelper.getPrivateValue(TileEntityLaunchController.class, te, "connectedPads");
            if (te.ticks % 20 == 0)
            {
                if (chunkLoadTicket != null)
                {                	
                    for (int i = 0; i < connectedPads.size(); i++)
                    {
                        BlockPos coords = connectedPads.get(i);
                        Block block = te.getWorld().getBlockState(coords).getBlock();
                        TileEntity tile = te.getWorld().getTileEntity(coords);
                        
                        if (!(tile instanceof IFuelDock))
                        {
                        	connectedPads.remove(i);
                        	//ReflectionHelper.setPrivateValue(TileEntityLaunchController.class, te, connectedPads, "connectedPads");
                            ForgeChunkManager.unforceChunk(chunkLoadTicket, new ChunkPos(coords.getX() >> 4, coords.getZ() >> 4));
                        }
                    }
                }
            }
        }
        else
        {
            if (te.frequency == -1 && te.destFrequency == -1)
            {
                GalacticraftCore.packetPipeline.sendToServer(new PacketSimpleMars(EnumSimplePacketMars.S_UPDATE_ADVANCED_GUI, GCCoreUtil.getDimensionID(te.getWorld()), new Object[] { 5, te.getPos(), 0 }));
            }
        }
        return;
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onTicketLoaded(TileEntityLaunchController te, Ticket ticket, boolean placed)
    {
        if (!te.getWorld().isRemote && ConfigManagerMars.launchControllerChunkLoad)
        {
            if (ticket == null)
            {
                return;
            }
           
            if (chunkLoadTicket == null)
            {
            	chunkLoadTicket = ticket;
            }
   
            NBTTagCompound nbt = chunkLoadTicket.getModData();
            nbt.setInteger("ChunkLoaderTileX", te.getPos().getX());
            nbt.setInteger("ChunkLoaderTileY", te.getPos().getY());
            nbt.setInteger("ChunkLoaderTileZ", te.getPos().getZ());

            //List<BlockPos> connectedPads = ReflectionHelper.getPrivateValue(TileEntityLaunchController.class, te, "connectedPads");
            
            
            for (int x = -3; x <= 3; x++)
            {
                for (int z = -3; z <= 3; z++)
                {
                    TileEntity tile = te.getWorld().getTileEntity(te.getPos().add(x, 0, z));
                    Block blockID = te.getWorld().getBlockState(te.getPos().add(x, 0, z)).getBlock();

                    if (tile instanceof IFuelDock)
                    {
                    	if (te.getPos().getX() + x >> 4 != te.getPos().getX() >> 4 || te.getPos().getZ() + z >> 4 != te.getPos().getZ() >> 4)
                        {
                        	
                            connectedPads.add(new BlockPos(te.getPos().getX() + x, te.getPos().getY(), te.getPos().getZ() + z));
                        	//ReflectionHelper.setPrivateValue(TileEntityLaunchController.class, te, connectedPads, "connectedPads");
                            
                            if (placed)
                            {
                                ChunkLoadingCallback.forceChunk(chunkLoadTicket, te.getWorld(), te.getPos().getX() + x, te.getPos().getY(), te.getPos().getZ() + z, te.getOwnerName());
                            }
                            else
                            {
                                ChunkLoadingCallback.addToList(te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), te.getOwnerName());
                            }
                        }
                    }
                }
            }

            ChunkLoadingCallback.forceChunk(chunkLoadTicket, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), te.getOwnerName());
        }
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean canAttachToLandingPad(TileEntityLaunchController te, IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        //GalaxySpace.instance.debug(tile);
        return tile instanceof IFuelDock;
    }
	

	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void updateRocketOnDockSettings(TileEntityLaunchController te)
    {
        if (te.attachedDock instanceof IFuelDock)
        {
       	
        	IFuelDock pad = ((IFuelDock) te.attachedDock);
            IDockable rocket = pad.getDockedEntity();
            if (rocket instanceof EntityAutoRocket)
            {
                ((EntityAutoRocket) rocket).updateControllerSettings(pad);
            }
        }
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static boolean setTarget(EntityAutoRocket rocket, boolean doSet, int destFreq)
    {
		Class<?> controllerClass = ReflectionHelper.getPrivateValue(EntityAutoRocket.class, rocket, "controllerClass");
    	
		
        WorldServer[] servers = GCCoreUtil.getWorldServerList(rocket.world);
        if (!GalacticraftCore.isPlanetsLoaded || controllerClass == null)
        {
            return false;
        }

        for (int i = 0; i < servers.length; i++)
        {
            WorldServer world = servers[i];

            try
            {
                for (TileEntity tile : new ArrayList<TileEntity>(world.loadedTileEntityList))
                {
                    if (!controllerClass.isInstance(tile))
                        continue;

                    tile = world.getTileEntity(tile.getPos());
                    if (!controllerClass.isInstance(tile))
                			continue;

                		int controllerFrequency = controllerClass.getField("frequency").getInt(tile);

                		if (destFreq == controllerFrequency)
                		{
                			boolean targetSet = false;
                			
                			blockLoop:
                				for (int x = -3; x <= 4; x++)
                				{
                					for (int z = -3; z <= 4; z++)
                					{
                                        BlockPos pos = new BlockPos(tile.getPos().add(x, 0, z));
                						Block block = world.getBlockState(pos).getBlock();
                						TileEntity tileen = world.getTileEntity(pos);
                	                        
                						//if (block instanceof BlockLandingPadFull)
                						if (tileen instanceof IFuelDock)
                						{
                							System.out.println(block);
                							if (doSet)
                							{
                								rocket.targetVec = pos;
                							}
                							targetSet = true;
                							break blockLoop;
                						}
                					}
                				}

                			if (doSet)
                			{
                				rocket.targetDimension = tile.getWorld().provider.getDimension();
                			}

                			if (!targetSet)
                			{
                				if (doSet)
                				{
                					rocket.targetVec = null;
                				}

                				return false;
                			}
                			else
                			{
                				return true;
                			}
                		}
                	}
                }
            catch (Exception e)
            {
            	e.printStackTrace();
            }
        }

        return false;
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static boolean setFrequency(EntityAutoRocket rocket)
    {
		Class<?> controllerClass = ReflectionHelper.getPrivateValue(EntityAutoRocket.class, rocket, "controllerClass");
		BlockVec3 activeLaunchController  = ReflectionHelper.getPrivateValue(EntityAutoRocket.class, rocket, "activeLaunchController");
        if (!GalacticraftCore.isPlanetsLoaded || controllerClass == null)
        {
            return false;
        }

        if (activeLaunchController != null)
                                {
            TileEntity launchController = activeLaunchController.getTileEntity(rocket.world);
            if (controllerClass.isInstance(launchController))
                                {
                                    try
                                    {
                                Boolean b = (Boolean) controllerClass.getMethod("validFrequency").invoke(launchController);

                                if (b != null && b)
                                {
                                    int controllerFrequency = controllerClass.getField("destFrequency").getInt(launchController);
                                    boolean foundPad = setTarget(rocket, false, controllerFrequency);
                                   
                                    if (foundPad)
                                    {
                                        rocket.destinationFrequency = controllerFrequency;
                                        GCLog.debug("Rocket under launch control: going to target frequency " + controllerFrequency);
                                        return true;
                                    }
                                }
                            }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

        rocket.destinationFrequency = -1;
        return false;
    }
	*/
	private static final EntityAlienVillager.ITradeList[] DEFAULT_TRADE_LIST_MAP = new EntityAlienVillager.ITradeList[] {
			  	new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.schematic, 1, 1), new EntityAlienVillager.PriceInfo(40, 55), BasicItems.SCHEMATIC_BOX.getItemStack()),
	            
			  
			  	new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.oxMask, 1, 0), new EntityAlienVillager.PriceInfo(1, 2)),
	            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.oxTankLight, 1, 235), new EntityAlienVillager.PriceInfo(3, 4)),
	            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.oxygenGear, 1, 0), new EntityAlienVillager.PriceInfo(3, 4)),
	            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.fuelCanister, 1, 317), new EntityAlienVillager.PriceInfo(3, 4)),
	            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.parachute, 1, 0), new EntityAlienVillager.PriceInfo(1, 2)),
	            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.battery, 1, 58), new EntityAlienVillager.PriceInfo(2, 4)),
	            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.oilCanister, 1, ItemCanisterGeneric.EMPTY), new EntityAlienVillager.PriceInfo(1, 1), new ItemStack(GCItems.foodItem, 1, 1)), //carrots = also yields a tin!
	            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.basicItem, 1, ItemBasic.WAFER_BASIC), new EntityAlienVillager.PriceInfo(3, 4)),
	            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.schematic, 1, 0), new EntityAlienVillager.PriceInfo(3, 5), new ItemStack(GCItems.schematic, 1, 1)), //Exchange buggy and rocket schematics
	            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.schematic, 1, 1), new EntityAlienVillager.PriceInfo(3, 5), new ItemStack(GCItems.schematic, 1, 0)), //Exchange buggy and rocket schematics
	            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.basicItem, 2, 3), new EntityAlienVillager.PriceInfo(1, 1), new ItemStack(GCItems.basicItem, 1, 6)), //Compressed Tin - needed to craft a Fuel Loader
	            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.basicItem, 2, 4), new EntityAlienVillager.PriceInfo(1, 1), new ItemStack(GCItems.basicItem, 1, 7)), //Compressed Copper - needed to craft a Fuel Loader
	            new EntityAlienVillager.EmeraldForItems(new ItemStack(Blocks.SAPLING, 1, 3), new EntityAlienVillager.PriceInfo(11, 39)) //The one thing Alien Villagers don't have and can't get is jungle trees...
	  };
	  
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void populateBuyingList(EntityAlienVillager e) {
		MerchantRecipeList buyingList =  ReflectionHelper.getPrivateValue(EntityAlienVillager.class, e, "buyingList");
		if (buyingList == null) {
			buyingList = new MerchantRecipeList();
		}

		for (EntityAlienVillager.ITradeList tradeList : DEFAULT_TRADE_LIST_MAP) {
			tradeList.modifyMerchantRecipeList(buyingList, e.getEntityWorld().rand);
		}
		
		ReflectionHelper.setPrivateValue(EntityAlienVillager.class, e, buyingList, "buyingList");
	}

	// Added code
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void onClientTick(TickHandlerClient object, ClientTickEvent event) {
		final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final WorldClient world = minecraft.world;
        final EntityPlayerSP player = minecraft.player;

        if (object.teleportingGui != null)
        {
            if (minecraft.currentScreen != object.teleportingGui)
            {
                minecraft.currentScreen = object.teleportingGui;
            }
        }

        if (object.menuReset)
        {
            TickHandlerClient.resetClient();
            object.menuReset = false;
        }

        if (event.phase == Phase.START && player != null)
        {
            if (ClientProxyCore.playerHead == null && player.getGameProfile() != null)
            {
                Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(player.getGameProfile());

                if (map.containsKey(Type.SKIN))
                {
                    ClientProxyCore.playerHead = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
                }
                else
                {
                    ClientProxyCore.playerHead = DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getUUID(player.getGameProfile()));
                }
            }

            //TickHandlerClient.tickCount++;
            // Added code
            long reflectedTickCount = ReflectionHelper.getPrivateValue(TickHandlerClient.class, object, "tickCount");
            ReflectionHelper.setPrivateValue(TickHandlerClient.class, object, reflectedTickCount + 1, "tickCount");
            //
            
            if (!GalacticraftCore.proxy.isPaused())
            {
            	// Added code
            	Set<FluidNetwork> reflectedFluidNetworks = ReflectionHelper.getPrivateValue(TickHandlerClient.class, object, "fluidNetworks");
            	//
            	
                Iterator<FluidNetwork> it = reflectedFluidNetworks.iterator();
                while (it.hasNext())
                {
                    FluidNetwork network = it.next();

                    if (network.getTransmitters().size() == 0)
                    {
                        it.remove();
                    }
                    else
                    {
                        network.clientTick();
                    }
                }
            }

            //if (TickHandlerClient.tickCount % 20 == 0)
            // Added code
            if (reflectedTickCount % 20 == 0)
            //
            {
                BubbleRenderer.clearBubbles();

                for (TileEntity tile : player.world.tickableTileEntities)
                {
                    if (tile instanceof IBubbleProviderColored)
                    {
                        BubbleRenderer.addBubble((IBubbleProviderColored)tile);
                    }
                }

                if (object.updateJEIhiding)
                {
                	object.updateJEIhiding = false;
                    // Update JEI to hide the ingot compressor recipe for GC steel in hard mode
                    // Update JEI to hide adventure mode recipes when not in adventure mode
                    GalacticraftJEI.updateHidden(CompressorRecipes.steelIngotsPresent && ConfigManagerCore.hardMode && !ConfigManagerCore.challengeRecipes, !ConfigManagerCore.challengeRecipes);
                }
                
                for (List<Footprint> fpList : FootprintRenderer.footprints.values())
                {
                    Iterator<Footprint> fpIt = fpList.iterator();
                    while (fpIt.hasNext())
                    {
                        Footprint fp = fpIt.next();
                        fp.age += 20;
                        fp.lightmapVal = player.world.getCombinedLight(new BlockPos(fp.position.x, fp.position.y, fp.position.z), 0);

                        if (fp.age >= Footprint.MAX_AGE)
                        {
                            fpIt.remove();
                        }
                    }
                }

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

                                if (block.getMaterial(state) != Material.AIR)
                                {
                                    int metadata = block.getMetaFromState(state);
                                    boolean isDetectable = false;

                                    for (BlockMetaList blockMetaList : ClientProxyCore.detectableBlocks)
                                    {
                                        if (blockMetaList.getBlock() == block && blockMetaList.getMetaList().contains(metadata))
                                        {
                                            isDetectable = true;
                                            break;
                                        }
                                    }

                                    if (isDetectable || (block instanceof IDetectableResource && ((IDetectableResource) block).isValueable(state)))
                                    {
                                        ClientProxyCore.valueableBlocks.add(new BlockVec3(x, y, z));
                                    }
                                }
                            }
                        }
                    }
                    
                    TileEntityOxygenSealer nearestSealer = TileEntityOxygenSealer.getNearestSealer(world, MathHelper.floor(player.posX), MathHelper.floor(player.posY), MathHelper.floor(player.posZ));
                    if (nearestSealer != null && !nearestSealer.sealed)
                    {
                        ClientProxyCore.leakTrace = nearestSealer.getLeakTraceClient();
                    }
                    else
                    {
                        ClientProxyCore.leakTrace = null;
                    }
                }
                else
                {
                    ClientProxyCore.leakTrace = null;
                }

                if (world != null)
                {
                    if (MapUtil.resetClientFlag.getAndSet(false))
                    {
                        MapUtil.resetClientBody();
                    }
                }
            }

            //if (ClientProxyCore.leakTrace != null) this.spawnLeakParticles();
            // Added code
            if (ClientProxyCore.leakTrace != null) {
            	try {
            		Method reflectedSpawnLeakParticles = object.getClass().getDeclaredMethod("spawnLeakParticles");
            		reflectedSpawnLeakParticles.setAccessible(true);
            		reflectedSpawnLeakParticles.invoke(object);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            }
            //
            
            if (world != null && TickHandlerClient.spaceRaceGuiScheduled && minecraft.currentScreen == null && ConfigManagerCore.enableSpaceRaceManagerPopup)
            {
                player.openGui(GalacticraftCore.instance, GuiIdsCore.SPACE_RACE_START, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
                TickHandlerClient.spaceRaceGuiScheduled = false;
            }
            
            // Added code
            boolean checkedVersion = false;
            //try {
            //	checkedVersion = ReflectionHelper.getPrivateValue(TickHandlerClient.class, object, "checkedVersion");
            //} catch (Exception e) {
            //	e.printStackTrace();
            //}
            //
            if (world != null && checkedVersion)
            {
                //ThreadVersionCheck.startCheck();
                //checkedVersion = false;
            	
            	// Added code
            	try {
            		Class<?> threadVersionClass = Class.forName("micdoodle8.mods.galacticraft.core.util.ThreadVersionCheck");
            		Object threadVersionInstance = threadVersionClass.newInstance();
            		threadVersionClass.getDeclaredMethod("startCheck").invoke(threadVersionInstance);
            	
            		ReflectionHelper.setPrivateValue(TickHandlerClient.class, object, false, "checkedVersion");
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            	//
            }

            boolean inSpaceShip = false;
            if (player.getRidingEntity() instanceof EntitySpaceshipBase)
            {
                inSpaceShip = true;
                EntitySpaceshipBase rocket = (EntitySpaceshipBase) player.getRidingEntity();
                
                if (rocket.prevRotationPitch != rocket.rotationPitch || rocket.prevRotationYaw != rocket.rotationYaw)
                {
                	// Added code
                	if(rocket instanceof EntityMultiSeatRocket) {
                		boolean isDriver = player.equals( ((EntityMultiSeatRocket) rocket).getDriver() );
                		
                		if(isDriver) {
                			GalacticraftCore.packetPipeline.sendToServer(new PacketRotateRocket(player.getRidingEntity()));
                		}
                	} else {
                	//
                		GalacticraftCore.packetPipeline.sendToServer(new PacketRotateRocket(player.getRidingEntity()));
                    // Added code
                	}
                    //
                }
            }

            if (world != null)
            {
                if (world.provider instanceof WorldProviderSurface)
                {
                    if (world.provider.getSkyRenderer() == null && inSpaceShip &&
                            player.getRidingEntity().posY > Constants.OVERWORLD_SKYPROVIDER_STARTHEIGHT)
                    {
                        world.provider.setSkyRenderer(new SkyProviderOverworld());
                    }
                    else if (world.provider.getSkyRenderer() instanceof SkyProviderOverworld && player.posY <= Constants.OVERWORLD_SKYPROVIDER_STARTHEIGHT)
                    {
                        world.provider.setSkyRenderer(null);
                    }
                }
                else if (world.provider instanceof WorldProviderSpaceStation)
                {
                    if (world.provider.getSkyRenderer() == null)
                    {
                        ((WorldProviderSpaceStation) world.provider).createSkyProvider();
                    }
                }
                else if (world.provider instanceof WorldProviderMoon)
                {
                    if (world.provider.getSkyRenderer() == null)
                    {
                        world.provider.setSkyRenderer(new SkyProviderMoon());
                    }

                    if (world.provider.getCloudRenderer() == null)
                    {
                        world.provider.setCloudRenderer(new CloudRenderer());
                    }
                }
            }

            if (inSpaceShip)
            {
                final EntitySpaceshipBase ship = (EntitySpaceshipBase) player.getRidingEntity();
                boolean hasChanged = false;

                if (minecraft.gameSettings.keyBindLeft.isKeyDown())
                {
                	// Added code
                	if(ship instanceof EntityMultiSeatRocket) {
                		boolean isDriver = player.equals( ((EntityMultiSeatRocket) ship).getDriver() );
                		
                		if(isDriver) {
                			ship.turnYaw(-1.0F);
                			hasChanged = true;
                		}
                	} else {
                	//
                		ship.turnYaw(-1.0F);
                		hasChanged = true;
                    // Added code
                	}
                    //
                }

                if (minecraft.gameSettings.keyBindRight.isKeyDown())
                {
                	// Added code
                	if(ship instanceof EntityMultiSeatRocket) {
                		boolean isDriver = player.equals( ((EntityMultiSeatRocket) ship).getDriver() );
                		
                		if(isDriver) {
                			ship.turnYaw(1.0F);
                			hasChanged = true;
                		}
                	} else {
                	//
                		ship.turnYaw(1.0F);
                		hasChanged = true;
                    // Added code
                	}
                    //
                }

                if (minecraft.gameSettings.keyBindForward.isKeyDown())
                {
                	// Added code
                	if(ship instanceof EntityMultiSeatRocket) {
                		boolean isDriver = player.equals( ((EntityMultiSeatRocket) ship).getDriver() );
                		
                		if(isDriver) {
                			if(ship.getLaunched()) {
                				ship.turnPitch(-0.7F);
                				hasChanged = true;
                			}
                		}
                	} else {
                	//
                    	if (ship.getLaunched())
                    	{
                        	ship.turnPitch(-0.7F);
                        	hasChanged = true;
                    	}
                    // Added code
            		}
                	//
                }

                if (minecraft.gameSettings.keyBindBack.isKeyDown())
                {
                	// Added code
                	if(ship instanceof EntityMultiSeatRocket) {
                		boolean isDriver = player.equals( ((EntityMultiSeatRocket) ship).getDriver() );
                		
                		if(isDriver) {
                			if(ship.getLaunched()) {
                				ship.turnPitch(0.7F);
                				hasChanged = true;
                			}
                		}
                	} else {
                	//
                    	if (ship.getLaunched())
                    	{
                        	ship.turnPitch(0.7F);
                        	hasChanged = true;
                    	}
                    // Added code
            		}
                	//
                }

                if (hasChanged)
                {
                    GalacticraftCore.packetPipeline.sendToServer(new PacketRotateRocket(ship));
                }
            }

            if (world != null)
            {
                List entityList = world.loadedEntityList;
                for (Object e : entityList)
                {
                    if (e instanceof IEntityNoisy)
                    {
                        IEntityNoisy vehicle = (IEntityNoisy) e;
                        if (vehicle.getSoundUpdater() == null)
                        {
                            ISound noise = vehicle.setSoundUpdater(FMLClientHandler.instance().getClient().player);
                            if (noise != null)
                            {
                                FMLClientHandler.instance().getClient().getSoundHandler().playSound(noise);
                            }
                        }
                    }
                }
            }

            if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiCelestialSelection)
            {
                player.motionY = 0;
            }

            if (world != null && world.provider instanceof IGalacticraftWorldProvider && OxygenUtil.noAtmosphericCombustion(world.provider) && ((IGalacticraftWorldProvider) world.provider).shouldDisablePrecipitation())
            {
                world.setRainStrength(0.0F);
            }

            boolean isPressed = KeyHandlerClient.spaceKey.isKeyDown();

            if (!isPressed)
            {
                ClientProxyCore.lastSpacebarDown = false;
            }

            if (player.getRidingEntity() != null && isPressed && !ClientProxyCore.lastSpacebarDown)
            {
                // Added code
            	if(player.getRidingEntity() instanceof EntityMultiSeatRocket) {
            		boolean isDriver = player.equals( ((EntityMultiSeatRocket)player.getRidingEntity()).getDriver() );
            		
            		if(isDriver) {
            			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_IGNITE_ROCKET, GCCoreUtil.getDimensionID(player.world), new Object[] {}));
            			ClientProxyCore.lastSpacebarDown = true;
            		}
            	} else {
            	//
            		GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_IGNITE_ROCKET, GCCoreUtil.getDimensionID(player.world), new Object[] {}));
            		ClientProxyCore.lastSpacebarDown = true;
                // Added code
            	}
                //
            }

            if (!(object.screenConnectionsUpdateList.isEmpty()))
            {
                HashSet<TileEntityScreen> updateListCopy = (HashSet<TileEntityScreen>) object.screenConnectionsUpdateList.clone();
                object.screenConnectionsUpdateList.clear();
                for (TileEntityScreen te : updateListCopy)
                {
                    if (te.getWorld().getBlockState(te.getPos()).getBlock() == GCBlocks.screen)
                    {
                        if (te.refreshOnUpdate)
                        {
                            te.refreshConnections(true);
                        }
                        te.getWorld().markBlockRangeForRenderUpdate(te.getPos(), te.getPos());
                    }
                }
            }
        }
        else if (event.phase == Phase.END)
        {
        	// Added code
        	List<GalacticraftPacketHandler> reflectedPacketHandlers = ReflectionHelper.getPrivateValue(TickHandlerClient.class, object, "packetHandlers");
        	//
        			
            if (world != null)
            {
                for (GalacticraftPacketHandler handler : reflectedPacketHandlers)
                {
                    handler.tick(world);
                }
            }
        }
        
        return;
	}
}