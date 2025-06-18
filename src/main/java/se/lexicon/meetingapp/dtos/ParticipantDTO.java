package se.lexicon.meetingapp.dtos;

import jakarta.validation.constraints.*;

public class ParticipantDTO {
    @Email
    @NotBlank
    private String email;
}
