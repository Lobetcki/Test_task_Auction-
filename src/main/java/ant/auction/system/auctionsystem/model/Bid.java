package ant.auction.system.auctionsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public LocalDateTime bidDate;
    public String bidderName;

    @ManyToOne
    public Lot lot;

}
