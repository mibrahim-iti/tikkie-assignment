package com.tikkie.dao;

import com.tikkie.model.Person;
import com.tikkie.util.Constants;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public class PersonDynamoDbDao {

    private final DynamoDbTable<Person> personTable;

    public PersonDynamoDbDao(final DynamoDbTable<Person> personTable) {

        this.personTable = personTable;
    }

    public Optional<Person> find(final String personId) {

        final Key key = Key.builder().partitionValue(personId).build();

        final QueryConditional queryConditional = QueryConditional.keyEqualTo(key);
        final PageIterable<Person> query = personTable.query(QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .consistentRead(Boolean.TRUE)
                .limit(1)
                .build());

        return query.items().stream().findFirst();
    }

    public Optional<Person> find(final String firstname, final String lastname) {

        DynamoDbIndex<Person> secIndex = personTable.index(Constants.FULL_NAME_GLOBAL_SECONDARY_INDEX);

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(firstname + Constants.FULL_NAME_DELIMITER + lastname).
                        build());


        PageIterable<Person> results = (PageIterable<Person>) secIndex.query(QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .build());

        return results.items().stream().findFirst();
    }

    public List<Person> findAll() {

        return personTable.scan().items().stream().collect(Collectors.toList());
    }

    public String save(final Person person) {

        personTable.putItem(person);

        return person.getId();
    }

    public void delete(final String personId) {

        final Optional<Person> optionalPerson = find(personId);

        optionalPerson.ifPresent(personTable::deleteItem);
    }

}
