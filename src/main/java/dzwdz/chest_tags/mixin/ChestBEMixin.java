package dzwdz.chest_tags.mixin;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(ChestBlockEntity.class)
public abstract class ChestBEMixin extends LootableContainerBlockEntity {
    public ChestBEMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Intrinsic
    @Override
    public CompoundTag toInitialChunkDataTag() {
        return super.toInitialChunkDataTag();
    }

    @Inject(
            at = @At("TAIL"),
            method = "toInitialChunkDataTag()Lnet/minecraft/nbt/CompoundTag;",
            cancellable = true
    )
    private void addChestName(CallbackInfoReturnable<CompoundTag> ci) {
        if (hasCustomName())
            ci.getReturnValue().putString("CustomName", Text.Serializer.toJson(getCustomName()));
    }
}
