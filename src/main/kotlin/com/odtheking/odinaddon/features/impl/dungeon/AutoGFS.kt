package com.odtheking.odinaddon.features.impl.dungeon
import com.odtheking.odin.clickgui.settings.impl.BooleanSetting
import com.odtheking.odin.clickgui.settings.impl.NumberSetting
import com.odtheking.odin.events.TickEvent
import com.odtheking.odin.events.core.on
import com.odtheking.odin.features.Module
import com.odtheking.odin.utils.fillItemFromSack
import com.odtheking.odin.utils.skyblock.dungeon.DungeonUtils

object AutoGFS : Module(
    name = "Auto GFS",
    description = "Automatically refills important items from your sacks."
) {
    private val refillTNT by BooleanSetting("Refill TNT", true, desc = "Refill SuperBoom TNT.")
    private val refillLeap by BooleanSetting("Refill Leaps", false, desc = "Refill Spirit Leaps.")
    private val refillPearl by BooleanSetting("Refill Pearl", true, desc = "Refill Ender Pearls.")
    private val refillJerry by BooleanSetting("Refill Jerry", false, desc = "Refill Inflatable Jerrys.")
    private val refillDecoy by BooleanSetting("Refill Decoy", false, desc = "Refill Decoys.")
    private val timerIncrements by NumberSetting("Refill Timer", 20L, 5L, 120L, 1L, desc = "Interval to refill.")
    private val dungeononly by BooleanSetting("Dungeons Only", true, desc = "Only refills in Dungeons.")
    private var lastRefillTime = 0L

    init {
        on<TickEvent.End> {
            if (System.currentTimeMillis() - lastRefillTime >= timerIncrements * 1000L) {
                refill()
                lastRefillTime = System.currentTimeMillis()
            }
        }


    }

    private fun refill() {
        if (dungeononly && !DungeonUtils.inDungeons) return
        if (mc.screen != null) return
        if (refillPearl) fillItemFromSack(16, "ENDER_PEARL", "ender_pearl", false)
        if (refillJerry) fillItemFromSack(64, "INFLATABLE_JERRY", "inflatable_jerry", false)
        if (refillTNT)   fillItemFromSack(64, "SUPERBOOM_TNT", "superboom_tnt", false)
        if (refillDecoy)   fillItemFromSack(64, "DECOY", "decoy", false)
        if (refillLeap)   fillItemFromSack(64, "SPIRIT_LEAP", "spirit_leap", false)
    }
}