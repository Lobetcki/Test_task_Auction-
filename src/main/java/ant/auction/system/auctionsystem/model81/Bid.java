package ant.auction.system.auctionsystem.model81;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Bid {

    public String bidderName;
    public Date bidDate;



}
