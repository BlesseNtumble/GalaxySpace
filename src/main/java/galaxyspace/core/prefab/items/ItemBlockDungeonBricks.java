package galaxyspace.core.prefab.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDungeonBricks extends ItemBlock 
{
    public ItemBlockDungeonBricks(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
       /* if (metadata >= 0 && metadata < BlockDungeonBricks.metadata.length) {
            return "tile." + BlockDungeonBricks.metadata[metadata];
        }*/
        return super.getUnlocalizedName();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List par2List, boolean b)
	{
		int n = is.getItemDamage();
		switch(n)
		{
			case 0:
				par2List.add(GCCoreUtil.translate("planet.ceres"));
				break;
			case 1:
				par2List.add(GCCoreUtil.translate("moon.io"));
				break;
		}	
	  
	}
}
