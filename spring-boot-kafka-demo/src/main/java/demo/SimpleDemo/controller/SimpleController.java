package demo.SimpleDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yxl
 * @date: 2022/9/30 下午5:17
 */


@RestController
public class SimpleController {

    @Autowired
    private KafkaTemplate template;

    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        this.template.send("topic_input", input);
    }

    @KafkaListener(id = "webGroup", topics = "topic_input")
    public void listen(String input) {
        System.out.println("input value: " + input);
    }
}
