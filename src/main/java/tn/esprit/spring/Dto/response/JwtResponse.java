package tn.esprit.spring.Dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
	private Long id;
	private String token;
	private String refreshToken;
	private String type = "Bearer";
	private String email;

	public JwtResponse(Long id,String accessToken,String refreshToken, String email) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.refreshToken = refreshToken;
	}
}
