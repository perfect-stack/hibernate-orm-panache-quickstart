package org.acme.service.person;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PersonResourceTest {

    @Test
    public void findByName() {
        List list = given()
                .when().get("/person/findByName/Daniel")
                .then()
                .statusCode(200)
                .extract()
                .as(List.class);
        assertTrue(list.size() > 0);
    }

    @Test
    public void crud() {
        Person p1 = new Person();
        p1.setGivenName("Test");
        p1.setFamilyName("Time" + System.currentTimeMillis());

        // Create
        Person p2 = given()
                .body(p1)
                .contentType(ContentType.JSON)
                .basePath("/person")
                .when().post().then().statusCode(200).extract().as(Person.class);

        assertTrue(p2.getId() != null);
        long personId = p2.getId();

        // Verify create
        Person p3 = given().when().get("/person/" + personId).then().statusCode(200).extract().as(Person.class);
        assertEquals(p1.getFamilyName(), p3.getFamilyName());

        // Update
        p3.setGivenName(p3.getGivenName() + System.currentTimeMillis());
        Person p4 = given()
            .body(p3)
            .contentType(ContentType.JSON)
            .basePath("/person/" + p3.getId())
            .when().put().then().statusCode(200).extract().as(Person.class);

        // Verify update
        Person p5 = given().when().get("/person/" + personId).then().statusCode(200).extract().as(Person.class);
        assertEquals(p3.getGivenName(), p5.getGivenName());

        // Delete
        given().when().delete("/person/" + personId).then().statusCode(204);

        // Verify delete
        given().when().get("/person/" + personId).then().statusCode(404);
    }
}
