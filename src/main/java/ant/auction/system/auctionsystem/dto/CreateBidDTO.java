package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateBidDTO {

    public String bidderName;
    @JsonIgnore
    public Lot lot;

    public static CreateBidDTO fromBid(Bid bid) {
        CreateBidDTO dto = new CreateBidDTO();
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
