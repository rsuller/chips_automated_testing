package uk.gov.companieshouse.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Env {

    private final Logger log;
    public static final Env INSTANCE = new Env();
    private final String env;
    public Config config;

    public static Env env() {
        return INSTANCE;
    }

    public Env() {
        this(ConfigConstants.ENVIRONMENT);
    }

    public Env(String env) {
        this.log = LoggerFactory.getLogger(this.getClass().getName());
        Optional param = Optional.ofNullable(env);
        if (param.isPresent()) {
            this.env = env;
            this.config = this.initConfig();
        } else {
            throw new RuntimeException("Cannot initialise Env. Please provide env profile parameter (-Denv=myProfile)");
        }
    }

    private Config initConfig() {
        Config systemConfig = ConfigFactory.systemProperties();
        File envConfig = this.searchConfigFileInClasspath("env.conf");
        return systemConfig.withFallback(ConfigFactory.parseFile(envConfig)).resolve().getConfig(this.env);
    }

    File searchConfigFileInClasspath(String filename) {
        List files;
        try {
            Stream<Path> paths = Files.walk((new File(ConfigConstants.PROJECT_DIR)).toPath());
            Throwable var4 = null;

            try {
                files = paths.filter((p) -> {
                    return p.endsWith(filename);
                }).filter((p) -> {
                    return !p.toString().contains("target");
                }).map(Path::toFile).collect(Collectors.toList());
            } catch (Throwable var14) {
                var4 = var14;
                throw var14;
            } finally {
                if (paths != null) {
                    if (var4 != null) {
                        try {
                            paths.close();
                        } catch (Throwable var13) {
                            var4.addSuppressed(var13);
                        }
                    } else {
                        paths.close();
                    }
                }

            }
        } catch (IOException var16) {
            throw new Error(var16);
        }

        if (files.size() == 0) {
            throw new Error("Config file with name [" + filename + "] could not be found in your classpath.");
        } else {
            if (files.size() > 1) {
                this.log.warn("More than one file found for this environment with name [" + filename + "]");
            }

            if (!((File)files.get(0)).isFile()) {
                throw new Error("The file [" + ((File)files.get(0)).getAbsolutePath() + "] is not a normal file.");
            } else {
                return (File)files.get(0);
            }
        }
    }
}
