package com.example.data_trans.marshall;

import jakarta.xml.bind.JAXBException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final JAXBService jaxbService;

    public PersonController(JAXBService jaxbService) {
        this.jaxbService = jaxbService;
    }

    // XML 데이터를 Person 객체로 변환
    @PostMapping("/unmarshal")// @GetMapping -> @PostMapping
    public Person unmarshalPerson(@RequestBody String xml) throws JAXBException {
        return jaxbService.unmarshalPerson(xml);
    }

    // Person 객체를 XML로 변환
    @GetMapping("/marshal")
    public String marshalPerson() throws JAXBException {
        Person person = new Person();
        person.setName("John Doe");
        person.setAge(30);
        return jaxbService.marshalPerson(person);
    }
}
