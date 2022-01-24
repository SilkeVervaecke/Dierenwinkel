package ehb.sv.werkstuk1.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }
}
