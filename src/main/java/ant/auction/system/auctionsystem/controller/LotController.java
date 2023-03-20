package ant.auction.system.auctionsystem.controller;

import ant.auction.system.auctionsystem.dto.CreateLotDTO;
import ant.auction.system.auctionsystem.dto.FullLotDTO;
import ant.auction.system.auctionsystem.service.LotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auction")
@RestController
public class LotController {

    private LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }


//                                                             //1 Get Получить информацию о первом ставившем на лот
//    @GetMapping ("/lot/{lotId}/first")
//    public ResponseEntity<BidDTO> getFirstBetOnTheLot (@PathVariable Long lotId) {
//
//      //  return ResponseEntity.ok(lotService.getFirstBid(lotId));
//        return null;
//    }
//                                                            //2 Get Возвращает имя ставившего на данный лот наибольшее количество раз
//    @GetMapping("/lot/{id}/frequent")
//    public String nameGreatVariety() {
//
//
//        return null;
//
//    }
//
//                                                            //3 lot/{id} Получить полную информацию о лоте

    @GetMapping("/lot/{id}/full")
    public ResponseEntity<FullLotDTO> getLot(@PathVariable Long id) {
        FullLotDTO fullLotDTO = lotService.getFullLot(id);
        if (fullLotDTO == null) {
            return ResponseEntity.notFound().build();
        }
    return ResponseEntity.ok(fullLotDTO);
    }
//// -------------------------------------------------------------------------------------------------------------------
                                                            //4 start Начать торги по лоту
    @PostMapping("/lot/{id}/start")
    public ResponseEntity<String> startLot(Long id) {
        lotService.startLot(id);
        return  ResponseEntity.ok("Лот переведен в статус начато");
    }
//
//
//                                                            //5 lot/{id}/bid Сделать ставку по лоту
//    @PostMapping ("/lot/{id}/bid")
//    public String Integer(){
//        return "1";
//
//    }
//
//                                                           //6 lot/{id}/stop Остановить торги по лот
//    @PostMapping("/lot/{id}/stop")
//    public String stopLot(Long lotId) {
////        if (lotId != 0) {
////
////        }
//        return null;
//    }

                                                               //7 lot Создает новый лот
    @PostMapping("/lot")
    public ResponseEntity<String> CreatedLot(@RequestBody CreateLotDTO createLotDTO) {

        return  ResponseEntity.ok("Лот: " + lotService.createdLot(createLotDTO) + " успешно создан");
    }

// -------------------------------------------------------------------------------------------------------------------


                            //8 get lot Получить все лоты, основываясь на фильтре статуса и номере страницы
//    @GetMapping("/lot")
//    public List<LotDTO> getAllLotsByStatusAndNumberPage() {
//            return null;
//    }
//                                            //9 Экспортировать все лоты в файл CSV
//    @GetMapping("lot/export")
//    public void exportAllLotsToCSVFile () {
//
//    }



}
