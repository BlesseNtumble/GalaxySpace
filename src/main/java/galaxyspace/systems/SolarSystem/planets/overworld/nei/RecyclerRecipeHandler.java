package galaxyspace.systems.SolarSystem.planets.overworld.nei;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes.RecycleRecipe;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class RecyclerRecipeHandler extends TemplateRecipeHandler
{
    private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    public static int ticksPassed;
    private int alternate = 0;
    static boolean hasLiquid = false;
    
    private FluidStack fluidresult = null;
    //int fluidcount = 0;
    
    private boolean hasRand = false;
    private int chanceRand = 0;
    
    int yoffset = -11;

    public String getRecipeId()
    {
        return "galaxyspace.recycler";
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void drawBackground(int i)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(RecyclerRecipeHandler.guiTexture);
        GuiDraw.drawTexturedModalRect(0, 0 + yoffset, 0, 0, 189, 88);
        
        GuiDraw.drawTexturedModalRect(15, 36 + yoffset, 192, 26, 20, 20);
        GuiDraw.drawTexturedModalRect(125, 45 + yoffset, 192, 26, 20, 20);
        GuiDraw.drawTexturedModalRect(148, 19 + yoffset, 192, 26, 20, 20);
        
        
        GuiDraw.drawTexturedModalRect(148, 41 + yoffset, 192, 66, 20, 42);
        
        
        if(this.fluidresult != null) { 
        	if (RecyclerRecipeHandler.ticksPassed % 70 >= 53)
        		GSUtils.drawFluid(new FluidStack(this.fluidresult, 1), 150, 43 + yoffset, 16, 38, 1);
	        String name = new FluidStack(this.fluidresult, 1).getLocalizedName() + ": " + this.fluidresult.amount + " mB";        
	        GuiDraw.fontRenderer.drawString(name, 120 - (name.length() * 3), 73 + yoffset, ColorUtil.to32BitColor(255, 255, 255, 0));
        }
        
        if(this.hasRand)
        {        	
        	String name = "Chance: " + this.chanceRand + "%"; 
        	GuiDraw.fontRenderer.drawString(name, 120 - (name.length() * 3), 25 + yoffset, ColorUtil.to32BitColor(255, 255, 255, 0));

        }
        
        GL11.glColor3f(1, 1, 1);
        GuiDraw.changeTexture(RecyclerRecipeHandler.guiTexture);
        GuiDraw.drawTexturedModalRect(148, 41 + yoffset, 192+20, 66, 20, 42);
        
        GuiDraw.drawTexturedModalRect(75, 36 + yoffset, 230, 60, 24, 26);
        GuiDraw.drawTexturedModalRect(104, 44 + yoffset, 192+20, 108, 16, 17);
        
		GuiDraw.drawTexturedModalRect(40, 43 + yoffset, 203, 131, 24, 4);
		GuiDraw.drawTexturedModalRect(64, 43 + yoffset, 203, 131, 11, 4);
		GuiDraw.drawTexturedModalRect(72, 66 + yoffset, 203, 131, 24, 4);

		GuiDraw.drawTexturedModalRect(72, 46 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(72, 49 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(72, 51 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(72, 54 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(72, 57 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(72, 60 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(72, 63 + yoffset, 203, 131, 3, 3);

		GuiDraw.drawTexturedModalRect(101, 50 + yoffset, 203, 114, 3, 4);

		GuiDraw.drawTexturedModalRect(96, 66 + yoffset, 203, 131, 3, 4);
		GuiDraw.drawTexturedModalRect(96, 63 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(96, 60 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(96, 57 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(96, 54 + yoffset, 203, 131, 3, 3);
		GuiDraw.drawTexturedModalRect(96, 51 + yoffset, 203, 131, 3, 3);

		GuiDraw.drawTexturedModalRect(99, 51 + yoffset, 203, 131, 6, 4);
          
        	
        
        if (RecyclerRecipeHandler.ticksPassed % 70 >= 53)
        {
        	GuiDraw.drawTexturedModalRect(104, 44 + yoffset, 192+20, 124, 16, 17); 
        	GuiDraw.drawTexturedModalRect(75, 39 + yoffset, 230, 86, 24, 24);
        }

/*
        this.drawTex(114, 60);

        */
        //GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
        //GuiDraw.drawTexturedModalRect(59, 33, 206, 231, Math.min(RecyclerRecipeHandler.ticksPassed % 70, 53), 6);
    }

    @Override
    public void onUpdate()
    {
        RecyclerRecipeHandler.ticksPassed += 1 + this.alternate;
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
        	List<RecycleRecipe> recipes = RecyclerRecipes.recycling().getRecipes();
        	
        	for (RecycleRecipe recipe : recipes)
            {
                this.arecipes.add(new RecyclerRecipe(recipe.getInput(), recipe.getOutput()));
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
    	List<RecycleRecipe> recipes = RecyclerRecipes.recycling().getRecipes();
    	
    	for (RecycleRecipe recipe : recipes)
    	{
    		if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getOutput(), result))
    		{    			
                this.arecipes.add(new RecyclerRecipe(recipe.getInput(), recipe.getOutput()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
    	List<RecycleRecipe> recipes = RecyclerRecipes.recycling().getRecipes();
    	
    	for (RecycleRecipe recipe : recipes)
    	{
    		/*if(recipe.getValue() != null)
    		{*/
    			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getInput(), ingredient))
	            {
	    			RecyclerRecipe arecipe = new RecyclerRecipe(recipe.getInput(), recipe.getOutput());
	    			arecipe.setIngredientPermutation(Arrays.asList(arecipe.input), ingredient);
	    			this.arecipes.add(arecipe);
	                break;                
	            }
    		//}
        }
    }
/*
    @Override
    public ArrayList<PositionedStack> getIngredientStacks(int recipe)
    {
        return (ArrayList<PositionedStack>) this.arecipes.get(recipe).getIngredients();
    }
*/
    
    @Override
    public PositionedStack getResultStack(int recipe)
    {
    	PositionedStack input = this.arecipes.get(recipe).getIngredient();
        
        this.hasLiquid = false;

		List<RecycleRecipe> recyclerecipes = RecyclerRecipes.recycling().getRecipes(); 
		
		RecycleRecipe rec_recipe = null;
		for(RecycleRecipe recipes : recyclerecipes)
		{
			if(recipes.getInput().isItemEqual(input.item))
			{
				rec_recipe = recipes;
				break;
			}
		}
		
		if(rec_recipe != null)
		{

	       /* this.fluidcount = 0;
	        if(rec_recipe.getFluidStack() != null) 
	        	this.fluidcount = rec_recipe.getFluidStack().amount;*/
	        
	        if (RecyclerRecipeHandler.ticksPassed % 70 >= 53)
	        {
	        	if(rec_recipe.hasChance())
	        	{
	        		this.hasRand = rec_recipe.hasChance();
	        		this.chanceRand = rec_recipe.getChance();
	        	}
	        	else
	        	{
	        		this.hasRand = false;
	        	}
	        	
	        	if(rec_recipe.getFluidStack() != null) 
	        	{
	        		this.fluidresult = rec_recipe.getFluidStack();
	        		
	        		this.hasLiquid = true;
	        		
	        	}
	        	else
	        	{
	        		this.hasLiquid = false;
	        	}
	        	
	        	
	            return this.arecipes.get(recipe).getResult();
	        }
		}
        
        return null;
    }

    public class RecyclerRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        public PositionedStack input;
        public PositionedStack output;

        @Override
        public PositionedStack getIngredient()
        {
        	return this.input;
        }

        @Override
        public PositionedStack getResult()
        {
            return this.output;
        }

        public RecyclerRecipe(ItemStack ingred, ItemStack result)
        {
            super();

            this.input = new PositionedStack(ingred, 17, 38 + yoffset);
            if(result != null) this.output = new PositionedStack(result, 127, 47 + yoffset);
        }

/*
        @Override
        public PositionedStack getOtherStack()
        {
            if (RecyclerRecipeHandler.ticksPassed % 70 >= 53 && RecyclerRecipeHandler.hasLiquid)
            {
                PositionedStack outputCopy = new PositionedStack(new ItemStack(GSItems.Helium3Canister, 1, 1), 150, 21 + yoffset);
                //outputCopy.rely += 18;
                return outputCopy;
            }

            return null;
        }
        */
        
    }

    @Override
    public String getRecipeName()
    {
        return EnumColor.WHITE + GCCoreUtil.translate(GSBlocks.Recycler.getUnlocalizedName() + ".name");
    }

    @Override
    public String getGuiTexture()
    {
        return GalaxySpace.ASSET_PREFIX + "textures/gui/rocket_assembly.png";
    }

    @Override
    public void drawForeground(int recipe)
    {
    }
}
