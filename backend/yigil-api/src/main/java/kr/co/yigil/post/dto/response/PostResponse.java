package kr.co.yigil.post.dto.response;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import lombok.Data;
import lombok.Setter;

@Data
public class PostResponse {
    private Long postId;
    private Long travelId;

    private String title;
    private String imageUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

//    private int likeCount;
    private int commentCount;

    public static PostResponse from(Travel travel, Post post, int commentCount) {
        if (travel instanceof Spot spot) {
            return from(spot, post, commentCount);
        } else if(travel instanceof Course course){
            return from(course, post, commentCount);
        } else{
            throw new BadRequestException(ExceptionCode.TRAVEL_CASTING_ERROR);
        }
    }

    public static PostResponse from(Spot spot, Post post, int commentCount) {
        return new PostResponse(
            post.getId(),
            spot.getId(),
            spot.getTitle(),
            spot.getFileUrl(),
            spot.getDescription(),
            post.getMember().getNickname(),
            post.getMember().getProfileImageUrl(),
            commentCount
        );
    }

    public static PostResponse from(Course course, Post post, int commentCount) {
        Spot representativeSpot = course.getSpots().get(course.getRepresentativeSpotOrder());
        String imgUrl = representativeSpot.getFileUrl();
        String description = representativeSpot.getDescription();

        return new PostResponse(
            post.getId(),
            course.getId(),
            course.getTitle(),
            imgUrl,
            description,
            post.getMember().getNickname(),
            post.getMember().getProfileImageUrl(),
            commentCount
        );
    }

    public PostResponse(Long postId, Long travelId, String title, String imageUrl, String description, String memberNickname, String memberImageUrl, int commentCount) {
        this.postId = postId;
        this.travelId = travelId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.memberNickname = memberNickname;
        this.memberImageUrl = memberImageUrl;
        this.commentCount = commentCount;
    }

}
