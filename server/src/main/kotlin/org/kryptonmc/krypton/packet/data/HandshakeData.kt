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
package org.kryptonmc.krypton.packet.data

import org.kryptonmc.krypton.packet.state.PacketState
import java.net.InetAddress

/**
 * Data sent in the [Handshake packet][org.kryptonmc.krypton.packet.in.handshake.PacketInHandshake].
 *
 * [address] and [port] are ignored by the Notchian server, and it is unclear why they are even sent,
 * but we also completely ignore them, as we already know the server's address and port.
 */
data class HandshakeData(
    val protocol: Int,
    val address: String,
    val port: UShort,
    val nextState: PacketState
)
