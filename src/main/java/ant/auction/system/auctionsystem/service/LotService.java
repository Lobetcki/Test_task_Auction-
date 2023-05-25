package ant.auction.system.auctionsystem.service;

import ant.auction.system.auctionsystem.dto.BidDTO;
import ant.auction.system.auctionsystem.dto.LotDTO;
import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.repositories.BidRepository;
import ant.auction.system.auctionsystem.repositories.LotRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LotService {

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;

    public LotService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

    //1 Получить информацию о первом ставившем на лот
    public Bid getFirstBid(Long lotId) {
        Bid bid = bidRepository.findBylotIdFirstBid(lotId);
        return bid;
    }

    //2 Получить полную информацию о лоте
    public Lot getLot(Long lotId) {
        return lotRepository.findById(lotId).orElse(null);
    }

    public Bid getBid(Long lotId) {
        return bidRepository.findBylotIdFinalBid(lotId);
    }

    public Integer currentPriceLot(Long lotId) {
        return lotRepository.getСurrentPriceLotId(lotId);
    }

    //3 Начать или закончить торги по лоту
    public Boolean startStopLot(Long lotId, String status) {
        try {
            Lot lot = lotRepository.findById(lotId).orElse(null);
            LotDTO lotDTO = LotDTO.fromLot(lot);
            if (status.equals("start")) {
                lotDTO.setStatus(Status.STARTED);
            }
            if (status.equals("stop")) {
                lotDTO.setStatus(Status.STOPPED);
            }
            LotDTO.fromLot(lotRepository.save(lotDTO.toLot()));
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    //4 Сделать ставку по лоту
    @Transactional
    public Lot createdBid(Long lotId, Bid bid) {

        Lot lot = Objects.requireNonNull(
                lotRepository.findById(lotId).orElse(null));
        if (lot.getStatus().equals(Status.STARTED)) {
            bid.setLot(lot);
            bidRepository.save(bid);
        }
        return lot;
    }

    //5 Создает новый лот
    public String createdLot(Lot lot) {
        lotRepository.save(lot);
        return lot.getTitle();
    }

    //6 Получить все лоты, основываясь на фильтре статуса и номере страницы
    public List<Lot> findLots1(Pageable pageable, Status status) {
        return lotRepository.findLotByStatus(pageable, status);
    }
}
