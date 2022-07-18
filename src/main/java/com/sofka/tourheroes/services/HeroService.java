package com.sofka.tourheroes.services;

import com.sofka.tourheroes.collections.Hero;
import com.sofka.tourheroes.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    public Mono<Hero> save(Hero hero){
        return this.heroRepository.save(hero);
    }

    public Flux<Hero> list(){
        return this.heroRepository.findAll();
    }

    public Mono<Hero> update(String id, Hero hero){
        return this.heroRepository.findById(id)
                .flatMap(heroe1 -> {
                    hero.setId(id);
                    return save(hero);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Hero> delete(String id){
        return this.heroRepository
                .findById(id)
                .flatMap(heroe -> this.heroRepository.deleteById(heroe.getId().toString()).thenReturn(heroe));
    }

}
