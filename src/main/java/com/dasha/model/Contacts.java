package com.dasha.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private String workEmail;

    @Override
    public String toString(){
        return String.format("phone: %s,\temail: %s,\twork Email:%s", phone, email, workEmail);
    }

}
