package toufoumaster.btwaila.mixin.mixins.accessors;

import net.minecraft.client.player.controller.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PlayerController.class, remap = false)
public interface IPlayerControllerAccessor {
    @Accessor float getCurrentDamage();
}
