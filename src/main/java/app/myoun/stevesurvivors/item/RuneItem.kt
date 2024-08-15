package app.myoun.stevesurvivors.item

import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.util.Rarity

class RuneItem : AttributeItem(
    Settings().rarity(Rarity.EPIC).fireproof()
) {

    companion object {
        fun createRuneItemStack(attribute: EntityAttribute, modifier: EntityAttributeModifier) =
            createAttributeItemStack(SteveItems.RUNE, attribute, modifier)
    }

    override fun getTranslationKey(): String {
        return "item.stevesurvivors.rune"
    }
}