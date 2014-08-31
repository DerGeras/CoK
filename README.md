CoK
===

Clash of Kingdoms Minecraft Mod

GitHub Page: http://dergeras.github.io/CoK/


===

Setup: gradlew setupDecompWorkspace eclipse

Arguments needed in the runtime configuration of eclipse:

Client:

Program arguments:

--version 1.6 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --accessToken FML --userProperties {}

VM Arguments:

-Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.coreMods.load=de.minestar.cok.preloader.CoKPreloader

Server:

VM Arguments:
-Dfml.coreMods.load=de.minestar.cok.preloader.CoKPreloader