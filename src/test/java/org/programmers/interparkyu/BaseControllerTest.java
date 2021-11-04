package org.programmers.interparkyu;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.programmers.interparkyu.hall.domain.Hall;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.hall.domain.Section;
import org.programmers.interparkyu.hall.repository.HallRepository;
import org.programmers.interparkyu.hall.repository.SeatRepository;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.domain.PerformanceCategory;
import org.programmers.interparkyu.performance.domain.Round;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.repository.RoundRepository;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.ticket.domain.Ticket;
import org.programmers.interparkyu.ticket.repository.RoundSeatRepository;
import org.programmers.interparkyu.ticket.repository.TicketRepository;
import org.programmers.interparkyu.user.domain.User;
import org.programmers.interparkyu.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseControllerTest {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundSeatRepository roundSeatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    protected static Hall givenHall;
    protected static Seat givenSeatA1;
    protected static Performance givenPerformance;
    protected static Round givenRound;
    protected static RoundSeat givenReservedRoundSeat;
    protected static RoundSeat givenUnReservedRoundSeat;
    protected static Ticket givenTicket;
    protected static User givenUser;

    @BeforeEach
    public void setupData() {
        final int NUMBER_OF_SEAT = 10;

        givenHall = Hall.builder()
            .name("테스트 홀")
            .seatCount(NUMBER_OF_SEAT)
            .build();
        hallRepository.save(givenHall);

        List<Seat> seats = new ArrayList<>();
        for (Section section : List.of(Section.A, Section.B)) {
            for (int i = 1; i <= 5; i++) {
                Seat seat = Seat.builder()
                    .section(section)
                    .sectionSeatNumber(i)
                    .hall(givenHall)
                    .price(100000)
                    .build();
                seats.add(seat);
            }
        }
        givenSeatA1 = seats.get(0);
        seatRepository.saveAll(seats);

        givenPerformance = Performance.builder()
            .title("무야호~ 더 뮤지컬")
            .runtime(180)
            .category(PerformanceCategory.MUSICAL)
            .hall(givenHall)
            .build();
        performanceRepository.save(givenPerformance);

        givenRound = Round.builder()
            .title("무야호~ 더 뮤지컬")
            .remainingSeats(NUMBER_OF_SEAT)
            .round(1)
            .date(LocalDate.now().plusDays(30))
            .startTime(LocalTime.now())
            .endTime(LocalTime.now().plusHours(3))
            .ticketingStartDateTime(LocalDateTime.now())
            .ticketingEndDateTime(LocalDateTime.now().plusDays(3))
            .ticketCancelableUntil(LocalDateTime.now().plusDays(25))
            .performance(givenPerformance)
            .build();
        roundRepository.save(givenRound);

        Round round2 = Round.builder()
            .title("무야호~ 더 뮤지컬")
            .remainingSeats(NUMBER_OF_SEAT)
            .round(2)
            .date(LocalDate.now().plusDays(30))
            .startTime(LocalTime.now().plusHours(4))
            .endTime(LocalTime.now().plusHours(7))
            .ticketingStartDateTime(LocalDateTime.now())
            .ticketingEndDateTime(LocalDateTime.now().plusDays(3))
            .ticketCancelableUntil(LocalDateTime.now().plusDays(25))
            .performance(givenPerformance)
            .build();
        roundRepository.save(round2);

        List<RoundSeat> roundSeats = new ArrayList<>();
        for (Round round : List.of(givenRound, round2)) {
            for (Seat seat : seats) {
                roundSeats.add(new RoundSeat(round, seat));
            }
        }
        givenReservedRoundSeat = roundSeats.get(0);
        givenUnReservedRoundSeat = roundSeats.get(1);
        roundSeatRepository.saveAll(roundSeats);

        givenUser = new User("tester");
        userRepository.save(givenUser);

        givenTicket = new Ticket(givenUser, givenRound, givenSeatA1);
        givenReservedRoundSeat.waitForPayment();
        ticketRepository.save(givenTicket);
    }

}
