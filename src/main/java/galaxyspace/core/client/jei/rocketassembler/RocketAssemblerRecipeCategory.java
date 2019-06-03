package galaxyspace.core.client.jei.rocketassembler;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.jei.GSRecipeCategories;
import galaxyspace.core.registers.blocks.GSBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RocketAssemblerRecipeCategory implements IRecipeCategory
{
    private static final ResourceLocation rocketassemblerTex = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/rocket_assembly.png");

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final IDrawableAnimated progressBar;
    
    @Nonnull
    private final IDrawable slot, nullbar;
    
    @Nonnull
    private final IDrawable resultslot;

    public RocketAssemblerRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(rocketassemblerTex, 3, 15, 161, 141);
        this.slot = guiHelper.createDrawable(rocketassemblerTex, 204, 75, 20, 20);
        this.localizedName = GSBlocks.ROCKET_ASSEMBLER.getLocalizedName();

        this.nullbar = guiHelper.createDrawable(rocketassemblerTex, 204, 96, 52, 9);
        
        this.resultslot  = guiHelper.createDrawable(rocketassemblerTex, 215, 144, 34, 34);
        
        IDrawableStatic progressBarDrawable = guiHelper.createDrawable(rocketassemblerTex, 206, 229, 50, 9);
        this.progressBar = guiHelper.createAnimatedDrawable(progressBarDrawable, 70, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return GSRecipeCategories.ROCKET_ASSEMBLER;
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
    	this.nullbar.draw(minecraft, 106, 105);
    	GL11.glColor3f(0.0F, 1.0F, 0.0F);
        this.progressBar.draw(minecraft, 107, 105);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        
        this.slot.draw(minecraft, 35, 10);
        this.slot.draw(minecraft, 35, 37);
        
        this.slot.draw(minecraft, 35, 65);
        this.slot.draw(minecraft, 35, 95);
        
        this.slot.draw(minecraft, 11, 70);
        this.slot.draw(minecraft, 59, 70);
        
        this.slot.draw(minecraft, 11, 105);
        this.slot.draw(minecraft, 59, 105);
        
        
        this.slot.draw(minecraft, 88, 22);
        this.slot.draw(minecraft, 109, 22);
        this.slot.draw(minecraft, 130, 22);
        
        this.resultslot.draw(minecraft, 126, 61);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

        itemstacks.init(0, true, 36, 11);        
        itemstacks.init(1, true, 36, 38);
        
        itemstacks.init(2, true, 36, 66);        
        itemstacks.init(3, true, 36, 96);
        
        itemstacks.init(4, true, 12, 71);
        itemstacks.init(5, true, 60, 71);
        
        itemstacks.init(6, true, 12, 106);
        itemstacks.init(7, true, 60, 106);
       
        itemstacks.init(8, false, 134, 69);
        
        itemstacks.init(9, true, 89, 23);
        itemstacks.init(10, true, 110, 23);
        itemstacks.init(11, true, 131, 23);
        
        itemstacks.set(ingredients);
    }

    @Override
    public String getModName()
    {
        return GalaxySpace.NAME;
    }
}
