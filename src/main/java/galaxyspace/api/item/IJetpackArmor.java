package galaxyspace.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IJetpackArmor {

	public static final String TAG_FUEL = "jetpack:fuel";
	public static final String TAG_ACT = "jetpack:activated";
	
	public void consumeFuel(ItemStack stack, int fuel);
	
	public void decrementFuel(ItemStack stack);
	
	public int getFuel(ItemStack stack);
	
	public boolean canFly(ItemStack stack, EntityPlayer player);
	
	public boolean isActivated(ItemStack stack);
	
	public void switchState(ItemStack stack, boolean state);
	
	public int getFireStreams(ItemStack stack);
}
