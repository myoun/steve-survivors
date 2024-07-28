package app.myoun.stevesurvivors.stat

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier


sealed interface StatValueType {

    fun read(tag: NbtCompound, id: Identifier): Any
    fun write(tag: NbtCompound, id: Identifier, value: Any): NbtCompound

    data object Integer: StatValueType {
        override fun read(tag: NbtCompound, id: Identifier): Any {
            return tag.getInt(id.toString())
        }

        override fun write(
            tag: NbtCompound,
            id: Identifier,
            value: Any,
        ): NbtCompound {
            tag.putInt(id.toString(), value as Int)
            return tag
        }
    }

    data object Percentage: StatValueType {
        override fun read(tag: NbtCompound, id: Identifier): Any {
            return tag.getInt(id.toString())
        }

        override fun write(
            tag: NbtCompound,
            id: Identifier,
            value: Any,
        ): NbtCompound {
            tag.putInt(id.toString(), value as Int)
            return tag
        }
    }
}