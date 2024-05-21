package dmytro.hadiuchko.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Table(name = "cart_item")
@SQLDelete(sql = "UPDATE cart_item SET is_deleted = true WHERE id =?")
@SQLRestriction(value = "is_deleted=false")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id", nullable = false)
    private ShoppingCart shoppingCart;
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
