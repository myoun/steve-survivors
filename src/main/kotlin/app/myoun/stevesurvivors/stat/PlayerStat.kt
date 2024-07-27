package app.myoun.stevesurvivors.stat

import net.minecraft.text.Text
import net.minecraft.util.Identifier

interface PlayerStat {

    val id: Identifier
    val description: Text
        get() = Text.translatable("stat.${id.namespace}.${id.path}.description")
    val type: StatValueType<*>
}