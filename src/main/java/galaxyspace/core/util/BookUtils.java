package galaxyspace.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

import galaxyspace.api.IBookPage;
import net.minecraft.util.ResourceLocation;

public class BookUtils {
	
	public static Map<String, ResourceLocation> GUIDE_BOOK_CATEGORIES = new LinkedHashMap<String, ResourceLocation>();
	public static Map<IBookPage, String> GUIDE_BOOK_PAGES = new LinkedHashMap<IBookPage, String>();
	
	public enum Book_Cateroies {
		GENERAL,
		BODIES,
		BLOCKS,
		ITEMS,
		MECHANICS;
		
		public String getName()
		{
			return this.name().toLowerCase();
		}
	}
		
	public static void addGuideBookCategory(String name, ResourceLocation icon)
	{
		GUIDE_BOOK_CATEGORIES.put(name, icon);
	}
		
	public static void addGuideBookPage(String category, IBookPage page)
	{
		GUIDE_BOOK_PAGES.put(page, category);
	}
}
