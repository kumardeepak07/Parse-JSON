package jsonparser;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class JsonController {

    @PostMapping("/parse")
    public String parseJson(@RequestBody String jsonInput) {
        try {
            JsonParser parser = new JsonParser(jsonInput);
            JsonValue result = parser.parse();
            return result.toString();
        } catch (Exception e) {
            return "Invalid JSON: " + e.getMessage();
        }
    }
}