package ant.auction.system.auctionsystem.service;

import ant.auction.system.auctionsystem.dto.BidDTO;
import ant.auction.system.auctionsystem.dto.CreateLotDTO;
import ant.auction.system.auctionsystem.dto.FullLotDTO;
import ant.auction.system.auctionsystem.dto.LotDTO;
import ant.auction.system.auctionsystem.model.Bid;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.repositories.BidRepository;
import ant.auction.system.auctionsystem.repositories.LotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        logger.info("Was invoked method for get all the students");

        return null;
    }




                                                //3 lot/{id} Получить полную информацию о лоте
    public FullLotDTO getFullLot(Long lotId) {
        logger.info("Возвращает полную информацию о лоте с последним ставившим и текущей ценой");
        FullLotDTO fullLotDTO = FullLotDTO.fromLot(lotRepository.findById(lotId).get());
        fullLotDTO.setLastBid(bidRepository.findBylotIdFinalBid(lotId));
        fullLotDTO.setCurrentPrice(fullLotDTO.getStartPrice() +
                (fullLotDTO.getBidPrice() * bidRepository.getBidCountByLotId()));
        return fullLotDTO;
    }

                                                              //4 start Начать торги по лоту
    public LotDTO startLot(Long lotId) {
        logger.info("Лот переведен в статус начато");
        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(lotId).get());
        lotDTO.setStatus(Status.STARTED);
        LotDTO.fromLot(lotRepository.save(lotDTO.toLot()));
        return lotDTO;
    }

                                                            //5 lot/{id}/bid Сделать ставку по лоту
    public LotDTO createdBid(Long lotId, String bidderName) {
        logger.info("Делает заявку по лоту");
        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(lotId).get());
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderName(bidderName);
        bidDTO.setLot(lotRepository.findById(lotId).get());
        BidDTO.fromBid(bidRepository.save(bidDTO.toBid()));
        return lotDTO;
    }

                                                             //7 lot Создает новый лот
    public String createdLot(CreateLotDTO createLotDTO) {
        logger.info("Новый лот создается");
        CreateLotDTO.fromLot(lotRepository.save(createLotDTO.toLot()));
        return createLotDTO.getTitle();

    }

}
