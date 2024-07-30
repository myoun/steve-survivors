package app.myoun.stevesurvivors.attribute

import net.minecraft.entity.attribute.ClampedEntityAttribute
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

object SteveAttributes {
    val PHYSICAL_ATTACK = AttributePair(
        ClampedEntityAttribute("attribute.name.stevesurvivors.physical_attack", 1.0, 1.0, 128.0),
        AttributeModifierValue("stevesurvivors.physical_attack", 2.0, EntityAttributeModifier.Operation.ADDITION)
    )

    val all = listOf(PHYSICAL_ATTACK)

    val HEALTH = AttributePair(
        fromVanilla("generic.max_health"),
        AttributeModifierValue("generic.max_health", 1.0, EntityAttributeModifier.Operation.ADDITION)
    )

    val ATTACK_SPEED = AttributePair(
        fromVanilla("generic.attack_speed"),
        AttributeModifierValue("generic.attack_speed", 0.5, EntityAttributeModifier.Operation.ADDITION)
    )

    val MOVEMENT_SPEED = AttributePair(
        fromVanilla("generic.movement_speed"),
        AttributeModifierValue("generic.movement_speed", 0.02, EntityAttributeModifier.Operation.ADDITION)
    )

    val allVanilla = all + listOf(HEALTH, ATTACK_SPEED, MOVEMENT_SPEED)

    fun fromVanilla(name: String): EntityAttribute {
        return Registries.ATTRIBUTE.get(Identifier("minecraft", name))!!
    }
}