package app.myoun.stevesurvivors.attribute

import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier

data class AttributePair(val attribute: EntityAttribute, val modifierValue: EntityAttributeModifier)

fun AttributePair.withValue(value: Double, operation: EntityAttributeModifier.Operation): AttributePair {
    return AttributePair(this.attribute, EntityAttributeModifier(this.modifierValue.id, this.modifierValue.name, value, operation))
}

fun AttributePair.withValue(value: Double): AttributePair {
    return withValue(value, this.modifierValue.operation)
}

val EntityAttributeModifier.value: Double
    get() = toNbt().getDouble("Amount")

val EntityAttributeModifier.name: String
    get() = toNbt().getString("Name")