package galaxyspace.systems.SolarSystem.planets.overworld.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import micdoodle8.mods.galacticraft.api.recipe.ShapedRecipesGC;
import micdoodle8.mods.galacticraft.api.recipe.ShapelessOreRecipeGC;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RocketAssemblyRecipes
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
            ItemStack itemstack1 = ItemStack.EMPTY;

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
                aitemstack[i1] = ItemStack.EMPTY;
            }
        }

        ShapedRecipesGC shapedrecipes = new ShapedRecipesGC(j, k, aitemstack, output);
        RocketAssemblyRecipes.recipes.add(shapedrecipes);
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

        RocketAssemblyRecipes.recipes.add(new ShapelessOreRecipeGC(par1ItemStack, arraylist.toArray()));
    }

    public static ItemStack findMatchingRecipe(IInventory inventory, World par2World)
    {
        int i = 0;
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;
        int j;

        for (j = 0; j < inventory.getSizeInventory(); ++j)
        {
            ItemStack itemstack2 = inventory.getStackInSlot(j);

            if (itemstack2 != ItemStack.EMPTY)
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
            for (j = 0; j < RocketAssemblyRecipes.recipes.size(); ++j)
            {
                IRecipe irecipe = RocketAssemblyRecipes.recipes.get(j);

                if (irecipe instanceof ShapedRecipesGC && RocketAssemblyRecipes.matches((ShapedRecipesGC) irecipe, inventory, par2World))
                {
                    return irecipe.getRecipeOutput().copy();
                }
                else if (irecipe instanceof ShapelessOreRecipeGC && RocketAssemblyRecipes.matchesShapeless((ShapelessOreRecipeGC) irecipe, inventory, par2World))
                {
                    return irecipe.getRecipeOutput().copy();
                }
            }

            return ItemStack.EMPTY;
        }
    }

    private static boolean matches(ShapedRecipesGC recipe, IInventory inventory, World par2World)
    {
        for (int i = 0; i <= 3 - recipe.recipeWidth; ++i)
        {
            for (int j = 0; j <= 3 - recipe.recipeHeight; ++j)
            {
                if (RocketAssemblyRecipes.checkMatch(recipe, inventory, i, j, true))
                {
                    return true;
                }

                if (RocketAssemblyRecipes.checkMatch(recipe, inventory, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean checkMatch(ShapedRecipesGC recipe, IInventory inventory, int par2, int par3, boolean par4)
    {
        for (int k = 0; k < 3; ++k)
        {
            for (int l = 0; l < 3; ++l)
            {
                int i1 = k - par2;
                int j1 = l - par3;
                ItemStack itemstack = ItemStack.EMPTY;

                if (i1 >= 0 && j1 >= 0 && i1 < recipe.recipeWidth && j1 < recipe.recipeHeight)
                {
                    if (par4)
                    {
                        itemstack = recipe.recipeItems[recipe.recipeWidth - i1 - 1 + j1 * recipe.recipeWidth];
                    }
                    else
                    {
                        itemstack = recipe.recipeItems[i1 + j1 * recipe.recipeWidth];
                    }
                }

                ItemStack itemstack1 = ItemStack.EMPTY;

                if (k >= 0 && l < 3)
                {
                    int k2 = k + l * 3;
                    itemstack1 = inventory.getStackInSlot(k2);
                }

                if (itemstack1 != ItemStack.EMPTY || itemstack != ItemStack.EMPTY)
                {
                    if (itemstack1 == ItemStack.EMPTY && itemstack != ItemStack.EMPTY || itemstack1 != ItemStack.EMPTY && itemstack == ItemStack.EMPTY)
                    {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem())
                    {
                        return false;
                    }

                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack1.getItemDamage())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean matchesShapeless(ShapelessOreRecipeGC recipe, IInventory var1, World par2World)
    {
        ArrayList<Object> required = new ArrayList<Object>(recipe.getInput());

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != ItemStack.EMPTY)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        match = OreDictionary.itemMatches((ItemStack)next, slot, false);
                    }
                    else if (next instanceof ArrayList)
                    {
                        Iterator<ItemStack> itr = ((ArrayList<ItemStack>)next).iterator();
                        while (itr.hasNext() && !match)
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    public static List<IRecipe> getRecipeList()
    {
        return RocketAssemblyRecipes.recipes;
    }
    
    public static void removeRecipe(ItemStack match)
    {
    	RocketAssemblyRecipes.recipes.removeIf(irecipe -> ItemStack.areItemStacksEqual(match, irecipe.getRecipeOutput()));
    }

}
