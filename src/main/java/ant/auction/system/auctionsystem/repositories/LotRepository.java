package ant.auction.system.auctionsystem.repositories;

import ant.auction.system.auctionsystem.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {



}
