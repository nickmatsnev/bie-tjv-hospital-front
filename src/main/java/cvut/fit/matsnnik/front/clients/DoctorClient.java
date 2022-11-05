package cvut.fit.matsnnik.front.clients;

import cvut.fit.matsnnik.front.models.DoctorLoginModel;
import cvut.fit.matsnnik.front.models.DoctorModel;
import cvut.fit.matsnnik.front.models.DoctorRegistrationModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DoctorClient {
    private final WebClient webClient;

    public DoctorClient(@Value("http://localhost:8081/") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<String> register(DoctorRegistrationModel newDoctor) {
        return webClient.post()
                .uri("doctors/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newDoctor)
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
}
