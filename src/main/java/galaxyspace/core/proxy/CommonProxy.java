package galaxyspace.core.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
		
    public void preload() {
		
	}
	
	
    public void load()
    {  
    	
	}
	
	
    public void postload() {

	}

    public TileEntity getServerTile(TileEntity source) {
		return source;
	}

	public int getBlockRender(Block block) {
		return 0;
	}
	
	public void register_event(Object obj)
	{
    	FMLCommonHandler.instance().bus().register(obj);
    	MinecraftForge.EVENT_BUS.register(obj);
	}


	public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo) {

		
	}


	public int getJetpackArmorRenderIndex() {

		return 0;
	}

	public void resetPlayerInAirTime(EntityPlayer player) {
		if(player instanceof EntityPlayerMP) {
			ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, ((EntityPlayerMP)player).playerNetServerHandler, Integer.valueOf(0), new String[]{"field_147365_f", "floatingTickCount"});
		}
	}


	public int getArmorRenderIndex() {
		
		return 0;
	} 
	
	public void showJetpackParticles(World world, EntityLivingBase wearer, int streams) {}
	
	public EntityPlayer getPlayerFromNetHandler(INetHandler handler)
    {
        if (handler instanceof NetHandlerPlayServer)
        {
            return ((NetHandlerPlayServer) handler).playerEntity;
        }
        else
        {
            return null;
        }
    }
}
