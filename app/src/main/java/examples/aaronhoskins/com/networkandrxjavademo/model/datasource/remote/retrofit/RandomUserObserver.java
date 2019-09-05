package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import examples.aaronhoskins.com.networkandrxjavademo.model.events.RnadomUserResponseEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RandomUserObserver implements Observer<RandomMeResponse> {
    private RandomMeResponse randomMeResponse;
    @Override
    public void onSubscribe(Disposable d) {
        Log.d("RxJAVA_OBR", "Subscribed");
    }

    @Override
    public void onNext(RandomMeResponse randomMeResponse) {
        this.randomMeResponse = randomMeResponse;
    }

    @Override
    public void onError(Throwable e) {
        Log.e("RxJAVA_OBR", "ERROR IN RX OBSERVER -->", e);
    }

    @Override
    public void onComplete() {
        EventBus.getDefault().post(new RnadomUserResponseEvent(randomMeResponse));
    }
}
