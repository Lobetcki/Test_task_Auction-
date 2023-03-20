package ant.auction.system.auctionsystem.dto;

import ant.auction.system.auctionsystem.model.Lot;
import lombok.Data;

@Data
public class CreateLotDTO {

    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;


    public static CreateLotDTO fromLot(Lot lot) {
        CreateLotDTO dto = new CreateLotDTO();

       // dto.setStatus(dto.getStatus());
        dto.setTitle(lot.getTitle());
//        dto.setDescription(lot.getDescription());
//        dto.setStartPrice(lot.getStartPrice());
//        dto.setBidPrice(lot.getBidPrice());

        return dto;
    }

    public Lot toLot() {
        Lot lot = new Lot();

        lot.setStatus("CREATED");
        lot.setTitle(this.getTitle());
        lot.setDescription(this.getDescription());
        lot.setStartPrice(this.getStartPrice());
        lot.setBidPrice(this.getBidPrice());

        return lot;
    }

}
