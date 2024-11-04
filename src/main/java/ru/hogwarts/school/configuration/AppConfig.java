package ru.hogwarts.school.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public GroupedOpenApi getFacultyGroup() {
        return GroupedOpenApi.builder()
                .displayName("faculty")
                .pathsToMatch("/faculty/**")
                .group("faculty")
                .build();
    }

    @Bean
    public GroupedOpenApi getStudentGroup() {
        return GroupedOpenApi.builder()
                .displayName("student")
                .pathsToMatch("/student/**")
                .group("student")
                .build();
    }

    @Bean
    public GroupedOpenApi getAvatarGroup() {
        return GroupedOpenApi.builder()
                .displayName("avatar")
                .pathsToMatch("/avatar/**")
                .group("avatar")
                .build();
    }
}

