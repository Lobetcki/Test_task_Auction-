package ant.auction.system.auctionsystem.service;

import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LotService {
    Bid getFirstBid(Long lotId);
    Lot getLot(Long lotId);
    Bid getBid(Long lotId);
    Integer currentPriceLot(Long lotId);
    Boolean startStopLot(Long lotId, String status);
    Lot createdBid(Long lotId, Bid bid);
    String createdLot(Lot lot);
    List<Lot> findLots1(Pageable pageable, Status status);
}

