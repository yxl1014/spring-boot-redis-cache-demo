package demo.Controller;


import demo.Service.UserService;
import demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个是 spring-boot-cache 的测试接口
 */
@RestController
public class CacheController {

    @Autowired
    private UserService userService;


    @PostMapping("cache_add")
    public User add(@RequestParam("id") int id) {
        return userService.getUser(id);
    }

    @PostMapping("cache_add1")
    public User add1(@RequestParam("id") int id) {
        return userService.getUser1(id);
    }
    @PostMapping("cache_add2")
    public User add2(@RequestParam("id") int id) {
        return userService.getUser2(id);
    }

    @PostMapping("cache_update")
    public User update(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
