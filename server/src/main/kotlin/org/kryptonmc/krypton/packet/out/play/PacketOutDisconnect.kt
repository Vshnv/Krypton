/*
 * This file is part of the Krypton project, licensed under the GNU General Public License v3.0
 *
 * Copyright (C) 2021 KryptonMC and the contributors of the Krypton project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.kryptonmc.krypton.packet.out.play

import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import org.kryptonmc.krypton.util.writeChat
import org.kryptonmc.krypton.packet.state.PlayPacket

/**
 * Sent to indicate that the client should close the connection to the server for the specified [reason].
 * This is different from [login disconnect][org.kryptonmc.krypton.packet.out.login.PacketOutLoginDisconnect],
 * which is sent in the [play][org.kryptonmc.krypton.packet.state.PacketState.PLAY] state.
 *
 * @param reason the reason for disconnection
 */
class PacketOutDisconnect(private val reason: Component) : PlayPacket(0x19) {

    override fun write(buf: ByteBuf) {
        buf.writeChat(reason)
    }
}
