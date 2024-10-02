package galaxyspace.core.achievements;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.entities.EntitySkeletonBoss;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemBlockMachine;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;

public class AchEvent {
	
	public Block block;
	public ItemStack stack;
	public EntityPlayer player;
	public int dimension;
	public World world;
	
	@SubscribeEvent
    public void onCraft(ItemCraftedEvent event)
    {
		if(event.player != null && event.crafting.getItem() != null)
		{
			Item item = event.crafting.getItem();
			int meta = event.crafting.getItemDamage();
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.aluminumWire) && meta == 0)
				event.player.triggerAchievement(AchievementList.ElectricGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.aluminumWire) && meta == 1)
				event.player.triggerAchievement(AchievementList.Electric2GC);			
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.machineTiered) && meta == 0)
				event.player.triggerAchievement(AchievementList.EnergyCellGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.machineBase) && meta == 0)
				event.player.triggerAchievement(AchievementList.CoalGeneratorGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.solarPanel) && (meta == 0 || meta == 4))
				event.player.triggerAchievement(AchievementList.SolarGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.machineBase2) && meta == 4)
				event.player.triggerAchievement(AchievementList.CircuitGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.machineBase) && meta == 12)
				event.player.triggerAchievement(AchievementList.CompressorGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.machineTiered) && meta == 4)
				event.player.triggerAchievement(AchievementList.FuranceGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GSBlocks.AssemblyMachine) && meta == 0)
				event.player.triggerAchievement(AchievementList.AssemblyGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.nasaWorkbench) && meta == 0)
				event.player.triggerAchievement(AchievementList.NASAGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GCBlocks.refinery) && meta == 0)
				event.player.triggerAchievement(AchievementList.RefineryGC);
			
			if (item == ItemBlockMachine.getItemFromBlock(GSBlocks.FuelGenerator) && meta == 0)
				event.player.triggerAchievement(AchievementList.FuelGeneratorGC);
		
			if (item == GCItems.oxygenFan && meta == 0)
				event.player.triggerAchievement(AchievementList.FanGC);
			
			if (item == GSItems.JetPack && meta == 0)
				event.player.triggerAchievement(AchievementList.JetpackGC);
		}	
	}
	
	public static void onEntityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.entityLiving;
		World world = living.worldObj;


		if (living instanceof EntityPlayerMP)
		{

			EntityPlayerMP player = (EntityPlayerMP)living;

			if(player.inventory.hasItem(GCItems.bucketOil))
				player.triggerAchievement(AchievementList.OilGC);
			
			if(player.inventory.hasItem(GCItems.bucketFuel))
				player.triggerAchievement(AchievementList.FuelGC);
			
			if(player.inventory.hasItem(GCItems.rocketTier1))
				player.triggerAchievement(AchievementList.RocketGC);
			
			if(player.inventory.hasItem(GCItems.heavyPlatingTier1))
				player.triggerAchievement(AchievementList.HdpGC);
			
			/*if(this.getLeadArmor(player))
				player.triggerAchievement(AchievementList.LeadArmorGC);*/
			
			if(player.inventory.hasItemStack(new ItemStack(MarsItems.marsItemBasic, 1, 3)))
				player.triggerAchievement(AchievementList.Hdp2GC);
			
			if(player.inventory.hasItem(MarsItems.spaceship))
				player.triggerAchievement(AchievementList.Rocket2GC);
		}
	}
		
	/*public static boolean getLeadArmor(EntityPlayerMP player)
	{
		boolean armor1 = player.inventory.hasItem(GSItems.LeadHelmet);
		boolean armor2 = player.inventory.hasItem(GSItems.LeadPlate);
		boolean armor3 = player.inventory.hasItem(GSItems.LeadLeg);
		boolean armor4 = player.inventory.hasItem(GSItems.LeadBoots);
		
		return armor1 && armor2 && armor3 && armor4;
	}*/
	@SubscribeEvent
    public void onPlayerBreakBlock(BlockEvent.BreakEvent event)
    {
		/*
		if (Main.get_dungeon(event.world.provider.dimensionId) != null)
		{
			event.setCanceled(true);
			event.getPlayer().addChatComponentMessage(new ChatComponentText(ChatColor.RED + "You cannot destroy block in dungeons."));
		}
		*/
    }
	
	@SubscribeEvent
    public void onItemPickup(ItemPickupEvent event)
    {
		Item item;
		item = event.pickedUp.getEntityItem().getItem();
		ItemStack itemStack = event.pickedUp.getEntityItem();
		int meta = itemStack.getItemDamage();

		if(item == ItemBlockMachine.getItemFromBlock(GCBlocks.basicBlock) && meta == 5) {
			event.player.triggerAchievement(AchievementList.MiningCopperGC);
	    }
		if(item == ItemBlockMachine.getItemFromBlock(GCBlocks.basicBlock) && meta == 6) {
			event.player.triggerAchievement(AchievementList.MiningTinGC);
	    }
		if(item == ItemBlockMachine.getItemFromBlock(GCBlocks.basicBlock) && meta == 7) {
			event.player.triggerAchievement(AchievementList.MiningAluminiumGC);
	    }
		if(item == GCItems.basicItem && meta == 2) {
			event.player.triggerAchievement(AchievementList.MiningSiliconGC);
	    }
		if(item == GCItems.meteoricIronRaw && meta == 0) 
			event.player.triggerAchievement(AchievementList.FallingMetallGC);

    }
	
	@SubscribeEvent
    public void onSmelt(ItemSmeltedEvent event)
    {
		
		Item item = event.smelting.getItem();
		int meta = event.smelting.getItemDamage();
		
		/*
		if (item == GCItems.basicItem && meta == 5)
		{
				event.player.triggerAchievement(AchievementList.AluminiumGC);
		}
*/
    }
	
	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
	      if(event.toDim == ConfigManagerCore.idDimensionMoon) {
	    	  event.player.triggerAchievement(AchievementList.MoonGC);
	      }
	      if(event.toDim == ConfigManagerMars.dimensionIDMars) {
	    	  event.player.triggerAchievement(AchievementList.MarsGC);
	      }
	 }
	
	@SubscribeEvent
    public void onEntityKillByPlayer(LivingDeathEvent event)
	{
		if (event.source.getDamageType().equals("player")) //fall - �������, mob - ������
		{
			if (event.entityLiving instanceof EntitySkeletonBoss)
				((EntityPlayer)((EntityDamageSource)event.source).getEntity()).triggerAchievement(AchievementList.BossMoonGC);
		}
	
	}

    public void triggerAchievement(StatBase p_71029_1_)
    {
        this.addStat(p_71029_1_, 1);
    }
    
    public void addStat(StatBase p_71064_1_, int p_71064_2_) {}



		 
}
