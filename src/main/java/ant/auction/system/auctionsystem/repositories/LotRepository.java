package ant.auction.system.auctionsystem.repositories;

import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    List<Lot> findLotByStatus(Pageable pageable, Status status);

    //- Получить текущую цену лота. Должен вернуть число.
    @Query(value = "SELECT l.bid_price * (SELECT COUNT(b) AS count FROM bid b WHERE lot_id = ?1) " +
            "+ l.start_price AS sum FROM lot AS l  WHERE l.id = ?1", nativeQuery = true)
    Integer getCurrentPriceLotId(Long lotId);
}
