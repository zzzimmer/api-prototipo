package org.zzzimmer.apiprototipo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailDTO (
        @NotBlank(message = "Email é obrigatório")
        @Email
        @Size(max = 200, message = "Muitos caracteres.")
        String email){
}
