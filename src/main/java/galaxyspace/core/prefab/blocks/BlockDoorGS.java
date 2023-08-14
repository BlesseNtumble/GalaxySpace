package galaxyspace.core.prefab.blocks;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDoorGS extends BlockDoor {
    private Item item;

    public BlockDoorGS(String name)
    {
        super(Material.WOOD);
        this.setHardness(3.0F);
        this.setSoundType(SoundType.WOOD);
        this.setTranslationKey(name);
        this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(OPEN, false)
                .withProperty(HINGE, BlockDoorGS.EnumHingePosition.LEFT)
                .withProperty(POWERED, false)
                .withProperty(HALF, BlockDoorGS.EnumDoorHalf.LOWER));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(item);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (!(state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER))
        {
            return item;
        }
        return Items.AIR;
    }

    public void setDoorItem(Item item) {
        this.item = item;
    }
}
