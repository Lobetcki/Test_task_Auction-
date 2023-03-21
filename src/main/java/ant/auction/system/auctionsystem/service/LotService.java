package ant.auction.system.auctionsystem.service;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.repositories.BidRepository;
import ant.auction.system.auctionsystem.repositories.LotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        logger.info("Получает информацию о первом ставившем на лот");
        return BidDTO.fromBid(bidRepository.findBylotIdFirstBid(lotId));
    }

                //2 Get Возвращает имя ставившего на данный лот наибольшее количество раз
    public BidDTO getMostFrequentBidder(Long lotId) {
        BidDTO bidDTO = BidDTO.fromBid(bidRepository.getMostFrequentBidder(lotId));

        return bidDTO;
    }

                                                //3 lot/{id} Получить полную информацию о лоте
    public Integer getCurrentPrice(Long lotId) {
        FullLotDTO fullLotDTO = FullLotDTO.fromLot(lotRepository.findById(lotId).get());
        return fullLotDTO.getStartPrice() + (fullLotDTO.getBidPrice() * bidRepository.getBidCountByLotId(lotId));
    }

    public FullLotDTO getFullLot(Long lotId) {
        logger.info("Возвращает полную информацию о лоте с последним ставившим и текущей ценой");
        FullLotDTO fullLotDTO = FullLotDTO.fromLot(lotRepository.findById(lotId).get());
        fullLotDTO.setLastBid(BidDTO.fromBid(bidRepository.findBylotIdFinalBid(lotId)));
        fullLotDTO.setCurrentPrice(getCurrentPrice(lotId));
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
    public LotDTO createdBid(Long lotId, CreateBidDTO createBidDTOBidDTO) {
        logger.info("Делает заявку по лоту");
        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(lotId).get());
        createBidDTOBidDTO.setLot(lotRepository.findById(lotId).get());
        CreateBidDTO.fromBid(bidRepository.save(createBidDTOBidDTO.toBid()));
        return lotDTO;
    }

                                                        //6 lot/{id}/stop Остановить торги по лот
    public LotDTO stopLot(Long lotId) {
        logger.info("Останавливает торги по лоту");
        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(lotId).get());
        lotDTO.setStatus(Status.STOPPED);
        LotDTO.fromLot(lotRepository.save(lotDTO.toLot()));
        return lotDTO;
    }

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
                    .peek(fullLotDTO -> fullLotDTO.setCurrentPrice(getCurrentPrice(fullLotDTO.getId())))
                    .peek(fullLotDTO -> fullLotDTO.setLastBid(BidDTO.fromBid(bidRepository.findBylotIdFinalBid(fullLotDTO.getId()))))
                    .collect(Collectors.toList());
    }



}
