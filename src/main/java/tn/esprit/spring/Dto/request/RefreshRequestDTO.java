package tn.esprit.spring.Dto.request;

import lombok.Builder;

@Builder
public record RefreshRequestDTO(String refreshToken) {
}
