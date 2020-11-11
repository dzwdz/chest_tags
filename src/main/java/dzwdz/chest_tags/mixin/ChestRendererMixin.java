package dzwdz.chest_tags.mixin;

import dzwdz.chest_tags.ChestTags;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
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
        //ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? (ChestType)blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();

        if (block instanceof AbstractChestBlock) {
            Text text = ((ChestBlockEntity)entity).getCustomName();
            if (text != null)
                ChestTags.renderTag(matrices, entity, text, vertexConsumers, light);
        }
    }
}
