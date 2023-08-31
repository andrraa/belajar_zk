package com.sdd.resource;

import java.util.ArrayList;
import java.util.List;

import com.sdd.entity.Employee;
import com.sdd.models.EmployeePKModel;
import com.sdd.models.ResponseAllModel;
import com.sdd.repository.EmployeeRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("/employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

  @Inject
  EmployeeRepository employeeRepository;

  @Path("/getAllEmployee")
  @GET
  public List<Employee> getAllEmployee() {
    List<Employee> employeeList = new ArrayList<>();
    try {
      employeeList = employeeRepository.listAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return employeeList;
  }

  @Path("/getEmployeeById/{employeepk}")
  @POST
  public Employee getEmployeeById(@PathParam("employeepk") Long employeepk) {
    Employee employee = new Employee();
    try {
      employee = employeeRepository.findById(employeepk);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return employee;
  }

  @Path("/getEmployeeById")
  @POST
  public Employee getEmployeeById2(EmployeePKModel employeePKModel) {
    Employee employee = new Employee();
    try {
      employee = employeeRepository.findById(employeePKModel.getEmployeepk());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return employee;
  }

  @Path("/addEmployee")
  @POST
  @Transactional
  public ResponseAllModel addEmployee(Employee employee) {
    ResponseAllModel responseAllModel = new ResponseAllModel();
    try {
      employeeRepository.persist(employee);
      if (employeeRepository.isPersistent(employee)) {
        responseAllModel.setCode(Long.valueOf(200));
        responseAllModel.setData(employee);
        responseAllModel.setMessage("Sukses!");
      } else {
        responseAllModel.setCode(Long.valueOf(404));
        responseAllModel.setMessage("Bad Request");
      }
    } catch (Exception e) {
      e.printStackTrace();
      responseAllModel.setCode(Long.valueOf(404));
      responseAllModel.setMessage("Bad Request");
    }
    return responseAllModel;
  }

}
