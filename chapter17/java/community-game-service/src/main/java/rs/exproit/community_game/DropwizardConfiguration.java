package rs.exproit.community_game;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DropwizardConfiguration extends Configuration {
    @Valid
    @NotNull
    public MongoConfiguration mongo = new MongoConfiguration();

    @Valid
    @NotNull
    public KatharsisConfiguration katharsis = new KatharsisConfiguration();

    public static final class MongoConfiguration {
        @NotNull
        public String host;

        @Min(1)
        @Max(65535)
        public int port;

        @NotNull
        public String db;

        @NotNull
        public String user;

        @NotNull
        public String password;
    }
    
    public static final class KatharsisConfiguration {
        @NotNull
        public String host;

        @NotNull
        public String searchPackage;

        public String webPrefix;
    }
}