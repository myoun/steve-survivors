package app.myoun.stevesurvivors.item

import app.myoun.stevesurvivors.attribute.AttributeModifierValue
import com.google.common.collect.Multimap
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.TrinketItem
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import java.util.UUID

class RuneItem : TrinketItem(
    Settings().rarity(Rarity.EPIC).fireproof()
) {

    companion object {

        fun createRuneItemStack(attribute: EntityAttribute, modifier: AttributeModifierValue): ItemStack? {
            val attributeId = Registries.ATTRIBUTE.getId(attribute) ?: return null
            val attributeTag = NbtCompound().also { tag ->
                tag.putString("Name", attributeId.toString())
                tag.putDouble("Amount", modifier.value)
                tag.putString("Operation", modifier.operation.name)
            }
            val stack = ItemStack(SteveItems.RUNE)
            stack.orCreateNbt.also { nbt ->
                nbt.put("Attribute", attributeTag)
            }
            return stack
        }
    }

    override fun getDefaultStack(): ItemStack? {
        val attributeTag = NbtCompound().also { tag ->
            tag.putString("Name", "minecraft:generic.luck")
            tag.putDouble("Amount", 1.0)
            tag.putString("Operation", "addition")
        }
        val stack = super.getDefaultStack()
        stack.orCreateNbt.also { nbt ->
            nbt.put("Attribute", attributeTag)
        }
        return stack
    }

    override fun getTranslationKey(): String {
        return "item.stevesurvivors.rune"
    }


    override fun getName(): Text? {
        return Text.translatable(this.translationKey, "Unknown")
    }

    override fun getName(stack: ItemStack?): Text? {
        val nbt = stack?.orCreateNbt
        if (nbt == null) return Text.literal("Unknown Rune")

        val key = Identifier.tryParse(nbt.getCompound("Attribute").getString("Name"))
        if (key == null) return Text.literal("Unknown Rune")
        val nameKey = Registries.ATTRIBUTE.get(key)?.translationKey ?: return Text.literal("Unknown Rune")
        return Text.translatable(this.translationKey, Text.translatable(nameKey))
    }

    override fun getModifiers(
        stack: ItemStack,
        slot: SlotReference?,
        entity: LivingEntity?,
        uuid: UUID?
    ): Multimap<EntityAttribute?, EntityAttributeModifier?>? {
        var modifiers = super.getModifiers(stack, slot, entity, uuid)
        val nbt = stack.orCreateNbt
        val attributeTag = nbt.getCompound("Attribute")

        val name = attributeTag.getString("Name")
        val amount = attributeTag.getDouble("Amount")
        val rawOperation = attributeTag.getString("Operation")
        val operation = when (rawOperation) {
            "addition" -> EntityAttributeModifier.Operation.ADDITION
            "multiply_base" -> EntityAttributeModifier.Operation.MULTIPLY_BASE
            "multiply_total" -> EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            else -> EntityAttributeModifier.Operation.ADDITION
        }

        val attributeIdentifier = Identifier(name)
        val attribute = Registries.ATTRIBUTE.get(attributeIdentifier) ?: return modifiers
        val modifier = EntityAttributeModifier(uuid, name, amount*stack.count, operation)

        modifiers.put(attribute, modifier)

        return modifiers
    }
}