package app.myoun.stevesurvivors.item.weapon

import app.myoun.stevesurvivors.attribute.SteveAttributes
import app.myoun.stevesurvivors.attribute.withValue
import net.minecraft.entity.EquipmentSlot

class DestroyItem : StatWeaponItem(listOf(
    SteveAttributes.PHYSICAL_ATTACK.withValue(7.0),
    SteveAttributes.CRITICAL_CHANCE.withValue(25.0),
    SteveAttributes.ATTACK_SPEED.withValue(-2.2)
), listOf(
    EquipmentSlot.MAINHAND
),Settings().fireproof().maxCount(1))