package galaxyspace.core.client.jei.recycler;

import javax.annotation.Nonnull;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.jei.GSRecipeCategories;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class UniversalRecyclerRecipeCategory implements IRecipeCategory<UniversalRecyclerRecipeWrapper>
{
    private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");

    @Nonnull
    private final IDrawable background, arrow, fluid;
    
    @Nonnull
    private final IDrawable result;
    
    @Nonnull
    private final String localizedName;
    
    public UniversalRecyclerRecipeCategory(IGuiHelper guiHelper)
    {
    	
        this.background = guiHelper.createDrawable(guiTexture, 0, 10, 170, 78);
        this.localizedName = GCCoreUtil.translate("tile.universal_recycler.name");
        
        this.arrow = guiHelper.createDrawable(guiTexture, 192, 109, 36, 15);
       	this.fluid = guiHelper.createDrawable(guiTexture, 192, 66, 20, 42);      
        
        this.result = guiHelper.createDrawable(guiTexture, 192, 26, 20, 20);

    }

    @Nonnull
    @Override
    public String getUid()
    {
        return GSRecipeCategories.UNIVERSAL_RECYCLER;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return this.localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft mc)
    {
    	this.result.draw(mc, 39, 14);    	
    	this.result.draw(mc, 89, 34);
    	
    	this.fluid.draw(mc, 145, 22);
    	
    	this.arrow.draw(mc, 50, 37);
    }
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, UniversalRecyclerRecipeWrapper recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();        
        IGuiFluidStackGroup fluidstacks = recipeLayout.getFluidStacks();
            
        int xOffset = 5;
        int yOffset = 10;
        itemstacks.init(0, true, 35 + xOffset, 5 + yOffset);        
        itemstacks.init(1, false, 90, 25 + yOffset);

        itemstacks.set(ingredients);     
        
        if(recipe.getFluidStack() != null) {
        	fluidstacks.init(0, true, 147, 24, 16, 38, 1000, false, null);
        	fluidstacks.set(0, recipe.getFluidStack());
        }
        
    }

    @Override
    public String getModName()
    {
        return GalaxySpace.NAME;
    }
}
