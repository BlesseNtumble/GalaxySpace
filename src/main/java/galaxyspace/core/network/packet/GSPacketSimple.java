package galaxyspace.core.network.packet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.api.tile.ITileEffects;
import galaxyspace.core.events.GSEventHandler;
import galaxyspace.core.handler.capabilities.GSStatsCapability;
import galaxyspace.core.handler.capabilities.GSStatsCapabilityClient;
import galaxyspace.core.handler.capabilities.StatsCapability;
import galaxyspace.core.handler.capabilities.StatsCapabilityClient;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStatsClient;
import micdoodle8.mods.galacticraft.core.network.NetworkUtil;
import micdoodle8.mods.galacticraft.core.network.PacketBase;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSPacketSimple extends PacketBase implements Packet<INetHandler>
{
    public static enum GSEnumSimplePacket
    {
        // SERVER
    	
    	 /**
         * Updates the gravity side of an artifical gravity block
         * - BlockVec3 pos: position of the block
         * - Integer gravityStrength
         */
        S_GRAVITY_RADIUS(Side.SERVER, BlockVec3.class, Integer.class),        
        S_ON_ADVANCED_GUI_CLICKED_INT(Side.SERVER, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class),
        S_CHANGE_FLIGHT_STATE(Side.SERVER, Boolean.class),
        S_REVERSE_SEPATATOR(Side.SERVER, BlockVec3.class),   
        S_UPDATE_NBT_ITEM_ON_GUI(Side.SERVER, BlockVec3.class, String.class, Boolean.class),
        S_UPDATE_NBT_ITEM_IN_ARMOR(Side.SERVER, Integer.class, String.class), 	
        //CLIENT
    	C_UPDATE_RESEARCHES(Side.CLIENT, Integer[].class),
    	C_UPDATE_RESEARCH(Side.CLIENT, Integer.class, Integer.class); // id, count
        
        
        
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
    	super();
    }
    
    public GSPacketSimple(GSEnumSimplePacket packetType, int dimID, Object... data)
    {
        this(packetType, dimID, Arrays.asList(data));
    }

    public GSPacketSimple(GSEnumSimplePacket packetType, World world, Object[] data)
    {
        this(packetType, GCCoreUtil.getDimensionID(world), Arrays.asList(data));
    }
    
    public GSPacketSimple(GSEnumSimplePacket packetType, int dimID, List<Object> data)
    {
        super(dimID);
        if (packetType.getDecodeClasses().length != data.size())
        {
            GCLog.info("Simple Packet Core found data length different than packet type");
            new RuntimeException().printStackTrace();
        }

        this.type = packetType;
        this.data = data;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
    	super.encodeInto(buffer);
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
    public void decodeInto(ByteBuf buffer)
    {
    	super.decodeInto(buffer);
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
    	EntityPlayerSP playerBaseClient = null;
        GCPlayerStatsClient stats = null;

        StatsCapability gs_stats = null;
		StatsCapabilityClient gs_stats_client = null;

        if (player instanceof EntityPlayerSP)
        {
            playerBaseClient = (EntityPlayerSP) player;
            stats = GCPlayerStatsClient.get(playerBaseClient);
            gs_stats = GSStatsCapability.get(player);
            gs_stats_client = GSStatsCapabilityClient.get(playerBaseClient);
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
        	case C_UPDATE_RESEARCHES:        		
        			if(gs_stats_client != null) 
	        		{	
        				int i = 0;
        				for(Object o : this.data)
        				{
        					gs_stats_client.setKnowledgeResearch(i++, (Integer) o);
        				}	        				
	        			
	        		}
        		break;
        	case C_UPDATE_RESEARCH:        		
        		int id = (int) this.data.get(0);
        		int count = (int) this.data.get(1);
        		
        		if(gs_stats_client != null)         		      			
        			gs_stats_client.setKnowledgeResearch(id, count);
        		
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
        case S_CHANGE_FLIGHT_STATE:
        	boolean state = (boolean) this.data.get(0);
        	GSEventHandler.enableFlight(player, state);
        	break;
        case S_GRAVITY_RADIUS:
            BlockVec3 pos = (BlockVec3) this.data.get(0);
            int strength = (int) this.data.get(1);
        
            tileEntity = pos.getTileEntity(playerBase.world);
            if(tileEntity instanceof TileEntityGravitationModule) {
                ((TileEntityGravitationModule)tileEntity).setGravityRadius(strength);
                tileEntity.markDirty();
                //playerBase.world.update(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            }
            break;
        case S_ON_ADVANCED_GUI_CLICKED_INT:
            TileEntity tile1 = player.world.getTileEntity(new BlockPos((Integer) this.data.get(1), (Integer) this.data.get(2), (Integer) this.data.get(3)));

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
        case S_REVERSE_SEPATATOR:
        	BlockVec3 posSep = (BlockVec3) this.data.get(0);
            
            tileEntity = posSep.getTileEntity(playerBase.world);
            if(tileEntity instanceof TileEntityLiquidSeparator) {
            	((TileEntityLiquidSeparator)tileEntity).setReverse(!((TileEntityLiquidSeparator)tileEntity).getReverse());
            	tileEntity.markDirty();
                playerBase.world.notifyBlockUpdate(tileEntity.getPos(), tileEntity.getBlockType().getDefaultState(), tileEntity.getBlockType().getDefaultState(), 0);
              
            }
        	break;
        case S_UPDATE_NBT_ITEM_ON_GUI:
        	BlockVec3 position = (BlockVec3) this.data.get(0);
        	String tag = (String) this.data.get(1);
        	boolean turn = (boolean) this.data.get(2);
        	boolean consumed = false;
        	
        	tileEntity = position.getTileEntity(playerBase.world);
        	if(tileEntity instanceof TileEntityModificationTable) 
        	{       		
	        	ItemStack stack = ((TileEntityModificationTable)tileEntity).getStackInSlot(0); 
	        	boolean check = true;
	        	ItemModule get_module = null;
	        	
	        	if(stack.getItem() instanceof IModificationItem)
	        	{
	        		for(ItemModule module : ((IModificationItem)stack.getItem()).getAvailableModules())
	        		{
	        			if(module.getName().equals(tag)) {
	        				/*for(ItemModule forb_module : module.getForrbidenModules()) {
	        					if(stack.getTagCompound().hasKey(forb_module.getName())) check = false;
	        				}*/
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
	        	
	        		if(turn) {	        	
		        	
		        		if(check && stack.getTagCompound().getInteger(ItemSpaceSuit.mod_count) > 0) {
			        		
				        	for(ItemStack con : get_module.getItemsForModule()) {
				        		if(GSEventHandler.consumeItemStack(playerBase.inventory, con)) {
				        			consumed = true;
				        		}
				        	}
			        		
				        	if(consumed || playerBase.capabilities.isCreativeMode) {
				        		ItemStack copied = stack;
			            	
				        		copied.getTagCompound().setBoolean(tag, true);
				        		copied.getTagCompound().setInteger(ItemSpaceSuit.mod_count, copied.getTagCompound().getInteger(ItemSpaceSuit.mod_count) - 1);
				        		((TileEntityModificationTable)tileEntity).setInventorySlotContents(0, copied);
				        		tileEntity.markDirty();
				        		playerBase.world.scheduleUpdate(tileEntity.getPos(), tileEntity.getBlockType(), 0);    
				        	}
			        	}
		        	}
			        else
			        {
			        	if(!playerBase.capabilities.isCreativeMode)
				        	for(ItemStack con : get_module.getItemsForModule()) {
				        		playerBase.addItemStackToInventory(con);
				        	}
			        	
			        	ItemStack copied = stack;
			        	copied.getTagCompound().removeTag(tag);
			        	copied.getTagCompound().setInteger(ItemSpaceSuit.mod_count, copied.getTagCompound().getInteger(ItemSpaceSuit.mod_count) + 1);
			        	((TileEntityModificationTable)tileEntity).setInventorySlotContents(0, copied);
		        		tileEntity.markDirty();
		        		playerBase.world.scheduleUpdate(tileEntity.getPos(), tileEntity.getBlockType(), 0); 
			        }
	        	}
        	}
        	
        	break;         
        case S_UPDATE_NBT_ITEM_IN_ARMOR: //TODO: PacketHack????
        	int armor_slot = (int) this.data.get(0);
        	tag = (String) this.data.get(1);
        	
        	ItemStack armor = playerBase.inventory.armorInventory.get(armor_slot);
	        if(armor != ItemStack.EMPTY && armor.getItem() instanceof ItemSpaceSuit) {
	        	ItemStack copied = armor;
	        	copied.getTagCompound().setBoolean(tag, !copied.getTagCompound().getBoolean(tag));
	        	playerBase.inventory.armorInventory.set(armor_slot, copied);  
	        	//GalaxySpace.debug(copied.getTagCompound().toString());
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
        this.decodeInto(var1);
    }

    @Override
    public void writePacketData(PacketBuffer var1)
    {
        this.encodeInto(var1);
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
