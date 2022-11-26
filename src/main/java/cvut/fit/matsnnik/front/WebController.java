package cvut.fit.matsnnik.front;

import cvut.fit.matsnnik.front.clients.DoctorClient;
import cvut.fit.matsnnik.front.clients.PatientClient;
import cvut.fit.matsnnik.front.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
            if(!doctorLoginModel.getPassword().equals(currentDoctor.getPassword())){
                return "redirect:/dlogin";
            }
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
        Iterable<SessionActualDTO> sessions = doctorClient.getSessionsByDid(currentDoctor.getDid());
        List<SessionActualDTO> sessionsWTime = new ArrayList<>();
        for(SessionActualDTO session: sessions){
            // we get it as string longs, i.e. '1002020'
            Long startL = Long.parseLong(session.getPlannedStart());
            Long endL = Long.parseLong(session.getPlannedEnd());
            Date startDate = new Date(startL);
            Date endDate = new Date(endL);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String startDateFormatted = formatter.format(startDate);
            String endDateFormatted = formatter.format(endDate);
            sessionsWTime.add(new SessionActualDTO(
                    startDateFormatted,
                    endDateFormatted,
                    session.getName(),
                    session.getDoctor(),
                    session.getPatient()
            ));
        }

        model.addAttribute("doctorname", currentDoctor);
        model.addAttribute("sessions", sessionsWTime);
        model.addAttribute("allPatients", patientClient.getAll());
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
        Iterable<SessionActualDTO> sessions = patientClient.getSessionsByPid(currentPatient.getPid());
        List<SessionActualDTO> sessionsWTime = new ArrayList<>();
        for(SessionActualDTO session: sessions){
            // we get it as string longs, i.e. '1002020'
            Long startL = Long.parseLong(session.getPlannedStart());
            Long endL = Long.parseLong(session.getPlannedEnd());
            Date startDate = new Date(startL);
            Date endDate = new Date(endL);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String startDateFormatted = formatter.format(startDate);
            String endDateFormatted = formatter.format(endDate);
            sessionsWTime.add(new SessionActualDTO(
                    startDateFormatted,
                    endDateFormatted,
                    session.getName(),
                    session.getDoctor(),
                    session.getPatient()
            ));
        }

        model.addAttribute("patient", currentPatient);
        model.addAttribute("sessions", sessionsWTime);
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
            if(!patientLoginModel.getPassword().equals(currentPatient.getPassword())){
                return "redirect:/plogin";
            }
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
    @GetMapping("/pprofile") ///
    public String getProfileRenderPatient(Model model) {
        if (currentPatient == null){

            return "redirect:/plogin";
        }
        model.addAttribute("patient", currentPatient);
        return "pprofile";
    }
    @GetMapping("/dprofile") ///
    public String getProfileRenderDoctor(Model model) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctor", currentDoctor);
        return "dprofile";
    }
    @GetMapping("/pabout")
    public String getAboutRenderPatient(Model model) {
        if (currentPatient == null){
            return "redirect:/plogin";
        }
        model.addAttribute("patient", currentPatient);
        return "pabout";
    }
    @GetMapping("/dabout")
    public String getAboutRenderDoctor(Model model) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctor", currentDoctor);
        return "dabout";
    }

    @GetMapping("/dregistration") /// get mapping for the registration page
    public String addDoctorRender(Model model ) {
        /// attribute that creates empty RegistrationModel
        model.addAttribute("doctorRegistrationModel", new DoctorRegistrationModel());
        return "dregister";
    }

    @PostMapping("/dregistration") /// post mapping to send the registering user credentials to the sever
    public String addDoctorSubmit(Model model, @ModelAttribute DoctorRegistrationModel doctorRegistrationModel) {


        /// if the password and password verification are not hte same throws error in the http
        if (!Objects.equals(doctorRegistrationModel.getPassword(), doctorRegistrationModel.getrPassword())){
            model.addAttribute("doctorRegistrationSubmit", "Passwords don't match");
            model.addAttribute("doctorRegistrationDto", new DoctorRegistrationModel());
            return "dregister";
        }

        if (doctorRegistrationModel.getPassword().length()<8)
        {
            model.addAttribute("doctorRegistrationSubmit", "Password should be at least 8 characters");
            model.addAttribute("doctorRegistrationModel", new DoctorRegistrationModel());
            return "dregister";
        }

        model.addAttribute("doctorRegistrationSubmit", doctorClient.register(doctorRegistrationModel).block());
        model.addAttribute("doctorRegistrationModel", new DoctorRegistrationModel());
        return "dregister";
    }

    @GetMapping("/pregistration") /// get mapping for the registration page
    public String addPatientRender(Model model) {
        /// attribute that creates empty RegistrationModel
        model.addAttribute("patientRegistrationDto", new PatientRegistrationModel());
        return "pregister";
    }

    @PostMapping("/pregistration") /// post mapping to send the registering user credentials to the sever
    public String addPatientSubmit(Model model, @ModelAttribute PatientRegistrationModel patientRegistrationModel) {
        /// if the password and password verification are not hte same throws error in the http
        if (!Objects.equals(patientRegistrationModel.getPassword(), patientRegistrationModel.getrPassword())){
            model.addAttribute("patientRegistrationSubmit", "Passwords don't match");
            model.addAttribute("patientRegistrationDto", new PatientRegistrationModel());
            return "pregister";
        }

        if (patientRegistrationModel.getPassword().length()<8)
        {
            model.addAttribute("patientRegistrationSubmit", "Password should be at least 8 characters");
            model.addAttribute("patientRegistrationDto", new PatientRegistrationModel());
            return "pregister";
        }
        model.addAttribute("patientRegistrationSubmit", patientClient.register(patientRegistrationModel).block());
        System.out.println(patientRegistrationModel.getName());
        model.addAttribute("patientRegistrationDto", new PatientRegistrationModel());
        return "pregister";
    }
    @GetMapping("/create-session") /// get mapping to get html for location creation page
    public String addSessionRender(Model model) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctorname", currentDoctor);
        /// create a new Location Model to fill it in
        model.addAttribute("sessionModel", new SessionActualDTO());
        model.addAttribute("patients", patientClient.getAll());
        return "createSession";
    }


    @PostMapping("/create-session") /// post mapping to pass the location model to the server
    public String addSessionSubmit(Model model, @ModelAttribute SessionActualDTO sessionActualDTO) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctorname", currentDoctor);
        /// passing the model to the createLocation in UserClient
        model.addAttribute("sessionModel", doctorClient.createSession(sessionActualDTO).block());
        model.addAttribute("patients", patientClient.getAll());
        return "sessionCreated";
    }
    @GetMapping("/get-one-patient/{id}") /// mapping to open patient details
    public String getOnePatient(Model model, @ModelAttribute PatientModel patientModel, @PathVariable("id") int id) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("doctorname", currentDoctor);
        /// getting the event model from the server
        model.addAttribute("details", patientClient.getById(id).block());

        return "sessionDetails";
    }

    @GetMapping("/get-one-doctor/{id}") /// mapping to open patient details
    public String getOneDoctor(Model model, @ModelAttribute DoctorModel doctorModel, @PathVariable("id") int id) {
        if (currentPatient == null){
            return "redirect:/plogin";
        }
        model.addAttribute("patient", currentPatient);

        /// getting the event model from the server
        model.addAttribute("details", doctorClient.get(id).block());

        return "showPatient";
    }
    @GetMapping("/get-one-session/{id}") /// mapping to open patient details
    public String getOneSession(Model model, @ModelAttribute SessionActualDTO sessionActualDTO, @PathVariable("id") int id) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("sessionCurrent", doctorClient.getSessionById(id).block());
        model.addAttribute("sessionModel", new SessionActualDTO());
        model.addAttribute("doctorname", currentDoctor);

        return "editSession";
    }
    @GetMapping("/get-one-session-name/{doctor}/{name}") /// mapping to open patient details
    public String getOneSessionByDoctorAndName(Model model, @ModelAttribute SessionActualDTO sessionActualDTO, @PathVariable("doctor") int doctor,@PathVariable("name") String name) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("sessionCurrent", doctorClient.getSessionByDoctorAndName(doctor, name).block());
        model.addAttribute("sessionModel", new SessionActualDTO());
        model.addAttribute("doctorname", currentDoctor);

        return "editSession";
    }
    @PostMapping("/get-one-session-name/{doctor}/{name}") /// mapping to open patient details
    public String putOneSession(Model model, @ModelAttribute SessionActualDTO sessionActualDTO, @PathVariable("doctor") int doctor,@PathVariable("name") String name) {
        if (currentDoctor == null){
            return "redirect:/dlogin";
        }
        model.addAttribute("sessionCurrent", doctorClient.getSessionByDoctorAndName(doctor, name).block());

        model.addAttribute("sessionModel", doctorClient.updateSession(doctor, name, sessionActualDTO).block());
        model.addAttribute("doctorname", currentDoctor);

        return "sessionUpdated";
    }


}
