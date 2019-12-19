package galaxyspace.core.client.gui.book;

import asmodeuscore.core.astronomy.gui.book.Page_WithCraftMatrix.Recipe_Type;
import asmodeuscore.core.utils.BookUtils;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.book.pages.bodies.Page_BarnardsSystem;
import galaxyspace.core.client.gui.book.pages.bodies.Page_ProximaSystem;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class BookRegister {

	public static Recipe_Type ASSEMBLER = EnumHelper.addEnum(Recipe_Type.class, "ASSEMBLER", new Class<?>[]{ItemStack.class}, new ItemStack(GSBlocks.ASSEMBLER));
	public static Recipe_Type ROCKET_ASSEMBLER = EnumHelper.addEnum(Recipe_Type.class, "ROCKET_ASSEMBLER", new Class<?>[]{ItemStack.class}, new ItemStack(GSBlocks.ROCKET_ASSEMBLER));
	public static Recipe_Type UNIVERSAL_RECYCLER = EnumHelper.addEnum(Recipe_Type.class, "UNIVERSAL_RECYCLER", new Class<?>[]{ItemStack.class}, new ItemStack(GSBlocks.UNIVERSAL_RECYCLER));
	public static Recipe_Type SEPARATOR = EnumHelper.addEnum(Recipe_Type.class, "SEPARATOR", new Class<?>[]{ItemStack.class}, new ItemStack(GSBlocks.LIQUID_SEPARATOR));
	
	public static void registerCatergories()
	{		
		BookUtils.addGuideBookCategory(Book_Cateroies.GENERAL.getName(), new ResourceLocation("textures/items/apple.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.BODIES.getName(), SolarSystemBodies.planetNeptune.getBodyIcon());
		BookUtils.addGuideBookCategory(Book_Cateroies.BLOCKS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/blocks/overworld/hydroponic_base.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.ITEMS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/volcanic_stone.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.MECHANICS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/temp_shield_control.png"));
		
		if(ACConfigCore.enableACentauriSystems)
			BookUtils.addGuideBookPage(new Page_ProximaSystem());
		if(BRConfigCore.enableBarnardsSystems)
			BookUtils.addGuideBookPage(new Page_BarnardsSystem());
	}
}
