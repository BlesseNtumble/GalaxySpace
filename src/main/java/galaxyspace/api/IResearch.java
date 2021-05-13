package galaxyspace.api;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IResearch {

	public abstract int getID();
	public abstract String getName();
	public abstract List<ItemStack> getNeedItems();
	public abstract IResearch[] getParents();
	public abstract float getNeedExperience();	
	public abstract List<ItemStack> getUnlockItems();
	public abstract int getPosX();
	public abstract int getPosY();	
	public abstract String getDescription();
	public abstract int getTextureID();
}
