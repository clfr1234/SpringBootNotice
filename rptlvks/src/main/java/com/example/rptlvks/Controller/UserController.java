package com.example.rptlvks.Controller;

import com.example.rptlvks.Entity.Notice;
import com.example.rptlvks.Entity.User;
import com.example.rptlvks.Repository.NoticeRepository;
import com.example.rptlvks.Repository.UserRepository;
import com.example.rptlvks.Service.NoticeService;
import com.example.rptlvks.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/")
    public String home(@PageableDefault(page = 1) Pageable pageable, Model model, @AuthenticationPrincipal User user) {
        if(user == null) {
            model.addAttribute("userdata", "none");
        } else {
            model.addAttribute("userdata",user);
        }

        Page<Notice> noticePages = noticeService.paging(pageable);

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), noticePages.getTotalPages());
        model.addAttribute("endPage",endPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("noticeData", noticePages);

        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "register";
    }


    @PostMapping("/submit_registration")
    public String submitRegisterForm(@ModelAttribute User user) {
        System.out.println("submit_registeration작동함");
        userService.registerSetting(user);
        return "redirect:/";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestBody String id) {
        return userService.id_search(id);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/loginFail")
    public String loginFail(Model errorCheck) {
        errorCheck.addAttribute("error","true");
        return "login";
    }
}