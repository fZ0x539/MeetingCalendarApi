package se.lexicon.meetingapp.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import se.lexicon.meetingapp.enums.MeetingLevel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MeetingRequestDTO {
    @NotBlank
    private String title;

    @NotNull
    @FutureOrPresent
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private MeetingLevel meetingLevel;

    @NotEmpty
    private List<@Valid ParticipantDTO> participants;

    @Size(max = 1000)
    private String description;
}

