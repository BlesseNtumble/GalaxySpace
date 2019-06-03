package galaxyspace.core.proxy;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	
	
    public void preload(FMLPreInitializationEvent event) {
    	//GameRegistry.registerFuelHandler(new GSFuelHandler());
	}
	
	
    public void load()
    {  
    	
	}
	
	
    public void postload() {
		 
	}
    
    public void registerItemRenderer(Item item, int meta, String id) {}
    
    public void registerVariants()
    {

    }

    public TileEntity getServerTile(TileEntity source) {
		return source;
	}

	public int getBlockRender(Block block) {

		return 0;
	}
	
	public void register_event(Object obj)
	{
    	MinecraftForge.EVENT_BUS.register(obj);
	}


	public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo) {		
	}
	
	public EntityPlayer getPlayerFromNetHandler(INetHandler handler)
    {
        if (handler instanceof NetHandlerPlayServer)
        {
            return ((NetHandlerPlayServer) handler).player;
        }
        else
        {
            return null;
        }
    }


	public void registerRender() {}


	public void registerTexture(Pre event, String texture) {
	}

	public void resetPlayerInAirTime(EntityPlayer player) {
		if(player instanceof EntityPlayerMP) {
			((EntityPlayerMP)player).connection.floatingTickCount = 0;
			//ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, ((EntityPlayerMP)player).connection, Integer.valueOf(0), new String[]{"field_147365_f", "floatingTickCount"});
		}
	}
}
