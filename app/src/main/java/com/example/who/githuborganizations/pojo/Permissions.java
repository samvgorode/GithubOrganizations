package com.example.who.githuborganizations.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by who on 26.09.2017.
 */

public class Permissions {

    @SerializedName("admin")
    @Expose
    private Boolean admin;
    @SerializedName("push")
    @Expose
    private Boolean push;
    @SerializedName("pull")
    @Expose
    private Boolean pull;

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getPush() {
        return push;
    }

    public void setPush(Boolean push) {
        this.push = push;
    }

    public Boolean getPull() {
        return pull;
    }

    public void setPull(Boolean pull) {
        this.pull = pull;
    }

}
