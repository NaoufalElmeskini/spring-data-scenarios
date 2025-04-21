package io.tintinapp.learningdata;


import io.tintinapp.learningdata.dto.CompanyDto;
import io.tintinapp.learningdata.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyDto> list(){
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream()
                .map(Converter::fromCompanyToCompanyDto)
                .toList();
    }


}
