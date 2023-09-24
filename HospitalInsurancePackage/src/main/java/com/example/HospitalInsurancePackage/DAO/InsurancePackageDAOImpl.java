package com.example.HospitalInsurancePackage.DAO;

import com.example.HospitalInsurancePackage.Contractors.InsurancePackageDAO;
import com.example.HospitalInsurancePackage.Mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import com.example.HospitalInsurancePackage.model.DiseaseDetails;
import com.example.HospitalInsurancePackage.model.InsurancePackage;
import com.example.HospitalInsurancePackage.model.InsurancePackageCoveredDisease;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InsurancePackageDAOImpl implements InsurancePackageDAO {
	

	private final JdbcTemplate jdbcTemplate;
	private static final String GET_INSURANCE_PACKAGES = "SELECT * FROM InsurancePackages";
	private static final String GET_COVERED_DISEASES_BY_PACKAGE_ID = "SELECT * FROM InsurancePackageCoveredDiseases WHERE insp_id = ?";
	private static final String GET_DISEASE_DETAILS_BY_DISEASE_ID = "select * from DiseaseDetails where disc_id=?";
	private static final String GET_FILTERED_PACKAGES="select * FROM InsurancePackages where insp_status=? and ? BETWEEN insp_agelimit_start AND insp_agelimit_end;";
	private static final String GET_PACKAGES_BY_STATUS="select * FROM InsurancePackages where insp_status=?";
	private static final String GET_FILTERED_PACKAGES_BY_AGE = "select * FROM InsurancePackages where ? BETWEEN insp_agelimit_start AND insp_agelimit_end;";
	@Autowired
	public InsurancePackageDAOImpl(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<InsurancePackage> getAllInsurancePackages() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(GET_INSURANCE_PACKAGES, new InsurancePackageRowMapper());
	}

	@SuppressWarnings("deprecation")
	public List<InsurancePackageCoveredDisease> getCoveredDiseasesByPackageId(int packageId) {
		return jdbcTemplate.query(GET_COVERED_DISEASES_BY_PACKAGE_ID, new Object[] { packageId },
				new InsurancePackageCoveredDiseaseRowMapper());
	}

	@SuppressWarnings("deprecation")
	@Override
	public DiseaseDetails getDetailsByDiseaseId(int discId) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(GET_DISEASE_DETAILS_BY_DISEASE_ID, new Object[] { discId },
				new DiseseDetailsRowMapper());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<InsurancePackage> getFiteredDiseases(String status, int age) {
		// TODO Auto-generated method stub
		System.out.println("dao"+status+age);
		//System.out.println(jdbcTemplate.query("select * FROM InsurancePackages", values, new InsurancePackageRowMapper()));
		return jdbcTemplate.query(GET_FILTERED_PACKAGES,new Object[] {status,age}, new InsurancePackageRowMapper());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<InsurancePackage> getPackagesByStatus(String status) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(GET_PACKAGES_BY_STATUS, new Object[] {status} ,new InsurancePackageRowMapper());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<InsurancePackage> getAllInsurancePackagesByAge(int age) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(GET_FILTERED_PACKAGES_BY_AGE,new Object[] {age}, new InsurancePackageRowMapper());
	}

	 
}
