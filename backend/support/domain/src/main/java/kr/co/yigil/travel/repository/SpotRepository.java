package kr.co.yigil.travel.repository;


import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findByIdAndMemberId(Long spotId, Long memberId);

    @Query("SELECT s FROM Spot s WHERE s.place.id = :placeId AND s.isInCourse = false")
    Slice<Spot> findAllByPlaceIdAndIsInCourseFalse(@Param("placeId") Long placeId, Pageable pageable);

    @Query("SELECT s FROM Spot s WHERE s.member.id = :memberId AND s.isInCourse = false")
    Slice<Spot> findAllByMemberAndIsInCourseFalse(Long memberId, Pageable pageable);
}
