package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import net.minecraft.block.Block;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHydroponicFarm extends TileEntity{

	public Block plant = null;
	public int metaplant = 0;
	
	public void setPlant(Block stack)
	{
		plant = stack;
	}
	public Block getPlant()
	{		
		return plant;		
	}
	
	public void setMetaPlant(int meta) {
		metaplant = meta;
	}
	
	public int getMetaPlant()
	{
		return metaplant;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		setPlant(plant);
		setMetaPlant(metaplant);
	}
}
