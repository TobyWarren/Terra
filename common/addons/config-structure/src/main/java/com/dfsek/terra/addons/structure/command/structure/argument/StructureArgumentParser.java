package com.dfsek.terra.addons.structure.command.structure.argument;

import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.command.arg.ArgumentParser;
import com.dfsek.terra.api.entity.CommandSender;
import com.dfsek.terra.api.entity.Player;
import com.dfsek.terra.api.injection.annotations.Inject;
import com.dfsek.terra.api.structure.configured.ConfiguredStructure;


public class StructureArgumentParser implements ArgumentParser<ConfiguredStructure> {
    @Inject
    private TerraPlugin main;
    
    @Override
    public ConfiguredStructure parse(CommandSender sender, String arg) {
        return ((Player) sender).world().getConfig().getRegistry(ConfiguredStructure.class).get(arg);
    }
}
