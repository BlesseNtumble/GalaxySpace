package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.GCItems;
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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class Barnarda_C_Ores extends Block{

	public static String[] metadata = new String[] {
		"coal_ore",	 		//0
		"iron_ore",			//1
		"gold_ore",			//2
		"redstone_ore",		//3
		"lapis_ore",		//4
		"diamond_ore",		//5
		"silicon_ore",		//6
		"copper_ore",		//7
		"tin_ore",			//8
		"aluminum_ore",		//9
		"quartz_ore",		//10
		"cobaltum_ore",		//11
		"nickel_ore"		//12
	};
	
	protected IIcon[] textures = new IIcon[metadata.length];
	
	public Barnarda_C_Ores()
    {
        super(Material.rock);
        this.setBlockName("BarnardaCOres");
        this.setStepSound(soundTypeStone);
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
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	switch (meta) {
	    	case 0:
	    		return Items.coal;
	    	case 3:
	    		return Items.redstone;
	    	case 4: 
	    		return Items.dye;
	    	case 5:
	    		return Items.diamond;
	    	case 6:
	    		return GCItems.basicItem;
	    	case 10:
	    		return Items.quartz;
	    	default:
	    		return Item.getItemFromBlock(this);
    	}
    }

    @Override
    public int damageDropped(int metadata) {
    	switch(metadata)
    	{
	    	case 0:
	    	case 3:
	    	case 5:
	    	case 10:
	    		return 0;
	    	case 4:
	    		return 4;
	    	case 6:
	    		return 2;
	    		
    		default: 
    			return metadata;
    	}
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	int bonus = 0;
		
		if (meta == 3) {
			bonus = 3;
		}

		if (meta == 6) {
			bonus = 1;
		}
		
		if (meta == 4) {
			bonus = 3;
		}
		
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(meta, random, fortune)) {
			int j = random.nextInt(fortune + 2) - 1;

			if (j < 0) {
				j = 0;
			}

			return this.quantityDropped(random) * (j + 1) + bonus;
		} 
		
		return this.quantityDropped(random) + bonus;
		
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
