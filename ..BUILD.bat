:start
@start /B 
gradlew clean build gsJar deobfGSJar
@pause >nul
@goto start