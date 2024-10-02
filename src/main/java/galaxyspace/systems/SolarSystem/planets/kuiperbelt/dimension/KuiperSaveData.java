package galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class KuiperSaveData extends WorldSavedData
{
    public static final String saveDataID = "GSKuiperData";
    public NBTTagCompound datacompound;
    private NBTTagCompound alldata;

    public KuiperSaveData(String s)
    {
        super(KuiperSaveData.saveDataID);
        this.datacompound = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.datacompound = nbt.getCompoundTag("kuiper");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("kuiper", this.datacompound);
    }
}
