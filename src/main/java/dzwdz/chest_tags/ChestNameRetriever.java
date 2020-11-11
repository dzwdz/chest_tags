package dzwdz.chest_tags;

import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class ChestNameRetriever implements DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Supplier<Text>> {
    public Supplier<Text> getFromBoth(final ChestBlockEntity chestBlockEntity, final ChestBlockEntity chestBlockEntity2) {
        return () -> {
            if (chestBlockEntity.hasCustomName()) {
                return chestBlockEntity.getDisplayName();
            } else {
                return chestBlockEntity2.hasCustomName() ? chestBlockEntity2.getDisplayName() : null;
            }
        };
    }

    public Supplier<Text> getFrom(ChestBlockEntity chestBlockEntity) {
        return chestBlockEntity::getCustomName;
    }

    public Supplier<Text> getFallback() {
        return () -> null;
    }
}