package galaxyspace.core.client.jei.assembler;

import java.util.List;

import javax.annotation.Nonnull;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.client.jei.GSRecipeCategories;
import galaxyspace.core.client.jei.GalaxySpaceJEI;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.util.GSConstants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblerRecipeCategory implements IRecipeCategory
{
    
    @Nonnull
    private final IDrawable backgroundTop, background, backgroundBottom, slot, blankArrow;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final IDrawableAnimated progressBar;
    
    

    public AssemblerRecipeCategory(IGuiHelper guiHelper)
    {
    	ResourceLocation gui = GSConfigCore.enableModernGUI ? GSConstants.GUI_MACHINE_MODERN : GSConstants.GUI_MACHINE_CLASSIC; 
        
    	this.backgroundTop = guiHelper.createDrawable(gui, 0, 0, 176, 12);
    	this.background = guiHelper.createDrawable(gui, 0, 0, 176, 92);
    	this.backgroundBottom = guiHelper.createDrawable(gui, 0, 31, 176, 30);
    	this.slot = guiHelper.createDrawable(gui, 0, 62, 18, 18);
    	this.blankArrow = guiHelper.createDrawable(gui, 181, 109, 36, 15);
    	
        this.localizedName = GSBlocks.ASSEMBLER.getLocalizedName();//GCCoreUtil.translate("tile.machine.3.name");

        //ResourceLocation resourceLocation, int u, int v, int width, int height
        IDrawableStatic progressBarDrawable = guiHelper.createDrawable(gui, 181, 125, 36, 16);//guiHelper.createDrawable(compressorTex, 180, 15, 52, 17);
        this.progressBar = guiHelper.createAnimatedDrawable(progressBarDrawable, 70, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return GSRecipeCategories.ASSEMBLER;
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
    public void drawExtras(@Nonnull Minecraft minecraft)
    {
       
        this.backgroundBottom.draw(minecraft, 0, 57);
        
        for(int i = 0; i < 3; i++)
        	for(int j = 0; j < 3; j++)
        		this.slot.draw(minecraft, i * 19 + 10, j * 19 + 20);
        
        this.slot.draw(minecraft, 139, 39);
        this.blankArrow.draw(minecraft, 85, 39);
        this.progressBar.draw(minecraft, 85, 38);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
      
        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

        for (int j = 0; j < 9; j++)
        {
            itemstacks.init(j, true, j % 3 * 19 + 10, j / 3 * 19 + 20);
        }

        itemstacks.init(9, false, 139, 39);

        if (ConfigManagerCore.quickMode)
        {
            List<ItemStack> output = ingredients.getOutputs(ItemStack.class).get(0);
            ItemStack stackOutput = output.get(0);
            if (stackOutput.getItem().getTranslationKey(stackOutput).contains("compressed"))
            {
                ItemStack stackDoubled = stackOutput.copy();
                stackDoubled.setCount(stackOutput.getCount() * 2);
                output.set(0, stackDoubled);
            }
        }

        itemstacks.set(ingredients);
    }

    @Override
    public String getModName()
    {
        return GalaxySpace.NAME;
    }
}
