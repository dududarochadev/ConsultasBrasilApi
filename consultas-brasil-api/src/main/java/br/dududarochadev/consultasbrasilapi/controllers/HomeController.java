import org.springBootFramework.stereotype.Controller;

@Controller
@RequestMapping("/")
public class HomeController {
    public String Home() {
        return "home";
    }
}