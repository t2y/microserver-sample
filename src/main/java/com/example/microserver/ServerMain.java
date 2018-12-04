package com.example.microserver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerMain {
    public static void main(final String[] args) throws IOException, NumberFormatException, URISyntaxException {
        val config = config();
        val port = config.getInt("port");
        val baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        log.info("listening on port {}", port);

        val app = ResourceConfig.forApplication(new RootResourceConfig());
        val server = JettyHttpContainerFactory.createServer(baseUri, app);
        log.info("server started");

        try {
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Config config() {
        val defaultConfig = ConfigFactory.load();

        val externalConfigFile = Optional.ofNullable(System.getProperty("config")).map(File::new);
        if (externalConfigFile.isPresent() && !externalConfigFile.get().exists()) {
            throw new RuntimeException(
                    "external config file " + externalConfigFile.get().getAbsolutePath() + " not found");
        }

        return externalConfigFile
                .map(ConfigFactory::parseFile)
                .map(c -> c.withFallback(defaultConfig))
                .orElse(defaultConfig);
    }

}