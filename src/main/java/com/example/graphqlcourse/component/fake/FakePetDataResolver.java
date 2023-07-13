package com.example.graphqlcourse.component.fake;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Cat;
import com.course.graphql.generated.types.Dog;
import com.course.graphql.generated.types.Pet;
import com.course.graphql.generated.types.PetFilter;
import com.example.graphqlcourse.datasource.fake.FakePetDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakePetDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.Pets
    )
    public List<Pet> getPets(@InputArgument(name = "petFilter") Optional<PetFilter> filter) {
        return filter.map(petFilter -> FakePetDataSource.PET_LIST.stream()
                        .filter(pet -> this.matchFilter(filter.get(), pet))
                        .collect(Collectors.toList()))
                .orElse(FakePetDataSource.PET_LIST);
    }

    private boolean matchFilter(PetFilter filter, Pet pet) {
        if (StringUtils.isBlank(filter.getPetType())) {
            return true;
        }

        if (filter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())) {
            return pet instanceof Dog;
        } else if (filter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())) {
            return pet instanceof Cat;
        } else {
            return false;
        }
    }


}
