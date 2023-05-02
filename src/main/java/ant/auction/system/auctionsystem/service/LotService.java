package ant.auction.system.auctionsystem.service;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.projections.FrequentView;
import ant.auction.system.auctionsystem.repositories.BidRepository;
import ant.auction.system.auctionsystem.repositories.LotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LotService  {

    private static final Logger logger = LoggerFactory.getLogger(LotService.class);

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;


    public LotService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

                                                //1 Get Получить информацию о первом ставившем на лот
    public BidDTO getFirstBid(Long lotId) {
        BidDTO bidDTO = BidDTO.fromBid(bidRepository.findBylotIdFirstBid(lotId));
        logger.debug("Получает информацию о первом ставившем на лот: " + bidDTO);
        return bidDTO;
    }

                //2 Get Возвращает имя ставившего на данный лот наибольшее количество раз
    public FrequentView getMostFrequentBidder(Long lotId) {
        logger.info("Возвращает имя ставившего на данный лот наибольшее количество раз");
        try {
        return bidRepository.getMostFrequentBidder(lotId);
        } catch (NullPointerException e) {
            return null;
        }
    }

                                                //3 lot/{id} Получить полную информацию о лоте
    public FullLotDTO getFullLot(Long lotId) {
        try {
            Lot lot = lotRepository.findById(lotId).orElse(null);
            FullLotDTO fullLotDTO = FullLotDTO.fromLot(lot);
            Bid bid = bidRepository.findBylotIdFinalBid(lotId);
            fullLotDTO.setCurrentPrice(fullLotDTO.getStartPrice() + (fullLotDTO.getBidPrice() * bidRepository.getBidCountByLotId(lotId)));
            if (bid == null) {
                fullLotDTO.setLastBid(null);
            } else {
                fullLotDTO.setLastBid(BidDTO.fromBid(bidRepository.findBylotIdFinalBid(lotId)));
            }
            logger.info("Возвращает полную информацию о лоте с последним ставившим и текущей ценой " + fullLotDTO);
            return fullLotDTO;
        } catch (NullPointerException e) {
            return null;
        }
    }

                                                              //4 start Начать или закончить торги по лоту
    public Boolean startStopLot(Long lotId, String start) {
        logger.info("Лот переведен в статус начато");
        try {
            Lot lot = lotRepository.findById(lotId).orElse(null);
            LotDTO lotDTO = LotDTO.fromLot(lot);
            if (start.equals("start")) {
                lotDTO.setStatus(Status.STARTED);
            }
            if (start.equals("stop")) {
                lotDTO.setStatus(Status.STOPPED);
            }
            LotDTO.fromLot(lotRepository.save(lotDTO.toLot()));
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

                                                            //5 lot/{id}/bid Сделать ставку по лоту
    @Transactional
    public LotDTO createdBid(Long lotId, CreateBidDTO createBidDTO) {
        logger.info("Делает заявку по лоту");
        try {
        LotDTO lotDTO = LotDTO.fromLot(Objects.requireNonNull(lotRepository.findById(lotId).orElse(null)));
        createBidDTO.setLot(lotDTO.toLot());
        CreateBidDTO.fromBid(bidRepository.save(createBidDTO.toBid()));
        return lotDTO;
        } catch (NullPointerException e) {
            return null;
        }
    }


                                                        //6 lot/{id}/stop Остановить торги по лот
//    public LotDTO stopLot(Long lotId) {
//        logger.info("Останавливает торги по лоту");
//        try {
//        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(lotId).get());
//        lotDTO.setStatus(Status.STOPPED);
//        LotDTO.fromLot(lotRepository.save(lotDTO.toLot()));
//        return lotDTO;
//            return true;
//        } catch (NullPointerException e) {
//            return false;
//        }
//    }

                                                             //7 lot Создает новый лот
    public String createdLot(CreateLotDTO createLotDTO) {
        logger.info("Новый лот создается");
        CreateLotDTO.fromLot(lotRepository.save(createLotDTO.toLot()));
        return createLotDTO.getTitle();
    }

                                //8 get lot Получить все лоты, основываясь на фильтре статуса и номере страницы
    public List<LotDTO> findLots(Pageable pageable, Status status) {
        logger.info("Получает все лоты, основываясь на фильтре статуса");
        return lotRepository.findLotByStatus(pageable, status)
                .stream().map(LotDTO::fromLot)
                .collect(Collectors.toList());
    }

                                                    //9 Экспортировать все лоты в файл CSV
    public List<FullLotDTO> getCSVFile() {
        logger.info("Экспортирует все лоты в файл CSV");
            return  lotRepository.findAll()
                    .stream().map(FullLotDTO::fromLot)
                    .peek(fullLotDTO -> fullLotDTO.setCurrentPrice(fullLotDTO.getStartPrice() + (fullLotDTO.getBidPrice() * bidRepository.getBidCountByLotId(fullLotDTO.getId()))))
                    .peek(fullLotDTO -> fullLotDTO.setLastBid(BidDTO.fromBid(bidRepository.findBylotIdFinalBid(fullLotDTO.getId()))))
                    .collect(Collectors.toList());
    }



}
