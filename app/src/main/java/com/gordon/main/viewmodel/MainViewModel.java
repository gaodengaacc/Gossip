package com.gordon.main.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.gordon.main.R;
import com.gordon.main.api.APIConstants;
import com.gordon.main.api.response.BaseResponse;
import com.gordon.main.api.response.NameFiveResponse;
import com.gordon.main.api.response.PhoneOneResponse;
import com.gordon.main.http.ResultInterceptor;
import com.gordon.main.http.converter.APINameConverterFactory;
import com.gordon.main.http.converter.APIPhoneConverterFactory;
import com.gordon.main.model.HttpModel;
import com.gordon.main.utils.ObservableNotifier;
import com.gordon.main.utils.StringMatcherUtils;
import com.gordon.main.viewmodel.base.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author Gordon
 * @since 2017/4/5
 * do()
 */

public class MainViewModel extends ViewModel {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> nameText = new ObservableField<>();
    public final ObservableInt nameTextVisible = new ObservableInt();
    public final ObservableInt phoneTextVisible = new ObservableInt();
    public final ObservableField<Spanned> nameOne = new ObservableField<>();
    public final ObservableField<Spanned> nameTwo = new ObservableField<>();
    public final ObservableField<Spanned> nameThree = new ObservableField<>();
    public final ObservableField<Spanned> nameFour = new ObservableField<>();
    public final ObservableField<Spanned> nameFive = new ObservableField<>();
    public final ObservableField<Spanned> nameScore = new ObservableField<>();
    public final ObservableInt nameScoreVisible = new ObservableInt();
    public final ObservableInt nameOneVisible = new ObservableInt();
    public final ObservableInt nameTwoVisible = new ObservableInt();
    public final ObservableInt nameThreeVisible = new ObservableInt();
    public final ObservableInt nameFourVisible = new ObservableInt();
    public final ObservableInt nameFiveVisible = new ObservableInt();
    public final ObservableInt topVisible = new ObservableInt();
    public final ObservableField<Animation> searchAnimation = new ObservableField<>();
    public final ObservableField<Animation> textAnimation = new ObservableField<>();
    @WatchThis
    public final BaseObservable showDialog = new BaseObservable();
    @WatchThis
    public final ObservableField<String> showText = new ObservableField();
    private List<ObservableField<Spanned>> nameList;
    private List<ObservableInt> nameVisibleList;
    private List<String> baseNameUrlList;
    private List<ResultInterceptor.Sign> signNameList;
    private List<String> basePhoneUrlList;
    private List<ResultInterceptor.Sign> signPhoneList;
    private Animation animationLoading;
    private Animation animationTextBefore;
    private Animation animationButtonBefore;
    private CheckWay checkWay;

