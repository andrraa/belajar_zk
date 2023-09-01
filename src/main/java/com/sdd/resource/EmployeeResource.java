package com.sdd.resource;

import java.util.ArrayList;
import java.util.List;

import com.sdd.entity.Employee;
import com.sdd.models.EmployeeNamaModel;
import com.sdd.models.EmployeePKModel;
import com.sdd.models.ResponseAllModel;
import com.sdd.repository.EmployeeRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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

  @Path("/deleteEmployee/{employeepk}")
  @DELETE
  @Transactional
  public ResponseAllModel deleteEmployeeById(@PathParam("employeepk") Long employeepk) {
    ResponseAllModel responseAllModel = new ResponseAllModel();
    try {
      if (employeeRepository.deleteById(employeepk)) {
        responseAllModel.setCode(Long.valueOf(200));
        responseAllModel.setMessage("Sukses Menghapus!");
      } else {
        responseAllModel.setCode(Long.valueOf(404));
        responseAllModel.setMessage("Gagal Menghapus!");
      }
    } catch (Exception e) {
      e.printStackTrace();
      responseAllModel.setCode(Long.valueOf(500));
      responseAllModel.setMessage("Error!");
    }
    return responseAllModel;
  }

  @Path("/editEmployee")
  @PUT
  @Transactional
  public ResponseAllModel editEmployee(Employee employee) {
    ResponseAllModel responseAllModel = new ResponseAllModel();

    try {
      Employee employee2 = employeeRepository.findById(employee.getEmployeepk());

      if (employee2 == null) {
        responseAllModel.setCode(Long.valueOf(404));
        responseAllModel.setMessage("Gagal Bro");
      } else {
        employee2.setNama(employee.getNama());
        employee2.setAlamat(employee.getAlamat());
        responseAllModel.setCode(Long.valueOf(200));
        responseAllModel.setMessage("Berhasil Bro");
        responseAllModel.setData(employee2);
      }

    } catch (Exception e) {
      e.getStackTrace();
    }
    return responseAllModel;
  }

  @Path("/patchEmployee")
  @PATCH
  @Transactional
  public ResponseAllModel patchEmployee(EmployeeNamaModel employeeNamaModel) {
    ResponseAllModel responseAllModel = new ResponseAllModel();

    try {
      Employee employee2 = employeeRepository.findById(employeeNamaModel.getEmployeepk());

      if (employee2 == null) {
        responseAllModel.setCode(Long.valueOf(404));
        responseAllModel.setMessage("Data Tidak Ada");
      } else {
        employee2.setNama(employeeNamaModel.getNama());
        responseAllModel.setCode(Long.valueOf(200));
        responseAllModel.setMessage("Berhasil Diubah Bro");
        responseAllModel.setData(employee2);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return responseAllModel;
  }

}
