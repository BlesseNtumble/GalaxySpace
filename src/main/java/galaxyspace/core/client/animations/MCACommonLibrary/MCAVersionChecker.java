package galaxyspace.core.client.animations.MCACommonLibrary;

public class MCAVersionChecker {
   public static final int VersionID = 2;

   public static void checkForLibraryVersion(Class modelClass, int modelVersion) {
      if (modelVersion > 2) {
         System.out.println("MCA WARNING: " + modelClass.getName() + " needs a newer version of the library (" + modelVersion + "). Things could go wrong!");
      }

   }
}
