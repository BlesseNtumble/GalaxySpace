package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc.IBlockShiftDesc;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRItems;

public class Barnarda_C_Dandelions extends BlockBush implements ITerraformableBlock, IShearable
{
	public static String[] metadata = new String[] {
		"hopper_flower",
		"leaves_balls",
		"light_balls",
		"tallgrass",
		"desert_flower_down",
		"desert_flower_up",
		"reeds",
		"reeds_fruits"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
    public Barnarda_C_Dandelions()    
    {
        super(Material.plants);
        this.setBlockName("BarnardaCDandelions");
        this.setStepSound(Block.soundTypeGrass);
        this.setBlockTextureName("dirt");
        this.setTickRandomly(true);
        
        //float var4 = 0.5F;
		//this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);

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
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return false;
	}
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
    	if(world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 7) {
    		player.inventory.addItemStackToInventory(new ItemStack(BRItems.Food, 1, 0));
    		world.setBlockMetadataWithNotify(x, y, z, 6, 2);
    	}
    	
        return false;
    }
    
    @Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
   	
    	Block block = world.getBlock(x, y - 1, z);
		int block_meta = world.getBlockMetadata(x, y - 1, z);

		if (side == 0)		
			return true;	
				
		return block == this || block == BRBlocks.BarnardaCFallingBlocks || block == BRBlocks.BarnardaCLeaves || block == BRBlocks.BarnardaCGrass || (block == BRBlocks.BarnardaCBlocks && block_meta == 0);

	}
    
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
    {
		return blockConstructorCalled;
    	
    }
        
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return (world.getBlockMetadata(x, y, z) == 1 || world.getBlockMetadata(x, y, z) == 2) ? 8 : 0;
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		return ret;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		for (int i = 0; i < metadata.length; i++)
			this.textures[i] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/" + metadata[i].toLowerCase());

	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
       if (meta < 0 || meta >= this.textures.length)
       {
            return this.textures[0];
       }

       return this.textures[meta];
    }
        
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
    public int damageDropped(int metadata) {
    	switch(metadata)
    	{
    		default: return metadata;
    	}
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
        if (metadata == 7) {
        	ret.clear();
            ret.add(new ItemStack(BRItems.Food, 1, 0));
            ret.add(new ItemStack(this, 1, 6));
        }
        return ret;
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 1;
    }
    
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
		
		if(is.getItemDamage() == 4 && world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y + 1, z, this, 5, 3);
		}
		
		if(is.getItemDamage() == 6 && !(world.getBlock(x, y - 1, z) instanceof Barnarda_C_Grass))		
			GSUtils.destroyBlock(world, x, y, z, (EntityPlayer) entity, x, y, z);
		
		
	}
    

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
    	if(world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 4)
    		if(world.getBlock(x, y + 1, z) == this)
    			GSUtils.destroyBlock(world, x, y + 1, z, player, x, y, z);
    	
    	
    	if(world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) >= 6)
    	{
    		for(int y1 = 0; y1 < 4; y1++)
    			if(world.getBlock(x, y + y1, z) == this && world.getBlockMetadata(x, y + y1, z) >= 6) {
					this.dropBlockAsItem(world, x, y + y1, z, world.getBlockMetadata(x, y + y1, z), 0);
					world.setBlockToAir(x, y + y1, z);
    			}
    	}
    	
    	return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	if (!world.isRemote)
		{
	    	if((world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) >= 6))
	    	{
	    		if (world.getBlock(x, y - 1, z) == Blocks.air)
	        	{
	        		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	                world.setBlockToAir(x, y, z);
	        	}		    		
	    		else if(world.isAirBlock(x, y + 1, z) )
	    		{
	    			int lenght = 1;
	    			while(world.getBlock(x, y - lenght, z) == this)
	    				lenght++;
	    			
	    			if(lenght < 4)
	    				world.setBlock(x, y + 1, z, this, 6, 3);
	    		
	    		}
	    		
	    		if(world.getBlockMetadata(x, y, z) == 6 && rand.nextInt(15) == 0)
	    		{
	    			world.setBlockMetadataWithNotify(x, y, z, 7, 2);
	    		}
	        }
	    	
		}
    }

    
    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
    {                
        return true;
    }
}