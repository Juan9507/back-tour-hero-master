package com.sofka.tourheroes.controllers;

import com.sofka.tourheroes.collections.Hero;
import com.sofka.tourheroes.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hero")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @PostMapping("/crear")
    public Mono<Hero> crear(@RequestBody Hero hero){
        return this.heroService.save(hero);
    }

    @GetMapping()
    public Flux<Hero> listar(){
        return this.heroService.list();
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Hero>> actualizar(@PathVariable("id") String id, @RequestBody Hero hero){
        return this.heroService.update(id,hero)
                .flatMap(hero1 -> Mono.just(ResponseEntity.ok(hero1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Hero>> eliminar(@PathVariable("id") String id){
        return this.heroService.delete(id)
                .flatMap(heroe -> Mono.just(ResponseEntity.ok(heroe)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }


}
