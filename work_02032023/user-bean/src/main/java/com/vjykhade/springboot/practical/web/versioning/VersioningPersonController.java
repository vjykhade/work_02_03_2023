package com.vjykhade.springboot.practical.web.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson()
    {
        return new PersonV1("Vijay Khade");
    }
    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson()
    {
        return new PersonV2(new Name("Vijay","Khade"));
    }
}
