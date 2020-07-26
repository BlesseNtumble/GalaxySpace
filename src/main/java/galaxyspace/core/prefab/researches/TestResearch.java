package galaxyspace.core.prefab.researches;

import galaxyspace.core.util.researches.BaseResearch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TestResearch extends BaseResearch{

	public TestResearch(int id) {
		super(id, "test_" + id, 5, 5);
		this.addNeedItem(new ItemStack(Items.APPLE));
	}

}
