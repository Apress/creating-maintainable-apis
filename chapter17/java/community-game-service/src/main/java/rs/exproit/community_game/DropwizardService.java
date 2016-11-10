package rs.exproit.community_game;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.katharsis.locator.JsonServiceLocator;
import io.katharsis.queryParams.DefaultQueryParamsParser;
import io.katharsis.queryParams.QueryParamsBuilder;
import io.katharsis.rs.KatharsisFeature;
import rs.exproit.community_game.domain.repository.*;
import rs.exproit.community_game.managed.MongoManaged;
import rs.exproit.community_game.managed.MongoManagedImpl;

import static io.katharsis.rs.KatharsisProperties.RESOURCE_DEFAULT_DOMAIN;
import static io.katharsis.rs.KatharsisProperties.RESOURCE_SEARCH_PACKAGE;
import static io.katharsis.rs.KatharsisProperties.WEB_PATH_PREFIX;

public class DropwizardService extends Application<DropwizardConfiguration> {
    private GuiceBundle<DropwizardConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<DropwizardConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<DropwizardConfiguration>newBuilder()
                .addModule(new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(MongoManaged.class).to(MongoManagedImpl.class);
                        
                        bind(GameRepository.class);
                        bind(ArtifactRepository.class);
                        bind(HeroRepository.class);
                        bind(PlayerRepository.class);
                        bind(ArtifactToGameRepository.class);
                        bind(PlayerToGameRepository.class);
                        bind(HeroToGameRepository.class);
                        bind(GameToPlayerRepository.class);
                        bind(GameToArtifactRepository.class);
                        bind(GameToHeroRepository.class);
                        bind(PlayerToHeroRepository.class);
                        bind(HeroToArtifactRepository.class);
                    }

                    @Provides
                    public MongoManagedImpl mongoManaged(DropwizardConfiguration configuration) throws Exception {
                        return new MongoManagedImpl(configuration.mongo);
                    }
                })
                .setConfigClass(DropwizardConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(DropwizardConfiguration dropwizardConfiguration, Environment environment) throws Exception {
        environment.lifecycle().manage(guiceBundle.getInjector().getInstance(MongoManagedImpl.class));

        environment.jersey().property(RESOURCE_DEFAULT_DOMAIN, dropwizardConfiguration.katharsis.host);
        environment.jersey().property(RESOURCE_SEARCH_PACKAGE, dropwizardConfiguration.katharsis.searchPackage);
        if (dropwizardConfiguration.katharsis.webPrefix != null) {
            environment.jersey().property(WEB_PATH_PREFIX, dropwizardConfiguration.katharsis.webPrefix);            
        }

        KatharsisFeature katharsisFeature = new KatharsisFeature(environment.getObjectMapper(),
                new QueryParamsBuilder(new DefaultQueryParamsParser()),
                new JsonServiceLocator() {
                    public <T> T getInstance(Class<T> aClass) {
                        return guiceBundle.getInjector().getInstance(aClass);
                    }
                });
        environment.jersey().register(katharsisFeature);
    }

    public static void main(String[] args) throws Exception {
        new DropwizardService().run(args);
    }
}
