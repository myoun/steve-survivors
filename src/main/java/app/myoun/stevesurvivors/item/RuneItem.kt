package app.myoun.stevesurvivors.item

import app.myoun.stevesurvivors.attribute.AttributeModifierValue
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.util.Rarity

class RuneItem : AttributeItem(
    Settings().rarity(Rarity.EPIC).fireproof()
) {

    companion object {
        fun createRuneItemStack(attribute: EntityAttribute, modifier: AttributeModifierValue) =
            createAttributeItemStack(SteveItems.RUNE, attribute, modifier)
    }

    override fun getTranslationKey(): String {
        return "item.stevesurvivors.rune"
    }
}