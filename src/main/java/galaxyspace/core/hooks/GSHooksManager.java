package galaxyspace.core.hooks;

import java.util.ArrayList;

import asmodeuscore.api.dimension.IAdvancedSpace;
import galaxyspace.core.events.SetBlockEvent;
import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import micdoodle8.mods.galacticraft.api.item.IItemThermal;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.DamageSourceGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityGasLiquefier;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityMethaneSynthesizer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;

public class GSHooksManager {
	
	@Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = false) 
    public static boolean setBlockState(World world, BlockPos pos, IBlockState newState, int flags) { 
    	return MinecraftForge.EVENT_BUS.post(new SetBlockEvent(world, pos, newState, flags)); 
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static int getAirProducts(TileEntityMethaneSynthesizer te) {
		
		WorldProvider WP = te.getWorld().provider;
        if (WP instanceof IGalacticraftWorldProvider)
        {
            ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider) WP).getCelestialBody().atmosphere.composition;
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
    }*/
}