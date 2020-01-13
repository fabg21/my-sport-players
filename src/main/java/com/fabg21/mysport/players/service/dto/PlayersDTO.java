package com.fabg21.mysport.players.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fabg21.mysport.players.domain.Players} entity.
 */
public class PlayersDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    private LocalDate dateOfBirth;

    private String email;

    private String address;

    private String phone;

    private String avatar;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayersDTO playersDTO = (PlayersDTO) o;
        if (playersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayersDTO{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
