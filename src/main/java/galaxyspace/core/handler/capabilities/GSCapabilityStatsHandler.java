package galaxyspace.core.handler.capabilities;

import galaxyspace.GalaxySpace;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class GSCapabilityStatsHandler {
	@CapabilityInject(StatsCapabilityClient.class)
	public static Capability<StatsCapabilityClient> GS_STATS_CAPABILITY_CLIENT = null;
	
	@CapabilityInject(StatsCapability.class)
	public static Capability<StatsCapability> GS_STATS_CAPABILITY = null;

	public static final ResourceLocation GS_PLAYER_PROPERTIES_CLIENT = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "player_stats_client");
	public static final ResourceLocation GS_PLAYER_PROPERTIES = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "player_stats");

	public static void register() {
		CapabilityManager.INSTANCE.register(StatsCapability.class, new Capability.IStorage<StatsCapability>() {
			@Override
			public NBTBase writeNBT(Capability<StatsCapability> capability, StatsCapability instance, EnumFacing side) {
				NBTTagCompound nbt = new NBTTagCompound();
                instance.saveNBTData(nbt);
                return nbt;
			}

			@Override
			public void readNBT(Capability<StatsCapability> capability, StatsCapability instance, EnumFacing side, NBTBase nbt) {
				instance.loadNBTData((NBTTagCompound) nbt);
			}
		}, GSStatsCapability::new);
		
		CapabilityManager.INSTANCE.register(StatsCapabilityClient.class, new Capability.IStorage<StatsCapabilityClient>()
        {
            @Override
            public NBTBase writeNBT(Capability<StatsCapabilityClient> capability, StatsCapabilityClient instance, EnumFacing side)
            {
                return null;
            }

            @Override
            public void readNBT(Capability<StatsCapabilityClient> capability, StatsCapabilityClient instance, EnumFacing side, NBTBase nbt) { }
        }, GSStatsCapabilityClient::new);
	}
}