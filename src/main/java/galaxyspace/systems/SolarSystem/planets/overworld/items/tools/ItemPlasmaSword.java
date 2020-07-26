package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.ItemSwordGS;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPlasmaSword extends ItemSwordGS{

	public ItemPlasmaSword() {
		super("plasma_sword", GSItems.PLASMA_TOOLS);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		if(stack.getItemDamage() == stack.getMaxDamage()) return false;
		
        stack.damageItem(1, attacker);
        return true;
    }
	

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
		if(stack.getItemDamage() == stack.getMaxDamage()) return false;
		
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }
}
