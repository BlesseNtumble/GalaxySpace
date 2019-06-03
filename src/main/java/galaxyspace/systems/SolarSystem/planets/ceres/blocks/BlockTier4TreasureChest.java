package galaxyspace.systems.SolarSystem.planets.ceres.blocks;

import asmodeuscore.api.item.IShiftDescription;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityTreasureChestCeres;
import micdoodle8.mods.galacticraft.core.blocks.BlockTier1TreasureChest;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTier4TreasureChest extends BlockTier1TreasureChest implements IShiftDescription
{
    public BlockTier4TreasureChest(String assetName)
    {
        super(assetName);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTreasureChestCeres();
    }

    @Override
    public String getShiftDescription(int meta)
    {
        return GCCoreUtil.translate(this.getUnlocalizedName() + ".desc");
    }
    
	@Override
	public String getDescription(int arg0) {
		return null;
	}
}