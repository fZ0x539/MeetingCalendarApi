package se.lexicon.meetingapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import se.lexicon.meetingapp.enums.MeetingLevel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING) // Stores the enum name as string in DB
    private MeetingLevel meetingLevel;

    @Builder.Default
    @OneToMany (mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    @Column(length = 500)
    private String description;


    public void addParticipant(Participant participant){
        participant.setMeeting(this);
        this.participants.add(participant);
    }

}


