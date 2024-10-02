package galaxyspace.systems.SolarSystem.planets.overworld.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemBattery extends ItemElectricBase 
{
	private float energyMax = 0;
	public ItemBattery(String name, float energy)
	{
		super();
		this.setUnlocalizedName(name);
	    this.setTextureName(GalaxySpace.ASSET_PREFIX + ":batteries/" + name);
	    this.energyMax = energy;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public CreativeTabs getCreativeTab()
	{
	    return GSCreativeTabs.GSItemsTab;
	}
	
	@Override
	public float getMaxElectricityStored(ItemStack itemStack)
	{
	    return this.energyMax;
	}
	/*
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		final int hx = (x + x + 12) / 2;
        final int hz = (z + z + 12) / 2;
        Block block = GSBlocks.FutureGlass;
        int n = 8;
        for(int i = -n; i < n; i++)
        {
        	for(int j = -n; j < n; j++)
            {
        		world.setBlock(hx + i, y - 1, hz + j, block, 0, 3);
        		world.setBlock(hx + i, y - 2, hz + j, Blocks.lava, 0, 3);
        		for(int o = 0; o < 10 + 2; o++)
                {        			
        			world.setBlock(hx + n, y - 2 + o, hz + n, GSBlocks.IoBlocks, 3, 3);
        			world.setBlock(hx + n, y - 2 + o, hz - n - 1, GSBlocks.IoBlocks, 3, 3);
        			world.setBlock(hx - n - 1, y - 2 + o, hz + n, GSBlocks.IoBlocks, 3, 3);
        			world.setBlock(hx - n - 1, y - 2 + o, hz - n - 1, GSBlocks.IoBlocks, 3, 3);  
        			
        			world.setBlock(hx + 8 * 2, y + o - 2, hz + j * 2, GSBlocks.IoBlocks, 3, 3);
        			world.setBlock(hx - 8 * 2, y + o - 2, hz + j * 2, GSBlocks.IoBlocks, 3, 3);
        			
        			world.setBlock(hx + i * 2, y + o - 2, hz + 8 * 2, GSBlocks.IoBlocks, 3, 3);
        			world.setBlock(hx + i * 2, y + o - 2, hz - 8 * 2, GSBlocks.IoBlocks, 3, 3);
        			
        			world.setBlock(hx + 8 * 2, y + o - 2, hz + 8 * 2, GSBlocks.IoBlocks, 3, 3);

                }       		
            }
        }
        
	    return true;
	}*/

}
