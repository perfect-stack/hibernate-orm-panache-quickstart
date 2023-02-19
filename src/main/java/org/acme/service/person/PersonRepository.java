package org.acme.service.person;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Person;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    public List<Person> findByName(String name) {
        return find("givenName", name).list();
    }
}