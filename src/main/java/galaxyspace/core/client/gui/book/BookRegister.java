package galaxyspace.core.client.gui.book;

import asmodeuscore.core.utils.BookUtils;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.book.pages.bodies.Page_BarnardsSystem;
import galaxyspace.core.client.gui.book.pages.bodies.Page_ProximaSystem;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BookRegister {

	public enum Recipe_Type
	{
		CRAFTING_TABLE(new ItemStack(Blocks.CRAFTING_TABLE)),
		NASA_WORKBENCH(new ItemStack(GCBlocks.nasaWorkbench)),
		COMPRESSOR(new ItemStack(GCBlocks.machineBase, 1, 12)),
		ASSEMBLER(new ItemStack(GSBlocks.ASSEMBLER)),
		ROCKET_ASSEMBLER(new ItemStack(GSBlocks.ROCKET_ASSEMBLER)),
		UNIVERSAL_RECYCLER(new ItemStack(GSBlocks.UNIVERSAL_RECYCLER)),
		SEPARATOR(new ItemStack(GSBlocks.LIQUID_SEPARATOR));
		
		private ItemStack item;
		Recipe_Type(ItemStack item)
		{
			this.item = item;
		}
		
		public ItemStack getItem()
		{
			return this.item;
		}
	}
	
	public static void registerCatergories()
	{
		BookUtils.addGuideBookCategory(Book_Cateroies.GENERAL.getName(), new ResourceLocation("textures/items/apple.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.BODIES.getName(), SolarSystemBodies.planetNeptune.getBodyIcon());
		BookUtils.addGuideBookCategory(Book_Cateroies.BLOCKS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/blocks/overworld/hydroponic_base.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.ITEMS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/volcanic_stone.png"));
		BookUtils.addGuideBookCategory(Book_Cateroies.MECHANICS.getName(), new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/basic/temp_shield_control.png"));
		
		if(ACConfigCore.enableACentauriSystems)
			BookUtils.addGuideBookPage(Book_Cateroies.BODIES.getName(), new Page_ProximaSystem());
		if(BRConfigCore.enableBarnardsSystems)
			BookUtils.addGuideBookPage(Book_Cateroies.BODIES.getName(), new Page_BarnardsSystem());
	}
}
