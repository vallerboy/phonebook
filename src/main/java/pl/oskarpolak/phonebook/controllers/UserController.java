package pl.oskarpolak.phonebook.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.oskarpolak.phonebook.models.RegisterForm;
import pl.oskarpolak.phonebook.models.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/add")
    public String showRegisterForm(Model model){
        model.addAttribute("registerForm", new RegisterForm());
        return "registerForm";
    }

    @PostMapping("/user/add")
    public String getDataFromRegister(@ModelAttribute @Valid RegisterForm registerForm,
                                      BindingResult bindingResult,
                                      Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("registerForm", "Błędne dane");
            return "registerForm";
        }

        if(userService.checkIfLoginExists(registerForm.getLogin())) {
            model.addAttribute("registerInfo", "Login zajety");
            return "registerForm";
        }

        userService.addUser(registerForm);
        model.addAttribute("registerInfo", "Zarejestrowano poprawnie!");
        return "registerForm";
    }

}
