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
public class DoctorClient {
    private final WebClient webClient;

    public DoctorClient(@Value("http://localhost:8081/") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<String> register(DoctorRegistrationModel newDoctor) {
        DoctorRegistrationDto doctorRegistrationDto =
                new DoctorRegistrationDto(newDoctor.getDid(),
                        newDoctor.getName(),
                        newDoctor.getSurname(),
                        newDoctor.getdType(),
                        newDoctor.getPassword());
        return webClient.post()
                .uri("doctors/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(doctorRegistrationDto)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }
    public Mono<String> login(DoctorLoginModel doctorLogins){
        return webClient.post()
                .uri("/doctors/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(doctorLogins)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        responce -> responce.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Mono<DoctorModel> get(int did){
        return webClient.get()
                .uri("/doctors/{did}", did)
                .retrieve()
                .bodyToMono(DoctorModel.class);
    }

    public Flux<SessionModel> getSessionsByPid(int id){
        return webClient.get()
                .uri("/sessions/patient/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(SessionModel.class);
    }

    public Iterable<SessionModel> getSessionsByDid(Integer id){
        return webClient.get()
                .uri("sessions/doctor/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToFlux(SessionModel.class).toIterable();
    }

    public Mono<String> createSession(SessionModel sessionModel){
        return webClient.post()
                .uri("/sessions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(sessionModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }
}
