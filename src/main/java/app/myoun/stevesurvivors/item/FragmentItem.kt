package app.myoun.stevesurvivors.item

import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.util.Rarity

class FragmentItem : AttributeItem(
    Settings().rarity(Rarity.RARE).fireproof()
) {

    companion object {
        fun createFragmentItemStack(attribute: EntityAttribute, modifier: EntityAttributeModifier) =
            createAttributeItemStack(SteveItems.FRAGMENT, attribute, modifier)
    }

    override fun getTranslationKey(): String {
        return "item.stevesurvivors.fragment"
    }

}