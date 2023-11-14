package com.example.archvizarena.web;

import com.example.archvizarena.model.binding.UserRegisterBindingModel;
import com.example.archvizarena.model.entity.enums.UserOccupationEnum;
import com.example.archvizarena.model.service.UserRegisterServiceModel;
import com.example.archvizarena.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

//    @ModelAttribute("userModel")

    @GetMapping("/register")
    public String getRegisterUser() {
        return "register-artist";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @PostMapping("/register")
//    ползваме валидатора директно като поставим анотация в аргумента
//    биндинг резулт е обекта който се получава от дто-то , ако има грешки
//    вместо да гръмне страницата ще редиректнем
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
//            flashAtributes са неща , които са достъпни след редиректа(ще се запазят полетата, които са въведени, вместо да се
//            изчистват и да трябва да се пише всичко на ново) при последващата гет заявка

            UserRegisterBindingModel modelWithoutUserOccupation = new UserRegisterBindingModel();
            BeanUtils.copyProperties(userRegisterBindingModel, modelWithoutUserOccupation, "userOccupation");

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", modelWithoutUserOccupation);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);



            return "redirect:/users/register";
        }

        if (userRegisterBindingModel.getUserOccupation().equals(UserOccupationEnum.ARTIST) && userRegisterBindingModel.getCreatorType() == null) {
            redirectAttributes.addAttribute("creator_type_error", "Bad");
            return "redirect:/users/register";
        }
        if (userRegisterBindingModel.getUserOccupation().equals(UserOccupationEnum.ARTIST) && userRegisterBindingModel.getPricePerImage() == null) {
            redirectAttributes.addAttribute("price_per_image_error", true);
            return "redirect:/users/register";
        }
        userService.register(modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));

        return "redirect:/login";
    }

}
