package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.ItemSwordGS;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPlasmaSword extends ItemSwordGS implements IModificationItem{

	public static String heat = "plasma_heat";
	
	public ItemPlasmaSword() {
		super("plasma_sword", GSItems.PLASMA_TOOLS);
		this.addPropertyOverride(new ResourceLocation("heat"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				
				if (entityIn == null)                
                    return 0.0F;
				
				boolean flag = entityIn.getHeldItemMainhand() == stack;
                boolean flag1 = entityIn.getHeldItemOffhand() == stack;
                
                if(flag || flag1)
                	if(stack.hasTagCompound() && stack.getTagCompound().hasKey(heat))
                		if(stack.getTagCompound().getFloat(heat) >= 10.0F)
                			return entityIn instanceof EntityPlayer ? 1.0F : 0.0F;
                
                return 0.0F;
			}
		});
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
		if(!world.isRemote && entity instanceof EntityPlayer)
		{
			if(!stack.hasTagCompound()) 
				stack.setTagCompound(new NBTTagCompound());

			if(!stack.getTagCompound().hasKey(heat))
				stack.getTagCompound().setFloat(heat, 0.0F);
			
			if(world.rand.nextInt(30) == 0 && stack.getTagCompound() != null)		
				if(stack.getTagCompound().hasKey(heat) && stack.getTagCompound().getFloat(heat) > 0.2F)
					stack.getTagCompound().setFloat(heat, stack.getTagCompound().getFloat(heat) - 0.2F);
		}
    }

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumColor.DARK_RED + "[WIP] Content");
		
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey(heat))
			list.add("Heat: " + stack.getTagCompound().getFloat(heat));
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		if(stack.getItemDamage() == stack.getMaxDamage()) return false;
		
		stack.damageItem(1, attacker);
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(heat) && stack.getTagCompound().getFloat(heat) <= 11.0F) 
			stack.getTagCompound().setFloat(heat, stack.getTagCompound().getFloat(heat) + 0.5F);
		
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

	@Override
	public Module_Type getType(ItemStack stack) {
		return Module_Type.PLASMA_TOOLS;
	}

	@Override
	public ItemModule[] getAvailableModules() {
		return null;
	}

	@Override
	public int getModificationCount(ItemStack stack) {
		return 3;
	}
}
