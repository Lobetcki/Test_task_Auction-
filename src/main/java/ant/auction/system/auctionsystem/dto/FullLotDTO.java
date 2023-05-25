package ant.auction.system.auctionsystem.dto;

import lombok.Data;

@Data
public class FullLotDTO {

    private LotDTO lotDTO;
    private Integer currentPrice;
    private BidDTO lastBid;
}
