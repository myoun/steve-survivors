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

    val allVanilla = all + listOf(HEALTH)

    fun fromVanilla(name: String): EntityAttribute {
        return Registries.ATTRIBUTE.get(Identifier("minecraft", name))!!
    }
}