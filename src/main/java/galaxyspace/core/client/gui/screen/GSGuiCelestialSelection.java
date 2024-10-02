package galaxyspace.core.client.gui.screen;

import com.google.common.collect.Maps;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.Event;
import galaxyspace.GalaxySpace;
import galaxyspace.api.BodiesHelper;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.util.astronomy.AstronomyUtil;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.network.IPacket;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GSGuiCelestialSelection
extends GuiCelestialSelection {
    public List<CelestialBody> possibleBodiesGS;
    private int posYSS = 1;
    private int timer = 0;
    private int traveltime = 0;
    private boolean check = false;
    private Random rand;
    private String galaxy = "galaxy." + BodiesHelper.Galaxies.MILKYWAY.getName();
    private int tierneed = -1;
    public int currenttier = 0;
    private int coef;
    private int fuelRocketLevel = 0;
    private int fuelSet = 0;
    private boolean showStarList = false;
    private boolean showGalaxyList = false;
    private boolean enableNewTierSystem = GSConfigCore.enableNewTierSystem;
    private List<AstronomyUtil.PathsSolarSystems> paths = new ArrayList<AstronomyUtil.PathsSolarSystems>();
    private List<String> galaxylist = new ArrayList<String>();
    private HashMap<SolarSystem, Integer> starlist = new LinkedHashMap<SolarSystem, Integer>();
    protected int canCreateOffset = 0;
    protected int animateGrandchildren = 0;
    private double isometx = GSConfigCore.enable2DGalaxyMap ? 0.0 : 55.0;
    private double isometz = 45.0;
    private boolean hideInfo = false;
    private boolean small_mode;
    private int small_page;
    private double xImgOffset;
    private double yImgOffset;
    private Vector3 nebula_color;
    private int nebula_img;
    public static ResourceLocation guiImg = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/galaxymap_1.png");
    public static ResourceLocation guiMain2 = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialselection2.png");

    public GSGuiCelestialSelection(boolean mapMode, List<CelestialBody> possibleBodies, Integer[] stats) {
        super(mapMode, possibleBodies);
        this.small_mode = Minecraft.getMinecraft().gameSettings.guiScale == 0;
        this.small_page = 0;
        this.xImgOffset = 0.0;
        this.yImgOffset = 0.0;
        this.nebula_color = new Vector3(1.0, 1.0, 1.0);
        this.nebula_img = 0;
        if (stats != null) {
            this.currenttier = stats[0];
            this.coef = GSConfigCore.speedTimeTravel + stats[0];
            this.fuelRocketLevel = stats[1];
        }
        int l = 0;
        for (SolarSystem solarSystem : GalaxyRegistry.getRegisteredSolarSystems().values()) {
            if (!this.galaxylist.contains(solarSystem.getUnlocalizedParentGalaxyName())) {
                this.galaxylist.add(solarSystem.getUnlocalizedParentGalaxyName());
            }
            if (!solarSystem.getUnlocalizedParentGalaxyName().equals(this.galaxy)) continue;
            this.starlist.put(solarSystem, ++l);
        }
        Random rand = new Random();
        this.nebula_img = rand.nextInt(2);
        this.nebula_color = new Vector3((double)rand.nextFloat(), (double)rand.nextFloat(), (double)rand.nextFloat());
    }

    public void initGui() {
        super.initGui();
    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void setImgBackground(ResourceLocation galaxy) {
        GL11.glPushMatrix();
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
        GL11.glTranslated((double)-40.0, (double)-40.0, (double)0.0);
        GL11.glTranslated((double)this.xImgOffset, (double)this.yImgOffset, (double)0.0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(galaxy);
        int width = Display.getWidth();
        int height = Display.getHeight();
        this.drawTexturedModalRect(0.0f, 0.0f, width, height, 0.0f, 0.0f, width * 2, height * 2, false, false, 1024.0f, 1024.0f);
        ResourceLocation guiImg_2 = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/galaxymap_nebula_" + this.nebula_img + ".png");
        this.mc.renderEngine.bindTexture(guiImg_2);
        GL11.glTranslated((double)(this.xImgOffset * 1.5), (double)(this.yImgOffset * 1.5), (double)0.0);
        GL11.glColor4f((float)0.5f, (float)0.4f, (float)this.nebula_color.floatZ(), (float)0.5f);
        this.drawTexturedModalRect(0.0f, 0.0f, width, height, 0.0f, 0.0f, 1024.0f, 1024.0f, false, false, 1024.0f, 1024.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public void setBlackBackground() {
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        Tessellator var3 = Tessellator.instance;
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        var3.startDrawingQuads();
        var3.addVertex(0.0, (double)this.height, -90.0);
        var3.addVertex((double)this.width, (double)this.height, -90.0);
        var3.addVertex((double)this.width, 0.0, -90.0);
        var3.addVertex(0.0, 0.0, -90.0);
        var3.draw();
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void drawGrid(float gridSize, float gridScale) {
        GL11.glColor4f((float)0.0f, (float)0.2f, (float)0.5f, (float)0.55f);
        GL11.glBegin((int)1);
        for (float v = -(gridSize += gridScale / 2.0f); v <= gridSize; v += gridScale) {
            GL11.glVertex3f((float)v, (float)(-gridSize), (float)-0.0f);
            GL11.glVertex3f((float)v, (float)gridSize, (float)-0.0f);
            GL11.glVertex3f((float)(-gridSize), (float)v, (float)-0.0f);
            GL11.glVertex3f((float)gridSize, (float)v, (float)-0.0f);
        }
        GL11.glEnd();
    }

    protected Vector3f getCelestialBodyPosition(CelestialBody cBody) {
        if (cBody instanceof Star) {
            if (cBody.getUnlocalizedName().equalsIgnoreCase("star.sol")) {
                return new Vector3f();
            }
            return ((Star)cBody).getParentSolarSystem().getMapPosition().toVector3f();
        }
        int cBodyTicks = this.celestialBodyTicks.get(cBody) == null ? 1 : (Integer)this.celestialBodyTicks.get(cBody);
        float timeScale = cBody instanceof Planet ? 200.0f : 2.0f;
        float distanceFromCenter = this.getScale(cBody);
        Vector3f cBodyPos = new Vector3f((float)Math.sin((float)cBodyTicks / (timeScale * cBody.getRelativeOrbitTime()) + cBody.getPhaseShift()) * distanceFromCenter, (float)Math.cos((float)cBodyTicks / (timeScale * cBody.getRelativeOrbitTime()) + cBody.getPhaseShift()) * distanceFromCenter, 0.0f);
        if (cBody instanceof Planet) {
            Vector3f parentVec = this.getCelestialBodyPosition((CelestialBody)((Planet)cBody).getParentSolarSystem().getMainStar());
            return Vector3f.add((Vector3f)cBodyPos, (Vector3f)parentVec, null);
        }
        if (cBody instanceof IChildBody) {
            Vector3f parentVec = this.getCelestialBodyPosition((CelestialBody)((IChildBody)cBody).getParentPlanet());
            return Vector3f.add((Vector3f)cBodyPos, (Vector3f)parentVec, null);
        }
        if (cBody instanceof Satellite) {
            Vector3f parentVec = this.getCelestialBodyPosition((CelestialBody)((Satellite)cBody).getParentPlanet());
            return Vector3f.add((Vector3f)cBodyPos, (Vector3f)parentVec, null);
        }
        return cBodyPos;
    }

    public void drawCircles() {
        CelestialBodyRenderEvent.CelestialRingRenderEvent.Post postEvent;
        int i;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glLineWidth((float)2.0f);
        int count = 0;
        float theta = 0.06981317f;
        float cos = (float)Math.cos(0.06981316953897476);
        float sin = (float)Math.sin(0.06981316953897476);
        for (Planet planet : GalaxyRegistry.getRegisteredPlanets().values()) {
            if (planet.getParentSolarSystem() == null || !planet.getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(this.galaxy)) continue;
            Vector3f systemOffset = this.getCelestialBodyPosition((CelestialBody)planet.getParentSolarSystem().getMainStar());
            float alpha = 1.0f;
            if (this.selectedBody instanceof IChildBody && ((IChildBody)this.selectedBody).getParentPlanet() != planet || this.selectedBody instanceof Planet && this.selectedBody != planet && this.selectionCount >= 2) {
                alpha = this.lastSelectedBody == null && !(this.selectedBody instanceof IChildBody) && !(this.selectedBody instanceof Satellite) ? 1.0f - Math.min((float)this.ticksSinceSelection / 45.0f, 1.0f) : 0.0f;
            }
            if (this.selectedBody != planet && this.selectionCount == 0) {
                alpha = 1.0f - Math.min(-this.zoom, 1.0f);
            }
            if (alpha == 0.0f) continue;
            switch (count % 2) {
                case 0: {
                    GL11.glColor4f((float)(planet.getRingColorR() / 1.4f), (float)(planet.getRingColorG() / 1.4f), (float)(planet.getRingColorB() / 1.4f), (float)(alpha / 1.4f));
                    break;
                }
                case 1: {
                    GL11.glColor4f((float)planet.getRingColorR(), (float)planet.getRingColorG(), (float)planet.getRingColorB(), (float)alpha);
                }
            }
            CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre((CelestialBody)planet, systemOffset);
            MinecraftForge.EVENT_BUS.post((Event)preEvent);
            float x = this.getScale((CelestialBody)planet);
            float y = 0.0f;
            if (!preEvent.isCanceled()) {
                GL11.glTranslatef((float)systemOffset.x, (float)systemOffset.y, (float)systemOffset.z);
                GL11.glBegin((int)2);
                for (i = 0; i < 90; ++i) {
                    GL11.glVertex2f((float)x, (float)y);
                    float temp = x;
                    x = cos * x - sin * y;
                    y = sin * temp + cos * y;
                }
                GL11.glEnd();
                GL11.glTranslatef((float)(-systemOffset.x), (float)(-systemOffset.y), (float)(-systemOffset.z));
                ++count;
            }
            postEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Post((CelestialBody)planet);
            MinecraftForge.EVENT_BUS.post((Event)postEvent);
        }
        count = 0;
        if (this.selectedBody != null) {
            float alpha;
            float x;
            Vector3f planetPos = this.getCelestialBodyPosition(this.selectedBody);
            if (this.selectedBody instanceof IChildBody) {
                planetPos = this.getCelestialBodyPosition((CelestialBody)((IChildBody)this.selectedBody).getParentPlanet());
            } else if (this.selectedBody instanceof Satellite) {
                planetPos = this.getCelestialBodyPosition((CelestialBody)((Satellite)this.selectedBody).getParentPlanet());
            }
            GL11.glTranslatef((float)planetPos.x, (float)planetPos.y, (float)0.0f);
            for (Moon moon : GalaxyRegistry.getRegisteredMoons().values()) {
                if ((moon.getParentPlanet() != this.selectedBody || this.selectionCount < 2) && moon != this.selectedBody && !this.getSiblings(this.selectedBody).contains(moon)) continue;
                x = this.getScale((CelestialBody)moon);
                float y = 0.0f;
                alpha = 1.0f;
                if (this.selectionCount >= 2) {
                    float f = alpha = this.selectedBody instanceof IChildBody ? 1.0f : Math.min(Math.max((float)(this.ticksSinceSelection - 30) / 15.0f, 0.0f), 1.0f);
                    if (this.lastSelectedBody instanceof Moon && GalaxyRegistry.getMoonsForPlanet((Planet)((Moon)this.lastSelectedBody).getParentPlanet()).contains(moon)) {
                        alpha = 1.0f;
                    }
                }
                if (alpha == 0.0f) continue;
                switch (count % 2) {
                    case 0: {
                        GL11.glColor4f((float)(moon.getRingColorR() / 1.4f), (float)(moon.getRingColorG() / 1.4f), (float)(moon.getRingColorB() / 1.4f), (float)alpha);
                        break;
                    }
                    case 1: {
                        GL11.glColor4f((float)moon.getRingColorR(), (float)moon.getRingColorG(), (float)moon.getRingColorB(), (float)alpha);
                    }
                }
                CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre((CelestialBody)moon, new Vector3f(0.0f, 0.0f, 0.0f));
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                if (!preEvent.isCanceled()) {
                    GL11.glBegin((int)2);
                    for (i = 0; i < 90; ++i) {
                        GL11.glVertex2f((float)x, (float)y);
                        float temp = x;
                        x = cos * x - sin * y;
                        y = sin * temp + cos * y;
                    }
                    GL11.glEnd();
                    ++count;
                }
                postEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Post((CelestialBody)moon);
                MinecraftForge.EVENT_BUS.post((Event)postEvent);
            }
            for (Satellite satellite : GalaxyRegistry.getRegisteredSatellites().values()) {
                if (this.currenttier <= 0 || !this.possibleBodies.contains(satellite) || (satellite.getParentPlanet() != this.selectedBody || this.selectionCount == 1 || this.ticksSinceSelection <= 24) && satellite != this.selectedBody && !(this.lastSelectedBody instanceof IChildBody)) continue;
                x = this.getScale((CelestialBody)satellite);
                float y = 0.0f;
                alpha = 1.0f;
                if (this.selectionCount >= 2) {
                    float f = alpha = this.selectedBody instanceof IChildBody ? 1.0f : Math.min(Math.max((float)(this.ticksSinceSelection - 30) / 15.0f, 0.0f), 1.0f);
                    if (this.lastSelectedBody instanceof Satellite && GalaxyRegistry.getSatellitesForCelestialBody((CelestialBody)((Satellite)this.lastSelectedBody).getParentPlanet()).contains(satellite)) {
                        alpha = 1.0f;
                    }
                }
                if (alpha == 0.0f) continue;
                switch (count % 2) {
                    case 0: {
                        GL11.glColor4f((float)satellite.getRingColorR(), (float)satellite.getRingColorG(), (float)satellite.getRingColorB(), (float)alpha);
                        break;
                    }
                    case 1: {
                        GL11.glColor4f((float)satellite.getRingColorR(), (float)satellite.getRingColorG(), (float)satellite.getRingColorB(), (float)alpha);
                    }
                }
                CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre((CelestialBody)satellite, new Vector3f(0.0f, 0.0f, 0.0f));
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                if (!preEvent.isCanceled()) {
                    GL11.glBegin((int)2);
                    for (i = 0; i < 90; ++i) {
                        GL11.glVertex2f((float)x, (float)y);
                        float temp = x;
                        x = cos * x - sin * y;
                        y = sin * temp + cos * y;
                    }
                    GL11.glEnd();
                    ++count;
                }
                postEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Post((CelestialBody)satellite);
                MinecraftForge.EVENT_BUS.post((Event)postEvent);
            }
        }
        GL11.glLineWidth((float)1.0f);
    }

    protected float getScale(CelestialBody celestialBody) {
        return 3.0f * celestialBody.getRelativeDistanceFromCenter().unScaledDistance * (celestialBody instanceof Planet ? 25.0f : 0.2f);
    }

    public void drawScreen(int mousePosX, int mousePosY, float partialTicks) {
        block8: {
            Matrix4f worldMatrix;
            float wheel;
            if (Mouse.hasWheel() && (wheel = (float)Mouse.getDWheel() / (this.selectedBody == null ? 500.0f : 250.0f)) != 0.0f) {
                if (this.selectedBody == null || this.selectionState == GuiCelestialSelection.EnumSelectionState.PREVIEW && this.selectionCount < 2) {
                    this.zoom = Math.min(Math.max(this.zoom + wheel * (this.zoom + 2.0f) / 10.0f, -1.0f), 3.0f);
                } else {
                    this.planetZoom = Math.min(Math.max(this.planetZoom + wheel, -11.0f), 5.0f);
                }
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            Matrix4f camMatrix = new Matrix4f();
            Matrix4f.translate((Vector3f)new Vector3f(0.0f, 0.0f, -9000.0f), (Matrix4f)camMatrix, (Matrix4f)camMatrix);
            Matrix4f viewMatrix = new Matrix4f();
            viewMatrix.m00 = 2.0f / (float)this.width;
            viewMatrix.m11 = 2.0f / (float)(-this.height);
            viewMatrix.m22 = -2.2222222E-4f;
            viewMatrix.m30 = -1.0f;
            viewMatrix.m31 = 1.0f;
            viewMatrix.m32 = -2.0f;
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            FloatBuffer fb = BufferUtils.createFloatBuffer((int)512);
            fb.rewind();
            viewMatrix.store(fb);
            fb.flip();
            GL11.glMultMatrix((FloatBuffer)fb);
            fb.clear();
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            fb.rewind();
            camMatrix.store(fb);
            fb.flip();
            fb.clear();
            GL11.glMultMatrix((FloatBuffer)fb);
            this.setBlackBackground();
            ResourceLocation galaxy = BodiesHelper.galaxiesImg.get(this.galaxy.replace("galaxy.", "")) != null ? new ResourceLocation(BodiesHelper.galaxiesImg.get(this.galaxy.replace("galaxy.", ""))) : new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":textures/gui/galaxy/" + this.galaxy.toLowerCase() + ".png");
            if (GSConfigCore.enableImgOnGalaxyMap) {
                this.setImgBackground(guiImg);
            }
            GL11.glPushMatrix();
            this.mainWorldMatrix = worldMatrix = this.setIsometric(partialTicks);
            float gridSize = 7000.0f;
            if (!GSConfigCore.enableImgOnGalaxyMap) {
                this.drawGrid(gridSize, (float)(this.height / 3) / 3.5f);
            }
            this.drawGyperCorridor();
            this.drawCircles();
            GL11.glPopMatrix();
            HashMap<CelestialBody, Matrix4f> matrixMap = this.drawCelestialBodies(worldMatrix);
            this.planetPosMap.clear();
            for (Map.Entry<CelestialBody, Matrix4f> e : matrixMap.entrySet()) {
                Matrix4f planetMatrix = e.getValue();
                Matrix4f matrix0 = Matrix4f.mul((Matrix4f)viewMatrix, (Matrix4f)planetMatrix, (Matrix4f)planetMatrix);
                int x = (int)Math.floor(((double)matrix0.m30 * 0.5 + 0.5) * (double)Minecraft.getMinecraft().displayWidth);
                int y = (int)Math.floor((double)Minecraft.getMinecraft().displayHeight - ((double)matrix0.m31 * 0.5 + 0.5) * (double)Minecraft.getMinecraft().displayHeight);
                Vector2f vec = new Vector2f((float)x, (float)y);
                Matrix4f scaleVec = new Matrix4f();
                scaleVec.m00 = matrix0.m00;
                scaleVec.m11 = matrix0.m11;
                scaleVec.m22 = matrix0.m22;
                Vector4f newVec = Matrix4f.transform((Matrix4f)scaleVec, (Vector4f)new Vector4f(2.0f, -2.0f, 0.0f, 0.0f), null);
                float iconSize = newVec.y * ((float)Minecraft.getMinecraft().displayHeight / 2.0f) * (float)(e.getKey() instanceof Star ? 2 : 1) * (e.getKey() == this.selectedBody ? 1.5f : 1.0f);
                this.planetPosMap.put(e.getKey(), new Vector3f(vec.x, vec.y, iconSize));
            }
            this.drawSelectionCursor(fb, worldMatrix);
            try {
                this.drawButtons(mousePosX, mousePosY);
            }
            catch (Exception e) {
                if (this.errorLogged) break block8;
                this.errorLogged = true;
                GCLog.severe((String)"Problem identifying planet or dimension in an add on for Galacticraft!");
                GCLog.severe((String)"(The problem is likely caused by a dimension ID conflict.  Check configs for dimension clashes.  You can also try disabling Mars space station in configs.)");
                e.printStackTrace();
            }
        }
        this.drawBorder();
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
    }

    public Matrix4f setIsometric(float partialTicks) {
        float zoomLocal;
        Matrix4f mat0 = new Matrix4f();
        Matrix4f.translate((Vector3f)new Vector3f((float)this.width / 2.0f, (float)this.height / 2.0f, 0.0f), (Matrix4f)mat0, (Matrix4f)mat0);
        Matrix4f.rotate((float)((float)Math.toRadians(this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)mat0, (Matrix4f)mat0);
        Matrix4f.rotate((float)((float)Math.toRadians(-this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)mat0, (Matrix4f)mat0);
        this.zoom = zoomLocal = this.getZoomAdvanced();
        Matrix4f.scale((Vector3f)new Vector3f(1.1f + zoomLocal, 1.1f + zoomLocal, 1.1f + zoomLocal), (Matrix4f)mat0, (Matrix4f)mat0);
        Vector2f cBodyPos = this.getTranslationAdvanced(partialTicks);
        this.position = this.getTranslationAdvanced(partialTicks);
        Matrix4f.translate((Vector3f)new Vector3f(-cBodyPos.x, -cBodyPos.y, 0.0f), (Matrix4f)mat0, (Matrix4f)mat0);
        FloatBuffer fb = BufferUtils.createFloatBuffer((int)16);
        fb.rewind();
        mat0.store(fb);
        fb.flip();
        GL11.glMultMatrix((FloatBuffer)fb);
        return mat0;
    }

    public HashMap<CelestialBody, Matrix4f> drawCelestialBodies(Matrix4f worldMatrix) {
        CelestialBodyRenderEvent.Post postEvent;
        Matrix4f worldMatrix1;
        Matrix4f worldMatrix0;
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        FloatBuffer fb = BufferUtils.createFloatBuffer((int)512);
        HashMap matrixMap = Maps.newHashMap();
        for (SolarSystem solarSystem : GalaxyRegistry.getRegisteredSolarSystems().values()) {
            Star star = solarSystem.getMainStar();
            if (star == null || star.getBodyIcon() == null || !star.getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(this.galaxy)) continue;
            GL11.glPushMatrix();
            Matrix4f worldMatrix02 = new Matrix4f(worldMatrix);
            Matrix4f.translate((Vector3f)this.getCelestialBodyPosition((CelestialBody)star), (Matrix4f)worldMatrix02, (Matrix4f)worldMatrix02);
            Matrix4f worldMatrix12 = new Matrix4f();
            Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix12, (Matrix4f)worldMatrix12);
            Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix12, (Matrix4f)worldMatrix12);
            worldMatrix12 = Matrix4f.mul((Matrix4f)worldMatrix02, (Matrix4f)worldMatrix12, (Matrix4f)worldMatrix12);
            fb.rewind();
            worldMatrix12.store(fb);
            fb.flip();
            GL11.glMultMatrix((FloatBuffer)fb);
            float alpha = 1.0f;
            if (alpha != 0.0f) {
                CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre((CelestialBody)star, star.getBodyIcon(), 8);
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
                if (preEvent.celestialBodyTexture != null) {
                    this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                }
                if (!preEvent.isCanceled()) {
                    int size = GSGuiCelestialSelection.getWidthForCelestialBodyStatic((CelestialBody)star);
                    boolean xOffset = false;
                    boolean yOffset = false;
                    GL11.glPushMatrix();
                    GL11.glShadeModel((int)7425);
                    GL11.glEnable((int)2848);
                    GL11.glHint((int)3154, (int)4354);
                    GL11.glDisable((int)3553);
                    float r = 1.0f;
                    float g = 1.0f;
                    float b = 0.8f;
                    float a = 0.5f;
                    float f10 = size * 4;
                    BodiesHelper.BodiesData data = BodiesHelper.data.get(star);
                    if (data != null && data.getClassPlanet() != null) {
                        if (data.getClassPlanet().contains(BodiesHelper.brown)) {
                            r = 0.8f;
                            g = 0.4f;
                            b = 0.2f;
                            a = 0.5f;
                        } else if (data.getClassPlanet().contains(BodiesHelper.red)) {
                            r = 0.9f;
                            g = 0.0f;
                            b = 0.0f;
                            a = 0.7f;
                        } else if (data.getClassPlanet().contains(BodiesHelper.orange)) {
                            r = 1.0f;
                            g = 0.4f;
                            b = 0.0f;
                            a = 0.7f;
                        } else if (data.getClassPlanet().contains(BodiesHelper.yellow)) {
                            r = 1.0f;
                            g = 0.8f;
                            b = 0.5f;
                            a = 0.7f;
                        } else if (data.getClassPlanet().contains(BodiesHelper.white)) {
                            r = 1.0f;
                            g = 1.0f;
                            b = 1.0f;
                            a = 0.7f;
                        } else if (data.getClassPlanet().contains(BodiesHelper.lightblue)) {
                            r = 0.4f;
                            g = 0.7f;
                            b = 1.0f;
                            a = 0.7f;
                        }
                    }
                    float xSize = size / 2 - 4;
                    float ySize = size / 2 - 4;
                    GL11.glColor4f((float)r, (float)g, (float)b, (float)a);
                    GL11.glDisable((int)2884);
                    GL11.glBegin((int)6);
                    GL11.glVertex2d((double)(xSize /= 4.0f), (double)(ySize /= 4.0f));
                    for (int angle = 0; angle <= 360; angle += 45) {
                        if (angle % 120 == 0) {
                            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
                        }
                        double x = (double)f10 * Math.cos((double)angle * Math.PI / 180.0);
                        double y = (double)f10 * Math.sin((double)angle * Math.PI / 180.0);
                        GL11.glVertex2d((double)(x + (double)xSize), (double)(y + (double)ySize));
                    }
                    GL11.glEnd();
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)2848);
                    GL11.glDisable((int)3008);
                    GL11.glDisable((int)3042);
                    GL11.glPopMatrix();
                    if (star == this.selectedBody && this.selectionCount == 1) {
                        size /= 2;
                        size *= 3;
                    }
                    this.drawTexturedModalRect1(-size / 2, -size / 2, size, size, 0.0f, 0.0f, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                    matrixMap.put(star, worldMatrix12);
                }
                CelestialBodyRenderEvent.Post postEvent2 = new CelestialBodyRenderEvent.Post((CelestialBody)star);
                MinecraftForge.EVENT_BUS.post((Event)postEvent2);
            }
            if (this.selectedBody == null) {
                GL11.glPushMatrix();
                GL11.glScalef((float)0.8f, (float)0.8f, (float)0.8f);
                String str = solarSystem.getMainStar().getLocalizedName();
                this.fontRendererObj.drawString(str, 10, -5, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                GL11.glPopMatrix();
            }
            fb.clear();
            GL11.glPopMatrix();
        }
        for (Planet planet : GalaxyRegistry.getRegisteredPlanets().values()) {
            if (planet.getBodyIcon() == null || !planet.getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(this.galaxy)) continue;
            GL11.glPushMatrix();
            worldMatrix0 = new Matrix4f(worldMatrix);
            Matrix4f.translate((Vector3f)this.getCelestialBodyPosition((CelestialBody)planet), (Matrix4f)worldMatrix0, (Matrix4f)worldMatrix0);
            worldMatrix1 = new Matrix4f();
            Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            worldMatrix1 = Matrix4f.mul((Matrix4f)worldMatrix0, (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            fb.rewind();
            worldMatrix1.store(fb);
            fb.flip();
            GL11.glMultMatrix((FloatBuffer)fb);
            float alpha = 1.0f;
            if (this.selectedBody instanceof IChildBody && ((IChildBody)this.selectedBody).getParentPlanet() != planet || this.selectedBody instanceof Planet && this.selectedBody != planet && this.selectionCount >= 2) {
                alpha = this.lastSelectedBody == null && !(this.selectedBody instanceof IChildBody) ? 1.0f - Math.min((float)this.ticksSinceSelection / 25.0f, 1.0f) : 0.0f;
            }
            if (alpha != 0.0f) {
                if (this.selectedBody == null) {
                    GL11.glPushMatrix();
                    GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                    String str = planet.getLocalizedName();
                    this.fontRendererObj.drawString(str, 4 + GSGuiCelestialSelection.getWidthForCelestialBodyStatic((CelestialBody)planet), -5, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    GL11.glPopMatrix();
                }
                CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre((CelestialBody)planet, planet.getBodyIcon(), 12);
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
                if (preEvent.celestialBodyTexture != null) {
                    this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                }
                if (!preEvent.isCanceled()) {
                    int size = GSGuiCelestialSelection.getWidthForCelestialBodyStatic((CelestialBody)planet);
                    this.drawTexturedModalRect1(-size / 2, -size / 2, size, size, 0.0f, 0.0f, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                    matrixMap.put(planet, worldMatrix1);
                }
                postEvent = new CelestialBodyRenderEvent.Post((CelestialBody)planet);
                MinecraftForge.EVENT_BUS.post((Event)postEvent);
            }
            fb.clear();
            GL11.glPopMatrix();
        }
        for (Moon moon : GalaxyRegistry.getRegisteredMoons().values()) {
            if (moon.getBodyIcon() == null || this.selectionCount >= 2 || !moon.getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(this.galaxy)) continue;
            GL11.glPushMatrix();
            worldMatrix0 = new Matrix4f(worldMatrix);
            Matrix4f.translate((Vector3f)this.getCelestialBodyPosition((CelestialBody)moon), (Matrix4f)worldMatrix0, (Matrix4f)worldMatrix0);
            worldMatrix1 = new Matrix4f();
            Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            Matrix4f.scale((Vector3f)new Vector3f(0.25f, 0.25f, 1.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            worldMatrix1 = Matrix4f.mul((Matrix4f)worldMatrix0, (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
            fb.rewind();
            worldMatrix1.store(fb);
            fb.flip();
            GL11.glMultMatrix((FloatBuffer)fb);
            float alpha = 1.0f;
            if (this.selectedBody instanceof IChildBody && (IChildBody)this.selectedBody != moon || this.selectedBody instanceof Moon && this.selectedBody != moon && this.selectionCount >= 2) {
                alpha = this.lastSelectedBody == null && !(this.selectedBody instanceof IChildBody) ? 1.0f - Math.min((float)this.ticksSinceSelection / 25.0f, 1.0f) : 0.0f;
            }
            if (alpha != 0.0f) {
                CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre((CelestialBody)moon, moon.getBodyIcon(), 8);
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
                if (preEvent.celestialBodyTexture != null) {
                    this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                }
                if (!preEvent.isCanceled()) {
                    int size = GSGuiCelestialSelection.getWidthForCelestialBodyStatic((CelestialBody)moon);
                    this.drawTexturedModalRect1(-size / 2, -size / 2, size, size, 0.0f, 0.0f, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                    matrixMap.put(moon, worldMatrix1);
                }
                postEvent = new CelestialBodyRenderEvent.Post((CelestialBody)moon);
                MinecraftForge.EVENT_BUS.post((Event)postEvent);
            }
            fb.clear();
            GL11.glPopMatrix();
        }
        if (this.selectedBody != null) {
            Matrix4f worldMatrix03 = new Matrix4f(worldMatrix);
            for (Moon moon : GalaxyRegistry.getRegisteredMoons().values()) {
                if (!((moon == this.selectedBody || moon.getParentPlanet() == this.selectedBody) && (this.selectionCount != 1 && this.ticksSinceSelection > 35 || this.selectionCount == 1 && this.ticksSinceSelection > 2 || this.selectedBody == moon || this.lastSelectedBody instanceof Moon && GalaxyRegistry.getMoonsForPlanet((Planet)((Moon)this.lastSelectedBody).getParentPlanet()).contains(moon))) && !this.getSiblings(this.selectedBody).contains(moon)) continue;
                GL11.glPushMatrix();
                worldMatrix1 = new Matrix4f(worldMatrix03);
                Matrix4f.translate((Vector3f)this.getCelestialBodyPosition((CelestialBody)moon), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                Matrix4f worldMatrix2 = new Matrix4f();
                Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                Matrix4f.scale((Vector3f)new Vector3f(0.25f, 0.25f, 1.0f), (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                worldMatrix2 = Matrix4f.mul((Matrix4f)worldMatrix1, (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                fb.rewind();
                worldMatrix2.store(fb);
                fb.flip();
                GL11.glMultMatrix((FloatBuffer)fb);
                GL11.glPushMatrix();
                CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre((CelestialBody)moon, moon.getBodyIcon(), 8);
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                if (preEvent.celestialBodyTexture != null) {
                    this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                }
                if (!preEvent.isCanceled()) {
                    int size = GSGuiCelestialSelection.getWidthForCelestialBodyStatic((CelestialBody)moon);
                    this.drawTexturedModalRect1(-size / 2, -size / 2, size, size, 0.0f, 0.0f, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                    matrixMap.put(moon, worldMatrix1);
                }
                postEvent = new CelestialBodyRenderEvent.Post((CelestialBody)moon);
                MinecraftForge.EVENT_BUS.post((Event)postEvent);
                GL11.glPopMatrix();
                if (this.selectionCount == 2) {
                    GL11.glPushMatrix();
                    GL11.glScalef((float)0.4f, (float)0.4f, (float)0.3f);
                    String str = moon.getLocalizedName();
                    this.fontRendererObj.drawString(str, 5, -5, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    GL11.glPopMatrix();
                }
                fb.clear();
                GL11.glPopMatrix();
            }
            worldMatrix03 = new Matrix4f(worldMatrix);
            for (Satellite satellite : GalaxyRegistry.getRegisteredSatellites().values()) {
                if (this.currenttier <= 0 || !this.possibleBodies.contains(satellite) || satellite != this.selectedBody && (satellite.getParentPlanet() != this.selectedBody || this.selectionCount == 1) || this.ticksSinceSelection <= 35 && this.selectedBody != satellite && (!(this.lastSelectedBody instanceof Satellite) || !GalaxyRegistry.getSatellitesForCelestialBody((CelestialBody)((Satellite)this.lastSelectedBody).getParentPlanet()).contains(satellite))) continue;
                GL11.glPushMatrix();
                worldMatrix1 = new Matrix4f(worldMatrix03);
                Matrix4f.translate((Vector3f)this.getCelestialBodyPosition((CelestialBody)satellite), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                Matrix4f worldMatrix2 = new Matrix4f();
                Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                Matrix4f.scale((Vector3f)new Vector3f(0.25f, 0.25f, 1.0f), (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                worldMatrix2 = Matrix4f.mul((Matrix4f)worldMatrix1, (Matrix4f)worldMatrix2, (Matrix4f)worldMatrix2);
                fb.rewind();
                worldMatrix2.store(fb);
                fb.flip();
                GL11.glMultMatrix((FloatBuffer)fb);
                CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre((CelestialBody)satellite, satellite.getBodyIcon(), 8);
                MinecraftForge.EVENT_BUS.post((Event)preEvent);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                if (!preEvent.isCanceled()) {
                    int size = GSGuiCelestialSelection.getWidthForCelestialBodyStatic((CelestialBody)satellite);
                    this.drawTexturedModalRect(-size / 2, -size / 2, size, size, 0.0f, 0.0f, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                    matrixMap.put(satellite, worldMatrix1);
                }
                postEvent = new CelestialBodyRenderEvent.Post((CelestialBody)satellite);
                MinecraftForge.EVENT_BUS.post((Event)postEvent);
                fb.clear();
                GL11.glPopMatrix();
            }
        }
        return matrixMap;
    }

    public void drawGyperCorridor() {
        for (AstronomyUtil.PathsSolarSystems list : this.paths) {
            Vector3 vec1 = list.getStartSystem().getMapPosition();
            Vector3 vec2 = list.getEndSystem().getMapPosition();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)1);
            GL11.glVertex3f((float)vec1.floatX(), (float)vec1.floatY(), (float)vec1.floatZ());
            GL11.glVertex3f((float)vec2.floatX(), (float)vec2.floatY(), (float)vec2.floatZ());
            GL11.glEnd();
            GL11.glLineWidth((float)1.0f);
        }
    }

    public void drawTexturedModalRect1(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY) {
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
        float texModX = 1.0f / texSizeX;
        float texModY = 1.0f / texSizeY;
        Tessellator tessellator = Tessellator.instance;
        float height0 = invertY ? 0.0f : vHeight;
        float height1 = invertY ? vHeight : 0.0f;
        float width0 = invertX ? uWidth : 0.0f;
        float width1 = invertX ? 0.0f : uWidth;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), (double)this.zLevel, (double)((u + width0) * texModX), (double)((v + height0) * texModY));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((u + width1) * texModX), (double)((v + height0) * texModY));
        tessellator.addVertexWithUV((double)(x + width), (double)y, (double)this.zLevel, (double)((u + width1) * texModX), (double)((v + height1) * texModY));
        tessellator.addVertexWithUV((double)x, (double)y, (double)this.zLevel, (double)((u + width0) * texModX), (double)((v + height1) * texModY));
        tessellator.draw();
    }

    protected void drawSelectionCursor(FloatBuffer fb, Matrix4f worldMatrix) {
        switch (this.selectionCount) {
            case 1: {
                if (this.selectedBody == null) break;
                GL11.glPushMatrix();
                Matrix4f worldMatrix0 = new Matrix4f(worldMatrix);
                Matrix4f.translate((Vector3f)this.getCelestialBodyPosition(this.selectedBody), (Matrix4f)worldMatrix0, (Matrix4f)worldMatrix0);
                Matrix4f worldMatrix1 = new Matrix4f();
                Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                worldMatrix1 = Matrix4f.mul((Matrix4f)worldMatrix0, (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                fb.rewind();
                worldMatrix1.store(fb);
                fb.flip();
                GL11.glMultMatrix((FloatBuffer)fb);
                fb.clear();
                GL11.glPushMatrix();
                GL11.glScalef((float)0.06666667f, (float)0.06666667f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                float colMod = this.getZoomAdvanced() < 4.9f ? (float)(Math.sin((float)this.ticksSinceSelection / 2.0f) * 0.5 + 0.5) : 1.0f;
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)(1.0f * colMod));
                int width = GSGuiCelestialSelection.getWidthForCelestialBodyStatic(this.selectedBody);
                if (width % 2 != 0) {
                    ++width;
                }
                if (this.selectionCount == 1) {
                    width /= 2;
                    width *= 3;
                }
                this.drawTexturedModalRect(-(width *= 10), -width, width * 2, width * 2, 266, 29, 100, 100, false, false);
                GL11.glPopMatrix();
                if (this.selectedBody instanceof Star) {
                    GL11.glScalef((float)0.8f, (float)0.8f, (float)0.8f);
                    String str = this.selectedBody.getLocalizedName();
                    this.fontRendererObj.drawString(str, 15 + GSGuiCelestialSelection.getWidthForCelestialBodyStatic(this.selectedBody), -5, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                } else if (this.selectedBody instanceof Planet) {
                    GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                    String str = this.selectedBody.getLocalizedName();
                    this.fontRendererObj.drawString(str, 10 + GSGuiCelestialSelection.getWidthForCelestialBodyStatic(this.selectedBody), -5, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                } else if (this.selectedBody instanceof IChildBody) {
                    GL11.glScalef((float)0.15f, (float)0.15f, (float)0.15f);
                    String str = this.selectedBody.getLocalizedName();
                    this.fontRendererObj.drawString(str, 12 + GSGuiCelestialSelection.getWidthForCelestialBodyStatic(this.selectedBody), -5, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                }
                GL11.glPopMatrix();
                break;
            }
            case 2: {
                if (this.selectedBody == null) break;
                GL11.glPushMatrix();
                Matrix4f worldMatrix0 = new Matrix4f(worldMatrix);
                Matrix4f.translate((Vector3f)this.getCelestialBodyPosition(this.selectedBody), (Matrix4f)worldMatrix0, (Matrix4f)worldMatrix0);
                Matrix4f worldMatrix1 = new Matrix4f();
                Matrix4f.rotate((float)((float)Math.toRadians(this.isometz)), (Vector3f)new Vector3f(0.0f, 0.0f, 1.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                Matrix4f.rotate((float)((float)Math.toRadians(-this.isometx)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                worldMatrix1 = Matrix4f.mul((Matrix4f)worldMatrix0, (Matrix4f)worldMatrix1, (Matrix4f)worldMatrix1);
                fb.rewind();
                worldMatrix1.store(fb);
                fb.flip();
                GL11.glMultMatrix((FloatBuffer)fb);
                fb.clear();
                float div = this.zoom + 1.0f - this.planetZoom;
                float scale = Math.max(0.3f, 1.5f / ((float)this.ticksSinceSelection / 5.0f)) * 2.0f / div;
                div = Math.max(div, 1.0E-4f);
                GL11.glScalef((float)scale, (float)scale, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                float colMod = this.getZoomAdvanced() < 4.9f ? (float)(Math.sin((float)this.ticksSinceSelection / 1.0f) * 0.5 + 0.5) : 1.0f;
                GL11.glColor4f((float)0.4f, (float)0.8f, (float)1.0f, (float)(1.0f * colMod));
                this.drawTexturedModalRect(-50, -50, 100, 100, 266, 29, 100, 100, false, false);
                GL11.glPopMatrix();
                break;
            }
        }
    }

    public static int getWidthForCelestialBodyStatic(CelestialBody celestialBody) {
        float size = celestialBody.getRelativeSize();
        if (size > 5.0f) {
            size = 5.0f;
        }
        if ((double)size < 0.9 && celestialBody instanceof IChildBody) {
            size = 1.0f;
        }
        if (Minecraft.getMinecraft().currentScreen instanceof GuiCelestialSelection && (celestialBody != ((GSGuiCelestialSelection)Minecraft.getMinecraft().currentScreen).selectedBody || ((GSGuiCelestialSelection)Minecraft.getMinecraft().currentScreen).selectionCount != 1)) {
            return (int)(celestialBody instanceof Star ? 8.0f * size : (celestialBody instanceof Planet ? 4.0f * size : (celestialBody instanceof IChildBody ? 2.0f * size : (float)(celestialBody instanceof Satellite ? 4 : 2))));
        }
        return (int)(celestialBody instanceof Star ? 12.0f * size : (celestialBody instanceof Planet ? 8.0f * size : (celestialBody instanceof IChildBody ? 6.0f * size : (float)(celestialBody instanceof Satellite ? 6 : 2))));
    }

    /*
     * Unable to fully structure code
     */
    public void drawButtons(int mousePosX, int mousePosY) {


       boolean handledSliderPos = false;

        block212: {
            block213: {
                if (this.coef < 1) {
                    this.coef = 1;
                }
                if (this.coef > 10) {
                    this.coef = 10;
                }
                if (!this.mapMode && this.ticksSinceMenuOpen % 1 == 0 && !this.mc.thePlayer.capabilities.isCreativeMode && this.timer < this.traveltime && this.check) {
                    this.timer += 1 * this.coef;
                }
                if (this.selectedBody != null && this.selectedBody.getTierRequirement() >= 0) {
                    float idcel = 0.0f;
                    SolarSystem star = GalacticraftCore.solarSystemSol;
                    double dist = 0.0;
                    if (this.selectedBody instanceof Planet) {
                        if (this.mc.thePlayer.worldObj.provider instanceof IGalacticraftWorldProvider) {
                            CelestialBody body = ((IGalacticraftWorldProvider)this.mc.thePlayer.worldObj.provider).getCelestialBody();
                            if (body instanceof Planet) {
                                star = ((Planet)body).getParentSolarSystem();
                                idcel = body.getRelativeDistanceFromCenter().unScaledDistance;
                                dist = Math.abs(Math.floor((idcel - this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance) * 100.0f)) / 100.0;
                            }
                            if (body instanceof IChildBody) {
                                star = ((IChildBody)body).getParentPlanet().getParentSolarSystem();
                                idcel = ((IChildBody)body).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance;
                                dist = Math.abs(Math.floor((idcel - this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance) * 100.0f)) / 100.0 + (double)(body.getRelativeDistanceFromCenter().unScaledDistance / 100.0f);
                            }
                        } else {
                            idcel = GalacticraftCore.planetOverworld.getRelativeDistanceFromCenter().unScaledDistance;
                            star = GalacticraftCore.solarSystemSol;
                            dist = Math.abs(Math.floor((idcel - this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance) * 100.0f)) / 100.0;
                        }
                        SolarSystem test = ((Planet)this.selectedBody).getParentSolarSystem();
                        double distothersystem = Math.floor(Vector3.distance((Vector3)star.getMapPosition(), (Vector3)test.getMapPosition()) / 100.0) * 100.0 - Math.abs(Math.floor((idcel + this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance) * 100.0f));
                        double time = 0;
                        double v0 = time = star == test ? (double)Math.round(dist * 70.0) : distothersystem;
                        if (!star.getUnlocalizedParentGalaxyName().equals(test.getUnlocalizedParentGalaxyName())) {
                            time *= 100.0;
                        }
                        this.traveltime = (time < 0.0 ? 10 : (int)time) * 100;
                        for (int i = 0; i <= 6; ++i) {
                            if (!(dist >= 0.2 * (double)i) || !star.getUnlocalizedParentGalaxyName().equals(test.getUnlocalizedParentGalaxyName()) || !this.selectedBody.getReachable()) continue;
                            this.tierneed = star == test ? (i == 6 ? i : i + 1) : 6;
                        }
                        if (((Planet)this.selectedBody).getParentSolarSystem().getMainStar().getName().contains("omega")) {
                            this.tierneed = 7;
                        }
                        int fuelcount = 0;
                        this.fuelSet = (fuelcount = (int)Math.round((double)this.fuelRocketLevel - (double)this.fuelRocketLevel * (dist / (double)this.currenttier))) > 0 ? fuelcount : 0;
                    } else if (!(this.selectedBody instanceof Star) && this.selectedBody instanceof IChildBody) {
                        if (this.mc.thePlayer.worldObj.provider instanceof IGalacticraftWorldProvider) {
                            CelestialBody body = ((IGalacticraftWorldProvider)this.mc.thePlayer.worldObj.provider).getCelestialBody();
                            idcel = 0.0f;
                            if (body instanceof Planet) {
                                star = ((Planet)body).getParentSolarSystem();
                                idcel = body.getRelativeDistanceFromCenter().unScaledDistance;
                                dist = Math.abs(Math.floor((idcel - ((IChildBody)this.selectedBody).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance) * 100.0f)) / 100.0 + (double)(this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance / 100.0f);
                            } else if (body instanceof IChildBody) {
                                star = ((IChildBody)body).getParentPlanet().getParentSolarSystem();
                                idcel = ((IChildBody)body).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance;
                                dist = Math.abs(Math.floor((idcel - ((IChildBody)this.selectedBody).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance) * 100.0f)) / 100.0 + (double)(this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance / 100.0f);
                            }
                        } else {
                            idcel = GalacticraftCore.planetOverworld.getRelativeDistanceFromCenter().unScaledDistance;
                            star = GalacticraftCore.solarSystemSol;
                            dist = Math.abs(Math.floor((idcel - ((IChildBody)this.selectedBody).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance) * 100.0f)) / 100.0 + (double)(this.selectedBody.getRelativeDistanceFromCenter().unScaledDistance / 100.0f);
                        }
                        SolarSystem test = ((IChildBody)this.selectedBody).getParentPlanet().getParentSolarSystem();
                        double time = 0;
                        double v1 = time = star == test ? (double)Math.round(dist * 70.0) : Math.floor(Vector3.distance((Vector3)star.getMapPosition(), (Vector3)test.getMapPosition()) / 200.0) * Math.floor((((IChildBody)this.selectedBody).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance + idcel) * 100.0f);
                        if (!star.getUnlocalizedParentGalaxyName().equals(test.getUnlocalizedParentGalaxyName())) {
                            time *= 100.0;
                        }
                        this.traveltime = (int)time * 100;
                        for (int i = 0; i <= 6; ++i) {
                            if (!(dist >= 0.25 * (double)i) || !star.getUnlocalizedParentGalaxyName().equals(test.getUnlocalizedParentGalaxyName()) || !this.selectedBody.getReachable()) continue;
                            this.tierneed = star == test ? (i == 6 ? i : i + 1) : 6;
                        }
                        if (((IChildBody)this.selectedBody).getParentPlanet().getParentSolarSystem().getMainStar().getName().contains("omega")) {
                            this.tierneed = 7;
                        }
                        int fuelcount = 0;
                        this.fuelSet = (fuelcount = (int)Math.round((double)this.fuelRocketLevel - (double)this.fuelRocketLevel * (dist / (double)this.currenttier) * 1.2000000476837158)) > 0 ? fuelcount : 0;
                    }
                }
                this.zLevel = 0.0f;
                handledSliderPos = false;
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 74, 11, 0, 392, 148, 22, false, false);
                String str = GCCoreUtil.translate((String)"gui.message.catalog.name").toUpperCase();
                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 40 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 1, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                int scale = (int)Math.min(95.0f, (float)this.ticksSinceMenuOpen * 12.0f);
                boolean planetZoomedNotMoon = this.selectionCount == 2 && this.selectedParent instanceof Planet == false;
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH - 95 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 12, 95, 41, 0, 436, 95, 41, false, false);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                if (GalaxyRegistry.getRegisteredSolarSystems().size() > 1) {
                    this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 2 - 13 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 37, 5, 5, 110, 448, 5, 5, false, true);
                }
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                str = this.getParentName();
                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 9 - 95 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 34, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 2 - 95 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14, 93, 17, 95, 436, 93, 17, false, false);
                if (this.galaxylist.size() > 1) {
                    this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 2 - 13 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 19, 5, 5, 110, 448, 5, 5, false, true);
                }
                str = GCCoreUtil.translate((String)this.galaxy);
                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 7 - 95 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 16, ColorUtil.to32BitColor((int)255, (int)0, (int)255, (int)255));
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                if (this.showGalaxyList) {
                    for (int i = 0; i < this.galaxylist.size(); ++i) {
                        int y = 19;
                        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)((float)scale / 95.0f));
                        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 6 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14 * i + y, 86, 10, 0, 489, 86, 10, false, false);
                        GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)((float)scale / 95.0f));
                        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 5 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14 * i + y - 1, 93, 12, 95, 464, 93, 12, false, false);
                        str = GCCoreUtil.translate((String)this.galaxylist.get(i));
                        this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 10 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14 * i + y + 1, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                    }
                }
                if (this.showStarList) {
                    for (Map.Entry<SolarSystem, Integer> entry : this.starlist.entrySet()) {
                        int l = entry.getValue();
                        int y = 19;
                        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)((float)scale / 95.0f));
                        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 6 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14 * l + y, 86, 10, 0, 489, 86, 10, false, false);
                        GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)((float)scale / 95.0f));
                        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 5 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14 * l + y - 1, 93, 12, 95, 464, 93, 12, false, false);
                        str = entry.getKey().getMainStar().getLocalizedName();
                        this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 10 + scale, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 14 * l + y + 1, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                    }
                }
                List<CelestialBody> children = this.getChildren(planetZoomedNotMoon != false ? this.selectedBody : this.selectedParent);
                this.drawChildren(children, 0, 0, true);
                if (this.mapMode) {
                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                    GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 74, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 74, 11, 0, 392, 148, 22, true, false);
                    str = GCCoreUtil.translate((String)"gui.message.exit.name").toUpperCase();
                    this.fontRendererObj.drawString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 40 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 1, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                }
                int menuTopLeft1 = this.height - GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH - 22;
                int posX1 = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH - 53;
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                this.drawTexturedModalRect(posX1 + 53, menuTopLeft1 - 5, 40, 12, 0, 456, 40, 12, false, false);
                this.drawTexturedModalRect(posX1 + 93, menuTopLeft1 - 5, 45, 12, 50, 456, 45, 12, false, false);
                str = this.hideInfo != false ? "Enable Info" : "Disable Info";
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 20, menuTopLeft1 - 3, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                this.drawTexturedModalRect(posX1 + 53, menuTopLeft1 - 19, 40, 12, 0, 456, 40, 12, false, false);
                this.drawTexturedModalRect(posX1 + 93, menuTopLeft1 - 19, 45, 12, 50, 456, 45, 12, false, false);
                str = this.small_mode != false ? "Small Mode" : "Normal Mode";
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 20, menuTopLeft1 - 17, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                if (this.selectedBody == null) break block212;
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                if (this.selectedBody instanceof Satellite) {
                    Satellite selectedSatellite = (Satellite)this.selectedBody;
                    int stationListSize = ((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).size();
                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
                    int max = Math.min(this.height / 2 / 14, stationListSize);
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 95, 53, this.selectedStationOwner.length() == 0 ? 95 : 0, 186, 95, 53, false, false);
                    if (this.spaceStationListOffset <= 0) {
                        GL11.glColor4f((float)0.65f, (float)0.65f, (float)0.65f, (float)1.0f);
                    } else {
                        GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                    }
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 85, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 45, 61, 4, 0, 239, 61, 4, false, false);
                    if (max + this.spaceStationListOffset >= stationListSize) {
                        GL11.glColor4f((float)0.65f, (float)0.65f, (float)0.65f, (float)1.0f);
                    } else {
                        GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                    }
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 85, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 49 + max * 14, 61, 4, 0, 239, 61, 4, false, true);
                    GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                    if (((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(this.selectedStationOwner) == null) {
                        str = GCCoreUtil.translate((String)"gui.message.selectSS.name");
                        this.drawSplitString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 47, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 20, 91, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255), false, false);
                    } else {
                        str = GCCoreUtil.translate((String)"gui.message.ssOwner.name");
                        this.fontRendererObj.drawString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 85, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 18, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        str = this.selectedStationOwner;
                        this.smallFontRenderer.drawString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 47 - this.smallFontRenderer.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 30, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    }

                    Iterator<Map.Entry<String, StationDataGUI>> it = this.spaceStationMap.get(getSatelliteParentID(selectedSatellite)).entrySet().iterator(); int i = 0;
                    int j = 0;
                    while (it.hasNext() && i < max) {
                       Map.Entry<String, StationDataGUI> e = it.next();
                        if (j >= this.spaceStationListOffset) {
                            this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                            GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                            int xOffset = 0;
                            if (((String)e.getKey()).equalsIgnoreCase(this.selectedStationOwner)) {
                                xOffset -= 5;
                            }
                            this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95 + xOffset, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 50 + i * 14, 93, 12, 95, 464, 93, 12, true, false);
                            str = "";
                            String str0 = ((GuiCelestialSelection.StationDataGUI)e.getValue()).getStationName();
                            for (int point = 0; this.smallFontRenderer.getStringWidth(str) < 80 && point < str0.length(); ++point) {
                                str = str + str0.substring(point, point + 1);
                            }
                            if (this.smallFontRenderer.getStringWidth(str) >= 80) {
                                str = str.substring(0, str.length() - 3);
                                str = str + "...";
                            }
                            this.smallFontRenderer.drawString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 88 + xOffset, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 51 + i * 14, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                            ++i;
                        }
                        ++j;
                    }
                } else if (!this.hideInfo) {
                    int LHS = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH;
                    int RHS = this.width - LHS;
                    int TOP = LHS;
                    int BOT = this.height - LHS;
                    this.mc.renderEngine.bindTexture(GSGuiCelestialSelection.guiMain2);
                    GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                    int y = 100;
                    int sliderPos = this.zoomTooltipPos;
                    if (this.zoomTooltipPos != 132) {
                        this.zoomTooltipPos = sliderPos = Math.min(this.ticksSinceSelection * 8, 132);
                    }
                    int menuTopLeft = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y;
                    int posX = this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - sliderPos + 7;
                    int posX2 = this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 18;
                    this.mc.renderEngine.bindTexture(GSGuiCelestialSelection.guiMain2);
                    GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                    if (this.selectedBody instanceof Star || this.selectedBody.getTierRequirement() < 0) {
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 12, 133, 31, 0, 79, 266, 62, true, false);
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 43, 133, 37, 0, 442, 266, 70, true, false);
                    } else if (!this.small_mode) {
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 13, 133, 237, 0, 79, 266, 433, true, false);
                    } else {
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 13, 133, 80, 0, 79, 266, 142, true, false);
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 93, 133, 17, 0, 472, 266, 40, true, false);
                        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                        this.drawTexturedModalRect(posX + 5, menuTopLeft + 112, 30, 11, 0, 414, 60, 22, false, false);
                        this.drawTexturedModalRect(posX + 35, menuTopLeft + 112, 50, 11, 128, 414, 40, 22, false, false);
                        this.drawTexturedModalRect(posX + 85, menuTopLeft + 112, 30, 11, 128, 414, 60, 22, false, false);
                        this.fontRendererObj.drawString("<-", posX + 15, menuTopLeft + 114, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        this.fontRendererObj.drawString("Page: " + (this.small_page + 1), posX + 45, menuTopLeft + 113, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        this.fontRendererObj.drawString("->", posX + 96, menuTopLeft + 114, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        this.mc.renderEngine.bindTexture(GSGuiCelestialSelection.guiMain2);
                    }
                    y += 15;
                    str = GCCoreUtil.translate((String)"gui.message.generalinformation");
                    if (!this.small_mode) {
                        this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                    } else if (this.small_page == 0 || this.selectedBody instanceof Star) {
                        this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                    }
                    if (this.selectedBody.getTierRequirement() != -1 && !(this.selectedBody instanceof Star)) {
                        str = GCCoreUtil.translate((String)"gui.message.physicalparameters");
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y + 40, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        } else if (this.small_page == 0) {
                            this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y + 40, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.atmosphericparameters");
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y + 82, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.atmosphericcomponents");
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y + 166, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        } else if (this.small_page == 2) {
                            this.fontRendererObj.drawString(str, posX, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                        }
                    }
                    if (this.selectedBody instanceof Star || this.selectedBody.getTierRequirement() == -1) {
                        str = GCCoreUtil.translate((String)"gui.message.type") + " " + GCCoreUtil.translate((String)"gui.message.star");
                    }
                    if (this.selectedBody instanceof Planet && this.selectedBody.getTierRequirement() != -1) {
                        str = GCCoreUtil.translate((String)"gui.message.type") + " " + GCCoreUtil.translate((String)"gui.message.planet");
                    }
                    if (this.selectedBody instanceof Moon) {
                        str = GCCoreUtil.translate((String)"gui.message.type") + " " + GCCoreUtil.translate((String)"gui.message.moon");
                    }
                    if (this.selectedBody instanceof Satellite) {
                        str = GCCoreUtil.translate((String)"gui.message.type") + " " + GCCoreUtil.translate((String)"gui.message.satellite");
                    }
                    if (this.selectedBody.getTierRequirement() == -2) {
                        str = GCCoreUtil.translate((String)"gui.message.type") + " " + GCCoreUtil.translate((String)"gui.info.blackhole.name");
                    }
                    if (!this.small_mode) {
                        this.fontRendererObj.drawString(str, posX, TOP + y + 10, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    } else if (this.small_page == 0 || this.selectedBody instanceof Star) {
                        this.fontRendererObj.drawString(str, posX, TOP + y + 10, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    }
                    WorldProvider dim = WorldUtil.getProviderForDimensionClient((int)this.selectedBody.getDimensionID());
                    if (dim instanceof IGalacticraftWorldProvider) {
                        if (((IGalacticraftWorldProvider)dim).getCelestialBody() instanceof Planet) {
                            Planet curplanet = (Planet)((IGalacticraftWorldProvider)dim).getCelestialBody();
                            this.galaxy = curplanet.getParentSolarSystem().getUnlocalizedParentGalaxyName();
                        } else if (((IGalacticraftWorldProvider)dim).getCelestialBody() instanceof IChildBody) {
                            IChildBody curplanet = (IChildBody)((IGalacticraftWorldProvider)dim).getCelestialBody();
                            this.galaxy = curplanet.getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName();
                        }
                    }
                    str = GCCoreUtil.translate((String)"gui.message.class") + " " + AstronomyUtil.classPlanet(this.selectedBody, dim);
                    if (!this.small_mode) {
                        this.fontRendererObj.drawString(str, posX, TOP + y + 20, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    } else if (this.small_page == 0 || this.selectedBody instanceof Star) {
                        this.fontRendererObj.drawString(str, posX, TOP + y + 20, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                    }
                    if (this.selectedBody.getTierRequirement() != -1 && !(this.selectedBody instanceof Star)) {
                        str = GCCoreUtil.translate((String)"gui.message.gravity") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.gravity") + " 100%";
                        } else if (this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld) {
                            float grav = (0.1f - ((IGalacticraftWorldProvider)dim).getGravity()) * 1000.0f;
                            if (((IGalacticraftWorldProvider)dim).getGravity() == 0.0f) {
                                grav = 0.0f;
                            }
                            str = GCCoreUtil.translate((String)"gui.message.gravity") + " " + (int)grav + "%";
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 38 + 12, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 0) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 38 + 12, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.daylength") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.daylength") + " " + ((WorldProviderSpace)dim).getDayLength() / 1000L + ":" + ((WorldProviderSpace)dim).getDayLength() % 1000L + "h";
                        } else if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.daylength") + " 24:0h";
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 38 + 22, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 0) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 38 + 22, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.atmopressure") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (dim instanceof IAdvancedSpace) {
                            str = GCCoreUtil.translate((String)"gui.message.atmopressure") + " " + ((IAdvancedSpace)dim).AtmosphericPressure();
                        } else if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.atmopressure") + " " + 0;
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 78 + 14, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 10, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.temperature") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (this.selectedBody.getReachable()) {
                            float temp = 0.0f;
                            try {
                                temp = ((IGalacticraftWorldProvider)dim).getThermalLevelModifier();
                            }
                            catch (Exception var21_60) {
                                // empty catch block
                            }
                            temp = temp == 0.0f ? 40.0f : temp * 40.0f;
                            str = GCCoreUtil.translate((String)"gui.message.temperature") + " " + (int)temp + " C";
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 78 + 24, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 20, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.windenergy") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld && dim instanceof IAdvancedSpace) {
                            str = GCCoreUtil.translate((String)"gui.message.windenergy") + " " + (float)Math.round(((IAdvancedSpace)dim).getSolarWindMultiplier() * 1000.0) / 1000.0f;
                        } else if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.windenergy") + " " + -1.0;
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 76 + 36, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 30, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.solarenergy") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (dim instanceof ISolarLevel) {
                            float boost = (float)Math.round((((ISolarLevel)dim).getSolarEnergyMultiplier() - 1.0) * 1000.0) / 10.0f;
                            str = GCCoreUtil.translate((String)"gui.message.solarenergy") + " " + boost + "%";
                        }
                        if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.solarenergy") + " 100%";
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 76 + 48, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 40, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.windspeed") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (dim instanceof IGalacticraftWorldProvider) {
                            str = GCCoreUtil.translate((String)"gui.message.windspeed") + " " + ((IGalacticraftWorldProvider)dim).getWindLevel() + " W/l";
                        }
                        if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.windspeed") + " 1.0 W/l";
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 74 + 60, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 50, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.breathableatmo") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.breathableatmo") + " " + this.localeBoolean(((IGalacticraftWorldProvider)dim).hasBreathableAtmosphere());
                        }
                        if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.breathableatmo") + " " + this.localeBoolean(true);
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 77 + 99, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 2) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 10, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = GCCoreUtil.translate((String)"gui.message.solarradiation") + " " + GCCoreUtil.translate((String)"gui.message.unknown");
                        if (dim instanceof IAdvancedSpace) {
                            str = GCCoreUtil.translate((String)"gui.message.solarradiation") + " " + this.localeBoolean(((IAdvancedSpace)dim).SolarRadiation());
                        } else if (this.selectedBody == GalacticraftCore.planetOverworld) {
                            str = GCCoreUtil.translate((String)"gui.message.solarradiation") + " " + this.localeBoolean(false);
                        }
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 78 + 66, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 1) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 60, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                        str = this.selectedBody.getReachable() != false && this.selectedBody.atmosphere.isEmpty() == false ? this.selectedBody.atmosphere.toString() : GCCoreUtil.translate((String)"gui.message.noatmosphere");
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 75 + 121, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 2) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 30, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                    }
                    str = GCCoreUtil.translate((String)"gui.message.traveltime") + " " + this.traveltime;
                    if (this.selectedBody.getTierRequirement() != -1 && this.selectedBody.getReachable() && !this.mapMode) {
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 82 + 143, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 2) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 50, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                    }
                    str = GCCoreUtil.translate((String)"gui.message.fuelset") + " " + this.fuelSet;
                    if (this.selectedBody.getTierRequirement() != -1 && this.selectedBody.getReachable() && !this.mapMode) {
                        if (!this.small_mode) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 84 + 132, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.small_page == 2) {
                            this.fontRendererObj.drawString(str, posX, TOP + y + 60, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        }
                    }
                    if (!(this.selectedBody instanceof Moon)) {
                        if (this.selectedBody instanceof Star) {
                            int x = 0;
                            for (CelestialBody body : this.getChildren(this.selectedParent)) {
                                if (body.getTierRequirement() < 0) continue;
                                ++x;
                            }
                            str = GCCoreUtil.translate((String)"gui.message.planets") + " " + x;
                            this.fontRendererObj.drawString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - sliderPos + 7, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + y + 29 + 11, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                        } else if (this.selectedBody.getTierRequirement() != -1) {
                            str = GCCoreUtil.translate((String)"gui.message.moons") + " " + this.getChildren(this.selectedBody).size();
                            if (!this.small_mode) {
                                this.fontRendererObj.drawString(str, posX, TOP + y + 75 + 132, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                            } else if (this.small_page == 2) {
                                this.fontRendererObj.drawString(str, posX, TOP + y + 40, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                            }
                        }
                    }
                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                    GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                }
                BodiesHelper.BodiesData data = BodiesHelper.data.get(this.selectedBody);
                int LHS = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH;
                int RHS = this.width - LHS;
                int TOP = LHS;
                if (data != null && !data.getItemStacks().isEmpty()) {
                    int y = 99;
                    int sliderPos = this.zoomTooltipPos;
                    if (this.zoomTooltipPos != 125) {
                        this.zoomTooltipPos = sliderPos = Math.min(this.ticksSinceSelection * 8, 125);
                    }
                    int menuTopLeft = this.height - LHS - y;
                    int posX = RHS - sliderPos;
                    if (!this.small_mode) {
                        this.mc.renderEngine.bindTexture(GSGuiCelestialSelection.guiMain2);
                        GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 12, 133, 31, 0, 79, 266, 62, true, false);
                        this.drawTexturedModalRect(posX - 7, menuTopLeft + 43, 133, 37, 0, 462, 266, 50, true, false);
                    }
                    if (!this.small_mode) {
                        this.fontRendererObj.drawString(GCCoreUtil.translate((String)"gui.message.required_equipment.name"), posX, menuTopLeft + 15, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                    } else if (this.small_page == 3) {
                        this.fontRendererObj.drawString(GCCoreUtil.translate((String)"gui.message.required_equipment.name"), posX, menuTopLeft - 245, ColorUtil.to32BitColor((int)255, (int)150, (int)200, (int)255));
                    }
                    int i = 0;
                    for (ItemStack stacks : data.getItemStacks()) {
                        ItemStack getStack = new ItemStack(stacks.getItem(), 1, stacks.getItemDamage());
                        int amount = this.getAmountInInventory(stacks);
                        int xPos = -1000;
                        int yPos = -1000;
                        if (!this.small_mode) {
                            xPos = posX - 5 + i % 6 * 85 / 4 + 5;
                            yPos = this.height - TOP - 70 + (i > 0 && i % 6 == 0 ? 24 : 0);
                            RenderHelper.enableGUIStandardItemLighting();
                            GSGuiCelestialSelection.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, getStack.copy(), xPos, yPos);
                            RenderHelper.disableStandardItemLighting();
                        } else if (this.small_page == 3) {
                            xPos = posX - 5 + i % 6 * 85 / 4 + 5;
                            yPos = TOP + 155 + (i > 0 && i % 6 == 0 ? 24 : 0);
                            RenderHelper.enableGUIStandardItemLighting();
                            GSGuiCelestialSelection.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, getStack.copy(), xPos, yPos);
                            RenderHelper.disableStandardItemLighting();
                        }
                        if (mousePosX >= xPos && mousePosX <= xPos + 16 && mousePosY >= yPos && mousePosY <= yPos + 16) {
                            GL11.glDepthMask((boolean)true);
                            GL11.glEnable((int)2929);
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)300.0f);
                            int k = this.fontRendererObj.getStringWidth(stacks.getDisplayName());
                            int j2 = mousePosX - k / 2;
                            int k2 = mousePosY - 12;
                            int i1 = 8;
                            if (j2 + k > this.width) {
                                j2 -= j2 - this.width + k;
                            }
                            if (k2 + i1 + 6 > this.height) {
                                k2 = this.height - i1 - 6;
                            }
                            int j1 = ColorUtil.to32BitColor((int)190, (int)0, (int)153, (int)255);
                            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
                            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
                            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
                            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
                            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
                            int k1 = ColorUtil.to32BitColor((int)170, (int)0, (int)153, (int)255);
                            int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & -16777216;
                            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
                            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
                            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
                            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
                            this.fontRendererObj.drawString(stacks.getDisplayName(), j2, k2, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                            GL11.glPopMatrix();
                        }
                        boolean valid = amount >= stacks.stackSize;
                        str = "" + stacks.stackSize;
                        int color = (valid | this.mc.thePlayer.capabilities.isCreativeMode) != false ? ColorUtil.to32BitColor((int)255, (int)0, (int)255, (int)0) : ColorUtil.to32BitColor((int)255, (int)255, (int)0, (int)0);
                        this.fontRendererObj.drawString(str, xPos + 8 - this.fontRendererObj.getStringWidth(str) / 2, yPos + 15, color);
                        ++i;
                    }
                }
                if (this.check) {
                    this.drawTransitBar(this.getScaledTravelTime(138));
                }
                if (this.canCreateSpaceStation(this.selectedBody) && !(this.selectedBody instanceof Satellite)) {
                    GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
                    int canCreateLength = Math.max(0, this.drawSplitString(GCCoreUtil.translate((String)"gui.message.canCreateSpaceStation.name"), 0, 0, 91, 0, true, true) - 2);
                    this.canCreateOffset = canCreateLength * this.smallFontRenderer.FONT_HEIGHT;
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 93, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 16, 93, 4, 159, 102, 93, 4, false, false);
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 69, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 12, 61, 4, 0, 170, 61, 4, false, false);
                    for (int barY = 0; barY < canCreateLength; ++barY) {
                        this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + barY * this.smallFontRenderer.FONT_HEIGHT, 93, this.smallFontRenderer.FONT_HEIGHT, 159, 106, 93, this.smallFontRenderer.FONT_HEIGHT, false, false);
                    }
                    SpaceStationRecipe recipe = WorldUtil.getSpaceStationRecipe((int)this.selectedBody.getDimensionID());
                    int h = 1;
                    if (recipe != null) {
                        h = Math.round((float)Math.ceil((float)recipe.getRecipeSize() / 4.0f) - 1.0f) * 25;
                    }
                    this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 93, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 20 + this.canCreateOffset, 93, 43 + h, 159, 106, 93, 43, false, false);
                    if (recipe != null) {
                        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.1f, (float)1.0f);
                        boolean validInputMaterials = true;
                        int i = 0;
                        for (Map.Entry<Object, Integer> e : recipe.getInput().entrySet()) {
                           Object next = e.getKey();
                           int xPos = this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95 + i % 4 * 93 / 4 + 5;
                            int yPos = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 35 + this.canCreateOffset + (i > 0 && i % 4 == 0 ? 24 : 0);
                            if (next instanceof ItemStack) {
                                int amount = this.getAmountInInventory((ItemStack)next);
                                RenderHelper.enableGUIStandardItemLighting();
                                GuiCelestialSelection.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((ItemStack)next).copy(), xPos, yPos);
                                RenderHelper.disableStandardItemLighting();
                                GL11.glEnable((int)3042);
                                if (mousePosX >= xPos && mousePosX <= xPos + 16 && mousePosY >= yPos && mousePosY <= yPos + 16) {
                                    GL11.glDepthMask((boolean)true);
                                    GL11.glEnable((int)2929);
                                    GL11.glPushMatrix();
                                    GL11.glTranslatef((float)0.0f, (float)0.0f, (float)300.0f);
                                    int k = this.smallFontRenderer.getStringWidth(((ItemStack)next).getDisplayName());
                                    int j2 = mousePosX - k / 2;
                                    int k2 = mousePosY - 12;
                                    int i1 = 8;
                                    if (j2 + k > this.width) {
                                        j2 -= j2 - this.width + k;
                                    }
                                    if (k2 + i1 + 6 > this.height) {
                                        k2 = this.height - i1 - 6;
                                    }
                                    int j1 = ColorUtil.to32BitColor((int)190, (int)0, (int)153, (int)255);
                                    this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
                                    this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
                                    this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
                                    this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
                                    int k1 = ColorUtil.to32BitColor((int)170, (int)0, (int)153, (int)255);
                                    int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & -16777216;
                                    this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
                                    this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
                                    this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
                                    this.smallFontRenderer.drawString(((ItemStack)next).getDisplayName(), j2, k2, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                                    GL11.glPopMatrix();
                                }
                                str = "" + amount + "/" + e.getValue();
                                boolean valid = amount >= e.getValue();
                                if (!valid && validInputMaterials) {
                                    validInputMaterials = false;
                                }
                                int color = (valid | this.mc.thePlayer.capabilities.isCreativeMode) != false ? ColorUtil.to32BitColor((int)255, (int)0, (int)255, (int)0) : ColorUtil.to32BitColor((int)255, (int)255, (int)0, (int)0);
                                this.smallFontRenderer.drawString(str, xPos + 8 - this.smallFontRenderer.getStringWidth(str) / 2, yPos + 15, color);
                            } else if (next instanceof ArrayList) {
                               ArrayList<ItemStack> items = (ArrayList<ItemStack>) next;
                               int amount = 0;
                                for (ItemStack stack : items) {
                                    amount += this.getAmountInInventory(stack);
                                }
                                RenderHelper.enableGUIStandardItemLighting();
                                ItemStack stack = ((ItemStack)items.get(this.ticksSinceMenuOpen / 20 % items.size())).copy();
                                GuiCelestialSelection.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, stack, xPos, yPos);
                                RenderHelper.disableStandardItemLighting();
                                GL11.glEnable((int)3042);
                                if (mousePosX >= xPos && mousePosX <= xPos + 16 && mousePosY >= yPos && mousePosY <= yPos + 16) {
                                    GL11.glDepthMask((boolean)true);
                                    GL11.glEnable((int)2929);
                                    GL11.glPushMatrix();
                                    GL11.glTranslatef((float)0.0f, (float)0.0f, (float)300.0f);
                                    int k = this.smallFontRenderer.getStringWidth(stack.getDisplayName());
                                    int j2 = mousePosX - k / 2;
                                    int k2 = mousePosY - 12;
                                    int i1 = 8;
                                    if (j2 + k > this.width) {
                                        j2 -= j2 - this.width + k;
                                    }
                                    if (k2 + i1 + 6 > this.height) {
                                        k2 = this.height - i1 - 6;
                                    }
                                    int j1 = ColorUtil.to32BitColor((int)190, (int)0, (int)153, (int)255);
                                    this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
                                    this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
                                    this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
                                    this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
                                    int k1 = ColorUtil.to32BitColor((int)170, (int)0, (int)153, (int)255);
                                    int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & -16777216;
                                    this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
                                    this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
                                    this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
                                    this.smallFontRenderer.drawString(stack.getDisplayName(), j2, k2, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                                    GL11.glPopMatrix();
                                }
                                str = "" + amount + "/" + e.getValue();
                               boolean valid = amount >= e.getValue();
                                if (!valid && validInputMaterials) {
                                    validInputMaterials = false;
                                }
                                int color = (valid | this.mc.thePlayer.capabilities.isCreativeMode) != false ? ColorUtil.to32BitColor((int)255, (int)0, (int)255, (int)0) : ColorUtil.to32BitColor((int)255, (int)255, (int)0, (int)0);
                                this.smallFontRenderer.drawString(str, xPos + 8 - this.smallFontRenderer.getStringWidth(str) / 2, yPos + 15, color);
                            }
                            ++i;
                        }
                        if (validInputMaterials || this.mc.thePlayer.capabilities.isCreativeMode) {
                            if (mousePosX >= this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 93 && mousePosX < this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH && mousePosY > GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 62 + this.canCreateOffset && mousePosY < GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 62 + 13 + this.canCreateOffset) {
                                GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.1f, (float)1.0f);
                            }
                        } else {
                            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                        }
                        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
                        this.canCreateOffset = (int)((long)this.canCreateOffset + Math.round(Math.ceil((float)recipe.getRecipeSize() / 4.0f) - 1.0) * 25L);
                        if (!this.mapMode && mousePosX >= this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95 && mousePosX <= this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH && mousePosY >= GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 63 + this.canCreateOffset && mousePosY <= GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 63 + 12 + this.canCreateOffset) {
                            this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 93, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 63 + this.canCreateOffset, 93, 12, 0, 174, 93, 12, false, false);
                        }
                        this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 93, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 63 + this.canCreateOffset, 93, 12, 0, 174, 93, 12, false, false);
                        int color = (int)((Math.sin((double)this.ticksSinceMenuOpen / 5.0) * 0.5 + 0.5) * 255.0);
                        this.drawSplitString(GCCoreUtil.translate((String)"gui.message.canCreateSpaceStation.name"), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 46, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 18, 91, ColorUtil.to32BitColor((int)255, (int)color, (int)255, (int)color), true, false);
                        if (!this.mapMode) {
                            this.drawSplitString(GCCoreUtil.translate((String)"gui.message.createSS.name").toUpperCase(), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 46, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 65 + this.canCreateOffset, 91, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255), false, false);
                        }
                    } else {
                        this.drawSplitString(GCCoreUtil.translate((String)"gui.message.cannotCreateSpaceStation.name"), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 48, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 138, 91, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255), true, false);
                    }
                }
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(0.3f - Math.min(0.3f, (float)this.ticksSinceSelection / 50.0f)));
                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 74, 11, 0, 392, 148, 22, false, false);
                str = GCCoreUtil.translate((String)"gui.message.catalog.name").toUpperCase();
                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 40 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 1, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                if (this.selectedBody instanceof Satellite) {
                    if (this.selectedStationOwner.length() == 0 || !this.selectedStationOwner.equalsIgnoreCase(this.mc.thePlayer.getGameProfile().getName())) {
                        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    } else {
                        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                    }
                    this.drawTexturedModalRect(this.width / 2 - 47, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 94, 11, 0, 414, 188, 22, false, false);
                } else {
                    this.drawTexturedModalRect(this.width / 2 - 47, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 94, 11, 0, 414, 188, 22, false, false);
                }
                if (this.selectedBody.getTierRequirement() >= 0 && !(this.selectedBody instanceof Satellite)) {
                   boolean canReach;
                    if (!this.selectedBody.getReachable() || (this.enableNewTierSystem != false ? this.currenttier < this.tierneed : this.possibleBodies != null && this.possibleBodies.contains(this.selectedBody) == false)) {
                        canReach = false;
                        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    } else {
                        canReach = true;
                        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                    }
                    this.drawTexturedModalRect(this.width / 2 - 30, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 11, 30, 11, 0, 414, 60, 22, false, false);
                    this.drawTexturedModalRect(this.width / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 11, 30, 11, 128, 414, 60, 22, false, false);
                    str = this.enableNewTierSystem != false ? GCCoreUtil.translateWithFormat((String)"gui.message.tier.name", (Object[])new Object[]{this.tierneed > 0 && this.selectedBody.getReachable() != false ? Integer.valueOf(this.tierneed) : "?"}) : GCCoreUtil.translateWithFormat((String)"gui.message.tier.name", (Object[])new Object[]{this.selectedBody.getTierRequirement() == 0 ? "?" : Integer.valueOf(this.selectedBody.getTierRequirement())});
                    this.fontRendererObj.drawString(str, this.width / 2 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 13, canReach != false ? ColorUtil.to32BitColor((int)255, (int)140, (int)140, (int)140) : ColorUtil.to32BitColor((int)255, (int)255, (int)100, (int)100));
                }
                if (this.selectedBody.getTierRequirement() == -2 && this.selectedBody instanceof Star) {
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                    this.drawTexturedModalRect(this.width / 2 - 40, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 11, 40, 11, 0, 414, 90, 22, false, false);
                    this.drawTexturedModalRect(this.width / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 11, 60, 11, 121, 414, 100, 22, false, false);
                    str = GCCoreUtil.translate((String)"gui.info.blackhole.name");
                    this.fontRendererObj.drawString(str, this.width / 2 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 13, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)0));
                }
                str = this.selectedBody.getLocalizedName();
                if (this.selectedBody instanceof Satellite) {
                    str = GCCoreUtil.translate((String)"gui.message.rename.name").toUpperCase();
                }
                this.fontRendererObj.drawString(str, this.width / 2 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 2, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 4, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 83, 12, 0, 477, 83, 12, false, false);
                if (this.mapMode) break block213;
                //if (!this.selectedBody.getReachable() || (this.enableNewTierSystem == false ? this.possibleBodies != null && this.possibleBodies.contains(this.selectedBody) == false : this.currenttier < this.tierneed)) ** GOTO lbl-1000
                if (this.selectedBody instanceof Satellite && this.selectedStationOwner.equals(""))
                // 2 sources

                {
                    GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                } else if (mousePosX > this.width - GSGuiCelestialSelection.BORDER_WIDTH - GSGuiCelestialSelection.BORDER_EDGE_WIDTH - 88 && mousePosX < this.width - GSGuiCelestialSelection.BORDER_WIDTH - GSGuiCelestialSelection.BORDER_EDGE_WIDTH && mousePosY > GSGuiCelestialSelection.BORDER_WIDTH + GSGuiCelestialSelection.BORDER_EDGE_WIDTH && mousePosY < GSGuiCelestialSelection.BORDER_WIDTH + GSGuiCelestialSelection.BORDER_EDGE_WIDTH + 13) {
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                } else {
                    GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                }
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 74, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 74, 11, 0, 392, 148, 22, true, false);
                str = GCCoreUtil.translate((String)"gui.message.launch.name").toUpperCase();
                this.fontRendererObj.drawString(str, this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 40 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 1, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
            }
            if (this.selectionCount == 1 && !(this.selectedBody instanceof Satellite)) {
                handledSliderPos = true;
                int sliderPos = this.zoomTooltipPos;
                if (this.zoomTooltipPos != 38) {
                    this.zoomTooltipPos = sliderPos = Math.min(this.ticksSinceSelection * 2, 38);
                }
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
                this.drawTexturedModalRect(this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 182, this.height - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - sliderPos, 83, 38, 346, 436, 166, 76, true, true);
               boolean flag0 = GalaxyRegistry.getSatellitesForCelestialBody(this.selectedBody).size() > 0;
               boolean flag1 = this.selectedBody instanceof Planet ? GalaxyRegistry.getMoonsForPlanet((Planet) this.selectedBody).size() > 0 : false;
               if (flag0 && flag1) {
                    this.drawSplitString(GCCoreUtil.translate((String)"gui.message.clickAgain.0.name"), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 182 + 41, this.height - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 1 + 7 - sliderPos, 79, ColorUtil.to32BitColor((int)255, (int)150, (int)150, (int)150), false, false);
                } else if (!flag0 && flag1) {
                    this.drawSplitString(GCCoreUtil.translate((String)"gui.message.clickAgain.1.name"), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 182 + 41, this.height - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 5 + 11 - sliderPos, 79, ColorUtil.to32BitColor((int)255, (int)150, (int)150, (int)150), false, false);
                } else if (flag0) {
                    this.drawSplitString(GCCoreUtil.translate((String)"gui.message.clickAgain.2.name"), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 182 + 41, this.height - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 5 + 11 - sliderPos, 79, ColorUtil.to32BitColor((int)255, (int)150, (int)150, (int)150), false, false);
                } else {
                    this.drawSplitString(GCCoreUtil.translate((String)"gui.message.clickAgain.3.name"), this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 182 + 41, this.height - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 10 + 20 - sliderPos, 79, ColorUtil.to32BitColor((int)255, (int)150, (int)150, (int)150), false, false);
                }
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
            }
            if (this.selectedBody instanceof Satellite && this.renamingSpaceStation) {
                this.drawDefaultBackground();
                GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
                this.drawTexturedModalRect(this.width / 2 - 90, this.height / 2 - 38, 179, 67, 159, 0, 179, 67, false, false);
                this.drawTexturedModalRect(this.width / 2 - 90 + 4, this.height / 2 - 38 + 2, 171, 10, 159, 92, 171, 10, false, false);
                this.drawTexturedModalRect(this.width / 2 - 90 + 8, this.height / 2 - 38 + 18, 161, 13, 159, 67, 161, 13, false, false);
                this.drawTexturedModalRect(this.width / 2 - 90 + 17, this.height / 2 - 38 + 59, 72, 12, 159, 80, 72, 12, true, false);
                this.drawTexturedModalRect(this.width / 2, this.height / 2 - 38 + 59, 72, 12, 159, 80, 72, 12, false, false);
                String str = GCCoreUtil.translate((String)"gui.message.assignName.name");
                this.fontRendererObj.drawString(str, this.width / 2 - this.fontRendererObj.getStringWidth(str) / 2, this.height / 2 - 35, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                str = GCCoreUtil.translate((String)"gui.message.apply.name");
                this.fontRendererObj.drawString(str, this.width / 2 - this.fontRendererObj.getStringWidth(str) / 2 - 36, this.height / 2 + 23, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                str = GCCoreUtil.translate((String)"gui.message.cancel.name");
                this.fontRendererObj.drawString(str, this.width / 2 + 36 - this.fontRendererObj.getStringWidth(str) / 2, this.height / 2 + 23, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
                if (this.renamingString == null) {
                    Satellite selectedSatellite = (Satellite)this.selectedBody;
                    String playerName = FMLClientHandler.instance().getClient().thePlayer.getGameProfile().getName();
                    this.renamingString = ((GuiCelestialSelection.StationDataGUI)((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(playerName)).getStationName();
                    if (this.renamingString == null) {
                        this.renamingString = ((GuiCelestialSelection.StationDataGUI)((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(playerName.toLowerCase())).getStationName();
                    }
                    if (this.renamingString == null) {
                        this.renamingString = "";
                    }
                }
                str = this.renamingString;
                String str0 = this.renamingString;
                if (this.ticksSinceMenuOpen / 10 % 2 == 0) {
                    str0 = str0 + "_";
                }
                this.fontRendererObj.drawString(str0, this.width / 2 - this.fontRendererObj.getStringWidth(str) / 2, this.height / 2 - 17, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
            }
        }
        String str = EnumColor.ORANGE + "GalaxySpace Galaxy Map";
        this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 5, this.height - 20, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
        if (!handledSliderPos) {
            this.zoomTooltipPos = 0;
        }
    }

    private int drawChildren(List<CelestialBody> children, int xOffsetBase, int yOffsetPrior, boolean recursive) {
        xOffsetBase += GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH;
        int yOffsetBase = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 50 + yOffsetPrior;
        int yOffset = 0;
        for (int i = 0; i < children.size(); ++i) {
            List grandchildren;
            float brightness;
            CelestialBody child = children.get(i);
            int xOffset = xOffsetBase + (child.equals((Object)this.selectedBody) ? 5 : 0);
            int scale = (int)Math.min(95.0f, Math.max(0.0f, (float)this.ticksSinceMenuOpen * 25.0f - (float)(95 * i)));
            this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
            float f = brightness = child.equals((Object)this.selectedBody) ? 0.2f : 0.0f;
            if (child.getReachable()) {
                if (child.equals((Object)this.selectedBody)) {
                    GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)((float)scale / 95.0f));
                } else {
                    GL11.glColor4f((float)0.0f, (float)(0.6f + brightness), (float)0.0f, (float)((float)scale / 95.0f));
                }
            } else {
                boolean checked = false;
                if (child instanceof Planet) {
                    for (Moon moon : GalaxyRegistry.getMoonsForPlanet((Planet)((Planet)child))) {
                        if (!moon.getReachable()) continue;
                        checked = true;
                        break;
                    }
                }
                if (child instanceof Planet && checked) {
                    GL11.glColor4f((float)(0.6f + brightness), (float)0.6f, (float)0.0f, (float)((float)scale / 95.0f));
                } else {
                    GL11.glColor4f((float)((child.getTierRequirement() <= -1 ? 0.0f : 0.6f) + brightness), (float)0.0f, (float)0.0f, (float)((float)scale / 95.0f));
                }
            }
            this.drawTexturedModalRect(3 + xOffset, yOffsetBase + yOffset + 1, 86, 10, 0, 489, 86, 10, false, false);
            GL11.glColor4f((float)(3.0f * brightness), (float)(0.6f + 2.0f * brightness), (float)1.0f, (float)((float)scale / 95.0f));
            this.drawTexturedModalRect(2 + xOffset, yOffsetBase + yOffset, 93, 12, 95, 464, 93, 12, false, false);
            if (scale > 0) {
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int color = 0xE0E0E0;
                this.fontRendererObj.drawString(child.getLocalizedName(), 7 + xOffset, yOffsetBase + yOffset + 2, color);
            }
            yOffset += 14;
            if (!child.equals((Object)this.selectedBody) || (grandchildren = this.getChildren(child)).size() <= 0) continue;
            if (this.animateGrandchildren == 14 * grandchildren.size()) {
                yOffset += this.drawChildren(grandchildren, 10, yOffset, false);
                continue;
            }
            if (this.animateGrandchildren >= 14) {
                LinkedList<CelestialBody> partial = new LinkedList<CelestialBody>();
                for (int j = 0; j < this.animateGrandchildren / 14; ++j) {
                    partial.add((CelestialBody)grandchildren.get(j));
                }
                this.drawChildren(partial, 10, yOffset, false);
            }
            yOffset += this.animateGrandchildren;
            this.animateGrandchildren += 2;
        }
        return yOffset;
    }

    protected void keyTyped(char keyChar, int keyID) {
        if (this.mapMode) {
            super.keyTyped(keyChar, keyID);
        }
        if (keyID == 1 && !this.check && this.selectedBody != null) {
            this.unselectCelestialBody();
        }
        if (this.renamingSpaceStation) {
            if (keyID == 14) {
                if (this.renamingString != null && this.renamingString.length() > 0) {
                    String toBeParsed = this.renamingString.substring(0, this.renamingString.length() - 1);
                    this.renamingString = this.isValid(toBeParsed) ? toBeParsed : "";
                }
            } else if (keyChar == '\u0016') {
                String pastestring = GuiScreen.getClipboardString();
                if (pastestring == null) {
                    pastestring = "";
                }
                if (this.isValid(this.renamingString + pastestring)) {
                    this.renamingString = this.renamingString + pastestring;
                    this.renamingString = this.renamingString.substring(0, Math.min(String.valueOf(this.renamingString).length(), 32));
                }
            } else if (this.isValid(this.renamingString + keyChar)) {
                this.renamingString = this.renamingString + keyChar;
                this.renamingString = this.renamingString.substring(0, Math.min(this.renamingString.length(), 32));
            }
            return;
        }
        if (keyID == 28) {
            if (!this.mc.thePlayer.capabilities.isCreativeMode && this.currenttier >= this.tierneed && this.enableNewTierSystem) {
                this.check = true;
            } else {
                this.GSteleportToSelectedBody();
            }
        }
    }

    protected void mouseClicked(int x, int y, int button) {
        int selectionCountOld;
        boolean planetZoomedMoon;
        SpaceStationRecipe recipe;
        boolean clickHandled = false;
        int LHS = BORDER_WIDTH + BORDER_EDGE_WIDTH;
        int RHS = this.width - LHS;
        int TOP = LHS;
        if (!this.check && this.selectedBody != null && x > BORDER_WIDTH + BORDER_EDGE_WIDTH && x < BORDER_WIDTH + BORDER_EDGE_WIDTH + 88 && y > BORDER_WIDTH + BORDER_EDGE_WIDTH && y < BORDER_WIDTH + BORDER_EDGE_WIDTH + 13) {
            this.unselectCelestialBody();
            return;
        }
        if (x > LHS && x < LHS + 84 && y > this.height - LHS - 26 && y < this.height - LHS - 16) {
            boolean bl = this.hideInfo = !this.hideInfo;
        }
        if (x > LHS && x < LHS + 84 && y > this.height - LHS - 42 && y < this.height - LHS - 30) {
            boolean bl = this.small_mode = !this.small_mode;
        }
        if (x > BORDER_WIDTH + BORDER_EDGE_WIDTH + 6 && x < BORDER_WIDTH + BORDER_EDGE_WIDTH + 93 && y > BORDER_WIDTH + BORDER_EDGE_WIDTH + 12 && y < BORDER_WIDTH + BORDER_EDGE_WIDTH + 26) {
            if (this.galaxylist.size() > 1) {
                boolean bl = this.showGalaxyList = !this.showGalaxyList;
            }
            if (this.showStarList) {
                this.showStarList = false;
            }
        }
        if (x > BORDER_WIDTH + BORDER_EDGE_WIDTH + 6 && x < BORDER_WIDTH + BORDER_EDGE_WIDTH + 93 && y > BORDER_WIDTH + BORDER_EDGE_WIDTH + 32 && y < BORDER_WIDTH + BORDER_EDGE_WIDTH + 43) {
            boolean bl = this.showStarList = !this.showStarList;
            if (this.showGalaxyList) {
                this.showGalaxyList = false;
            }
        }
        if (this.showStarList) {
            for (Map.Entry<SolarSystem, Integer> entry : this.starlist.entrySet()) {
                int l = entry.getValue();
                if (x <= BORDER_WIDTH + BORDER_EDGE_WIDTH + 100 || x >= BORDER_WIDTH + BORDER_EDGE_WIDTH + 193 || y <= BORDER_WIDTH + BORDER_EDGE_WIDTH + 14 * l + 19 || y >= BORDER_WIDTH + BORDER_EDGE_WIDTH + 14 * l + 34) continue;
                this.selectedParent = entry.getKey();
                this.showStarList = false;
                this.selectedBody = entry.getKey().getMainStar();
                this.selectionCount = 1;
                this.zoom = -0.2f;
                this.position = new Vector2f(this.getCelestialBodyPosition((CelestialBody)this.selectedBody).x, this.getCelestialBodyPosition((CelestialBody)this.selectedBody).y);
                clickHandled = true;
                if (this.selectedBody instanceof IChildBody) {
                    this.galaxy = ((IChildBody)this.selectedBody).getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName();
                }
                if (this.selectedBody instanceof Planet) {
                    this.galaxy = ((Planet)this.selectedBody).getParentSolarSystem().getUnlocalizedParentGalaxyName();
                }
                if (!(this.selectedBody instanceof Star)) continue;
                this.galaxy = ((Star)this.selectedBody).getParentSolarSystem().getUnlocalizedParentGalaxyName();
            }
        }
        if (this.showGalaxyList) {
            boolean l = true;
            for (int i = 0; i < this.galaxylist.size(); ++i) {
                if (x <= BORDER_WIDTH + BORDER_EDGE_WIDTH + 100 || x >= BORDER_WIDTH + BORDER_EDGE_WIDTH + 193 || y <= BORDER_WIDTH + BORDER_EDGE_WIDTH + 13 * i + 19 || y >= BORDER_WIDTH + BORDER_EDGE_WIDTH + 13 * i + 34) continue;
                this.galaxy = this.galaxylist.get(i);
                this.zoom = -1.0f;
                this.showGalaxyList = false;
                this.starlist.clear();
            }
        }
        if (!this.mapMode && x >= this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95 && x < this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH && y > GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 62 + this.canCreateOffset && y < GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 62 + 14 + this.canCreateOffset && this.selectedBody != null && (recipe = WorldUtil.getSpaceStationRecipe((int)this.selectedBody.getDimensionID())) != null && this.canCreateSpaceStation(this.selectedBody)) {
            if (recipe.matches((EntityPlayer)this.mc.thePlayer, false) || this.mc.thePlayer.capabilities.isCreativeMode) {
                GalacticraftCore.packetPipeline.sendToServer((IPacket)new PacketSimple(PacketSimple.EnumSimplePacket.S_BIND_SPACE_STATION_ID, new Object[]{this.selectedBody.getDimensionID()}));
                if (this.selectionCount < 2) {
                    this.selectionCount = 2;
                    this.preSelectZoom = this.zoom;
                    this.preSelectPosition = this.position;
                    this.ticksSinceSelection = 0;
                    this.doneZooming = false;
                }
            }
            clickHandled = true;
        }
        if (this.mapMode && x > this.width - BORDER_WIDTH - BORDER_EDGE_WIDTH - 88 && x < this.width - BORDER_WIDTH - BORDER_EDGE_WIDTH && y > BORDER_WIDTH + BORDER_EDGE_WIDTH && y < BORDER_WIDTH + BORDER_EDGE_WIDTH + 13) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            clickHandled = true;
        }
        if (this.selectedBody != null && !this.mapMode && x > this.width - BORDER_WIDTH - BORDER_EDGE_WIDTH - 88 && x < this.width - BORDER_WIDTH - BORDER_EDGE_WIDTH && y > BORDER_WIDTH + BORDER_EDGE_WIDTH && y < BORDER_WIDTH + BORDER_EDGE_WIDTH + 13) {
            if (!(this.selectedBody instanceof Satellite) || !this.selectedStationOwner.equals("")) {
                if (!this.mc.thePlayer.capabilities.isCreativeMode && this.currenttier >= this.tierneed && this.enableNewTierSystem) {
                    this.check = true;
                } else {
                    this.GSteleportToSelectedBody();
                }
            }
            clickHandled = true;
        }
        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY() * -1 + Minecraft.getMinecraft().displayHeight - 1;
        if (this.selectedBody instanceof Satellite) {
            if (this.renamingSpaceStation) {
                if (x >= this.width / 2 - 90 && x <= this.width / 2 + 90 && y >= this.height / 2 - 38 && y <= this.height / 2 + 38) {
                    if (x >= this.width / 2 - 90 + 17 && x <= this.width / 2 - 90 + 17 + 72 && y >= this.height / 2 - 38 + 59 && y <= this.height / 2 - 38 + 59 + 12) {
                        String strName = this.mc.thePlayer.getGameProfile().getName();
                        Satellite selectedSatellite = (Satellite)this.selectedBody;
                        Integer spacestationID = ((GuiCelestialSelection.StationDataGUI)((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(strName)).getStationDimensionID();
                        if (spacestationID == null) {
                            spacestationID = ((GuiCelestialSelection.StationDataGUI)((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(strName.toLowerCase())).getStationDimensionID();
                        }
                        if (spacestationID != null) {
                            ((GuiCelestialSelection.StationDataGUI)((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(strName)).setStationName(this.renamingString);
                            GalacticraftCore.packetPipeline.sendToServer((IPacket)new PacketSimple(PacketSimple.EnumSimplePacket.S_RENAME_SPACE_STATION, new Object[]{this.renamingString, spacestationID}));
                        }
                        this.renamingSpaceStation = false;
                    }
                    if (x >= this.width / 2 && x <= this.width / 2 + 72 && y >= this.height / 2 - 38 + 59 && y <= this.height / 2 - 38 + 59 + 12) {
                        this.renamingSpaceStation = false;
                    }
                    clickHandled = true;
                }
            } else {
                this.drawTexturedModalRect(this.width / 2 - 47, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH, 94, 11, 0, 414, 188, 22, false, false);
                if (x >= this.width / 2 - 47 && x <= this.width / 2 - 47 + 94 && y >= GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH && y <= GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 11 && this.selectedStationOwner.length() != 0 && this.selectedStationOwner.equalsIgnoreCase(this.mc.thePlayer.getGameProfile().getName())) {
                    this.renamingSpaceStation = true;
                    this.renamingString = null;
                    clickHandled = true;
                }
                Satellite selectedSatellite = (Satellite)this.selectedBody;
                int stationListSize = ((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).size();
                int max = Math.min(this.height / 2 / 14, stationListSize);
                int xPos = this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 85;
                int yPos2 = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 45;
                if (x >= xPos && x <= xPos + 61 && y >= yPos2 && y <= yPos2 + 4) {
                    if (this.spaceStationListOffset > 0) {
                        --this.spaceStationListOffset;
                    }
                    clickHandled = true;
                }
                xPos = this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 85;
                yPos2 = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 49 + max * 14;
                if (x >= xPos && x <= xPos + 61 && y >= yPos2 && y <= yPos2 + 4) {
                    if (max + this.spaceStationListOffset < stationListSize) {
                        ++this.spaceStationListOffset;
                    }
                    clickHandled = true;
                }
                Iterator it = ((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).entrySet().iterator();
                int i = 0;
                int j = 0;
                while (it.hasNext() && i < max) {
                    Map.Entry<Object, Integer> e = (Map.Entry<Object, Integer>) it.next();
                    if (j >= this.spaceStationListOffset) {
                        int xOffset = 0;
                        if (((String)e.getKey()).equalsIgnoreCase(this.selectedStationOwner)) {
                            xOffset -= 5;
                        }
                        xPos = this.width - GuiCelestialSelection.BORDER_WIDTH - GuiCelestialSelection.BORDER_EDGE_WIDTH - 95 + xOffset;
                        yPos2 = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 50 + i * 14;
                        if (x >= xPos && x <= xPos + 93 && y >= yPos2 && y <= yPos2 + 12) {
                            this.selectedStationOwner = (String)e.getKey();
                            clickHandled = true;
                        }
                        ++i;
                    }
                    ++j;
                }
            }
        }
        int xPos = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 2;
        int yPos = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 10;
        boolean bl = planetZoomedMoon = this.selectionCount == 2 && this.selectedParent instanceof Planet;
        if (x >= xPos && x <= xPos + 93 && y >= yPos && y <= yPos + 12 && this.selectedParent instanceof CelestialBody) {
            if (this.selectedBody == null) {
                this.preSelectZoom = this.zoom;
                this.preSelectPosition = this.position;
            }
            selectionCountOld = this.selectionCount;
            if (!this.check && this.selectionCount > 0) {
                this.unselectCelestialBody();
            }
            if (selectionCountOld == 2) {
                this.selectionCount = 1;
            }
            this.selectedBody = (CelestialBody)this.selectedParent;
            this.ticksSinceSelection = 0;
            ++this.selectionCount;
            if (this.selectionCount == 2 && !planetZoomedMoon) {
                this.ticksSinceMenuOpen = 0;
            }
            clickHandled = true;
        }
        if (x >= xPos && x <= xPos + 93 && y >= (yPos += 22) && y <= yPos + 12) {
            if (planetZoomedMoon) {
                if (this.selectedBody == null) {
                    this.preSelectZoom = this.zoom;
                    this.preSelectPosition = this.position;
                }
                selectionCountOld = this.selectionCount;
                if (!this.check && this.selectionCount > 0) {
                    this.unselectCelestialBody();
                }
                if (selectionCountOld == 2) {
                    this.selectionCount = 1;
                }
                this.selectedBody = (CelestialBody)this.selectedParent;
                this.ticksSinceSelection = 0;
                ++this.selectionCount;
            }
            clickHandled = true;
        }
        if (!clickHandled) {
            List<CelestialBody> children = this.getChildren(this.selectionCount == 2 ? this.selectedBody : this.selectedParent);
            yPos = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 50;
           for(CelestialBody child : children) {

                clickHandled = this.testClicked(child, child.equals(this.selectedBody) ? 5 : 0, yPos, x, y, false);
                yPos += 14;
                if (!clickHandled && this.selectionCount != 2 && child.equals((Object)this.selectedBody)) {
                    List<CelestialBody> grandchildren = this.getChildren(child);
                    int gOffset = 0;
                    for (CelestialBody grandchild : grandchildren) {
                        if (gOffset + 14 > this.animateGrandchildren) break;
                        clickHandled = this.testClicked(grandchild, 10, yPos, x, y, true);
                        yPos += 14;
                        gOffset += 14;
                        if (!clickHandled) continue;
                        break;
                    }
                    yPos += this.animateGrandchildren - gOffset;
                }
                if (!clickHandled) continue;
                break;
            }
        }
        if (!clickHandled) {
            for (Map.Entry<CelestialBody, Vector3f> e : this.planetPosMap.entrySet()) {
                float iconSize;
                CelestialBody bodyClicked = (CelestialBody)e.getKey();
                if (this.selectedBody == null && bodyClicked instanceof IChildBody || !((float)mouseX >= ((Vector3f)e.getValue()).x - (iconSize = ((Vector3f)e.getValue()).z)) || !((float)mouseX <= ((Vector3f)e.getValue()).x + iconSize) || !((float)mouseY >= ((Vector3f)e.getValue()).y - iconSize) || !((float)mouseY <= ((Vector3f)e.getValue()).y + iconSize) || this.selectedBody == bodyClicked && this.selectionCount >= 2) continue;
                if (this.selectionCount > 0 && this.selectedBody != bodyClicked) {
                    if (!(this.check || this.selectedBody instanceof IChildBody && ((IChildBody)this.selectedBody).getParentPlanet() == bodyClicked)) {
                        this.unselectCelestialBody();
                    } else if (this.selectionCount == 2) {
                        --this.selectionCount;
                    }
                }
                this.doneZooming = false;
                this.planetZoom = 0.0f;
                if (bodyClicked != this.selectedBody) {
                    this.lastSelectedBody = this.selectedBody;
                    this.animateGrandchildren = 0;
                }
                if (this.selectionCount == 1 && !(bodyClicked instanceof IChildBody)) {
                    this.preSelectZoom = this.zoom;
                    this.preSelectPosition = this.position;
                }
                if (!this.check) {
                    this.selectedBody = bodyClicked;
                }
                this.ticksSinceSelection = 0;
                ++this.selectionCount;
                if (this.selectionCount == 2) {
                    this.ticksSinceMenuOpen = 0;
                }
                if (this.selectedBody instanceof IChildBody) {
                    this.galaxy = ((IChildBody)this.selectedBody).getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName();
                }
                if (this.selectedBody instanceof Planet) {
                    this.galaxy = ((Planet)this.selectedBody).getParentSolarSystem().getUnlocalizedParentGalaxyName();
                }
                if (this.selectedBody instanceof Star) {
                    this.galaxy = ((Star)this.selectedBody).getParentSolarSystem().getUnlocalizedParentGalaxyName();
                }
                if (this.selectedBody instanceof Satellite && ((Map)this.spaceStationMap.get(this.getSatelliteParentID((Satellite)this.selectedBody))).size() >= 1) {
                   Iterator<Map.Entry<String, StationDataGUI>> it = this.spaceStationMap.get(this.getSatelliteParentID((Satellite) this.selectedBody)).entrySet().iterator();
                   this.selectedStationOwner = it.next().getKey();
                }
                clickHandled = true;
                break;
            }
        }
        if (!clickHandled) {
            if (!this.check && this.selectedBody != null) {
                if (x > RHS - 35 && x < RHS - 10 && y > TOP + 210 && y < TOP + 220) {
                    BodiesHelper.BodiesData data = BodiesHelper.data.get(this.selectedBody);
                    int k = data != null && data.getItemStacks().size() > 0 ? 3 : 2;
                    if (this.small_page < k) {
                        ++this.small_page;
                    }
                } else if (x > RHS - 120 && x < RHS - 90 && y > TOP + 210 && y < TOP + 220) {
                    if (this.small_page > 0) {
                        --this.small_page;
                    }
                } else {
                    this.unselectCelestialBody();
                }
                this.showStarList = false;
            }
            this.mouseDragging = true;
        }
        Object selectedParent = this.selectedParent;
        if (this.selectedBody instanceof IChildBody) {
            selectedParent = ((IChildBody)this.selectedBody).getParentPlanet();
        } else if (this.selectedBody instanceof Planet) {
            selectedParent = ((Planet)this.selectedBody).getParentSolarSystem();
        } else if (this.selectedBody instanceof Star) {
            selectedParent = ((Star)this.selectedBody).getParentSolarSystem();
        } else if (this.selectedBody == null) {
            if (this.lastSelectedBody instanceof Planet) {
                selectedParent = ((Planet)this.lastSelectedBody).getParentSolarSystem();
            }
            if (this.lastSelectedBody instanceof IChildBody) {
                selectedParent = ((IChildBody)this.lastSelectedBody).getParentPlanet().getParentSolarSystem();
            }
        }
        if (this.selectedParent != selectedParent) {
            this.selectedParent = selectedParent;
            this.ticksSinceMenuOpen = 0;
        }
    }

    private boolean testClicked(CelestialBody body, int xOffset, int yPos, int x, int y, boolean grandchild) {
        int xPos = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 2 + xOffset;
        if (x >= xPos && x <= xPos + 93 && y >= yPos && y <= yPos + 12 && (this.selectedBody != body || this.selectionCount != 2)) {
            if (this.selectedBody == null) {
                this.preSelectZoom = this.zoom;
                this.preSelectPosition = this.position;
            }
            int selectionCountOld = this.selectionCount;
            if (this.selectionCount == 1 && this.selectedBody != body) {
                this.unselectCelestialBody();
            }
            if (selectionCountOld == 2) {
                this.selectionCount = 1;
            }
            this.doneZooming = false;
            this.planetZoom = 0.0f;
            if (body != this.selectedBody) {
                this.lastSelectedBody = this.selectedBody;
            }
            this.selectedBody = body;
            this.ticksSinceSelection = 0;
            this.small_page = 0;
            ++this.selectionCount;
            if (grandchild) {
                this.selectionCount = 2;
            }
            if (this.selectionCount == 2) {
                this.ticksSinceMenuOpen = 0;
            }
            this.animateGrandchildren = 0;
            return true;
        }
        return false;
    }

    protected void mouseClickMove(int x, int y, int lastButtonClicked, long timeSinceMouseClick) {
        if (this.mouseDragging && this.lastMovePosX != -1 && lastButtonClicked == 0) {
            int deltaX = x - this.lastMovePosX;
            int deltaY = y - this.lastMovePosY;
            this.translation.x = this.translation.x + (float)(deltaX - deltaY) * -0.4f * (ConfigManagerCore.invertMapMouseScroll ? -1.0f : 1.0f) * ConfigManagerCore.mapMouseScrollSensitivity;
            this.translation.y = this.translation.y + (float)(deltaY + deltaX) * -0.4f * (ConfigManagerCore.invertMapMouseScroll ? -1.0f : 1.0f) * ConfigManagerCore.mapMouseScrollSensitivity;
            if (GSConfigCore.enableDynamicImgOnMap) {
                this.xImgOffset += (double)((float)deltaX * -0.2f);
                this.yImgOffset += (double)((float)deltaY * -0.2f);
            }
        }
        this.lastMovePosX = x;
        this.lastMovePosY = y;
        this.showStarList = false;
        this.showGalaxyList = false;
        if (this.xImgOffset > 20.0) {
            this.xImgOffset = 20.0;
        }
        if (this.xImgOffset < -240.0) {
            this.xImgOffset = -240.0;
        }
        if (this.yImgOffset > 20.0) {
            this.yImgOffset = 20.0;
        }
        if (this.yImgOffset < -180.0) {
            this.yImgOffset = -180.0;
        }
    }

    protected void drawTransitBar(int length) {
        String str;
        int menuTopLeft = GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH;
        this.mc.renderEngine.bindTexture(guiMain2);
        GL11.glColor4f((float)0.0f, (float)0.6f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(this.width / 2 - 113, menuTopLeft + 15, 225, 67, 0, 0, 225, 67, false, false);
        this.drawTexturedModalRect(this.width / 2 - 50, menuTopLeft + 57, 0 + length, 78, 270, 0, 138, 77, false, false);
        if (this.mc.thePlayer.worldObj.provider instanceof IGalacticraftWorldProvider) {
            CelestialBody body = ((IGalacticraftWorldProvider)this.mc.thePlayer.worldObj.provider).getCelestialBody();
            this.drawBodyOnGUI(body, this.width / 2 - 76, menuTopLeft + 54, 16, 16);
            str = GCCoreUtil.translate((String)body.getLocalizedName());
        } else {
            this.drawBodyOnGUI((CelestialBody)GalacticraftCore.planetOverworld, this.width / 2 - 76, menuTopLeft + 54, 16, 16);
            str = GCCoreUtil.translate((String)GalacticraftCore.planetOverworld.getLocalizedName());
        }
        this.fontRendererObj.drawString(str, this.width / 2 - 105, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 35, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
        this.drawBodyOnGUI(this.selectedBody, this.width / 2 + 59, menuTopLeft + 54, 16, 16);
        str = GCCoreUtil.translate((String)this.selectedBody.getLocalizedName());
        this.fontRendererObj.drawString(str, this.width / 2 + 50, GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 35, ColorUtil.to32BitColor((int)255, (int)255, (int)255, (int)255));
        str = "Boost: x" + this.coef;
        this.fontRendererObj.drawString(str, this.width / 2 - (this.fontRendererObj.getStringWidth(str) - 25), GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 48, ColorUtil.to32BitColor((int)255, (int)0, (int)255, (int)255));
        float time = this.traveltime - this.timer;
        str = (int)(time / 100.0f) + "h " + (int)(time % 100.0f) + "m";
        this.fontRendererObj.drawString(str, this.width / 2 - (this.fontRendererObj.getStringWidth(str) - 20), GuiCelestialSelection.BORDER_WIDTH + GuiCelestialSelection.BORDER_EDGE_WIDTH + 59, ColorUtil.to32BitColor((int)255, (int)0, (int)255, (int)255));
        if (this.timer >= this.traveltime) {
            this.GSteleportToSelectedBody();
        }
    }

    protected int getScaledTravelTime(int barLength) {
        double remain = this.timer;
        double total = this.traveltime;
        double relative = remain / total;
        double scaled = relative * (double)barLength;
        return (int)scaled;
    }

    protected void drawBodyOnGUI(CelestialBody body, int x, int y, int w, int h) {
        if (body == null) {
            return;
        }
        this.mc.renderEngine.bindTexture(body.getBodyIcon());
        this.drawFullSizedTexturedRect(x, y, w, h);
    }

    public void drawFullSizedTexturedRect(int x, int y, int width, int height) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), (double)this.zLevel, 0.0, 0.0);
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, 1.0, 0.0);
        tessellator.addVertexWithUV((double)(x + width), (double)y, (double)this.zLevel, 1.0, 1.0);
        tessellator.addVertexWithUV((double)x, (double)y, (double)this.zLevel, 0.0, 1.0);
        tessellator.draw();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean GSteleportToSelectedBody() {
        if (this.selectedBody != null && this.selectedBody.getReachable() && (this.enableNewTierSystem ? this.currenttier >= this.tierneed : this.possibleBodies != null && this.possibleBodies.contains(this.selectedBody))) {
            try {
                String dimension;
                if (this.selectedBody instanceof Satellite) {
                    if (this.spaceStationMap == null) {
                        GCLog.severe((String)"Please report as a BUG: spaceStationIDs was null.");
                        return false;
                    }
                    Satellite selectedSatellite = (Satellite)this.selectedBody;
                    Integer mapping = ((GuiCelestialSelection.StationDataGUI)((Map)this.spaceStationMap.get(this.getSatelliteParentID(selectedSatellite))).get(this.selectedStationOwner)).getStationDimensionID();
                    if (mapping == null) {
                        GCLog.severe((String)("Problem matching player name in space station check: " + this.selectedStationOwner));
                        return false;
                    }
                    int spacestationID = mapping;
                    WorldProvider spacestation = WorldUtil.getProviderForDimensionClient((int)spacestationID);
                    if (spacestation == null) {
                        GCLog.severe((String)("Failed to find a spacestation with dimension " + spacestationID));
                        return false;
                    }
                    dimension = WorldUtil.getDimensionName((WorldProvider)spacestation);
                } else {
                    dimension = WorldUtil.getDimensionName((WorldProvider)WorldUtil.getProviderForDimensionClient((int)this.selectedBody.getDimensionID()));
                }
                if (dimension.contains("$")) {
                    this.mc.gameSettings.thirdPersonView = 0;
                }
                GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSPacketSimple.GSEnumSimplePacket.S_TELEPORT_ENTITY, dimension, this.fuelSet));
                this.mc.displayGuiScreen(null);
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String localeBoolean(boolean bol) {
        if (bol) {
            return GCCoreUtil.translate((String)"gui.message.yes");
        }
        return GCCoreUtil.translate((String)"gui.message.no");
    }

    protected boolean canCreateSpaceStation(CelestialBody atBody) {
        if (this.mapMode || ConfigManagerCore.disableSpaceStationCreation) {
            return false;
        }
        if (!atBody.getReachable() || (this.enableNewTierSystem ? this.currenttier < this.tierneed : this.possibleBodies != null && !this.possibleBodies.contains(atBody))) {
            return false;
        }
        boolean foundRecipe = false;
        for (SpaceStationType type : GalacticraftRegistry.getSpaceStationData()) {
            if (type.getWorldToOrbitID() != atBody.getDimensionID()) continue;
            foundRecipe = true;
        }
        if (!foundRecipe) {
            return false;
        }
        if (!ClientProxyCore.clientSpaceStationID.containsKey(atBody.getDimensionID())) {
            return true;
        }
        int resultID = (Integer)ClientProxyCore.clientSpaceStationID.get(atBody.getDimensionID());
        return resultID == 0 || resultID == -1;
    }

    protected void unselectCelestialBody() {
        this.selectionCount = 0;
        this.ticksSinceUnselection = 0;
        this.lastSelectedBody = this.selectedBody;
        this.selectedBody = null;
        this.doneZooming = false;
        this.selectedStationOwner = "";
        this.animateGrandchildren = 0;
    }

    protected String getParentName() {
        if (this.selectedBody instanceof Planet) {
            SolarSystem parentSolarSystem = ((Planet)this.selectedBody).getParentSolarSystem();
            if (parentSolarSystem != null) {
                return parentSolarSystem.getLocalizedName();
            }
        } else if (this.selectedBody instanceof IChildBody) {
            Planet parentPlanet = ((IChildBody)this.selectedBody).getParentPlanet();
            if (parentPlanet != null) {
                return parentPlanet.getLocalizedName();
            }
        } else {
            SolarSystem parentSolarSystem;
            if (this.selectedParent instanceof SolarSystem) {
                return ((SolarSystem)this.selectedParent).getLocalizedName();
            }
            if (this.selectedBody instanceof Star && (parentSolarSystem = ((Star)this.selectedBody).getParentSolarSystem()) != null) {
                return parentSolarSystem.getLocalizedName();
            }
        }
        return "Null";
    }
}

