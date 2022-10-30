package cvut.fit.matsnnik.front;

import cvut.fit.matsnnik.front.clients.DoctorClient;
import cvut.fit.matsnnik.front.models.DoctorLoginModel;
import cvut.fit.matsnnik.front.models.DoctorModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WebController {
 private final DoctorClient doctorClient;
 DoctorModel currentDoctor;

    public WebController(DoctorClient doctorClient) {
        this.doctorClient = doctorClient;
    }

    @GetMapping("")
    public String emptyUrl(Model model){
        if(currentDoctor == null){
            return "redirect:/login";
        } else{
            return "redirect/:dhome";
        }
    }

    @PostMapping("/login")
    public String enterLoginDoctor(Model model, @ModelAttribute DoctorLoginModel doctorLoginModel) throws Exception {
        currentDoctor = doctorClient.get(doctorLoginModel.getDid()).block();
        try{
            model.addAttribute("doctorLoginModel", doctorClient.login(doctorLoginModel));
            return "redirect:/dhome";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login")
    public String enterRender(Model model){
        model.addAttribute("doctorLoginModel", new DoctorLoginModel());
        return "login";
    }
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        return "invalidPassword";
    }
    @GetMapping("/dhome")
    public String addDoctorHome(Model model){
        if(currentDoctor == null){
            return "redirect:/login";
        }
        model.addAttribute("doctorname", currentDoctor);
        return "dhome";
    }
    @GetMapping("/logout") /// mapping to log out from the system
    public String doctorLogout() {
        currentDoctor = null;
        return "redirect:/login";
    }


}
