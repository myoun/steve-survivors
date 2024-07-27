package app.myoun.stevesurvivors.stat

import app.myoun.stevesurvivors.SteveSurvivors
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import java.util.UUID


class PlayerData {
    val stats: HashMap<PlayerStat, Any> = hashMapOf()
}

class PlayerState : PersistentState() {

    val players: HashMap<UUID, PlayerData> = hashMapOf()

    companion object {
        @JvmStatic
        val type: Type<PlayerState> = Type(
            ::PlayerState,
            PlayerState::createFromNbt,
            null
        )

        @JvmStatic
        fun createFromNbt(tag: NbtCompound): PlayerState {
            val state = PlayerState()
            val playersNbt = tag.getCompound("players")
            playersNbt.keys.forEach { key ->
                val playerData = PlayerData()
                val statNbt = playersNbt.getCompound(key)
                statNbt.keys.forEach { statKey ->
                    val statId = Identifier.tryParse(statKey)!!
                    val stat = PlayerStats.allMap[statKey]!!
                    playerData.stats[stat] = stat.type.read(statNbt, statId)!!
                }
                playerData.stats
            }
            return state
        }

        @JvmStatic
        fun getServerState(server: MinecraftServer): PlayerState {
            val persistentStateManager = server.getWorld(World.OVERWORLD)!!.persistentStateManager
            val state = persistentStateManager.getOrCreate(type, SteveSurvivors.ID)

            state.markDirty()

            return state
        }

        @JvmStatic
        fun getPlayerState(player: PlayerEntity): PlayerData {
            val serverState = getServerState(player.world.server!!)
            val playerState = serverState.players.computeIfAbsent(player.uuid) { uuid -> PlayerData() }

            return playerState
        }

    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        val playersNbt = NbtCompound()

        players.forEach { uuid, value ->
            val statNbt = NbtCompound()
            value.stats.forEach { stat, statValue ->
                stat.type.write(statNbt, stat.id, statValue as Nothing)
            }
            playersNbt.put(uuid.toString(), statNbt)
        }
        nbt.put("players", playersNbt)

        return nbt
    }

}