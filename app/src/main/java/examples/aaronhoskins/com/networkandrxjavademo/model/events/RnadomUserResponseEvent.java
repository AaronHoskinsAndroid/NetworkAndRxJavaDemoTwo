package examples.aaronhoskins.com.networkandrxjavademo.model.events;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;

public class RnadomUserResponseEvent {
    RandomMeResponse randomMeResponse;

    public RnadomUserResponseEvent(RandomMeResponse randomMeResponse) {
        this.randomMeResponse = randomMeResponse;
    }

    public RandomMeResponse getRandomMeResponse() {
        return randomMeResponse;
    }

    public void setRandomMeResponse(RandomMeResponse randomMeResponse) {
        this.randomMeResponse = randomMeResponse;
    }
}
