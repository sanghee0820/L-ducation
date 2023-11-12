package Lducation.demo.course.controller;

import Lducation.demo.course.dto.CourseDTO;
import Lducation.demo.course.service.CourseService;
import Lducation.demo.course.service.KakaoMapService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InfoController {
    private final KakaoMapService googleMapService;
    private final CourseService courseService;

    @GetMapping("/info")
    public ResponseEntity<List<CourseDTO>> getPosition(
            @RequestParam(value = "x", required = false) final String latitude,
            @RequestParam(value = "y", required = false) final String longitude) {
        List<CourseDTO> courseInfo;
        if (latitude == null && longitude == null) {
            courseInfo = courseService.getCourseInfoAll();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseInfo);
        }
        courseInfo =
                courseService.getCourseInfoByGu(googleMapService.getPosition(latitude, longitude));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseInfo);
    }
}