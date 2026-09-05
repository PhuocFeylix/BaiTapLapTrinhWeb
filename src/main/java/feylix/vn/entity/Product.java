package feylix.vn.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@Column(name = "productname", columnDefinition = "NVARCHAR(200) NOT NULL")
	private String productName;

	@Column(name = "price")
	private double price;

	@Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(name = "images", columnDefinition = "NVARCHAR(MAX)")
	private String images;

	@Column(name = "quantity")
	private int quantity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "status")
	private int status;

	// Khai báo mối quan hệ N-1 với Category
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId", nullable = false) // Khóa ngoại kết nối với bảng categories
	private Category category;

	public Product() {
	}

	public Product(String productName, double price, String description, String images, int quantity, int status,
			Category category) {
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.images = images;
		this.quantity = quantity;
		this.status = status;
		this.category = category;
	}

	// Getters và Setters
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}