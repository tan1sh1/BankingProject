package Entities;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private String CID;
    private String name;
    private String phone;
    private String email;
    private String password;
    private LocalDate dob;

    public Customer(){

    }

    public Customer(String CID, String cname, String phone, String email, String password, LocalDate dob) {
        this.CID = CID;
        this.name=name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CID='" + CID + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this!= o) return false;
        Customer customer = (Customer) o;
        return Objects.equals(CID, customer.CID) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CID, name, phone, email, password, dob);
    }
}
