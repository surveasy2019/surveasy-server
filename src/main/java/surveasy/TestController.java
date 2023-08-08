package surveasy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/{word}")
    public ResponseEntity<String> test(@PathVariable String word) {
        return ResponseEntity.ok("hello " + word);
    }
}
