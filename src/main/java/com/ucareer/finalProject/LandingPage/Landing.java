package com.ucareer.finalProject.LandingPage;

import com.ucareer.finalProject.galleries.Gallery;
import com.ucareer.finalProject.galleryItems.GalleryItem;
import com.ucareer.finalProject.heads.Head;
import com.ucareer.finalProject.menusItems.MenusItem;
import com.ucareer.finalProject.users.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class Landing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Head headId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Gallery galleryId;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userId;

    @ManyToMany
    private List<MenusItem> menusItems = new ArrayList<>();

    @ManyToMany
    private List<GalleryItem> galleryItems = new ArrayList<>();

    private String slug;

    @Temporal(TIMESTAMP)
    @Column(name = "create_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TIMESTAMP)
    @Column(name = "modified_at", columnDefinition = "TIMESTAMP  default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date modifiedAt;

    public List<GalleryItem> getGalleryItems() {
        return galleryItems;
    }

    public void setGalleryItems(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<MenusItem> getMenusItems() {
        return menusItems;
    }

    public void setMenusItems(List<MenusItem> menusItems) {
        this.menusItems = menusItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Head getHeadId() {
        return headId;
    }

    public void setHeadId(Head headId) {
        this.headId = headId;
    }

    public Gallery getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Gallery galleryId) {
        this.galleryId = galleryId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
