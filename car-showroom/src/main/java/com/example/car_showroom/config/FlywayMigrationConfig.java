package com.example.car_showroom.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for flyway migration
 */
@Configuration
public class FlywayMigrationConfig implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
        flyway.repair();
        flyway.migrate();
    }
}
