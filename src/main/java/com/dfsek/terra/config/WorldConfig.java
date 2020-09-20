package com.dfsek.terra.config;

import com.dfsek.terra.Terra;
import com.dfsek.terra.biome.UserDefinedBiome;
import com.dfsek.terra.biome.UserDefinedGrid;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.polydev.gaea.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldConfig {
    private static JavaPlugin main;
    private static final Map<World, WorldConfig> configs = new HashMap<>();

    public float zoneFreq;
    public float freq1;
    public float freq2;
    public int seaLevel;
    public UserDefinedGrid[] definedGrids = new UserDefinedGrid[32];


    public WorldConfig(World w, JavaPlugin main) {
        WorldConfig.main = main;
        load(w);
    }

    public static void reloadAll() {
        for(Map.Entry<World, WorldConfig> e : configs.entrySet()) {
            e.getValue().load(e.getKey());
        }
    }

    public static WorldConfig fromWorld(World w) {
        if(configs.containsKey(w)) return configs.get(w);
        return new WorldConfig(w, Terra.getInstance());
    }

    public void load(World w) {
        long start = System.nanoTime();
        main.getLogger().info("Loading world configuration values for " + w + "...");
        FileConfiguration config = new YamlConfiguration();
        try { // Load/create world config file
            File configFile = new File(main.getDataFolder() + File.separator + "worlds", w.getName() + ".yml");
            if(! configFile.exists()) {
                configFile.getParentFile().mkdirs();
                main.getLogger().info("Configuration for world \"" + w + "\" not found. Copying default config.");
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(main.getResource("world.yml")), configFile);
            }
            config.load(configFile);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            main.getLogger().severe("Unable to load configuration for world " + w + ".");
        }

        // Get values from config.
        seaLevel = config.getInt("sea-level", 63);
        zoneFreq = 1f/config.getInt("frequencies.zone", 1536);
        freq1 = 1f/config.getInt("frequencies.grid-1", 256);
        freq2 = 1f/config.getInt("frequencies.grid-2", 512);


        configs.put(w, this); // WorldConfig must be included in map before Grids are loaded.

        for(int i = 0; i < 32; i++) {
            String partName = config.getStringList("grids").get(i);
            if(partName.startsWith("BIOME:")) {
                UserDefinedBiome[][] temp = new UserDefinedBiome[16][16];
                UserDefinedBiome b = BiomeConfig.fromID(partName.substring(6)).getBiome();
                for(int x = 0; x < 16; x++) {
                    for(int z = 0; z < 16; z++) {
                        temp[x][z] = b;
                    }
                }
                definedGrids[i] = new UserDefinedGrid(w, freq1, freq2, temp);
                main.getLogger().info("Loaded single-biome grid " + partName);
            } else definedGrids[i] = BiomeGridConfig.getBiomeGrids().get(partName).getGrid(w);
        }



        main.getLogger().info("World load complete. Time elapsed: " + ((double) (System.nanoTime() - start)) / 1000000 + "ms");
    }
}