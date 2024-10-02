package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Forest;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Swampland;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
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

public class Barnarda_C_Grass extends Block implements ITerraformableBlock{
	
	public static String[] metadata = new String[] {
		"plains_grass",
		"forest_grass",
		"swampland_grass",
		"jungle_grass"
	};
	
    protected IIcon[] BlockIconSide = new IIcon[metadata.length];
    protected IIcon[] BlockIconTop = new IIcon[metadata.length];
    protected IIcon[] BlockIconBottom = new IIcon[metadata.length];
	
	public Barnarda_C_Grass() {
		super(Material.grass);
		this.setBlockName("BarnardaCGrass");
	    this.setHardness(0.8F);
	    this.setStepSound(soundTypeGrass);
	    this.setHarvestLevel("shovel", 0);
	    this.setTickRandomly(true);
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
    	return BRBlocks.BarnardaCBlocks.getItemDropped(0, random, par3);
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
	}
    
    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.metadata.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	for (int i = 0; i < metadata.length; i++) {
    		BlockIconSide[i] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/"+metadata[i]+"_side");
    		BlockIconTop[i] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/"+metadata[i]+"_top");
    		BlockIconBottom[i] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/dirt");
    	}
    }
    
    @Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection side, IPlantable plant)
	{
		return true;
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        
    	if (meta < 0 || meta >= this.metadata.length)
        {
             return this.BlockIconBottom[0];
        }
    	
        if (side == 1 ) { return BlockIconTop[meta]; }
        else if (side == 0) { return BlockIconBottom[meta]; }
        //else if (side != meta) { return BlockIconSide[meta]; }
        else { return BlockIconSide[meta]; }
    }
    
    @Override
	public void updateTick(World world, int par2, int par3, int par4, Random par5Random)
	{
		if (!world.isRemote)
		{
			if (world.getBlockLightValue(par2, par3 + 1, par4) < 4 && world.getBlockLightOpacity(par2, par3 + 1, par4) > 2)
			{
				world.setBlock(par2, par3, par4, BRBlocks.BarnardaCBlocks, 0, 3);
			}
			else if (world.getBlockLightValue(par2, par3 + 1, par4) >= 5)
			{
				for (int var6 = 0; var6 < 4; ++var6)
				{
					int var7 = par2 + par5Random.nextInt(3) - 1;
					int var8 = par3 + par5Random.nextInt(5) - 3;
					int var9 = par4 + par5Random.nextInt(3) - 1;
					Block var10 = world.getBlock(var7, var8 + 1, var9);

					if (world.getBlock(var7, var8, var9) == BRBlocks.BarnardaCBlocks && world.getBlockMetadata(var7, var8, var9) == 0)
					{
						if (world.getBlockLightValue(var7, var8 + 1, var9) >= 4 && var10.getLightOpacity() <= 2)
						{
							int meta = 0;
							if(world.provider instanceof WE_WorldProvider) {
								if(WE_Biome.getBiomeAt(var7, var9) instanceof Barnarda_C_Forest) meta = 1;
								if(WE_Biome.getBiomeAt(var7, var9) instanceof Barnarda_C_Swampland) meta = 2;
							}
							world.setBlock(var7, var8, var9, BRBlocks.BarnardaCGrass, meta, 3);
						}
					}
				}
			}
		}
	}
    
    @Override
   	public boolean isTerraformable(World world, int x, int y, int z) {
   		return false;
   	}
   /* 
    @Override
    @SideOnly(Side.CLIENT)   
    public int getBlockColor()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return 0x89AC76;//ColorizerGrass.getGrassColor(d0, d1);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return this.getBlockColor();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldAc, int x, int y, int z)
    {
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        World world = FMLClientHandler.instance().getWorldClient();
        
        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
            	int i2 = 0x89AC76;//world.getBiomeGenForCoords(x + l1, z + k1).getBiomeGrassColor(x + l1, y, z + k1);
            	if(world.provider instanceof WE_WorldProvider)
            		i2 = WE_Biome.getBiomeAt(x, z).biomeGrassColor;
                
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }*/
}
