package com.gordon.main.api.response;

/**
 * @author Gordon
 * @since 2017/4/11
 * do()
 */

public class NameFiveResponse extends BaseResponse {
    public String getNameScore() {
        return nameScore;
    }

    public void setNameScore(String nameScore) {
        this.nameScore = nameScore;
    }

    private String nameScore;
}
