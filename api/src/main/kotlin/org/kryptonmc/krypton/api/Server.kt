/*
 * This file is part of the Krypton API, licensed under the MIT license.
 *
 * Copyright (C) 2021 KryptonMC and the contributors to the Krypton project.
 *
 * This project is licensed under the terms of the MIT license.
 * For more details, please reference the LICENSE file in the api top-level directory.
 */
package org.kryptonmc.krypton.api

import net.kyori.adventure.audience.ForwardingAudience
import net.kyori.adventure.text.Component
import org.kryptonmc.krypton.api.command.CommandManager
import org.kryptonmc.krypton.api.command.Sender
import org.kryptonmc.krypton.api.entity.entities.Player
import org.kryptonmc.krypton.api.event.EventBus
import org.kryptonmc.krypton.api.plugin.PluginManager
import org.kryptonmc.krypton.api.scheduling.Scheduler
import org.kryptonmc.krypton.api.service.ServicesManager
import org.kryptonmc.krypton.api.status.StatusInfo
import org.kryptonmc.krypton.api.world.Difficulty
import org.kryptonmc.krypton.api.world.Gamemode
import org.kryptonmc.krypton.api.world.WorldManager
import org.kryptonmc.krypton.api.world.scoreboard.Scoreboard
import java.net.InetSocketAddress
import java.util.UUID

/**
 * The server
 */
interface Server : ForwardingAudience {

    /**
     * The information about this server implementation
     */
    val info: ServerInfo

    /**
     * The world manager for this server
     *
     * The world manager is used to load, save, create and update
     * worlds.
     */
    val worldManager: WorldManager

    /**
     * The command manager for this server
     *
     * This is used to register and unregister commands
     */
    val commandManager: CommandManager

    /**
     * The plugin manager for this server
     *
     * This is used to retrieve plugins and check whether they have
     * been initialised or not.
     */
    val pluginManager: PluginManager

    /**
     *
     */
    val servicesManager: ServicesManager

    /**
     * The event bus for this server
     *
     * This is used to subscribe to and call events
     */
    val eventBus: EventBus

    /**
     * The scheduler for this server
     *
     * This can be used to run and schedule asynchronous tasks
     */
    val scheduler: Scheduler

    /**
     * The status information for this server
     */
    val status: StatusInfo

    /**
     * If this server is in online mode, meaning it authenticates
     * players through Mojang
     */
    val isOnline: Boolean

    /**
     * If this server has hardcore mode enabled
     */
    val isHardcore: Boolean

    /**
     * The default difficulty of this server
     */
    val difficulty: Difficulty

    /**
     * The default gamemode of this server
     */
    val gamemode: Gamemode

    /**
     * The address that this server is currently bound to.
     */
    val address: InetSocketAddress

    /**
     * The list of online players
     */
    val players: Set<Player>

    /**
     * Get a player by their [uuid]
     *
     * @param uuid the player's [UUID]
     * @return the player with the specified [uuid], or null if there
     * is no connected player with that [UUID]
     */
    fun player(uuid: UUID): Player?

    /**
     * Get a player by their [name]
     *
     * @param name the player's name
     */
    fun player(name: String): Player?

    /**
     * The console's [Sender] object
     */
    val console: Sender

    /**
     * The server's [Scoreboard], or null if there isn't one
     */
    val scoreboard: Scoreboard?

    /**
     * Broadcast a message to every player on the server, optionally with
     * the specified [permission]
     *
     * @param message the message to send
     * @param permission (optional) the permission that players require to receive
     * the broadcast
     */
    fun broadcast(message: Component, permission: String? = null)

    /**
     * Holder for information about the server implementing this API
     */
    interface ServerInfo {

        /**
         * The name of this server implementation
         */
        val name: String

        /**
         * The version string of this server implementation (not Minecraft version)
         */
        val version: String

        /**
         * The target Minecraft version for this implementation
         */
        val minecraftVersion: String
    }
}
