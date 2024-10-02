package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFutureGlasses extends BlockGlass
{
	public static String[] metadata = new String[] {
		"FutureGlass_Black",
		"FutureGlass_Red",
		"FutureGlass_Green",
		"FutureGlass_Brown",
		"FutureGlass_Blue",
		"FutureGlass_Purple",
		"FutureGlass_Cyan",
		"FutureGlass_Lightgray",
		"FutureGlass_Gray",	
		"FutureGlass_Pink",
		"FutureGlass_Lime",
		"FutureGlass_Yellow",		
		"FutureGlass_Lightblue",
		"FutureGlass_Magneta",
		"FutureGlass_Orange",				
		"FutureGlass_White"		
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
    public BlockFutureGlasses()
    {
        super(Material.glass, false);
        this.setBlockName("FutureGlasses");
        this.setStepSound(soundTypeGlass);
        this.setBlockTextureName("dirt");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
     
    public boolean renderAsNormalBlock()
    {
        return false;
    }
        
    @Override
    public boolean isNormalCube() {
    	return true;
    }
        
    @Override
    public void registerBlockIcons(IIconRegister register) {
       	this.textures[0] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_black");
       	this.textures[1] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_red");
       	this.textures[2] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_green");
       	this.textures[3] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_brown");
       	this.textures[4] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_blue");
       	this.textures[5] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_purple");
       	this.textures[6] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_cyan");
       	this.textures[7] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_lightgray");
       	this.textures[8] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_gray");
       	this.textures[9] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_pink");
       	this.textures[10] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_lime");
       	this.textures[11] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_yellow");
       	this.textures[12] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_lightblue");
       	this.textures[13] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_magneta");
       	this.textures[14] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_orange");
       	this.textures[15] = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_white");
         
    }
        
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
       if (meta < 0 || meta >= this.textures.length + 1)
       {
            return this.textures[0];
       }

       return this.textures[meta];
    }
        
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
	}

	@Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < this.textures.length; ++i) {
			list.add(new ItemStack(block, 1, i));
		}
	}

} 
