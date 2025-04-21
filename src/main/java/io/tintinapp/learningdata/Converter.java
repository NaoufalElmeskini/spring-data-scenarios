package io.tintinapp.learningdata;

import io.tintinapp.learningdata.dto.CompanyDto;
import io.tintinapp.learningdata.dto.EmployeeDto;
import io.tintinapp.learningdata.entity.Company;
import io.tintinapp.learningdata.entity.Employee;

public class Converter {

    public static CompanyDto fromCompanyToCompanyDto(Company company){
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .employees(company.getEmployees().stream().map(Converter::fromEmployeeToEmployeeDto).toList())
                .build();
    }

    public static EmployeeDto fromEmployeeToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .lastname(employee.getLastname())
                .firstname(employee.getFirstname())
                .companyName(employee.getCompany().getName())
                .build();
    }

}
