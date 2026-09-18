package cc.me0wo.melodyprogress

import eu.midnightdust.lib.config.MidnightConfig
import eu.midnightdust.lib.config.MidnightConfig.Entry

class MelodyConfig : MidnightConfig() {
    companion object {
        @JvmField
        @Entry
        var startMessage: String = "❤ MelodyProgress 0/4"

        @JvmField
        @Entry
        var progressMessage14: String = "MelodyProgress ❤ 1/4"

        @JvmField
        @Entry
        var progressMessage24: String = "❤ MelodyProgress 2/4"

        @JvmField
        @Entry
        var progressMessage34: String = "MelodyProgress ❤ 3/4"
    }
}
