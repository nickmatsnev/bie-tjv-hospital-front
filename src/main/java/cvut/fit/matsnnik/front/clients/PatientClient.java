package cvut.fit.matsnnik.front.clients;

import cvut.fit.matsnnik.front.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PatientClient {

    private final WebClient webClient;

    public PatientClient(@Value("http://localhost:8081/") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<String> register(PatientRegistrationModel newPatient){
        PatientDTO patientDto = new PatientDTO(newPatient.getPid(),
                newPatient.getEmail(),
                newPatient.getName(),
                newPatient.getSurname(),
                newPatient.getAge(),
                newPatient.getPassword());

        System.out.println( newPatient.getName());
        return webClient.post()
                .uri("/patients/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(patientDto)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }
    public Mono<String> login(PatientLoginModel patientLoginModel){
        return webClient.post()
                .uri("/patients/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(patientLoginModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }
    public Mono<PatientModel> get(String email){
        return webClient.get()
                .uri("patients/email/{email}", email)
                .retrieve()
                .bodyToMono(PatientModel.class);
    }
    public Mono<PatientModel> getById(int pid){
        return webClient.get()
                .uri("patients/{pid}", pid)
                .retrieve()
                .bodyToMono(PatientModel.class);
    }

    public Iterable<PatientModel> getAll(){
        return webClient.get()
                .uri("patients/all")
                .retrieve()
                .bodyToFlux(PatientModel.class).toIterable();
    }

    public Iterable<SessionModel> getSessionsByPid(int id){
        return webClient.get()
                .uri("/sessions/patient/{id}", id)
                .retrieve()
                .bodyToFlux(SessionModel.class).toIterable();
    }

    public Iterable<SessionModel> getSessionsByDid(int id){
        return webClient.get()
                .uri("/sessions/doctor/{id}", id)
                .retrieve()
                .bodyToFlux(SessionModel.class).toIterable();
    }


}
