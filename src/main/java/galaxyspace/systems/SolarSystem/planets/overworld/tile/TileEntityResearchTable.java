package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import micdoodle8.mods.galacticraft.core.tile.TileEntityAdvanced;
import net.minecraft.util.EnumFacing;

public class TileEntityResearchTable extends TileEntityAdvanced{

	public TileEntityResearchTable()
	{
		super("tile.research_table.name");
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0};
	}

	@Override
	public double getPacketRange() {
		return 0;
	}

	@Override
	public int getPacketCooldown() {
		return 0;
	}

	@Override
	public boolean isNetworkedTile() {
		return false;
	}
}
