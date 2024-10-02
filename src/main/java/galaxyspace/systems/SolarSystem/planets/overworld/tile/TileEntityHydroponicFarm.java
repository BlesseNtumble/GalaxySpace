package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import net.minecraft.block.Block;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHydroponicFarm extends TileEntity {
   public Block plant = null;
   public int metaplant = 0;
   public int tier;

   public TileEntityHydroponicFarm(int tier) {
      this.tier = tier;
   }

   public int getTier() {
      return this.tier;
   }

   public void setPlant(Block stack) {
      this.plant = stack;
   }

   public Block getPlant() {
      return this.plant;
   }

   public void setMetaPlant(int meta) {
      this.metaplant = meta;
   }

   public int getMetaPlant() {
      return this.metaplant;
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      super.onDataPacket(net, packet);
      this.setPlant(this.plant);
   }
}
