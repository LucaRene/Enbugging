package Web.Tracking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/interactions")
    public List<UserInteraction> getUserInteractions() {
        System.out.println(trackingService.getUserInteractions());
        return trackingService.getUserInteractions();
    }
}