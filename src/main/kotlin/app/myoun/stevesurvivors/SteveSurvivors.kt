package app.myoun.stevesurvivors

import app.myoun.stevesurvivors.callback.StatChangedCallback
import app.myoun.stevesurvivors.stat.PlayerState
import app.myoun.stevesurvivors.stat.PlayerStats
import app.myoun.stevesurvivors.stat.StatValueType
import net.darkhax.attributefix.mixin.AccessorRangedAttribute
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import org.apache.logging.log4j.LogManager
import java.util.UUID

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
    val preJoinPlayers = hashMapOf<UUID, ServerPlayerEntity>()

    override fun onInitialize() {
        ServerPlayConnectionEvents.JOIN.register { handler, sender, server ->
            preJoinPlayers[handler.player.uuid] = handler.player
            val playerState = PlayerState.getPlayerState(handler.player)
            handler.player.sendMessage(
                Text.literal(
                    PlayerState.getPlayerState(handler.player).stats[PlayerStats.Health].toString()
                )
            )
        }

        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            SERVER = server
        }

        StatChangedCallback.EVENT.register { player, stat, value ->
            when (stat) {
                PlayerStats.Health -> {
                    val hpAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
                    hpAttribute?.baseValue = (value as Int).toDouble()
                }
            }
        }
    }
}