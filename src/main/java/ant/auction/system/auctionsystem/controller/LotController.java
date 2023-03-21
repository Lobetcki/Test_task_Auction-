package ant.auction.system.auctionsystem.controller;

import ant.auction.system.auctionsystem.dto.*;
import ant.auction.system.auctionsystem.model.Status;
import ant.auction.system.auctionsystem.service.LotService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auction")
@RestController
public class LotController {

    private LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }


                                                             //1 Get Получить информацию о первом ставившем на лот
    @GetMapping ("/lot/{id}/first")
    public ResponseEntity<BidDTO> getFirstBetOnTheLot (@PathVariable Long id) {

        return ResponseEntity.ok(lotService.getFirstBid(id));
    }

                                                            //2 Get Возвращает имя ставившего на данный лот наибольшее количество раз
    @GetMapping("/lot/{id}/frequent")
    public ResponseEntity<String> getMostFrequentBidder(@PathVariable Long id) {
        return ResponseEntity.ok(lotService.getMostFrequentBidder(id));
    }

                                                            //3 lot/{id} Получить полную информацию о лоте

    @GetMapping("/lot/{id}/full")
    public ResponseEntity<FullLotDTO> getFullLot(@PathVariable Long id) {
        FullLotDTO fullLotDTO = lotService.getFullLot(id);
        if (fullLotDTO == null) {
            return ResponseEntity.notFound().build();
        }
    return ResponseEntity.ok(fullLotDTO);
    }
//// -------------------------------------------------------------------------------------------------------------------
                                                            //4 start Начать торги по лоту
    @PostMapping("/lot/{id}/start")
    public ResponseEntity<String> startLot(@PathVariable Long id) {
        LotDTO lotDTO = lotService.startLot(id);
        if (lotDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лот не найден");
        }
        return  ResponseEntity.ok("Лот переведен в статус начато");
    }


                                                        //5 bid Сделать ставку по лоту
    @PostMapping ("/lot/{id}/bid")
    public ResponseEntity<String> createdBid(@PathVariable Long id, @RequestBody CreateBidDTO createBidDTO){
        LotDTO lotDTO = lotService.createdBid(id, createBidDTO);
        if (lotDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лот не найден");
        }
        if (!(lotDTO.getStatus().equals(Status.STARTED))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Лот в неверном статусе");
        }
        return  ResponseEntity.ok("Ставка создана");

    }

                                                           //6 stop Остановить торги по лот
    @PostMapping("/lot/{id}/stop")
    public ResponseEntity<String> stopLot(Long id) {
        LotDTO lotDTO = lotService.stopLot(id);
        if (lotDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лот не найден");
        }
        return  ResponseEntity.ok("Лот перемещен в статус остановлен");
    }

                                                               //7 lot Создает новый лот
    @PostMapping("/lot")
    public ResponseEntity<String> createdLot(@RequestBody CreateLotDTO createLotDTO) {

        return  ResponseEntity.ok("Лот: " + lotService.createdLot(createLotDTO) + " успешно создан");
    }

// -------------------------------------------------------------------------------------------------------------------


                            //8 get lot Получить все лоты, основываясь на фильтре статуса и номере страницы
    @GetMapping("/lots")
    public ResponseEntity<List<LotDTO>> findLots(Pageable pageable, @RequestParam("status") Status status) {
        return ResponseEntity.ok(lotService.findLots(pageable, status));
    }



                                                     //9 Экспортировать все лоты в файл CSV
    @GetMapping("lot/export")
    public void getCSVFile() {
        


    }



}
