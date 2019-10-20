package com.learnreactivespring.v1;


import com.learnreactivespring.constants.ItemConstants;
import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class ItemControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    public List<Item> data() {
       return  Arrays.asList(new Item(null, "Samsung TV", 400.0),
                new Item(null, "LG TV", 420.0),
                new Item(null, "Apple Watch", 299.99),
                new Item(null, "Beats Headphones", 149.99),
                new Item("ABC", "Bose Headphones", 149.99));
    }



@Before
public void setUp(){
    itemReactiveRepository.deleteAll()
            .thenMany(Flux.fromIterable(data()))
            .flatMap(itemReactiveRepository::save)
            .doOnNext(item -> {
                System.out.println("Inserted item" +item);
            })
                    .blockLast();


}

@Test
public void getAll()
{
    webTestClient.get().uri(ItemConstants.END_POINT)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Item.class)
            .hasSize(5);
}

@Test
    public void getOne()
{
    webTestClient.get().uri(ItemConstants.END_POINT.concat("/{id}"),"ABC")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.price", "149.99");
}


}
