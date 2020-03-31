package galaxyspace.core.util.researches;

import java.util.Set;

import net.minecraft.item.ItemStack;

public interface IResearch {

	public abstract int getID();
	public abstract String getName();
	public abstract Set<ItemStack> getNeedItems();
	public abstract IResearch[] getParents();
	public abstract float getNeedExperience();	
	public abstract Set<ItemStack> getUnlockItems();
	public abstract int getPosX();
	public abstract int getPosY();	
}
