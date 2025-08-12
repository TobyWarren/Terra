/*
 * This file is part of Terra.
 *
 * Terra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Terra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Terra.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dfsek.terra.bukkit.generator;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import com.dfsek.terra.api.world.chunk.generation.ProtoWorld;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 1.16.5-safe wrapper: avoids org.bukkit.generator.WorldInfo and other 1.17+ APIs.
 */
public class BukkitChunkGeneratorWrapper extends ChunkGenerator {
    private final ChunkGenerator delegate;
    private final ConfigPack pack;
    private final BlockState air;

    public BukkitChunkGeneratorWrapper(ChunkGenerator delegate, ConfigPack pack, BlockState air) {
        this.delegate = delegate;
        this.pack = pack;
        this.air = air;
    }

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world,
                                                @NotNull Random random,
                                                int x, int z,
                                                @NotNull BiomeGrid biomeGrid) {
        // Create empty target buffer for Bukkit
        ChunkData out = createChunkData(world);

        // Build Terra proto world just for this chunk
        BukkitProtoWorld proto = new BukkitProtoWorld(world, x, z, air, pack.getBiomeProvider());

        // Run Terra generation once (no re-entrancy)
        delegate.generate(proto);

        // Copy the proto result into Bukkit's ChunkData
        BukkitProtoWorld.copyToChunkData(proto, out, biomeGrid);

        return out;
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        // Keep empty on 1.16.x to avoid newer BlockPopulator/LimitedRegion APIs.
        return Collections.emptyList();
    }
}
        }
    }
}
