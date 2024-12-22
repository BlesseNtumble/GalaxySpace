package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Energy;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGasExtractor extends ItemElectricBase implements ISortableItem, IModificationItem {

    private static final int DISCHARGE_COUNT = 100;
    public ItemGasExtractor()
    {
        super();
        this.setTranslationKey("gas_extractor");
        this.setHasSubtypes(true);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        if(!stack.getTagCompound().hasKey(ItemSpaceSuit.mod_count))
        {
            stack.getTagCompound().setInteger(ItemSpaceSuit.mod_count, getModificationCount(stack));
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if (tab == GSCreativeTabs.GSArmorTab || tab == CreativeTabs.SEARCH)
            list.add(new ItemStack(this, 1, this.getMaxDamage()));

    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if(!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        tooltip.add(GCCoreUtil.translate("gui.gas_extractor.desc"));
        tooltip.add("");
        tooltip.add(EnumColor.GREY + GCCoreUtil.translate("gui.module.available_modules") + " " + itemStack.getTagCompound().getInteger(ItemSpaceSuit.mod_count));
        tooltip.add("");
        tooltip.add(EnumColor.AQUA + GCCoreUtil.translate("gui.module.list"));
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {

            for(ItemModule s : this.getAvailableModules()) {
                if(itemStack.getTagCompound().hasKey(s.getName()))
                    tooltip.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(this.check(itemStack, s.getName()) + GCCoreUtil.translate("gui.module." + s.getName()), 150));
            }

        }
        else
        {
            tooltip.add(EnumColor.DARK_AQUA + GCCoreUtil.translateWithFormat("item_desc.shift.name", GameSettings.getKeyDisplayString(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode())));
        }
        tooltip.add("");
        super.addInformation(itemStack, worldIn, tooltip, flagIn);
    }

    private String check(ItemStack stack, String nbt)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().getBoolean(nbt)) return TextFormatting.DARK_GREEN + "- ";
        return TextFormatting.DARK_RED + "- ";
    }

    @Override
    public float getMaxElectricityStored(ItemStack stack) {
        boolean energy = stack.hasTagCompound() && stack.getTagCompound().hasKey("energy");
        return energy ? 18000.0F : 6000.0F;
    }

    @Override
    public EnumSortCategoryItem getCategory(int meta) {
        return EnumSortCategoryItem.TOOLS;
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(world.isRemote)
            return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));

        if(this.getElectricityStored(stack) < DISCHARGE_COUNT)
            return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));

        RayTraceResult result = ItemBasicGS.getRay(world, player, true);

        if(result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            if(world.getBlockState(result.getBlockPos()).getBlock() == GSFluids.BLOCK_NATURE_GAS) {
                if(tryFillCanister(player, new FluidStack(GSFluids.NatureGas, 200))) {
                    player.inventoryContainer.detectAndSendChanges();
                    world.setBlockToAir(result.getBlockPos());

                    if(!player.capabilities.isCreativeMode)
                        this.discharge(stack, DISCHARGE_COUNT, true);

                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
                }
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));

    }

    private boolean tryFillCanister(EntityPlayer player, FluidStack fluid) {

        for(int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack stack = player.inventory.mainInventory.get(i);

            if(stack.getItem() instanceof ItemCanisterGeneric) {
                if(stack.getItem() == GSItems.NATURE_GAS_CANISTER && stack.getItemDamage() > 1) {

//                    if(stack.getItemDamage() + fluid.amount < stack.getMaxDamage())
//                        stack.setItemDamage(stack.getItemDamage() - fluid.amount);
//                    else
//                        stack.setItemDamage(1);

                    if(stack.getItemDamage() - fluid.amount > 1)
                        stack.setItemDamage(stack.getItemDamage() - fluid.amount);
                    else
                        stack.setItemDamage(1);

                    player.inventory.setInventorySlotContents(i, stack);
                    return true;
                }

                if(stack.getItem() == GCItems.oilCanister && stack.getItemDamage() == ItemCanisterGeneric.EMPTY) {

                    stack = new ItemStack(GSItems.NATURE_GAS_CANISTER, 1, stack.getMaxDamage() - fluid.amount);
                    player.inventory.setInventorySlotContents(i, stack);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public GSUtils.Module_Type getType(ItemStack stack) {
        return GSUtils.Module_Type.TOOLS;
    }

    @Override
    public ItemModule[] getAvailableModules() {
        return new ItemModule[] { new Energy() };
    }

    @Override
    public int getModificationCount(ItemStack stack) {
        return 1;
    }
}
