package galaxyspace.core.prefab.researches;

import galaxyspace.core.util.researches.BaseResearch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TestResearch extends BaseResearch{

	public TestResearch(int id, int posX, int posY) {
		super(id, "test_" + id, posX, posY);
		this.addNeedItem(new ItemStack(Items.APPLE));
	}

}
