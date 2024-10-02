package galaxyspace.systems.SolarSystem.planets.overworld.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.nei.NEIGalaxySpaceConfig;
import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblyMachineRecipeHandler extends TemplateRecipeHandler
{
    private static final ResourceLocation assemblymachineTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/assembly_machine.png");
    public static int ticksPassed;
    private int alternate = 0;

    public String getRecipeId()
    {
        return "galaxyspace.assemblymachine";
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    public Set<Entry<ArrayList<PositionedStack>, PositionedStack>> getRecipes()
    {
        HashMap<ArrayList<PositionedStack>, PositionedStack> recipes = new HashMap<ArrayList<PositionedStack>, PositionedStack>();

        for (Entry<HashMap<Integer, PositionedStack>, PositionedStack> stack : NEIGalaxySpaceConfig.getAssemblyMachineRecipes())
        {
            ArrayList<PositionedStack> inputStacks = new ArrayList<PositionedStack>();

            for (Map.Entry<Integer, PositionedStack> input : stack.getKey().entrySet())
            {
                inputStacks.add(input.getValue());
            }

            recipes.put(inputStacks, stack.getValue());
        }

        return recipes.entrySet();
    }

    @Override
    public void drawBackground(int i)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(AssemblyMachineRecipeHandler.assemblymachineTexture);
        GuiDraw.drawTexturedModalRect(20, 25, 18, 17, 137, 54);

        if (AssemblyMachineRecipeHandler.ticksPassed % 70 > 26)
        {
            GuiDraw.drawTexturedModalRect(93, 39, 176, 0, 17, 13);
        }

        GuiDraw.drawTexturedModalRect(79, 46, 176, 13, Math.min(AssemblyMachineRecipeHandler.ticksPassed % 70, 53), 17);
    }

    @Override
    public void onUpdate()
    {
        AssemblyMachineRecipeHandler.ticksPassed += 1 + this.alternate;
        this.alternate = 1 - this.alternate;
        super.onUpdate();
    }

    @Override
    public void loadTransferRects()
    {
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(this.getRecipeId()))
        {
            for (final Map.Entry<ArrayList<PositionedStack>, PositionedStack> irecipe : this.getRecipes())
            {
                this.arecipes.add(new AssemblyMachineRecipe(irecipe));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (final Map.Entry<ArrayList<PositionedStack>, PositionedStack> irecipe : this.getRecipes())
        {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getValue().item, result))
            {
                this.arecipes.add(new AssemblyMachineRecipe(irecipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        for (final Map.Entry<ArrayList<PositionedStack>, PositionedStack> irecipe : this.getRecipes())
        {
            for (final PositionedStack pstack : irecipe.getKey())
            {
                if (pstack.contains(ingredient))
                {
                    this.arecipes.add(new AssemblyMachineRecipe(irecipe));
                    break;
                }
            }
        }
    }

    @Override
    public ArrayList<PositionedStack> getIngredientStacks(int recipe)
    {
        return (ArrayList<PositionedStack>) this.arecipes.get(recipe).getIngredients();
    }

    @Override
    public PositionedStack getResultStack(int recipe)
    {
        if (AssemblyMachineRecipeHandler.ticksPassed % 70 >= 53)
        {
            return this.arecipes.get(recipe).getResult();
        }

        return null;
    }

    public class AssemblyMachineRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        public ArrayList<PositionedStack> input;
        public PositionedStack output;

        @Override
        public ArrayList<PositionedStack> getIngredients()
        {
        	return (ArrayList<PositionedStack>) getCycledIngredients(cycleticks / 20, this.input);
        }

        @Override
        public PositionedStack getResult()
        {
            return this.output;
        }

        public AssemblyMachineRecipe(ArrayList<PositionedStack> pstack1, PositionedStack pstack2)
        {
            super();

            this.input = pstack1;
            this.output = pstack2;
        }

        public AssemblyMachineRecipe(Map.Entry<ArrayList<PositionedStack>, PositionedStack> recipe)
        {
            this(new ArrayList<PositionedStack>(recipe.getKey()), recipe.getValue().copy());
        }
/*
        @Override
        public PositionedStack getOtherStack()
        {
            if (AssemblyMachineRecipeHandler.ticksPassed % 70 >= 53)
            {
                PositionedStack outputCopy = this.output.copy();
                outputCopy.rely += 18;
                return outputCopy;
            }

            return null;
        }
        
        */
    }

    @Override
    public String getRecipeName()
    {
        return GSBlocks.AssemblyMachine.getLocalizedName();
    }

    @Override
    public String getGuiTexture()
    {
        return GalaxySpace.ASSET_PREFIX + "textures/gui/assembly_machine.png";
    }

    @Override
    public void drawForeground(int recipe)
    {
    }
}
