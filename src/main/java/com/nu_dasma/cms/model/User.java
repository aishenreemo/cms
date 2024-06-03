package com.nu_dasma.cms.model;

public class User {
    public static final int ROLE_UNKNOWN = 0;
    public static final int ROLE_STUDENT = 1;
    public static final int ROLE_ADMINISTRATOR = 2;

    public int roleType;

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;

    public User(int id, int roleType, String email, String firstName, String lastName, String middleName) {
        this.id = id;
        this.roleType = roleType;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public void printInfo() {
        System.out.println("\033[1mUser Info\033[0m");
        System.out.println("---------");
        System.out.printf("ID: %d\n", this.id);
        System.out.printf(
            "Name: %s%s, %s\n",
            this.firstName,
            this.middleName == null ? "" : " " + this.middleName,
            this.lastName
        );
        System.out.printf("Email: %s\n", this.email);
        System.out.printf("Role: %s\n", this.roleType == ROLE_STUDENT ? "Student" : "Administrator");
    }
}
