package galaxyspace.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCFluids;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.fluid.OxygenPressureProtocol;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class GSUtils {
	
	public static List<String> changelog = new ArrayList<String>();
	private static HashSet<BlockPos> checked;
	
	public enum Module_Type {
		PLASMA_TOOLS,
		SPACESUIT,
		ROCKET,
		OXYGEN_TANK,
		ALL
	}

	public enum FluidType
	{
		STILL,
		FLOWING
	}
	
	public static TextureAtlasSprite missingIcon;
	private static TextureMap texMap = null;
	
	public static void initFluidTextures(TextureMap map) 
	{
		missingIcon = map.getMissingSprite();

		texMap = map;
	}
	
	public static TextureAtlasSprite getBlockTexture(Block block) {
		return getBlockTexture(block, "");		
	}
	
	public static TextureAtlasSprite getBlockTexture(Block block, String addpath) 
	{
		if(block == null)
		{
			return missingIcon;
		}
		
		ResourceLocation spriteLocation;
		spriteLocation = block.getRegistryName();		
		
		TextureAtlasSprite sprite = texMap.getAtlasSprite(spriteLocation.getResourceDomain() + ":blocks/" + spriteLocation.getResourcePath() + addpath);

		return sprite != null ? sprite : missingIcon;
	}
	
	public static TextureAtlasSprite getItemTexture(Item item, String addpath) 
	{
		if(item == null)
		{
			return missingIcon;
		}
		
		ResourceLocation spriteLocation;
		spriteLocation = item.getRegistryName();		
		
		TextureAtlasSprite sprite = texMap.getAtlasSprite(spriteLocation.getResourceDomain() + ":items/" + spriteLocation.getResourcePath() + addpath);

		return sprite != null ? sprite : missingIcon;
	}
	
	public static TextureAtlasSprite getFluidTexture(Fluid fluid, FluidType type) 
	{
		if(fluid == null || type == null)
		{
			return missingIcon;
		}
		
		ResourceLocation spriteLocation;
		if (type == FluidType.STILL){
			spriteLocation = fluid.getStill();
		} else {
			spriteLocation = fluid.getFlowing();
		}

		TextureAtlasSprite sprite = texMap.getTextureExtry(spriteLocation.toString());
		
		return sprite != null ? sprite : missingIcon;
	}
	
	public static void displayGauge(int xPos, int yPos, int scale, FluidStack fluid, int side /*0-left, 1-right*/)
	{
		if(fluid == null)
		{
			return;
		}

		int guiWidth = 0;//(width - xSize) / 2;
		int guiHeight = 0;//(height - ySize) / 2;

		int start = 0;

		while(true)
		{
			int renderRemaining;

			if(scale > 16)
			{
				renderRemaining = 16;
				scale -= 16;
			}
			else {
				renderRemaining = scale;
				scale = 0;
			}

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			drawTexturedModalRect(guiWidth + xPos, guiHeight + yPos + 58 - renderRemaining - start, GSUtils.getFluidTexture(fluid.getFluid(), FluidType.STILL), 16, 16 - (16 - renderRemaining));
			start+=16;

			if(renderRemaining == 0 || scale == 0)
			{
				break;
			}
		}

		//mc.renderEngine.bindTexture(this.guiTexture);
		//drawTexturedModalRect(guiWidth + xPos, guiHeight + yPos, 176, side == 0 ? 0 : 54, 16, 54);
	}

	public static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
		double zLevel = 0.0;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }
	
	public static boolean testFuel(String name)
    {
		if (name.contains("helium")) return true;		
		if (name.contains("fuel")) return true;		
	
		return false;
    }
	
	public static int fillWithGCFuel(FluidTank tank, FluidStack liquid, boolean doFill, EntityTieredRocket rocket)
	{
		if (liquid != null && testFuel(FluidRegistry.getFluidName(liquid)))
		{
			FluidStack liquidInTank = tank.getFluid();;
			
			if (liquid.isFluidEqual(new FluidStack(rocket.getRocketTier() > 10 ? GSFluids.HeliumHydrogen : GCFluids.fluidFuel, 1))) {
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
	
	public static int getColor(int r, int g, int b, int a) {
		
		int color = a << 24 | r << 16 | g << 8 | b;			
	    return color - 16777216;
	}
	
	public static Vector3 getDimColor(Vector3 daycolor, Vector3 nightcolor, float starbrightness)
	{
		float night = starbrightness;
        float day = 1.0F - starbrightness;
        
        float dayColR = daycolor.floatX() / 255.0F;
        float dayColG = daycolor.floatY() / 255.0F;
        float dayColB = daycolor.floatZ() / 255.0F;
        float nightColR = nightcolor.floatX() / 255.0F;
        float nightColG = nightcolor.floatY() / 255.0F;
        float nightColB = nightcolor.floatZ() / 255.0F;
        return new Vector3(dayColR * day + nightColR * night,
                dayColG * day + nightColG * night,
                dayColB * day + nightColB * night);
	}
	
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
	
	public static void checkFluidTankTransfer(NonNullList<ItemStack> stacks, int slot, FluidTank tank)
    {
        if (FluidUtil.isValidContainer(stacks.get(slot)))
        {
            final FluidStack liquid = tank.getFluid();

            if (liquid != null)
            {
            	if(liquid.getFluid() == GSFluids.LiquidEthaneMethane)
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GSItems.EM_CANISTER);            
            	else if(liquid.getFluid().getName().contains("methane"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, AsteroidsItems.methaneCanister);
            	else if(liquid.getFluid() == GSFluids.Helium3)
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GSItems.HELIUM_CANISTER);
            	else if(liquid.getFluid() == GSFluids.HeliumHydrogen)
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GSItems.HELIUM_HYDROGEN_CANISTER);
            	else if(liquid.getFluid().getName().contains("hydrogen"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GSItems.HYDROGEN_CANISTER);
            	else if(liquid.getFluid().getName().contains("ethane"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GSItems.ETHANE_CANISTER); 
            	else if(liquid.getFluid().getName().contains("oxygen"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, AsteroidsItems.canisterLOX);
            	else if(liquid.getFluid().getName().contains("oil"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GCItems.oilCanister);
            	else if(liquid.getFluid().getName().contains("fuel"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, GCItems.fuelCanister);
            	else if(liquid.getFluid().getName().contains("nirtogen"))
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, AsteroidsItems.canisterLN2);           
            	else if(liquid.isFluidEqual(new FluidStack(FluidRegistry.WATER, 1)) && stacks.get(slot).getItem() instanceof ItemBucket)
            		FluidUtil.tryFillContainer(tank, liquid, stacks, slot, Items.BUCKET);
            	else if(liquid.isFluidEqual(new FluidStack(FluidRegistry.WATER, 1)) && !(stacks.get(slot).getItem() instanceof ItemBucket))
            	{
            		
            	}
            	else FluidUtil.tryFillContainer(tank, liquid, stacks, slot, Items.BUCKET);
            }
        }      
    }
	
	public static boolean getThermalControl(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		checked = new HashSet<>();
		return testContactWithBreathableAir(world, block, pos, 0) == 1;
	}
	
	private static synchronized int testContactWithBreathableAir(World world, Block block, BlockPos pos,
			int limitCount) {
		checked.add(pos);
		if (block == GCBlocks.breatheableAir || block == GCBlocks.brightBreatheableAir) {
			return block.getMetaFromState(world.getBlockState(pos));
		}

		IBlockState state = world.getBlockState(pos);
		if (block == null || block.getMaterial(state) == Material.AIR) {
			return -1;
		}

		// Test for non-sided permeable or solid blocks first
		boolean permeableFlag = false;
		if (!(block instanceof BlockLeaves)) {
			if (block.isOpaqueCube(state)) {
				if (block instanceof BlockGravel || block.getMaterial(state) == Material.CLOTH
						|| block instanceof BlockSponge) {
					permeableFlag = true;
				} else {
					return -1;
				}
			} else if (block instanceof BlockGlass || block instanceof BlockStainedGlass) {
				return -1;
			} else if (block instanceof BlockLiquid) {
				return -1;
			} else if (OxygenPressureProtocol.nonPermeableBlocks.containsKey(block)) {
				ArrayList<Integer> metaList = OxygenPressureProtocol.nonPermeableBlocks.get(block);
				if (metaList.contains(-1) || metaList.contains(state.getBlock().getMetaFromState(state))) {
					return -1;
				}
			}
		} else {
			permeableFlag = true;
		}

		// Testing a non-air, permeable block (for example a torch or a ladder)
		if (limitCount < 5) {
			for (EnumFacing side : EnumFacing.VALUES) {
				if (permeableFlag || canBlockPassAirOnSide(world, block, pos, side)) {
					BlockPos sidevec = pos.add(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
					if (!checked.contains(sidevec)) {
						Block newblock = world.getBlockState(sidevec).getBlock();
						int adjResult = testContactWithBreathableAir(world, newblock, sidevec,
								limitCount + 1);
						if (adjResult >= 0) {
							return adjResult;
						}
					}
				}
			}
		}

		return -1;
	}
	
	 private static boolean canBlockPassAirOnSide(World world, Block block, BlockPos vec, EnumFacing side)
	    {
	        if (block instanceof IPartialSealableBlock)
	        {
	            return !((IPartialSealableBlock) block).isSealed(world, vec, side);
	        }

	        //Half slab seals on the top side or the bottom side according to its metadata
	        if (block instanceof BlockSlab)
	        {
	            IBlockState state = world.getBlockState(vec);
	            int meta = state.getBlock().getMetaFromState(state);
	            return !(side == EnumFacing.DOWN && (meta & 8) == 8 || side == EnumFacing.UP && (meta & 8) == 0);
	        }

	        //Farmland etc only seals on the solid underside
	        if (block instanceof BlockFarmland || block instanceof BlockEnchantmentTable || block instanceof BlockLiquid)
	        {
	            return side != EnumFacing.UP;
	        }

	        if (block instanceof BlockPistonBase)
	        {
	            IBlockState state = world.getBlockState(vec);
	            if ((Boolean) state.getValue(BlockPistonBase.EXTENDED))
	            {
	                int meta0 = state.getBlock().getMetaFromState(state);
	                EnumFacing facing = BlockPistonBase.getFacing(meta0);
	                return side != facing;
	            }
	            return false;
	        }

	        return !block.isSideSolid(world.getBlockState(vec), world, vec, EnumFacing.getFront(side.getIndex() ^ 1));
	    }
	 
	public static void start() {
		Minecraft mc = Minecraft.getMinecraft();
		
		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(mc, mc.displayWidth, mc.displayHeight);

		final int width = scaledresolution.getScaledWidth();
		final int height = scaledresolution.getScaledHeight();

		try {
			URL url = new URL(
					"https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/1.12.2/1.12.2/changelog.txt");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String str;

			int i = 0;
			while ((str = in.readLine()) != null) {
				if (mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("ru_")) {
					i++;
					String str2 = new String(str.getBytes(), "UTF-8");
					if (str2.startsWith("r.")) {
						str2 = str2.replace("r.", "");
						changelog.add(str2);
						if(str2.length() > 70) changelog.add("");
						//font.drawString(str2, x, y + 11 * i + 10,
								//ColorUtil.to32BitColor(255, 150, 200, 255), true);
					}
				} else {//if (this.mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("en_")) {
					i++;
					String str2 = new String(str.getBytes(), "UTF-8");
					if (str2.startsWith("e.")) {
						str2 = str.replace("e.", "");
						changelog.add(str2);
						if(str2.length() > 70) changelog.add("");
						//font.drawString(str2, x, y + 12 * i - 185,
								//ColorUtil.to32BitColor(255, 150, 200, 255), true);
					}
				} 
			}
			//count = i;
			in.close();
		} catch (Exception e) {
		}
	}
	
	public static String path = "K://MCP/codding/1.12.2/src/main/resources/assets/galaxyspace/";

	public static void addBlockJsonFiles(Block block, String addPath){
		try{
			File blockStates = new File(path + "/blockstates/", block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			File modelBlock = new File(path + "/models/block/", block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			File modelItemBlock = new File(path + "/models/item/", block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			
			if (!blockStates.exists())
				if (blockStates.createNewFile()) {
					blockstateJson(block, blockStates);
				} else if (blockStates.exists()) {
					blockstateJson(block, blockStates);
				}
			
			if (!modelBlock.exists())
				if (modelBlock.createNewFile()) {
					modelBlockJson(block, modelBlock, addPath);
				} else if (modelBlock.exists()) {
					modelBlockJson(block, modelBlock, addPath);
				}
			
			if (!modelItemBlock.exists())
				if (modelItemBlock.createNewFile()) {
					modelItemBlockJson(block, modelItemBlock, addPath);
				} else if (modelItemBlock.exists()) {
					modelItemBlockJson(block, modelItemBlock, addPath);
				}
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	
	private static void blockstateJson(Block block, File file){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		  "{"
		+ "\n \"variants\": {"
		+     "\n \"normal\": { "
		+         "\"model\" : "
		+          "\"" + GalaxySpace.MODID + ":" + block.getUnlocalizedName().toLowerCase().substring(5) + "\""
		+         "}"
		+     "}"
		+ "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
		
	}
	
	private static void modelBlockJson(Block block, File file, String addPath){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"block/cube_all\", "
		+     " \"textures\": { "
		+         "\"all\" : "
		+          "\"" + GalaxySpace.MODID + ":blocks/" + addPath + block.getUnlocalizedName().toLowerCase().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
		
	}
	
	private static void modelItemBlockJson(Block block, File file, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{\n" 
			+ "	\"parent\": \"" + GalaxySpace.MODID + ":block/" + block.getUnlocalizedName().toLowerCase().substring(5) + "\" \n"
			+ "}"
			);
			writer.close();
		}catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
	/*
	private static void modelItemBlockJson(Block block, File file, String addPath){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"block/cube_all\", "
		+     " \"textures\": { "
		+         "\"all\" : "
		+          "\"" + GalaxySpace.MODID + ":blocks/" + addPath + block.getUnlocalizedName().toLowerCase().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	*/
	// ***********ITEMS************
	public static void addItemJsonFiles(Item item) {
		addItemJsonFiles(item, "", item.getUnlocalizedName().toLowerCase().substring(5));
	}
	
	public static void addItemJsonFiles(Item item, String folder)
	{
		addItemJsonFiles(item, folder, item.getUnlocalizedName().toLowerCase().substring(5));
	}
	
	public static void addItemJsonFiles(Item item, String folder, String name) {
		try {
			File pathFolder = new File(path + "/models/item/" + folder);
			if(!pathFolder.exists()) pathFolder.mkdirs();
			
			File modelItemFile = new File(pathFolder, name + ".json");
		
			if(!modelItemFile.exists())
				if (modelItemFile.createNewFile()) {
					modelItemJson(item, modelItemFile, folder);
				} else if (modelItemFile.exists()) {
					modelItemJson(item, modelItemFile, folder);
				}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelItemJson(Item item, File file, String folder) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"item/generated\", "
			+     " \"textures\": { "
			+         "\"layer0\" : "
			+          "\"" + GalaxySpace.MODID + ":items/" + folder + item.getUnlocalizedName().toLowerCase().substring(5) + "\""
			+       "}"
			+   "}"
			);					
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	// ***********BLOCKS META************
	public static void addBlockMetadataJsonFiles(Block block, String[] variants, String property, String addPath) {
		try {
			File blockstateItemBlockMeta = new File(path + "/blockstates/",
					block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			for (int i = 0; i < variants.length; i++) {
				File modelBlockMeta = new File(path + "/models/block/" + addPath,
						variants[i] + ".json");
				File modelItemBlockMeta = new File(path + "/models/item/" + addPath,
						variants[i] + ".json");

				if(!modelBlockMeta.exists())
					if (modelBlockMeta.createNewFile()) {
						modelBlockMetaJson(block, variants, modelBlockMeta, i, addPath);
					} else if (modelBlockMeta.exists()) {
						modelBlockMetaJson(block, variants, modelBlockMeta, i, addPath);
					}
				if(!modelItemBlockMeta.exists())
					if (modelItemBlockMeta.createNewFile()) {
						modelItemBlockMetaJson(block, variants, modelItemBlockMeta, i, addPath);
					} else if (modelItemBlockMeta.exists()) {
						modelItemBlockMetaJson(block, variants, modelItemBlockMeta, i, addPath);
					}
			}
			if(!blockstateItemBlockMeta.exists())
				if (blockstateItemBlockMeta.createNewFile()) {
					blockstateItemBlockMetaJson(block, blockstateItemBlockMeta, variants, property);
				} else if (blockstateItemBlockMeta.exists()) {
					blockstateItemBlockMetaJson(block, blockstateItemBlockMeta, variants, property);
				}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void blockstateItemBlockMetaJson(Block block, File file, String[] variants, String property) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("{ \n	\"variants\": { \n");
			
			for (int i = 0; i < variants.length; i++) {
				String string = "\"" + property + "="  + variants[i] + "\"" + ": { \"model\": " + "\"" + GalaxySpace.MODID
						+ ":" + variants[i] + "\"}";
				if (variants[i] != variants[0]) {
					writer.write(", \n" + "		" + string);
				} else {
					writer.write("		" + string);
				}
			}
			writer.write("\n	} \n}");
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelBlockMetaJson(Block block, String[] variants, File file, int i, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{ \n"
			+ "	\"parent\": \"block/cube_all\", \n"
			+ " 	\"textures\": { "
			+         "\"all\" : "
			+          "\"" + GalaxySpace.MODID + ":blocks/" + addPath + variants[i] + "\""
			+       "}"
			+   "\n}"
			);
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelItemBlockMetaJson(Block block, String[] variants, File file, int i, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{\n" 
			+ "	\"parent\": \"" + GalaxySpace.MODID + ":block/" + variants[i] + "\" \n"
			+ "}"
			);
			writer.close();
		}catch (IOException ex) {
			System.out.println(ex);
		}
	}
	/*
	private static void modelItemBlockMetaJson(Block block, String[] variants, File file, int i, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"block/cube_all\", "
			+     " \"textures\": { "
			+         "\"all\" : "
			+          "\"" + GalaxySpace.MODID + ":blocks/" + addPath + variants[i] + "\""
			+       "}"
			+   "}"
			);
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}*/

	// ***********ITEMS META************
	public static void addItemMetadataJsonFiles(Item item, String[] variants, String folder) {
		try {
			for (int i = 0; i < variants.length; i++) {
				File folders = new File(path + "/models/item/"+ folder);
				if(!folders.exists()) folders.mkdirs();
				
				File modelVariantsItemMeta = new File(path + "/models/item/"+ folder, variants[i] + ".json");
				
							
				if (!modelVariantsItemMeta.exists())
					if (modelVariantsItemMeta.createNewFile()) {
						modelVariantsItemMetaJson(item, variants, modelVariantsItemMeta, i, folder);
					} else if (modelVariantsItemMeta.exists()) {
						modelVariantsItemMetaJson(item, variants, modelVariantsItemMeta, i, folder);
					}
				
			}

		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelVariantsItemMetaJson(Item item, String[] variants, File file, int i, String folder) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"item/generated\", "
			+     " \"textures\": { "
			+         "\"layer0\" : "
			+          "\"" + GalaxySpace.MODID + ":items/" + folder + variants[i] + "\""
			+       "}"
			+   "}"
			);
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
	public static void renderDebugGui(GuiContainer gui, int width, int height)
	{
		boolean enable = GalaxySpace.debug;
		
		if(enable)
			for(int i = 0; i < gui.inventorySlots.inventorySlots.size(); i++)
			{
				int x = gui.inventorySlots.getSlot(i).xPos;
				int y = gui.inventorySlots.getSlot(i).yPos;
				gui.mc.fontRenderer.drawString(EnumColor.WHITE + "" + gui.inventorySlots.getSlot(i).getSlotIndex(), width + x + 5, height + y + 5, 4210752);
			}
	}
	
	public static ItemStack findMatchingNASARecipe(List<INasaWorkbenchRecipe> recipes, IInventory craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : recipes)
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }
	

	public static void renderItemIntoSlot(RenderItem re, ItemStack stack, int x, int y, Vec3d color)
    {
		GlStateManager.pushMatrix();
        renderItemModelIntoGUI(re, stack, x, y, re.getItemModelWithOverrides(stack, (World)null, (EntityLivingBase)null), color);
        GSUtils.drawRect(x+1, y+1, x + 15, y + 15, 0xCC8B8B8B, 100.5D);        
        GlStateManager.popMatrix();
    }

    private static void renderItemModelIntoGUI(RenderItem re, ItemStack stack, int x, int y, IBakedModel bakedmodel, Vec3d color)
    {
        GlStateManager.pushMatrix();
        re.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        re.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        re.setupGuiTransform(x, y, bakedmodel.isGui3d());
        bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
        renderItem(re, stack, bakedmodel, color);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        re.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        re.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }
    
    private static void renderItem(RenderItem re, ItemStack stack, IBakedModel model, Vec3d color)
    {
        if (!stack.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);

            if (model.isBuiltInRenderer())
            {
            	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
            }
            else
            {
                renderModel(re, model, stack, color);

                if (stack.hasEffect())
                {
                    renderEffect(re, model);
                }
            }

            GlStateManager.popMatrix();
        }
    }
	
    private static void renderEffect(RenderItem re, IBakedModel model)
    {
    	ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        re.textureManager.bindTexture(RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
        GlStateManager.translate(f, 0.0F, 0.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
        renderModel(re, model, -8372020);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
        GlStateManager.translate(-f1, 0.0F, 0.0F);
        GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
        renderModel(re, model, -8372020);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        re.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }
    
    private static void renderModel(RenderItem re, IBakedModel model, ItemStack stack, Vec3d color)
    {
        renderModel(re, model, GSUtils.getColor((int) (255 * color.x), (int) (255 * color.y), (int) (255 * color.z), 255), stack);
    }

    private static void renderModel(RenderItem re, IBakedModel model, int color)
    {
        renderModel(re, model, color, ItemStack.EMPTY);
    }
    
    private static void renderModel(RenderItem re, IBakedModel model, int color, ItemStack stack)
    {
        if (net.minecraftforge.common.ForgeModContainer.allowEmissiveItems)
        {
            net.minecraftforge.client.ForgeHooksClient.renderLitItem(re, model, color, stack);
            return;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

        for (EnumFacing enumfacing : EnumFacing.values())
        {
            re.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, enumfacing, 0L), color, stack);
        }

        re.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, (EnumFacing)null, 0L), color, stack);
        tessellator.draw();
        
    }
	
    public static void drawRect(int left, int top, int right, int bottom, int color, double zLevel)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)left, (double)bottom, zLevel).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, zLevel).endVertex();
        bufferbuilder.pos((double)right, (double)top, zLevel).endVertex();
        bufferbuilder.pos((double)left, (double)top, zLevel).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
