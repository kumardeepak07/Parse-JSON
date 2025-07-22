package jsonparser;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
@RestController
public class JsonController {

    @RequestMapping(value = "/parse", 
                    method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseEntity<String> parseJson(@RequestBody(required = false) String jsonInput) {
        if ("OPTIONS".equalsIgnoreCase(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getRequest().getMethod())) {
            // return an empty OK for preflight
            return ResponseEntity.ok().build();
        }
        try {
            JsonParser parser = new JsonParser(jsonInput);
            return ResponseEntity.ok(parser.parse().toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid JSON: " + e.getMessage());
        }
    }
}
