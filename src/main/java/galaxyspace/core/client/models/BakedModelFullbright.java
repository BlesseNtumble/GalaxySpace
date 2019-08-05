package galaxyspace.core.client.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;

public class BakedModelFullbright extends BakedModelDelegate {
	
	private static VertexFormat format = new VertexFormat(DefaultVertexFormats.BLOCK).addElement(DefaultVertexFormats.TEX_2S);
	
	
	private String texture, layer;
	private int bright;
	private double alpha;
	private TextureAtlasSprite sprite;
	private List<BakedQuad> cache;
	
    public BakedModelFullbright(IBakedModel base, String texture, int bright, double color) {
        super(base);
       // this.texture = texture;
        this.bright = bright;
        this.alpha = color;
        sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());       
    }

    private void putVertex(UnpackedBakedQuad.Builder builder, IBlockState state, Vec3d normal, double x, double y, double z, TextureAtlasSprite sprite, float u, float v, boolean hasBrightness, int brightness, Vec3d color)
	{
    	
		for (int e = 0; e < format.getElementCount(); e++)
		{
			switch (format.getElement(e).getUsage())
			{
				case POSITION:
					builder.put(e, (float) x, (float) y, (float) z);
					break;
				case COLOR:
					builder.put(e, (float)color.x, (float)color.y, (float)color.z, 1.0F);
					break;
				case UV:
					if (format.getElement(e).getIndex() == 1)
					{
						if (hasBrightness)
						{
							float blockLight = ((float) ((brightness >> 4) & 15) * 32) / 65535;
							float skyLight = ((float) ((brightness >> 20) & 15) * 32) / 65535;
							builder.put(e, blockLight, skyLight);
						}
						else
						{
							builder.put(e);
						}
					}
					else
					{
						u = sprite.getInterpolatedU(u);
						v = sprite.getInterpolatedV(v);
						builder.put(e, u, v);
					}
					break;
				case NORMAL:
					if (hasBrightness)
					{
						builder.put(e, 0.0F, 1.0F, 0.0F);
					}
					else
					{
						builder.put(e,(float) normal.x, (float) normal.y, (float) normal.z);
					}
					break;
				default:
					builder.put(e);
					break;
			}
		}
	}
    
    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {    	
    	
        if (state == null) {
            return base.getQuads(state, side, rand);
        }
               
        List<BakedQuad> quads = new ArrayList<BakedQuad>(12);

        quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 0.0D), this.base.getParticleTexture(), false, 0, new Vec3d(1.0D, 1.0D, 1.0D), 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 0.0D), this.base.getParticleTexture(), false, 0, new Vec3d(1.0D, 1.0D, 1.0D), 0));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), this.base.getParticleTexture(), false, 0, new Vec3d(1.0D, 1.0D, 1.0D), 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), this.base.getParticleTexture(), false, 0, new Vec3d(1.0D, 1.0D, 1.0D), 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D), this.base.getParticleTexture(), false, 0, new Vec3d(1.0D, 1.0D, 1.0D), 0));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 0.0D, 1.0D), this.base.getParticleTexture(), false, 0, new Vec3d(1.0D, 1.0D, 1.0D), 0));


		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 0.0D), sprite, true, this.bright, new Vec3d(this.alpha, this.alpha, this.alpha), 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 0.0D), sprite, true, this.bright, new Vec3d(this.alpha, this.alpha, this.alpha), 0));
		
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), sprite, true, this.bright, new Vec3d(this.alpha, this.alpha, this.alpha), 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), sprite, true, this.bright, new Vec3d(this.alpha, this.alpha, this.alpha), 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D), sprite, true, this.bright, new Vec3d(this.alpha, this.alpha, this.alpha), 0));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 0.0D, 1.0D), sprite, true, this.bright, new Vec3d(this.alpha, this.alpha, this.alpha), 0));

        return quads;

    }

    
    private BakedQuad createQuad(IBlockState state, Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, boolean hasBrightness, int brightness, Vec3d color, int tint)
	{
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();		
		
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);

		builder.setContractUVs(true);
		builder.setTexture(sprite);
		builder.setQuadTint(tint);
		
		
		putVertex(builder, state, normal, v1.x, v1.y, v1.z, sprite, 16, 16, hasBrightness, brightness, color);
		putVertex(builder, state, normal, v2.x, v2.y, v2.z, sprite, 16, 0, hasBrightness, brightness, color);
		putVertex(builder, state, normal, v3.x, v3.y, v3.z, sprite, 0, 0, hasBrightness, brightness, color);
		putVertex(builder, state, normal, v4.x, v4.y, v4.z, sprite, 0, 16, hasBrightness, brightness, color);
		//builder.setApplyDiffuseLighting(false);
		return builder.build();
	}
      
    
    public static boolean isLightMapDisabled() {
        return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
    }

    public static VertexFormat getFormatWithLightMap(VertexFormat formatdef) {
        if (isLightMapDisabled()) {
            return formatdef;
        }

        if (formatdef == DefaultVertexFormats.BLOCK) {
            return DefaultVertexFormats.BLOCK;
        } else if (formatdef == DefaultVertexFormats.ITEM) {
            return format;
        } else if (!formatdef.hasUvOffset(1)) {
            VertexFormat result = new VertexFormat(formatdef);

            result.addElement(DefaultVertexFormats.TEX_2S);

            return result;
        }

        return formatdef;
    }

}