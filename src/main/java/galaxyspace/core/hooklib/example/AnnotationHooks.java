package galaxyspace.core.hooklib.example;

import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.asm.Hook.ReturnValue;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeHooks;

public class AnnotationHooks {

    /**
     * Цель: при каждом ресайзе окна выводить в консоль новый размер
     */
    @Hook
    public static void resize(Minecraft mc, int x, int y) {
        System.out.println("Resize, x=" + x + ", y=" + y);
    }

    /**
     * Цель: уменьшить вдвое показатели брони у всех игроков.
     * P.S: фордж перехватывает получение показателя брони, ну а мы перехватим перехватчик :D
     */
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getTotalArmorValue(ForgeHooks fh, EntityPlayer player, @ReturnValue int returnValue) {
        return returnValue / 2;
    }

    /**
     * Цель: запретить возможность телепортироваться в ад и обратно чаще, чем раз в пять секунд.
     */
    @Hook(returnCondition = ReturnCondition.ON_TRUE, intReturnConstant = 100)
    public static boolean getPortalCooldown(EntityPlayer player) {
        return player.dimension == 0;
    }

    /**
     * Цель: уменьшить вдвое яркость сущностей, которые выше полутора блоков.
     * Проверка на высоту в одном методе, пересчёт яркости - в другом.
     */
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ON_TRUE, returnAnotherMethod = "getBrightness")
    public static boolean getBrightnessForRender(Entity entity, float f) {
        return entity.height > 1.5f;
    }

    public static int getBrightness(Entity entity, float f) {
        int oldValue = 0;
        int j = ((oldValue >> 20) & 15) / 2;
        int k = ((oldValue >> 4) & 15) / 2;
        return j << 20 | k << 4;
    }
}
