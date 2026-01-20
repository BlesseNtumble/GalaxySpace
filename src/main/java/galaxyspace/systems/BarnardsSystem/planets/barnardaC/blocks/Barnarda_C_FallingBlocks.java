package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Barnarda_C_FallingBlocks extends BlockFalling implements ITerraformableBlock
{
	public static String[] metadata = new String[] {
		"sand"
	};
	
	protected IIcon[] textures = new IIcon[metadata.length];
	
    public Barnarda_C_FallingBlocks()
    {
        super();
        this.setBlockName("BarnardaCFallingBlocks");
        this.setHardness(1.0F);
        this.setStepSound(soundTypeSand);
        this.setHarvestLevel("shovel", 2);
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
		return false;
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
   	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection side, IPlantable plantable)
   	{
		Block plant = plantable.getPlant(world, x, y + 1, z);
		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		
		switch (plantType) {
			case Desert: 
				return this == BRBlocks.BarnardaCFallingBlocks && world.getBlockMetadata(x, y, z) == 0;
			default:
				break;
		}
   		return super.canSustainPlant(world, x, y, z, side, plantable);
   	}
    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (world.getBlock(x, y, z) == this)
            if (world.getBlock(x, y + 1, z) instanceof Barnarda_C_Dandelions && world.getBlockMetadata(x, y + 1, z) == 4)
                GSUtils.destroyBlock(world, x, y + 1, z, player, x, y, z);
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

}