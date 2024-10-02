package galaxyspace.systems.ACentauriSystem.planets.proximaB.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class ProximaBBlocks extends Block implements ITerraformableBlock
{
	public static String[] metadata = new String[] {
		"Proxima_B_Surface",		//0
		"Proxima_B_Subsurface",		//1
		"Proxima_B_Stone",			//2
		"Proxima_B_Ice_Surface",	//3
		"Proxima_B_Ash_Rock",		//4
		"Proxima_B_Gold_Ore",		//5
		"Proxima_B_Tin_Ore",		//6
		"Proxima_B_Copper_Ore",		//7
		"Proxima_B_Coal_Ore",		//8
		"Proxima_B_Silicon_Ore",	//9
		"Proxima_B_Emerald_Ore"		//10
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	protected IIcon[] othertextures = new IIcon[2];
	
    public ProximaBBlocks()
    {
        super(Material.rock);
        this.setBlockName("ProximaBBlocks");
        this.setHardness(2.0F);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return true;
	}
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	switch(meta)
    	{
    		case 8: return Items.coal;
    		case 9: return GCItems.basicItem;
    		case 10: return Items.emerald;
    		default: return Item.getItemFromBlock(this);
    	}

    }
    
    @Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		int qty;
		switch (meta) {
		case 8:
		case 9:
		case 10:
			qty = 1;
			if (fortune > 0)
				qty += random.nextInt(fortune);
			break;
			
		default:
			qty = 1;
		}
		return qty;		
	}
    
    @Override
    public int damageDropped(int metadata) {
    	switch(metadata)
    	{
    		case 8:
    		case 10: return 0;
    	
    		case 9: return 2;
    		default: return metadata;
    	}
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    }    
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "proximasystem/proxima_b/" + this.metadata[i].toLowerCase());
		
		othertextures[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "proximasystem/proxima_b/proxima_b_ice_surface_top");
		othertextures[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "proximasystem/proxima_b/proxima_b_ice_surface_side");
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta > this.textures.length)
        {
            return this.textures[0];
        }

        if(meta == 3)
        {
        	if(side == 1) 
        		return this.othertextures[0];
        	else if(side == 0) 
        		return this.textures[0];
        	else 
        		return this.othertextures[1];
        	
        }
        return this.textures[meta];
    }

    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.textures.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}
    
    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
    {
    	if (plantable == Blocks.deadbush && world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 0)
        {
            return true;
        }
		return super.canSustainPlant(world, x, y, z, direction, plantable);
    	
    }
}

