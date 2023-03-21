package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Bid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BidDTO {

    public String bidderName;
    public LocalDateTime bidDate;


    public static BidDTO fromBid(Bid bid) {
        BidDTO dto = new BidDTO();

        dto.setBidDate(bid.getBidDate());
        dto.setBidderName(bid.getBidderName());

        return dto;
    }

    public Bid toBid() {
        Bid bid = new Bid();

        bid.setBidDate(LocalDateTime.now());
        bid.setBidderName(this.getBidderName());

        return bid;
    }

}
