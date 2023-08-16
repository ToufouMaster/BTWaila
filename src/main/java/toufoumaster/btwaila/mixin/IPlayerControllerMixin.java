package toufoumaster.btwaila.mixin;

import net.minecraft.client.player.controller.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PlayerController.class, remap = false)
public interface IPlayerControllerMixin {
    @Accessor float getCurrentDamage();
}
