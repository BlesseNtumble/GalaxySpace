package galaxyspace.core.client.jei.assembler;

import java.util.List;

import javax.annotation.Nonnull;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.client.jei.GSRecipeCategories;
import galaxyspace.core.client.jei.GalaxySpaceJEI;
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
    private static final ResourceLocation compressorTex = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/assembly_machine.png");
    private static final ResourceLocation compressorTexBlank = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/ingot_compressor_blank.png");

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final IDrawable backgroundBlank;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final IDrawableAnimated progressBar;
    
    private boolean drawNothing = false; 

    public AssemblerRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(compressorTex, 18, 17, 137, 66);
        this.backgroundBlank = guiHelper.createDrawable(compressorTexBlank, 18, 17, 137, 78);
        this.localizedName = GSBlocks.ASSEMBLER.getLocalizedName();//GCCoreUtil.translate("tile.machine.3.name");

        //ResourceLocation resourceLocation, int u, int v, int width, int height
        IDrawableStatic progressBarDrawable = guiHelper.createDrawable(compressorTex, 176, 13, 52, 17);//guiHelper.createDrawable(compressorTex, 180, 15, 52, 17);
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
        if (this.drawNothing)
        {
            return this.backgroundBlank;
        }
        return this.background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft)
    {
        if (!this.drawNothing) this.progressBar.draw(minecraft, 59, 21);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        this.drawNothing = GalaxySpaceJEI.hidden.contains(recipeWrapper);
        if (this.drawNothing) return;

        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

        for (int j = 0; j < 9; j++)
        {
            itemstacks.init(j, true, j % 3 * 18, j / 3 * 18);
        }

        itemstacks.init(9, false, 119, 18);

        if (ConfigManagerCore.quickMode)
        {
            List<ItemStack> output = ingredients.getOutputs(ItemStack.class).get(0);
            ItemStack stackOutput = output.get(0);
            if (stackOutput.getItem().getUnlocalizedName(stackOutput).contains("compressed"))
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
