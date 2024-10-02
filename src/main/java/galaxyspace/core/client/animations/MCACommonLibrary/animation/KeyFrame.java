package galaxyspace.core.client.animations.MCACommonLibrary.animation;

import galaxyspace.core.client.animations.MCACommonLibrary.math.Quaternion;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Vector3f;
import java.util.HashMap;

public class KeyFrame {
   public HashMap<String, Quaternion> modelRenderersRotations = new HashMap();
   public HashMap<String, Vector3f> modelRenderersTranslations = new HashMap();

   public boolean useBoxInRotations(String boxName) {
      return this.modelRenderersRotations.get(boxName) != null;
   }

   public boolean useBoxInTranslations(String boxName) {
      return this.modelRenderersTranslations.get(boxName) != null;
   }
}
