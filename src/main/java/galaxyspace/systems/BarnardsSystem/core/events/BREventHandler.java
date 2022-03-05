package galaxyspace.systems.BarnardsSystem.core.events;

import galaxyspace.core.handler.capabilities.GSStatsCapability;
import galaxyspace.core.handler.capabilities.StatsCapability;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigDimensions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BREventHandler {

	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{
		World world = event.getWorld();
		IBlockState state = world.getBlockState(event.getPos());
		
		if(state == BRBlocks.BARNARDA_C_GRASS.getDefaultState() || state == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT))
		{
			world.setBlockState(event.getPos(), BRBlocks.BARNARDA_C_FARMLAND.getDefaultState(), 3);
			event.setResult(Result.ALLOW);
		}		
	}
	
	@SubscribeEvent
	public void furnanceFuel(FurnaceFuelBurnTimeEvent e)
	{
		ItemStack stack = e.getItemStack();
		if(stack.isItemEqual(new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 1, 6)))
		{
			e.setBurnTime(180);
		}
		
		if(stack.isItemEqual(new ItemStack(BRBlocks.BARNARDA_C_VIOLET_LOG, 1, 0)))
		{
			e.setBurnTime(-1);
		}
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		World world = living.world;
		
		if (living instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)living;
			final StatsCapability gsstats = GSStatsCapability.get(player);
			
			if(BRConfigCore.enableBarnardsSystems && BRConfigDimensions.enableBarnardaC && BRConfigCore.survivalModeOnBarnarda && !gsstats.isBarnardaSurvivalMode()) { 
				WorldUtil.transferEntityToDimension(player, BRConfigDimensions.dimensionIDBarnardaC, player.getServerWorld());
				
				gsstats.setBarnardaSurvivalMode();
				
				player.sendMessage(new TextComponentString(EnumColor.BRIGHT_GREEN + "[BETA] Welcome in survival mode on Barnarda C."));
				
			}
		}
	}
}
