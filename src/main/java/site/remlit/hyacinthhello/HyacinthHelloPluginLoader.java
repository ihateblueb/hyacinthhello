package site.remlit.hyacinthhello;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

@SuppressWarnings("UnstableApiUsage")
public class HyacinthHelloPluginLoader implements PluginLoader {

    @Override
    public void classloader(
            PluginClasspathBuilder classpathBuilder
    ) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-stdlib:2.3.0"), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("co.aikar:acf-paper:0.5.1-SNAPSHOT"), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("redis.clients:jedis:6.0.0"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.zaxxer:HikariCP:6.3.0"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.postgresql:postgresql:42.7.7"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.mysql:mysql-connector-j:9.3.0"), null));

        resolver.addRepository(
                new RemoteRepository.Builder(
                        "central",
                        "default",
                        "https://maven-central.storage-download.googleapis.com/maven2/"
                ).build()
        );
        resolver.addRepository(
                new RemoteRepository.Builder(
                        "paper",
                        "default",
                        "https://repo.papermc.io/repository/maven-public/"
                ).build()
        );
        resolver.addRepository(
                new RemoteRepository.Builder(
                        "aikar-repo",
                        "default",
                        "https://repo.aikar.co/content/groups/aikar/"
                ).build()
        );
        classpathBuilder.addLibrary(resolver);
    }

}