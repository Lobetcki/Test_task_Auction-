package ant.auction.system.auctionsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;

    @OneToMany(mappedBy = "lot")
    private List<Bid> lastBid;

}
