package org.apiaddicts.apitools.apigen.generatorcore.generator.implementations.java.apigen.web.controller.endpoints;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.apiaddicts.apitools.apigen.archetypecore.core.persistence.filter.Filter;
import org.apiaddicts.apitools.apigen.generatorcore.config.Configuration;
import org.apiaddicts.apitools.apigen.generatorcore.config.controller.Endpoint;
import org.apiaddicts.apitools.apigen.generatorcore.generator.implementations.java.apigen.ApigenContext;
import org.apiaddicts.apitools.apigen.generatorcore.generator.implementations.java.apigen.web.resource.output.ApigenEntityOutputResourceBuilder;
import org.apiaddicts.apitools.apigen.generatorcore.generator.implementations.java.apigen.web.response.builders.EntitySimpleResponseBuilder;
import org.apiaddicts.apitools.apigen.generatorcore.generator.implementations.java.common.persistence.EntityBuilder;
import org.apiaddicts.apitools.apigen.generatorcore.utils.Mapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetByIdMoreLevelsEndpointBuilder<C extends ApigenContext> extends ApigenAbstractEndpointBuilder<C> {


    public GetByIdMoreLevelsEndpointBuilder(Mapping rootMapping, Endpoint endpoint, C ctx, Configuration cfg) {
        super(rootMapping, endpoint, ctx, cfg);
    }

    @Override
    protected Class getMappingClass() {
        return GetMapping.class;
    }

    @Override
    protected TypeName getReturnTypeName() {
        return EntitySimpleResponseBuilder.getTypeName(endpoint.getResponse().getRelatedEntity(), cfg.getBasePackage());
    }

    @Override
    protected void addStatements() {
        TypeName filterType = ClassName.get(Filter.class);
        TypeName entityType = EntityBuilder.getTypeName(entityName, cfg.getBasePackage());
        TypeName resourceType = ApigenEntityOutputResourceBuilder.getTypeName(entityName, cfg.getBasePackage());
        String translatorParams = pathParamsToString(Arrays.asList("select", "exclude", "expand"));
        String params = pathParamsToString(Arrays.asList("select", "exclude", "expand"));
        builder.addStatement("$L.translate($L, $T.class)", NAMING_TRANSLATOR_NAME, translatorParams, resourceType);
        builder.addStatement("$T filter = getParentFilter($L, $L, \"$L\")", filterType, pathParams.get(0), null, endpoint.getChildParentRelationProperty());
        builder.addStatement("expand = getparentExpand(expand, \"$L\")", endpoint.getParentEntity());
        builder.addStatement("$T searchResult = $L.search(Long.valueOf($L), $L, $L)", entityType, SERVICE_NAME, pathParams.get(1), params, "filter");
        builder.addStatement("$T result = $L.toResource(searchResult)", resourceType, MAPPER_NAME);
        TypeName responseTypeMoreLevel = EntitySimpleResponseBuilder.getTypeName(endpoint.getResponse().getRelatedEntity(), cfg.getBasePackage());
        builder.addStatement("return new $T(result)", responseTypeMoreLevel);
    }

    private String pathParamsToString(List<String> names) {
        Set<String> params = builder.parameters.stream().map(p -> p.name).collect(Collectors.toSet());
        return names.stream().map(n -> params.contains(n) ? n : "null").collect(Collectors.joining(", "));
    }
}