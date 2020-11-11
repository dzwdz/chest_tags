package dzwdz.chest_tags.mixin;

import dzwdz.chest_tags.ChestNameRetriever;
import dzwdz.chest_tags.ChestTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestRendererMixin {
    @Inject(
            at = @At("TAIL"),
            method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"
    )
    private void renderTag(BlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo callbackInfo) {
        if (!(entity instanceof ChestBlockEntity)) return;

        World world = entity.getWorld();
        if (world == null) return;
        BlockState blockState = entity.getCachedState();
        Block block = blockState.getBlock();

        if (block instanceof ChestBlock) {
            DoubleBlockProperties.Type type = ChestBlock.getDoubleBlockType(blockState);
            if (type == DoubleBlockProperties.Type.SECOND) return;
            boolean centered = type == DoubleBlockProperties.Type.SINGLE;

            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource2 = ((ChestBlock)block).getBlockEntitySource(blockState, world, entity.getPos(), true);
            Text text = propertySource2.apply(new ChestNameRetriever()).get();

            if (text != null) {
                matrices.push();
                matrices.translate(.5, 0, .5);
                float f = blockState.get(ChestBlock.FACING).asRotation();
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180-f));
                matrices.translate(0, .4, -.45);
                if (!centered)
                    matrices.translate(.3, 0, 0);

                ChestTags.renderTag(matrices, text, vertexConsumers, light, centered);

                matrices.pop();
            }
        }
    }
}
