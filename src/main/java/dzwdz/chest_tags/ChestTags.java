package dzwdz.chest_tags;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

public class ChestTags implements ModInitializer {
    @Override
    public void onInitialize() {

    }

    public static void renderTag(MatrixStack matrices, BlockEntity entity, Text text, VertexConsumerProvider vertexConsumers, int light) {
        BlockState blockState = entity.getCachedState();

        matrices.push();
        matrices.translate(.5, 0, .5);
        float f = blockState.get(ChestBlock.FACING).asRotation();
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180-f));
        matrices.translate(.3, .4, -.45);
        matrices.scale(-0.025F, -0.025F, 0.025F);

        Matrix4f matrix4f = matrices.peek().getModel();
        float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
        int j = (int)(g * 255.0F) << 24;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        textRenderer.draw(text, 0, 0, -1, true, matrix4f, vertexConsumers, false, 0, light);

        matrices.pop();
    }
}
