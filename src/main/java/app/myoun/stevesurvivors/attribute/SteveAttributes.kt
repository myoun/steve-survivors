package app.myoun.stevesurvivors.attribute

import net.minecraft.entity.attribute.ClampedEntityAttribute
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import java.util.UUID

object SteveAttributes {
    val ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")
    val ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3")

    val PHYSICAL_ATTACK = AttributePair(
        ClampedEntityAttribute("attribute.name.stevesurvivors.physical_attack", 1.0, 1.0, 128.0),
        EntityAttributeModifier(nameUUID("physical_attack"), "stevesurvivors.physical_attack", 2.0, EntityAttributeModifier.Operation.ADDITION)
    )

    val MAGIC_ATTACK = AttributePair(
        ClampedEntityAttribute("attribute.name.stevesurvivors.magic_attack", 0.0, 0.0, 128.0),
        EntityAttributeModifier(nameUUID("magic_attack"), "stevesurvivors.magic_attack", 3.0, EntityAttributeModifier.Operation.ADDITION)
    )

    val CRITICAL_CHANCE = AttributePair(
        ClampedEntityAttribute("attribute.name.stevesurvivors.critical_chance", 0.0, 0.0, 100.0),
        EntityAttributeModifier(nameUUID("critical_change"), "stevesurvivors.critical_chance", 20.0, EntityAttributeModifier.Operation.ADDITION)
    )

    val all = listOf(PHYSICAL_ATTACK, MAGIC_ATTACK, CRITICAL_CHANCE)

    val HEALTH = AttributePair(
        fromVanilla("generic.max_health"),
        EntityAttributeModifier(nameUUID("max_health"),"generic.max_health", 1.0, EntityAttributeModifier.Operation.ADDITION)
    )

    val ATTACK_SPEED = AttributePair(
        fromVanilla("generic.attack_speed"),
        EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "generic.attack_speed", 0.5, EntityAttributeModifier.Operation.ADDITION)
    )

    val MOVEMENT_SPEED = AttributePair(
        fromVanilla("generic.movement_speed"),
        EntityAttributeModifier(nameUUID("movement_speed"), "generic.movement_speed", 0.02, EntityAttributeModifier.Operation.ADDITION)
    )

    val allVanilla = all + listOf(HEALTH, ATTACK_SPEED, MOVEMENT_SPEED)

    val baseValueMap = allVanilla.map { it.modifierValue.id to it }.toMap()

    fun fromVanilla(name: String): EntityAttribute {
        return Registries.ATTRIBUTE.get(Identifier("minecraft", name))!!
    }

    fun nameUUID(name: String): UUID {
        return UUID.nameUUIDFromBytes(name.toByteArray(Charsets.UTF_8))
    }
}