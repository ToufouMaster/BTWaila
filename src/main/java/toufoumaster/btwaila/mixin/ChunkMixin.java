package toufoumaster.btwaila.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Chunk.class, remap = false, priority = 999)
public class ChunkMixin {
	@Redirect(method = "setBlockID(IIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;onBlockRemoval(Lnet/minecraft/core/world/World;III)V"))
	private void patchDoubles(Block instance, World world, int x, int y, int z){
		if (!world.isClientSide){
			instance.onBlockRemoval(world, x, y, z);
		}
	}
}
