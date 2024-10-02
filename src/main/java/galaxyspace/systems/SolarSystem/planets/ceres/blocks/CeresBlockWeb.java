package galaxyspace.systems.SolarSystem.planets.ceres.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class CeresBlockWeb extends Block implements ITerraformableBlock
{

    public CeresBlockWeb()
    {
        super(Material.web);
        this.setBlockName("CeresWeb");
        this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/ceres/ceresweb");
    }
    
 
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }


	@Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity living)
	{
	        living.setInWeb();
	        if (world.provider instanceof IGalacticraftWorldProvider)
			{
	        	if (!(living instanceof EntityPlayer))
	        	{

	        	}
	        	else if (living instanceof EntityPlayerMP)
	        	{
	        		EntityPlayerMP player = (EntityPlayerMP)living;
	        		player.addPotionEffect(new PotionEffect(Potion.wither.id, 80));
	        	}
			}
	}
	
	@Override
	public boolean isOpaqueCube()
	{
	        return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
	
	@Override
	public int getRenderType()
    {
        return 1;
    }
	
	@Override
	public boolean renderAsNormalBlock()
	{
	    return false;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }
	
	@Override
	protected boolean canSilkHarvest()
    {
        return false;
    }

}