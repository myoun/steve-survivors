package app.myoun.stevesurvivors

import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.World

object SteveDamageTypes {

    val PHYSICAL = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier(SteveSurvivors.ID, "physical"))
    val MAGIC = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier(SteveSurvivors.ID, "magic"))

    fun of(world: World, key: RegistryKey<DamageType>): DamageSource {
        return DamageSource(world.registryManager.get(RegistryKeys.DAMAGE_TYPE).entryOf(key))
    }

}