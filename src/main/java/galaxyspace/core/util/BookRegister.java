package galaxyspace.core.util;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.BookUtils.Book_Cateroies;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import net.minecraft.util.ResourceLocation;

public class BookRegister {

	public static void registerCatergories()
	{
		BookUtils.addGuideBookCategory(Book_Cateroies.GENERAL.getName(), new ResourceLocation("textures/items/apple.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.BODIES.getName(), SolarSystemBodies.planetNeptune.getBodyIcon());
		BookUtils.addGuideBookCategory(Book_Cateroies.BLOCKS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/blocks/overworld/hydroponic_base.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.ITEMS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/VolcanicStone.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.MECHANICS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/SchematicBox.png"));
		//BookUtils.addGuideBookCategory("galaxy_addition", new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/wafer_modern.png"));
	}
}
