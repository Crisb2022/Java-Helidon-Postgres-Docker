package com.distribuida.config;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class AppConfig {
    @Produces
    public Config getConfig() {
        return Config.create();
    }

    @Produces
    public DbClient getDbClient() {
        return DbClient.builder(getConfig().get("db")).build();
    }
}
