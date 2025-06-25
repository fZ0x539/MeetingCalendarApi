package se.lexicon.meetingapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.meetingapp.entities.Meeting;
import se.lexicon.meetingapp.entities.Participant;
import se.lexicon.meetingapp.enums.MeetingLevel;
import se.lexicon.meetingapp.repositories.MeetingRepository;
import se.lexicon.meetingapp.repositories.ParticipantRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DatabseSeeder implements CommandLineRunner {

    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public void run(String... args) throws Exception {
        participantRepository.deleteAll();
        meetingRepository.deleteAll();


        List<Meeting> meetings = Arrays.asList(
                createMeeting("Team Sync", LocalDateTime.now().plusDays(1), MeetingLevel.Team, "Weekly team meeting"),
                createMeeting("Project Kickoff", LocalDateTime.now().plusDays(2), MeetingLevel.Team, "New project initiation"),
                createMeeting("Client Review", LocalDateTime.now().plusDays(3), MeetingLevel.Client, "Quarterly client review"),
                createMeeting("Sprint Planning", LocalDateTime.now().plusDays(4), MeetingLevel.Department, "Next sprint planning"),
                createMeeting("Product Demo", LocalDateTime.now().plusDays(5), MeetingLevel.Division, "Showcase new features"),
                createMeeting("Retrospective", LocalDateTime.now().plusDays(6), MeetingLevel.Department, "Sprint retrospective"),
                createMeeting("Board Meeting", LocalDateTime.now().plusDays(7), MeetingLevel.Division, "Monthly board meeting"),
                createMeeting("Workshop", LocalDateTime.now().plusDays(8), MeetingLevel.Team, "Technical workshop"),
                createMeeting("Partner Meeting", LocalDateTime.now().plusDays(9), MeetingLevel.Client, "Partner collaboration"),
                createMeeting("All Hands", LocalDateTime.now().plusDays(10), MeetingLevel.Division, "Company-wide meeting")
        );

        // Save all meetings first
        meetingRepository.saveAll(meetings);

        // Add participants to each meeting
        addParticipantsToMeeting(meetings.get(0), Arrays.asList("john.doe@example.com", "jane.smith@example.com"));
        addParticipantsToMeeting(meetings.get(1), Arrays.asList("alice.johnson@example.com", "bob.williams@example.com", "charlie.brown@example.com"));
        addParticipantsToMeeting(meetings.get(2), Arrays.asList("client.one@client.com", "client.two@client.com"));
        addParticipantsToMeeting(meetings.get(3), Arrays.asList("dev.one@example.com", "dev.two@example.com", "dev.three@example.com", "dev.four@example.com"));
        addParticipantsToMeeting(meetings.get(4), Arrays.asList("product.manager@example.com", "ux.designer@example.com", "client.rep@client.com"));
        addParticipantsToMeeting(meetings.get(5), Arrays.asList("scrum.master@example.com", "team.member@example.com"));
        addParticipantsToMeeting(meetings.get(6), Arrays.asList("ceo@example.com", "cto@example.com", "cfo@example.com", "hr.director@example.com"));
        addParticipantsToMeeting(meetings.get(7), Arrays.asList("tech.lead@example.com", "senior.dev@example.com"));
        addParticipantsToMeeting(meetings.get(8), Arrays.asList("partner.one@partner.com", "partner.two@partner.com", "our.rep@example.com"));
        addParticipantsToMeeting(meetings.get(9), Arrays.asList("employee.one@example.com", "employee.two@example.com", "employee.three@example.com"));

        // Save all meetings again to persist participants
        meetingRepository.saveAll(meetings);
    }

    private Meeting createMeeting(String title, LocalDateTime dateTime, MeetingLevel level, String description) {
        return Meeting.builder()
                .title(title)
                .dateTime(dateTime)
                .meetingLevel(level)
                .description(description)
                .build();
    }

    private void addParticipantsToMeeting(Meeting meeting, List<String> emails) {
        emails.forEach(email -> {
            Participant participant = Participant.builder()
                    .email(email)
                    .meeting(meeting)
                    .build();
            meeting.addParticipant(participant);
        });
    }
}
