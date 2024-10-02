package galaxyspace.core.prefab.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.api.block.IGlowBlock;
import galaxyspace.core.client.render.block.LayerBlockRender;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDungeonBricks extends Block implements IGlowBlock {

	public static String[] metadata = new String[] {
			"CeresBricks",
			"IoBricks"
		};
	

	protected IIcon[][] textures = new IIcon[this.metadata.length][2];
	
	public BlockDungeonBricks()
    {
        super(Material.rock);
        this.setBlockName("DungeonBricks");
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return GSCreativeTabs.GSBlocksTab;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    } 

	@Override
	public int damageDropped(int metadata) {
		return metadata;
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
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		for(int i = 0; i < this.metadata.length; i++) {
	   		this.textures[i][0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockdungeonbricks/" + this.metadata[i].toLowerCase());
	   		this.textures[i][1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockdungeonbricks/" + this.metadata[i].toLowerCase() + "_layer");

		}
		
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta > this.textures.length)
        {
            return this.textures[0][0];
        }

        return this.textures[meta][0];
    }
	
	@Override
	public int getRenderType() {
		return LayerBlockRender.glowBlockID;
	}

	@Override
	public IIcon getOverlayForSide(int side, int meta) {
		return this.textures[meta][1];
	}

	@Override
	public boolean enableGlow(int meta) {
		return true;
	}

	@Override
	public int alphaGlow(int meta) {
		switch(meta)
		{
			case 0: return 20;
			case 1: return 100;
			default: return 0;
		}
	}
	
	
}
