/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql;

import java.util.List;

/**
 *
 * @author gabalca
 */
public class Treballador {
    private Long id;
    private String name;
    private String title;
    private double salary;
    private double salaryAfterTaxes;
    private Departament dept;
    private List<Project> projects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalaryAfterTaxes() {
        return salaryAfterTaxes;
    }

    public void setSalaryAfterTaxes(double salaryAfterTaxes) {
        this.salaryAfterTaxes = salaryAfterTaxes;
    }

    public Departament getDept() {
        return dept;
    }

    public void setDept(Departament dept) {
        this.dept = dept;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Treballador{" + "id=" + id + ", name=" + name + ", title=" + title + ", salary=" + salary + ", salaryAfterTaxes=" + salaryAfterTaxes + ", dept=" + dept + ", projects=" + projects + '}';
    }

    

    
    
    
}
