package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOres extends Block implements ITerraformableBlock, IDetectableResource
{

	public static String[] metadata = new String[] {
		"CobaltOre",
		"NickelOre",
		"SapphireMoonOre",
		"UraniumOre",
		"SapphireBlock"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
    public BlockOres()
    {
        super(Material.rock);
        this.setBlockName("GSOres");
        this.setHardness(2.0F);
        this.setStepSound(soundTypeStone);
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
    public void registerBlockIcons(IIconRegister register) {
    	for(int i = 0; i < metadata.length; i++)    	
    		this.textures[i] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/" + metadata[i].toLowerCase());    		 
    	     	
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
    		case 2: return 6;
    		case 3: return 16;
    		default: return metadata;
    	}
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	if(meta == 2 || meta == 3) return GSItems.BasicItems;
    	return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	if(meta == 2) return fortune > 0 ? 1 + random.nextInt(fortune) : 1;
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
	public boolean isTerraformable(World world, int x, int y, int z) {
		return false;
	}


	@Override
	public boolean isValueable(int metadata) {
		return true;
	}
	/*
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){ 

		ItemStack i = player.inventory.getCurrentItem();
		 
		if(i != null && i.getItem() == GSItems.BasicItems) { 
			world.setBlock(x, y+1, z, GSBlocks.FutureGlass); 
			return true; 
		} 
		return false; 
	}*/

}