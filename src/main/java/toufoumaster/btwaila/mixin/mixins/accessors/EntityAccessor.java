package toufoumaster.btwaila.mixin.mixins.accessors;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Entity.class, remap = false)
public interface EntityAccessor {
    @Invoker
    void callAddAdditionalSaveData(CompoundTag tag);

    @Invoker
    void callReadAdditionalSaveData(CompoundTag tag);
}
