package galaxyspace.systems.SolarSystem.moons.io.blocks;

import asmodeuscore.api.item.IShiftDescription;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityTreasureChestIo;
import micdoodle8.mods.galacticraft.core.blocks.BlockTier1TreasureChest;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTier5TreasureChest extends BlockTier1TreasureChest implements IShiftDescription
{
    public BlockTier5TreasureChest(String assetName)
    {
        super(assetName);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTreasureChestIo();
    }

    @Override
    public String getShiftDescription(int meta)
    {
        return GCCoreUtil.translate(this.getTranslationKey() + ".desc");
    }
    
	@Override
	public String getDescription(int arg0) {
		return null;
	}
}