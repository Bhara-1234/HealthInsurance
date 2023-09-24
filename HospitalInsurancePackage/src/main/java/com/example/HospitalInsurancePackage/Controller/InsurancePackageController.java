package com.example.HospitalInsurancePackage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.HospitalInsurancePackage.Contractors.InsurancePackageService;
import com.example.HospitalInsurancePackage.model.DiseaseDetails;
import com.example.HospitalInsurancePackage.model.InsurancePackage;
import com.example.HospitalInsurancePackage.model.InsurancePackageCoveredDisease;

@Controller
public class InsurancePackageController {

	private final InsurancePackageService insurancePackageRepository;

	@Autowired
	public InsurancePackageController(InsurancePackageService insurancePackageRepository) {
		this.insurancePackageRepository = insurancePackageRepository;
	}

	@GetMapping("/list")
	public String getAllInsurancePackages(Model model) {
		// Retrieve data from the repository
		List<InsurancePackage> insurancePackages = insurancePackageRepository.getAllInsurancePackages();

		// Add the data to the model for rendering in the Thymeleaf template
		model.addAttribute("insurancePackages", insurancePackages);

		// Return the name of the Thymeleaf template to render
		return "packages"; // This assumes you have a Thymeleaf template named
							// "insurance-packages-list.html"
	}

	@GetMapping("/view-insurance/{inspId}")
	public String viewInsurance(@PathVariable int inspId, Model model) {
		// Get the covered diseases for the given inspId
		List<InsurancePackageCoveredDisease> coveredDiseases = insurancePackageRepository
				.getCoveredDiseasesByPackageId(inspId);

		// Add the covered diseases to the model
		model.addAttribute("coveredDiseases", coveredDiseases);

		return "insurance-package-view";
	}

	@GetMapping("/diseasedetails/{discId}")
	public String viewDiseseDetails(@PathVariable int discId, Model model) {
		DiseaseDetails dd = insurancePackageRepository.getDiseaseDetailsById(discId);
		model.addAttribute("diseasedetails", dd);
		System.out.println("age1");
		return "diseasedetails";

	}

	@RequestMapping(value = "/start")
	public String packages() {
		return "redirect:/list";

	}

	@GetMapping("/filteredpackages")
	public String getFilteredPackages(@RequestParam("status") String status, @RequestParam("age") String age,
			Model model) {
		System.out.println(status);
		if ("ALL".equals(status) && age.equals("")) {
			System.out.println("if");
			List<InsurancePackage> insurancePackages = insurancePackageRepository.getAllInsurancePackages();

			// Add the data to the model for rendering in the Thymeleaf template
			System.out.println(insurancePackages);
			model.addAttribute("insurancePackages", insurancePackages);

			// Return the name of the Thymeleaf template to render
			return "packages";
		}
		else if ("ALL".equals(status) && !age.equals("")) {
			System.out.println("if");
			List<InsurancePackage> insurancePackages = insurancePackageRepository.getAllInsurancePackagesByAge(Integer.parseInt(age));

			// Add the data to the model for rendering in the Thymeleaf template
			System.out.println(insurancePackages);
			model.addAttribute("insurancePackages", insurancePackages);

			// Return the name of the Thymeleaf template to render
			return "packages";
		}
		else {

			if (age.equals("")) {
				List<InsurancePackage> insurancePackages = insurancePackageRepository.getPackagesByStatus(status);
				model.addAttribute("insurancePackages", insurancePackages);
				return "packages";
			} else {
				List<InsurancePackage> packages = insurancePackageRepository.getFilteredPackages(status,
						Integer.parseInt(age));
				model.addAttribute("insurancePackages", packages);
				System.out.println(packages);
				System.out.println(age);

				return "packages";

			}
		}

	}
}
