package net.cloudappi.apigen.generatorcore.generator.web.controller.endpoints;

import com.squareup.javapoet.TypeName;
import lombok.extern.slf4j.Slf4j;
import net.cloudappi.apigen.generatorcore.config.controller.Endpoint;
import net.cloudappi.apigen.generatorcore.utils.Mapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@Slf4j
public class DeleteEndpointBuilder extends EndpointBuilder {

    private TypeName composedIdType;

    public DeleteEndpointBuilder(Mapping rootMapping, Endpoint endpoint, TypeName composedIdType, String basePackage) {
        super(rootMapping, endpoint, basePackage);
        this.composedIdType = composedIdType;
    }

    @Override
    protected Class getMappingClass() {
        return DeleteMapping.class;
    }

    @Override
    protected String getMapping() {
        return mapping.getValue();
    }

    @Override
    protected void addStatements() {
        builder.addStatement("$L.delete($L)", SERVICE_NAME, identifierParam);
    }

    @Override
    protected TypeName getIdentifierPathParamType() {
        return composedIdType;
    }
}
