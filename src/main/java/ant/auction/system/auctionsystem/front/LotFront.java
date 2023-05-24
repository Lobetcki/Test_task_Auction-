package ant.auction.system.auctionsystem.front;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.service.LotService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LotFront {
    private final LotService lotService;

    public LotFront(LotService lotService) {
        this.lotService = lotService;
    }

    //1 Get Получить информацию о первом ставившем на лот
    public BidDTO getFirstBid(Long lotId) { // неработает
        Bid bid = lotService.getFirstBid(lotId);
        if (bid == null) return null;
        BidDTO bidDTO = BidDTO.fromBid(bid);
        return bidDTO;
    }

    //2 lot/{id} Получить полную информацию о лоте
    public FullLotDTO getFullLot(Long lotId) {
        try {
            FullLotDTO fullLotDTO = FullLotDTO.fromLot(lotService.getLot(lotId));

            Bid bid = lotService.getBid(lotId);
            if (bid == null) {
                fullLotDTO.setLastBid(null);
            } else {
                fullLotDTO.setLastBid(BidDTO.fromBid(bid));
            }

            fullLotDTO.setCurrentPrice(lotService.currentPriceLot(lotId));

            return fullLotDTO;
        } catch (NullPointerException e) {
            return null;
        }
    }

    //3 Начать или закончить торги по лоту
    public Boolean startStopLot(Long lotId, String status) {
        return lotService.startStopLot(lotId, status);
    }

    //4 Сделать ставку по лоту
    public LotDTO createdBid(Long lotId, CreateBidDTO createBidDTO) {
        try {
            Bid bid = createBidDTO.toBid();
            Lot lot = lotService.createdBid(lotId, bid);
            return LotDTO.fromLot(lot);
        } catch (NullPointerException e) {
            return null;
        }
    }

    //5 lot Создает новый лот
    public String createdLot(CreateLotDTO createLotDTO) {
        return lotService.createdLot(createLotDTO.toLot());
    }

    //6 Получить все лоты, основываясь на фильтре статуса и номере страницы
    public List<LotDTO> findLots(Pageable pageable, Status status) {
        List<Lot> lots = lotService.findLots1(pageable, status);
        return lots
                .stream().map(LotDTO::fromLot)
                .collect(Collectors.toList());
    }
}
