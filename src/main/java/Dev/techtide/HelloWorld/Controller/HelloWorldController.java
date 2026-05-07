package Dev.techtide.HelloWorld.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//Rpresentation State Tarnsfer(REST)
public class HelloWorldController {
    @GetMapping("/h")
    String sayHelloWorld(){
        return "Hello World!";

    }
    @GetMapping("/hi")
        String chand(){
            return "Chandru";
        }

}
