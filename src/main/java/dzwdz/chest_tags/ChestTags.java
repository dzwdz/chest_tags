package dzwdz.chest_tags;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

public class ChestTags implements ModInitializer {
    @Override
    public void onInitialize() {

    }

    public static void renderTag(MatrixStack matrices, Text text, VertexConsumerProvider vertexConsumers, int light, boolean centered) {
        matrices.push();
        matrices.scale(-0.025F, -0.025F, 0.025F);

        Matrix4f matrix4f = matrices.peek().getModel();
        float x = 0;
        if (centered) {
            x = -MinecraftClient.getInstance().textRenderer.getWidth(text) / 2f;
        }
        MinecraftClient.getInstance().textRenderer
                .draw(text, x, 0, -1, false, matrix4f, vertexConsumers, false, 0, light);
        matrices.pop();
    }
}
