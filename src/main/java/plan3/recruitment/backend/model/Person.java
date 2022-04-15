package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

    @JsonProperty
    private final String email;
    @JsonProperty
    private final String firstname;
    @JsonProperty
    private final String lastname;

    public Person(@JsonProperty("firstname") final String firstname,
                  @JsonProperty("lastname") final String lastname,
                  @JsonProperty("email") final String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return this.firstname + ' ' + this.lastname + " [" + this.email + "] ";
    }

    public String getEmail() {
        return this.email;
    }

	// DO NOT REMOVE THIS METHOD. But feel free to adjust to suit your needs.
    public static Person valueOf(final String firstname, final String lastname, final String email) {
        return new Person(firstname, lastname, email);
    }

}