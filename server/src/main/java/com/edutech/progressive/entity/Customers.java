package com.edutech.progressive.entity;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customers implements Comparable<Customers> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Kept nullable till later stages (MS2 Day 12)
    private String role;

    public Customers() {}

    public Customers(int customerId, String name, String email, String username, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Customers(int customerId, String name, String email, String username, String password, String role) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public int compareTo(Customers other) {
        if (other == null) return -1;
        String n1 = this.name;
        String n2 = other.name;
        if (n1 == null && n2 == null) return 0;
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        return n1.compareToIgnoreCase(n2);
    }
}
