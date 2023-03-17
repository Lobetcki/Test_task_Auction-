package ant.auction.system.auctionsystem.controller;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auction")
@RestController
public class LotController {

                                                             //1 Get Получить информацию о первом ставившем на лот
    @GetMapping ("/lot/{id}/first")
    public BidDTO getFirstBetOnTheLot (Lot lotId) {

        return null;
    }
                                                            //2 Get Возвращает имя ставившего на данный лот наибольшее количество раз
    @GetMapping("/lot/{id}/frequent")
    public String nameGreatVariety() {
        return null;

    }

                                                            //3 lot/{id} Получить полную информацию о лоте

    @GetMapping("/lot/{id}/full")
    public Lot getLot() {
    return null;
    }
// -------------------------------------------------------------------------------------------------------------------
                                                            //4 lot/{id}/start Начать торги по лоту
    @PostMapping("/lot/{id}/start")
    public String startLot() {
        if (lot != 0){

        }
return null;
    }


                                                            //5 lot/{id}/bid Сделать ставку по лоту
    @PostMapping ("/lot/{id}/bid")
    public String Integer(){
        return "1";

    }

                                                           //6 lot/{id}/stop Остановить торги по лот
    @PostMapping("/lot/{id}/stop")
    public String stopLot() {
        if (lot != 0) {

        }
        return null;
    }

                                                               //7 lot Создает новый лот
    @PostMapping("/lot/{id}/stop")
    public String CreatedLot() {

    }

// -------------------------------------------------------------------------------------------------------------------


                            //8 get lot Получить все лоты, основываясь на фильтре статуса и номере страницы
    @GetMapping("/lot")
    public LotDto getAllLotsByStatusAndNumberPage() {

    }
                                            //9 Экспортировать все лоты в файл CSV
    @GetMapping("lot/export")
    public void exportAllLotsToCSVFile () {

    }



}
