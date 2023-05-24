package ant.auction.system.auctionsystem.controller;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.front.LotFront;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.service.LotService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/lot")
@RestController
public class LotController {

    private final LotService lotService;
    private final LotFront lotFront;

    public LotController(LotService lotService, LotFront lotFront) {
        this.lotService = lotService;
        this.lotFront = lotFront;
    }

    //1 Получить информацию о первом ставившем на лот
    @GetMapping ("/{id}/firstBid")
    public ResponseEntity<BidDTO> getFirstBetOnTheLot (@PathVariable Long id) {
        BidDTO bidDTO = lotFront.getFirstBid(id);
        if (bidDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bidDTO);
    }

    //2 Получить полную информацию о лоте
    @GetMapping("/{id}")
    public ResponseEntity<FullLotDTO> getFullLot(@PathVariable Long id) {
        FullLotDTO fullLotDTO = lotFront.getFullLot(id);
        if (fullLotDTO == null) {
            return ResponseEntity.notFound().build();
        }
    return ResponseEntity.ok(fullLotDTO);
    }

    //3 Начать торги по лоту
    @PostMapping("/{id}/start")
    public ResponseEntity<String> startLot(@PathVariable Long id) {
        Boolean lotBoolean = lotFront.startStopLot(id, "start");
        if (!lotBoolean) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лот не найден");
        }
        return  ResponseEntity.ok("Лот переведен в статус START");
    }

    //3 Остановить торги по лоту
    @PostMapping("/{id}/stop")
    public ResponseEntity<String> stopLot(Long id) {
        Boolean lotBoolean = lotFront.startStopLot(id, "stop");
        if (!lotBoolean) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лот не найден");
        }
        return  ResponseEntity.ok("Лот перемещен в статус STOP");
    }

    //4 Сделать ставку по лоту
    @PostMapping ("/{id}/createdBid")
    public ResponseEntity<String> createdBid(@PathVariable Long id,
                                             @RequestBody CreateBidDTO createBidDTO) {
        LotDTO lotDTO = lotFront.createdBid(id, createBidDTO);
        if (lotDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лот не найден");
        }
        if (!(lotDTO.getStatus().equals(Status.STARTED))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Лот в неверном статусе");
        }
        return ResponseEntity.ok("Ставка создана");

    }

    //5 Создает новый лот
    @PostMapping("/createdLot")
    public ResponseEntity<String> createdLot(@RequestBody CreateLotDTO createLotDTO) {
        if (createLotDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return  ResponseEntity.ok(
                "Лот: " + lotFront.createdLot(createLotDTO) + " успешно создан");
    }

    //6 Получить все лоты, основываясь на фильтре статуса и номере страницы
    @GetMapping("/all")
    public ResponseEntity<List<LotDTO>> findLots(Pageable pageable,
                                                 @RequestParam("status") Status status) {
        return ResponseEntity.ok(lotService.findLots(pageable, status));
    }
}
