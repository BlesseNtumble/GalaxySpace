package galaxyspace.core.util.researches;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;

public class BaseResearch implements IResearch{

	private static String name;
	private static Set<ItemStack> items_for_need = new HashSet();
	private static Set<ItemStack> unlock_items = new HashSet();
	private static IResearch parent = null;
	private static int id, need_exp = 0;
	
	
	public BaseResearch(int id, String name)
	{
		this(id, name, null);
	}
	
	public BaseResearch(int id, String name, IResearch parent)
	{
		this.id = id;
		this.name = name;
		this.parent = parent;
	}
	
	public void setExperince(int exp)
	{
		this.need_exp = exp;
	}
	
	public void addNeedItem(ItemStack stack)
	{
		this.items_for_need.add(stack);
	}
	
	public void addUnlockItem(ItemStack stack)
	{
		this.unlock_items.add(stack);
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Set<ItemStack> getNeedItems() {
		return this.items_for_need;
	}

	@Override
	public IResearch getParent() {
		return this.parent;
	}

	@Override
	public float getNeedExperience() {
		return this.need_exp;
	}

	@Override
	public Set<ItemStack> getUnlockItems() {
		return this.unlock_items;
	}

	@Override
	public int getID() {
		return this.id;
	}

}
