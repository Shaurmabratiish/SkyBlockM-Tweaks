package despairscent.skyblockm.tweaks.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static despairscent.skyblockm.tweaks.ModUtils.CONFIG;

@Mixin(EntityRenderDispatcher.class)
public class CullingEntitiesMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void onRenderEntity(
            E entity, double x, double y, double z, float yaw, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

        if (!CONFIG.cullingEntities.enabled) return;

        if (entity instanceof ArmorStandEntity || entity instanceof DisplayEntity) {

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            double distanceSq = client.player.squaredDistanceTo(entity);
            double configDist = CONFIG.cullingEntities.distance;
            double maxDistSq = configDist * configDist;

            if (distanceSq <= maxDistSq) return;

            World world = entity.getWorld();
            if (world.getBlockState(entity.getBlockPos()).isOf(net.minecraft.block.Blocks.BARRIER)) {
                ci.cancel();
            }
        }
    }
}