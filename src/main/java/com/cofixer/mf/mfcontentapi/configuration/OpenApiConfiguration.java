package com.cofixer.mf.mfcontentapi.configuration;

import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    private static final String TITLE = "mf-content-api";
    private static final String DESCRIPTION = "API for mf-content";


    @Bean
    public OpenAPI openAPI() {
        String version = String.valueOf(TemporalUtil.getEpochSecond());
        return new OpenAPI()
                .info(new Info()
                        .description(DESCRIPTION)
                        .title(TITLE)
                        .version(version)
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/mr-alloc/mf-content-api")
                );
    }

}
