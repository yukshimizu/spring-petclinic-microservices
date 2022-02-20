package org.springframework.samples.petclinic.visits.config;

import co.elastic.apm.attach.ElasticApmAttacher;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "elastic.apm")
@ConditionalOnProperty(value = "elastic.apm.enabled", havingValue = "true")
@Setter
public class ElasticApmConfig {
    private static final String SERVER_URL_KEY = "server_url";
    private String serverUrl;

    private static final String SERVICE_NAME_KEY = "service_name";
    @Value("${spring.application.name}") private String serviceName;

    private static final String SECRET_TOKEN_KEY = "secret_token";
    private String secretToken;

    private static final String ENVIRONMENT_KEY = "environment";
    private String environment;

    private static final String LOG_ECS_REFORMATTING = "log_ecs_reformatting";
    private String log_ecs_reformatting;

    private static final String ENABLE_EXPERIMENTAL_INSTRUMENTATIONS = "enable_experimental_instrumentations";
    private String enable_experimental_instrumentations;

    @PostConstruct
    public void init() {

        Map<String, String> apmProps = new HashMap<>(6);
        apmProps.put(SERVER_URL_KEY, serverUrl);
        apmProps.put(SERVICE_NAME_KEY, serviceName);
        apmProps.put(SECRET_TOKEN_KEY, secretToken);
        apmProps.put(ENVIRONMENT_KEY, environment);
        apmProps.put(LOG_ECS_REFORMATTING, log_ecs_reformatting);
        apmProps.put(ENABLE_EXPERIMENTAL_INSTRUMENTATIONS, enable_experimental_instrumentations);

        ElasticApmAttacher.attach(apmProps);
    }
}
