package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by doug on 4/13/17.
 */
@Controller
public class PeopleController {

    @Autowired
    PeopleRepository peopleRepository;

    @GetMapping("/")
    public String listPeople(Model model, String search){

        model.addAttribute("search", search);
        model.addAttribute("people", peopleRepository.listPeople(search));

        return "index";
    }

    @GetMapping("/personForm")
    public String personForm(){
        return "personForm";
    }
}
