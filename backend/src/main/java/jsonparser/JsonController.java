package jsonparser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class JsonController {

    @PostMapping("/parse")
    public Map<String, Object> parseJson(@RequestBody String jsonStr) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
    }
}
