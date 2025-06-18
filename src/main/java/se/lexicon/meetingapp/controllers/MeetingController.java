package se.lexicon.meetingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.meetingapp.repositories.MeetingRepository;
import se.lexicon.meetingapp.repositories.ParticipantRepository;

@RestController
@RequestMapping("/api/meetings/")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    @GetMapping("/")
    public ResponseEntity<>

}
