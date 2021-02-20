package org.mychat.backend.auth.register;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RegisterRequest {
    private String userName;

    private String password;
    private String firstName;

    private String email="";

    private String phoneNumber="";

    private String lastName;

    private String address;

    private Integer pinCode;

    private String dateOfBirth;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    // public RegisterRequest(String userName, String password, String firstName, String email, String phoneNumber,
    //         String lastName, String address, Integer pinCode, String dateOfBirth, Gender gender) {
    //     this.userName = userName;
    //     this.password = password;
    //     this.firstName = firstName;
    //     this.email = email;
    //     this.phoneNumber = phoneNumber;
    //     this.lastName = lastName;
    //     this.address = address;
    //     this.pinCode = pinCode;
    //     this.dateOfBirth = dateOfBirth;
    //     this.gender = gender;
    // }

    @Override
    public String toString() {
        return "RegisterRequest [address=" + address + ", dateOfBirth=" + dateOfBirth + ", email=" + email
                + ", firstName=" + firstName + ",  lastName=" + lastName + ", password="
                + password + ", phoneNumber=" + phoneNumber + ", pinCode=" + pinCode + ", userName=" + userName + "]";
    }
}
