package demo.Service;


import demo.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //最简单的用法，cacheNames 就是 缓存等名字  key拼接
    //keys *
    //2) "UserService::1"
    //3) "UserService::12"
    //就是 cacheNames 拼上 id
    @Cacheable(cacheNames = "UserService", key = "#id")
    //多个参数可以使用#标识哪一个是key，否则就会把所有的参数都当成key
    public User getUser(int id) {
        System.out.println("调用了一次");
        User user = new User();
        user.setId(id);
        user.setUsername("xxx" + id);
        user.setPassword("yyy" + id);
        return user;
    }

    //keyGenerator 就是可以自定义key的类型通过config注入到bean当中，用于解耦
    //"UserService::getUser1[[1]]"
    //"UserService::getUser1[[2]]"
    @Cacheable(cacheNames = "UserService", keyGenerator = "myKeyGenerator")
    //多个参数可以使用#标识哪一个是key，否则就会把所有的参数都当成key
    public User getUser1(int id) {
        System.out.println("调用了一次");
        User user = new User();
        user.setId(id);
        user.setUsername(id + "xxx");
        user.setPassword(id + "yyy");
        return user;
    }

    //condition 就是增加一个判断条件 这里的条件就是 如果id小于10才会进行转换
    //"UserService::getUser2[[3]]"
    //添加了 3和11 但是redis里只有3
    @Cacheable(cacheNames = "UserService", keyGenerator = "myKeyGenerator", condition = "#id < 10")
    //多个参数可以使用#标识哪一个是key，否则就会把所有的参数都当成key
    public User getUser2(int id) {
        System.out.println("调用了一次");
        User user = new User();
        user.setId(id);
        user.setUsername(id + "qqq");
        user.setPassword(id + "zzz");
        return user;
    }

    //unless 与condition 这里的条件就是 如果id小于10就不缓存
    //懒得测了
    @Cacheable(cacheNames = "UserService", keyGenerator = "myKeyGenerator", unless = "#id < 10")
    //多个参数可以使用#标识哪一个是key，否则就会把所有的参数都当成key
    public User getUser3(int id) {
        System.out.println("调用了一次");
        User user = new User();
        user.setId(id);
        user.setUsername(id + "qqq");
        user.setPassword(id + "zzz");
        return user;
    }

    //unless 与 cache还自带一些关键字(其他的关键字在readme中) 比如 result 这里就是如果拿到的返回值是null就不缓存
    //懒得测了
    @Cacheable(cacheNames = "UserService", keyGenerator = "myKeyGenerator", unless = "#result = null")
    //多个参数可以使用#标识哪一个是key，否则就会把所有的参数都当成key
    public User getUser4(int id) {
        System.out.println("调用了一次");
        User user = new User();
        user.setId(id);
        user.setUsername(id + "qqq");
        user.setPassword(id + "zzz");
        return user;
    }


    //sync 表示异步模式  默认是方法执行完，以同步的方式将方法返回的结果存在缓存中。
    //这个测不了
    @Cacheable(cacheNames = "UserService", keyGenerator = "myKeyGenerator", sync = true)
    //多个参数可以使用#标识哪一个是key，否则就会把所有的参数都当成key
    public User getUser5(int id) {
        System.out.println("调用了一次");
        User user = new User();
        user.setId(id);
        user.setUsername(id + "qqq");
        user.setPassword(id + "zzz");
        return user;
    }


    //CachePut是修改数据
    @CachePut(cacheNames = "UserService", key = "#user.id")
    public User updateUser(User user) {
        return user;
    }


    //CacheEvict是删除缓存
    @CacheEvict(cacheNames = "UserService")
    public void deleteUser(int id) {
        System.out.println("id delete");
    }


}
