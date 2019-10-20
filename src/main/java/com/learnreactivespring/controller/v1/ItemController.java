package com.learnreactivespring.controller.v1;



import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.learnreactivespring.constants.ItemConstants.END_POINT;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    @GetMapping(END_POINT)
    public Flux<Item> getAll()
    {
        return itemReactiveRepository.findAll();
    }

    @GetMapping(END_POINT+"/{id}")
    public Mono<ResponseEntity<Item>> getOneItem(@PathVariable String id){
       return itemReactiveRepository.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(END_POINT)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@RequestBody Item item)
    {
        return itemReactiveRepository.save(item);
    }

    @DeleteMapping(END_POINT+"/{id")
    public Mono<Void> delete(@PathVariable String id)
    {
        return itemReactiveRepository.deleteById(id);
    }


}
