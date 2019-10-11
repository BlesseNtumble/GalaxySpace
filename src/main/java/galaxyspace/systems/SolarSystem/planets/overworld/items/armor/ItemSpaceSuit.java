package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import asmodeuscore.api.item.IItemPressurized;
import asmodeuscore.api.item.IItemRadiation;
import galaxyspace.api.item.IJetpackArmor;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.client.models.ModelOBJArmor;
import galaxyspace.core.prefab.items.ItemElectricArmor;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Energy;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Gravity;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Jetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Jump;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Nightvision;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.SensorLens;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Speed;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Stepassist;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemSpaceSuitModel;
import micdoodle8.mods.galacticraft.api.item.IArmorCorrosionResistant;
import micdoodle8.mods.galacticraft.api.item.IArmorGravity;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.api.item.ISensorGlassesArmor;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.OverlaySensorGlasses;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpaceSuit extends ItemElectricArmor implements IArmorCorrosionResistant, ISensorGlassesArmor, IModificationItem, IJetpackArmor, ISpecialArmor, IArmorGravity, IItemPressurized, IItemRadiation{

	private int tier = 0;
	public static String mod_count = "modification_count";
	public static String[] suit_buttons = new String[] {"helmet_button", "chest_button", "legs_button", "boots_button"};
	public static boolean[] pressedKey = new boolean[3];
	
	private boolean[] key_handler = new boolean[4];
	private float jumpCharge;
	
	public ItemSpaceSuit(ArmorMaterial materialIn, EntityEquipmentSlot armorIndex, int tier) {
		super(materialIn, armorIndex);
		this.setUnlocalizedName("space_suit_" + armorIndex.toString().toLowerCase());
		this.setMaxDamage(200);
		this.setNoRepair();
		this.tier = tier;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);	
		
		if(!stack.hasTagCompound()) 
			stack.setTagCompound(new NBTTagCompound());
		
		if(!stack.getTagCompound().hasKey(mod_count))
		{
			stack.getTagCompound().setInteger(mod_count, getModificationCount(stack));
		}
		
		switch(getArmorType(stack)) {
			case HEAD:
				if(!stack.getTagCompound().hasKey(suit_buttons[0]))
					stack.getTagCompound().setBoolean(suit_buttons[0], false);
				break;
			case CHEST:
				if(!stack.getTagCompound().hasKey(suit_buttons[1]))
					stack.getTagCompound().setBoolean(suit_buttons[1], false);
				break;
			case LEGS:
				if(!stack.getTagCompound().hasKey(suit_buttons[2]))
					stack.getTagCompound().setBoolean(suit_buttons[2], false);
				break;
			case FEET:
				if(!stack.getTagCompound().hasKey(suit_buttons[3]))
					stack.getTagCompound().setBoolean(suit_buttons[3], false);
				break;
			default: break;
		}
	}
	 
	public int getTier()
	{
		return this.tier;
	}
	
	private void pressed()
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		if(mc.gameSettings.keyBindJump.isKeyDown()) this.pressedKey[0] = true;
		else this.pressedKey[0] = false;
		
		if(mc.gameSettings.keyBindForward.isKeyDown()) this.pressedKey[1] = true;
		else this.pressedKey[1] = false;
		
		if(mc.gameSettings.keyBindSneak.isKeyDown()) this.pressedKey[2] = true;
		else this.pressedKey[2] = false;

	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, net.minecraft.client.gui.ScaledResolution resolution, float partialTicks) {
		if (getArmorType(stack) == EntityEquipmentSlot.HEAD && stack.getTagCompound().getBoolean("sensor") && getElectricityStored(stack) > 2) {
			
			if(stack.getTagCompound().getBoolean(suit_buttons[0])) {
				OverlaySensorGlasses.renderSensorGlassesMain(stack, player, resolution, partialTicks);
				OverlaySensorGlasses.renderSensorGlassesValueableBlocks(stack, player, resolution, partialTicks);
			}
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) 
	{	
		if(!world.isRemote && !player.capabilities.isCreativeMode)
		{
			if(player.world.provider instanceof IGalacticraftWorldProvider)
			{
				if(((IGalacticraftWorldProvider)player.world.provider).shouldCorrodeArmor())
				{
					final GCPlayerStats stats = GCPlayerStats.get(player);
					if(stats.getShieldControllerInSlot().isEmpty())
						this.discharge(itemStack, 50, true);
				}
			}
		}
		
		if(world.isRemote) {pressed();}
		
		if (itemStack.hasTagCompound()) {
			for(ItemModule modules : this.getAvailableModules())
			{
				if(getArmorType(itemStack) == modules.getEquipmentSlot())
				{
					if(itemStack.getTagCompound().getBoolean(modules.getName()) && getElectricityStored(itemStack) >= modules.getDischargeCount())
					{
						modules.onUpdate(world, player, itemStack, itemStack.getTagCompound().getBoolean(suit_buttons[3 - getArmorType(itemStack).getIndex()]));
						if(itemStack.getTagCompound().getBoolean(suit_buttons[3 - getArmorType(itemStack).getIndex()]))
							if(player.ticksExisted % 20 == 0) 
								this.discharge(itemStack, modules.getDischargeCount(), true);
					}
				}
			}
			
			if(getArmorType(itemStack) == EntityEquipmentSlot.FEET)
			{
				if(itemStack.getTagCompound().getBoolean(suit_buttons[3])) {
					if (this.pressedKey[0]) {
						if (itemStack.getTagCompound().getBoolean("jump") && getElectricityStored(itemStack) >= 5) {
							if(player.onGround) this.jumpCharge = 1.0f;
							
							if (player.motionY >= 0.0 && this.jumpCharge > 0.0f && !player.isInWater()) {
								/*if (this.jumpCharge == 1.0f) {
	                                player.motionX *= 3.5;
	                                player.motionZ *= 3.5;
	                            }*/
																
								player.fallDistance = 0.3F;
								this.discharge(itemStack, 5, true);
								
								player.motionY += (double)(this.jumpCharge * 0.25f);
		                        this.jumpCharge = (float)((double)this.jumpCharge * 0.75);	                        
		                         
							}
						}
						else if (this.jumpCharge < 1.0f) {
							this.jumpCharge = 0.0f;
						}
					}
				}
			}
			/*
			switch(getArmorType(itemStack))
			{
				case HEAD:
					if(itemStack.getTagCompound().getBoolean(suit_buttons[0])) {
						if (itemStack.getTagCompound().getBoolean(SpaceSuit_Modules.NIGHTVISION.getName()) && getElectricityStored(itemStack) >= 2) {
							if(world.rand.nextInt(12) == 0) this.discharge(itemStack, 2, true);
							player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20*15, 0, true, false));
						}
						if (itemStack.getTagCompound().getBoolean(SpaceSuit_Modules.SENSOR.getName()) && getElectricityStored(itemStack) >= 2) {
							if(world.rand.nextInt(12) == 0) this.discharge(itemStack, 2, true);
						}
					}
					else
					{
						if(player.isPotionActive(MobEffects.NIGHT_VISION)) 
							player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
					}
					break;
				case LEGS:
					if(itemStack.getTagCompound().getBoolean(suit_buttons[2])) {
						if (this.pressedKey[1]) {						
							if (itemStack.getTagCompound().getBoolean(SpaceSuit_Modules.SPEED.getName()) && getElectricityStored(itemStack) >= 5) {
								this.discharge(itemStack, 5, true);
								float speed = 0.12f;
								if (player.onGround && !player.isInWater())								
									player.moveRelative(0.0f, 0.0f, 1.0f, speed);
							}
						}
					}
					break;
				case FEET:
					if(itemStack.getTagCompound().getBoolean(suit_buttons[3])) {
						if (this.pressedKey[0]) {
							if (itemStack.getTagCompound().getBoolean(SpaceSuit_Modules.GRAVITY.getName()) && getElectricityStored(itemStack) >= 5)
								this.discharge(itemStack, 5, true);
							
							if (itemStack.getTagCompound().getBoolean(SpaceSuit_Modules.JUMP.getName()) && getElectricityStored(itemStack) >= 5) {
								if(player.onGround) this.jumpCharge = 1.0f;
								
								if (player.motionY >= 0.0 && this.jumpCharge > 0.0f && !player.isInWater()) {
									/*if (this.jumpCharge == 1.0f) {
		                                player.motionX *= 3.5;
		                                player.motionZ *= 3.5;
		                            }*//*
																	
									player.fallDistance = 0.3F;
									this.discharge(itemStack, 5, true);
									
									player.motionY += (double)(this.jumpCharge * 0.25f);
			                        this.jumpCharge = (float)((double)this.jumpCharge * 0.75);	                        
			                         
								}
							}
							else if (this.jumpCharge < 1.0f) {
								this.jumpCharge = 0.0f;
							}
							
						}
					}
					
					if (this.pressedKey[1]) {
						if (itemStack.getTagCompound().getBoolean(SpaceSuit_Modules.STEPASSIST.getName()) && getElectricityStored(itemStack) >= 5)
							player.stepHeight = 1.5F;
						else				
							player.stepHeight = 0.5F;							
					}
					
					break;
				default:
					break;
			
			}		*/	
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flagIn)
    {		
		list.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(GCCoreUtil.translate("gui.spacesuit.desc"), 250));	
		if(getArmorType(stack).equals(EntityEquipmentSlot.HEAD)) 
			list.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.spacesuit_helmet.desc"));	
		
		String color = "";
		float joules = this.getElectricityStored(stack);

		if (joules <= this.getMaxElectricityStored(stack) / 3) {
			color = "\u00a74";
		} else if (joules > this.getMaxElectricityStored(stack) * 2 / 3) {
			color = "\u00a72";
		} else {
			color = "\u00a76";
		}
		
		//list.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.module.caninstall"));
		
		list.add(EnumColor.GREY + GCCoreUtil.translate("gui.module.available_modules") + " " + stack.getTagCompound().getInteger(mod_count));
		list.add("");
		list.add(EnumColor.AQUA + GCCoreUtil.translate("gui.module.list"));
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
			
			for(ItemModule s : this.getAvailableModules()) {
				if(s.getEquipmentSlot() == getArmorType(stack) && stack.getTagCompound().hasKey(s.getName()))
					list.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(this.check(stack, s.getName()) + GCCoreUtil.translate("gui.module." + s.getName()), 150));        	
			}

        }
		else
		{
			list.add(EnumColor.DARK_AQUA + GCCoreUtil.translateWithFormat("item_desc.shift.name", GameSettings.getKeyDisplayString(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode())));
     	}
		list.add("");
		list.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/"
				+ EnergyDisplayHelper.getEnergyDisplayS(this.getMaxElectricityStored(stack)));

    }
	
	private String check(ItemStack stack, String nbt)
	{
		if (stack.hasTagCompound() && stack.getTagCompound().getBoolean(nbt)) return TextFormatting.DARK_GREEN + "- ";
		return TextFormatting.DARK_RED + "- ";		
	}
	
	private EntityEquipmentSlot getArmorType(ItemStack stack) {
        EntityEquipmentSlot i = EntityLiving.getSlotForItemStack(stack);
        return i;
	}
	
	public void needText(EntityPlayer player, String text) {
	    player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + GCCoreUtil.translate("gui.message." + text)));
	}
	public void installText(EntityPlayer player, String text) {
		player.sendMessage(new TextComponentString(TextFormatting.DARK_GREEN + GCCoreUtil.translate("gui.message.install." + text)));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
		ModelBiped armorModel;
		if(armorSlot.equals(EntityEquipmentSlot.CHEST) && itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("jetpack"))
    		armorModel = new ItemSpaceSuitModel(5);
		else
			armorModel = new ItemSpaceSuitModel(armorSlot.getIndex());
		
		if (itemStack.getItem() instanceof ItemSpaceSuit) {
			armorModel = fillingArmorModel(armorModel, entityLiving);
			if (hasColor(itemStack) && armorModel instanceof ModelOBJArmor)
				((ModelOBJArmor)armorModel).color = getColor(itemStack);
		}
		return armorModel;
	}
	
	public static ModelBiped fillingArmorModel(ModelBiped model, EntityLivingBase entityLiving) { 
		if (model == null) return model; 
			model.bipedHead.showModel = 
			model.bipedHeadwear.showModel = 
			model.bipedBody.showModel = 
			model.bipedRightArm.showModel = 
			model.bipedLeftArm.showModel = 
			model.bipedRightLeg.showModel = 
			model.bipedLeftLeg.showModel = false; 
			model.isSneak = entityLiving.isSneaking(); 
			model.isRiding = entityLiving.isRiding(); 
			model.isChild = entityLiving.isChild(); 
			
			return model; 
	}
	
	@Override
	public float getMaxElectricityStored(ItemStack stack) {
		
		boolean energy = stack.hasTagCompound() && stack.getTagCompound().hasKey("energy");
		switch(getArmorType(stack).getIndex())
		{
			case 0: 			
				return energy ? 100000.0F : 25000.0F;
			case 1: 
				return energy ? 150000.0F : 50000.0F;
			case 2: 
				return energy ? 250000.0F : 75000.0F;
			case 3: 
				return energy ? 50000.0F : 20000.0F;				
			default: 
				return 10000.0F;
		}
	}
	
	@Override
	public int gravityOverrideIfLow(EntityPlayer player) {
		if (!player.inventory.armorInventory.get(0).isEmpty() && !player.inventory.armorInventory.get(0).hasTagCompound()) 
			player.inventory.armorInventory.get(0).setTagCompound(new NBTTagCompound());
		
		return (!player.inventory.armorInventory.get(0).isEmpty()
				&& player.inventory.armorInventory.get(0).getTagCompound().getBoolean(suit_buttons[3])				
				&& player.inventory.armorInventory.get(0).getTagCompound().getBoolean("gravity") 
				&& getElectricityStored(player.inventory.armorInventory.get(0)) > 0) ? 100 : 0;
		
	}
	
	@Override
	public int gravityOverrideIfHigh(EntityPlayer p) {
		return 0;
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isUnblockable()) 
			return new ISpecialArmor.ArmorProperties(0, 0.0, 0);
		return new ISpecialArmor.ArmorProperties(1, 0.15, Integer.MAX_VALUE);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		
		this.discharge(stack, 5, true);		
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSArmorTab || tab == CreativeTabs.SEARCH)
        {
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));	        
        }
    }

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
    {
		return 0x2ed8db;
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
		return true;
    }

	@Override
	public void consumeFuel(ItemStack stack, int fuel) {
		this.discharge(stack, 5, true);
		
	}

	@Override
	public void decrementFuel(ItemStack stack) {
		if(stack.getItem() instanceof IItemElectric)
			((IItemElectric)stack.getItem()).discharge(stack, 1, true);		
	}

	@Override
	public int getFuel(ItemStack stack) {

		return stack.getTagCompound().getInteger(TAG_FUEL);
	}

	@Override
	public boolean canFly(ItemStack stack, EntityPlayer player) {
		if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("jetpack") && stack.getTagCompound().getBoolean(suit_buttons[1]))
			if(stack.getItemDamage() < stack.getMaxDamage())
			{
				return true;
			}
		return false;
	}

	@Override
	public boolean isActivated(ItemStack stack) {
		return stack.getTagCompound().getBoolean(TAG_ACT);
	}

	@Override
	public void switchState(ItemStack stack, boolean state) {
		
		if(!stack.isEmpty() && stack.hasTagCompound()) 
			stack.getTagCompound().setBoolean(TAG_ACT, state);
		else
		{
			stack.setTagCompound(new NBTTagCompound());
		}
	}

	@Override
	public int getFireStreams(ItemStack stack) {
		return 2;
	}

	@Override
	public float getSolarRadiationProtectModify() {
		return 1.0F;
	}
	
	@Override
	public Module_Type getType(ItemStack stack)
	{
		return Module_Type.SPACESUIT;
	}

	@Override
	public ItemModule[] getAvailableModules() {
		return new ItemModule[] { new SensorLens(), new Nightvision(), new Jetpack(), new Speed(), new Gravity(), new Stepassist(), new Jump(), new Energy() };
	}

	@Override
	public int getModificationCount(ItemStack stack) {
		return 2;
	}
}
