package galaxyspace.core.client.animations.MCACommonLibrary.animation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Channel {
   public static final byte LINEAR = 0;
   public static final byte LOOP = 1;
   public static final byte CYCLE = 2;
   public static final byte CUSTOM = 3;
   public String name;
   public float fps;
   public int totalFrames;
   public HashMap<Integer, KeyFrame> keyFrames;
   public byte mode;

   public Channel(String _name) {
      this.keyFrames = new HashMap();
      this.mode = 0;
      this.name = _name;
      this.totalFrames = 0;
      this.initializeAllFrames();
   }

   public Channel(String _name, float _fps, int _totalFrames, byte _mode) {
      this(_name);
      this.fps = _fps;
      this.totalFrames = _totalFrames;
      this.mode = _mode;
   }

   protected void initializeAllFrames() {
   }

   public KeyFrame getPreviousRotationKeyFrameForBox(String boxName, float currentFrame) {
      int latestFramePosition = -1;
      KeyFrame latestKeyFrame = null;
      Iterator var5 = this.keyFrames.entrySet().iterator();

      while(var5.hasNext()) {
         Entry<Integer, KeyFrame> entry = (Entry)var5.next();
         Integer key = (Integer)entry.getKey();
         KeyFrame value = (KeyFrame)entry.getValue();
         if ((float)key <= currentFrame && key > latestFramePosition && value.useBoxInRotations(boxName)) {
            latestFramePosition = key;
            latestKeyFrame = value;
         }
      }

      return latestKeyFrame;
   }

   public KeyFrame getNextRotationKeyFrameForBox(String boxName, float currentFrame) {
      int nextFramePosition = -1;
      KeyFrame nextKeyFrame = null;
      Iterator var5 = this.keyFrames.entrySet().iterator();

      while(true) {
         Integer key;
         KeyFrame value;
         do {
            do {
               if (!var5.hasNext()) {
                  return nextKeyFrame;
               }

               Entry<Integer, KeyFrame> entry = (Entry)var5.next();
               key = (Integer)entry.getKey();
               value = (KeyFrame)entry.getValue();
            } while(!((float)key > currentFrame));
         } while(key >= nextFramePosition && nextFramePosition != -1);

         if (value.useBoxInRotations(boxName)) {
            nextFramePosition = key;
            nextKeyFrame = value;
         }
      }
   }

   public KeyFrame getPreviousTranslationKeyFrameForBox(String boxName, float currentFrame) {
      int latestFramePosition = -1;
      KeyFrame latestKeyFrame = null;
      Iterator var5 = this.keyFrames.entrySet().iterator();

      while(var5.hasNext()) {
         Entry<Integer, KeyFrame> entry = (Entry)var5.next();
         Integer key = (Integer)entry.getKey();
         KeyFrame value = (KeyFrame)entry.getValue();
         if ((float)key <= currentFrame && key > latestFramePosition && value.useBoxInTranslations(boxName)) {
            latestFramePosition = key;
            latestKeyFrame = value;
         }
      }

      return latestKeyFrame;
   }

   public KeyFrame getNextTranslationKeyFrameForBox(String boxName, float currentFrame) {
      int nextFramePosition = -1;
      KeyFrame nextKeyFrame = null;
      Iterator var5 = this.keyFrames.entrySet().iterator();

      while(true) {
         Integer key;
         KeyFrame value;
         do {
            do {
               if (!var5.hasNext()) {
                  return nextKeyFrame;
               }

               Entry<Integer, KeyFrame> entry = (Entry)var5.next();
               key = (Integer)entry.getKey();
               value = (KeyFrame)entry.getValue();
            } while(!((float)key > currentFrame));
         } while(key >= nextFramePosition && nextFramePosition != -1);

         if (value.useBoxInTranslations(boxName)) {
            nextFramePosition = key;
            nextKeyFrame = value;
         }
      }
   }

   public int getKeyFramePosition(KeyFrame keyFrame) {
      if (keyFrame != null) {
         Iterator var2 = this.keyFrames.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<Integer, KeyFrame> entry = (Entry)var2.next();
            Integer key = (Integer)entry.getKey();
            KeyFrame keyframe = (KeyFrame)entry.getValue();
            if (keyframe == keyFrame) {
               return key;
            }
         }
      }

      return -1;
   }

   public static boolean shouldAnimationStop() {
      return false;
   }
}
