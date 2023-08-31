package com.sdd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Memployee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long employeepk;

  private String nama;
  private String alamat;

  public String getNama() {
    return nama != null ? nama.trim() : nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getAlamat() {
    return alamat != null ? alamat.trim() : alamat;
  }

  public void setAlamat(String alamat) {
    this.alamat = alamat;
  }

  public Long getEmployeepk() {
    return employeepk;
  }

  public void setEmployeepk(Long employeepk) {
    this.employeepk = employeepk;
  }

}
