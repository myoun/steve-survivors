package app.myoun.stevesurvivors

import app.myoun.stevesurvivors.attribute.SteveAttributes
import app.myoun.stevesurvivors.item.FragmentItem
import app.myoun.stevesurvivors.item.RuneItem
import app.myoun.stevesurvivors.item.SteveItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.server.MinecraftServer
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

object SteveSurvivors : ModInitializer {

    @JvmStatic
    val ID = "stevesurvivors"

    @JvmStatic
    val LOGGER = LogManager.getLogger(ID)

    @JvmStatic
    lateinit var SERVER: MinecraftServer

    @JvmStatic
    val isServerInitialized: Boolean
        get() = ::SERVER.isInitialized

    @JvmStatic
    val ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ID, "item_group"))

    @JvmStatic
    val ITEM_GROUP = FabricItemGroup.builder()
        .icon { ->
            ItemStack(Items.MAGMA_CREAM)
        }.displayName(Text.translatable("itemGroup.stevesurvivors"))
        .build()

    override fun onInitialize() {
        for (pair in SteveAttributes.all) {
            val attribute = pair.attribute
            val identifier = Identifier(ID, attribute.translationKey.split(".").last())
            Registry.register(Registries.ATTRIBUTE, identifier, attribute)
        }

        Registry.register(Registries.ITEM, Identifier(ID, "rune"), SteveItems.RUNE)
        Registry.register(Registries.ITEM, Identifier(ID, "fragment"), SteveItems.FRAGMENT)

        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP)

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register { itemGroup ->
            var lastRune: ItemStack? = null
            var lastFragment: ItemStack? = null
            for (pair in SteveAttributes.allVanilla) {
                val runeStack = RuneItem.createRuneItemStack(pair.attribute, pair.modifierValue)
                val fragmentStack = FragmentItem.createFragmentItemStack(pair.attribute, pair.modifierValue)

                if (lastRune == null) {
                    itemGroup.add(runeStack)
                } else {
                    itemGroup.addAfter(lastRune, runeStack)
                }

                if (lastFragment == null) {
                    itemGroup.add(fragmentStack)
                } else {
                    itemGroup.addAfter(lastFragment, fragmentStack)
                }

                lastRune = runeStack
                lastFragment = fragmentStack
            }
        }

        AttackEntityCallback.EVENT.register { player, world, hand, entity, hitResult ->
            if (player.isSpectator) return@register ActionResult.PASS
            val physicalAttack = player.getAttributeValue(SteveAttributes.PHYSICAL_ATTACK.attribute).toFloat()
            entity.damage(SteveDamageTypes.of(world, SteveDamageTypes.PHYSICAL), physicalAttack)
            ActionResult.SUCCESS
        }

        ModifyItemAttributeModifiersCallback.EVENT.register { stack, slot, modifiers ->
        }

        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            SERVER = server
            LOGGER.info(Registries.ATTRIBUTE.get(Identifier("minecraft", "generic.attack_speed"))?.translationKey)
        }


    }
}