package galaxyspace.core.util.researches;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TestResearch extends BaseResearch{

	public TestResearch(int id) {
		super(id, "xuy");
		this.addNeedItem(new ItemStack(Items.APPLE));
	}

}
