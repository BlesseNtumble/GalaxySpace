package galaxyspace.core.client.render.entity;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEvolvedColdBlaze extends RenderLiving<EntityEvolvedColdBlaze>
{
	private static final ResourceLocation mobTextures = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/model/evolved_coldblaze.png");

	public RenderEvolvedColdBlaze(RenderManager renderManagerIn)
	{
		super(renderManagerIn, new ModelBlaze(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEvolvedColdBlaze entity) {
		return mobTextures;
	}

}
