/*
 * This file is part of the Krypton API, licensed under the MIT license.
 *
 * Copyright (C) 2021 KryptonMC and the contributors to the Krypton project.
 *
 * This project is licensed under the terms of the MIT license.
 * For more details, please reference the LICENSE file in the api top-level directory.
 */
package org.kryptonmc.krypton.api.event.events.play

import org.kryptonmc.krypton.api.entity.entities.Player
import org.kryptonmc.krypton.api.event.Event
import org.kryptonmc.krypton.api.registry.NamespacedKey

/**
 * Called when a plugin message is received from a client.
 *
 * @param player the player who's client sent the message
 * @param channel the channel the message came from
 * @param message the message received
 */
data class PluginMessageEvent(
    val player: Player,
    val channel: NamespacedKey,
    val message: ByteArray
) : Event {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PluginMessageEvent
        return player == other.player && channel == other.channel && message.contentEquals(other.message)
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result *= 31 + channel.hashCode()
        result *= 31 + message.contentHashCode()
        return result
    }
}
