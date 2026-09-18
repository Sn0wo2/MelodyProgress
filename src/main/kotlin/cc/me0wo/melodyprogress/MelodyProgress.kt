package cc.me0wo.melodyprogress

import eu.midnightdust.lib.config.MidnightConfig
import com.mojang.brigadier.Command
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.item.Items
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object MelodyProgress : ClientModInitializer {
    val LOGGER: Logger = LoggerFactory.getLogger("melodyprogress")
    
    private val PROGRESS_SLOTS = intArrayOf(25, 34, 43) // todo remove 43

    private var currentStage = -1

    override fun onInitializeClient() {
        MidnightConfig.init("melodyprogress", MelodyConfig::class.java)

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                ClientCommands.literal("melodyprogress").executes { ctx ->
                    val client = ctx.source.client
                    client.execute {
                        client.setScreen(MidnightConfig.getScreen(client.screen, "melodyprogress"))
                    }
                    Command.SINGLE_SUCCESS
                }
            )
        }

        ClientTickEvents.END_CLIENT_TICK.register(::onTick)
    }

    private fun onTick(client: Minecraft) {
        if (client.screen?.title?.string != "Click the button on time!") {
            currentStage = -1
            return
        }

        val player = client.player ?: return
        val menu = player.containerMenu

        if (currentStage == -1) {
            currentStage = 0
            sendPartyChat(player, MelodyConfig.startMessage)
        }

        if (currentStage >= PROGRESS_SLOTS.size - 1) return
        for (i in PROGRESS_SLOTS.indices) {
            if (i <= currentStage) continue

            if (menu.getSlot(PROGRESS_SLOTS[i]).item.`is`(Items.LIME_TERRACOTTA)) {
                sendPartyChat(player, when (i) {
                    0 -> MelodyConfig.progressMessage14
                    1 -> MelodyConfig.progressMessage24
                    else -> MelodyConfig.progressMessage34
                })
                currentStage = i
            }
        }
    }

    private fun sendPartyChat(player: LocalPlayer, message: String) {
        if (message.isBlank()) return
        player.connection.sendCommand("pc $message")
    }
}
