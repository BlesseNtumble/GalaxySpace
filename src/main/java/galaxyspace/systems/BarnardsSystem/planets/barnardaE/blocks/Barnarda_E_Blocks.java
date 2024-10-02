package galaxyspace.systems.BarnardsSystem.planets.barnardaE.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class Barnarda_E_Blocks extends Block{

	public static String[] metadata = new String[] {
		"grunt",
		"subgrunt",
		"oreIron",
		"oreGold"
	};
	
	protected IIcon[] textures = new IIcon[metadata.length];
	
	public Barnarda_E_Blocks()
    {
        super(Material.rock);
        this.setBlockName("BarnardaEBlocks");
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setBlockTextureName("dirt");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return GSCreativeTabs.GSBlocksTab;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		for (int i = 0; i < metadata.length; i++)
			this.textures[i] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaE/" + metadata[i].toLowerCase());

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
   	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection side, IPlantable plant)
   	{
   		return false;
   	}
}
