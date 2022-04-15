package plan3.recruitment.backend.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.dropwizard.jackson.Jackson;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void jsonRoundTripTest() throws Exception {
        final var person = Person.valueOf("first", "last", "first@last.se");
        final String fixture = fixture("fixtures/person.json");
        final JsonNode expected = MAPPER.readTree(fixture);
        final JsonNode actual = MAPPER.readTree(MAPPER.writeValueAsString(person));
        assertEquals(expected, actual);
    }
}
