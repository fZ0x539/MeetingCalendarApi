package se.lexicon.meetingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.meetingapp.entities.Participant;

public interface ParticipantRepository extends JpaRepository<Long, Participant> {
}
