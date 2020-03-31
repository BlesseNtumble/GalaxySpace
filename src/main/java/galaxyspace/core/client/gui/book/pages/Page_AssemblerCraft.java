package galaxyspace.core.client.gui.book.pages;

import java.util.List;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.gui.book.Page_WithCraftMatrix;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.AssemblyRecipes;
import micdoodle8.mods.galacticraft.api.recipe.ShapelessOreRecipeGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

public abstract class Page_AssemblerCraft extends Page_WithCraftMatrix{

	@Override
	public ItemStack[] getRecipe() {
		List<IRecipe> rec = AssemblyRecipes.getRecipes(getItem());
		
		ItemStack[] items = new ItemStack[9];
		
		if(rec.get(0) instanceof ShapelessOreRecipeGC) {
			int i = 0;
			for(Object s : ((ShapelessOreRecipeGC)rec.get(0)).getInput())
			{				
				if(s instanceof ItemStack)
					items[i] = (ItemStack) s;
				else
				{
					items[i] = ((NonNullList<ItemStack>)s).get(0);
				}
				
				i++;				
			}
		}	
		return items;
	}
	
	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY)
	{
		super.drawPage(x, y, font, mouseX, mouseY);
	
		if(getRecipe().length > 0)
		{			
			this.drawText("Components: ", x + 275, y - 25, 0, font);
			
			this.mc.renderEngine.bindTexture(this.bookPageTexture);	
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			for(int i = 0; i < 3; i++)
				for(int k = 0; k < 3; k++) {
					if(this.checkClick(mouseX, mouseY, x + 310 + (k * 25), y + 82 + (i * 25), 16, 16))
						this.drawTexturedModalRect(x + 308 + (k * 25), y + 80 + (i * 25), 20, 20, 473, 94, 20, 20, false, false, 576, 250);
					else
						this.drawTexturedModalRect(x + 308 + (k * 25), y + 80 + (i * 25), 20, 20, 452, 94, 20, 20, false, false, 576, 250);
				}
			
			
			int z = 0, j = 0;
			
			//GalaxySpace.debug(getRecipe()+ "");
						
			for(ItemStack s : getRecipe())
			{				
				if(s == null) continue;
				
				RenderHelper.enableGUIStandardItemLighting();
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(s, x + 310 + (j * 25), y + 82 + (z * 25));
                RenderHelper.disableStandardItemLighting();
                
                if(this.checkClick(mouseX, mouseY, x + 310 + (j * 25), y + 82 + (z * 25), 16, 16))
                	this.drawToolTip(x + 308 + (j * 25), y + 80 + (z * 25), s.getDisplayName());
                
                j++;                
                if(j % 3 == 0) 
                {
                	z++;
                	j = 0;
                }
			}
			
			this.mc.getTextureManager().bindTexture(bookPageTexture);
			drawTexturedModalRect(x + 257, y + 36 , 4, 188, 492, 10, 14, 160, false, false, 512, 256);
				
		}		
		String str = GCCoreUtil.translate("book.page." + titlePage() + ".text");
		this.drawText(str, x, y - 40, this.getScroll(), font);
	}
}
