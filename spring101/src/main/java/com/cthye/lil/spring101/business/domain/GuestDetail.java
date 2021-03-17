package com.cthye.lil.spring101.business.domain;


public class GuestDetail {

    private long guest_id;

    private String full_name;

    private String email;

    private String phone_number;

    public long getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(long guest_id) {
        this.guest_id = guest_id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public boolean equals(Object obj) {
        GuestDetail guest = ((GuestDetail) obj);
        return guest_id == guest.guest_id && full_name.equals(guest.full_name) && (email == null || email.equals(guest.email));
    }
}
