package org.acme.service.person;

import org.acme.domain.Person;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.List;

@Path("/person")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    @Path("{id}")
    public Person findOne(Long id) {
        try {
            System.out.println("PersonResource.findOne() started");
            return personService.findById(id);
        }
        finally {
            System.out.println("PersonResource.findOne() finished");
        }
    }

    @GET
    @Path("/findByName/{name}")
    public List<Person> findByName(String name) {
        return personService.findByName(name);
    }

    @POST
    @Transactional
    public Person create(Person person) {
        if(person.getId() != null) {
            throw new WebApplicationException("Request body id cannot be defined for a create operation");
        }
        return personService.create(person);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Person update(@PathParam("id") Long id, @RequestBody Person person) {
        if(!person.getId().equals(id)) {
            throw new WebApplicationException("Request body id must match the path id for an update operation");
        }
        return personService.update(person);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteById(Long id) {
        personService.deleteById(id);
    }

    @POST
    @Path("/loadSampleData")
    @Transactional
    public String loadSampleData() {
        return personService.loadSampleData();
    }

}
