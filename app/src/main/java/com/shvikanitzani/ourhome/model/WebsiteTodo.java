package com.shvikanitzani.ourhome.model;

import java.io.Serializable;

/**
 * Created by PC on 24/05/2015.
 */
public class WebsiteTodo implements Serializable{
    public String title;
    public String details;

    private WebsiteTodo(){}


    public  WebsiteTodo(String title, String details)
    {
        this.title = title;
        this.details = details;
    }
}
