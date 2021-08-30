package com.dfsek.terra.addons.terrascript.script.builders;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.dfsek.terra.addons.terrascript.parser.lang.ImplementationArguments;
import com.dfsek.terra.addons.terrascript.parser.lang.Returnable;
import com.dfsek.terra.addons.terrascript.parser.lang.functions.Function;
import com.dfsek.terra.addons.terrascript.parser.lang.functions.FunctionBuilder;
import com.dfsek.terra.addons.terrascript.parser.lang.variables.Variable;
import com.dfsek.terra.addons.terrascript.script.TerraImplementationArguments;
import com.dfsek.terra.addons.terrascript.tokenizer.Position;


public class UnaryBooleanFunctionBuilder implements FunctionBuilder<Function<Void>> {
    
    private final BiConsumer<Boolean, TerraImplementationArguments> function;
    
    public UnaryBooleanFunctionBuilder(BiConsumer<Boolean, TerraImplementationArguments> function) {
        this.function = function;
    }
    
    @Override
    public Function<Void> build(List<Returnable<?>> argumentList, Position position) {
        return new Function<Void>() {
            @Override
            public ReturnType returnType() {
                return ReturnType.VOID;
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public Void apply(ImplementationArguments implementationArguments, Map<String, Variable<?>> variableMap) {
                function.accept(((Returnable<Boolean>) argumentList.get(0)).apply(implementationArguments, variableMap),
                                (TerraImplementationArguments) implementationArguments);
                return null;
            }
            
            @Override
            public Position getPosition() {
                return position;
            }
        };
    }
    
    @Override
    public int argNumber() {
        return 1;
    }
    
    @Override
    public Returnable.ReturnType getArgument(int position) {
        if(position == 0) return Returnable.ReturnType.BOOLEAN;
        return null;
    }
}
