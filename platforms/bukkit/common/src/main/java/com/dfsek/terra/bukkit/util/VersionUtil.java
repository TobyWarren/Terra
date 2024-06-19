package com.dfsek.terra.bukkit.util;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionUtil {
    public static final SpigotVersionInfo SPIGOT_VERSION_INFO;
    public static final MinecraftVersionInfo MINECRAFT_VERSION_INFO;

    private static final Logger logger = LoggerFactory.getLogger(VersionUtil.class);

    static {
        SPIGOT_VERSION_INFO = new SpigotVersionInfo();

        MinecraftVersionInfo mcVersionInfo;
        try {
            mcVersionInfo = new MinecraftVersionInfo();
        } catch(Throwable t) {
            logger.error("Error while parsing minecraft version info. Continuing launch, but setting all versions to -1.");
            mcVersionInfo = new MinecraftVersionInfo(-1, -1, -1);
        }
        MINECRAFT_VERSION_INFO = mcVersionInfo;
    }

    public static MinecraftVersionInfo getMinecraftVersionInfo() {
        return MINECRAFT_VERSION_INFO;
    }

    public static SpigotVersionInfo getSpigotVersionInfo() {
        return SPIGOT_VERSION_INFO;
    }

    public static final class SpigotVersionInfo {
        private final boolean spigot;
        private final boolean paper;
        private final boolean mohist;

        public SpigotVersionInfo() {
            logger.debug("Parsing spigot version info...");

            paper = PaperLib.isPaper();
            spigot = PaperLib.isSpigot();

            boolean isMohist = false;
            try {
                Class.forName("com.mohistmc.MohistMC");
                // it's mohist
                isMohist = true;
            } catch(ClassNotFoundException ignore) { }
            this.mohist = isMohist;

            logger.debug("Spigot version info parsed successfully.");
        }

        public boolean isPaper() {
            return paper;
        }

        public boolean isMohist() {
            return mohist;
        }

        public boolean isSpigot() {
            return spigot;
        }
    }

    public static final class MinecraftVersionInfo {
        private static final Logger logger = LoggerFactory.getLogger(MinecraftVersionInfo.class);

        private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
        private final int major;
        private final int minor;
        private final int patch;

        private MinecraftVersionInfo() {
            this(1, 21, 0); // Set fixed version here
        }

        private MinecraftVersionInfo(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        private MinecraftVersionInfo(String versionString) {
            Matcher versionMatcher = VERSION_PATTERN.matcher(versionString);
            if(versionMatcher.find()) {
                major = 1; // Override detected major version
                minor = 21; // Override detected minor version
                patch = 0; // Override detected patch version
            } else {
                logger.warn("Error while parsing minecraft version info. Continuing launch, but setting all versions to -1.");

                major = 1;
                minor = 21;
                patch = 0;
            }
        }

        @Override
        public String toString() {
            return "v1.21"; // Fixed version string
        }

        public int getMajor() {
            return 1; // Fixed major version
        }

        public int getMinor() {
            return 21; // Fixed minor version
        }

        public int getPatch() {
            return 0; // Fixed patch version
        }
    }
}
