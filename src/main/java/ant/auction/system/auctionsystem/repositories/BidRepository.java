package ant.auction.system.auctionsystem.repositories;

import ant.auction.system.auctionsystem.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    //-Получить информацию о первом ставившем на лот.
    @Query(value = "SELECT * FROM bid b WHERE lot_id = ?1 ORDER BY bid_date LIMIT 1", nativeQuery = true)
    Bid findBylotIdFirstBid(Long lotId);

    //- Получить последнюю заявку (bid) ставившего на лот. Должен вернуть Bid.
    @Query(value = "SELECT * FROM bid b WHERE lot_id = ?1 ORDER BY bid_date DESC LIMIT 1", nativeQuery = true)
    Bid findBylotIdFinalBid(Long lotId);
}
