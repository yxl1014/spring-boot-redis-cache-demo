package demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.DateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * 这个是邮件可以设置html格式
 * 模板使用的是thymeleaf
 */
@RestController
public class HtmlController {

    private HashMap<String, String> emails = new HashMap<>();
    private Random r = new Random();


    //模板套用方法
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;


    //邮箱：123456@qq.com---验证码：025715
    //邮件发送成功!
    @GetMapping("/getCode_Html")
    public String getCode(@RequestParam("e") String email) {
        //随机生产验证码
        String code = randomCode();
        //存一下
        this.emails.put(email, code);
        //生成模板
        String captchaTemp = getCaptchaTempl(email, code, 3);
        System.out.println("邮箱：" + email + "---验证码：" + code);
        //发送邮件
        sendEMail(email, captchaTemp);
        return "ok";
    }

    @PostMapping("checkCode_Html")
    public String checkCode(@RequestParam("e") String email, @RequestParam("c") String code) {
        String c = this.emails.get(email);
        if (c == null)
            return "未获取";
        if (c.equals(code))
            return "check ok";
        else
            return "check error";
    }


    private String randomCode() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 6; i++)
            res.append(r.nextInt(10));
        return res.toString();
    }

    public void sendEMail(String email, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
            minehelper.setFrom("123456@163.com"); //设置发送邮件账号
            minehelper.setTo(email); //设置接收邮件的人，可以多个
            minehelper.setSubject("验证码"); //设置发送邮件的主题
            minehelper.setText(code,true); //设置发送邮件的内容 第二个设置为true则可以发送带HTML的邮件
            mailSender.send(message);
            System.out.println("邮件发送成功!");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("邮件发送失败!");
        }
    }

    /**
     * 获得验证码模板
     *
     * @param email   用户邮箱
     * @param code    验证码
     * @param timeout 超时时间
     * @return
     */
    public String getCaptchaTempl(String email, String code, int timeout) {
        Context context = new Context();
        //设置模板所需的参数
        context.setVariable("title", "验证码");
        context.setVariable("email", email);
        context.setVariable("code", code);
        context.setVariable("date", DateUtils.format(new Date(), new Locale("yyyy-MM-dd hh:mm:ss")));
        //通过模板类将动态参数传入HTML模板,并返回模板内容 参数一:模板名字，参数二：动态参数Web文本   模板再resoureces中
        String content = templateEngine.process("/mailCode", context);
        return content;
    }
}
