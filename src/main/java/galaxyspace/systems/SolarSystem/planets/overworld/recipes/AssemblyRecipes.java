package galaxyspace.systems.SolarSystem.planets.overworld.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import micdoodle8.mods.galacticraft.api.recipe.ShapedRecipesGC;
import micdoodle8.mods.galacticraft.api.recipe.ShapelessOreRecipeGC;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class AssemblyRecipes
{
    private static List<IRecipe> recipes = new ArrayList<IRecipe>();

    public static ShapedRecipesGC addRecipe(ItemStack output, Object... inputList)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (inputList[i] instanceof String[])
        {
            String[] astring = (String[]) inputList[i++];

            for (String s1 : astring)
            {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else
        {
            while (inputList[i] instanceof String)
            {
                String s2 = (String) inputList[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap<Character, ItemStack> hashmap;

        for (hashmap = new HashMap<Character, ItemStack>(); i < inputList.length; i += 2)
        {
            Character character = (Character) inputList[i];
            ItemStack itemstack1 = null;

            if (inputList[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item) inputList[i + 1]);
            }
            else if (inputList[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block) inputList[i + 1], 1, 32767);
            }
            else if (inputList[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack) inputList[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = hashmap.get(Character.valueOf(c0)).copy();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }

        ShapedRecipesGC shapedrecipes = new ShapedRecipesGC(j, k, aitemstack, output);
        AssemblyRecipes.recipes.add(shapedrecipes);
        return shapedrecipes;
    }
    public static void addShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
    {
        ArrayList arraylist = new ArrayList();
        int i = par2ArrayOfObj.length;

        for (int j = 0; j < i; ++j)
        {
            Object object1 = par2ArrayOfObj[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack) object1).copy());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item) object1));
            }
            else if (object1 instanceof String)
            {
                arraylist.add(object1);
            }
            else
            {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless compressor recipe!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        AssemblyRecipes.recipes.add(new ShapelessOreRecipeGC(par1ItemStack, arraylist.toArray()));
    }

    public static ItemStack findMatchingRecipe(InventoryCrafting inventory, World par2World)
    {
        int i = 0;
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;
        int j;

        for (j = 0; j < inventory.getSizeInventory(); ++j)
        {
            ItemStack itemstack2 = inventory.getStackInSlot(j);

            if (!itemstack2.isEmpty())
            {
                if (i == 0)
                {
                    itemstack = itemstack2;
                }

                if (i == 1)
                {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.getCount() == 1 && itemstack1.getCount() == 1 && itemstack.getItem().isRepairable())
        {
            int k = itemstack.getItem().getMaxDamage() - itemstack.getItemDamage();
            int l = itemstack.getItem().getMaxDamage() - itemstack1.getItemDamage();
            int i1 = k + l + itemstack.getItem().getMaxDamage() * 5 / 100;
            int j1 = itemstack.getItem().getMaxDamage() - i1;

            if (j1 < 0)
            {
                j1 = 0;
            }

            return new ItemStack(itemstack.getItem(), 1, j1);
        }
        else
        {
        	List<IRecipe> theRecipes = AssemblyRecipes.getRecipeList();
        	
        	for (j = 0; j < theRecipes.size(); ++j)
            {
                IRecipe irecipe = theRecipes.get(j);

                if (irecipe instanceof ShapedRecipesGC && irecipe.matches(inventory, par2World))
                {
                    return irecipe.getRecipeOutput().copy();
                }
                else if (irecipe instanceof ShapelessOreRecipeGC && irecipe.matches(inventory, par2World))  {
                	
                    return irecipe.getRecipeOutput().copy();
                }
            }

            return ItemStack.EMPTY;
        }
    }

    public static List<IRecipe> getRecipeListAll()
    {
        List<IRecipe> result = new ArrayList<>(AssemblyRecipes.recipes);
        List<IRecipe> endList = getRecipeListHidden(true, true);
        result.removeIf(irecipe -> endList.contains(irecipe));
        IRecipe ice = null;
        Item iceItem = new ItemStack(Blocks.ICE).getItem();
        for (IRecipe test : result)
        {
            if (test.getRecipeOutput().getItem() == iceItem)
            {
                ice = test;
                break;
            }
        }
        if (ice != null)
        {
            result.remove(ice);
            result.add(ice);
        }
        result.addAll(endList);
        return result;
    }
    
    public static List<IRecipe> getRecipeListHidden(boolean hideSteel, boolean hideAdventure)
    {
        if (!hideAdventure) return new ArrayList<IRecipe>(0);

        List<IRecipe> result = new ArrayList<>(AssemblyRecipes.recipes);
        result.removeIf(irecipe -> AssemblyRecipes.recipes.contains(irecipe));
        if (CompressorRecipes.steelIngotsPresent && hideSteel)
        {
            List<IRecipe> resultSteelless = new ArrayList<>(result.size());
            for (IRecipe recipe : result)
            {
                ItemStack output = recipe.getRecipeOutput();
                if (output == null) continue;
                if (output.getItemDamage() == 9 && output.getItem() == GCItems.basicItem && recipe instanceof ShapelessOreRecipeGC)
                {
                    if (((ShapelessOreRecipeGC)recipe).matches(CompressorRecipes.steelRecipeGC))
                    {
                        continue;
                    }
                }
                resultSteelless.add(recipe);
            }
            return resultSteelless;
        }
        return result;
    }
    
    public static List<IRecipe> getRecipeList()
    {
        return AssemblyRecipes.recipes;
    }
    
    public static List<IRecipe> getRecipes(ItemStack match)
    {
        List<IRecipe> result = new ArrayList(AssemblyRecipes.getRecipeList());
        result.removeIf(irecipe -> !ItemStack.areItemStacksEqual(match, irecipe.getRecipeOutput()));
        return result;
    }
    
    public static void removeRecipe(ItemStack match)
    {
    	AssemblyRecipes.recipes.removeIf(irecipe -> ItemStack.areItemStacksEqual(match, irecipe.getRecipeOutput()));
    }

}
