package com.example.tienda.ropa.tienda_ropa.services.clothe;

import com.example.tienda.ropa.tienda_ropa.classes.clothe.ClothePubli;
import com.example.tienda.ropa.tienda_ropa.classes.elements.ResponseEntityGenerator;
import com.example.tienda.ropa.tienda_ropa.entities.clothe.ClotheStock;
import com.example.tienda.ropa.tienda_ropa.repositories.clothe.IClothesStockRepository;
import com.example.tienda.ropa.tienda_ropa.repositories.elements.IImageRespository;
import com.example.tienda.ropa.tienda_ropa.services.user.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClothesStockServices {

    @Autowired
    private IClothesStockRepository clothesRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IImageRespository imageRespository;
    
    @Transactional
    public ResponseEntity<?> save(ClothePubli publi){
       ClotheStock clothe = publi.getClothe();
        ClotheStock clotheStock = clothesRepository.save(clothe);
       
        associateImages(clothe);

        emailService.sendEmailVips(publi);

        return ResponseEntityGenerator.genetateResponseEntity("Prenda creada con éxito",201,clotheStock);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable){
        Page<ClotheStock> clothes = clothesRepository.findAll(pageable);
        if(!clothes.isEmpty()) {
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("No se encontraron prendas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByCode(String code, Pageable pageable){
        Page<ClotheStock> clothes = clothesRepository.findByCode(code, pageable);
        if(!clothes.isEmpty()){
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("Prendas no encontradas",404,null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByParams(String name, String size, Float priceMin, Float priceMax, String genericType, String specificType, Pageable pageable){
        Page<ClotheStock> clothes = clothesRepository.findByParameters(name, size, priceMin,priceMax, genericType, specificType, pageable);
        if(!clothes.isEmpty()) {
            return ResponseEntityGenerator.genetateResponseEntity("Prendas encontradas con éxito",200,clothes);
        }else{
            return ResponseEntityGenerator.genetateResponseEntity("No se encontraron prendas",404,null);
        }
    }



    private void associateImages(ClotheStock clothe) {
        clothe.getImages().forEach(image -> {
            image.setClotheStock(clothe);
            imageRespository.save(image);
        });
    }

}
