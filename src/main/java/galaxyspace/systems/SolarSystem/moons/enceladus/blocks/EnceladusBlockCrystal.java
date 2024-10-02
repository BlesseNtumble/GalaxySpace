package galaxyspace.systems.SolarSystem.moons.enceladus.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EnceladusBlockCrystal extends BlockContainer implements IDetectableResource {
	

    public EnceladusBlockCrystal() {
            super(Material.glass);
            this.setBlockName("Crystall");
            this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/enceladus/enceladuscrystal");
            this.setHarvestLevel("pickaxe", 3);
            this.setHardness(3.0F);
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            this.setLightLevel(0.5F);
    }
    
    //Make sure you set this as your TileEntity class relevant for the block!
    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
            return new TileEntityBlockCrystallTE();
    }
    
    //You don't want the normal render type, or it wont render properly.
    @Override
    public int getRenderType() {
            return -1;
    }
    
    //It's not an opaque cube, so you need this.
    @Override
    public boolean isOpaqueCube() {
            return false;
    }
    
    public int getRenderBlockPass()
    {
    	return 1;
    }
    
    //It's not a normal block, so you need this too.
    public boolean renderAsNormalBlock() {
            return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return GSItems.BasicItems;
    }
    
    public int quantityDropped(Random random)
    {
        return 1 + random.nextInt(2);
    }

    @Override
    public int damageDropped(int metadata) {
        return 10;
    }

	@Override
	public boolean isValueable(int metadata) {
		return true;
	}

}
