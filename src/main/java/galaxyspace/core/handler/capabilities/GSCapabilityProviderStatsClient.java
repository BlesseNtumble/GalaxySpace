package galaxyspace.core.handler.capabilities;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class GSCapabilityProviderStatsClient implements ICapabilityProvider
{
    private EntityPlayerSP owner;
    private StatsCapabilityClient statsCapability;

    public GSCapabilityProviderStatsClient(EntityPlayerSP owner)
    {
        this.owner = owner;
        this.statsCapability = GSCapabilityStatsHandler.GS_STATS_CAPABILITY_CLIENT.getDefaultInstance();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == GSCapabilityStatsHandler.GS_STATS_CAPABILITY_CLIENT;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (GSCapabilityStatsHandler.GS_STATS_CAPABILITY_CLIENT != null && capability == GSCapabilityStatsHandler.GS_STATS_CAPABILITY_CLIENT)
        {
            return GSCapabilityStatsHandler.GS_STATS_CAPABILITY_CLIENT.cast(statsCapability);
        }

        return null;
    }

}
