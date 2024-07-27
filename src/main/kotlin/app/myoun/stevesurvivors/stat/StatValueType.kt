package app.myoun.stevesurvivors.stat

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier


sealed interface StatValueType<T> {

    fun read(tag: NbtCompound, id: Identifier): T
    fun write(tag: NbtCompound, id: Identifier, value: T): NbtCompound

    data object Integer: StatValueType<Int> {
        override fun read(tag: NbtCompound, id: Identifier): Int {
            return tag.getInt(id.toString())
        }

        override fun write(
            tag: NbtCompound,
            id: Identifier,
            value: Int,
        ): NbtCompound {
            tag.putInt(id.toString(), value)
            return tag
        }
    }

    data object Percentage: StatValueType<Int> {
        override fun read(tag: NbtCompound, id: Identifier): Int {
            return tag.getInt(id.toString())
        }

        override fun write(
            tag: NbtCompound,
            id: Identifier,
            value: Int,
        ): NbtCompound {
            tag.putInt(id.toString(), value)
            return tag
        }
    }
}