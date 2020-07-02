package galaxyspace.core.util.researches;

import java.util.HashSet;
import java.util.Set;

import galaxyspace.api.IResearch;
import net.minecraft.item.ItemStack;

public class BaseResearch implements IResearch{

	private String name;
	private Set<ItemStack> items_for_need = new HashSet();
	private Set<ItemStack> unlock_items = new HashSet();
	private IResearch[] parents;
	private int id, need_exp = 0, posX = 0, posY = 0;
		
	public BaseResearch(int id, String name, int posX, int posY)
	{
		this(id, name, null, posX, posY);
	}
	
	public BaseResearch(int id, String name, IResearch[] parent, int posX, int posY)
	{
		this.id = id;
		this.name = name;
		this.parents = parent;
		this.posX = posX;
		this.posY = posY;
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
	public IResearch[] getParents() {
		return this.parents;
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

	@Override
	public int getPosX() {
		return this.posX;
	}

	@Override
	public int getPosY() {
		return this.posY;
	}

}
