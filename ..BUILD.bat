:start
@start /B 
gradlew clean build coreJar gsJar smJar deobfJar deobfGSJar
@pause >nul
@goto start