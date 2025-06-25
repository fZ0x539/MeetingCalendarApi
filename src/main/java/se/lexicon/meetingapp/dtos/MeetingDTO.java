package se.lexicon.meetingapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import se.lexicon.meetingapp.enums.MeetingLevel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class MeetingDTO {

    private Long id;

    @NotBlank (message = "Please enter a title!")
    private String title;

    @NotNull (message = "Please enter a date.")
    @FutureOrPresent (message = "Date must be set in the future.")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    @NotNull (message = "Please enter a time.")
    private LocalTime time;

    @NotNull (message = "Select the level for which the meeting will be held.")
    private MeetingLevel meetingLevel;

    @NotEmpty (message = "Meeting must have participants.")
    private List<@Email (message = "Please enter a valid email.") String> participants;

    // Custom validation method
    @AssertTrue(message = "Duplicate emails found in participants list")
    public boolean isParticipantsUnique() {
        if (participants == null) return true;

        // Case-insensitive duplicate check
        Set<String> uniqueEmails = participants.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return uniqueEmails.size() == participants.size();
    }

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}

