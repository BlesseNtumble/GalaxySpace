package galaxyspace.core.client.animations.MCACommonLibrary.animation;

import galaxyspace.core.client.animations.MCAClientLibrary.MCAModelRenderer;
import galaxyspace.core.client.animations.MCACommonLibrary.IMCAnimatedEntity;
import java.util.HashMap;

public class CustomChannel extends Channel {
   public CustomChannel(String _name) {
      super(_name);
      this.mode = 3;
   }

   protected void initializeAllFrames() {
   }

   public KeyFrame getPreviousRotationKeyFrameForBox(String boxName, float currentFrame) {
      return null;
   }

   public KeyFrame getNextRotationKeyFrameForBox(String boxName, float currentFrame) {
      return null;
   }

   public KeyFrame getPreviousTranslationKeyFrameForBox(String boxName, float currentFrame) {
      return null;
   }

   public KeyFrame getNextTranslationKeyFrameForBox(String boxName, float currentFrame) {
      return null;
   }

   public int getKeyFramePosition(KeyFrame keyFrame) {
      return -1;
   }

   public void update(HashMap<String, MCAModelRenderer> parts, IMCAnimatedEntity entity) {
   }
}
