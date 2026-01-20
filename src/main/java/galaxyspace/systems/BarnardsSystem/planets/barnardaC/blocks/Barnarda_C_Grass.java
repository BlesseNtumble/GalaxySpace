package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;

import java.util.Random;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.api.block.IOverlayBlock;
import galaxyspace.core.client.render.block.LayerBlockRender;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.WorldProviderBarnardaC_WE;
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

public class Barnarda_C_Grass extends Block implements ITerraformableBlock, IOverlayBlock {
    protected IIcon blockIconSide;
    protected IIcon blockIconTop;
    protected IIcon blockIconSideSnow;
    protected IIcon blockIconSideOverlay;
    /**
     * Used by the ISBRH renderer to easily render the biome colored textures.
     **/
    boolean isRenderingOverlay;

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
        return 0;
    }
    
    @Override
    public int damageDropped(int metadata) {
    	return 0;
    }

    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		world.setBlockMetadataWithNotify(x, y, z, 0, 3);
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		blockIconSide = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":barnardssystem/barnardaC/grass_side");
		blockIconTop = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":barnardssystem/barnardaC/grass_top");
        blockIconSideSnow = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":barnardssystem/barnardaC/grass_snowed");
        blockIconSideOverlay = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":barnardssystem/barnardaC/grass_side_overlay");
    }
    
    @Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection side, IPlantable plant)
	{
		return (plant instanceof Barnarda_C_Dandelions) || (plant.getPlantMetadata(world, x, y, z) == 0 || plant.getPlantMetadata(world, x, y, z) == 3 || plant.getPlantMetadata(world, x, y, z) > 5);
	}

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        switch(ForgeDirection.getOrientation(side)) {
            case UP:
                return blockIconTop;
            case DOWN:
                return BRBlocks.BarnardaCBlocks.getIcon(0, 0);
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                if(isRenderingOverlay) //This is how it knows whether you do colors or not
                    return blockIconSideOverlay;
                return blockIconSide;
        }
        return blockIconTop;
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
							world.setBlock(var7, var8, var9, BRBlocks.BarnardaCGrass, 0, 3);
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

    @Override
    public boolean hasBlockOnSide(IBlockAccess world, Block blockToCheckFor, int x, int y, int z, int side) {
       if(ForgeDirection.getOrientation(side) == ForgeDirection.UP)
           return world.getBlock(x, y + 1, z) == blockToCheckFor;
       return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        if(isRenderingOverlay && side == 0) //Bottom face
            return false;
        if(!isRenderingOverlay && side == 1) //Top face
            return false;
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public boolean shouldOverlayColor(int meta) {
        return true;
    }
    @Override
    public boolean getIsRenderingOverlay() {
        return isRenderingOverlay;
    }
    @Override
    public void setIsRenderingOverlay(boolean bool) {
        isRenderingOverlay = bool;
    }
    @Override
    public int getRenderType() {
        return LayerBlockRender.glowBlockID;
    }

	//TODO: TEST THIS STUFF, MAKE SURE TO TWEAK THE BIOME COLOR SETTINGS IN THE BIOME PROVIDERS IF IT WORKS

    @Override
    @SideOnly(Side.CLIENT)   
    public int getBlockColor()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return 0x89AC76;
		//TODO: Maybe use this line, look into how grass works for vanilla
		//ColorizerGrass.getGrassColor(d0, d1);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
	 **/
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta)
    {
        return this.getBlockColor();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldAc, int x, int y, int z)
    {
        int r = 0;
        int g = 0;
        int b = 0;
        World world = FMLClientHandler.instance().getWorldClient();
        for (int xi = -1; xi <= 1; ++xi) {
            for (int zi = -1; zi <= 1; ++zi) {
                int color = 0x89AC76;
                if (world.provider instanceof WorldProviderBarnardaC_WE) {
                    try {
                        color = WE_Biome.getBiomeAt(WorldProviderBarnardaC_WE.chunk, x + xi, z + zi).biomeGrassColor;
                    } catch (Exception e) {
                        GalaxySpace.info("[ERROR] Failed to get world provider and biome for dimension: " + world.provider);
                    }

                }

                r += (color & 0xFF0000) >> 16;
                g += (color & 0x00FF00) >> 8;
                b += color & 0x0000FF;
            }
        }
        //Average of colors in 3x3 area.
        //Returns 0x000000 if worldAc is not world
        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
    }

}
