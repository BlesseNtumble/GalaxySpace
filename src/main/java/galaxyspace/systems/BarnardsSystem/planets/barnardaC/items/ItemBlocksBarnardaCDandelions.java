package galaxyspace.systems.BarnardsSystem.planets.barnardaC.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Dandelions;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlocksBarnardaCDandelions extends ItemMultiTexture 
{
	@SideOnly(Side.CLIENT)
    private IIcon field_150938_b;
	
    public ItemBlocksBarnardaCDandelions(Block block) {
        super(block, block, Barnarda_C_Dandelions.metadata);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    public String getUnlocalizedName(ItemStack is) {
    	
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < Barnarda_C_Dandelions.metadata.length) {
            return super.getUnlocalizedName() + "." + Barnarda_C_Dandelions.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
        String s = this.field_150939_a.getItemIconName();

        if (s != null)
        {
            this.field_150938_b = p_94581_1_.registerIcon(s);
        }
    }
}
