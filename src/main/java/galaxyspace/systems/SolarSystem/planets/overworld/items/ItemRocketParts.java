package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemRocketParts extends Item
{
    public static String[] names = { 
    	/*"RocketConeT2", 
    	"RocketBodyT2",
    	"RocketEngineT2", 
    	"RocketBoosterT2", 
    	"RocketStabilisatorT2",
    	*/
    	"RocketConeT3", 
    	"RocketBodyT3",
    	"RocketEngineT3", 
    	"RocketBoosterT3", 
    	"RocketStabilisatorT3",
    	
    	"RocketConeT4", 
    	"RocketBodyT4",
    	"RocketEngineT4", 
    	"RocketBoosterT4", 
    	"RocketStabilisatorT4",
    	
    	"RocketConeT5", 
    	"RocketBodyT5",
    	"RocketEngineT5", 
    	"RocketBoosterT5", 
    	"RocketStabilisatorT5",
    	
    	"RocketConeT6", 
    	"RocketBodyT6",
    	"RocketEngineT6", 
    	"RocketBoosterT6", 
    	"RocketStabilisatorT6"
    	
    };
    
    protected IIcon[] icons = new IIcon[this.names.length];

    public ItemRocketParts()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("RocketParts");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        int i = 0;

        for (String name : this.names)
        {
            this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":rocketparts/" + name);
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        if (this.icons.length > damage)
        {
            return this.icons[damage];
        }

        return super.getIconFromDamage(damage);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < ItemRocketParts.names.length; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {/*
        if (this.icons.length > par1ItemStack.getItemDamage())
        {
            return "item." + this.names[par1ItemStack.getItemDamage()];
        }
*/
    	for(int k = 0; k <= 4; k++)
        {
    		if(par1ItemStack.getItemDamage() == k*5) return "item.RocketCone";
    		if(par1ItemStack.getItemDamage() == k*5 + 1) return "item.RocketBody";
    		if(par1ItemStack.getItemDamage() == k*5 + 2) return "item.RocketEngine";
    		if(par1ItemStack.getItemDamage() == k*5 + 3) return "item.RocketBooster";
    		if(par1ItemStack.getItemDamage() == k*5 + 4) return "item.RocketStabilizer";
        }
        return "unnamed";
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b)
    {
        for(int i = 0; i <= 4; i++)
        {
        	if(par1ItemStack.getItemDamage() == i) par2List.add(GCCoreUtil.translate("gui.tier3.desc"));
        	else if(par1ItemStack.getItemDamage() == i+5) par2List.add(GCCoreUtil.translate("gui.tier4.desc"));
        	else if(par1ItemStack.getItemDamage() == i+10) par2List.add(GCCoreUtil.translate("gui.tier5.desc"));
        	else if(par1ItemStack.getItemDamage() == i+15) par2List.add(GCCoreUtil.translate("gui.tier6.desc"));
        }
    }
}
