package com.dfsek.terra.bukkit.generator;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.World;

public class BukkitChunkGeneratorWrapper extends org.bukkit.generator.ChunkGenerator {
    private final ChunkGenerator generator;
    private final ConfigPack pack;
    private final BlockState air;

    public BukkitChunkGeneratorWrapper(ChunkGenerator generator, ConfigPack pack, BlockState air) {
        this.generator = generator;
        this.pack = pack;
        this.air = air;
    }

    @Override
    public ChunkData generateChunkData(World world, java.util.Random random, int x, int z, BiomeGrid biome) {
        BukkitProtoWorld proto = new BukkitProtoWorld(world, pack, x, z, air);
        generator.generateChunk(proto);
        return BukkitProtoWorld.copyToChunkData(world, proto, biome);
    }
}
