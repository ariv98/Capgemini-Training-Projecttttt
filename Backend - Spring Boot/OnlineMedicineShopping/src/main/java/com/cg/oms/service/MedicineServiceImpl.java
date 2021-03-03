package com.cg.oms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.oms.converter.MedicineConverter;
import com.cg.oms.exception.MedicineNotFoundException;
import com.cg.oms.model.Medicine;
import com.cg.oms.repository.MedicineRepository;
import com.cg.oms.vo.MedicineVo;

/**
 * Medicine Service
 * 
 * @author Saranish
 *
 */
@Service
public class MedicineServiceImpl implements MedicineService
{
	public static final String EXCEPTION_MESSAGE = "No Medicine found with this id ";

	/**
	 * Autowiring the repository and the converter of medicinee
	 */
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private MedicineConverter medicineConverter;

	/**
	 * Used to list all the medicine from the database
	 * 
	 * @return
	 */
	@Override
	public List<MedicineVo> getAllMedicine()
	{
		List<Medicine> medicine = medicineRepository.findAll();
		return medicineConverter.modelToVo(medicine);
	}

	/**
	 * Get a specific medicine of the given ID
	 * 
	 * @param id
	 * @return
	 * @throws MedicineNotFoundException
	 */
	@Override
	public MedicineVo getMedicineById(Long id) throws MedicineNotFoundException
	{
		Medicine medicine = medicineRepository.findById(id)
				.orElseThrow(() -> new MedicineNotFoundException(EXCEPTION_MESSAGE + id));
		return medicineConverter.modelToVo(medicine);
	}

	/**
	 * Used to get medicine by given name
	 * 
	 * @param name
	 * @return
	 * @throws MedicineNotFoundException
	 */
	@Override
	public MedicineVo getMedicineByName(String name) throws MedicineNotFoundException
	{
		Medicine medicine = ((Optional<Medicine>) medicineRepository.findByMedicineName(name))
				.orElseThrow(() -> new MedicineNotFoundException(EXCEPTION_MESSAGE + name));
		return medicineConverter.modelToVo(medicine);
	}

	/**
	 * Used to retrieve the list of the medicine based on the given medicine name
	 * 
	 * @param name
	 * @return
	 * @throws MedicineNotFoundException
	 */
	@Override
	public MedicineVo getMedicineByManufacturerName(String name) throws MedicineNotFoundException
	{
		Medicine medicine = ((Optional<Medicine>) medicineRepository.findByMedicineManufacturerName(name))
				.orElseThrow(() -> new MedicineNotFoundException(EXCEPTION_MESSAGE + name));
		return medicineConverter.modelToVo(medicine);
	}

	/**
	 * Used to store the given medicine Object passed from the controller
	 * 
	 * @param medicineVo
	 * @return
	 */
	@Override
	public String saveMedicine(MedicineVo medicineVo)
	{
		Medicine medicine = medicineConverter.voToModel(medicineVo);
		medicine = medicineRepository.save(medicine);
		MedicineVo savedVo = medicineConverter.modelToVo(medicine);
		return "Registered SuccessFully!!! " + savedVo.toString();
	}

	/**
	 * Used to update the medicine of given id and the changed object
	 * 
	 * @param id
	 * @param newMedicine
	 * @return
	 * @throws MedicineNotFoundException
	 */
	@Override
	public String updateMedicine(Long id, MedicineVo newMedicine) throws MedicineNotFoundException
	{
		Medicine oldMedicine = medicineRepository.findById(id)
				.orElseThrow(() -> new MedicineNotFoundException(EXCEPTION_MESSAGE + id));
		oldMedicine.setMedicineCategory(newMedicine.getMedicineCategory());
		oldMedicine.setMedicineDescription(newMedicine.getMedicineDescription());
		oldMedicine.setMedicineExpiryDate(newMedicine.getMedicineExpiryDate());
		oldMedicine.setMedicineManufactureDate(newMedicine.getMedicineManufacturerDate());
		oldMedicine.setMedicineManufacturerName(newMedicine.getMedicineManufacturerName());
		oldMedicine.setMedicineName(newMedicine.getMedicineName());
		oldMedicine.setMedicinePrice(newMedicine.getMedicinePrice());
		oldMedicine.setMedicineQuantity(newMedicine.getMedicineQuantity());
		Medicine updatedMedicine = medicineRepository.save(oldMedicine);
		MedicineVo savedVo = medicineConverter.modelToVo(updatedMedicine);
		return "Updated Successfully" + savedVo.toString();
	}

	/**
	 * Used to delete the medicine
	 * 
	 * @param id
	 * @return
	 * @throws MedicineNotFoundException
	 */
	@Override
	public String deleteMedicine(Long id) throws MedicineNotFoundException
	{
		Medicine medicine = medicineRepository.findById(id)
				.orElseThrow(() -> new MedicineNotFoundException(EXCEPTION_MESSAGE + id));
		medicineRepository.delete(medicine);
		return "Record Deleted Successfully!!";
	}

}
