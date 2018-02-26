package com.example.dill.app.rest.dishes.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dill.app.domain.dishes.Dish;
import com.example.dill.app.domain.dishes.DishRepository;
import com.example.dill.app.exceptions.DishNotFoundException;

@Service
public class DishesServiceImpl implements DishesService {

	@Autowired
	private DishRepository dishRepository;
	
	@Override
	public DishDto getDish(String id) {
	
		Dish dish = dishRepository.findById(id);
		if(dish == null){
			throw new DishNotFoundException(id);
		}else{
			return new DishDto(dish.getType(), dish.getName(), dish.getId());
		}
	}

	@Override
	public DishDto createDish(DishDto dishReqDto) {
		Dish dish = new Dish(dishReqDto.getType(), dishReqDto.getName());
		dishRepository.save(dish);
		
		dish = dishRepository.findByName(dish.getName());
		DishDto dishResDto = new DishDto(dish.getType(), dish.getName(), dish.getId());
		
		return dishResDto;
	}

	@Override
	public List<DishDto> getAllDishes() {
		List<Dish> dishes = dishRepository.findAll();
		if(dishes.size()==0){
			throw new DishNotFoundException();
		}else{
			List<DishDto> dishesDto = new ArrayList<DishDto>();
			
			for (Dish dish : dishes) {
				DishDto dishDto = new DishDto(dish.getType(), dish.getName(), dish.getId());
				dishesDto.add(dishDto);
			}
			return dishesDto;
		}
	}
}
