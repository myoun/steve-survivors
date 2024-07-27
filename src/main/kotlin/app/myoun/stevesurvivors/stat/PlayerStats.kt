package app.myoun.stevesurvivors.stat

import app.myoun.stevesurvivors.SteveSurvivors
import net.minecraft.util.Identifier


object PlayerStats {
    val all: List<PlayerStat> = listOf(Health)
    val allMap
        get() = all.map { it.id.toString() to it }.toMap()

    object Health: PlayerStat {
        override val id: Identifier = Identifier(SteveSurvivors.ID, "health")
        override val type: StatValueType<Int> = StatValueType.Integer
    }
}




