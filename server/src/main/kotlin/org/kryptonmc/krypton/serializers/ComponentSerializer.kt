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
package org.kryptonmc.krypton.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

/**
 * A hacky serialiser that bridges the Gson component serialiser and kotlinx.serialization, allowing us to (de)serialise
 * using this serialiser rather than treating everything as a string and parsing that
 */
object ComponentSerializer : KSerializer<Component> {

    override val descriptor = PrimitiveSerialDescriptor("net.kyori.adventure.text.Component", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Component) {
        encoder.encodeSerializableValue(JsonElement.serializer(), Json.parseToJsonElement(GsonComponentSerializer.gson().serialize(value)))
    }

    override fun deserialize(decoder: Decoder): Component {
        return GsonComponentSerializer.gson().deserialize(decoder.decodeSerializableValue(JsonObject.serializer()).toString())
    }
}
