package com.signify.review.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.SocketUtils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DBConfig {

	@Bean("mongoTemplate")
	MongoTemplate getMongoTemplate() {
		MongoClient mongoClient = MongoClients.create();
		return new MongoTemplate(mongoClient, "test");
	}

	@PostConstruct
	public void init() throws Exception {
		String ip = "localhost";
		int randomPort = SocketUtils.findAvailableTcpPort();

		ImmutableMongodConfig mongodConfig = ImmutableMongodConfig.builder().version(Version.Main.DEVELOPMENT)
				.net(new Net(ip, randomPort, Network.localhostIsIPv6())).build();
		MongodStarter starter = MongodStarter.getDefaultInstance();
		MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
	}


}