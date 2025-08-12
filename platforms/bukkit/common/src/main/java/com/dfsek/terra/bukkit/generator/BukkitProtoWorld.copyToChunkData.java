// Add this to BukkitProtoWorld class
public static org.bukkit.generator.ChunkGenerator.ChunkData copyToChunkData(org.bukkit.World world, BukkitProtoWorld proto, org.bukkit.generator.ChunkGenerator.BiomeGrid biomeGrid) {
    org.bukkit.generator.ChunkGenerator.ChunkData chunkData = world.getChunkGenerator().createChunkData(world);
    for (int y = 0; y < 256; y++) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                org.bukkit.block.data.BlockData data = BukkitAdapters.adaptBlockData(proto.getBlock(x, y, z));
                chunkData.setBlock(x, y, z, data);
                biomeGrid.setBiome(x, y, z, BukkitAdapters.adaptBiome(proto.getBiome(x, y, z)));
            }
        }
    }
    return chunkData;
}
