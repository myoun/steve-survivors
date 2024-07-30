package app.myoun.stevesurvivors

import app.myoun.stevesurvivors.attribute.SteveAttributes
import app.myoun.stevesurvivors.item.RuneItem
import app.myoun.stevesurvivors.item.SteveItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.server.MinecraftServer
import net.minecraft.text.Text
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

        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP)

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register { itemGroup ->
            for (pair in SteveAttributes.allVanilla) {
                val stack = RuneItem.createRuneItemStack(pair.attribute, pair.modifierValue)
                itemGroup.add(stack)
            }
        }

        ModifyItemAttributeModifiersCallback.EVENT.register { stack, slot, modifiers ->

        }

        ServerPlayConnectionEvents.JOIN.register { handler, sender, server ->

        }

        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            SERVER = server
            LOGGER.info(Registries.ATTRIBUTE.get(Identifier("minecraft", "generic.attack_speed"))?.translationKey)
        }
    }
}