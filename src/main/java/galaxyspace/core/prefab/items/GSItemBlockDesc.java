package galaxyspace.core.prefab.items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import asmodeuscore.api.item.IShiftDescription;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSItemBlockDesc extends ItemBlock
{
    public GSItemBlockDesc(Block block)
    {
        super(block);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            return;
        }

    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> info, ITooltipFlag flagIn)
    {
        if (this.getBlock() instanceof IShiftDescription && ((IShiftDescription) this.getBlock()).showDescription(stack.getItemDamage()))
        {
        	if(((IShiftDescription) this.getBlock()).getDescription(stack.getItemDamage()) != null)
        		info.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(((IShiftDescription) this.getBlock()).getDescription(stack.getItemDamage()), 150));
            
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            {
                info.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(((IShiftDescription) this.getBlock()).getShiftDescription(stack.getItemDamage()), 150));
            }
            else
            {
                if (this.getBlock() instanceof BlockTileGC)
                {
                    TileEntity te = ((BlockTileGC) this.getBlock()).createTileEntity(null, getBlock().getStateFromMeta(stack.getItemDamage() & 12));
                    if (te instanceof TileBaseElectricBlock)
                    {
                        float powerDrawn = ((TileBaseElectricBlock) te).storage.getMaxExtract();
                        if (powerDrawn > 0)
                        {
                            info.add(TextFormatting.GREEN + GCCoreUtil.translateWithFormat("item_desc.powerdraw.name", EnergyDisplayHelper.getEnergyDisplayS(powerDrawn * 20)));
                        }
                    }
                }
                else if (this.getBlock() instanceof BlockAdvancedTile)
                {
                    TileEntity te = ((BlockAdvancedTile) this.getBlock()).createTileEntity(worldIn, getBlock().getStateFromMeta(stack.getItemDamage() & 12));
                    if (te instanceof TileBaseElectricBlock)
                    {
                        float powerDrawn = ((TileBaseElectricBlock) te).storage.getMaxExtract();
                        if (powerDrawn > 0)
                        {
                            info.add(TextFormatting.GREEN + GCCoreUtil.translateWithFormat("item_desc.powerdraw.name", EnergyDisplayHelper.getEnergyDisplayS(powerDrawn * 20)));
                        }
                    }
                }
                info.add(TextFormatting.DARK_AQUA + GCCoreUtil.translateWithFormat("item_desc.shift.name", GameSettings.getKeyDisplayString(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode())));
            }
        }
    }
}

