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
package org.kryptonmc.krypton.packet.out.play.scoreboard

import io.netty.buffer.ByteBuf
import org.kryptonmc.krypton.api.world.scoreboard.Objective
import org.kryptonmc.krypton.packet.state.PlayPacket
import org.kryptonmc.krypton.util.writeChat
import org.kryptonmc.krypton.util.writeString
import org.kryptonmc.krypton.util.writeVarInt

/**
 * Tells the client to perform an action to an objective for a scoreboard
 */
class PacketOutScoreboardObjective(
    private val action: ObjectiveAction,
    private val objective: Objective
) : PlayPacket(0x4A) {

    override fun write(buf: ByteBuf) {
        buf.writeString(objective.name, 16)
        buf.writeByte(action.id)
        buf.writeChat(objective.displayName)
        if (action != ObjectiveAction.REMOVE) buf.writeVarInt(objective.renderType.ordinal)
    }
}

enum class ObjectiveAction(val id: Int) {

    CREATE(0),
    REMOVE(1),
    UPDATE_TEXT(2);
}
