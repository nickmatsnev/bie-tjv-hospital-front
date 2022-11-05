package cvut.fit.matsnnik.front;

import cvut.fit.matsnnik.front.clients.DoctorClient;
import cvut.fit.matsnnik.front.clients.PatientClient;
import cvut.fit.matsnnik.front.models.DoctorLoginModel;
import cvut.fit.matsnnik.front.models.DoctorModel;
import cvut.fit.matsnnik.front.models.PatientLoginModel;
import cvut.fit.matsnnik.front.models.PatientModel;
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
 private final PatientClient patientClient;
 DoctorModel currentDoctor;
 PatientModel currentPatient;
    public WebController(DoctorClient doctorClient, PatientClient patientClient) {
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    @GetMapping("")
    public String emptyUrl(Model model){
        if(currentDoctor == null && currentPatient == null){
            return "redirect:/plogin";
        } else if (currentDoctor != null && currentPatient == null){
            return "redirect/:dhome";
        } else if(currentDoctor == null){
            return "redirect/:phome";
        }  else{
            currentPatient = null;
            currentDoctor = null;
            return "redirect/:plogin";
        }
    }

    @PostMapping("/dlogin")
    public String enterLoginDoctor(Model model, @ModelAttribute DoctorLoginModel doctorLoginModel) throws Exception {
        currentDoctor = doctorClient.get(doctorLoginModel.getDid()).block();
        try{
            model.addAttribute("doctorLoginModel", doctorClient.login(doctorLoginModel));
            return "redirect:/dhome";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dlogin")
    public String enterRender(Model model){
        model.addAttribute("doctorLoginModel", new DoctorLoginModel());
        return "dlogin";
    }
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        return "invalidPassword";
    }
    @GetMapping("/dhome")
    public String addDoctorHome(Model model){
        if(currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctorname", currentDoctor);
        return "dhome";
    }
    @GetMapping("/dlogout") /// mapping to log out from the system
    public String doctorLogout() {
        currentDoctor = null;
        return "redirect:/dlogin";
    }
    @GetMapping("/phome")
    public String addPatientHome(Model model){
        if(currentPatient == null){
            return "redirect:/plogin";
        }
        model.addAttribute("patient", currentPatient);
        return "phome";
    }
    @GetMapping("/plogout") /// mapping to log out from the system
    public String patientLogout() {
        currentDoctor = null;
        return "redirect:/plogin";
    }
    @PostMapping("/plogin")
    public String enterLoginPatient(Model model, @ModelAttribute PatientLoginModel patientLoginModel) throws Exception {
        currentPatient = patientClient.get(patientLoginModel.getEmail()).block();
        try{
            model.addAttribute("patientLoginModel", patientClient.login(patientLoginModel));
            return "redirect:/phome";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/plogin")
    public String enterRenderPatient(Model model){
        model.addAttribute("patientLoginModel", new PatientLoginModel());
        return "plogin";
    }
    @GetMapping("/pprofile") /// ьфззштп ещ пуе зкщашду зфпу
    public String getProfileRenderPatient(Model model) {
        if (currentPatient == null){
            return "redirect:/plogin";
        }
        model.addAttribute("patient", currentPatient);
        return "pprofile";
    }
    @GetMapping("/dprofile") /// ьфззштп ещ пуе зкщашду зфпу
    public String getProfileRenderDoctor(Model model) {
        if (currentDoctor == null){
            return "redirect:/plogin";
        }
        model.addAttribute("doctor", currentDoctor);
        return "dprofile";
    }
    @GetMapping("/pabout") /// ьфззштп ещ пуе зкщашду зфпу
    public String getAboutRenderPatient(Model model) {
        if (currentPatient == null){
            return "redirect:/plogin";
        }
        model.addAttribute("patient", currentPatient);
        return "pabout";
    }
    @GetMapping("/dabout") /// ьфззштп ещ пуе зкщашду зфпу
    public String getAboutRenderDoctor(Model model) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctor", currentDoctor);
        return "dabout";
    }



}
