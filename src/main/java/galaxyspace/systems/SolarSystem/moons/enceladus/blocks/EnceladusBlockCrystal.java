package galaxyspace.systems.SolarSystem.moons.enceladus.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EnceladusBlockCrystal extends BlockContainer implements IDetectableResource {


    public static final Block.SoundType soundTypeCrystal = new Block.SoundType("stone", 0.75F, 0.75F)
    {
        private static final String __OBFID = "CL_00000200";
        /**
         * Used when a block breaks, e.g.: Player break, Sheep eating grass, etc..
         */
        public String getBreakSound()
        {
            return "dig.glass";
        }
        public String func_150496_b()
        {
            return "step.stone";
        }
    };

    public EnceladusBlockCrystal() {
            super(Material.glass);
            this.setBlockName("Crystall");
            this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/enceladus/enceladuscrystal");
            this.setHarvestLevel("pickaxe", 3);
            this.setHardness(3.0F);
            this.setStepSound(soundTypeCrystal);
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            this.lightValue = 8;
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
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
    {
            if (!isValidPosition(world, x, y, z, ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)))) {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, this.stepSound.getBreakSound(), (this.stepSound.getVolume() + 1.0F) / 2.0F, this.stepSound.getPitch() * 0.8F);
                world.setBlock(x, y, z, Blocks.air);
            }
    }

    public static boolean isValidPosition(World world, int x, int y, int z, ForgeDirection direction)
    {
        if(direction == ForgeDirection.UNKNOWN)
            return false;
        Block supportingBlock = world.getBlock(x-direction.offsetX, y-direction.offsetY, z-direction.offsetZ);
        int meta = world.getBlockMetadata(x-direction.offsetX, y-direction.offsetY, z-direction.offsetZ);
        return (supportingBlock == GSBlocks.EnceladusBlocks && meta == 1) || (supportingBlock == Blocks.packed_ice && world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) != 15);

    }
    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return isValidPosition(world, x, y, z, ForgeDirection.getOrientation(side));
    }
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return side;
    }
    @Override
    public boolean isValueable(int metadata) {
        return true;
    }

}
