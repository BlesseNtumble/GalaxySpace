package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

public class Barnarda_C_Cactus extends BlockCactus {

    public Barnarda_C_Cactus() {
        setTranslationKey("barnarda_c_cactus");
        setDefaultState(blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        setTickRandomly(true);
        setCreativeTab(GSCreativeTabs.GSBlocksTab);
        setHardness(0.4F);
        setSoundType(SoundType.CLOTH);
    }
    
    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    	IBlockState plant = plantable.getPlant(world, pos.offset(direction));

        IBlockState sandBlockState = BRBlocks.BARNARDA_C_FALLING_BLOCKS.getDefaultState().withProperty(Barnarda_C_Falling_Blocks.BASIC_TYPE, Barnarda_C_Falling_Blocks.EnumFallingBlockBarnardaC.SAND);
        
        if(plant.getBlock() == BRBlocks.BARNARDA_C_CACTUS) {
            return this == BRBlocks.BARNARDA_C_CACTUS || this == sandBlockState.getBlock();
        }

        return false;
    }
}
