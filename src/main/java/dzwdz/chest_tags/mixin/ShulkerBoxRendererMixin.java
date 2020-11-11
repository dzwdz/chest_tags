package dzwdz.chest_tags.mixin;

import dzwdz.chest_tags.ChestTags;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public abstract class ShulkerBoxRendererMixin extends BlockEntityRenderer {
    public ShulkerBoxRendererMixin(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Inject(
            at = @At("TAIL"),
            method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"
    )
    private void renderTag(ShulkerBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo callbackInfo) {
        World world = entity.getWorld();
        if (world == null) return;
        Text text = entity.getCustomName();

        if (text != null) {
            matrices.push();
            matrices.translate(.5, 1.5, .5);
            matrices.multiply(dispatcher.camera.getRotation());

            ChestTags.renderTag(matrices, text, vertexConsumers, light, true, true);

            matrices.pop();
        }
    }
}
