package galaxyspace.systems.SolarSystem.planets.venus.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class VenusBlocks extends Block implements ITerraformableBlock, IDetectableResource
{

	public static String[] metadata = new String[] {
		"VenusGrunt",
		"VenusSubGrunt",
		"VenusSulfurOre",
		"VenusDiamondOre",
		"VenusVolcanicStone"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
    public VenusBlocks()
    {
        super(Material.rock);
        this.setBlockName("VenusBlocks");
        this.setHardness(3.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
        //this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "venus/venusgrunt");
    }
    
    @Override
    public int damageDropped(int metadata) {
    	if(metadata == 2) return 9;
    	if(metadata == 3) return 0;
    	if(metadata == 4) return 13;
        return metadata;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
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
    		case 2:
    		case 4: return GSItems.BasicItems;
    		case 3: return Items.diamond;
    	}
    	return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	switch(meta)
    	{    		
    		case 2: return 2 + random.nextInt(2) + random.nextInt(3 * (fortune + 1));
    		case 3: return 1 + random.nextInt(fortune + 1);
    		case 4: return 1;
    	}
        return 1;
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
	   	for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/venus/" + this.metadata[i].toLowerCase());

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
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.textures.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}

	@Override
	public boolean isValueable(int metadata) {
		if(metadata == 2 || metadata == 3) return true;
		return false;
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return false;
    }

}