package app.myoun.stevesurvivors.item.weapon

import app.myoun.stevesurvivors.attribute.AttributePair
import app.myoun.stevesurvivors.attribute.SteveAttributes
import app.myoun.stevesurvivors.attribute.name
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.Item

abstract class StatWeaponItem(val attributePairs: Collection<AttributePair>, val slots: Collection<EquipmentSlot>, settings: Settings) : Item(settings) {

    val attributeModifiers = ImmutableMultimap.builder< EntityAttribute, EntityAttributeModifier>().also { builder ->
        attributePairs.forEach { pair ->
            if (pair.modifierValue.id == SteveAttributes.ATTACK_SPEED_MODIFIER_ID) {
                builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", pair.modifierValue.value, EntityAttributeModifier.Operation.ADDITION))
            } else {
                builder.put(pair.attribute, EntityAttributeModifier(pair.modifierValue.name, pair.modifierValue.value, pair.modifierValue.operation))
            }
        }
    }.build()

    override fun getAttributeModifiers(slot: EquipmentSlot?): Multimap<EntityAttribute?, EntityAttributeModifier?>? {
        return if (slot in slots) attributeModifiers
        else super.getAttributeModifiers(slot)
    }


}