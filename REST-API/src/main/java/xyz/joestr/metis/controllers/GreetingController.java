package xyz.joestr.metis.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.joestr.metis.classes.Greeting;
import xyz.joestr.metis.classes.User;

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
//return Boolean.FALSE;
        }
        
        if (user.getName() == null) {
            return user;
//return Boolean.FALSE; 
        }
        
        return user;
        //return Boolean.TRUE;
    }
}
