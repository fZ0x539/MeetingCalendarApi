package se.lexicon.meetingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.meetingapp.entities.Participant;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByMeetingId(Long meetingId);
}
