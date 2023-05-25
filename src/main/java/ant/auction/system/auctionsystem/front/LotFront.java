package ant.auction.system.auctionsystem.front;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.model.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LotFront {
    BidDTO getFirstBid(Long lotId);
    FullLotDTO getFullLot(Long lotId);
    Boolean startStopLot(Long lotId, String status);
    LotDTO createdBid(Long lotId, CreateBidDTO createBidDTO);
    String createdLot(CreateLotDTO createLotDTO);
    List<LotDTO> findLots(Pageable pageable, Status status);
}
