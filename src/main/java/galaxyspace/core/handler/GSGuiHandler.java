package galaxyspace.core.handler;

import asmodeuscore.core.astronomy.gui.book.ACGuiGuideBook;
import galaxyspace.core.client.gui.entity.GuiAstroWolfInventory;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import galaxyspace.core.prefab.inventory.ContainerAstroWolf;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiGasCollector;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiGasGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiModernStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiOxygenStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiRocketAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGasCollector;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGasGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModernStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerOxygenStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRocketAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvOxygenStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasCollector;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSGuiHandler implements IGuiHandler{

	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

        if (playerBase == null)
        {
            player.sendMessage(new TextComponentString("Galaxy Space player instance null server-side. This is a bug."));
            return null;
        }

        GCPlayerStats stats = GCPlayerStats.get(playerBase);
        

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        
        if(ID == GSConfigCore.guiIDGuideBook) 
        	return new ContainerPlayer(player.inventory, false, player);
        
        if(ID == 1005)
        	return new ContainerAstroWolf(player.inventory, (EntityAstroWolf) world.getEntityByID(x), player);
        
        if (tile != null)
        {
        	
        	if (tile instanceof TileEntityFuelGenerator)
                return new ContainerFuelGenerator(player.inventory, (TileEntityFuelGenerator) tile);
        	
        	else if (tile instanceof TileEntityAssembler)
        		return new ContainerAssembler(player.inventory, (TileEntityAssembler) tile);   
        	
        	else if (tile instanceof TileEntityModernSolarPanel)
        		return new ContainerModernSolarPanel(player.inventory, (TileEntityModernSolarPanel) tile);   
        
        	else if (tile instanceof TileEntityWindGenerator)
             	return new ContainerWindGenerator(player.inventory, (TileEntityWindGenerator) tile);   
         
        	else if (tile instanceof TileEntityRocketAssembler)
        		return new ContainerRocketAssembler(player.inventory, (TileEntityRocketAssembler) tile);   
        	
        	else if (tile instanceof TileEntityUniversalRecycler)
        		return new ContainerUniversalRecycler(player.inventory, (TileEntityUniversalRecycler) tile);
        
        	else if (tile instanceof TileEntityLiquidExtractor)
        		return new ContainerLiquidExtractor(player.inventory, (TileEntityLiquidExtractor) tile);
        	
        	else if (tile instanceof TileEntityLiquidSeparator)
        		return new ContainerLiquidSeparator(player.inventory, (TileEntityLiquidSeparator) tile);
        	
        	else if (tile instanceof TileEntityHydroponicBase)
        		return new ContainerHydroponicBase(player.inventory, (TileEntityHydroponicBase) tile);
        	
        	else if (tile instanceof TileEntityGravitationModule)
        		return new ContainerGravitationModule(player.inventory, (TileEntityGravitationModule) tile);
        	
        	else if (tile instanceof TileEntityRadiationStabiliser)
        		return new ContainerRadiationStabiliser(player.inventory, (TileEntityRadiationStabiliser) tile);
        	
        	else if (tile instanceof TileEntityPlanetShield)
        		return new ContainerPlanetShield(player.inventory, (TileEntityPlanetShield) tile);
        	
        	else if (tile instanceof TileEntityModernStorageModule)
        		return new ContainerModernStorageModule(player.inventory, (TileEntityModernStorageModule) tile);
        	
        	else if (tile instanceof TileEntityModificationTable)
        		return new ContainerModificationTable(player.inventory, (TileEntityModificationTable) tile);
        	
        	else if (tile instanceof TileEntityAdvOxygenStorageModule)
        		return new ContainerOxygenStorageModule(player.inventory, (TileEntityAdvOxygenStorageModule) tile);
        	
            else if (tile instanceof TileEntityAdvCircuitFabricator)
        		return new ContainerAdvCircuitFabricator(player.inventory, (TileEntityAdvCircuitFabricator) tile);
        	
        	else if (tile instanceof TileEntityResearchTable)
        		return new ContainerResearchTable(player.inventory, (TileEntityResearchTable) tile);
        	
        	else if (tile instanceof TileEntityGasCollector)
        		return new ContainerGasCollector(player.inventory, (TileEntityGasCollector) tile);

        	else if (tile instanceof TileEntityGasGenerator)
                return new ContainerGasGenerator(player.inventory, (TileEntityGasGenerator) tile);
        }
       
        
        return null;
    }
	
	@Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (GCCoreUtil.getEffectiveSide() == Side.CLIENT)
        {
        	if(ID == 1005)
            	return new GuiAstroWolfInventory(player, (EntityAstroWolf) world.getEntityByID(x));
            			
            return this.getClientGuiElement(ID, player, world, new BlockPos(x, y, z));
        }

        return null;
    }
	
	@SideOnly(Side.CLIENT)
    private Object getClientGuiElement(int ID, EntityPlayer player, World world, BlockPos position)
    {
        EntityPlayerSP playerClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);
        TileEntity tile = world.getTileEntity(position);
        
        if(ID == GSConfigCore.guiIDGuideBook) 
        	return new ACGuiGuideBook();
        
        
        			
        if (tile != null)
        {
        	if (tile instanceof TileEntityFuelGenerator)            
                return new GuiFuelGenerator(player.inventory, (TileEntityFuelGenerator) tile);
        	
        	else if (tile instanceof TileEntityAssembler)            
        		return new GuiAssembler(player.inventory, (TileEntityAssembler) tile);
        	
        	else if (tile instanceof TileEntityModernSolarPanel)            
        		return new GuiModernSolarPanel(player.inventory, (TileEntityModernSolarPanel) tile);
            
        	else if (tile instanceof TileEntityWindGenerator)            
        		return new GuiWindGenerator(player.inventory, (TileEntityWindGenerator) tile);
        	
        	else if (tile instanceof TileEntityRocketAssembler)            
        		return new GuiRocketAssembler(player.inventory, (TileEntityRocketAssembler) tile);
        	
        	else if (tile instanceof TileEntityUniversalRecycler)            
        		return new GuiUniversalRecycler(player.inventory, (TileEntityUniversalRecycler) tile);
        	
        	else if (tile instanceof TileEntityLiquidExtractor)            
        		return new GuiLiquidExtractor(player.inventory, (TileEntityLiquidExtractor) tile);
        	
        	else if (tile instanceof TileEntityLiquidSeparator)            
        		return new GuiLiquidSeparator(player.inventory, (TileEntityLiquidSeparator) tile);
        	
        	else if (tile instanceof TileEntityHydroponicBase)            
        		return new GuiHydroponicBase(player.inventory, (TileEntityHydroponicBase) tile);
        	
        	else if (tile instanceof TileEntityGravitationModule)            
        		return new GuiGravitationModule(player.inventory, (TileEntityGravitationModule) tile);
        	
        	else if (tile instanceof TileEntityRadiationStabiliser)            
        		return new GuiRadiationStabiliser(player.inventory, (TileEntityRadiationStabiliser) tile);
        	
        	else if (tile instanceof TileEntityPlanetShield)            
        		return new GuiPlanetShield(player.inventory, (TileEntityPlanetShield) tile);        	
        	
        	else if (tile instanceof TileEntityModernStorageModule)            
        		return new GuiModernStorageModule(player.inventory, (TileEntityModernStorageModule) tile);
        	
        	else if (tile instanceof TileEntityModificationTable)            
        		return new GuiModificationTable(player.inventory, (TileEntityModificationTable) tile);
        	
        	else if (tile instanceof TileEntityAdvOxygenStorageModule)            
        		return new GuiOxygenStorageModule(player.inventory, (TileEntityAdvOxygenStorageModule) tile);
        	
        	else if (tile instanceof TileEntityAdvCircuitFabricator)            
        		return new GuiAdvCircuitFabricator(player.inventory, (TileEntityAdvCircuitFabricator) tile);
        	
        	else if (tile instanceof TileEntityResearchTable)            
        		return new GuiResearchTable(player.inventory, (TileEntityResearchTable) tile);        
        	
        	else if (tile instanceof TileEntityGasCollector)            
        		return new GuiGasCollector(player.inventory, (TileEntityGasCollector) tile); 
        	
        	else if (tile instanceof TileEntityGasGenerator)            
        		return new GuiGasGenerator(player.inventory, (TileEntityGasGenerator) tile);  

        }
        return null;
    }
        
}
