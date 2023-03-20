package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.repositories.LotRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class BidDTO {

    private Long id;
    public LocalDateTime bidDate;
    public String bidderName;
    public Lot lot;

    public static BidDTO fromBid(Bid bid) {
        BidDTO dto = new BidDTO();

        dto.setId(bid.getId());
        dto.setBidDate(bid.getBidDate());
        dto.setBidderName(bid.getBidderName());
        dto.setLot(bid.getLot());

        return dto;
    }

    public Bid toBid() {
        Bid bid = new Bid();

        bid.setBidDate(LocalDateTime.now());
        bid.setBidderName(this.getBidderName());
        bid.setLot(this.getLot());
        return bid;
    }


}
