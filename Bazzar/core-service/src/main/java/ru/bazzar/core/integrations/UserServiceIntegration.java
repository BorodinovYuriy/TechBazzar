package ru.bazzar.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.api.UserDto;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UserServiceIntegration {
    private final WebClient userServiceWebClient;

    public void payment(String username, BigDecimal totalPrice) {
        userServiceWebClient.get()
                .uri("api/v1/users/payment/" + totalPrice)
                .header("username", username)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Не достаточно средств на счете!"))
                )
                .bodyToMono(Void.class)
                .block();
    }

    public void refundPayment(String username, BigDecimal totalPrice) {
        userServiceWebClient.get()
                .uri("api/v1/users/refund/" + totalPrice)
                .header("username", username)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Ошибка при возврате средств!"))
                )
                .bodyToMono(Void.class)
                .block();
    }

    public UserDto getUser(String username) {
        return userServiceWebClient.get()
                .uri("api/v1/users/is_active/" + username)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Пользователь с таким именем не найдем в UserService!"))
                )
                .bodyToMono(UserDto.class)
                .block();
    }

    public void receivingProfit(UserDto userDto) {
        userServiceWebClient.post()
                .uri("api/v1/users/change_balance")
                .bodyValue(userDto)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Произошла неизвестная ошибка!"))
                )
                .bodyToMono(Void.class)
                .block();
    }

    public void refundProfit(UserDto userDto) {
        userServiceWebClient.post()
                .uri("api/v1/users/decrease_balance")
                .bodyValue(userDto)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Произошла неизвестная ошибка!"))
                )
                .bodyToMono(Void.class)
                .block();
    }
}
