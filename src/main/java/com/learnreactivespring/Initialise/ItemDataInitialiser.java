package com.learnreactivespring.Initialise;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
@Component
public class ItemDataInitialiser implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        initialDataSetup();

    }
    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    public List<Item> data()
    {
      return  Arrays.asList(new Item(null, "Samsung TV", 400.0),
            new Item(null, "LG TV", 420.0),
            new Item(null, "Apple Watch", 299.99),
            new Item(null, "Beats Headphones", 149.99),
            new Item("ABC", "Bose Headphones", 149.99));
}

    private void initialDataSetup() {
        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(itemReactiveRepository::save)
                .thenMany(itemReactiveRepository.findAll())
                .subscribe(item -> {
                    System.out.println("CommandLine Runner" +item);
                });
    }
    }



