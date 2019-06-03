package galaxyspace.core.handler.capabilities;

import java.lang.ref.WeakReference;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class GSCapabilityProviderStats implements ICapabilitySerializable<NBTTagCompound> {
	private EntityPlayerMP owner;
	private IStatsCapability statsCapability;

	public GSCapabilityProviderStats(EntityPlayerMP owner) {
		this.owner = owner;
		this.statsCapability = GSCapabilityStatsHandler.GS_STATS_CAPABILITY.getDefaultInstance();
		this.statsCapability.setPlayer(new WeakReference<>(this.owner));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == GSCapabilityStatsHandler.GS_STATS_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (GSCapabilityStatsHandler.GS_STATS_CAPABILITY != null && capability == GSCapabilityStatsHandler.GS_STATS_CAPABILITY) {
			return GSCapabilityStatsHandler.GS_STATS_CAPABILITY.cast(statsCapability);
		}

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		statsCapability.saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		statsCapability.loadNBTData(nbt);
	}
}
