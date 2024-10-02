package galaxyspace.core.network.packet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.api.tile.ITileEffects;
import galaxyspace.core.client.gui.screen.GSGuiCelestialSelection;
import galaxyspace.core.events.GSEventHandler;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStatsClient;
import micdoodle8.mods.galacticraft.core.network.IPacket;
import micdoodle8.mods.galacticraft.core.network.NetworkUtil;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class GSPacketSimple extends Packet implements IPacket
{
    public static enum GSEnumSimplePacket
    {
        // SERVER
        S_GRAVITY_RADIUS(Side.SERVER, BlockVec3.class, Integer.class),   
        S_REVERSE_SEPATATOR(Side.SERVER, BlockVec3.class),   
        S_ON_ADVANCED_GUI_CLICKED_INT(Side.SERVER, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class),
        S_CHANGE_FLIGHT_STATE(Side.SERVER, Boolean.class),
        S_TELEPORT_ENTITY(Side.SERVER, String.class, Integer.class),
        S_UPDATE_NBT_ITEM_ON_GUI(Side.SERVER, BlockVec3.class, String.class),
        S_UPDATE_NBT_ITEM_IN_ARMOR(Side.SERVER, Integer.class, String.class),
        //CLIENT
        C_UPDATE_DIMENSION_LIST(Side.CLIENT, String.class, String.class, Integer[].class);  
        
        private Side targetSide;
        private Class<?>[] decodeAs;

        private GSEnumSimplePacket(Side targetSide, Class<?>... decodeAs)
        {
            this.targetSide = targetSide;
            this.decodeAs = decodeAs;
        }

        public Side getTargetSide()
        {
            return this.targetSide;
        }

        public Class<?>[] getDecodeClasses()
        {
            return this.decodeAs;
        }
    }

    private GSEnumSimplePacket type;
    private List<Object> data;
    static private String spamCheckString;

    public GSPacketSimple()
    {
    }

    public GSPacketSimple(GSEnumSimplePacket packetType, Object... data)
    {
        this(packetType, Arrays.asList(data));
    }

    public GSPacketSimple(GSEnumSimplePacket packetType, List<Object> data)
    {
        if (packetType.getDecodeClasses().length != data.size())
        {
          new RuntimeException().printStackTrace();
        }

        this.type = packetType;
        this.data = data;
    }

    @Override
    public void encodeInto(ChannelHandlerContext context, ByteBuf buffer)
    {
        buffer.writeInt(this.type.ordinal());

        try
        {
            NetworkUtil.encodeData(buffer, this.data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext context, ByteBuf buffer)
    {
        this.type = GSEnumSimplePacket.values()[buffer.readInt()];

        try
        {
            if (this.type.getDecodeClasses().length > 0)
            {
                this.data = NetworkUtil.decodeData(this.type.getDecodeClasses(), buffer);
            }
            if (buffer.readableBytes() > 0)
            {
                GCLog.severe("Galacticraft packet length problem for packet type " + this.type.toString());
            }
        }
        catch (Exception e)
        {
            System.err.println("[Galacticraft] Error handling simple packet type: " + this.type.toString() + " " + buffer.toString());
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player)
    {
        EntityClientPlayerMP playerBaseClient = null;
        GCPlayerStatsClient stats = null;

        if (player instanceof EntityClientPlayerMP)
        {
            playerBaseClient = (EntityClientPlayerMP) player;
            stats = GCPlayerStatsClient.get(playerBaseClient);
        }
        /*else
        {/*
            if (type != EnumSimplePacket.C_UPDATE_SPACESTATION_LIST && type != EnumSimplePacket.C_UPDATE_PLANETS_LIST && type != EnumSimplePacket.C_UPDATE_CONFIGS)
            {
                return;
            }
        }*/
        switch (this.type)
        {
        case C_UPDATE_DIMENSION_LIST:
            if (String.valueOf(this.data.get(0)).equals(FMLClientHandler.instance().getClient().thePlayer.getGameProfile().getName()))
            {            	
                String dimensionList = (String) this.data.get(1);
                if (ConfigManagerCore.enableDebug)
                {
                    if (!dimensionList.equals(this.spamCheckString))
                    {
                        GCLog.info("DEBUG info: " + dimensionList);
                        this.spamCheckString = new String(dimensionList);
                    }
                }
                final String[] destinations = dimensionList.split("\\?");
                List<CelestialBody> possibleCelestialBodies = Lists.newArrayList();
                Map<Integer, Map<String, GSGuiCelestialSelection.StationDataGUI>> spaceStationData = Maps.newHashMap();
//              Map<String, String> spaceStationNames = Maps.newHashMap();
//             	Map<String, Integer> spaceStationIDs = Maps.newHashMap();
//              Map<String, Integer> spaceStationHomes = Maps.newHashMap();

                for (String str : destinations)
                {
                    CelestialBody celestialBody = WorldUtil.getReachableCelestialBodiesForName(str);

                    if (celestialBody == null && str.contains("$"))
                    {
                        String[] values = str.split("\\$");

                        int homePlanetID = Integer.parseInt(values[4]);

                        for (Satellite satellite : GalaxyRegistry.getRegisteredSatellites().values())
                        {
                            if (satellite.getParentPlanet().getDimensionID() == homePlanetID)
                            {
                                celestialBody = satellite;
                                break;
                            }
                        }

                        if (!spaceStationData.containsKey(homePlanetID))
                        {
                            spaceStationData.put(homePlanetID, new HashMap<String, GSGuiCelestialSelection.StationDataGUI>());
                        }

                        spaceStationData.get(homePlanetID).put(values[1], new GSGuiCelestialSelection.StationDataGUI(values[2], Integer.parseInt(values[3])));

//                        spaceStationNames.put(values[1], values[2]);
//                        spaceStationIDs.put(values[1], Integer.parseInt(values[3]));
//                        spaceStationHomes.put(values[1], Integer.parseInt(values[4]));
                    }

                    if (celestialBody != null)
                    {
                        possibleCelestialBodies.add(celestialBody);
                    }
                }

                if (FMLClientHandler.instance().getClient().theWorld != null)
                {
                    if (!(FMLClientHandler.instance().getClient().currentScreen instanceof GSGuiCelestialSelection))
                    {            
                    	GalaxySpace.debug(this.data + "");
                    	Integer[] ints = new Integer[] {(Integer) this.data.get(2), (Integer) this.data.get(3)};
                        GSGuiCelestialSelection gui = new GSGuiCelestialSelection(false, possibleCelestialBodies, ints);
                        gui.spaceStationMap = spaceStationData;
//                      gui.spaceStationNames = spaceStationNames;
//                      gui.spaceStationIDs = spaceStationIDs;
                        FMLClientHandler.instance().getClient().displayGuiScreen(gui);
                    }
                    else
                    {
                        //((GSGuiCelestialSelection) FMLClientHandler.instance().getClient().currentScreen).currenttier = (Integer) this.data.get(1);
                        ((GSGuiCelestialSelection) FMLClientHandler.instance().getClient().currentScreen).spaceStationMap = spaceStationData;
//                    	((GSGuiCelestialSelection) FMLClientHandler.instance().getClient().currentScreen).spaceStationNames = spaceStationNames;
//                      ((GSGuiCelestialSelection) FMLClientHandler.instance().getClient().currentScreen).spaceStationIDs = spaceStationIDs;
                    }
                }
            }
            break;
        default:
            break;
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

        if (playerBase == null)
        {
            return;
        }
        
        GCPlayerStats stats = GCPlayerStats.get(playerBase);
        TileEntity tileEntity;
        
        switch (this.type)
        {
	        case S_UPDATE_NBT_ITEM_ON_GUI:
	        	BlockVec3 pos = (BlockVec3) this.data.get(0);
	        	String tag = (String) this.data.get(1);
	        	boolean turn = false;
	        	boolean consumed = false;
	        	
	        	tileEntity = pos.getTileEntity(playerBase.worldObj);
	        	if(tileEntity instanceof TileEntityModificationTable) 
	        	{
	        		
		        	ItemStack stack = ((TileEntityModificationTable)tileEntity).getStackInSlot(0); 
		        	boolean check = true;
		        	ItemModule get_module = null;
		        	
		        	if(stack.getItem() instanceof IModificationItem)
		        	{
			        	for(ItemModule module : GSUtils.getListModule())
						{
			        		if(module.getName().equals(tag)) {
		        				get_module = module;
		        				break;
		        			}
						}
		        	}
		        	if(get_module != null) {
		        		if(get_module.getForrbidenModules() != null)
			        		for(ItemModule forb_module : get_module.getForrbidenModules())
			        		{
			        			if(stack.getTagCompound().hasKey(forb_module.getName())) { 
			        				check = false;
			        				break;
			        			}
			        		}
		        		
		        		if(!stack.getTagCompound().hasKey(tag) || !stack.getTagCompound().getBoolean(tag))
		        			turn = true;
		        		else if (stack.getTagCompound().hasKey(tag))
		        			turn = false;
		       
		        		if(turn) 
		        		{
			        		if(check && stack.getTagCompound().getInteger(ItemSpaceArmors.mod_count) > 0) {
			        			
			        			for(ItemStack con : get_module.getItemsForModule()) {					        		
					        		consumed = GSEventHandler.consumeItemStack(playerBase, playerBase.inventory, con);					        		
					        	}
			        			
			        			if(consumed || playerBase.capabilities.isCreativeMode) {
					        		ItemStack copied = stack;
				            	
					        		copied.getTagCompound().setBoolean(tag, true);
					        		copied.getTagCompound().setInteger(ItemSpaceArmors.mod_count, copied.getTagCompound().getInteger(ItemSpaceArmors.mod_count) - 1);
					        		((TileEntityModificationTable)tileEntity).setInventorySlotContents(0, copied);
					        		tileEntity.markDirty();
			    	        		playerBase.worldObj.markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);   
					        	}
			        		}
		        		}
		        		else
				        {
				        	if(!playerBase.capabilities.isCreativeMode)
					        	for(ItemStack con : get_module.getItemsForModule()) {
					        		playerBase.inventory.addItemStackToInventory(con);
					        	}
				        	
				        	ItemStack copied = stack;
				        	copied.getTagCompound().removeTag(tag);
				        	copied.getTagCompound().setInteger(ItemSpaceArmors.mod_count, copied.getTagCompound().getInteger(ItemSpaceArmors.mod_count) + 1);
				        	((TileEntityModificationTable)tileEntity).setInventorySlotContents(0, copied);
			        		tileEntity.markDirty();
			        		playerBase.worldObj.markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord); 
				        }
		        	}		        	
	        	}	        
	        	break; 
	        case S_UPDATE_NBT_ITEM_IN_ARMOR: 
	        	Integer armor_slot = (Integer) this.data.get(0);
	        	tag = (String) this.data.get(1);
	        	
	        	ItemStack armor = playerBase.inventory.armorInventory[armor_slot];
		        if(armor != null && armor.getItem() instanceof ItemSpaceArmors) {
		        	ItemStack copied = armor;
		        	copied.getTagCompound().setBoolean(tag, !copied.getTagCompound().getBoolean(tag));
		        	playerBase.inventory.armorInventory[armor_slot] = copied;  
		        	//GalaxySpace.debug(copied.getTagCompound().toString());
	        	}
	        	break;
	        case S_CHANGE_FLIGHT_STATE:
	        	Boolean state = (Boolean) this.data.get(0);
	        	GSEventHandler.enableFlight(player, state);
	        	break;	        	
	        case S_GRAVITY_RADIUS:
	            pos = (BlockVec3) this.data.get(0);
	            Integer strength = (Integer) this.data.get(1);
	        
	            tileEntity = pos.getTileEntity(playerBase.worldObj);
	            if(tileEntity instanceof TileEntityGravitationModule) {
	                ((TileEntityGravitationModule)tileEntity).setGravityRadius(strength);
	                tileEntity.markDirty();
	                playerBase.worldObj.markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
	            }
	            break;
	        case S_REVERSE_SEPATATOR:
	        	BlockVec3 posSep = (BlockVec3) this.data.get(0);
	            
	            tileEntity = posSep.getTileEntity(playerBase.worldObj);
	            if(tileEntity instanceof TileEntityLiquidSeparator) {
	            	((TileEntityLiquidSeparator)tileEntity).setReverse(!((TileEntityLiquidSeparator)tileEntity).getReverse());
	            	tileEntity.markDirty();
	                playerBase.worldObj.markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
	              
	            }
	        	break;
	        case S_ON_ADVANCED_GUI_CLICKED_INT:
	            TileEntity tile1 = player.worldObj.getTileEntity((Integer) this.data.get(1), (Integer) this.data.get(2), (Integer) this.data.get(3));
	            
	            switch ((Integer) this.data.get(0))
	            {
	            	case 6:
	            		if (tile1 instanceof ITileEffects)
	            		{
	            			ITileEffects distributor = (ITileEffects) tile1;
	            			distributor.setEffectsVisible((Integer) this.data.get(4) == 1);
	            		}
	            		break;
	            }
	            break;
	        case S_TELEPORT_ENTITY:
	            try
	            {
	                final WorldProvider provider = WorldUtil.getProviderForNameServer((String) this.data.get(0));
	                final Integer dim = provider.dimensionId;
	                GCLog.info("Found matching world (" + dim.toString() + ") for name: " + (String) this.data.get(0));
	
	                if (playerBase.worldObj instanceof WorldServer)
	                {
	                    final WorldServer world = (WorldServer) playerBase.worldObj;
	
	                    WorldUtil.transferEntityToDimension(playerBase, dim, world);
	                }
	
	                stats.teleportCooldown = 10;
	                stats.fuelLevel = (Integer) this.data.get(1);
	                
	                GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_CLOSE_GUI, new Object[] { }), playerBase);
	            }
	            catch (final Exception e)
	            {
	                GCLog.severe("Error occurred when attempting to transfer entity to dimension: " + (String) this.data.get(0));
	                e.printStackTrace();
	            }
	            break;
	        default:
	            break;
        }
    }

	/*
     *
	 * BEGIN "net.minecraft.network.Packet" IMPLEMENTATION
	 * 
	 * This is for handling server->client packets before the player has joined the world
	 * 
	 */

    @Override
    public void readPacketData(PacketBuffer var1)
    {
        this.decodeInto(null, var1);
    }

    @Override
    public void writePacketData(PacketBuffer var1)
    {
        this.encodeInto(null, var1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void processPacket(INetHandler var1)
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            this.handleClientSide(FMLClientHandler.instance().getClientPlayerEntity());
        }
    }

	/*
	 * 
	 * END "net.minecraft.network.Packet" IMPLEMENTATION
	 * 
	 * This is for handling server->client packets before the player has joined the world
	 * 
	 */
}
