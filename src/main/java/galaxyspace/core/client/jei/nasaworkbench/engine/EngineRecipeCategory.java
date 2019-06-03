package galaxyspace.core.client.jei.nasaworkbench.engine;

import javax.annotation.Nonnull;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.jei.GSRecipeCategories;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class EngineRecipeCategory implements IRecipeCategory
{
    private static final ResourceLocation rocketGuiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_clear.png");

    @Nonnull
    private final IDrawable background;
    
    @Nonnull
    private final IDrawable result;
    
    @Nonnull
    private final String localizedName;

    public EngineRecipeCategory(IGuiHelper guiHelper)
    {
    	
        this.background = guiHelper.createDrawable(rocketGuiTexture, 3, 4, 187, 151);
        this.localizedName = GCCoreUtil.translate("tile.rocket_workbench.name");
        
        this.result = guiHelper.createDrawable(rocketGuiTexture, 192, 21, 34, 34);

    }

    @Nonnull
    @Override
    public String getUid()
    {
        return GSRecipeCategories.ENGINE;
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
    public void drawExtras(Minecraft minecraft)
    {
    	this.result.draw(minecraft, 135, 110);
    }
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

        int xOffset = 7;
        int yOffset = 10;
        itemstacks.init(0, true, 5 + xOffset, 25 + yOffset);
        itemstacks.init(11, true, 5 + xOffset, 25+16*3 + yOffset);
        
        itemstacks.init(1, true, 21 + xOffset, 25 + yOffset);
        itemstacks.init(5, true, 21 + xOffset, 25+16 + yOffset);
        itemstacks.init(6, true, 21 + xOffset, 25+16*2 + yOffset);
        itemstacks.init(12, true, 21 + xOffset, 25+16*3 + yOffset);
        itemstacks.init(16, true, 21 + xOffset, 25+16*4 + yOffset);
        
        itemstacks.init(2, true, 37 + xOffset, 25 + yOffset);
        itemstacks.init(7, true, 37 + xOffset, 25+16 + yOffset);
        itemstacks.init(8, true, 37 + xOffset, 25+16*2 + yOffset);
        itemstacks.init(13, true, 37 + xOffset, 25+16*3 + yOffset);
        
        itemstacks.init(3, true, 53 + xOffset, 25 + yOffset);
        itemstacks.init(9, true, 53 + xOffset, 25+16 + yOffset);
        itemstacks.init(10, true, 53 + xOffset, 25+16*2 + yOffset);
        itemstacks.init(14, true, 53 + xOffset, 25+16*3 + yOffset);
        itemstacks.init(17, true, 53 + xOffset, 25+16*4 + yOffset);
        
        itemstacks.init(4, true, 69 + xOffset, 25 + yOffset);
        itemstacks.init(15, true, 69 + xOffset, 25+16*3 + yOffset);
        
        itemstacks.init(18, false, 143, 118);

        itemstacks.set(ingredients);
    }

    @Override
    public String getModName()
    {
        return GalaxySpace.NAME;
    }
}
