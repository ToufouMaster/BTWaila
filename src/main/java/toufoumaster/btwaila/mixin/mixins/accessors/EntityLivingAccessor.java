package toufoumaster.btwaila.mixin.mixins.accessors;

import net.minecraft.core.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLiving.class)
public interface EntityLivingAccessor {
    @Invoker("getDropItemId")
    int callGetDropItemId();
}
