package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BidDTO {

    public String bidderName;
    public LocalDateTime bidDate;
    @JsonIgnore
    public Lot lot;

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
        bid.setLot(this.getLot());
        return bid;
    }

}
