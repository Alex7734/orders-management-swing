package logic;

import data.ProductDAO;
import model.Product;
import util.MaxIdFlagEnum;

import java.util.List;

import static model.Product.validProduct;
import static util.IdGenerator.generateId;

/**
 * Business logic class of Product class.
 * Contains a validator, data access object and a product object.
 */
public class ProductBLL {
    private final ProductDAO productDAO;


    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Creates a new product object and inserts it into the database.
     * @param name the name of the product
     * @param amount the amount of the product
     * @param price the price of the product
     * @param manufacturer the manufacturer of the product
     * @return true if the product was successfully inserted, false otherwise
     */
    public boolean insertProduct(String name, int amount, double price, String manufacturer) {
        Product product = new Product(generateId(MaxIdFlagEnum.PRODUCT), name, amount, price, manufacturer);
        if (!validProduct(product))
            return false;

        productDAO.insert(product);

        return true;
    }

    /**
     * Updates a product object from the database.
     * @param productId the id of the product to be updated
     * @param name the new name of the product
     * @param amount the new amount of the product
     * @param price the new price of the product
     * @param manufacturer the new manufacturer of the product
     * @return true if the product was successfully updated, false otherwise
     */
    public boolean updateProduct(long productId, String name, int amount, double price, String manufacturer) {
        Product product = new Product(productId, name, amount, price, manufacturer);
        if (!validProduct(product))
            return false;

        productDAO.update(product);

        return true;
    }

    /**
     * Finds a product in the database.
     * @param id the id of the product to be found
     * @return the product if it was found, null otherwise
     */
    public Product findProduct(long id) {
        return productDAO.findById(id);
    }

    /**
     * Finds all products in the database.
     * @return List of all products.
     */
    public List<Product> findAllProducts() {
        return (List<Product>)  productDAO.findAll();
    }

    /**
     * Deletes a product object from the database.
     * @param id the id of the product to be deleted
     */
    public void deleteProduct(long id) {
        productDAO.delete(id);
    }
}
