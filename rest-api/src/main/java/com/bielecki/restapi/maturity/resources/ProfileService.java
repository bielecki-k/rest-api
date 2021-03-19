package com.bielecki.restapi.maturity.resources;

import com.bielecki.restapi.maturity.swamp.Profile;
import com.bielecki.restapi.maturity.util.DataFixtureUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("/api/maturity/profiles")
public class ProfileService {

    @PostMapping
    public List<Profile> getAllProfiles() {
        return DataFixtureUtils.allProfiles();
    }

}
