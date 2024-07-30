package app.myoun.stevesurvivors.mixin;

import app.myoun.stevesurvivors.attribute.SteveAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    /**
     * @author myoun
     * @reason To add new attribute to player entity.
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createPlayerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED)
                .add(EntityAttributes.GENERIC_LUCK)
                // new attributes
                .add(SteveAttributes.INSTANCE.getPHYSICAL_ATTACK().getAttribute(), 1.0f);
    }
}
