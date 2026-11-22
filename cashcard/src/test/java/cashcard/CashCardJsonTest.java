package cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // Tells spring boot to spin up a lightweight test environment specifically for JSON serialization and deserialization (using Jackson).
class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json; // asks spring to automatically inject a JacksonTester helper configured for CashCard class.

    // Java Object --> JSON
    @Test // marks this method as a JUnit 5 test case.
    void cashCardSerializationTest() throws IOException { // declares the test method; throws IOException is required because reading expected.json and writing JSON can throw I/O exceptions.

        CashCard cashCard = new CashCard(99L, 123.45); // Creates a sample CashCard object with an id of 99 (Long) and an amount of 123.45 (Double).

        assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 99,
                    "amount": 123.45
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45));
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}
