package galaxyspace.core.client.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;

public class GuiGif {
	public int frames;
    public BufferedImage[] images;
    public int[] imagesGLIDs;
    public boolean errored;
    private int currentFrame = 0;
    protected int x;
    protected int y;
    private int x2;
    private int y2;
    public static final int PIXEL_FORMAT_RGBA = 4;
    
    public GuiGif(int x, int y, int x2, int y2) {
    	this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        InputStream is = null;
        try {
            int i;
            is = this.getClass().getResourceAsStream("/assets/galaxyspace/textures/gui/test.gif");
            ImageInputStream stream = ImageIO.createImageInputStream(is);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
            if (!readers.hasNext()) {
                throw new IOException("No suitable reader found for image");
            }
            ImageReader reader = readers.next();
            reader.setInput(stream);
            this.frames = reader.getNumImages(true);
            BufferedImage[] images = new BufferedImage[this.frames];
            this.imagesGLIDs = new int[this.frames];
            for (i = 0; i < this.frames; ++i) {
                images[i] = reader.read(i);
            }
            reader.dispose();
            for (i = 0; i < this.frames; ++i) {
                this.imagesGLIDs[i] = this.loadTexture(images[i]);
            }
        }
        catch (IOException e) {
            try {
                e.printStackTrace();
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly(is);
                throw throwable;
            }
            IOUtils.closeQuietly((InputStream)is);
        }
        IOUtils.closeQuietly((InputStream)is);
    }
    
    public void increaseFrame() {
        this.currentFrame = this.currentFrame != this.frames - 1 ? ++this.currentFrame : 0;
    }
    
    private int loadTexture(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer((int)(image.getWidth() * image.getHeight() * 4));
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 255));
                buffer.put((byte)(pixel >> 8 & 255));
                buffer.put((byte)(pixel & 255));
                buffer.put((byte)(pixel >> 24 & 255));
            }
        }
        buffer.flip();
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)textureID);
        GL11.glTexParameteri((int)3553, (int)10242, (int)33071);
        GL11.glTexParameteri((int)3553, (int)10243, (int)33071);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)image.getWidth(), (int)image.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)buffer);
        return textureID;
    }
    
    public void draw() {
    	GL11.glPushMatrix();
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture((int)3553, (int)this.imagesGLIDs[this.currentFrame]);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.0f);
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2f((float)this.x, (float)this.y);
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2f((float)this.x2, (float)this.y);
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2f((float)this.x2, (float)this.y2);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2f((float)this.x, (float)this.y2);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}
