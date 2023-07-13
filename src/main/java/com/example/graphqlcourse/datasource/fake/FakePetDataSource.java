package com.example.graphqlcourse.datasource.fake;

import com.course.graphql.generated.types.Cat;
import com.course.graphql.generated.types.Dog;
import com.course.graphql.generated.types.Pet;
import com.course.graphql.generated.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakePetDataSource {

    public static final List<Pet> PET_LIST = new ArrayList<>();

    @Autowired
    private Faker faker;

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 10; i++) {
            Pet animal = switch (i % 2) {
                case 0 -> Dog.newBuilder().name(faker.dog().name())
                        .food(PetFoodType.OMNIVORE)
                        .breed(faker.dog().breed())
                        .size(faker.dog().size())
                        .coatLength(faker.dog().coatLength())
                        .build();
                default -> Cat.newBuilder().name(faker.cat().name())
                        .food(PetFoodType.CARNIVORE)
                        .breed(faker.cat().breed())
                        .registry(faker.cat().registry())
                        .build();
            };

            PET_LIST.add(animal);
        }
    }
}