    public MainViewModel() {
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
        nameScoreVisible.set(View.VISIBLE);
        nameTextVisible.set(View.INVISIBLE);
        phoneTextVisible.set(View.INVISIBLE);
        nameList = new ArrayList<>();
        nameVisibleList = new ArrayList<>();
        baseNameUrlList = new ArrayList<>();
        signNameList = new ArrayList<>();
        basePhoneUrlList = new ArrayList<>();
        signPhoneList = new ArrayList<>();
        nameList.add(nameOne);
        nameList.add(nameTwo);
        nameList.add(nameThree);
        nameList.add(nameFour);
        nameList.add(nameFive);
        nameVisibleList.add(nameOneVisible);
        nameVisibleList.add(nameTwoVisible);
        nameVisibleList.add(nameThreeVisible);
        nameVisibleList.add(nameFourVisible);
        nameVisibleList.add(nameFiveVisible);
        baseNameUrlList.add(APIConstants.NAME1);
        baseNameUrlList.add(APIConstants.NAME2);
        baseNameUrlList.add(APIConstants.NAME34);
        baseNameUrlList.add(APIConstants.NAME34);
        baseNameUrlList.add(APIConstants.NAME5);
        basePhoneUrlList.add(APIConstants.PHONE1);
        basePhoneUrlList.add(APIConstants.NAME34);
        basePhoneUrlList.add(APIConstants.NAME34);
        signNameList.add(ResultInterceptor.Sign.NameOne);
        signNameList.add(ResultInterceptor.Sign.NameTwo);
        signNameList.add(ResultInterceptor.Sign.NameThree);
        signNameList.add(ResultInterceptor.Sign.NameFour);
        signNameList.add(ResultInterceptor.Sign.NameFive);
        signPhoneList.add(ResultInterceptor.Sign.PhoneOne);
        signPhoneList.add(ResultInterceptor.Sign.NameThree);
        signPhoneList.add(ResultInterceptor.Sign.NameFour);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                doResult(view);
                break;
            case R.id.menu_button:
                showDialog.notifyChange();
                break;
            default:
                break;
        }
    }

    private void doResult(final View view) {
        switch (checkName()) {
            case Null_Way:
                ObservableNotifier.alwaysNotify(showText, "名字或者号码不能为空");
                return;
            case Special_Char_way:
                ObservableNotifier.alwaysNotify(showText, "不能含有特殊字符");
                return;
            case Name_Not_China:
                ObservableNotifier.alwaysNotify(showText, "名字必须只能为汉字或者号码");
                return;
            case Name_length:
                ObservableNotifier.alwaysNotify(showText, "名字长度位8汉字以内");
                return;
            case Is_Name:
                checkWay = CheckWay.Is_Name;
                doResultBefore(view);
                break;
            case Is_Num:
                checkWay = CheckWay.Is_Num;
                doResultBefore(view);
                break;
            default:
                break;
        }
    }

    private void doResultBefore(View view) {
        animationBefore(view);
        nameScore.set(Html.fromHtml("测算中..."));
        for (ObservableField<Spanned> spanned : nameList) {
            spanned.set(Html.fromHtml(""));
        }
        for (ObservableInt visible : nameVisibleList) {
            visible.set(View.GONE);
        }
    }

    private void doNum() {

        for (int i = 0; i < signPhoneList.size(); i++) {
            final int finalI = i;
            new HttpModel(basePhoneUrlList.get(i), (signPhoneList.get(i) == ResultInterceptor.Sign.PhoneOne)
                    ? APIPhoneConverterFactory.create() : APINameConverterFactory.create(),
                    new ResultInterceptor(signPhoneList.get(i))).result(nameText.get(), signPhoneList.get(i))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(BaseResponse response) throws Exception {
                            animationAfter();
                            System.out.println("API_Result Name" + " phone1 =" + response.getContent());
                            if (response.getContent() != null && !response.getContent().equals("") && !response.getContent().equals("null")) {
                                nameVisibleList.get(finalI).set(View.VISIBLE);
                                nameList.get(finalI).set(Html.fromHtml(response.getContent()));
                            }else {
                                nameVisibleList.get(finalI).set(View.GONE);
                            }
                            if (ResultInterceptor.Sign.PhoneOne == signPhoneList.get(finalI)) {
                                PhoneOneResponse phoneOneResponse = (PhoneOneResponse) response;
                                String phoneValue = phoneOneResponse.getPhoneValue().equals("") ? "50" : phoneOneResponse.getPhoneValue();
                                String isGoodOrBad = phoneOneResponse.getGoodOrBad().equals("凶") ? "<font color='#FF0000'>(凶)</font>" : "<font color='#006400'>(吉)</font>";
                                nameScore.set(Html.fromHtml("(" + nameText.get() + ")价值：￥ " + phoneValue + isGoodOrBad + "<br/>"));
                            }
                        }
                    });
        }
    }

    private void doName() {
        nameScore.set(Html.fromHtml("测算中..."));
        for (int i = 0; i < signNameList.size(); i++) {
            final int finalI = i;
            new HttpModel(baseNameUrlList.get(i), APINameConverterFactory.create(), new ResultInterceptor(signNameList.get(i))).result(nameText.get(), signNameList.get(i))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(BaseResponse response) throws Exception {
                            animationAfter();
                            System.out.println("API_Result Name" + finalI + " =" + response.getContent());
                            if (response.getContent() != null && !response.getContent().equals("") && !response.getContent().equals("null")) {
                                nameVisibleList.get(finalI).set(View.VISIBLE);
                                nameList.get(finalI).set(Html.fromHtml(response.getContent()));
                            }
                            if (ResultInterceptor.Sign.NameFive == signNameList.get(finalI)) {
                                NameFiveResponse nameFiveResponse = (NameFiveResponse) response;
                                String nameS = nameFiveResponse.getNameScore().equals("null") ? "50" : nameFiveResponse.getNameScore();
                                nameScore.set(Html.fromHtml(nameText.get() + "名字得分：" + "<font color='#FF0000'>"
                                        + nameS + "分" + "</font><br/>"));
                            }

                        }
                    });
        }
    }

    private void animationBefore(View view) {
        nameText.set(name.get());
        name.set("");
        nameTextVisible.set(View.VISIBLE);
        LinearInterpolator lin = new LinearInterpolator();
//        if (animationTextBefore == null) {
        animationTextBefore = AnimationUtils.loadAnimation(
                view.getContext(), R.anim.text_anim);
        animationTextBefore.setAnimationListener(listener);
        animationTextBefore.setInterpolator(lin);
//        }
        ObservableNotifier.alwaysNotify(textAnimation, animationTextBefore);
        if (animationButtonBefore == null) {
            animationButtonBefore = AnimationUtils.loadAnimation(
                    view.getContext(), R.anim.shan_anim);
            animationButtonBefore.setInterpolator(lin);
        }
        ObservableNotifier.alwaysNotify(searchAnimation, animationButtonBefore);
        if (animationLoading == null) {
            animationLoading = AnimationUtils.loadAnimation(
                    view.getContext(), R.anim.serch_button_anim);
            animationLoading.setInterpolator(lin);
        }
    }

    private void animationLoading() {
        ObservableNotifier.alwaysNotify(searchAnimation, animationLoading);
    }

    private void animationAfter() {
        textAnimation.set(null);
        searchAnimation.set(null);
    }

    private CheckWay checkName() {
        if (name.get() == null || name.get().trim().equals("")) {
            return CheckWay.Null_Way;
        }
        if (StringMatcherUtils.isMatchNumber(name.get().trim())) {
            return CheckWay.Is_Num;
        }
        if (StringMatcherUtils.isMatchChinese(name.get().trim())) {
            if (name.get().trim().length() > 8) {
                return CheckWay.Name_length;
            } else {
                return CheckWay.Is_Name;
            }
        } else {
            return CheckWay.Name_Not_China;
        }
    }


    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            nameTextVisible.set(View.INVISIBLE);
            animationLoading();
            if (checkWay == CheckWay.Is_Num) {
                doNum();
            } else if (checkWay == CheckWay.Is_Name) {
                doName();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private enum CheckWay {
        Null_Way(1),
        Special_Char_way(2),
        Name_Not_China(3),
        Name_length(4),
        Is_Name(5),
        Is_Num(6);

        private CheckWay(int way) {

        }
    }
}
