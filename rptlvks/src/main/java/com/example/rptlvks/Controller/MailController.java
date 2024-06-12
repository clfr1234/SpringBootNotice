package com.example.rptlvks.Controller;

import com.example.rptlvks.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    private int number;

    @PostMapping("/mailSend")
    @ResponseBody
    public String mailSend(@RequestBody String mail) {
        System.out.println(mail);
        String failSuccess = "Fail";

        try {
            number = mailService.sendMail(mail);
            String num = String.valueOf(number);

            failSuccess = "Success";
        } catch (Exception e) {
            System.out.println(e);
            failSuccess = "Fail";
        }

        return failSuccess;
    }

    @PostMapping("/mailCheck")
    @ResponseBody
    public String mailCheck(@RequestBody String resultNum) {
        System.out.println(resultNum);
        boolean isMatch = resultNum.equals(String.valueOf(number));

        String TF = String.valueOf(isMatch);

        return TF;
    }
}
