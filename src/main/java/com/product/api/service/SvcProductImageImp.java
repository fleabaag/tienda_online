package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage {

    private static final int ACTIVE = 1;
    private static final int INACTIVE = 0;

    private final RepoProduct repoProduct;
    private final RepoProductImage repoProductImage;

    @Value("${app.upload.dir:uploads}")
    private final String uploadDir;
    @Value("${app.upload.images:img}")
    private final String uploadImages;

    public SvcProductImageImp(
            RepoProduct repoProduct,
            RepoProductImage repoProductImage,
            @Value("${app.upload.dir:uploads}") String uploadDir,
            @Value("${app.upload.images:img}") String uploadImages) {
        this.repoProduct = repoProduct;
        this.repoProductImage = repoProductImage;
        this.uploadDir = uploadDir;
        this.uploadImages = uploadImages;
    }

    @Override
    public List<DtoProductImageOut> getProductImages(Integer productId) {
        validateProductId(productId);
        try {
            return repoProductImage
                    .findByProductIdAndStatusOrderByProductImageIdAsc(productId, ACTIVE)
                    .stream()
                    .map(this::toDto)
                    .toList();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> createProductImage(Integer productId, DtoProductImageIn in) {
        if (in == null || in.getImage() == null || in.getImage().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La imagen es obligatoria.");
        }

        try {
            validateProductId(productId);           
            String encodedImage = extractBase64(in.getImage());
            byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
            String fileName = UUID.randomUUID().toString() + ".png";
            Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);

            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);

            ProductImage productImage = new ProductImage();
            productImage.setProduct_id(productId);
            productImage.setImage("/img/product/" + fileName);
            productImage.setStatus(ACTIVE);
            repoProductImage.save(productImage);

            return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La imagen no contiene un Base64 válido.");
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo de imagen.");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> deleteProductImage(Integer productId, Integer productImageId) {
        validateProductId(productId);
        validateId(productImageId, "El id de la imagen no es válido.");

        try {
            ProductImage productImage = repoProductImage
                    .findByProductImageIdAndProductId(productImageId, productId)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                            "El id de la imagen del producto no existe."));

            if (!Integer.valueOf(ACTIVE).equals(productImage.getStatus())) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen del producto no existe.");
            }

            productImage.setStatus(INACTIVE);
            repoProductImage.save(productImage);
            return ResponseEntity.ok("La imagen ha sido eliminada");
        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private String extractBase64(String image) {
        String value = image.trim();
        if (value.startsWith("data:image/")) {
            int commaIndex = value.indexOf(',');
            if (commaIndex < 0 || commaIndex == value.length() - 1) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "El formato de la imagen no es válido.");
            }
            return value.substring(commaIndex + 1);
        }
        return value;
    }

    private void validateProductId(Integer productId) {
        validateId(productId, "El id del producto no es válido.");
        try {
            if (repoProduct.findById(productId).isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe.");
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void validateId(Integer id, String message) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, message);
        }
    }

    private DtoProductImageOut toDto(ProductImage image) {
        DtoProductImageOut out = new DtoProductImageOut();
        out.setProductImageId(image.getProduct_image_id());
        out.setProduct_id(image.getProduct_id());
        out.setImage(image.getImage());
        return out;
    }
}
