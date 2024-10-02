package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Barnarda_C_Water_Grass extends BlockLilyPad {

	public Barnarda_C_Water_Grass()
	{
		super();
		this.setBlockName("BarnardaCWaterGrass");
        this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/water_grass");
        this.setStepSound(Block.soundTypeGrass);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        if (entity == null || !(entity instanceof EntityBoat))
        {
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }
    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity instanceof EntityBoat)
        {
            world.func_147480_a(x,y,z, true);
        }
		
        entity.motionX = entity.motionZ /= 2;
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int par3) {
		return null;
	}
}
