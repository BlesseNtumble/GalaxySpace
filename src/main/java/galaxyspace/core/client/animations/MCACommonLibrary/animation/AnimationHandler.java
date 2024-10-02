package galaxyspace.core.client.animations.MCACommonLibrary.animation;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.client.animations.MCAClientLibrary.MCAModelRenderer;
import galaxyspace.core.client.animations.MCACommonLibrary.IMCAnimatedEntity;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Quaternion;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public abstract class AnimationHandler {
   public static AnimTickHandler animTickHandler;
   private IMCAnimatedEntity animatedEntity;
   public ArrayList<Channel> animCurrentChannels = new ArrayList();
   public HashMap<String, Long> animPrevTime = new HashMap();
   public HashMap<String, Float> animCurrentFrame = new HashMap();
   private HashMap<String, ArrayList<String>> animationEvents = new HashMap();

   public AnimationHandler(IMCAnimatedEntity entity) {
      if (animTickHandler == null) {
         animTickHandler = new AnimTickHandler();
      }

      animTickHandler.addEntity(entity);
      this.animatedEntity = entity;
   }

   public IMCAnimatedEntity getEntity() {
      return this.animatedEntity;
   }

   public void activateAnimation(HashMap<String, Channel> animChannels, String name, float startingFrame) {
      if (animChannels.get(name) != null) {
         Channel selectedChannel = (Channel)animChannels.get(name);
         int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
         if (indexToRemove != -1) {
            this.animCurrentChannels.remove(indexToRemove);
         }

         this.animCurrentChannels.add(selectedChannel);
         this.animPrevTime.put(name, System.nanoTime());
         this.animCurrentFrame.put(name, startingFrame);
         if (this.animationEvents.get(name) == null) {
            this.animationEvents.put(name, new ArrayList());
         }
      } else {
         System.out.println("The animation called " + name + " doesn't exist!");
      }

   }

   public abstract void activateAnimation(String var1, float var2);

   public void stopAnimation(HashMap<String, Channel> animChannels, String name) {
      Channel selectedChannel = (Channel)animChannels.get(name);
      if (selectedChannel != null) {
         int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
         if (indexToRemove != -1) {
            this.animCurrentChannels.remove(indexToRemove);
            this.animPrevTime.remove(name);
            this.animCurrentFrame.remove(name);
            ((ArrayList)this.animationEvents.get(name)).clear();
         }
      } else {
         System.out.println("The animation called " + name + " doesn't exist!");
      }

   }

   public abstract void stopAnimation(String var1);

   public void animationsUpdate() {
      Iterator it = this.animCurrentChannels.iterator();

      while(it.hasNext()) {
         Channel anim = (Channel)it.next();
         float prevFrame = (Float)this.animCurrentFrame.get(anim.name);
         boolean animStatus = updateAnimation(this.animatedEntity, anim, this.animPrevTime, this.animCurrentFrame);
         if (this.animCurrentFrame.get(anim.name) != null) {
            this.fireAnimationEvent(anim, prevFrame, (Float)this.animCurrentFrame.get(anim.name));
         }

         if (!animStatus) {
            it.remove();
            this.animPrevTime.remove(anim.name);
            this.animCurrentFrame.remove(anim.name);
            ((ArrayList)this.animationEvents.get(anim.name)).clear();
         }
      }

   }

   public boolean isAnimationActive(String name) {
      boolean animAlreadyUsed = false;
      Iterator var3 = this.animatedEntity.getAnimationHandler().animCurrentChannels.iterator();

      while(var3.hasNext()) {
         Channel anim = (Channel)var3.next();
         if (anim.name.equals(name)) {
            animAlreadyUsed = true;
            break;
         }
      }

      return animAlreadyUsed;
   }

   private void fireAnimationEvent(Channel anim, float prevFrame, float frame) {
      if (isWorldRemote(this.animatedEntity)) {
         this.fireAnimationEventClientSide(anim, prevFrame, frame);
      } else {
         this.fireAnimationEventServerSide(anim, prevFrame, frame);
      }

   }

   @SideOnly(Side.CLIENT)
   public abstract void fireAnimationEventClientSide(Channel var1, float var2, float var3);

   public abstract void fireAnimationEventServerSide(Channel var1, float var2, float var3);

   public boolean alreadyCalledEvent(String animName, String eventName) {
      if (this.animationEvents.get(animName) == null) {
         System.out.println("Cannot check for event " + eventName + "! Animation " + animName + "does not exist or is not active.");
         return true;
      } else {
         return ((ArrayList)this.animationEvents.get(animName)).contains(eventName);
      }
   }

   public void setCalledEvent(String animName, String eventName) {
      if (this.animationEvents.get(animName) != null) {
         ((ArrayList)this.animationEvents.get(animName)).add(eventName);
      } else {
         System.out.println("Cannot set event " + eventName + "! Animation " + animName + "does not exist or is not active.");
      }

   }

   public static boolean updateAnimation(IMCAnimatedEntity entity, Channel channel, HashMap<String, Long> prevTimeAnim, HashMap<String, Float> prevFrameAnim) {
      long prevTime;
      if (!FMLCommonHandler.instance().getEffectiveSide().isServer() && (!FMLCommonHandler.instance().getEffectiveSide().isClient() || isGamePaused())) {
         prevTime = System.nanoTime();
         prevTimeAnim.put(channel.name, prevTime);
         return true;
      } else if (channel.mode != 3) {
         prevTime = (Long)prevTimeAnim.get(channel.name);
         float prevFrame = (Float)prevFrameAnim.get(channel.name);
         long currentTime = System.nanoTime();
         double deltaTime = (double)(currentTime - prevTime) / 1.0E9D;
         float numberOfSkippedFrames = (float)(deltaTime * (double)channel.fps);
         float currentFrame = prevFrame + numberOfSkippedFrames;
         if (currentFrame < (float)(channel.totalFrames - 1)) {
            prevTimeAnim.put(channel.name, currentTime);
            prevFrameAnim.put(channel.name, currentFrame);
            return true;
         } else if (channel.mode == 1) {
            prevTimeAnim.put(channel.name, currentTime);
            prevFrameAnim.put(channel.name, 0.0F);
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   private static boolean isGamePaused() {
      Minecraft MC = Minecraft.getMinecraft();
      return MC.isSingleplayer() && MC.currentScreen != null && MC.currentScreen.doesGuiPauseGame() && !MC.getIntegratedServer().getPublic();
   }

   @SideOnly(Side.CLIENT)
   public static void performAnimationInModel(HashMap<String, MCAModelRenderer> parts, IMCAnimatedEntity entity) {
      Iterator var2 = parts.entrySet().iterator();

      label117:
      while(var2.hasNext()) {
         Entry<String, MCAModelRenderer> entry = (Entry)var2.next();
         String boxName = (String)entry.getKey();
         MCAModelRenderer box = (MCAModelRenderer)entry.getValue();
         boolean anyRotationApplied = false;
         boolean anyTranslationApplied = false;
         boolean anyCustomAnimationRunning = false;
         Iterator var9 = entity.getAnimationHandler().animCurrentChannels.iterator();

         while(true) {
            while(true) {
               while(var9.hasNext()) {
                  Channel channel = (Channel)var9.next();
                  if (channel.mode != 3) {
                     float currentFrame = (Float)entity.getAnimationHandler().animCurrentFrame.get(channel.name);
                     KeyFrame prevRotationKeyFrame = channel.getPreviousRotationKeyFrameForBox(boxName, (Float)entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                     int prevRotationKeyFramePosition = prevRotationKeyFrame != null ? channel.getKeyFramePosition(prevRotationKeyFrame) : 0;
                     KeyFrame nextRotationKeyFrame = channel.getNextRotationKeyFrameForBox(boxName, (Float)entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                     int nextRotationKeyFramePosition = nextRotationKeyFrame != null ? channel.getKeyFramePosition(nextRotationKeyFrame) : 0;
                     float SLERPProgress = (currentFrame - (float)prevRotationKeyFramePosition) / (float)(nextRotationKeyFramePosition - prevRotationKeyFramePosition);
                     if (SLERPProgress > 1.0F || SLERPProgress < 0.0F) {
                        SLERPProgress = 1.0F;
                     }

                     Quaternion currentQuat;
                     if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame == null && nextRotationKeyFramePosition != 0) {
                        currentQuat = new Quaternion();
                        currentQuat.slerp(((MCAModelRenderer)parts.get(boxName)).getDefaultRotationAsQuaternion(), (Quaternion)nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                        box.getRotationMatrix().set(currentQuat).transpose();
                        anyRotationApplied = true;
                     } else if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame != null && nextRotationKeyFramePosition != 0) {
                        currentQuat = new Quaternion();
                        currentQuat.slerp((Quaternion)prevRotationKeyFrame.modelRenderersRotations.get(boxName), (Quaternion)nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                        box.getRotationMatrix().set(currentQuat).transpose();
                        anyRotationApplied = true;
                     } else if (prevRotationKeyFramePosition != 0 && nextRotationKeyFramePosition != 0) {
                        currentQuat = new Quaternion();
                        currentQuat.slerp((Quaternion)prevRotationKeyFrame.modelRenderersRotations.get(boxName), (Quaternion)nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                        box.getRotationMatrix().set(currentQuat).transpose();
                        anyRotationApplied = true;
                     }

                     KeyFrame prevTranslationKeyFrame = channel.getPreviousTranslationKeyFrameForBox(boxName, (Float)entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                     int prevTranslationsKeyFramePosition = prevTranslationKeyFrame != null ? channel.getKeyFramePosition(prevTranslationKeyFrame) : 0;
                     KeyFrame nextTranslationKeyFrame = channel.getNextTranslationKeyFrameForBox(boxName, (Float)entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                     int nextTranslationsKeyFramePosition = nextTranslationKeyFrame != null ? channel.getKeyFramePosition(nextTranslationKeyFrame) : 0;
                     float LERPProgress = (currentFrame - (float)prevTranslationsKeyFramePosition) / (float)(nextTranslationsKeyFramePosition - prevTranslationsKeyFramePosition);
                     if (LERPProgress > 1.0F) {
                        LERPProgress = 1.0F;
                     }

                     Vector3f startPosition;
                     Vector3f endPosition;
                     Vector3f currentPosition;
                     if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame == null && nextTranslationsKeyFramePosition != 0) {
                        startPosition = ((MCAModelRenderer)parts.get(boxName)).getPositionAsVector();
                        endPosition = (Vector3f)nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        box.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                        anyTranslationApplied = true;
                     } else if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame != null && nextTranslationsKeyFramePosition != 0) {
                        startPosition = (Vector3f)prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        endPosition = (Vector3f)nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        box.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                     } else if (prevTranslationsKeyFramePosition != 0 && nextTranslationsKeyFramePosition != 0) {
                        startPosition = (Vector3f)prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        endPosition = (Vector3f)nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        box.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                        anyTranslationApplied = true;
                     }
                  } else {
                     anyCustomAnimationRunning = true;
                     ((CustomChannel)channel).update(parts, entity);
                  }
               }

               if (!anyRotationApplied && !anyCustomAnimationRunning) {
                  box.resetRotationMatrix();
               }

               if (!anyTranslationApplied && !anyCustomAnimationRunning) {
                  box.resetRotationPoint();
               }
               continue label117;
            }
         }
      }

   }

   public static boolean isWorldRemote(IMCAnimatedEntity animatedEntity) {
      return ((Entity)animatedEntity).worldObj.isRemote;
   }
}
