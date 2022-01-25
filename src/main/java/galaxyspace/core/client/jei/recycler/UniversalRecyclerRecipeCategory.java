package galaxyspace.core.client.jei.recycler;

import javax.annotation.Nonnull;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.jei.GSRecipeCategories;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.util.GSConstants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class UniversalRecyclerRecipeCategory implements IRecipeCategory<UniversalRecyclerRecipeWrapper>
{
    private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");

    @Nonnull
    private final IDrawable backgroundTop, background, backgroundBottom, slot, blankArrow, fluid, fluid_foreground;
    @Nonnull
    private final IDrawableAnimated progressBar;
    @Nonnull
    private final String localizedName;
    
    public UniversalRecyclerRecipeCategory(IGuiHelper guiHelper)
    {
    	
    	ResourceLocation gui = GSConfigCore.enableModernGUI ? GSConstants.GUI_MACHINE_MODERN : GSConstants.GUI_MACHINE_CLASSIC; 
        
        this.localizedName = GCCoreUtil.translate("tile.universal_recycler.name");
        
        this.backgroundTop = guiHelper.createDrawable(gui, 0, 0, 176, 12);
    	this.background = guiHelper.createDrawable(gui, 0, 0, 176, 92);
    	this.backgroundBottom = guiHelper.createDrawable(gui, 0, 31, 176, 30);
    	this.slot = guiHelper.createDrawable(gui, 0, 62, 18, 18);
    	this.blankArrow = guiHelper.createDrawable(gui, 181, 109, 36, 15); 
    	
    	this.fluid = guiHelper.createDrawable(gui, 180, 67, 18, 40); 
    	this.fluid_foreground = guiHelper.createDrawable(gui, 201, 67, 18, 40); 
    	
    	
    	IDrawableStatic progressBarDrawable = guiHelper.createDrawable(gui, 181, 125, 36, 16);//guiHelper.createDrawable(compressorTex, 180, 15, 52, 17);
        this.progressBar = guiHelper.createAnimatedDrawable(progressBarDrawable, 70, IDrawableAnimated.StartDirection.LEFT, false);
     
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
    	this.backgroundBottom.draw(mc, 0, 59);
    	
    	this.slot.draw(mc, 20, 35);
    	this.slot.draw(mc, 90, 35);
    	this.slot.draw(mc, 112, 35);
    	
        this.blankArrow.draw(mc, 45, 39);
        this.progressBar.draw(mc, 45, 38);
        
    	this.fluid.draw(mc, 146, 23);
    	this.fluid_foreground.draw(mc, 147, 23);
    	
    	
    	
    }
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, UniversalRecyclerRecipeWrapper recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();        
        IGuiFluidStackGroup fluidstacks = recipeLayout.getFluidStacks();
               
                    
        int xOffset = 5;
        int yOffset = 10;
        itemstacks.init(0, true, 15 + xOffset, 25 + yOffset);        
        itemstacks.init(1, false, 90, 25 + yOffset);
        itemstacks.init(2, false, 90 + 22, 25 + yOffset);

        itemstacks.set(ingredients);  
        
        //System.out.println(ingredients.getInputs(ItemStack.class) + " -> " + ingredients.getOutputs(ItemStack.class));
        
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
