/*
package galaxyspace.core.client.models;


import cpw.mods.fml.client.FMLClientHandler;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.planets.mars.client.model.ModelBalloonParachute;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class ModelFloater extends ModelBase {
    private static final ResourceLocation grayParachuteTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/model/parachute/gray.png");



    public void renderAll()
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ModelBalloonParachute.grayParachuteTexture);

        int i;

        for (i = 0; i < this.parachute.length; i++)
        {
            this.parachute[i].render(0.0625F);
        }

        for (i = 0; i < this.parachuteStrings.length; i++)
        {
            this.parachuteStrings[i].render(0.0625F);
        }

        this.parachute[0].rotateAngleY = (float) (0 * (Math.PI / 180F));
        this.parachute[2].rotateAngleY = (float) -(0 * (Math.PI / 180F));
        this.parachuteStrings[0].rotateAngleY = (float) (0 * (Math.PI / 180F));
        this.parachuteStrings[1].rotateAngleY = (float) (0 * (Math.PI / 180F));
        this.parachuteStrings[2].rotateAngleY = (float) -(0 * (Math.PI / 180F));
        this.parachuteStrings[3].rotateAngleY = (float) -(0 * (Math.PI / 180F));

        this.parachute[0].setRotationPoint(-5.85F, -11.0F, 2.0F);
        this.parachute[1].setRotationPoint(9F, -7F, 2.0F);
        this.parachute[2].setRotationPoint(-2.15F, 4.0F, 2.0F);
        this.parachute[0].rotateAngleZ = (float) (210F * (Math.PI / 180F));
        this.parachute[1].rotateAngleZ = (float) (180F * (Math.PI / 180F));
        this.parachute[2].rotateAngleZ = (float) -(210F * (Math.PI / 180F));
        this.parachuteStrings[0].rotateAngleZ = (float) ((155F + 180F) * (Math.PI / 180F));
        this.parachuteStrings[0].rotateAngleX = (float) (23F * (Math.PI / 180F));
        this.parachuteStrings[0].setRotationPoint(9.0F, 3.0F, 2.0F);
        this.parachuteStrings[1].rotateAngleZ = (float) ((155F + 180F) * (Math.PI / 180F));
        this.parachuteStrings[1].rotateAngleX = (float) -(23F * (Math.PI / 180F));
        this.parachuteStrings[1].setRotationPoint(9.0F, 3.0F, 2.0F);

        this.parachuteStrings[2].rotateAngleZ = (float) -((155F + 180F) * (Math.PI / 180F));
        this.parachuteStrings[2].rotateAngleX = (float) (23F * (Math.PI / 180F));
        this.parachuteStrings[2].setRotationPoint(9.0F, 3.0F, 2.0F);
        this.parachuteStrings[3].rotateAngleZ = (float) -((155F + 180F) * (Math.PI / 180F));
        this.parachuteStrings[3].rotateAngleX = (float) -(23F * (Math.PI / 180F));
        this.parachuteStrings[3].setRotationPoint(9.0F, 3.0F, 2.0F);
    }

    public void renderParachute()
    {
    }
}
*/
