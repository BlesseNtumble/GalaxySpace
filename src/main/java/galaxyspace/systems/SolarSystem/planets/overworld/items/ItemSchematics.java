package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSchematics extends Item implements ISchematicItem{

	 public static String[] names = 
	    	{ 
		 	"Cone",
		 	"MainPart",
		 	"Engine",
		 	"Booster",
		 	"Stabilizer",
		 	"OxTanks",
		 	"PortableNuclearReactor"
		 	
	    	};
	 protected IIcon[] icons = new IIcon[this.names.length];
	    
	 public ItemSchematics()
	 {
		super();
		this.setUnlocalizedName("ItemSchematics");
	    this.setMaxStackSize(1);
	    this.setHasSubtypes(true);
	    this.setMaxDamage(0);
    
	 }

	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b)
	 {
		 int meta = par1ItemStack.getItemDamage();
		
		 if(meta == 0) par2List.add(GCCoreUtil.translate("item.RocketCone.name"));
		 if(meta == 1) par2List.add(GCCoreUtil.translate("item.RocketBody.name"));
		 if(meta == 2) par2List.add(GCCoreUtil.translate("item.RocketEngine.name"));
		 if(meta == 3) par2List.add(GCCoreUtil.translate("item.RocketBooster.name"));
		 if(meta == 4) par2List.add(GCCoreUtil.translate("item.RocketStabilizer.name"));
		 if(meta == 5) par2List.add(GCCoreUtil.translate("gui.schematic_oxtanks.desc"));
		 if(meta == 6) par2List.add(GCCoreUtil.translate(GSBlocks.PortableNuclearReactor.getUnlocalizedName() + ".name"));	
		 
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
			 // this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "schematic_rocketT" + (3+i));
			 this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "schematics/schematic_" + i);        	
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
		 for (int i = 0; i < this.names.length; i++)
		 {
			 par3List.add(new ItemStack(par1, 1, i));
		 }
	 }

	 @Override
	 public String getUnlocalizedName(ItemStack par1ItemStack)
	 {
		 return "item.Schematic";
	 }
    
	 @Override
	 public int getMetadata(int par1)
	 {
		 return par1;
	 }
}
