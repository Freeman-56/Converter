package converter.repository;

import converter.Model.Valute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ValuteRepository extends JpaRepository<Valute, Integer> {
    @Query(value = "select * from valute where last_update = ?1", nativeQuery = true)
    List<Valute> findValutesByFreshDate(LocalDate now);

}
