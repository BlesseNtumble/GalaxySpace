package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
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
	
	protected IIcon[] textures = new IIcon[metadata.length];
	
    public Barnarda_C_Dandelions()    
    {
        super(Material.plants);
        this.setBlockName("BarnardaCDandelions");
        this.setStepSound(Block.soundTypeGrass);
        this.setBlockTextureName("dirt");
        this.setTickRandomly(true);
    }
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z) instanceof Barnarda_C_Dandelions) {
			switch(world.getBlockMetadata(x, y, z)) {
				case 0:
					this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.8F, 0.7F);
					return;
				case 1:
					this.setBlockBounds(0.15F, 0F, 0.15F, 0.85F, 1F, 0.85F);
				case 2:
				case 3:
					this.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 0.75F, 0.85F);
					return;
				case 4:
					this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 1F, 0.8F);
					return;
				case 5:
					this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.5F, 0.8F);
					return;
				case 6:
				case 7:
					this.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1F, 0.85F);
			}
		}
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
		int meta = world.getBlockMetadata(x, y, z);
		switch (meta) {
			case 5:
				if (world.getBlock(x, y - 1, z) == this)
					ret.add(new ItemStack(this, 1, 4));
				else
					ret.add(new ItemStack(this, 1, 5));
				break;
			case 7:
				ret.add(new ItemStack(this, 1, 6));
				//The berries are dropped by getDrops();
				break;
		default:
			ret.add(new ItemStack(this, 1, meta));
		}
		return ret;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
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
        return metadata;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if (metadata == 7) {
            ret.add(new ItemStack(BRItems.Food, 1, 0));
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
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		if(world.getBlock(x, y, z) == this) {
			switch (world.getBlockMetadata(x, y, z)) {
				case 4:
					if (world.getBlock(x, y + 1, z) == this)
						world.setBlockToAir(x, y+1, z);
					break;
				case 5:
					if (world.getBlock(x, y - 1, z) == this)
						world.setBlockToAir(x, y-1, z);
					break;
			}
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}


	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 1) {
			if (!isValidPosition(world, x, y, z, ForgeDirection.DOWN, meta)) {
				this.dropBlockAsItem(world, x, y, z, meta, 0);
				world.setBlockToAir(x, y, z);
			}
		}
		else if (meta != 4)
			if(!isValidPosition(world, x, y, z, ForgeDirection.UP, meta)) {
				this.dropBlockAsItem(world, x, y, z, meta, 0);
				world.setBlockToAir(x, y, z);
			}
	}
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	if (!world.isRemote)
		{
	    	if((world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) >= 6))
	    	{
	    		if(world.isAirBlock(x, y + 1, z) )
	    		{
	    			int length = 1;
	    			while(world.getBlock(x, y - length, z) == this)
	    				length++;
	    			
	    			if(length < 4)
	    				world.setBlock(x, y + 1, z, this, 6, 3);
	    		
	    		}
	    		
	    		if(world.getBlockMetadata(x, y, z) == 6 && rand.nextInt(15) == 0)
	    		{
	    			world.setBlockMetadataWithNotify(x, y, z, 7, 2);
	    		}
	        }
	    	
		}
    }

    public boolean isValidPosition(World world, int x, int y, int z, ForgeDirection direction, int metadata)
    {
		if ((metadata == 1))
			return direction == ForgeDirection.DOWN && world.getBlock(x, y+1, z) instanceof Barnarda_C_Leaves;
		if(direction != ForgeDirection.UP)
			return false;
		Block block = world.getBlock(x, y-1, z);
		switch (metadata) {
			case 4:
				if(world.isAirBlock(x, y+1, z)) {
					if(block instanceof Barnarda_C_FallingBlocks) {
						world.setBlock(x, y, z, this, 4, 3);
						world.setBlock(x, y + 1, z, this, 5, 3);
						//Copy of onItemUse() in net.minecraft.item.ItemHoe
						world.playSoundEffect(x+0.5D, y+0.5D,z+0.5D,this.stepSound.getBreakSound() , (this.stepSound.getVolume() + 1.0F) / 2.0F, this.stepSound.getPitch() * 0.8F);
						return true;
					}
				}
				return false;
			case 5:
				return block instanceof Barnarda_C_FallingBlocks || (block instanceof Barnarda_C_Dandelions && world.getBlockMetadata(x, y-1, z) == 4);
			case 6:
			case 7:
				return block instanceof Barnarda_C_Grass || (block == this && world.getBlockMetadata(x, y-1, z) >= 6);
			default:
				return block instanceof Barnarda_C_Grass;

		}
    }

	/**
	 * THIS METHOD SHOULD NOT BE CALLED UNLESS YOU KNOW WHAT YOU ARE DOING, USE isValidPosition() IN MOST COMMON CASES.
	 * [GalaxySpace] If this method is called with itemStack.stackSize < 1 then unknown issues can occur. This should never happen, but it's food for thought.
	 */
	@Override
	public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack)
	{
		int meta = itemStack != null ? itemStack.getItemDamage() : 0;
		if(meta == 4)
			itemStack.stackSize--;
		return isValidPosition(world, x, y, z, ForgeDirection.getOrientation(side), meta);
	}
}