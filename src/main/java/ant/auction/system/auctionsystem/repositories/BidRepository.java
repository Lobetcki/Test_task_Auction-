package ant.auction.system.auctionsystem.repositories;

import ant.auction.system.auctionsystem.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

            //- Возможность получить последнюю заявку (bid) ставившего на лот. Должен вернуть Bid.
    @Query(value = "SELECT * FROM bid b ORDER BY bid_date DESC LIMIT 1", nativeQuery = true)
    Bid findBylotIdFinalBid(Long lotId);

             //- Возможность получить количество всех bid для лота. Должен вернуть число.
    @Query(value = "SELECT COUNT(lot_id) AS count FROM bid b", nativeQuery = true)
    Integer getBidCountByLotId();
}
