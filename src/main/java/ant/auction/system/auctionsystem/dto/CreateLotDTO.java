package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import lombok.Data;

@Data
public class CreateLotDTO {

    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;

    public Lot toLot() {
        Lot lot = new Lot();

        lot.setStatus(Status.CREATED);
        lot.setTitle(this.getTitle());
        lot.setDescription(this.getDescription());
        lot.setStartPrice(this.getStartPrice());
        lot.setBidPrice(this.getBidPrice());

        return lot;
    }

}
