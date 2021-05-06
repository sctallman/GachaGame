package com.group3.data;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.group3.beans.Collectible;

import reactor.core.publisher.Flux;

public interface CollectibleRepository extends ReactiveCassandraRepository<Collectible, String>{

	@AllowFiltering
	Flux<Collectible> findByGamerId(int id);

}
