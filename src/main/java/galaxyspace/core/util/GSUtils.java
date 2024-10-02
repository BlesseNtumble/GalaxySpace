package galaxyspace.core.util;

import java.util.HashSet;
import java.util.Set;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.FluidRenderer;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Energy;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Gravity;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Jetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Jump;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Nightvision;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.SensorLens;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Speed;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Stepassist;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Thaumicvision;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class GSUtils {

	private static Set<ItemModule> modules = new HashSet<ItemModule>();
	
	public enum Module_Type {
		SPACESUIT, ROCKET, OXYGEN_TANK, ALL
	}
	
	public static void registerModules() {
		modules.add(new Energy());
		modules.add(new Gravity());
		modules.add(new Jetpack());
		modules.add(new Jump());
		modules.add(new Nightvision());
		modules.add(new SensorLens());
		modules.add(new Speed());
		modules.add(new Stepassist());
		modules.add(new Thaumicvision());
	}
	
	public static void registerModule(ItemModule module) {
		modules.add(module);		
	}
	
	public static ItemModule getModuleFromName(String name)
	{
		for(ItemModule modules : modules)
			if(modules.getName().equals(name))
				return modules;
		return null;
	}
	
	public static Set<ItemModule> getListModule() {
		return modules;
	}
	/*
	public static ItemStack[] getItemsForModules(String module)
	{
		if(Loader.isModLoaded("Thaumcraft"))
		{
			if(module.equals("thaumvision"))
				return new ItemStack[] {new ItemStack(ConfigItems.itemGoggles)};
		}
		
		switch(module) {
			case "sensor":			
				return new ItemStack[] { new ItemStack(GCItems.sensorGlasses, 1, 0) };
			case "nightvision":			
				return new ItemStack[] { new ItemStack(Items.potionitem, 1, 8262) };
			case "jetpack":			
				return new ItemStack[] { new ItemStack(GSItems.JetPack, 1, OreDictionary.WILDCARD_VALUE) };
			case "speed":
				return new ItemStack[] { new ItemStack(GCItems.rocketEngine, 2, 1) };
			case "gravity":
				return new ItemStack[] { new ItemStack(GSItems.CompressedPlates, 2, 1) };		
			case "stepassist":
				return new ItemStack[] { new ItemStack(Items.slime_ball, 4, 0) };	
			case "jump":
				return new ItemStack[] { new ItemStack(Item.getItemFromBlock(Blocks.piston), 2, 0) };
				
		}
		return null;
	}
	
	public enum SpaceSuit_Modules	{ 
		//HEAD
		SENSOR("sensor", 0, true, new String[] {"nightvision"}),
		NIGHTVISION("nightvision", 0, true, new String[] {"sensor"}),
		THAUMVISION("thaumvision", 0, false, new String[] {""}),
		//CHEST
		JETPACK("jetpack", 1, true, new String[] {""}),
		//SPEED
		SPEED("speed", 2, true, new String[] {""}),
		//GRAVITY
		GRAVITY("gravity", 3, true, new String[] {"jump"}),
		STEPASSIST("stepassist", 3, false, new String[] {""}),
		JUMP("jump", 3, true, new String[] {"gravity"});
	
		private final String name;
		private final int slot;
		private final String[] forb_modules;
		private final boolean isActive;
		
		SpaceSuit_Modules(String name, int slot, boolean isActive, String[] forb_modules)
		{
			this.name = name;
			this.slot = slot;
			this.isActive = isActive;
			this.forb_modules = forb_modules;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public int getSlot()
		{
			return this.slot;
		}
		
		public boolean isActive()
		{
			return this.isActive;
		}
		
		public String[] getForbiddenModules()
		{
			return this.forb_modules;
		}
	}
	*/
	public static float calculateCelestialAngle(long worldtime, float ticks, float daylenght)
    {
        int j = (int)(worldtime % daylenght);
        float f1 = ((float)j + ticks) / daylenght - 0.25F;

        if (f1 < 0.0F)
        {
            ++f1;
        }

        if (f1 > 1.0F)
        {
            --f1;
        }

        float f2 = f1;
        f1 = 1.0F - (float)((Math.cos((double)f1 * Math.PI) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1;
    }
	
	//BC Method
	public static void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity)
	{
		//int x = this.width / 2 - 8;
	    //int y = this.height / 2 - 62;
	        
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}
		
		IIcon icon = fluid.getFluid().getIcon(fluid);
		
		if (icon == null) {
			icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		FluidRenderer.setGLColorFromInt(fluid.getFluid().getColor(fluid));
		
	    //int w = 16;
	    //int h = 38;
	        
	    int fullX = width / 16;
	    int fullY = height / 16;
	    int lastX = width - fullX * 16;
	    int lastY = height - fullY * 16;
		int level = fluid.amount * height / maxCapacity;

		int fullLvl = (height - level) / 16;
		int lastLvl = (height - level) - fullLvl * 16;
		for (int i = 0; i < fullX; i++) {
			for (int j = 0; j < fullY; j++) {
				if (j >= fullLvl) {
					drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
				}
			}
		}
		for (int i = 0; i < fullX; i++) {
			drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
		}
		for (int i = 0; i < fullY; i++) {
			if (i >= fullLvl) {
				drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
			}
		}
		drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
	}
	
	private static void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut) 
	{
		double zLevel = 0;
		 
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, zLevel, icon.getMinU(), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + height, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + cut, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
		tess.addVertexWithUV(x, y + cut, zLevel, icon.getMinU(), icon.getInterpolatedV(cut));
		tess.draw();
	}
	
	public static boolean testFuel(String name)
    {
		if (name.contains("heliumhydrogen")) return true;		
		if (name.contains("fuel")) return true;		
	
		return false;
    }
	
	public static int fillWithGCFuel(FluidTank tank, FluidStack liquid, boolean doFill, EntityTieredRocket rocket)
	{
		if (liquid != null && testFuel(FluidRegistry.getFluidName(liquid)))
		{
			FluidStack liquidInTank = tank.getFluid();;
			
			if (liquid.isFluidEqual(new FluidStack(rocket.getRocketTier() > 4 ? GSFluids.HeliumHydrogen : GalacticraftCore.fluidFuel, 1))) {
				// If the tank is empty, fill it with the current type of GC fuel
				if (liquidInTank == null) {
					return tank.fill(new FluidStack(liquid.getFluid(), liquid.amount), doFill);
				}

				// If the tank already contains something, fill it with more of the same
				if (liquidInTank.amount < tank.getCapacity()) {
					return tank.fill(new FluidStack(liquidInTank, liquid.amount), doFill);
				}
			}		

		}
		
		return 0;
	}
	
	public static int getColor(int r, int g, int b) {
		
		int R = r * 256 * 256;
		int G = g * 256;
		int B = b;
		int color = R+G+B;	
		
	    return color - 16777216;
	}
	
	public static boolean inPressureBubble(World worldObj, double avgX, double avgY, double avgZ)
	{
        for (final BlockVec3Dim blockVec : TileEntityRadiationStabiliser.loadedTiles)
        {
            if (blockVec != null && blockVec.dim == worldObj.provider.dimensionId)
            {
            	TileEntity tile = worldObj.getTileEntity(blockVec.x, blockVec.y, blockVec.z);
            	if (tile instanceof TileEntityRadiationStabiliser)
            	{
	            	if (((TileEntityRadiationStabiliser) tile).inBubble(avgX, avgY, avgZ)) return true;
            	}
            }
        }

		return false;
	}
	
	public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
        if (!world.isRemote && player instanceof EntityPlayer)
            d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.func_147447_a(vec3, vec31, par3, !par3, par3);
    }
	
	public static void destroyBlock(World world, int x, int y, int z, EntityPlayer playerEntity, int refX, int refY, int refZ) {
		if (world.isAirBlock(x, y, z))
			return;

		
		if (!(playerEntity instanceof EntityPlayerMP))
			return;
		

    	
		EntityPlayerMP player = (EntityPlayerMP) playerEntity;
		
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		Block refBlock = world.getBlock(refX, refY, refZ);
		float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
		float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

		
		if (!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f)
			return;

		/*
		if (!ForgeHooks.canToolHarvestBlock(block, meta, player.getCurrentEquippedItem()))
			return;
		 */
	
		BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
		if (event.isCanceled())
			return;

		if (player.capabilities.isCreativeMode) {
			block.onBlockHarvested(world, x, y, z, meta, player);
			if (block.removedByPlayer(world, player, x, y, z, false))
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);

			// send update to client
			if (!world.isRemote) {
				player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
			}
			return;
		}

		
		// callback to the tool the player uses. Called on both sides. This damages the
		// tool n stuff.
		player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);

		// server sided handling
		if (!world.isRemote) {
			// serverside we reproduce ItemInWorldManager.tryHarvestBlock

			// ItemInWorldManager.removeBlock
			block.onBlockHarvested(world, x, y, z, meta, player);

			if (block.removedByPlayer(world, player, x, y, z, true)) // boolean is if block can be harvested, checked
																		// above
			{
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
				block.harvestBlock(world, player, x, y, z, meta);
				block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
			}

			// always send block update to client
			player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		}
		// client sided handling
		else {
			// PlayerControllerMP pcmp = Minecraft.getMinecraft().playerController;
			// clientside we do a "this clock has been clicked on long enough to be broken"
			// call. This should not send any new packets
			// the code above, executed on the server, sends a block-updates that give us
			// the correct state of the block we destroy.

			// following code can be found in PlayerControllerMP.onPlayerDestroyBlock
			world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
			if (block.removedByPlayer(world, player, x, y, z, true)) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			}
			// callback to the tool
			ItemStack itemstack = player.getCurrentEquippedItem();
			if (itemstack != null) {
				itemstack.func_150999_a(world, block, x, y, z, player);

				if (itemstack.stackSize == 0) {
					player.destroyCurrentEquippedItem();
				}
			}
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
		}
	}
}
