package galaxyspace.systems.SolarSystem.moons.europa.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEvolvedGuardian extends ModelBase
{
    ModelRenderer Block1;
    ModelRenderer Block2;
    ModelRenderer Block3;
    ModelRenderer Block4;
    ModelRenderer Block6;
    ModelRenderer Block7;
    ModelRenderer Block8;
    ModelRenderer Block5;
    ModelRenderer Block17;
    ModelRenderer Block15;
    ModelRenderer Block14;
    ModelRenderer Block13;
    ModelRenderer Block10;
    ModelRenderer Block9;
    ModelRenderer Block18;
    ModelRenderer Block19;
    ModelRenderer Block20;
    ModelRenderer Block21;
    ModelRenderer Block22;
    ModelRenderer Block23;
    ModelRenderer Block24;
    ModelRenderer Block25;
    ModelRenderer Block26;
    ModelRenderer Block27;
    ModelRenderer Block28;
    ModelRenderer Block29;
    ModelRenderer Block30;
    ModelRenderer Block31;
    ModelRenderer Block32;
    ModelRenderer Block33;
    ModelRenderer Block34;
    ModelRenderer Block35;
    ModelRenderer Block36;

    public ModelEvolvedGuardian()
    {
        this( 0.0f );
    }

    public ModelEvolvedGuardian( float par1 )
    {
        Block1 = new ModelRenderer( this, 808, 39 );
        Block1.setTextureSize( 1024, 512 );
        Block1.addBox( -10.5F, -14.5F, -14.5F, 29, 29, 29);
        Block1.setRotationPoint( 0F, -8F, 0F );
        Block2 = new ModelRenderer( this, 942, 166 );
        Block2.setTextureSize( 1024, 512 );
        Block2.addBox( 16F, -8F, -8F, 16, 16, 16);
        Block2.setRotationPoint( 0F, -8F, 0F );
        Block3 = new ModelRenderer( this, 942, 210 );
        Block3.setTextureSize( 1024, 512 );
        Block3.addBox( 14F, -8F, -8F, 20, 16, 16);
        Block3.setRotationPoint( 0F, -8F, 0F );
        Block4 = new ModelRenderer( this, 939, 242 );
        Block4.setTextureSize( 1024, 512 );
        Block4.addBox( 14F, -8F, -8F, 20, 16, 16);
        Block4.setRotationPoint( 0F, -8F, 0F );
        Block6 = new ModelRenderer( this, 872, 213 );
        Block6.setTextureSize( 1024, 512 );
        Block6.addBox( 30F, -3F, 10F, 26, 6, 6);
        Block6.setRotationPoint( 0F, -8F, 0F );
        Block7 = new ModelRenderer( this, 873, 201 );
        Block7.setTextureSize( 1024, 512 );
        Block7.addBox( 29F, -3F, -16F, 26, 6, 6);
        Block7.setRotationPoint( 0F, -8F, 0F );
        Block8 = new ModelRenderer( this, 814, 151 );
        Block8.setTextureSize( 1024, 512 );
        Block8.addBox( 58F, -1F, -1F, 26, 2, 2);
        Block8.setRotationPoint( 0F, -8F, 0F );
        Block5 = new ModelRenderer( this, 942, 198 );
        Block5.setTextureSize( 1024, 512 );
        Block5.addBox( 32F, -3F, -3F, 26, 6, 6);
        Block5.setRotationPoint( 0F, -8F, 0F );
        Block17 = new ModelRenderer( this, 873, 151 );
        Block17.setTextureSize( 1024, 512 );
        Block17.addBox( 70.5F, -23.5F, -1F, 5, 15, 2);
        Block17.setRotationPoint( 0F, -6F, 0F );
        Block15 = new ModelRenderer( this, 937, 242 );
        Block15.setTextureSize( 1024, 512 );
        Block15.addBox( 70.5F, -7F, -1F, 5, 2, 2);
        Block15.setRotationPoint( 0F, -8F, 0F );
        Block14 = new ModelRenderer( this, 976, 274 );
        Block14.setTextureSize( 1024, 512 );
        Block14.addBox( 68F, -5F, -1F, 10, 2, 2);
        Block14.setRotationPoint( 0F, -8F, 0F );
        Block13 = new ModelRenderer( this, 936, 274 );
        Block13.setTextureSize( 1024, 512 );
        Block13.addBox( 65F, -3F, -1F, 16, 2, 2);
        Block13.setRotationPoint( 0F, -8F, 0F );
        Block10 = new ModelRenderer( this, 793, 124 );
        Block10.setTextureSize( 1024, 512 );
        Block10.addBox( 94F, -47F, -18.8F, 26, 2, 2);
        Block10.setRotationPoint( -35.8989F, 38.23715F, 15.81319F );
        Block9 = new ModelRenderer( this, 789, 97 );
        Block9.setTextureSize( 1024, 512 );
        Block9.addBox( 89F, -42F, 17.2F, 26, 2, 2);
        Block9.setRotationPoint( -31.3139F, 33.17715F, -16.2734F );
        Block18 = new ModelRenderer( this, 821, 97 );
        Block18.setTextureSize( 1024, 512 );
        Block18.addBox( -8.5F, -16F, -12.5F, 25, 2, 25);
        Block18.setRotationPoint( 0F, -8F, 0F );
        Block19 = new ModelRenderer( this, 828, 124 );
        Block19.setTextureSize( 1024, 512 );
        Block19.addBox( -8.5F, 14F, -12.5F, 25, 2, 25);
        Block19.setRotationPoint( 0F, -8F, 0F );
        Block20 = new ModelRenderer( this, 924, 68 );
        Block20.setTextureSize( 1024, 512 );
        Block20.addBox( -8.5F, -12.5F, -16F, 25, 25, 2);
        Block20.setRotationPoint( 0F, -8F, 0F );
        Block21 = new ModelRenderer( this, 925, 95 );
        Block21.setTextureSize( 1024, 512 );
        Block21.addBox( -8.5F, -12.5F, 14F, 25, 25, 2);
        Block21.setRotationPoint( 0F, -8F, 0F );
        Block22 = new ModelRenderer( this, 888, 151 );
        Block22.setTextureSize( 1024, 512 );
        Block22.addBox( -12F, -12.5F, -12.5F, 2, 25, 25);
        Block22.setRotationPoint( 0F, -8F, 0F );
        Block23 = new ModelRenderer( this, 976, 0 );
        Block23.setTextureSize( 1024, 512 );
        Block23.addBox( -12F, -11F, -11F, 2, 22, 22);
        Block23.setRotationPoint( -2F, -8F, 0F );
        Block24 = new ModelRenderer( this, 928, 44 );
        Block24.setTextureSize( 1024, 512 );
        Block24.addBox( -5F, -11F, -18F, 22, 22, 2);
        Block24.setRotationPoint( -2F, -8F, 0F );
        Block25 = new ModelRenderer( this, 928, 20 );
        Block25.setTextureSize( 1024, 512 );
        Block25.addBox( -5F, -11F, 16F, 22, 22, 2);
        Block25.setRotationPoint( -2F, -8F, 0F );
        Block26 = new ModelRenderer( this, 932, 122 );
        Block26.setTextureSize( 1024, 512 );
        Block26.addBox( 20F, -11F, -11F, 2, 22, 22);
        Block26.setRotationPoint( -2F, -8F, 0F );
        Block27 = new ModelRenderer( this, 821, 48 );
        Block27.setTextureSize( 1024, 512 );
        Block27.addBox( 84F, -21F, 134F, 4, 8, 4);
        Block27.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block28 = new ModelRenderer( this, 805, 48 );
        Block28.setTextureSize( 1024, 512 );
        Block28.addBox( -138F, 30F, 63F, 4, 8, 4);
        Block28.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block29 = new ModelRenderer( this, 895, 44 );
        Block29.setTextureSize( 1024, 512 );
        Block29.addBox( 85F, 25F, 134F, 4, 8, 4);
        Block29.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block30 = new ModelRenderer( this, 981, 52 );
        Block30.setTextureSize( 1024, 512 );
        Block30.addBox( -139F, 75F, 63F, 4, 8, 4);
        Block30.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block31 = new ModelRenderer( this, 789, 48 );
        Block31.setTextureSize( 1024, 512 );
        Block31.addBox( 4F, -114F, 134F, 4, 8, 4);
        Block31.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block32 = new ModelRenderer( this, 773, 48 );
        Block32.setTextureSize( 1024, 512 );
        Block32.addBox( 4F, -68F, 134F, 4, 8, 4);
        Block32.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block33 = new ModelRenderer( this, 979, 10 );
        Block33.setTextureSize( 1024, 512 );
        Block33.addBox( -67F, -163F, -59F, 4, 8, 4);
        Block33.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block34 = new ModelRenderer( this, 873, 27 );
        Block34.setTextureSize( 1024, 512 );
        Block34.addBox( -67F, -118F, -59F, 4, 8, 4);
        Block34.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block35 = new ModelRenderer( this, 976, 44 );
        Block35.setTextureSize( 1024, 512 );
        Block35.addBox( -170F, -61F, -52F, 8, 4, 4);
        Block35.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
        Block36 = new ModelRenderer( this, 974, 2 );
        Block36.setTextureSize( 1024, 512 );
        Block36.addBox( -77F, -61F, 141F, 8, 4, 4);
        Block36.setRotationPoint( 140.4221F, 48.86702F, -65.33626F );
    }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
   {
        Block1.rotateAngleX = 0F;
        Block1.rotateAngleY = 0F;
        Block1.rotateAngleZ = 0F;
        Block1.renderWithRotation(par7);

        Block2.rotateAngleX = 0F;
        Block2.rotateAngleY = 0F;
        Block2.rotateAngleZ = 0F;
        Block2.renderWithRotation(par7);

        Block3.rotateAngleX = 0F;
        Block3.rotateAngleY = 0.7853982F;
        Block3.rotateAngleZ = 0F;
        Block3.renderWithRotation(par7);

        Block4.rotateAngleX = 0F;
        Block4.rotateAngleY = -0.7853982F;
        Block4.rotateAngleZ = 0F;
        Block4.renderWithRotation(par7);

        Block6.rotateAngleX = 0F;
        Block6.rotateAngleY = -0.2617994F;
        Block6.rotateAngleZ = 0F;
        Block6.renderWithRotation(par7);

        Block7.rotateAngleX = 0F;
        Block7.rotateAngleY = 0.2617994F;
        Block7.rotateAngleZ = 0F;
        Block7.renderWithRotation(par7);

        Block8.rotateAngleX = 0F;
        Block8.rotateAngleY = 0F;
        Block8.rotateAngleZ = 0F;
        Block8.renderWithRotation(par7);

        Block5.rotateAngleX = 0F;
        Block5.rotateAngleY = 0F;
        Block5.rotateAngleZ = 0F;
        Block5.renderWithRotation(par7);

        Block17.rotateAngleX = 0F;
        Block17.rotateAngleY = 0F;
        Block17.rotateAngleZ = 0F;
        Block17.renderWithRotation(par7);

        Block15.rotateAngleX = 0F;
        Block15.rotateAngleY = 0F;
        Block15.rotateAngleZ = 0F;
        Block15.renderWithRotation(par7);

        Block14.rotateAngleX = 0F;
        Block14.rotateAngleY = 0F;
        Block14.rotateAngleZ = 0F;
        Block14.renderWithRotation(par7);

        Block13.rotateAngleX = 0F;
        Block13.rotateAngleY = 0F;
        Block13.rotateAngleZ = 0F;
        Block13.renderWithRotation(par7);

        Block10.rotateAngleX = 0F;
        Block10.rotateAngleY = 0F;
        Block10.rotateAngleZ = 0F;
        Block10.renderWithRotation(par7);

        Block9.rotateAngleX = 0F;
        Block9.rotateAngleY = 0F;
        Block9.rotateAngleZ = 0F;
        Block9.renderWithRotation(par7);

        Block18.rotateAngleX = 0F;
        Block18.rotateAngleY = 0F;
        Block18.rotateAngleZ = 0F;
        Block18.renderWithRotation(par7);

        Block19.rotateAngleX = 0F;
        Block19.rotateAngleY = 0F;
        Block19.rotateAngleZ = 0F;
        Block19.renderWithRotation(par7);

        Block20.rotateAngleX = 0F;
        Block20.rotateAngleY = 0F;
        Block20.rotateAngleZ = 0F;
        Block20.renderWithRotation(par7);

        Block21.rotateAngleX = 0F;
        Block21.rotateAngleY = 0F;
        Block21.rotateAngleZ = 0F;
        Block21.renderWithRotation(par7);

        Block22.rotateAngleX = 0F;
        Block22.rotateAngleY = 0F;
        Block22.rotateAngleZ = 0F;
        Block22.renderWithRotation(par7);

        Block23.rotateAngleX = 0F;
        Block23.rotateAngleY = 0F;
        Block23.rotateAngleZ = 0F;
        Block23.renderWithRotation(par7);

        Block24.rotateAngleX = 0F;
        Block24.rotateAngleY = 0F;
        Block24.rotateAngleZ = 0F;
        Block24.renderWithRotation(par7);

        Block25.rotateAngleX = 0F;
        Block25.rotateAngleY = 0F;
        Block25.rotateAngleZ = 0F;
        Block25.renderWithRotation(par7);

        Block26.rotateAngleX = 0F;
        Block26.rotateAngleY = 0F;
        Block26.rotateAngleZ = 0F;
        Block26.renderWithRotation(par7);

        Block27.rotateAngleX = 0F;
        Block27.rotateAngleY = -1.570796F;
        Block27.rotateAngleZ = -0.7853982F;
        Block27.renderWithRotation(par7);

        Block28.rotateAngleX = 0F;
        Block28.rotateAngleY = 0F;
        Block28.rotateAngleZ = 0.7853982F;
        Block28.renderWithRotation(par7);

        Block29.rotateAngleX = 0F;
        Block29.rotateAngleY = -1.570796F;
        Block29.rotateAngleZ = -0.7853982F;
        Block29.renderWithRotation(par7);

        Block30.rotateAngleX = 0F;
        Block30.rotateAngleY = 0F;
        Block30.rotateAngleZ = 0.7853982F;
        Block30.renderWithRotation(par7);

        Block31.rotateAngleX = 0F;
        Block31.rotateAngleY = -1.570796F;
        Block31.rotateAngleZ = 0.7853982F;
        Block31.renderWithRotation(par7);

        Block32.rotateAngleX = 0F;
        Block32.rotateAngleY = -1.570796F;
        Block32.rotateAngleZ = 0.7853982F;
        Block32.renderWithRotation(par7);

        Block33.rotateAngleX = 0.7853981F;
        Block33.rotateAngleY = 1.570796F;
        Block33.rotateAngleZ = 0F;
        Block33.renderWithRotation(par7);

        Block34.rotateAngleX = 0.7853981F;
        Block34.rotateAngleY = 1.570796F;
        Block34.rotateAngleZ = 0F;
        Block34.renderWithRotation(par7);

        Block35.rotateAngleX = 0F;
        Block35.rotateAngleY = 0.7853982F;
        Block35.rotateAngleZ = 0F;
        Block35.renderWithRotation(par7);

        Block36.rotateAngleX = 0F;
        Block36.rotateAngleY = -0.7853982F;
        Block36.rotateAngleZ = 0F;
        Block36.renderWithRotation(par7);

    }

}
