package ant.auction.system.auctionsystem.projections;

import java.time.LocalDateTime;

public interface FrequentView {

    LocalDateTime getBidDate();
    String getBidderName();
}
