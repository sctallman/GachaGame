package com.group3.controllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group3.beans.Collectible;
import com.group3.beans.CollectibleType;
import com.group3.services.CollectibleService;

@RestController
@RequestMapping(value = "/collectibles")
public class CollectibleController {

	@Autowired
	private CollectibleService collectibleService;
	

	public CollectibleController() {
		super();
	}
	
	@PostMapping
	public Publisher<Collectible> addCollectible(@RequestBody Collectible c) {
		return collectibleService.createCollectible(c);
	}
	
	@PutMapping
	public Publisher<Collectible> updateCollectible(@RequestBody Collectible c) {
		return collectibleService.updateCollectible(c);
	}
	
	@GetMapping
	public Publisher<Collectible> getCollectibles(@RequestParam("filter") String filter) {
		System.out.println(filter);
		switch(filter.toLowerCase()) {
		case "all":
			return collectibleService.getAllCollectibles();
		default:
			return collectibleService.getCollectibles(filter);
		}
	}
}
