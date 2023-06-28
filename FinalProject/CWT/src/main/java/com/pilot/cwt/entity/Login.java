package com.pilot.cwt.entity;

public class Login {
    private String lastName;

    private String firstName;

    private String email;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNam) {
        this.firstName = firstNam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        StringBuilder customerInfo = new StringBuilder();
        for (int i = 0; i <= 1; i++) {
            customerInfo.append(" ");
        }
        return "Customer{" + "\n" +
                customerInfo + "lastname='" + lastName + '\'' + "," + "\n" +
                customerInfo + "firstname='" + firstName + '\'' + "," + "\n" +
                customerInfo + "email='" + email + '\'' + "," + "\n" +
                '}';
    }
}
