package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.prefab.entities.EntityIceSpike;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTerraManipulator extends Item {

	public static int DISTANCE = 15;
	
	public ItemTerraManipulator()
	{
		this.setMaxDamage(1000);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("matter_manipulator");
		this.setCreativeTab(GSCreativeTabs.GSArmorTab);
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSArmorTab || tab == CreativeTabs.SEARCH)
        {
    		list.add(new ItemStack(this, 1, 0));
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
	
		if(stack.hasTagCompound())
		{
			list.add(EnumColor.DARK_RED + "Timer: " + stack.getTagCompound().getInteger("timer"));
		}
	}
	
	public static RayTraceResult getRay(World world, EntityPlayer player, boolean useLiquids)
	{
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		double d0 = player.posX;
		double d1 = player.posY + (double) player.getEyeHeight();
		double d2 = player.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() + 10;
		Vec3d vec3d1 = vec3d.addVector((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {		
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult ray = this.getRay(world, player, true);//player.rayTrace(DISTANCE, player.ticksExisted);
				
		/*if(world.isRemote)
		{
			GalaxySpace.proxy.spawnParticle("beamFX", new Vector3(), new Vector3(), new Object[] { new Vector3(0.0F, 0.0F, 0.0F) });
		}*/
		if(!world.isRemote) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("timer", 0);
			}
					
			stack.getTagCompound().setInteger("timer", stack.getTagCompound().getInteger("timer") + 1);
			
			int i = stack.getTagCompound().getInteger("timer");	
			
			//MODE: DIG
			
			if(ray != null && i % 1 == 0) {
				IBlockState block = world.getBlockState(ray.getBlockPos());
				
				EntityIceSpike beam = new EntityIceSpike(world, player);
				beam.posY = player.posY + (double)(player.height / 2.0F) + 0.5D;
				beam.shoot(player, player.rotationPitch, player.rotationYawHead, 0.0F, 0.8F, 0.0F);
				//beam.shoot(player.posX, player.posY, player.posZ, 0.8F, 0.0F);
				world.spawnEntity(beam);
				
				if(!world.isRemote && block.isBlockNormalCube())
				{						
					if(player.inventory.getFirstEmptyStack() > 0)
						player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(block.getBlock())));
					world.destroyBlock(ray.getBlockPos(), false);
				}
								
				//return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
			}	
			
			
			if(i % 5 == 0) {
	
				EntityIceSpike entitysmallfireball = new EntityIceSpike(world, player);
				entitysmallfireball.posY = player.posY + (double)(player.height / 2.0F) + 0.5D;
				entitysmallfireball.shoot(player, player.rotationPitch, player.rotationYawHead, 0.0F, 0.8F, 0.0F);
				world.spawnEntity(entitysmallfireball);				
				
			}
			
			stack.getTagCompound().setInteger("timer", 1);
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			
			//GalaxySpace.debug("work");
			if(entityplayer.getActiveItemStack().hasTagCompound())
			{
				if(entityplayer.getActiveItemStack().getTagCompound().hasKey("timer"))
				{
					entityplayer.getActiveItemStack().getTagCompound().setInteger("timer", 1);
				}
			}
		}
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 10;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
}
