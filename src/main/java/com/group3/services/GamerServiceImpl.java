package com.group3.services;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group3.beans.Gamer;
import com.group3.data.GamerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GamerServiceImpl implements GamerService {
	@Autowired
	private GamerRepository gamerRepo;

	@Override
	public Mono<Gamer> getGamer(int gamerId) {
		return gamerRepo.findById(gamerId);
	}

	@Override
	public Mono<Gamer> addGamer(Gamer gg) {
		return gamerRepo.insert(gg);
	}

	@Override
	public Mono<Gamer> updateGamer(Gamer gg) {
		return gamerRepo.save(gg);
	}

	@Override
	public Flux<Gamer> getGamers() {
		return gamerRepo.findAll();
	}

	@Override
	public Flux<Gamer> getGamersByPvpScore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
//	@Moderator	// only moderators can perform this action
	public Mono<Gamer> banGamer(int gamerId, Date banLiftDate) {
		Mono<Gamer> gamer = gamerRepo.findById(gamerId).map(gg -> {				// find the gamer
			gg.setRole(Gamer.Role.BANNED);										// set gamer role to banned
			Set<Date> banDates;													// declare set of ban dates
			if(gg.getBanDates() == null) {										// check if the set is exists
				banDates = Collections.synchronizedSet(new HashSet<Date>());	// make the set if it doesn't exist, *synchronized*
			} else {
				banDates = gg.getBanDates();									// if the set exists, load it
			}
			banDates.add(banLiftDate);											// add passed date to set
			gg.setBanDates(banDates);											// assign ban date set to gamer
			gamerRepo.save(gg);													// save updated gamer in repo
			return gg;															// return transormed gamer to mono
		});
		return gamer;															// return Mono<Gamer> to controller
	}

	public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
		return gamerRepo.findByUsername(username)
				.doOnSuccess(gamer -> {
					gamer.setLastLogin(Date.from(Instant.now()));
					gamerRepo.save(gamer);
				})
				.map(gamer -> gamer);
	}
}
