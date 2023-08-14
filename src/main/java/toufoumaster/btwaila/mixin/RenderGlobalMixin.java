package toufoumaster.btwaila.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;

@Mixin(value = RenderGlobal.class, remap = false)
public class RenderGlobalMixin {
    @Shadow private Minecraft mc;
    @Shadow private World worldObj;

    @Inject( method = "drawSelectionBox", at = @At("TAIL"))
    public void drawSelectionBox(ICamera camera, HitResult hitResult, float renderPartialTicks, CallbackInfo ci) {
        if (this.mc.gameSettings.immersiveMode.drawOutline()) {
            if (hitResult.hitType == HitResult.HitType.TILE) {
                int blockMetadata = this.worldObj.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z);
                BTWaila.blockToDraw = hitResult;
                BTWaila.blockMetadata = blockMetadata;

                BTWaila.showBlockOverlay = true;
            } else {
                BTWaila.entityToDraw = this.mc.objectMouseOver.entity;
                BTWaila.showEntityOverlay = true;
            }
        }
    }
}
