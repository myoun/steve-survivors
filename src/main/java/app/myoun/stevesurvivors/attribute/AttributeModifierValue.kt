package app.myoun.stevesurvivors.attribute

import net.minecraft.entity.attribute.EntityAttributeModifier

data class AttributeModifierValue(val name: String, val value: Double, val operation: EntityAttributeModifier.Operation)
