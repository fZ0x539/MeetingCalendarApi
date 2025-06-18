package se.lexicon.meetingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.meetingapp.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Long, Meeting> {
}
