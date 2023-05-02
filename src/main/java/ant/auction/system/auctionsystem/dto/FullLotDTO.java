package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import lombok.Data;

@Data
public class FullLotDTO {

    private Long id;
    private Status status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;

    private Integer currentPrice;
    private BidDTO lastBid;


    public static FullLotDTO fromLot(Lot lot) {
        FullLotDTO dto = new FullLotDTO();
        dto.setId(lot.getId());
        dto.setStatus(lot.getStatus());
        dto.setTitle(lot.getTitle());
        dto.setDescription(lot.getDescription());
        dto.setStartPrice(lot.getStartPrice());
        dto.setBidPrice(lot.getBidPrice());
        return dto;
    }
}
