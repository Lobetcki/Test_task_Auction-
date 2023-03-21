package ant.auction.system.auctionsystem.repositories;

import ant.auction.system.auctionsystem.dto.LotDTO;
import ant.auction.system.auctionsystem.model.Lot;
import ant.auction.system.auctionsystem.model.Status;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    List<Lot> findLotByStatus(Pageable pageable, Status status);



}
