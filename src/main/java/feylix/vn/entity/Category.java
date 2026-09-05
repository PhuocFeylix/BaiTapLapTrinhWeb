package feylix.vn.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;

	@Column(name = "categoryname", columnDefinition = "NVARCHAR(200) NOT NULL")
	private String categoryname;

	@Column(name = "images", columnDefinition = "NVARCHAR(MAX)")
	private String images;

	@Column(name = "status")
	private int status;

	// Quan hệ 1-N với Product (mappedBy chỉ định thuộc tính category trong Product)
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products;

	public Category() {
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Category(String categoryname, String images, int status) {
		this.categoryname = categoryname;
		this.images = images;
		this.status = status;
	}

	// Getters và Setters
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}