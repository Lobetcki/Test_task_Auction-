package ant.auction.system.auctionsystem.repositories;

import ant.auction.system.auctionsystem.dto.BidDTO;
import ant.auction.system.auctionsystem.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

            //- Возможность получить последнюю заявку (bid) ставившего на лот. Должен вернуть Bid.
    @Query(value = "SELECT * FROM bid b WHERE lot_id = ?1 ORDER BY bid_date DESC LIMIT 1", nativeQuery = true)
    Bid findBylotIdFinalBid(Long lotId);

             //- Возможность получить количество всех bid для лота. Должен вернуть число.
    @Query(value = "SELECT COUNT(b) AS count FROM bid b WHERE lot_id = ?1", nativeQuery = true)
    Integer getBidCountByLotId(Long lotId);

                                //-Получить информацию о первом ставившем на лот.
    @Query(value = "SELECT * FROM bid b WHERE lot_id = ?1 ORDER BY bid_date LIMIT 1", nativeQuery = true)
    Bid findBylotIdFirstBid(Long lotId);

    @Query(value = "SELECT bidder_name, max(bid_date) FROM bid " +
            "WHERE lot_id = ?1 " +
            "GROUP BY bidder_name ORDER BY count(*) DESC LIMIT 1", nativeQuery = true)
    Bid getMostFrequentBidder(Long lotId);
}
