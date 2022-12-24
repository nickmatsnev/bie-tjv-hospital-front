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

    public Flux<SessionActualDTO> getSessionsByPid(int id){
        return webClient.get()
                .uri("/sessions/patient/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(SessionActualDTO.class);
    }

    public Iterable<SessionActualDTO> getSessionsByDid(Integer id){
        return webClient.get()
                .uri("sessions/doctor/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToFlux(SessionActualDTO.class).toIterable();
    }

    public Mono<String> createSession(SessionActualDTO sessionActualDTO){
        System.out.println("sessions patient when sent to server " + sessionActualDTO.getPatient());
        return webClient.post()
                .uri("/sessions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(sessionActualDTO)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Mono<SessionActualDTO> getSessionById(Integer id){
        return webClient.get()
                .uri("sessions/session/{id}", id)
                .retrieve()
                .bodyToMono(SessionActualDTO.class);
    }
    public Mono<SessionActualDTO> getSessionByDoctorAndName(Integer doctor, String name){
        return webClient.get()
                .uri("sessions/session/name/{doctor}/{name}", doctor, name)
                .retrieve()
                .bodyToMono(SessionActualDTO.class);
    }
    public Mono<String> updateSession(Integer doctor, String name, SessionActualDTO sessionActualDTO){
        System.out.println("sessions patient when sent to server to update " + sessionActualDTO.getPatient());
        System.out.println("sessions name when sent to server to update from url : " + name);
        return webClient.post()
                .uri("sessions/session/name/{doctor}/{name}", doctor, name)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(sessionActualDTO)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Mono<String> createRequest(RequestModel requestModel){
        return webClient.post()
                .uri("/requests/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }
    public Mono<RequestModel> getRequestById(Integer requestId){
        return webClient.get()
                .uri("/requests/{id}", requestId)
                .retrieve()
                .bodyToMono(RequestModel.class);
    }
    public Iterable<RequestModel> getPendingRequestsByDoctorId(Integer doctorId){
        return webClient.get()
                .uri("/requests/pending/doctor/{id}", doctorId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RequestModel.class).toIterable();
    }
    public Iterable<RequestModel> getRejectedRequestsByDoctorId(Integer doctorId){
        return webClient.get()
                .uri("/requests/rejected/doctor/{id}", doctorId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RequestModel.class).toIterable();
    }
    public Iterable<RequestModel> getPendingRequestsByPatientId(Integer patientId){
        return webClient.get()
                .uri("/requests/pending/patient/{id}", patientId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RequestModel.class).toIterable();
    }
    public Iterable<RequestModel> getRejectedRequestsByPatientId(Integer patientId){
        return webClient.get()
                .uri("/requests/rejected/patient/{id}", patientId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RequestModel.class).toIterable();
    }

    public Mono<String>  acceptRequest(String name, Integer doctorId, Integer patientId){
        return webClient.get()
                .uri("/requests/accept/{name}/{doctor}/{patient}", name, doctorId, patientId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> rejectRequest(String name, Integer doctorId, Integer patientId){
        return webClient.get()
                .uri("/requests/reject/{name}/{doctor}/{patient}", name, doctorId, patientId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Iterable<DoctorModel> getAll(){
        return webClient.get()
                .uri("/doctors/all")
                .retrieve()
                .bodyToFlux(DoctorModel.class).toIterable();
    }

}
