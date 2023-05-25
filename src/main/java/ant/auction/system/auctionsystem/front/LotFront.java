package ant.auction.system.auctionsystem.front;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.service.LotService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LotFront {
    private final LotService lotService;

    public LotFront(LotService lotService) {
        this.lotService = lotService;
    }

    //1 Получить информацию о первом ставившем на лот
    public BidDTO getFirstBid(Long lotId) {
        Bid bid = lotService.getFirstBid(lotId);
        if (bid == null) return null;
        return BidDTO.fromBid(bid);
    }

    //2 Получить полную информацию о лоте
    public FullLotDTO getFullLot(Long lotId) {
        try {
            FullLotDTO fullLotDTO = new FullLotDTO();
            fullLotDTO.setLotDTO(LotDTO.fromLot(lotService.getLot(lotId)));
            fullLotDTO.setCurrentPrice(lotService.currentPriceLot(lotId));
            Bid bid = lotService.getBid(lotId);
            if (bid == null) {
                fullLotDTO.setLastBid(null);
            } else {
                fullLotDTO.setLastBid(BidDTO.fromBid(bid));
            }
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

    //5 Создает новый лот
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
