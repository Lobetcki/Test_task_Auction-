package ant.auction.system.auctionsystem.model81;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Lot {
    private Long id;
    private String status;
    private String title;
    private String description;
    private String startPrice;
    private String bidPrice;
    private String currentPrice;

    OneToMany
    private List<Bid> lastBid;

}
