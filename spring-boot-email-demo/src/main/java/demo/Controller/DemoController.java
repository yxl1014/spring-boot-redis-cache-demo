package demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

@RestController
public class DemoController {

    private HashMap<String, String> emails = new HashMap<>();
    private Random r = new Random();

    @Autowired
    private JavaMailSender mailSender;


    //获取验证码
    @GetMapping("/getCode")
    public String getCode(@RequestParam("e") String email) {
        //随机生成一个验证码
        String code = randomCode();

        //将这个验证码存到map中 真正项目中可以存到redis当中
        this.emails.put(email, code);
        //发送邮件
        sendEMail(email, code);
        return "ok";
    }

    //验证
    @PostMapping("checkCode")
    public String checkCode(@RequestParam("e") String email, @RequestParam("c") String code) {
        String c = this.emails.get(email);
        if (c == null)
            return "未获取";
        if (c.equals(code))
            return "check ok";
        else
            return "check error";
    }


    //随机生成6位验证码
    private String randomCode() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 6; i++)
            res.append(r.nextInt(10));
        return res.toString();
    }

    public void sendEMail(String email, String code) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("123456@163.com"); //设置发送邮件账号
            simpleMailMessage.setTo(email); //设置接收邮件的人，可以多个
            simpleMailMessage.setSubject("验证码"); //设置发送邮件的主题，就是标题
            simpleMailMessage.setText(code); //设置发送邮件的内容
            mailSender.send(simpleMailMessage);
            System.out.println("邮件发送成功!");
        } catch (MailException e) {
            System.out.println(e);
            System.out.println("邮件发送失败!");
        }
    }
}
