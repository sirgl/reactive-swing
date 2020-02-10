package ui

import java.util.*

interface Logger {
    fun log(feature: LogFeature, textProvider:  () -> String)
    fun isEnabled(feature: LogFeature) : Boolean
}

enum class LogFeature {
    ReconciliationOps,
    ReconciliationStats,
    NewTree
}

class StderrLogger(private val enabledFeatures: EnumSet<LogFeature>) : Logger {
    override fun log(feature: LogFeature, textProvider: () -> String) {
        if (isEnabled(feature)) {
            System.err.println(textProvider())
        }
    }

    override fun isEnabled(feature: LogFeature): Boolean {
        return feature in enabledFeatures
    }
}