package com.example.graphqlcourse.component.fake;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Hello;
import com.course.graphql.generated.types.HelloInput;
import com.example.graphqlcourse.datasource.fake.FakeHelloDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class FakeHelloMutation {

//    @DgsData(
//            parentType = DgsConstants.MUTATION.TYPE_NAME,
//            field = DgsConstants.MUTATION.AddHello
//    )
    @DgsMutation
    public int addHello(@InputArgument(name = "helloInput") HelloInput input) {
        var hello = Hello.newBuilder().text(input.getText()).randomNumber(input.getNumber()).build();

        FakeHelloDataSource.HELLO_LIST.add(hello);

        return FakeHelloDataSource.HELLO_LIST.size();
    }

        @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.ReplaceHelloText
    )
//    @DgsMutation
    public List<Hello> replaceHelloText(@InputArgument(name = "helloInput") HelloInput input) {
        FakeHelloDataSource.HELLO_LIST.stream()
                .filter(hello -> hello.getRandomNumber().equals(input.getNumber()))
                .forEach(hello -> hello.setText(input.getText()));

        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.DeleteHello
    )
    public int deleteHello(@InputArgument(name = "number") int number) {
        FakeHelloDataSource.HELLO_LIST.removeIf(hello -> hello.getRandomNumber() == number);

        return FakeHelloDataSource.HELLO_LIST.size();
    }
}
