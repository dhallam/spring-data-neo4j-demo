package me.dhallam.springdataneo4jdemo;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@Configuration
@EnableNeo4jRepositories("me.dhallam.springdataneo4jdemo")
@SuppressWarnings("deprecation")
public class Neo4jConfig extends Neo4jConfiguration {

	public Neo4jConfig() {
		setBasePackage(Neo4jConfig.class.getPackage().getName());
	}

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(
				"target/neo4jdb").newGraphDatabase();
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public WrappingNeoServerBootstrapper neo4jWebServer() {
		return new WrappingNeoServerBootstrapper(
				(GraphDatabaseAPI) graphDatabaseService());
	}
}
