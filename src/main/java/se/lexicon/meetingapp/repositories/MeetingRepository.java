package se.lexicon.meetingapp.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.lexicon.meetingapp.entities.Meeting;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("SELECT m FROM Meeting m LEFT JOIN FETCH m.participants")
    @EntityGraph(attributePaths = {"participants.email"})
    List<Meeting> findAllWithParticipantEmails();
}
