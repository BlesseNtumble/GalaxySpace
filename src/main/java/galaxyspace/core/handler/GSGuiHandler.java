package galaxyspace.core.handler;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.systems.SolarSystem.moons.moon.gui.GuiAlienTrade;
import galaxyspace.systems.SolarSystem.moons.moon.inventory.ContainerAlienTrade;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiAdvFuelLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiAdvOxygenSealer;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiConverterSurface;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiOxStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiOxygenFiller;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiRocketAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiSolarWind;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.GuiWindTurbine;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAdvFuelLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAdvOxygenSealer;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerConverterSurface;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerOxStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerOxygenFiller;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRocketAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerSolarWind;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerWindTurbine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvFuelLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvOxygenSealer;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityConverterSurface;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxygenFiller;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarWind;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindTurbine;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GSGuiHandler implements IGuiHandler
{
	
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);
        GuiContainerGC inv;
        InventoryPlayer inv1 = player.inventory;
        GCPlayerStats stats = GCPlayerStats.get(playerBase);

        if (playerBase == null)
        {
            player.addChatMessage(new ChatComponentText("Galacticraft player instance null server-side. This is a bug."));
            return null;
        }
                
        TileEntity tile = world.getTileEntity(x, y, z);

        if(ID == 10)
        {
        	return new ContainerAlienTrade(player.inventory);
        }
        
        if (tile != null)
        {
        	
            if (tile instanceof TileEntityAssemblyMachine)            
                return new ContainerAssemblyMachine(player.inventory, (TileEntityAssemblyMachine) tile);            

            else if (tile instanceof TileEntityFuelGenerator)
                return new ContainerFuelGenerator(player.inventory, (TileEntityFuelGenerator) tile);            
            
            else if (tile instanceof TileEntityStorageModule)
                return new ContainerStorageModule(player.inventory, (TileEntityStorageModule) tile);
            
            else if (tile instanceof TileEntitySolarWind)
                return new ContainerSolarWind(player.inventory, (TileEntitySolarWind) tile);
            
            else if (tile instanceof TileEntityConverterSurface)
                return new ContainerConverterSurface(player.inventory, (TileEntityConverterSurface) tile);
            
            else if (tile instanceof TileEntityOxStorageModule)
                return new ContainerOxStorageModule(player.inventory, (TileEntityOxStorageModule) tile);
                    
            else if (tile instanceof TileEntitySolarPanel)
                return new ContainerSolarPanel(player.inventory, (TileEntitySolarPanel) tile);
            
            else if (tile instanceof TileEntityGravitationModule)
                return new ContainerGravitationModule(player.inventory, (TileEntityGravitationModule) tile);
            
            else if (tile instanceof TileEntityWindTurbine)
            	return new ContainerWindTurbine(player.inventory, (TileEntityWindTurbine) tile);
            
            else if (tile instanceof TileEntityAdvOxygenSealer)
            	return new ContainerAdvOxygenSealer(player.inventory, (TileEntityAdvOxygenSealer) tile);
            
            else if (tile instanceof TileEntityAdvFuelLoader)
            	return new ContainerAdvFuelLoader(player.inventory, (TileEntityAdvFuelLoader) tile);
            
            else if (tile instanceof TileEntityRocketAssemblyMachine)
            	return new ContainerRocketAssemblyMachine(player.inventory, (TileEntityRocketAssemblyMachine) tile);
        
            else if (tile instanceof TileEntityRecycler)
            	return new ContainerRecycler(player.inventory, (TileEntityRecycler) tile);
            
            else if (tile instanceof TileEntityLiquidExtractor)
            	return new ContainerLiquidExtractor(player.inventory, (TileEntityLiquidExtractor) tile);
        
            else if (tile instanceof TileEntityPortableNuclearReactor)
            	return new ContainerPortableNuclearReactor(player.inventory, (TileEntityPortableNuclearReactor) tile);
   
            else if (tile instanceof TileEntityHydroponicBase)
            	return new ContainerHydroponicBase(player.inventory, (TileEntityHydroponicBase) tile);
            
            else if (tile instanceof TileEntityLiquidSeparator)
            	return new ContainerLiquidSeparator(player.inventory, (TileEntityLiquidSeparator) tile);
       
            else if (tile instanceof TileEntityRadiationStabiliser)
            	return new ContainerRadiationStabiliser(player.inventory, (TileEntityRadiationStabiliser) tile);
            
            else if (tile instanceof TileEntityModificationTable)
            	return new ContainerModificationTable(player.inventory, (TileEntityModificationTable) tile);
            
            else if (tile instanceof TileEntityOxygenFiller)
            	return new ContainerOxygenFiller(player.inventory, (TileEntityOxygenFiller) tile);
            
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            return this.getClientGuiElement(ID, player, world, new Vector3(x, y, z));
        }
        
        return null;
    }

    @SideOnly(Side.CLIENT)
    private Object getClientGuiElement(int ID, EntityPlayer player, World world, Vector3 position)
    {
        //EntityClientPlayerMP playerClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);

        TileEntity tile = world.getTileEntity(position.intX(), position.intY(), position.intZ());
        
        if(ID == 10)
        {
        	return new GuiAlienTrade(player.inventory);
        }
        if (tile != null)
        {
        	
            if (tile instanceof TileEntityAssemblyMachine)            
                return new GuiAssemblyMachine(player.inventory, (TileEntityAssemblyMachine) world.getTileEntity(position.intX(), position.intY(), position.intZ()));

            else if (tile instanceof TileEntityFuelGenerator)            
                return new GuiFuelGenerator(player.inventory, (TileEntityFuelGenerator) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityStorageModule)            
                return new GuiStorageModule(player.inventory, (TileEntityStorageModule) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntitySolarWind)
                return new GuiSolarWind(player.inventory, (TileEntitySolarWind) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityConverterSurface)            
            	return new GuiConverterSurface(player.inventory, (TileEntityConverterSurface) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityOxStorageModule)            
                return new GuiOxStorageModule(player.inventory, (TileEntityOxStorageModule) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntitySolarPanel)
            	return new GuiSolarPanel(player.inventory, (TileEntitySolarPanel) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityGravitationModule)            
                return new GuiGravitationModule(player.inventory, (TileEntityGravitationModule) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
                        
            else if (tile instanceof TileEntityWindTurbine)
            	return new GuiWindTurbine(player.inventory, (TileEntityWindTurbine) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
                    
            else if (tile instanceof TileEntityAdvOxygenSealer)
            	return new GuiAdvOxygenSealer(player.inventory, (TileEntityAdvOxygenSealer) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
             
            else if (tile instanceof TileEntityAdvFuelLoader)
            	return new GuiAdvFuelLoader(player.inventory, (TileEntityAdvFuelLoader) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityRocketAssemblyMachine)
            	return new GuiRocketAssemblyMachine(player.inventory, (TileEntityRocketAssemblyMachine) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
           
            else if (tile instanceof TileEntityRecycler)
            	return new GuiRecycler(player.inventory, (TileEntityRecycler) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
           
            else if (tile instanceof TileEntityLiquidExtractor)
            	return new GuiLiquidExtractor(player.inventory, (TileEntityLiquidExtractor) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityPortableNuclearReactor)
            	return new GuiPortableNuclearReactor(player.inventory, (TileEntityPortableNuclearReactor) world.getTileEntity(position.intX(), position.intY(), position.intZ()));

            else if (tile instanceof TileEntityHydroponicBase)
            	return new GuiHydroponicBase(player.inventory, (TileEntityHydroponicBase) tile);
            
            else if (tile instanceof TileEntityLiquidSeparator)
            	return new GuiLiquidSeparator(player.inventory, (TileEntityLiquidSeparator) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
        
            else if (tile instanceof TileEntityRadiationStabiliser)
            	return new GuiRadiationStabiliser(player.inventory, (TileEntityRadiationStabiliser) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityModificationTable)
            	return new GuiModificationTable(player.inventory, (TileEntityModificationTable) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
            else if (tile instanceof TileEntityOxygenFiller)
            	return new GuiOxygenFiller(player.inventory, (TileEntityOxygenFiller) world.getTileEntity(position.intX(), position.intY(), position.intZ()));
            
        }        
        return null;
    }
}
