package xyz.joestr.tachyon.information_exchange_server.controllers;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.joestr.tachyon.information_exchange_server.classes.Greeting;
import xyz.joestr.tachyon.information_exchange_server.classes.User;

@RestController
public class GreetingController {
 
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping("/greeting/{name}")
    public Greeting greeting2(@PathVariable String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(
        value = "/user",
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public User registerUser(@RequestBody User user) {
        
        if (user.getUniqueID() == null) {
            return user;
        }
        
        if (user.getName() == null) {
            return user;
        }
        
        return user;
    }
    
    @RequestMapping(
        value = "/op",
        method = RequestMethod.GET,
        produces = "video/mp4"
    )
    public FileSystemResource op() {
        return new FileSystemResource(new File(this.getClass().getResource("Tagtraeumer_-_Sinn_(offizielles_Video).mp4").getFile()));
    }
}
