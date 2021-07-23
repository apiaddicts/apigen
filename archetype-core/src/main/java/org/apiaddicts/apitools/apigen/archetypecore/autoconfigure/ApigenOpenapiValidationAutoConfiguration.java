package org.apiaddicts.apitools.apigen.archetypecore.autoconfigure;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import com.atlassian.oai.validator.springmvc.SpringMVCLevelResolverFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.io.IOException;

@Configuration
@ConditionalOnProperty(prefix = "apigen.openapi.validation", name = "enabled", havingValue = "true")
public class ApigenOpenapiValidationAutoConfiguration {

    @Bean
    public Filter openApiValidationFilter(
            @Value("${apigen.openapi.validation.request.enabled:true}") boolean validateRequest,
            @Value("${apigen.openapi.validation.response.enabled:false}") boolean validateResponse
    ) {
        return new OpenApiValidationFilter(validateRequest, validateResponse);
    }

    @Bean
    public WebMvcConfigurer addOpenApiValidationInterceptor(
            @Value("${apigen.openapi.validation.resource:classpath:static/api.yaml}") final Resource apiSpecification
    ) throws IOException {
        final OpenApiInteractionValidator validator = OpenApiInteractionValidator
                .createForSpecificationUrl(apiSpecification.getURI().toString())
                .withLevelResolver(SpringMVCLevelResolverFactory.create())
                .withResolveCombinators(true)
                .build();
        final OpenApiValidationInterceptor openApiValidationInterceptor = new OpenApiValidationInterceptor(validator);
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(final InterceptorRegistry registry) {
                registry.addInterceptor(openApiValidationInterceptor);
            }
        };
    }
}
