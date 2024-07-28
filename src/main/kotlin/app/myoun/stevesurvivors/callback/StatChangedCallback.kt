package app.myoun.stevesurvivors.callback

import app.myoun.stevesurvivors.stat.PlayerStat
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity

fun interface StatChangedCallback {

    companion object {
        @JvmStatic
        val EVENT: Event<StatChangedCallback> = EventFactory.createArrayBacked(StatChangedCallback::class.java) { listeners ->
            runner@StatChangedCallback { player: PlayerEntity, stat: PlayerStat, value: Any ->
                for (listener in listeners) {
                    listener.interact(player, stat, value)
                }
            }
        }
    }



    fun interact(player: PlayerEntity, stat: PlayerStat, value: Any)
}