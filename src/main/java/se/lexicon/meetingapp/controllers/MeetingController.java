package se.lexicon.meetingapp.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import se.lexicon.meetingapp.dtos.MeetingDTO;
import se.lexicon.meetingapp.entities.Meeting;
import se.lexicon.meetingapp.entities.Participant;
import se.lexicon.meetingapp.repositories.MeetingRepository;
import se.lexicon.meetingapp.repositories.ParticipantRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    @GetMapping
    public ResponseEntity<List<MeetingDTO>> getMeetings() {
        List<Meeting> meetings = meetingRepository.findAllWithParticipantEmails();


        var response = meetings.stream()
                .map(m -> MeetingDTO
                        .builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .date(m.getDateTime().toLocalDate())
                        .time(m.getDateTime().toLocalTime())
                        .meetingLevel(m.getMeetingLevel())
                        .participants(m.getParticipants().stream().map(Participant::getEmail).toList())
                        .description(m.getDescription())
                        .build()
                ).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> getMeeting(@PathVariable Long id) {
        Meeting meeting = meetingRepository.findById(id).orElse(null);
        if (meeting == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(convertToDto(meeting));
    }

    @PostMapping
    public ResponseEntity<MeetingDTO> createMeeting(
            @Valid @RequestBody MeetingDTO request,
            UriComponentsBuilder uriBuilder
    ) {
        Meeting meetingEntity = convertToEntity(request);
        var savedMeeting = meetingRepository.save(meetingEntity);
        var savedMeetingDto = convertToDto(savedMeeting);
        var uri = uriBuilder.path("/meetings/{id}").buildAndExpand(savedMeetingDto.getId()).toUri();
        return ResponseEntity.created(uri).body(savedMeetingDto);

    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<MeetingDTO> updateMeeting(
            @PathVariable Long id,
            @Valid @RequestBody MeetingDTO request
    ) {
        var meeting = meetingRepository.findById(id).orElse(null);
        if (meeting == null)
            return ResponseEntity.notFound().build();

        meeting.setTitle(request.getTitle());
        meeting.setDateTime(request.getDate().atTime(request.getTime()));
        meeting.setMeetingLevel(request.getMeetingLevel());
        meeting.setDescription(request.getDescription());

        Set<String> existingEmails = meeting.getParticipants().stream()
                .map(Participant::getEmail)
                .collect(Collectors.toSet());

        request.getParticipants().stream()
                .filter(email -> !existingEmails.contains(email))
                .forEach(email -> {
                    Participant newParticipant = new Participant();
                    newParticipant.setEmail(email);
                    meeting.addParticipant(newParticipant);
                });

        meeting.getParticipants().removeIf(
                p -> !request.getParticipants().contains(p.getEmail())
        );

        meetingRepository.save(meeting);
        return ResponseEntity.ok(convertToDto(meeting));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(
            @PathVariable Long id
    ) {
        var meeting = meetingRepository.findById(id).orElse(null);
        if (meeting == null)
            return ResponseEntity.notFound().build();

        meetingRepository.delete(meeting);
        return ResponseEntity.noContent().build();
    }

    private MeetingDTO convertToDto(Meeting meeting) {
        return MeetingDTO.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .date(meeting.getDateTime().toLocalDate())
                .time(meeting.getDateTime().toLocalTime())
                .meetingLevel(meeting.getMeetingLevel())
                .participants(getParticipantEmails(meeting))
                .description(meeting.getDescription())
                .build();
    }

    private Meeting convertToEntity(MeetingDTO meetingDto) {
        var meeting = Meeting.builder()
                .title(meetingDto.getTitle())
                .dateTime(meetingDto.getDate().atTime(meetingDto.getTime()))
                .meetingLevel(meetingDto.getMeetingLevel())
                .description(meetingDto.getDescription())
                .build();
        List<Participant> participants = meetingDto.getParticipants()
                .stream().map(email ->
                        Participant.builder()
                                .email(email)
                                .meeting(meeting).build()).toList();

        meeting.setParticipants(participants);
        return meeting;
    }


    private List<String> getParticipantEmails(Meeting meeting) {
        return meeting.getParticipants().stream()
                .map(Participant::getEmail)
                .toList();
    }
}
