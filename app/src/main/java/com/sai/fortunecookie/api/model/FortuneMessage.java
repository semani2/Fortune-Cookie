package com.sai.fortunecookie.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sai on 1/23/18.
 */

public class FortuneMessage {

    @SerializedName("fortune")
    @Expose
    private String[] fortune;

    public String[] getFortune ()
    {
        return fortune;
    }

    public void setFortune (String[] fortune)
    {
        this.fortune = fortune;
    }

}
