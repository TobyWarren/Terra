// Replace getDefaultWorldGenerator in TerraBukkitPlugin with:
@Override
public org.bukkit.generator.ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
    com.dfsek.terra.api.config.ConfigPack pack = loadConfigPack(id);
    com.dfsek.terra.api.block.state.BlockState air = getAirBlockState();
    com.dfsek.terra.api.world.chunk.generation.ChunkGenerator terraGenerator = createTerraGenerator(pack);
    return new com.dfsek.terra.bukkit.generator.BukkitChunkGeneratorWrapper(terraGenerator, pack, air);
}
