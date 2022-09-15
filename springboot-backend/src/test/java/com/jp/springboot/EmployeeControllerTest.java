package com.jp.springboot;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.springboot.controller.EmployeeController;
import com.jp.springboot.model.Employee;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeController employeeController;

    ObjectMapper objectMapper = new ObjectMapper();

    Employee getEmployee1() {
     
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Hunter");
        employee.setLastName("Sigmund");
        employee.setEmailId("hunter@gmail.com");

        return employee;
    }

    Employee getEmployee2() {
     
        Employee employee = new Employee();
        employee.setId(2);
        employee.setFirstName("Noah");
        employee.setLastName("Taffah");
        employee.setEmailId("noah@gmail.com");

        return employee;
    }

    @Test 
    void getAllEmployees_shouldHaveCorrectResponse() throws Exception {
        //arrange
        List<Employee> employees = Arrays.asList(getEmployee1(), getEmployee2());

        //act
        when(employeeController.getAllEmployees()).thenReturn(employees);
        ResultActions result = mvc.perform(get("/api/v1/employees").accept(MediaType.APPLICATION_JSON));

        //assert
        verify(employeeController, times(1)).getAllEmployees();
        result.andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(objectMapper.writeValueAsString(employees))
        );

    }

    @Test
    void createEmployee_shouldHaveCorrectResponse() throws Exception {
        // arrange
        Employee employee = getEmployee1();

        // act
        when(employeeController.createEmployee(any(Employee.class))).thenReturn(employee);
        ResultActions result = mvc.perform(post("/api/v1/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)));

        // assert
        verify(employeeController, times(1)).createEmployee(any(Employee.class));
        result.andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(objectMapper.writeValueAsString(employee))
        );
    }


    @Test
    void getEmployeeById_shouldHaveCorrectResponse() throws Exception {
        // arrange
        Employee employee = getEmployee1();
        ResponseEntity<Employee> employeeResponse = new ResponseEntity<>(employee, HttpStatus.OK);
        Long id = employee.getId();

        // act
        when(employeeController.getEmployeeById(id)).thenReturn(employeeResponse);
        ResultActions result = mvc.perform(get("/api/v1/employees/"+id).accept(MediaType.APPLICATION_JSON));
    
        // assert
        verify(employeeController, times(1)).getEmployeeById(id);
        result.andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(objectMapper.writeValueAsString(employee))
        );
    }

    @Test
    void updateEmployee_shouldHaveCorrectResponse() throws Exception {
        // arrange
        Employee employee = getEmployee1();
        Long id = employee.getId();
        employee.setFirstName("Rashmi");
        ResponseEntity<Employee> employeeResponse = new ResponseEntity<>(employee, HttpStatus.OK);

        // act
        when(employeeController.updateEmployee(anyLong(), any(Employee.class))).thenReturn(employeeResponse);
        ResultActions result = mvc.perform(put("/api/v1/employees/"+id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)));

        // assert
        verify(employeeController, times(1)).updateEmployee(anyLong(), any(Employee.class));
        result.andExpectAll(
            status().is2xxSuccessful()
        );
    }

    @Test
    void deleteEmployee_shouldHaveCorrectResponse() throws Exception {
        // arrange
        

        // act

        // assert

    }



 
}