package galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class KuiperBeltSaveData extends WorldSavedData
{
    public static final String saveDataID = Constants.GCDATAFOLDER + "GSKuiperBeltData";
    public NBTTagCompound datacompound;

    public KuiperBeltSaveData(String s)
    {
        super(KuiperBeltSaveData.saveDataID);
        this.datacompound = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.datacompound = nbt.getCompoundTag("kuiperbelt");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("kuiperbelt", this.datacompound);
        return nbt;
    }
}
