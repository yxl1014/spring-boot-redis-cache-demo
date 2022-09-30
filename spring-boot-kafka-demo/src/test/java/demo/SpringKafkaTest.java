package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author yxl
 * @date: 2022/9/30 下午6:35
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringKafkaTest.class)
@EmbeddedKafka(count = 4, ports = {9092, 9093, 9094, 9095})
public class SpringKafkaTest {
    @Test
    public void contextLoads() throws IOException {
        System.in.read();
    }
}
